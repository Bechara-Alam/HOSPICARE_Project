namespace HospitalAPI.Models
{
    public class Appointment
    {
        public int Id { get; set; }

        public int DoctorId { get; set; }

        public int PatientId { get; set; }

        public string Date { get; set; }

        public string Time { get; set; }

        public string Status { get; set; }
        public string? PaymentStatus { get; set; } = "pending";
        public string? PaymentMethod { get; set; }
        public string? ReferenceNumber { get; set; }
        public int Amount { get; set; }
    }
}