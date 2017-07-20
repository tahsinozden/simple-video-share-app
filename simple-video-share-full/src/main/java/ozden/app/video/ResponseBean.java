package ozden.app.video;

import org.springframework.http.HttpStatus;

public class ResponseBean {
    private String message;
    private HttpStatus httpStatus;

    public ResponseBean(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
