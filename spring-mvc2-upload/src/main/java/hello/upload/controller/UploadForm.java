package hello.upload.controller;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * SpringUploadController 사용하는 도메인
 */
@Data
public class UploadForm {

    private String itemName;
    private MultipartFile attachFile;
}
