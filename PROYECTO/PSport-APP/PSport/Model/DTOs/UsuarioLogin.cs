using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PSport.Model.DTOs
{
    public class UsuarioLogin
    {
        public string email { get; set; }

        //sha512
        public string password { get; set; }
    }

}
