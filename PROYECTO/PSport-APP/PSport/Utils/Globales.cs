using PSport.Model.DTOs;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PSport.Utils
{
    public class Globales
    {
        //
        public static dynamic? _LoggedUser = null;

        //EAS KEY CRYPTO
        public static string __KeyMyKey = "b14ca5898a4e4133bbce2ea2315a1916";

        public static string _ConfigurationPath = FileSystem.Current.CacheDirectory + "/config.config";

        public static APIController _APICONTROLLER = new APIController();

#if RELEASE
        //public static string URL_BASE_API = "http://10.0.2.2:9432/ppsport/api";
        //public static string URL_BASE_API = "http://192.168.1.143:9432/ppsport/api";

        //PRE
        public static string URL_BASE_API = "https://glad-cuddly-shepherd.ngrok-free.app/ppsport/api";

#endif

#if !RELEASE
        //EMULADOR
        //public static string URL_BASE_API = "http://10.0.2.2:9432/ppsport/api";

        //PRE
        //public static string URL_BASE_API = "https://glad-cuddly-shepherd.ngrok-free.app/ppsport/api";

        //LOCAL
        public static string URL_BASE_API = "http://192.168.1.143:9432/ppsport/api"; 
#endif

        public static Dictionary<string, string> _AppConfig = new Dictionary<string, string>()
        {
            { "Recuerdame", "1" },
            { "USR", "" },
            { "PASS", "" },
        };

        //
        public static DateTime? _FechaCaducidadAccessToken = null;
        public static DateTime? _FechaCaducidadRefreshToken = null;
        public static string _CurrentAccessToken = String.Empty;
        public static string _CurrentRefreshToken = String.Empty;

        //Paleta de colores
        public static Paleta? _paleta;

        public static List<Paleta> MisPaletas = new List<Paleta>();

        //
        public static PolideportivoDTO? selectedPolideportivo = null;
        public static PistaDTO? selectedPista = null;

    }

}
