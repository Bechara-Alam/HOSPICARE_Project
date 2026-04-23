public class DoctorDto
{
    public string StaffId { get; set; }
    public string FullName { get; set; }
    public string PasswordHash { get; set; }

    // ✅ ADD THESE
    public string? Phone { get; set; }
    public string? Specialization { get; set; }
    public int Price { get; set; }
}