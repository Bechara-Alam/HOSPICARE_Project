namespace HospitalAPI.Models
{
    public class ReservationDto
    {
        public int RoomId { get; set; }

        public string PatientName { get; set; }

        public DateTime StartDateTime { get; set; }

        public DateTime EndDateTime { get; set; }
    }
}