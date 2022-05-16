package ru.netology.cloudstorage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.cloudstorage.entity.FileUpload;
import ru.netology.cloudstorage.service.StorageService;

import java.util.List;

@RestController
@RequestMapping("/cloud")
public class StorageController {

    private final StorageService storageService;

    @Autowired
    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/file")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            storageService.uploadFile(file);
            return ResponseEntity.ok(new String("Upload file successfully: "
                    + file.getOriginalFilename()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new String("Could not upload the file: "
                    + file.getOriginalFilename()));
        }
    }

    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable("filename") String fileName) {
        FileUpload file = storageService.getFile(fileName);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=\"" + file.getFileName() + "\"")
                .body(new ByteArrayResource(file.getFileData()));
    }

    @GetMapping("/delete/{filename:.+}")
    public ResponseEntity<String> geleteFile(@PathVariable("filename") String fileName) {
        try {
            storageService.deleteFile(fileName);
            return ResponseEntity.ok(new String("Delete file successfully: "
                    + fileName));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new String("Could not delete the file: "
                    + fileName));
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<FileUpload>> gelAllFiles() {
        try {
            return ResponseEntity.ok(storageService.getAllFiles());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/putfile")
    public ResponseEntity<String> putFile(@RequestParam("namefile") String nameFile,
                                          @RequestParam("newnamefile") String newNameFile) {
        try {
            storageService.putFile(nameFile, newNameFile);
            return ResponseEntity.ok(new String("Edit file name successfully: "
                    + newNameFile));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new String("Could not edit the file name: "
                    + nameFile));
        }
    }
}