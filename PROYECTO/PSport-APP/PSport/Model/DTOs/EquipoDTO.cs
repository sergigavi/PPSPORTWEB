using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PSport.Model.DTOs
{
    public class EquipoDTO
    {

        public Guid id { get; set; }
        public string nombre { get; set; }
        public string deporte { get; set; }
        public string img { get; set; }
        public List<dynamic> jugadores { get; set; }
        public List<dynamic> equipoTorneos { get; set; }
        public List<dynamic> equipoPartidos { get; set; }

    }
}
