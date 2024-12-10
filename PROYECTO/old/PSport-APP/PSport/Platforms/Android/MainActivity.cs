using Android.App;
using Android.Content.PM;
using Android.OS;
using AndroidX.AppCompat.App;

namespace PSport
{
    [Activity(Theme = "@style/Maui.SplashTheme", MainLauncher = true, LaunchMode = LaunchMode.SingleTop, ConfigurationChanges = ConfigChanges.ScreenSize | ConfigChanges.Orientation | ConfigChanges.UiMode | ConfigChanges.ScreenLayout | ConfigChanges.SmallestScreenSize | ConfigChanges.Density)]
    public class MainActivity : MauiAppCompatActivity
    {
        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);

            try
            {
                // Forzar el modo claro
                AppCompatDelegate.DefaultNightMode = AppCompatDelegate.ModeNightNo;
                //For Android API 31+
                var uiModeManager = (UiModeManager)GetSystemService(UiModeService);
                uiModeManager.SetApplicationNightMode(1);
            }
            catch (Exception exc)
            {

            }

        }
    }
}
