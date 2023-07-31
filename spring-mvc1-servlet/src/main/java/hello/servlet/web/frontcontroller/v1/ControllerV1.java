package hello.servlet.web.frontcontroller.v1;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 서블릿과 비슷한 모양의 컨트롤러 인터페이스 도입
 * 각 컨트롤러는 해당 인터페이스를 구현하면 로직의 일관성을 가져갈 수 있습니다. (다형성)
 */
public interface ControllerV1 {

    void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
