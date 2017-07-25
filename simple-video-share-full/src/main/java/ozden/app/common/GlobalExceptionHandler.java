package ozden.app.common;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class);

    @Value("${cors.enabled.domain}")
    private String corsEnabledDomainName;

    @ExceptionHandler(MultipartException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleMultipartException(HttpServletRequest request, MultipartException e) {
        logger.error("MultipartException is here");

        // headers should be set otherwise, frontend will get CORS error.
        // in controllers @CrossOrigin works well but not in ControllerAdvice
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, corsEnabledDomainName);
        return new ResponseEntity<>(new ErrorResponse(e, "file limit exceeded!"), headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    class ErrorResponse {
        Exception exception;
        String message;

        public ErrorResponse(Exception exception, String message) {
            this.exception = exception;
            this.message = message;
        }

        // getters are for json conversion
        public Exception getException() {
            return exception;
        }

        public String getMessage() {
            return message;
        }
    }
}