using Android.App;
using Android.Runtime;

namespace PSport
{
#if !DEBUG
    [Application]
#endif

#if DEBUG
    [Application(UsesCleartextTraffic = true)]
#endif
    public class MainApplication : MauiApplication
    {

        public MainApplication(IntPtr handle, JniHandleOwnership ownership)
            : base(handle, ownership)
        {

        }

        protected override MauiApp CreateMauiApp() => MauiProgram.CreateMauiApp();
    }
}
