package alex.com.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionResolver {

    @ExceptionHandler(value = {org.apache.tomcat.util.http.fileupload.FileUploadException.class,
            org.springframework.web.multipart.MultipartException.class})
    public ResponseEntity<Object> fileUploadExceptionHandler() {

        return new ResponseEntity<>("Вы используете битый файл.", HttpStatus.BAD_REQUEST);
    }

}
