using CommunityToolkit.Maui.Behaviors;
using PSport.Utils;

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

    private void ContentPage_Appearing(object sender, EventArgs e)
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
    }

    private async void btnEditar_Clicked(object sender, EventArgs e)
    {
        //Obtengo los cambios


        await Globales._APICONTROLLER.EditarPerfil();
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