using Newtonsoft.Json;
using PSport.Model.DTOs;
using PSport.Utils;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PSport
{
    public class APIController
    {

        private HttpClient HttpClient;

        public APIController()
        {
            //Para saltarme el https
            var handler = new HttpClientHandler();
            handler.ClientCertificateOptions = ClientCertificateOption.Manual;
            handler.ServerCertificateCustomValidationCallback =
            (httpRequestMessage, cert, cetChain, policyErrors) =>
            {
                return true;
            };

            System.Net.ServicePointManager.ServerCertificateValidationCallback +=
                (se, cert, chain, sslerror) =>
                {
                    return true;
                };

            this.HttpClient = new HttpClient(handler);

            this.HttpClient.Timeout = TimeSpan.FromSeconds(119);
        }

        private async void ComprobarToken()
        {
            if (Globales._FechaCaducidadAccessToken == DateTime.Now)
            {
                if (Globales._FechaCaducidadRefreshToken == DateTime.Now)
                {
                    //Todo caducado, cerrar sesion
                }
                else
                {
                    //Token de acceso caducado, uso el refresh para renovarlo
                    await RenovarAccessToken();
                }
                
            }
        }

        private async Task RenovarAccessToken()
        {

        }

        #region Login

        public async Task<dynamic> Login(UsuarioLogin usuario)
        {
            try
            {
                string url = Globales.URL_BASE_API + "/auth";

                var data = JsonConvert.SerializeObject(usuario, Newtonsoft.Json.Formatting.None, new JsonSerializerSettings { NullValueHandling = NullValueHandling.Ignore });
                var httpContent = new StringContent(data, Encoding.UTF8, "application/json");

                //HttpClient.DefaultRequestHeaders.Add("Accept", "*/*");
                //HttpClient.DefaultRequestHeaders.Add("Cache-Control", "no-cache");
                //HttpClient.DefaultRequestHeaders.Add("Connection", "keep-alive");
                //HttpClient.DefaultRequestHeaders.Add("Content-Type", "application/json");

                var res = await HttpClient.PostAsync(url, httpContent);   //"https://jsonplaceholder.typicode.com/posts"

                string resObj = await res.Content.ReadAsStringAsync();

                if (res.IsSuccessStatusCode)
                {

                    if (resObj != null)
                    {
                        var obj = JsonConvert.DeserializeObject(resObj);

                        if (obj != null)
                        {
                            return obj;
                        }
                        else
                        {
                            return null;
                        }
                    }
                    else
                    {
                        return null;
                    }
                }
                else
                {
                    HttpClient.CancelPendingRequests();
                    string resErr = resObj;

                    if (resErr != null)
                    {
                        return JsonConvert.DeserializeObject(resErr);
                    }
                    else
                    {
                        return null;
                    }

                }
            }
            catch (TimeoutException te)
            {
                Console.WriteLine(te.ToString());
                return null;
            }
            catch (Exception exc)
            {
                HttpClient.CancelPendingRequests();
                Console.WriteLine(exc.Message);
                return null;
            }


        }

        #endregion

        #region Register

        public async Task<dynamic> Register(UsuarioRegister usuario)
        {
            try
            {
                string url = Globales.URL_BASE_API + "/usuarios/registrar";

                var data = JsonConvert.SerializeObject(usuario, Newtonsoft.Json.Formatting.None, new JsonSerializerSettings { NullValueHandling = NullValueHandling.Ignore });
                var httpContent = new StringContent(data, Encoding.UTF8, "application/json");

                var res = await HttpClient.PostAsync(url, httpContent);

                string resObj = await res.Content.ReadAsStringAsync();

                if (res.IsSuccessStatusCode)
                {

                    if (resObj != null)
                    {
                        var obj = JsonConvert.DeserializeObject(resObj);

                        if (obj != null)
                        {
                            return obj;
                        }
                        else
                        {
                            return null;
                        }
                    }
                    else
                    {
                        return null;
                    }
                }
                else
                {
                    HttpClient.CancelPendingRequests();
                    string resErr = resObj;

                    if (resErr != null)
                    {
                        return JsonConvert.DeserializeObject(resErr);
                    }
                    else
                    {
                        return null;
                    }

                }
            }
            catch (TimeoutException te)
            {
                Console.WriteLine(te.ToString());
                return null;
            }
            catch (Exception exc)
            {
                HttpClient.CancelPendingRequests();
                Console.WriteLine(exc.Message);
                return null;
            }

        }

        #endregion

        public async Task<List<ReservaDTO>> GetReservasByIdUsuario(string id)
        {
            this.ComprobarToken();

            try
            {
                string url = Globales.URL_BASE_API + $"/reservas/todas/{id}";

                var res = await HttpClient.GetAsync(url);   //params

                string resObj = await res.Content.ReadAsStringAsync();

                if (res.IsSuccessStatusCode)
                {

                    if (resObj != null)
                    {
                        var obj = JsonConvert.DeserializeObject<List<ReservaDTO>>(resObj);

                        if (obj != null)
                        {
                            return obj;
                        }
                        else
                        {
                            return null;
                        }
                    }
                    else
                    {
                        return null;
                    }
                }
                else
                {
                    HttpClient.CancelPendingRequests();
                    return null;
                }
            }
            catch (Exception exc)
            {
                HttpClient.CancelPendingRequests();
                Console.WriteLine(exc.Message);
                return null;
            }
        }

        internal async Task<dynamic> GetPolideportivos()
        {
            this.ComprobarToken();

            try
            {
                string url = Globales.URL_BASE_API + "/polideportivos/todoslimit";

                this.HttpClient.DefaultRequestHeaders.Authorization = new System.Net.Http.Headers.AuthenticationHeaderValue("Bearer", Globales._CurrentAccessToken);
                var res = await HttpClient.GetAsync(url);   //params

                string resObj = await res.Content.ReadAsStringAsync();

                if (res.IsSuccessStatusCode)
                {

                    if (resObj != null)
                    {
                        var obj = JsonConvert.DeserializeObject(resObj);

                        if (obj != null)
                        {
                            return obj;
                        }
                        else
                        {
                            return null;
                        }
                    }
                    else
                    {
                        return null;
                    }
                }
                else
                {
                    HttpClient.CancelPendingRequests();
                    string resErr = resObj;

                    if (resErr != null)
                    {
                        return JsonConvert.DeserializeObject(resErr);
                    }
                    else
                    {
                        return null;
                    }

                }
            }
            catch (Exception exc)
            {
                HttpClient.CancelPendingRequests();
                Console.WriteLine(exc.Message);
                return null;
            }
        }

        internal async Task<dynamic> GetUsuarioByEmail(string email)
        {
            this.ComprobarToken();

            try
            {
                string url = Globales.URL_BASE_API + $"/usuarios/getByEmail/{email}";

                this.HttpClient.DefaultRequestHeaders.Authorization = new System.Net.Http.Headers.AuthenticationHeaderValue("Bearer", Globales._CurrentAccessToken);
                var res = await HttpClient.GetAsync(url);

                string resObj = await res.Content.ReadAsStringAsync();

                if (res.IsSuccessStatusCode)
                {

                    if (resObj != null)
                    {
                        var obj = JsonConvert.DeserializeObject(resObj);

                        if (obj != null)
                        {
                            return obj;
                        }
                        else
                        {
                            return null;
                        }
                    }
                    else
                    {
                        return null;
                    }
                }
                else
                {
                    HttpClient.CancelPendingRequests();
                    string resErr = resObj;

                    if (resErr != null)
                    {
                        return JsonConvert.DeserializeObject(resErr);
                    }
                    else
                    {
                        return null;
                    }

                }
            }
            catch (Exception exc)
            {
                HttpClient.CancelPendingRequests();
                Console.WriteLine(exc.Message);
                return null;
            }
        }

        public async Task EditarPerfil()
        {

        }

        public async Task<dynamic> Reservar(dynamic? newReserva)
        {
            this.ComprobarToken();

            try
            {
                string url = Globales.URL_BASE_API + $"/reservas/nueva";

                this.HttpClient.DefaultRequestHeaders.Authorization = new System.Net.Http.Headers.AuthenticationHeaderValue("Bearer", Globales._CurrentAccessToken);

                var data = JsonConvert.SerializeObject(newReserva, Newtonsoft.Json.Formatting.None, new JsonSerializerSettings { NullValueHandling = NullValueHandling.Ignore });
                var httpContent = new StringContent(data, Encoding.UTF8, "application/json");

                var res = await HttpClient.PostAsync(url, httpContent);

                string resObj = await res.Content.ReadAsStringAsync();

                if (res.IsSuccessStatusCode)
                {

                    if (resObj != null)
                    {
                        var obj = JsonConvert.DeserializeObject(resObj);

                        if (obj != null)
                        {
                            return obj;
                        }
                        else
                        {
                            return null;
                        }
                    }
                    else
                    {
                        return null;
                    }
                }
                else
                {
                    HttpClient.CancelPendingRequests();
                    string resErr = resObj;

                    if (resErr != null)
                    {
                        return JsonConvert.DeserializeObject(resErr);
                    }
                    else
                    {
                        return null;
                    }

                }
            }
            catch (Exception exc)
            {
                HttpClient.CancelPendingRequests();
                Console.WriteLine(exc.Message);
                return null;
            }
        }

        public async Task<List<PistaDTO>> getPistasByIdPolideportivo(Guid id)
        {
            this.ComprobarToken();

            try
            {
                string url = Globales.URL_BASE_API + $"/polideportivos/{id}/pistas";

                this.HttpClient.DefaultRequestHeaders.Authorization = new System.Net.Http.Headers.AuthenticationHeaderValue("Bearer", Globales._CurrentAccessToken);

                var res = await HttpClient.GetAsync(url);

                string resObj = await res.Content.ReadAsStringAsync();

                if (res.IsSuccessStatusCode)
                {

                    if (resObj != null)
                    {
                        var obj = JsonConvert.DeserializeObject<List<PistaDTO>>(resObj);

                        if (obj != null)
                        {
                            return obj;
                        }
                        else
                        {
                            return null;
                        }
                    }
                    else
                    {
                        return null;
                    }
                }
                else
                {
                    HttpClient.CancelPendingRequests();
                    string resErr = resObj;

                    if (resErr != null)
                    {
                        return null;
                    }
                    else
                    {
                        return null;
                    }

                }
            }
            catch (Exception exc)
            {
                HttpClient.CancelPendingRequests();
                Console.WriteLine(exc.Message);
                return null;
            }
        }

        /// <summary>
        /// obtengo las reservas que hay en una determinada pista un dia concreto
        /// </summary>
        /// <param name="id"></param>
        /// <param name="date"></param>
        /// <returns></returns>
        public async Task<List<ReservaRequest>> getReservasDePistaByFecha(Guid id, DateTime date)
        {
            this.ComprobarToken();

            try
            {
                string url = Globales.URL_BASE_API + $"/polideportivo/pistas/{id}/reservas?={date}";

                this.HttpClient.DefaultRequestHeaders.Authorization = new System.Net.Http.Headers.AuthenticationHeaderValue("Bearer", Globales._CurrentAccessToken);

                var res = await HttpClient.GetAsync(url);

                string resObj = await res.Content.ReadAsStringAsync();

                if (res.IsSuccessStatusCode)
                {
                    return JsonConvert.DeserializeObject<List<ReservaRequest>>(resObj);
                }
                else
                {
                    return null;
                }

            }
            catch (Exception exc)
            {
                exc.ToString();
                return null;
            }
        }
    }
}
