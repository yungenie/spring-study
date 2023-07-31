package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/***
 * API TEST (json -> json, json -> String, String -> json, String -> String)
 */
@Slf4j
@RestController
public class RestControllerTest {

    /***
     * Json으로 요청 받아 Json으로 응답하는 경우
     * @param data : VO 객체
     * @return 객체
     */
    @PostMapping("/request-body-json-test-v1")
    public HelloData requestBodyJsonTestV1(@RequestBody HelloData data) {
        log.info("username={}, age={}", data.getUsername(), data.getAge());
        return data; // return type : json
    }

    /***
     * Json으로 요청 받아 String으로 응답하는 경우
     * @param data : VO 객체
     * @return String // 예를 들어 조회 값을 주세요. 값을 만들어서 줘야 할 때는 조회값을 string convert 후 전달.
     */
    @PostMapping("/request-body-json-test-v2")
    public String requestBodyJsonTestV2(@RequestBody HelloData data) {
        log.info("username={}, age={}", data.getUsername(), data.getAge());
        return "go"; // return type :
    }


}
