using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PSport.Model
{
    public class Reserva
    {
        public Guid Id { get; set; }
        public string? Nombre { get; set; }
        public Guid Usuario_Id { get; set; }
        public Guid Deporte_Id { get; set; }
        public Guid Pista_Id { get; set; }
        public DateTime HoraInicio { get; set; }
        public DateTime HoraFin { get; set; }
    }
}
