using CommunityToolkit.Maui.Behaviors;
using PSport.Model.DTOs;
using PSport.Utils;

namespace PSport.Pages.Admin;

public partial class AdmGestionEquipos : ContentPage
{
	public AdmGestionEquipos()
	{
		InitializeComponent();
	}

    private void ContentPage_Appearing(object sender, EventArgs e)
    {
        this.Behaviors.Add(new StatusBarBehavior
        {
            StatusBarColor = Globales._paleta.color2
        });

        this.BackgroundColor = Globales._paleta.color0;

        this.BtnAnnadirEquipo.BackgroundColor = Globales._paleta.color3;
        this.BtnAnnadirEquipo.BorderColor = Globales._paleta.color2;

        try
        {
            List<string> deportes = new List<string>();
            deportes.Add("TENIS");
            deportes.Add("PADEL");
            deportes.Add("FUTBOL_7");
            deportes.Add("FUTBOL_SALA");
            deportes.Add("FUTBOL_11");
            deportes.Add("BALONCESTO");

            pickerDeporte.ItemsSource = deportes;
        }
        catch (Exception exxc)
        {
            exxc.ToString();
        }
    }

    private async void BtnAnnadirEquipo_Clicked(object sender, EventArgs e)
    {
        try
        {
            EquipoRequest equipo = new EquipoRequest();

            equipo.nombre = entryNombre.Text;
            equipo.deporte = pickerDeporte.SelectedIndex;

            dynamic res = await Globales._APICONTROLLER.crearEquipo(equipo);

            string resID = res.id;
            if (resID != null)
            {
                await DisplayAlert("AVISO", "Equipo creado correctamente", "OK");
                await Navigation.PopAsync();
            }
            else
            {
                await DisplayAlert("AVISO", "Se ha producido un error creando el equipo", "Entendido");
            }

           
        }
        catch (Exception ex)
        {
            ex.ToString();

            await DisplayAlert("AVISO", "Se ha producido un error añadiendo el torneo", "OK");
        }
    }

    
}