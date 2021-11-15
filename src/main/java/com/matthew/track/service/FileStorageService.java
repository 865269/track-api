package com.matthew.track.service;

import com.matthew.track.exceptions.FileStorageException;
import com.matthew.track.exceptions.MyFileNotFoundException;
import com.matthew.track.model.UploadedFile;
import com.matthew.track.properties.FileStorageProperties;
import com.matthew.track.repo.FileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * File upload code from https://www.callicoder.com/spring-boot-file-upload-download-rest-api-example/
 */
@Service
public class FileStorageService {


    private final Path fileStorageLocation;

    @Autowired
    private final FileRepo fileRepo;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties, FileRepo fileRepo) {

        this.fileRepo = fileRepo;

        String contextPath = Paths.get("").toAbsolutePath().normalize().toString();
        this.fileStorageLocation = Paths.get(contextPath + fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        // Normalize file firstName
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's firstName contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same firstName)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

    public UploadedFile saveUploadedFile(UploadedFile uploadedFile) {
        return fileRepo.save(uploadedFile);
    }


}
