using CommunityToolkit.Maui.Behaviors;
using PSport.Utils;

namespace PSport.Pages;

public partial class MostrarEquipoPage : ContentPage
{
	public MostrarEquipoPage()
	{
		InitializeComponent();

	}

    private void ContentPage_Appearing(object sender, EventArgs e)
    {
        cargarPaleta();

        try
        {
            lblNombre.Text = Globales.selectedEquipo.nombre;
            lblDeporte.Text = Globales.selectedEquipo.deporte;
        }
        catch (Exception exc)
        {

        }
    }

    private void cargarPaleta()
    {
        this.Behaviors.Add(new StatusBarBehavior
        {
            StatusBarColor = Globales._paleta.color2
        });

        this.BackgroundColor = Globales._paleta.color0;

        btnUnirme.BackgroundColor = Globales._paleta.color3;
        btnUnirme.TextColor = Globales._paleta.color0;
        btnUnirme.BorderColor = Globales._paleta.color2;
    }

    private async void btnUnirme_Clicked(object sender, EventArgs e)
    {
        try
        {
            await btnUnirme.ScaleTo(1.075, 250);
            await btnUnirme.ScaleTo(1, 500);

            string iduser = Globales._LoggedUser.id;
            string idJugador = "";

            if (string.IsNullOrEmpty(idJugador))
            {
                Globales._infoSelectedJugador = await Globales._APICONTROLLER.getAllInfoJugadorByIdUsuario(iduser);
                idJugador = Globales._infoSelectedJugador.id;
            }
            
            dynamic res = await Globales._APICONTROLLER.UnirseAEquipo(idJugador, Globales.selectedEquipo.id.ToString());

            string resid = res.id;

            if (resid != null)
            {
                await DisplayAlert("AVISO", "¡Te has unido al equipo!", "Entendido");
                await Navigation.PopAsync();
            }
        }
        catch (Exception exc)
        {

        }

    }
}