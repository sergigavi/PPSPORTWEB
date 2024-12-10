using CommunityToolkit.Maui.Behaviors;
using PSport.Model.DTOs;
using PSport.Utils;

namespace PSport.Pages;

public partial class RegisterPage : ContentPage
{
	public RegisterPage()
	{
		InitializeComponent();
        CargarPaleta();
	}

    private void CargarPaleta()
    {
        this.Behaviors.Add(new StatusBarBehavior
        {
            StatusBarColor = Globales._paleta.color2
        }); 

        this.BackgroundColor = Globales._paleta.color0;
        this.btnRegistrarse.BackgroundColor = Globales._paleta.color3;
        this.btnLogin.BackgroundColor = Globales._paleta.color2;

        btnRegistrarse.BorderColor = Globales._paleta.color2;
        btnLogin.BorderColor = Globales._paleta.color3;
    }
    private async void btnLogin_Clicked(object sender, EventArgs e)
    {
        await Navigation.PopAsync();
    }

    private async void btnRegistrarse_Clicked(object sender, EventArgs e)
    {
        await btnRegistrarse.ScaleTo(1.075, 250);
        await btnRegistrarse.ScaleTo(1, 500);

        if ((string.IsNullOrEmpty(this.entryContraseña.Text) || string.IsNullOrEmpty(this.entryContraseña2.Text)) && this.entryContraseña.Text != this.entryContraseña2.Text)
        {
            //
            return;
        }

        if (string.IsNullOrEmpty(entryDNI.Text) || string.IsNullOrEmpty(entryNombre.Text) || string.IsNullOrEmpty(entryApellido1.Text) || string.IsNullOrEmpty(entryApellido2.Text) || string.IsNullOrEmpty(pickerFechaNac.Date.ToString()) || string.IsNullOrEmpty(entryUsuario.Text) )
            { return; }

        if (!Utils.Utils.CheckDNI(entryDNI.Text))
        {
            return;
        }

            
        UsuarioRegister u = new UsuarioRegister();
        u.dni = this.entryDNI.Text;
        u.nombre = this.entryNombre.Text;
        u.apellido1 = this.entryApellido1.Text;
        u.apellido2 = this.entryApellido2.Text;
        u.fechaNacimiento = this.pickerFechaNac.Date.ToString("yyyy-MM-dd");
        u.email = this.entryUsuario.Text;
        u.password = Utils.Utils.EncriptarHashStringSHA512(this.entryContraseña.Text);

        dynamic res = await Globales._APICONTROLLER.Register(u);

        if (res != null)
        {
            await LimpiarEntrys();
            await Navigation.PopAsync();
        }
        else
        {

        }

    }

    private async Task LimpiarEntrys()
    {
        entryDNI.Text = string.Empty;
        entryNombre.Text = string.Empty;
        entryApellido1.Text = string.Empty;
        entryApellido2.Text = string.Empty;
        //entryFechaNac.Text = string.Empty;
        pickerFechaNac.Date = DateTime.MinValue;
        entryUsuario.Text = string.Empty;
        entryContraseña.Text = string.Empty;
        entryContraseña2.Text = string.Empty;
    }

    private async void entryContraseña_TextChanged(object sender, TextChangedEventArgs e)
    {
        if (string.IsNullOrEmpty(entryContraseña.Text) || string.IsNullOrEmpty(entryContraseña2.Text))
        {
            return;
        }

        if (entryContraseña.Text.Equals(entryContraseña2.Text))
        {
            await entryContraseña.TextColorTo(Color.FromArgb("#00CC00"), 1, 250);
            await entryContraseña2.TextColorTo(Color.FromArgb("00CC00"), 1, 250);
        }
        else
        {
            await entryContraseña.TextColorTo(Color.FromArgb("#CC0000"), 1, 250);
            await entryContraseña2.TextColorTo(Color.FromArgb("CC0000"), 1, 250);
        }

    }

    private async void entryDNI_TextChanged(object sender, TextChangedEventArgs e)
    {
        try
        {
            if (Utils.Utils.CheckDNI(entryDNI.Text))
            {
                await entryDNI.TextColorTo(Color.FromArgb("00CC00"), 1, 250);
            }
            else
            {
                await entryDNI.TextColorTo(Color.FromArgb("CC0000"), 1, 250);
            }
        }
        catch (Exception w)
        {

        }
        
    }
}