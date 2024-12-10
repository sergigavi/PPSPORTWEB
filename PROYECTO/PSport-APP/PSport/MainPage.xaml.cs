using CommunityToolkit.Maui.Behaviors;
using PSport.Model.DTOs;
using PSport.Pages;
using PSport.Pages.Admin;
using PSport.Utils;

namespace PSport
{
    public partial class MainPage : ContentPage
    {
        int count = 0;

        public MainPage()
        {
            InitializeComponent();

            InitPaletas();

            Navigation.PushAsync(new LoginPage());

            Utils.Utils.ComprobarConfiguracion();
        }

        private void InitPaletas()
        {
            //Paleta 1
            Paleta p1 = new Paleta();
            p1.nombre = "1";
            p1.color0 = Color.FromArgb("#FFF4DA"); //beige;
            p1.color1 = Color.FromArgb("#F4F4F4"); //blanquito
            p1.color2 = Color.FromArgb("#047127"); //verde
            p1.color3 = Color.FromArgb("#9F0B1A"); //rojo
            p1.color4 = Color.FromArgb("#9F0B1A"); //rojo

            Globales.MisPaletas.Add(p1);

            //Paleta 2
            Paleta p2 = new Paleta();
            p2.nombre = "2";
            p2.color0 = Color.FromArgb("#4335A7");
            p2.color1 = Color.FromArgb("#80C4E9");
            p2.color2 = Color.FromArgb("#FFF6E9");
            p2.color3 = Color.FromArgb("#FF7F3E");
            p2.color4 = Color.FromArgb("#FF7F3E");

            Globales.MisPaletas.Add(p2);
            
            //Paleta 3
            Paleta p3 = new Paleta();
            p3.nombre = "3";
            p3.color0 = Color.FromArgb("#3D5300");
            p3.color1 = Color.FromArgb("#ABBA7C");
            p3.color2 = Color.FromArgb("#FFE31A");
            p3.color3 = Color.FromArgb("#F09319");
            p3.color4 = Color.FromArgb("#F09319");

            Globales.MisPaletas.Add(p3);
            
            //Paleta 4
            Paleta p4 = new Paleta();
            p4.nombre = "4";
            p4.color0 = Color.FromArgb("#0D92F4");
            p4.color1 = Color.FromArgb("#77CDFF");
            p4.color2 = Color.FromArgb("#F95454");
            p4.color3 = Color.FromArgb("#C62E2E");
            p4.color4 = Color.FromArgb("#C62E2E");

            Globales.MisPaletas.Add(p4);
            
            //Paleta 5
            Paleta p5 = new Paleta();
            p5.nombre = "5";
            p5.color0 = Color.FromArgb("#091057");
            p5.color1 = Color.FromArgb("#024CAA");
            p5.color2 = Color.FromArgb("#F95454");
            p5.color3 = Color.FromArgb("#EC8305");
            p5.color4 = Color.FromArgb("#EC8305");

            Globales.MisPaletas.Add(p5);
            
            //Paleta 6
            Paleta p6 = new Paleta();
            p6.nombre = "6";
            p6.color0 = Color.FromArgb("#E4E0E1");
            p6.color1 = Color.FromArgb("#D6C0B3");
            p6.color2 = Color.FromArgb("#AB886D");
            p6.color3 = Color.FromArgb("#493628");
            p6.color4 = Color.FromArgb("#493628");

            Globales.MisPaletas.Add(p6);

            //
            Globales._paleta = p1;
            
        }

        private async void ContentPage_Appearing(object sender, EventArgs e)
        {

            try
            {
                string rolUser = Globales._LoggedUser.rol;

                if ( rolUser == "ADMIN")
                {
                    btnAdm.IsEnabled = true;
                    btnAdm.IsVisible = true;
                }
            }
            catch (Exception exc)
            {

            }

            this.Behaviors.Add(new StatusBarBehavior
            {
                StatusBarColor = Globales._paleta.color2
            });

            btncerrarSesion.BorderColor = Globales._paleta.color3;

            btnPerfil.BackgroundColor = Globales._paleta.color2;
            btnPerfil.BorderColor = Globales._paleta.color3;

            this.BackgroundColor = Globales._paleta.color0;

            btnReservas.BackgroundColor = Globales._paleta.color3;
            btnReservas.TextColor = Globales._paleta.color0;
            btnReservas.BorderColor = Globales._paleta.color2;
            
            btnCalendario.BackgroundColor = Globales._paleta.color3;
            btnCalendario.TextColor = Globales._paleta.color0;
            btnCalendario.BorderColor = Globales._paleta.color2;

            btnPerfil.BackgroundColor = Globales._paleta.color2;
            btnPerfil.TextColor = Globales._paleta.color0;

            btnEquipos.BackgroundColor = Globales._paleta.color3;
            btnEquipos.TextColor = Globales._paleta.color0;
            btnEquipos.BorderColor = Globales._paleta.color2;

            btnTorneos.BackgroundColor = Globales._paleta.color3;
            btnTorneos.TextColor = Globales._paleta.color0;
            btnTorneos.BorderColor = Globales._paleta.color2;

            btnAdm.BackgroundColor = Globales._paleta.color3;
            btnAdm.TextColor = Globales._paleta.color0;
            btnAdm.BorderColor = Globales._paleta.color2;

            //
            lblNombre.Text = $"¡Hola {Globales._LoggedUser.nombre}!";

        }

        private async void btnCalendario_Clicked(object sender, EventArgs e)
        {
            await btnCalendario.ScaleTo(1.075, 250);
            await btnCalendario.ScaleTo(1, 500);

            await Navigation.PushAsync(new CalendarioPage());
        }

        private async void btnPerfil_Clicked(object sender, EventArgs e)
        {
            await btnPerfil.ScaleTo(1.075, 250);
            await btnPerfil.ScaleTo(1, 500);

            await Navigation.PushAsync(new PerfilPage());
        }

        private async void btnEquipos_Clicked(object sender, EventArgs e)
        {
            await btnEquipos.ScaleTo(1.075, 250);
            await btnEquipos.ScaleTo(1, 500);

            await Navigation.PushAsync(new EquiposPage());

        }

        private async void btnTorneos_Clicked(object sender, EventArgs e)
        {
            await btnTorneos.ScaleTo(1.075, 250);
            await btnTorneos.ScaleTo(1, 500);

            await Navigation.PushAsync(new TorneosPage());
        }

        private async void btnReservas_Clicked(object sender, EventArgs e)
        {
            await btnReservas.ScaleTo(1.075, 250);
            await btnReservas.ScaleTo(1, 500);

            await Navigation.PushAsync(new ReservasPage());

        }

        private async void btncerrarSesion_Clicked(object sender, EventArgs e)
        {
            await btncerrarSesion.ScaleTo(1.075, 250);
            await btncerrarSesion.ScaleTo(1, 500);

            await Navigation.PopToRootAsync();
            await DisplayAlert("ALERTA", "Se ha cerrado la sesión", "Entendido");
            Globales._AppConfig = new Dictionary<string, string>()
            {
                { "Recuerdame", "1" },
                { "USR", "" },
                { "PASS", "" },
            };

            await Utils.Utils.SobreescribirConfiguracion();

            await Navigation.PushAsync(new LoginPage());
        }

        private async void btnAdm_Clicked(object sender, EventArgs e)
        {
            await btnAdm.ScaleTo(1.075, 250);
            await btnAdm.ScaleTo(1, 500);

            await Navigation.PushAsync(new AdmMenu());
        }


    }

}
