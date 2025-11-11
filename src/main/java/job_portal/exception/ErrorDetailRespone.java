package job_portal.exception;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorDetailRespone<T> {
    private String code;
    private T description;
}