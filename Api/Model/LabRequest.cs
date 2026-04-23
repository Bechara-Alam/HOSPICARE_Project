namespace HospitalAPI.Models
{
    public class LabRequest
    {
        public int Id { get; set; }

        public int PatientId { get; set; }
        public string PatientName { get; set; }

        public int? DoctorId { get; set; }
        public string? DoctorName { get; set; }

        public string TestName { get; set; }
        public string PreferredDate { get; set; }
        public string? Notes { get; set; }

        public string Status { get; set; } = "Requested";

        public DateTime CreatedAt { get; set; } = DateTime.Now;
        public DateTime? ProcessingAt { get; set; }
        public DateTime? CompletedAt { get; set; }
    }
}