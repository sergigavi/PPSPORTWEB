using CommunityToolkit.Maui.Behaviors;
using PSport.Model.DTOs;
using PSport.Utils;
using System.Collections.Generic;

namespace PSport.Pages.Admin;

public partial class AdmGestionTorneos : ContentPage
{
    List<PartidoDTO> AllPartidos = new List<PartidoDTO>();

    public AdmGestionTorneos()
	{
		InitializeComponent();
	}

    private async void ContentPage_Appearing(object sender, EventArgs e)
    {

        cargarPaleta();

		try
		{
			List<string> tiposTorneo = new List<string>();
			tiposTorneo.Add("LIGA");
			tiposTorneo.Add("ELIMINATORIO");

			pickerTipo.ItemsSource = tiposTorneo;

            List<string> deportes = new List<string>();
            deportes.Add("TENIS");
            deportes.Add("PADEL");
            deportes.Add("FUTBOL_7");
            deportes.Add("FUTBOL_SALA");
            deportes.Add("FUTBOL_11");
            deportes.Add("BALONCESTO");

            pickerDeporte.ItemsSource = deportes;

        }
		catch (Exception exc)
		{
			exc.ToString();
		}

        try
        {
            AllPartidos = await Globales._APICONTROLLER.getAllPartidos();
            pickerPartidos.ItemsSource = AllPartidos.Select(p => p.nombreTorneo + " / " + p.ronda).ToList();
        }
        catch (Exception exc)
        {
            exc.ToString();
        }
    }

    private void cargarPaleta()
    {
        this.Behaviors.Add(new StatusBarBehavior
        {
            StatusBarColor = Globales._paleta.color2
        });

        this.BackgroundColor = Globales._paleta.color0;

        this.BtnAnnadirTorneo.BackgroundColor = Globales._paleta.color3;
        this.BtnAnnadirTorneo.BorderColor = Globales._paleta.color2;

        this.btnGuardarResultado.BackgroundColor = Globales._paleta.color3;
        this.btnGuardarResultado.BorderColor = Globales._paleta.color2;
    }

    private async void BtnAnnadirTorneo_Clicked(object sender, EventArgs e)
    {
		try
		{
            TorneoDTO2 torneo = new TorneoDTO2();
            torneo.nombre = entryNombre.Text;
            torneo.deporte = pickerDeporte.SelectedIndex;
            torneo.tipoTorneo = pickerTipo.SelectedIndex;
            torneo.fechaInicio = pickerFechaInicio.Date.ToString("yyyy-MM-ddTHH:mm:ss");
            torneo.fechaFin = pickerFechaFin.Date.ToString("yyyy-MM-ddTHH:mm:ss");

            var res = await Globales._APICONTROLLER.crearTorneo(torneo);

            string idnuevo = res.id;
            if (idnuevo != null)
            {
                await DisplayAlert("AVISO", "Torneo creado correctamente", "OK");
                await Navigation.PopAsync();
            }
            else{
                await DisplayAlert("AVISO", "Se ha producido un error creando el torneo", "Entendido");
            }
            
        }
		catch (Exception ex)
		{
			ex.ToString();

			await DisplayAlert("AVISO", "Se ha producido un error añadiendo el torneo", "OK");
		}
		
    }

    private void pickerPartidos_SelectedIndexChanged(object sender, EventArgs e)
    {
        try
        {
            Globales.selectedPartidoADM = AllPartidos[pickerPartidos.SelectedIndex];

            editarPartido.IsVisible = true;
            editarPartido.IsEnabled = true;
        }
        catch (Exception exc)
        {

        }


    }

    private async void btnGuardarResultado_Clicked(object sender, EventArgs e)
    {
        try
        {
            dynamic res = await Globales._APICONTROLLER.anotarResultadoPartido(Globales.selectedPartidoADM.id, entryResultadoPartido.Text);
        }
        catch (Exception exc)
        {

        }
    }
}