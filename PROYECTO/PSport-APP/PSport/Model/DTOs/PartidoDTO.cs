using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PSport.Model.DTOs
{
    public class PartidoDTO
    {
        public Guid? id { get; set; }
        public string fecha { get; set; }
        public string jugadoresPartidos { get; set; }
        public string equiposPartidos { get; set; }
        public string torneoID { get; set; }
        public string nombreTorneo { get; set; }
        public string resultado { get; set; }
        public string ronda { get; set; }
    }
}
