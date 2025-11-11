package job_portal.exception;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ApiException {

    @ExceptionHandler(ResponseStatusException.class)
    ResponseEntity<?> handleResponseStatusException(ResponseStatusException e){
        ErrorDetailRespone<?> errorDetailRespone = ErrorDetailRespone.builder()
            .code(e.getStatusCode().toString())
            .description(e.getReason())
            .build();
        return new ResponseEntity<>(ErrorRespone.builder()
            .error(errorDetailRespone)
            .build(),
            e.getStatusCode()
        );
    }
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ErrorRespone<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<Map<String,String>> errorDetail = new ArrayList<>();
        e.getFieldErrors()
            .forEach(
                fieldError -> {
                    Map<String, String> errorDetailMap = new HashMap<>();
                    errorDetailMap.put("field", fieldError.getField());
                    errorDetailMap.put("reason", fieldError.getDefaultMessage());
                    errorDetail.add(errorDetailMap);
                }
            );

        ErrorDetailRespone<?> errorDetailRespone = ErrorDetailRespone.builder()
            .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .description(errorDetail)
            .build();

        return ErrorRespone.builder()
            .error(errorDetailRespone)
            .build();
    }
    
}
