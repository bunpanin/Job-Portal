package job_portal.feature.seeker.reference.dto.request;

public record UpdateReferenceRequest(
    String name,
    String position,
    String company,
    String phoneNumber,
    String email
) {
}
