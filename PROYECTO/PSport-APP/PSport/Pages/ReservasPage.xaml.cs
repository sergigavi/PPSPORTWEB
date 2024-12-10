using CommunityToolkit.Maui.Behaviors;
using Newtonsoft.Json;
using PSport.Model;
using PSport.Model.DTOs;
using PSport.Utils;
using System.Collections;
using System.Collections.Generic;
using System.Linq;

namespace PSport.Pages;

public partial class ReservasPage : ContentPage
{
    private List<PolideportivoDTO> polideportivos = new List<PolideportivoDTO>();
    private List<PolideportivoDTO> polideportivosFiltered = new List<PolideportivoDTO>();

    public ReservasPage()
	{
		InitializeComponent();

        CargarPaleta();
        CargarReservas();
	}

    private void CargarPaleta()
    {
        this.Behaviors.Add(new StatusBarBehavior
        {
            StatusBarColor = Globales._paleta.color2
        });

        this.BackgroundColor = Globales._paleta.color0;

        lblNumResultados.TextColor = Globales._paleta.color3;
    }

    private async void CargarReservas()
    {

        try
        {
            dynamic Polideportivos = await Globales._APICONTROLLER.GetPolideportivos();

            if (Polideportivos != null)
            {
                polideportivos = ConvertirPolideportivos(Polideportivos);
                polideportivosFiltered = new List<PolideportivoDTO>(polideportivos);
                lvPolideportivos.ItemsSource = polideportivosFiltered;
                lblNumResultados.Text = "Resultados: " + polideportivosFiltered.Count;
            }
            else
            {
                await DisplayAlert("ALERTA", "No hay polideportivos disponibles", "Entendido");
            }

            aiCarga.IsVisible = false;
            aiCarga.IsEnabled = false;
            aiCarga.IsRunning = false;

            //List<PolideportivoDTO> poliList = new List<PolideportivoDTO>();
            //PolideportivoDTO pol = new PolideportivoDTO();

            //pol.id = Guid.Empty;
            //pol.cp = "poli.cp";
            //pol.direccion = "poli.direccion";
            //pol.localidad = "poli.localidad";
            //pol.nombre = "poli.nombre";

            //poliList.Add(pol);

            //lvPolideportivos.ItemsSource = poliList;

        }
        catch (Exception e)
        {
            await DisplayAlert("ALERTA", "Se ha producido un error obteniendo los polideportivos", "Entendido");
            e.ToString();
        }

        aiCarga.IsVisible = false;
        aiCarga.IsEnabled = false;
        aiCarga.IsRunning = false;

    }

    private List<PolideportivoDTO> ConvertirPolideportivos(dynamic polideportivos)
    {
        List<PolideportivoDTO> poliList = new List<PolideportivoDTO>();

        foreach (dynamic poli in polideportivos)
        {
            //var jsonpoli = JsonConvert.SerializeObject(poli);

            try
            {
                PolideportivoDTO pol = new PolideportivoDTO();

                string idstring = poli.id;
                pol.id = Guid.Parse(idstring);
                pol.cp = poli.cp;
                pol.direccion = poli.direccion;
                pol.localidad = poli.localidad;
                pol.nombre = poli.nombre;

                try
                {
                    pol.coordenadas = poli.latitud + ":"+ poli.longitud;

                    pol.horario = poli.horario;
                    pol.horaApertura = poli.horaApertura;
                    pol.horaCierre = poli.horaCierre;

                    //var pistas = poli.pistas;
                }
                catch (Exception exc)
                {
                    exc.ToString();
                }

                //pol.foto = ImageSource.FromUri(new Uri("https://cdn.discordapp.com/attachments/1286727623040303231/1311743953808130091/image.png?ex=6749f841&is=6748a6c1&hm=bef9e7e523891d3f4049b24872ea314ad6082989ba84d05f700673291bfe2d3f&"));

                //
                try
                {
                    string fotostring = poli.foto;
                    MemoryStream stream = new MemoryStream(Convert.FromBase64String(fotostring));
                    ImageSource image = ImageSource.FromStream(() => stream);
                    pol.foto = image;
                }
                catch (Exception exc)
                {
                    exc.ToString();
                }

                //pol.jugadoresAsociados = poli.jugadoresAsociados;
                //pol.pistas = poli.pistas;

                poliList.Add(pol);
            }
            catch (Exception e)
            {
                e.ToString();
            }
        }

        return poliList;
    }


    private async void lvPolideportivos_ItemSelected(object sender, SelectedItemChangedEventArgs e)
    {
        Globales.selectedPolideportivo = ((PolideportivoDTO)e.SelectedItem);
        await Navigation.PushAsync(new NuevaReservaPage());
    }

    private void ContentPage_Appearing(object sender, EventArgs e)
    {
        Globales.selectedPolideportivo = null;
        Globales.selectedPista = null;
    }

    private async void entryBusqueda_TextChanged(object sender, TextChangedEventArgs e)
    {
        if (e.NewTextValue.Length < 3)
        {
            if (e.NewTextValue.Length == 0)
            {
                polideportivosFiltered = new List<PolideportivoDTO>(polideportivos);
                lvPolideportivos.ItemsSource = polideportivosFiltered;
            }

            return;
        }

        polideportivosFiltered = polideportivos.Where(p => p.nombre.ToLower().Contains(e.NewTextValue.ToLower())).ToList();

        if (polideportivosFiltered.Count > 0)
        {
            
        }
        else
        {
            await DisplayAlert("ALERTA","No se han encontrado polideportivos", "Entendido");
            polideportivosFiltered = new List<PolideportivoDTO>(polideportivos);
        }

        lvPolideportivos.ItemsSource = polideportivosFiltered;

        lblNumResultados.Text = "Resultados: " + polideportivosFiltered.Count;

    }

}