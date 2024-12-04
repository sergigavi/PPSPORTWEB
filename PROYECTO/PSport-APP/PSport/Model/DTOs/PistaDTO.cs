using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PSport.Model.DTOs
{
    public class PistaDTO
    {
        public Guid id { get; set; }
        public string nombre { get; set; }
        public string tipoPista { get; set; }
        public string nombrePolideportivo { get; set; }
        public string polideportivoID { get; set; }

    }
}
