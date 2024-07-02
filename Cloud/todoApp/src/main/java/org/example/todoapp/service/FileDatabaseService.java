package org.example.todoapp.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.todoapp.entity.FileEntity;
import org.example.todoapp.repository.FileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FileDatabaseService {
    private final FileRepository fileRepository;

    @Transactional
    public FileEntity saveFileMetadata(String uuid, String path, String originalFileName, long size, String mimeType) {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setUuid(uuid);
        fileEntity.setPath(path);
        fileEntity.setOriginalFilename(originalFileName);
        fileEntity.setSize(size);
        fileEntity.setMimeType(mimeType);

        return fileRepository.save(fileEntity);
    }

    public Optional<FileEntity> getFileMetadata(String uuid) {
        return fileRepository.findByUuid(uuid);
    }
}
