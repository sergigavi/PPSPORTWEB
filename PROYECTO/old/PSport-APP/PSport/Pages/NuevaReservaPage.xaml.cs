
using CommunityToolkit.Maui.Behaviors;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using PSport.Model.DTOs;
using PSport.Utils;
using System.Collections.Generic;
using System.Dynamic;

namespace PSport.Pages;

public partial class NuevaReservaPage : ContentPage
{
    List<ReservaRequest> reservasHechas = new List<ReservaRequest>();

    public NuevaReservaPage()
	{
		InitializeComponent();

        InicializarPaleta();

        CargarPistas();

	}

    /// <summary>
    /// Cargo las pistas que tiene este polideportivo
    /// </summary>
    private async Task CargarPistas()
    {
        try
        {
            List<PistaDTO> pistas = await Globales._APICONTROLLER.getPistasByIdPolideportivo(Globales.selectedPolideportivo.id);
            
            Globales.selectedPolideportivo.pistas = pistas;

            pickerPista.ItemsSource = Globales.selectedPolideportivo.pistas.Select(p => p.nombre).ToList();
        }
        catch (Exception exc)
        {
            exc.ToString();
        }
    }

    private void InicializarPaleta()
    {
        this.Behaviors.Add(new StatusBarBehavior
        {
            StatusBarColor = Globales._paleta.color2
        });

        this.BackgroundColor = Globales._paleta.color0;

        //

        btnReservar.BackgroundColor = Globales._paleta.color3;
        btnReservar.TextColor = Globales._paleta.color0;
        btnReservar.BorderColor = Globales._paleta.color2;
    }

    private async void datePickerDia_DateSelected(object sender, DateChangedEventArgs e)
    {
        if (Globales.selectedPista == null)
        {
            await DisplayAlert("AVISO", "Debes seleccionar pista para realizar la reserva", "Entendido");
            return;
        }

        reservasHechas = await Globales._APICONTROLLER.getReservasDePistaByFecha(Globales.selectedPista.id, this.datePickerDia.Date);

        StackOpciones.IsEnabled = true;
        StackOpciones.IsVisible = true;
    }

    private async void btnMaps_Clicked(object sender, EventArgs e)
    {
        string[] coordenadas = Globales.selectedPolideportivo.coordenadas.Split(":");
        string urlMaps = $"https://www.google.com/maps/search/?api=1&query={coordenadas[0].Replace(",",".")}, {coordenadas[1].Replace(",", ".")}";

        try
        {
            Uri uri = new Uri(urlMaps);
            await Browser.Default.OpenAsync(uri, BrowserLaunchMode.SystemPreferred);
        }
        catch (Exception exc)
        {
            exc.ToString();
        }
    }

    private void ContentPage_Appearing(object sender, EventArgs e)
    {
        lblNombrePoli.Text = Globales.selectedPolideportivo.nombre;
    }

    private async void btnReservar_Clicked(object sender, EventArgs e)
    {

        await btnReservar.ScaleTo(1.075, 250);
        await btnReservar.ScaleTo(1, 500);

        dynamic NewReserva = new ExpandoObject();
        //NewReserva.fechaInicio = datePickerDia.Date.ToString("yyyy-MM-ddT") + pickerHoraInicio.Time.ToString();
        //NewReserva.fechaFin = ((DateTime.Parse(NewReserva.fechaInicio)).AddHours(int.Parse(entryDuracionReserva.Text))).ToString("yyyy-MM-ddTHH:mm:ss");
        //NewReserva.fechaFin = datePickerDia.Date.ToString("yyyy-MM-ddT") + pickerHoraFin.Time.ToString();

        NewReserva.fecha = datePickerDia.Date.ToString("yyyy-MM-dd");
        NewReserva.horaInicio = pickerHoraInicio.Time;
        NewReserva.horaFin = pickerHoraFin.Time;
        NewReserva.pistaID = Globales.selectedPista.id;
        NewReserva.usuarioID = Globales._LoggedUser.id;

        dynamic resReserva = Globales._APICONTROLLER.Reservar(NewReserva);

        await DisplayAlert("ALERTA","Se ha creado la reserva", "Entendido");
        await Navigation.PopToRootAsync();
    }

    private void pickerPista_SelectedIndexChanged(object sender, EventArgs e)
    {
        Picker miPicker = (Picker)sender;
        
        Globales.selectedPista = Globales.selectedPolideportivo.pistas[miPicker.SelectedIndex];
    }
}