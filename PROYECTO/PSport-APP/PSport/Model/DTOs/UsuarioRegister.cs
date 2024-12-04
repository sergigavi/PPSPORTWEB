using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PSport.Model.DTOs
{
    public class UsuarioRegister
    {
        public string dni { get; set; }
        public string nombre { get; set; }
        public string apellido1 { get; set; }
        public string apellido2 { get; set; }
        public string fechaNacimiento { get; set; }
        public string email { get; set; }

        //sha512
        public string password { get; set; }
    }
}
