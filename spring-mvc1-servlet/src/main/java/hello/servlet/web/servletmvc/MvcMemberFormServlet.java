package hello.servlet.web.servletmvc;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "mvcMemberFormServlet", urlPatterns = "/servlet-mvc/members/new-form")
public class MvcMemberFormServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String viewPath = "/WEB-INF/views/new-form.jsp"; //WEB-INF 경로 안에 있으면 url 호출 불가 합니다. 컨트롤러 통해서 호출 가능
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath); //dispatcher는 controller에서 view로 이동하기 위해 사용합니다.
        dispatcher.forward(request, response); //다른 서블릿이나 JSP로 이동할 수 있는 기능. 서버 내부에서 다시 호출함하므로 웹 브라우저로 응답 하는 게 아님.

    }
}
