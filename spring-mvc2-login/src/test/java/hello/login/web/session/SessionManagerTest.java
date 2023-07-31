package hello.login.web.session;

import hello.login.domain.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SessionManagerTest {

    SessionManager sessionManager = new SessionManager();

    @Test
    void sessionTest() {

        //세션 생성 (서버)
        MockHttpServletResponse response = new MockHttpServletResponse(); // 가짜 HttpServletResponse
        Member member = new Member();
        sessionManager.createSession(member, response);

        //요청에 응답 쿠키 저장 (클라이언트)
        MockHttpServletRequest request = new MockHttpServletRequest(); // 가짜 HttpServletRequest
        request.setCookies(response.getCookies());

        //세션 조회 (서버)
        Object result = sessionManager.getSession(request);
        assertThat(result).isEqualTo(member);

        //세션 만료 (서버)
        sessionManager.expire(request);
        Object expired = sessionManager.getSession(request);
        assertThat(expired).isNull();

    }
}