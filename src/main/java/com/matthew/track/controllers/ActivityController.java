package com.matthew.track.controllers;

import com.matthew.track.models.Activity;
import com.matthew.track.models.UploadFileResponse;
import com.matthew.track.services.ActivityService;
import com.matthew.track.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.ServletContext;
import java.util.List;


@RestController
@RequestMapping("/activities")
public class ActivityController {

    @Autowired
    private final ActivityService activityService;

    @Autowired
    private final FileStorageService fileStorageService;

    @Autowired
    ServletContext context;

    public ActivityController(ActivityService activityService, FileStorageService fileStorageService) {
        this.activityService = activityService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Activity> getActivityById(@PathVariable("id") Long id) {
        Activity activity = activityService.getActivityById(id);
        return new ResponseEntity<>(activity, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Activity>> getAllActivies() {
        List<Activity> activities = activityService.getAllActivities();
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Activity> addActivity(@RequestBody Activity activity) {
        Activity newActivity = activityService.saveActivity(activity);
        return new ResponseEntity<>(newActivity, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteActivity(@PathVariable("id") Long id) {
        activityService.deleteActivity(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/uploadFile/{id}")
    public ResponseEntity<UploadFileResponse> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("id") Long id) {

        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        System.out.println(fileDownloadUri);

        Activity activity = activityService.getActivityById(id);
        activity.setImageUrl(fileDownloadUri);
        activity = activityService.saveActivity(activity);

        UploadFileResponse uploadFileResponse = new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());

        return new ResponseEntity<>(uploadFileResponse, HttpStatus.OK);
    }

}
