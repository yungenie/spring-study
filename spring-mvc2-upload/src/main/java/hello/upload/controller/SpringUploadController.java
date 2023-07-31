package hello.upload.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@Slf4j
@Controller
@RequestMapping("/spring")
public class SpringUploadController {

    @Value("${file.dir}")
    private String fileDir;

    @GetMapping("/upload")
    public String newFile() {
        return "upload-form";
    }

    /**
     * @RequestParam 업로드하는 HTML Form의 name에 맟춰 매핑된다.
     */
    //@PostMapping("/upload")
    public String saveFileReq(@RequestParam String itemName,
                           @RequestParam MultipartFile file, HttpServletRequest request) throws IOException {

        log.info("request={}", request);
        log.info("itemName={}", itemName);
        log.info("multipartFile={}", file);

        if (!file.isEmpty()) {
            String fullPath = fileDir + file.getOriginalFilename(); // 업로드 파일 명
            log.info("파일 저장 fullPath={}", fullPath);
            file.transferTo(new File(fullPath)); // 파일 저장
        }

        return "upload-form";
    }

    /**
     * @ModelAttribute 업로드하는 HTML Form과 UploadForm 도메인 매핑된다.
     */
    @PostMapping("/upload")
    public String saveFileModel(@ModelAttribute UploadForm uploadForm) throws IOException {

        if (!uploadForm.getAttachFile().isEmpty()) {
            String fullPath = fileDir + uploadForm.getAttachFile().getOriginalFilename(); // 업로드 파일 명
            log.info("파일 저장 fullPath={}", fullPath);
            uploadForm.getAttachFile().transferTo(new File(fullPath)); // 파일 저장
        }

        return "upload-form";
    }

    // note @RequestParam,@ModelAttribute ArgumentResolver가 알아서 다 적용해준다.
}
