using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;

namespace PSport.Utils
{
    public class Utils
    {

        #region CONFIG

        public async static Task<Dictionary<string, string>> ComprobarConfiguracion()
        {
            //  Compruebo que exista
            //  Si no existe lo creo e inicializo
            if (!File.Exists(Globales._ConfigurationPath.ToString()))
            {
                FileStream file = File.Create(Globales._ConfigurationPath.ToString());

                file.Close();
                file.Dispose();

                using FileStream outputStream = System.IO.File.OpenWrite(Globales._ConfigurationPath);
                using StreamWriter streamWriter = new StreamWriter(outputStream);

                foreach (KeyValuePair<string, string> entry in Globales._AppConfig)
                {
                    await streamWriter.WriteLineAsync($"{entry.Key}:{entry.Value}");
                }

                streamWriter.Close();
                outputStream.Close();
            }
            else
            {   //Si existe lo leo y lo cargo en mi global
                using FileStream inputStream = System.IO.File.OpenRead(Globales._ConfigurationPath);
                using StreamReader streamReader = new StreamReader(inputStream);

                string configStr = streamReader.ReadToEnd();    //Lo he tenido que hacer síncrono porque sino se me paraba aqui

                if (configStr != null)
                {
                    Globales._AppConfig = new Dictionary<string, string>();

                    string[] configarray = configStr.Split("\n");

                    foreach (string config in configarray)
                    {
                        try
                        {
                            string clave = config.Split(":")[0];
                            string valor = config.Split(":")[1];

                            if ((clave == "USR" || clave == "PASS") && valor.Trim() != "")
                            {
                                valor = DecryptString(Globales.__KeyMyKey, valor);
                            }

                            Globales._AppConfig.Add(clave, valor);

                        }
                        catch (Exception exc)
                        {
                            exc.ToString();
                        }

                    }

                }

            }

            return Globales._AppConfig;
        }

        public async static Task<bool> SobreescribirConfiguracion()
        {
            try
            {
                if (File.Exists(Globales._ConfigurationPath.ToString()))
                {
                    File.Delete(Globales._ConfigurationPath.ToString());
                }

                FileStream fs = File.Create(Globales._ConfigurationPath.ToString());
                fs.Close();
                fs.Dispose();

                using FileStream outputStream = System.IO.File.OpenWrite(Globales._ConfigurationPath);
                using StreamWriter streamWriter = new StreamWriter(outputStream);

                foreach (KeyValuePair<string, string> entry in Globales._AppConfig)
                {
                    if (entry.Key == "USR" || entry.Key == "PASS")
                    {
                        await streamWriter.WriteLineAsync($"{entry.Key}:{EncryptString(Globales.__KeyMyKey, entry.Value)}");
                    }
                    else
                    {
                        await streamWriter.WriteLineAsync($"{entry.Key}:{entry.Value}");
                    }
                }

                streamWriter.Close();
                outputStream.Close();
                return true;
            }
            catch (Exception exc)
            {
                exc.ToString();
                return false;
            }


        }

        #endregion

        #region HASHING
        /// <summary>
        /// Convierto un string en un hash codificado en SHA512
        /// </summary>
        /// <param name="str"></param>
        /// <returns></returns>
        public static string EncriptarHashStringSHA512(string str)
        {
            byte[] data = Encoding.UTF8.GetBytes(str);

            SHA512 sha512 = SHA512.Create();

            byte[] hashedString = sha512.ComputeHash(data);

            return GetStringFromHashByteArray(hashedString);
        }

        private static string GetStringFromHashByteArray(byte[] hashedString)
        {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < hashedString.Length; i++)
            {
                result.Append(hashedString[i].ToString("X2"));
            }
            return result.ToString();
        }

        #endregion

        #region ENCRIPTACION

        #region Aes

        public static string EncryptString(string key, string plainText)
        {
            byte[] iv = new byte[16];
            byte[] array;

            using (Aes aes = Aes.Create())
            {
                aes.Key = Encoding.UTF8.GetBytes(key);
                aes.IV = iv;

                ICryptoTransform encryptor = aes.CreateEncryptor(aes.Key, aes.IV);

                using (MemoryStream memoryStream = new MemoryStream())
                {
                    using (CryptoStream cryptoStream = new CryptoStream((Stream)memoryStream, encryptor, CryptoStreamMode.Write))
                    {
                        using (StreamWriter streamWriter = new StreamWriter((Stream)cryptoStream))
                        {
                            streamWriter.Write(plainText);
                        }

                        array = memoryStream.ToArray();
                    }
                }
            }

            return Convert.ToBase64String(array);
        }

        public static string DecryptString(string key, string cipherText)
        {
            byte[] iv = new byte[16];
            byte[] buffer = Convert.FromBase64String(cipherText);

            using (Aes aes = Aes.Create())
            {
                aes.Key = Encoding.UTF8.GetBytes(key);
                aes.IV = iv;
                ICryptoTransform decryptor = aes.CreateDecryptor(aes.Key, aes.IV);

                using (MemoryStream memoryStream = new MemoryStream(buffer))
                {
                    using (CryptoStream cryptoStream = new CryptoStream((Stream)memoryStream, decryptor, CryptoStreamMode.Read))
                    {
                        using (StreamReader streamReader = new StreamReader((Stream)cryptoStream))
                        {
                            return streamReader.ReadToEnd();
                        }
                    }
                }
            }
        }

        #endregion

        #endregion

        #region VALIDACIONES

        //Devuelve true si es valido el DNI
        public static bool CheckDNI(string dni)
        {
            //Comprobamos si el DNI tiene 9 digitos
            if (dni.Length != 9)
            {
                //No es un DNI Valido
                return false;
            }

            //Extraemos los números y la letra
            string dniNumbers = dni.Substring(0, dni.Length - 1);

            if ( Char.IsLetter( dniNumbers[0] ))
            {
                dniNumbers = 0 + dniNumbers.Substring(1);
            }

            string dniLeter = dni.Substring(dni.Length - 1, 1);
            //Intentamos convertir los números del DNI a integer
            var numbersValid = int.TryParse(dniNumbers, out int dniInteger);
            if (!numbersValid)
            {
                //No se pudo convertir los números a formato númerico
                return false;
            }
            if (CalculateDNILeter(dniInteger) != dniLeter)
            {
                //La letra del DNI es incorrecta
                return false;
            }
            //DNI Valido :)
            return true;
        }


        public static string CalculateDNILeter(int dniNumbers)
        {
            //Cargamos los digitos de control
            string[] control = { "T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E" };
            var mod = dniNumbers % 23;
            return control[mod];
        }

        #endregion

    }
}
