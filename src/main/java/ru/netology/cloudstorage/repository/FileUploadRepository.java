package ru.netology.cloudstorage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.netology.cloudstorage.entity.FileUpload;

@Repository
public interface FileUploadRepository extends JpaRepository<FileUpload, Long> {
    FileUpload findByFileName(String fileName);
}
