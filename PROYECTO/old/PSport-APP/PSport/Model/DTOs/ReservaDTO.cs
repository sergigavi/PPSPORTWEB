using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PSport.Model.DTOs
{
    public class ReservaDTO
    {
        public Guid id { get; set; }
        public string fecha { get; set; }
        public string horaInicio { get; set; }
        public string horaFin { get; set; }
        public string usuarioID { get; set; }
        public PistaDTO pista { get; set; }
    }
}
