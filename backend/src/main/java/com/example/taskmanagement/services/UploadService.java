package com.example.taskmanagement.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Service
public class UploadService {

    @Value("${google.firebase.storage.bucket-name}")
    private String bucketName;

    private File convertToFile(MultipartFile multipartFile, String fileName) {
        File file = new File(fileName);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(multipartFile.getBytes());
            fos.close();
        } catch (Exception e) {
            return null;
        }
        return file;
    }

    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public String uploadFile(File file, String fileName) throws IOException {

        // Create Blob
        BlobId blobId = BlobId.of(this.bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();

        // Set Firebase Google Credential
        InputStream inputStream = UploadService.class.getClassLoader().getResourceAsStream("firebase.json");
        Credentials credentials = GoogleCredentials.fromStream(inputStream);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

        storage.create(blobInfo, Files.readAllBytes(file.toPath()));

        final String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/"
                + this.bucketName
                + "/o/%s?alt=media";

        return String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));

    }

    public String upload(MultipartFile multipartFile) {
        try {
            String fileName = multipartFile.getOriginalFilename();
            String fileExtension = getFileExtension(fileName);

            fileName = UUID.randomUUID().toString().concat(fileExtension);

            File file = convertToFile(multipartFile, fileName);

            String uploadURL = uploadFile(file, fileName);

            file.delete();

            return uploadURL;
        } catch (Exception e) {
            return null;
        }
    }

}
