
using CommunityToolkit.Maui.Behaviors;
using PSport.Model.DTOs;
using PSport.Utils;

namespace PSport.Pages;

public partial class EquiposPage : ContentPage
{

    List<EquipoDTO> allEquipos = new List<EquipoDTO>();
    List<EquipoDTO> misEquipos = new List<EquipoDTO>();

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

    private async void ContentPage_Appearing(object sender, EventArgs e)
    {

        try
        {
            //List<EquipoDTO> misEquiposList = new List<EquipoDTO>();

            //EquipoDTO equipo1 = new EquipoDTO();
            //equipo1.nombre = "Equipo A";
            //equipo1.deporte = "FUTBOL 11";
            //equipo1.img = rutaImg;
            //misEquiposList.Add(equipo1);

            //EquipoDTO equipo2 = new EquipoDTO();
            //equipo2.nombre = "Equipo B";
            //equipo2.deporte = "TENIS";
            //equipo2.img = rutaImg;
            //misEquiposList.Add(equipo2);

            //EquipoDTO equipo3 = new EquipoDTO();
            //equipo3.nombre = "Equipo C";
            //equipo3.img = rutaImg;
            //equipo3.deporte = "Baloncesto";
            //misEquiposList.Add(equipo3);

            //lvInfoMiEquipo.ItemsSource = misEquiposList;

            ////
            //List<EquipoDTO> misEquiposList2 = new List<EquipoDTO>();

            //EquipoDTO quipo1 = new EquipoDTO();
            //quipo1.nombre = "Equipo D";
            //quipo1.deporte = "FUTBOL 11";
            //quipo1.img = rutaImg;
            //misEquiposList2.Add(quipo1);

            //EquipoDTO quipo2 = new EquipoDTO();
            //quipo2.nombre = "Equipo E";
            //quipo2.deporte = "TENIS";
            //quipo2.img = rutaImg;
            //misEquiposList2.Add(quipo2);

            //EquipoDTO quipo3 = new EquipoDTO();
            //quipo3.nombre = "Equipo F";
            //quipo3.deporte = "Baloncesto";
            //quipo3.img = rutaImg;
            //misEquiposList2.Add(quipo3);
            
            //lvAllEquipos.ItemsSource = misEquiposList2;

            //Cargo los equipos
            try
            {
                string idUser = Globales._LoggedUser.id;
                //misEquipos = await Globales._APICONTROLLER.getEquiposByIdUsuario(Guid.Parse(idUser));
                //misEquipos.ForEach(e => e.img = rutaImg);

                //
                Globales._infoSelectedJugador = await Globales._APICONTROLLER.getInfoJugadorByIdUser(idUser);

            }
            catch (Exception eex)
            {
                eex.ToString();
            }
            
            allEquipos = await Globales._APICONTROLLER.getAllEquipos();
            allEquipos.ForEach(e => e.img = rutaImg);

            lvAllEquipos.ItemsSource = allEquipos;

            lvInfoMiEquipo.ItemsSource = Globales._infoSelectedJugador.equipos;

        }
        catch (Exception exc)
        {
            exc.ToString();
        }

    }

    private void lvInfoMiEquipo_ItemSelected(object sender, SelectedItemChangedEventArgs e)
    {

    }

    private async void lvAllEquipos_ItemSelected(object sender, SelectedItemChangedEventArgs e)
    {
        Globales.selectedEquipo = (EquipoDTO)e.SelectedItem;

        await Navigation.PushAsync(new MostrarEquipoPage());
    }
}