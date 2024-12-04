
using CommunityToolkit.Maui.Behaviors;
using PSport.Model.DTOs;
using PSport.Utils;

namespace PSport.Pages;

public partial class EquiposPage : ContentPage
{

    private string rutaImg = "https://cdn-icons-png.flaticon.com/512/3790/3790332.png";
	public EquiposPage()
	{
		InitializeComponent();

		cargarPaleta();
	}

    private void cargarPaleta()
    {
        this.Behaviors.Add(new StatusBarBehavior
        {
            StatusBarColor = Globales._paleta.color2
        });

        this.BackgroundColor = Globales._paleta.color0;
    }

    private void ContentPage_Appearing(object sender, EventArgs e)
    {

        try
        {
            List<EquipoDTO> misEquiposList = new List<EquipoDTO>();

            EquipoDTO equipo1 = new EquipoDTO();
            equipo1.nombre = "Equipo A";
            equipo1.deporte = "FUTBOL 11";
            misEquiposList.Add(equipo1);

            EquipoDTO equipo2 = new EquipoDTO();
            equipo2.nombre = "Equipo B";
            equipo2.deporte = "TENIS";
            misEquiposList.Add(equipo2);

            EquipoDTO equipo3 = new EquipoDTO();
            equipo3.nombre = "Equipo C";
            equipo3.deporte = "Baloncesto";
            misEquiposList.Add(equipo3);

            lvInfoMiEquipo.ItemsSource = misEquiposList;

            //
            List<EquipoDTO> misEquiposList2 = new List<EquipoDTO>();

            EquipoDTO quipo1 = new EquipoDTO();
            quipo1.nombre = "Equipo D";
            quipo1.deporte = "FUTBOL 11";
            misEquiposList2.Add(quipo1);

            EquipoDTO quipo2 = new EquipoDTO();
            quipo2.nombre = "Equipo E";
            quipo2.deporte = "TENIS";
            misEquiposList2.Add(quipo2);

            EquipoDTO quipo3 = new EquipoDTO();
            quipo3.nombre = "Equipo F";
            quipo3.deporte = "Baloncesto";
            misEquiposList2.Add(quipo3);
            lvAllEquipos.ItemsSource = misEquiposList2;
        }
        catch (Exception exc)
        {
            exc.ToString();
        }

    }

    private void lvInfoMiEquipo_ItemSelected(object sender, SelectedItemChangedEventArgs e)
    {

    }
}