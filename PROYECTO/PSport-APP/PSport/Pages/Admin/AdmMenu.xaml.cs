using CommunityToolkit.Maui.Behaviors;
using PSport.Utils;

namespace PSport.Pages.Admin;

public partial class AdmMenu : ContentPage
{
	public AdmMenu()
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

        btnEquiposADM.BackgroundColor = Globales._paleta.color3;
        btnEquiposADM.TextColor = Globales._paleta.color0;
        btnEquiposADM.BorderColor = Globales._paleta.color2;
        
        btnPistasADM.BackgroundColor = Globales._paleta.color3;
        btnPistasADM.TextColor = Globales._paleta.color0;
        btnPistasADM.BorderColor = Globales._paleta.color2;
        
        btnTorneosADM.BackgroundColor = Globales._paleta.color3;
        btnTorneosADM.TextColor = Globales._paleta.color0;
        btnTorneosADM.BorderColor = Globales._paleta.color2;

        //
        lblNombre.Text = $"¡Bienvenido {Globales._LoggedUser.nombre} ADMIN!";
    }

    private async void btnEquiposADM_Clicked(object sender, EventArgs e)
    {
        await btnEquiposADM.ScaleTo(1.075, 250);
        await btnEquiposADM.ScaleTo(1, 500);

        await Navigation.PushAsync(new AdmGestionEquipos());
    }

    private async void btnPistasADM_Clicked(object sender, EventArgs e)
    {
        await btnPistasADM.ScaleTo(1.075, 250);
        await btnPistasADM.ScaleTo(1, 500);

        await Navigation.PushAsync(new AdmGestionPistas());
    }

    private async void btnTorneosADM_Clicked(object sender, EventArgs e)
    {
        await btnTorneosADM.ScaleTo(1.075, 250);
        await btnTorneosADM.ScaleTo(1, 500);

        await Navigation.PushAsync(new AdmGestionTorneos());
    }
}