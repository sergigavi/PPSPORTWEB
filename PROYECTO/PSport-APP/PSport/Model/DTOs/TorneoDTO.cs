using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PSport.Model.DTOs
{
    public class TorneoDTO2
    {
        public Guid? id { get; set; }
        public string nombre { get; set; }
        public int deporte { get; set; }
        public int tipoTorneo { get; set; }
        public string estado { get; set; }
        public string fechaInicio { get; set; }
        public string fechaFin { get; set; }
        public List<dynamic>? partidos { get; set; }
        public List<dynamic>? jugadoresTorneo { get; set; }
        public List<dynamic>? equiposTorneo { get; set; }
    }

    public class TorneoDTO
    {
        public Guid? id { get; set; }
        public string nombre { get; set; }
        public string deporte { get; set; }
        public string tipoTorneo { get; set; }
        public string estado { get; set; }
        public string fechaInicio { get; set; }
        public string fechaFin { get; set; }
        public List<dynamic>? partidos { get; set; }
        public List<dynamic>? jugadoresTorneo { get; set; }
        public List<dynamic>? equiposTorneo { get; set; }
    }
}
