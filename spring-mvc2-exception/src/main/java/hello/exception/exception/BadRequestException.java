package hello.exception.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * API 예외 처리 - ResponseStatusExceptionResolver
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "error.bad") //messages.properties 사용 가능.
public class BadRequestException extends RuntimeException {
}
//ResponseStatusExceptionResolver