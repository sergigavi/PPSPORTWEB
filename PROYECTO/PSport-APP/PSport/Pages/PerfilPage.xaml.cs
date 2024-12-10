using CommunityToolkit.Maui.Behaviors;
using PSport.Utils;
using System.Dynamic;

namespace PSport.Pages;

public partial class PerfilPage : ContentPage
{
	public PerfilPage()
	{
		InitializeComponent();

        //cargarPaleta();
	}

    private void cargarPaleta()
    {
        this.Behaviors.Add(new StatusBarBehavior
        {
            StatusBarColor = Globales._paleta.color2
        });

        this.BackgroundColor = Globales._paleta.color0;

        btnEditar.BackgroundColor = Globales._paleta.color3;
        btnEditar.BorderColor = Globales._paleta.color2;
    }

    private async void ContentPage_Appearing(object sender, EventArgs e)
    {
        cargarPaleta();

		try
		{
            lblDni.Text = Globales._LoggedUser.dni;
            lblNombre.Text = Globales._LoggedUser.nombre;

            string apellidos = "";
            apellidos += Globales._LoggedUser.apellido1 != null ? Globales._LoggedUser.apellido1 + " " : "";
            apellidos += Globales._LoggedUser.apellido2 != null ? Globales._LoggedUser.apellido2 : "";
            lblApellidos.Text = apellidos;

            lblFechaNac.Text = Globales._LoggedUser.fechaNacimiento;
            lblCorreo.Text = Globales._LoggedUser.email;
            lblRol.Text = Globales._LoggedUser.rol;
        }
		catch (Exception exc)
		{
            exc.ToString();
		}

        try
        {
            PickerPaleta.ItemsSource = Globales.MisPaletas.Select(p => p.nombre).ToList();
            //PickerPaleta.SelectedItem = "1";
        }
        catch (Exception exc)
        {
            exc.ToString();
        }

        try
        {
            string idusuario = Globales._LoggedUser.id;
            Globales._infoSelectedJugador = await Globales._APICONTROLLER.getAllInfoJugadorByIdUsuario(idusuario);
        }
        catch (Exception exc)
        {

            exc.ToString();
        }
    }

    private async void btnEditar_Clicked(object sender, EventArgs e)
    {
        //Obtengo los cambios
        string iduser = Globales._LoggedUser.id;

        if (entryNuevaPass1.Text == null || entryNuevaPass2.Text == null)
        {
            await DisplayAlert("AVISO", "Debes introducir la nueva contraseña dos veces","Entendido");
            return;
        }

        if (entryNuevaPass1.Text != entryNuevaPass2.Text)
        {
            await DisplayAlert("AVISO", "Las contraseñas deben coincidir", "Entendido");
            return;
        }

        dynamic nuevoUser = new ExpandoObject();
        nuevoUser.email = lblCorreo.Text;
        nuevoUser.password = entryNuevaPass1.Text;

        var res = await Globales._APICONTROLLER.EditarPerfil(iduser, nuevoUser);

        string newid = res.id;

        if (newid != null)
        {
            await DisplayAlert("AVISO", "Usuario modificado correctamente", "Entendido");
        }

    }

    private void PickerPaleta_SelectedIndexChanged(object sender, EventArgs e)
    {

        try
        {
            string paletanueva = this.PickerPaleta.SelectedItem.ToString();

            Globales._paleta = Globales.MisPaletas.Find(p => p.nombre == paletanueva);
            ContentPage_Appearing(null, null);
        }
        catch (Exception exc)
        {
            exc.ToString();
        }
        
    }
}