package hello.upload.domain;

import lombok.Data;

import java.util.List;

/**
 * 사용자 화면용
 */
@Data
public class Item {

    private Long id;
    private String itemName;
    private UploadFile attachFile;
    private List<UploadFile> imageFiles;
}
