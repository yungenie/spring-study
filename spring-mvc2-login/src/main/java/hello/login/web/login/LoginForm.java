package hello.login.web.login;

import lombok.*;

import javax.validation.constraints.NotEmpty;

/*
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
*/


//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//
//@Setter

@AllArgsConstructor
@Getter
@ToString
public class LoginForm {

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;

}
