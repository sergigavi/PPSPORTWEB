using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PSport.Model.DTOs
{
    public class PolideportivoDTO
    {
        public Guid id { get; set; }
        public string cp { get; set; }
        public string direccion { get; set; }
        public ImageSource foto { get; set; }
        public List<dynamic> jugadoresAsociados { get; set; }
        public string localidad { get; set; }
        public string nombre { get; set; }
        public List<PistaDTO> pistas { get; set; }
        public string pistasDeportesString { get; set; }
        public string coordenadas { get; set; }
        public string horaApertura { get; set; }
        public string horaCierre { get; set; }
        public string horario { get; set; }
    }
}
