namespace HospitalAPI.Models
{
    public class LabResult
    {
        public int Id { get; set; }

        public int LabRequestId { get; set; }

        public int PatientId { get; set; }
        

        public int? DoctorId { get; set; }
        public string? DoctorName { get; set; }

        public string? TestName { get; set; }
        public string? PatientName { get; set; }
        public string? Report { get; set; }

        public string? PdfFileName { get; set; }
        public string? PdfUrl { get; set; }

        public DateTime ResultDate { get; set; } = DateTime.Now;
    }
}