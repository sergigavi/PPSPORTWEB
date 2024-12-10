using CommunityToolkit.Maui.Behaviors;
using PSport.Model.DTOs;
using PSport.Utils;

namespace PSport.Pages;

public partial class CalendarioPage : ContentPage
{
    List<ReservaDTO> misReservas = new List<ReservaDTO>();

    List<ReservaDTO> misReservasPendientes = new List<ReservaDTO>();
    List<ReservaDTO> historicoReservas = new List<ReservaDTO>();

    public CalendarioPage()
	{
		InitializeComponent();

	}

    private async void ContentPage_Appearing(object sender, EventArgs e)
    {
        try
        {
            await cargarPaleta();

            string idstring = Globales._LoggedUser.id;
            misReservas = await Globales._APICONTROLLER.GetReservasByIdUsuario(idstring);

            misReservasPendientes = misReservas.Where(r => DateTime.Parse(r.fecha) > DateTime.Now).ToList();
            historicoReservas = misReservas.Where(r => DateTime.Parse(r.fecha) < DateTime.Now).ToList();

            lvMisReservas.ItemsSource = misReservasPendientes;
            lvHistorico.ItemsSource = historicoReservas;

            lblDia.Text = misReservas[0].fecha;
            lblPista.Text = misReservas[0].pista.nombre;
            lblPoli.Text = misReservas[0].pista.nombrePolideportivo;
        }
        catch (Exception exc)
        {
            exc.ToString();
        }
        
    }

    private async Task cargarPaleta()
    {
        this.Behaviors.Add(new StatusBarBehavior
        {
            StatusBarColor = Globales._paleta.color2
        });

        this.BackgroundColor = Globales._paleta.color0;

        lblProximaRes.TextColor = Globales._paleta.color2;

        lblDiaTitulo.TextColor = Globales._paleta.color3;
        lblPistaTitulo.TextColor = Globales._paleta.color3;
        lblPoliTitulo.TextColor = Globales._paleta.color3;

    }

    private void lvMisReservas_ItemSelected(object sender, SelectedItemChangedEventArgs e)
    {

    }
}