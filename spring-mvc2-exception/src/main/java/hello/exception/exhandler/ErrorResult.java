package hello.exception.exhandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;


@Getter
@AllArgsConstructor
@ToString
public class ErrorResult {
    private String code;
    private String message;
}
