package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.argumentresolver.Login;
import hello.login.web.session.SessionConst;
import hello.login.web.session.SessionManager;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;


    /**
     * 기본
     */
/*    @GetMapping("/")
    public String home() {
        //return "redirect:/items";
        return "home";
    }*/

    /**
     * 쿠키
     * @CookieValue를 사용해서 편리하게 쿠키를 조회할 수 있습니다.
     */
/*    @GetMapping("/")
    public String homeLogin(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {

        if (memberId == null) {
            return "home"; // 로그인 쿠키가 없는 사용자
        }

        // 로그인
        Member loginMember = memberRepository.findById(memberId);
        if (loginMember == null) {
            return "home"; // 로그인 쿠키가 있어도 회원이 아닌 사용자
        }
        model.addAttribute("member", loginMember);
        return "loginHome"; // 로그인 쿠키가 있는 회원
    }*/

    /**
     * 세션쿠키
     */
/*    @GetMapping("/")
    public String homeLogin(HttpServletRequest request, Model model) {

        //세션 관리자에 저장된 회원 정보 조회
        Member member = (Member) sessionManager.getSession(request);

        //로그인
        if (member == null) {
            return "home";
        }

        model.addAttribute("member", member);
        return "loginHome";
    }*/

    /**
     * 서블릿이 제공하는 HttpSession
     * - JSESSIONID (추정 불가능한 랜덤 값)
     */
/*    @GetMapping("/")
    public String homeLogin(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false);
        if (session == null) {
            return "home";
        }

        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        //세션에 회원 데이터가 없으면 home
        if (loginMember == null) {
            return "home";
        }
        log.info("안녕하세요" + loginMember + "고객님");

        //세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);
        return "loginHome";
    }*/

    /**
     * 스프링이 제공하는 @SessionAttribute
     * : 이미 로그인 된 사용자 찾을 때 편리하게 사용할 수 있음. 단, 세션 생성하는 기능은 아님
     */
/*    @GetMapping("/")
    public String homeLogin(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
            Member loginMember, Model model) {

        //세션에 회원 데이터가 없으면 home
        if (loginMember == null) {
            return "home";
        }

        //세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);
        return "loginHome";
    }*/

    /**
     * 요청 매핑 핸들러 어댑터 구조 ArgumentResolver 사용
     *
     */
    @GetMapping("/")
    public String homeLogin(@Login Member loginMember, Model model) {

        //세션에 회원 데이터가 없으면 home
        if (loginMember == null) {
            return "home";
        }

        //세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);
        return "loginHome";
    }

}