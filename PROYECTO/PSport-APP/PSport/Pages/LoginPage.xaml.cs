using PSport.Model.DTOs;
using PSport.Utils;
using CommunityToolkit.Maui.Behaviors;
using Plugin.Maui.Biometric;
using Newtonsoft.Json;

namespace PSport.Pages;

public partial class LoginPage : ContentPage
{
	public LoginPage()
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
        this.btnEntrar.BackgroundColor = Globales._paleta.color3;
        this.btnEntrar.BorderColor = Globales._paleta.color2;

        this.btnRegistro.TextColor = Globales._paleta.color2;
    }

    protected override bool OnBackButtonPressed()
    {
        return true;    //true me lo bloquea
    }
    private async void btnRegistro_Clicked(object sender, EventArgs e)
    {
        await Navigation.PushAsync(new RegisterPage());
    }

    private async void btnEntrar_Clicked(object sender, EventArgs e)
    {
        
        await btnEntrar.ScaleTo(1.075, 250);
        await btnEntrar.ScaleTo(1, 500);

        if (string.IsNullOrEmpty(this.entryUsuario.Text) || string.IsNullOrEmpty(this.entryContraseña.Text))
        {
            return;
        }

        #region truco almendruco

        //Easter egg
        if (entryUsuario.Text == "truco" && entryContraseña.Text == "almendruco")
        {
            if (Globales.URL_BASE_API.Contains("ngrok"))
            {
                Globales.URL_BASE_API = "http://192.168.1.143:9432/ppsport/api";
            }
            else
            {
                Globales.URL_BASE_API = "https://glad-cuddly-shepherd.ngrok-free.app/ppsport/api";
            }

            await DisplayAlert("AVISO", $"Se ha cambiado la ruta de acceso a la api por: {Globales.URL_BASE_API}", "OK");

            entryUsuario.Text = "";
            entryContraseña.Text = "";
            return;
            
        }

        #endregion

        btnHuella.IsVisible = false;
        ai.IsVisible = true;
        ai.IsEnabled = true;
        ai.IsRunning = true;

        UsuarioLogin u = new UsuarioLogin();
        u.email = this.entryUsuario.Text;
        u.password = Utils.Utils.EncriptarHashStringSHA512(this.entryContraseña.Text);

        await IniciarSesion(u);

    }

    private async Task IniciarSesion(UsuarioLogin u)
    {
        dynamic res = await Globales._APICONTROLLER.Login(u);

        if (res != null)
        {
            if (res.accessToken != null)
            {
                Globales._CurrentAccessToken = res.accessToken;
                Globales._CurrentRefreshToken = res.refreshToken;

                Globales._FechaCaducidadAccessToken = DateTime.Now.AddMinutes(59);
                Globales._FechaCaducidadRefreshToken = DateTime.Now.AddHours(23);

                Globales._LoggedUser = await Globales._APICONTROLLER.GetUsuarioByEmail(u.email);

                Globales._AppConfig["USR"] = this.entryUsuario.Text;
                Globales._AppConfig["PASS"] = Utils.Utils.EncryptString(Globales.__KeyMyKey, this.entryContraseña.Text);

                await Utils.Utils.SobreescribirConfiguracion();

                this.entryUsuario.Text = string.Empty;
                this.entryContraseña.Text = string.Empty;

                await Navigation.PopAsync();
            }
            else
            {
                await DisplayAlert("AVISO", "Se ha producido un error al iniciar sesión", "Entendido");
            }



        }
        else
        {

        }

        ai.IsVisible = false;
        ai.IsEnabled = false;
        ai.IsRunning = false;

        btnHuella.IsVisible = true;

    }

    private async void btnHuella_Clicked(object sender, EventArgs e)
    {
        await AutenticarPorBiometria();

    }

    private async Task AutenticarPorBiometria()
    {
        try
        {
#if !DEBUG
            var result = await BiometricAuthenticationService.Default.AuthenticateAsync(
            new AuthenticationRequest()
            {
                Title = "Autenticación...",
                NegativeText = "Cancelar"
            }, CancellationToken.None);
#endif
#if DEBUG
            if (true)
#endif

#if !DEBUG
            if (result.Status == BiometricResponseStatus.Success)
#endif

            {

                btnHuella.IsVisible = false;

                ai.IsEnabled = true;
                ai.IsVisible = true;
                ai.IsRunning = true;

                string pass = Utils.Utils.DecryptString(Globales.__KeyMyKey, Globales._AppConfig["PASS"]);

                //
                UsuarioLogin u = new UsuarioLogin();
                //u.email = Utils.Utils.DecryptString(Globales.__KeyMyKey, Globales._AppConfig["USR"]);
                u.email = Globales._AppConfig["USR"];
                u.password = Utils.Utils.EncriptarHashStringSHA512(pass);

                await IniciarSesion(u);

                ai.IsVisible = false;
                ai.IsEnabled = false;
                ai.IsRunning = false;

                btnHuella.IsVisible = true;

            }

        }
        catch (Exception)
        {
            ai.IsVisible = false;
            ai.IsEnabled = false;
            ai.IsRunning = false;

            btnHuella.IsVisible = true;
        } 

    }

}