using Microsoft.AspNetCore.Http;

namespace HospitalAPI.Models
{
    public class UploadLabResultDto
    {
        public int LabRequestId { get; set; }
        public string? Report { get; set; }
        public IFormFile? File { get; set; }
    }
}