package ru.netology.cloudstorage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.cloudstorage.entity.FileUpload;
import ru.netology.cloudstorage.repository.FileUploadRepository;

import java.io.IOException;
import java.util.List;

@Service
public class StorageServiceImpl implements StorageService {
    private final FileUploadRepository fileUploadRepository;

    @Autowired
    public StorageServiceImpl(FileUploadRepository fileUploadRepository) {
        this.fileUploadRepository = fileUploadRepository;
    }

    @Override
    public void uploadFile(MultipartFile multipartFile) {
        try {
            FileUpload fileUpload = FileUpload.builder()
                    .fileName(multipartFile.getOriginalFilename())
                    .fileType(multipartFile.getContentType())
                    .fileData(multipartFile.getBytes())
                    .fileSize(multipartFile.getSize())
                    .build();
            fileUploadRepository.save(fileUpload);
        } catch (IOException e) {
            throw new RuntimeException("Cloud not store the file. Error: " + e.getMessage());
        }
    }

    @Override
    public FileUpload getFile(String fileName) {
        try {
            FileUpload fileUpload = fileUploadRepository.findByFileName(fileName);
            return fileUpload;
        } catch (RuntimeException e) {
            throw new RuntimeException("File not found. Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteFile(String fileName) {
        try {
            FileUpload fileUpload = fileUploadRepository.findByFileName(fileName);
            fileUploadRepository.delete(fileUpload);
        } catch (RuntimeException e) {
            throw new RuntimeException("File not found. Error: " + e.getMessage());
        }
    }

    @Override
    public List<FileUpload> getAllFiles() {
        try {
            return fileUploadRepository.findAll();
        } catch (RuntimeException e) {
            throw new RuntimeException("Files not found. Error: " + e.getMessage());
        }
    }

    @Override
    public void putFile(String fileName, String newNameFile) {
        try {
            FileUpload fileUpload = fileUploadRepository.findByFileName(fileName);
            fileUpload.setFileName(newNameFile);
            fileUploadRepository.save(fileUpload);
        } catch (RuntimeException e) {
            throw new RuntimeException("File not found. Error: " + e.getMessage());
        }
    }
}