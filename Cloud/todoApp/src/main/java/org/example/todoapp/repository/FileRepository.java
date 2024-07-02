package org.example.todoapp.repository;

import java.util.Optional;
import org.example.todoapp.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
    Optional<FileEntity> findByUuid(String uuid);
}
