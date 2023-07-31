package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class ControllerTest {

    @ResponseBody
    @GetMapping("/model-attribute-get-test-v1")
    public String modelAttributeGetTestV1(@ModelAttribute HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }

    @ResponseBody
    @GetMapping("/model-attribute-get-test-v2")
    public String modelAttributeGetTestV2(HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }

    @ResponseBody
    @PostMapping("/model-attribute-post-test-v1")
    public String modelAttributePostTestV1(@ModelAttribute HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }

    @ResponseBody
    @PostMapping("/model-attribute-post-test-v2")
    public String modelAttributePostTestV2(HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }

    @ResponseBody
    @GetMapping("/model-attribute-get-test-v3")
    public String modelAttributeGetTestV3(@RequestBody HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }

    /**
     * error : Resolved [org.springframework.web.HttpMediaTypeNotSupportedException: Content type 'application/x-www-form-urlencoded;charset=UTF-8' not supported]
     * 해결된 [org.springframework.web.지원되지 않는 HttpMediaType예외: 내용 유형 'application/x-www-form-url 인코딩; 문자 집합=UTF-8'은 지원되지 않습니다]
     */
    @ResponseBody
    @PostMapping("/model-attribute-post-test-v3")
    public String modelAttributePostTestV3(@RequestBody HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }

}
