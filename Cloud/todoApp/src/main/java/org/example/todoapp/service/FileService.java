package org.example.todoapp.service;

import java.time.LocalDate;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

//새로만든 서비스클래스
@Service
@RequiredArgsConstructor
public class FileService {

    private final S3Service s3Service;
    private final FileDatabaseService fileDatabaseService;

    @Transactional
    public void uploadFileAndSaveMetadata(MultipartFile file) throws Exception {
        String uuid = UUID.randomUUID().toString();
        String datePath = LocalDate.now().toString().replace("-", "/");
        String key = datePath + "/" + uuid;

        try {
            s3Service.uploadFile(file, key);
            fileDatabaseService.saveFileMetadata(uuid, key, file.getOriginalFilename(), file.getSize(), file.getContentType());
        } catch (Exception e) {
            s3Service.deleteFile(key); // S3 업로드 롤백
            throw e; // 예외 재발생
        }
    }
}
