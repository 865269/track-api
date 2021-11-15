package com.matthew.track.controller;

import com.matthew.track.model.activity.ActivityDTO;
import com.matthew.track.service.impl.ActivityServiceImpl;
import com.matthew.track.model.activity.Event;
import com.matthew.track.model.UploadedFile;
import com.matthew.track.service.FileStorageService;
import com.matthew.track.model.activity.Activity;
import com.matthew.track.service.inter.ActivityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.ServletContext;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/activities")
public class ActivityController {

    private final ActivityService activityService;

    private final FileStorageService fileStorageService;

    @Autowired
    ServletContext context;

    @Autowired
    private ModelMapper modelMapper;

    public ActivityController(ActivityServiceImpl activityService, FileStorageService fileStorageService) {
        this.activityService = activityService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityDTO> getActivityById(@PathVariable("id") Long id) {
        Activity activity = activityService.getActivityById(id);
        return new ResponseEntity<>(convertToDto(activity), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ActivityDTO>> getAllActivities() {
        List<Activity> activities = activityService.getAllActivities();
        return new ResponseEntity<>(activities.stream().map(this::convertToDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ActivityDTO> addActivity(@RequestBody ActivityDTO activityDTO) throws ParseException {
        activityDTO.setCreated(new Date());
        Activity newActivity = convertToEntity(activityDTO);
        newActivity = activityService.saveActivity(newActivity);
        return new ResponseEntity<>(convertToDto(newActivity), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<ActivityDTO> updateActivity(@RequestBody ActivityDTO activityDTO) throws ParseException {
        Activity updateActivity = convertToEntity(activityDTO);
        updateActivity = activityService.saveActivity(updateActivity);
        return new ResponseEntity<>(convertToDto(updateActivity), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteActivity(@PathVariable("id") Long id) {
        activityService.deleteActivity(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/uploadFile/{id}")
    public ResponseEntity<UploadedFile> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("id") Long id) {

        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/downloadFile/")
                .path(fileName)
                .toUriString();

        System.out.println(fileDownloadUri);

        Activity activity = activityService.getActivityById(id);
        activity.setImageUrl(fileDownloadUri);
        activity = activityService.saveActivity(activity);

        UploadedFile uploadedFile = new UploadedFile(fileName, fileDownloadUri, file.getContentType(), file.getSize());
        uploadedFile = fileStorageService.saveUploadedFile(uploadedFile);

        return new ResponseEntity<>(uploadedFile, HttpStatus.OK);
    }

    @PostMapping("/{activityId}/event/add")
    public ResponseEntity<Event> addEvent(@PathVariable("activityId") long activityId, @RequestBody Event event) {

        event.setCreated(new Date());

        Activity activity = activityService.getActivityById(activityId);
        activity.addEvent(event);

        activityService.saveActivity(activity);

        return new ResponseEntity<>(event, HttpStatus.CREATED);
    }

    private ActivityDTO convertToDto(Activity activity) {
        ActivityDTO activityDTO = modelMapper.map(activity, ActivityDTO.class);
        activityDTO.setEvents(activity.getEvents());

        return activityDTO;
    }

    private Activity convertToEntity(ActivityDTO activityDTO) throws ParseException {
        Activity activity = modelMapper.map(activityDTO, Activity.class);

        return activity;
    }

    @GetMapping("/page/{page}/{size}")
    public ResponseEntity<Page<ActivityDTO>> getPageOfActivities(@PathVariable("page") int page, @PathVariable("size") int size) {
        Pageable currentPage = PageRequest.of(page, size);
        Page<Activity> activities = activityService.getPageOfActivities(currentPage);

        Page<ActivityDTO> dtoPage = activities.map(new Function<Activity, ActivityDTO>() {
            @Override
            public ActivityDTO apply(Activity activity) {
                return convertToDto(activity);
            }
        });

        return new ResponseEntity<>(dtoPage, HttpStatus.OK);
    }

}
