package hello.upload.file;

import hello.upload.domain.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 업로드 파일 정보 보관
 * - 서버에서는 사용자가 업로드한 파일명을 겹치지 않도록 내부에서 관리하는 별도의 파일명이 필요함.
 */
@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    /**
     * 파일 경로 반환
     */
    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    /**
     * 다중 업로드
     */
    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFileResult.add(storeFile(multipartFile));
            }
        }
        return storeFileResult;
    }

    /**
     * 파일 저장
     */
    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFileName))); // 파일 저장
        return new UploadFile(originalFilename, storeFileName);
    }

    /**
     * 서버에 저장하는 파일명
     */
    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename); // 확장자
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    /**
     * 오리지널 파일에서 확장자 추출
     */
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }


}
