using CommunityToolkit.Maui.Behaviors;
using PSport.Model.DTOs;
using PSport.Utils;
using System.Linq;

namespace PSport.Pages;

public partial class TorneosPage : ContentPage
{
    private List<TorneoDTO> AllTorneos = new List<TorneoDTO>();
    private List<TorneoDTO> AllMisTorneos = new List<TorneoDTO>();

    public TorneosPage()
	{
		InitializeComponent();
	}

    private void lvMisTorneos_ItemSelected(object sender, SelectedItemChangedEventArgs e)
    {

    }

    private void ContentPage_Appearing(object sender, EventArgs e)
    {
        cargarPaleta();

        cargarTorneos();
    }

    private async void cargarTorneos()
    {
        try
        {
            AllTorneos = await Globales._APICONTROLLER.getAllTorneos();

            if (AllTorneos.Count < 1)
            {
                await DisplayAlert("AVISO", "No se han encontrado torneos disponibles", "Entendido");
            }

            AllMisTorneos = new List<TorneoDTO>(AllTorneos);

            lvMisTorneos.ItemsSource = AllMisTorneos;
            lvAllTorneos.ItemsSource = AllTorneos;

        }
        catch (Exception exc)
        {
            exc.ToString();
        }
    }

    private async void cargarPaleta()
    {
        this.Behaviors.Add(new StatusBarBehavior
        {
            StatusBarColor = Globales._paleta.color2
        });

        this.BackgroundColor = Globales._paleta.color0;

        //btnUnirme.BackgroundColor = Globales._paleta.color3;
        //btnUnirme.TextColor = Globales._paleta.color0;
        //btnUnirme.BorderColor = Globales._paleta.color2;
    }
}