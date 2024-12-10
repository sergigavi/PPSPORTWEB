using CommunityToolkit.Maui.Behaviors;
using PSport.Model.DTOs;
using PSport.Utils;
using System.Dynamic;

namespace PSport.Pages.Admin;

public partial class AdmGestionPistas : ContentPage
{
    dynamic AllPolideportivos = new ExpandoObject();
    List<PolideportivoDTO> AllPolideportivosList = new List<PolideportivoDTO>();
    List<PolideportivoDTO> AllPolideportivosAEliminar = new List<PolideportivoDTO>();

    PolideportivoDTO selectedPoli = new PolideportivoDTO();
    PolideportivoDTO selectedPoliAEliminar = new PolideportivoDTO();

	public AdmGestionPistas()
	{
		InitializeComponent();
	}

    private async void ContentPage_Appearing(object sender, EventArgs e)
    {
        this.Behaviors.Add(new StatusBarBehavior
        {
            StatusBarColor = Globales._paleta.color2
        });

        this.BackgroundColor = Globales._paleta.color0;

        this.btnAnnadirPolideportivo.BackgroundColor = Globales._paleta.color3;
        this.btnAnnadirPolideportivo.BorderColor = Globales._paleta.color2;

        this.btnAnnadirPista.BackgroundColor = Globales._paleta.color3;
        this.btnAnnadirPista.BorderColor = Globales._paleta.color2;

        this.btnBorrarPolideportivo.BackgroundColor = Globales._paleta.color3;
        this.btnBorrarPolideportivo.BorderColor = Globales._paleta.color2;

        try
        {
            AllPolideportivos = await Globales._APICONTROLLER.GetPolideportivos();

            foreach (dynamic poli in AllPolideportivos)
            {
                PolideportivoDTO pol = new PolideportivoDTO();

                pol.id = poli.id;
                pol.nombre = poli.nombre;
                pol.direccion = poli.direccion;
                pol.localidad = poli.localidad;
                pol.cp = poli.cp;
                pol.horaApertura = poli.horaApertura;
                pol.horaCierre = poli.horaCierre;
                pol.horario = poli.horario;

                AllPolideportivosList.Add(pol);
            }

            pickerPolideportivos.ItemsSource = AllPolideportivosList.Select(p => p.nombre).ToList();

            //
            AllPolideportivosAEliminar = new List<PolideportivoDTO>(AllPolideportivosList);

            pickerPolideportivosEliminables.ItemsSource = AllPolideportivosAEliminar.Select(p => p.nombre).ToList();
        }
        catch (Exception exxc)
        {
            exxc.ToString();
        }
    }

    private async void btnAnnadirPolideportivo_Clicked(object sender, EventArgs e)
    {
        try
        {
            PolideportivoDTO newPoli = new PolideportivoDTO();

            newPoli.cp = entrycp.Text;
            newPoli.direccion = entryDireccion.Text;
            newPoli.localidad = entryLocalidad.Text;
            newPoli.nombre = entryNombre.Text;

            newPoli.horaApertura = pickerHoraApertura.Time.ToString();
            newPoli.horaCierre = pickerHoraCierre.Time.ToString();

            dynamic res = await Globales._APICONTROLLER.crearPolideportivo(newPoli);

            string resID = res.id;
            if (resID != null)
            {
                await DisplayAlert("AVISO", "Polideportivo creado correctamente", "OK");
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

    private void pickerPolideportivos_SelectedIndexChanged(object sender, EventArgs e)
    {
        selectedPoli = AllPolideportivosList[pickerPolideportivos.SelectedIndex];

        List<string> deportes = new List<string>();
        deportes.Add("TENIS");
        deportes.Add("PADEL");
        deportes.Add("FUTBOL_7");
        deportes.Add("FUTBOL_SALA");
        deportes.Add("FUTBOL_11");
        deportes.Add("BALONCESTO");

        pickerTipoPista.ItemsSource = deportes;
    }

    private async void btnAnnadirPista_Clicked(object sender, EventArgs e)
    {
        dynamic newPista = new ExpandoObject();
        newPista.nombre = entryNombrePista.Text;
        newPista.tipoPista = pickerTipoPista.SelectedIndex;

        var res = await Globales._APICONTROLLER.registrarPistaEnPolideportivo(selectedPoli.id, newPista);

        string idPista = res.id;

        if (idPista != null)
        {
            await DisplayAlert("AVISO", "Se ha creado la pista nueva", "Entendido");
            await Navigation.PopAsync();
        }
        else
        {
            await DisplayAlert("ALERTA", "Se ha producido un error añadiendo la nueva pista", "Ok");
        }

    }

    private void pickerPolideportivosEliminables_SelectedIndexChanged(object sender, EventArgs e)
    {
        selectedPoliAEliminar = AllPolideportivosAEliminar[pickerPolideportivosEliminables.SelectedIndex];
    }

    private async void btnBorrarPolideportivo_Clicked(object sender, EventArgs e)
    {
        try
        {
            var res = await Globales._APICONTROLLER.eliminarPolideportivoById(selectedPoliAEliminar.id.ToString());
            if (res != null)
            {
                await DisplayAlert("AVISO", $"{res}", "OK");
                await Navigation.PopAsync();
            }
        }
        catch (Exception exc)
        {

        }
    }
}