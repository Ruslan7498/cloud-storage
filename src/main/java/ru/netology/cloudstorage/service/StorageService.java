package ru.netology.cloudstorage.service;

import org.springframework.web.multipart.MultipartFile;
import ru.netology.cloudstorage.entity.FileUpload;

import java.util.List;

public interface StorageService {
    void uploadFile(MultipartFile multipartFile);

    void deleteFile(String nameFile);

    FileUpload getFile(String fileName);

    List<FileUpload> getAllFiles();

    void putFile(String fileName, String newNameFile);
}
