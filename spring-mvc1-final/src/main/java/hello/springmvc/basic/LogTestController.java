package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Controller 는 반환 값이 String 이면 뷰 이름으로 인식됩니다. 그래서 뷰를 찾고 뷰가 랜더링 됩니다.
 * @RestController 는 반환 값으로 뷰를 찾는 것이 아니라, HTTP 메시지 바디에 바로 입력됩니다.
 */
//@Controller
@Slf4j
@RestController
public class LogTestController {

    //private final Logger log = LoggerFactory.getLogger(getClass());
    //private final Logger log = LoggerFactory.getLogger(LogTestController.class); // 클래스명 수동으로 기입 가능.
    //private static final Logger log = LoggerFactory.getLogger(LogTestController.class);



    @RequestMapping("/log-test")
    public String logTest() {
        String name = "Spring";

        System.out.println("name = " + name);
        /***
         * log level : TRACE < DEBUG < INFO < WARN < ERROR
         */
        log.trace("trace my log="+ name); // 연산이 일어나면서 cpu, 메모리 사용함. 쓸모없는 resource 사용합니다.
        log.trace("trace log={}, {}, {}", name, name, name); //여러개 출력도 가능합니다.
        log.debug("debug log={}", name);
        log.info(" info log={}", name);
        log.warn(" warn log={}", name);
        log.error("error log={}", name);

        return "ok";
    }
}
