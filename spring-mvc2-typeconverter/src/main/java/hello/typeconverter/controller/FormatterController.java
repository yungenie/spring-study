package hello.typeconverter.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Controller
public class FormatterController {

    @GetMapping("/formatter/edit")
    public String formatterForm(Model model) {
        ReqForm reqForm = new ReqForm();
        // 현재날짜 -> 날짜 (포맷지원)
        reqForm.setLocalDateTime(LocalDateTime.now());

        model.addAttribute("form", reqForm);
        return "formatter-form";
    }

    @PostMapping("/formatter/edit")
    public String formatterEdit(@ModelAttribute ResForm resForm) {
        return "formatter-view";
    }

    @Data
    static class ReqForm {
        private Integer number; // 10000 -> 10,000

        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime localDateTime;

        @DateTimeFormat(pattern = "yyyy/MM/dd")
        private LocalDate date; // note form에서 받을 때 String 타입으로 넘어오니깐 String 타입으로 선언해봤음.

    }

    @Data
    static class ResForm {
        @NumberFormat(pattern = "###,###")
        private Integer number; // 10000 -> 10,000

        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime localDateTime;

        @DateTimeFormat(pattern = "yyyy/MM/dd")
        private LocalDate date;

        //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy hh:mm:ss a", timezone = "Asia/Seoul")
        // @JsonFormat 객체 <-> JSON 사용하는 포맷으로 jackson 라이브러리 com.fasterxml.jackson.annotation.JsonFormat
    }
}
