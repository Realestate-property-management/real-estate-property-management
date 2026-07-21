package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.integration.S3Service;

@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = "*")
public class FileUploadController {

    @Autowired
    private S3Service s3Service;

    // Upload File
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {

        return s3Service.uploadFile(file);

    }

    // Download File
    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileName) {

        byte[] data = s3Service.downloadFile(fileName);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + fileName)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(data);
    }

    // Delete File
    @DeleteMapping("/delete/{fileName}")
    public String deleteFile(@PathVariable String fileName) {

        s3Service.deleteFile(fileName);

        return "File deleted successfully.";

    }

}