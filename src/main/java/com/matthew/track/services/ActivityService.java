package com.matthew.track.services;

import java.util.List;

import com.matthew.track.exceptions.ActivityNotFoundException;
import com.matthew.track.models.Activity;
import com.matthew.track.repos.ActivityRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {

    @Autowired
    private final ActivityRepo activityRepo;

    public ActivityService(ActivityRepo activityRepo) {
        this.activityRepo = activityRepo;
    }

    public Activity saveActivity(Activity activity) {
        return activityRepo.save(activity);
    }

    public Activity getActivityById(Long id) {
        return activityRepo.findActivityById(id)
                .orElseThrow(() -> new ActivityNotFoundException("Activity by id " + id + " was not found"));
    }

    public List<Activity> getAllActivities() { return activityRepo.findAll(); }

    public void deleteActivity(Long id) {
        activityRepo.deleteById(id);
    }
}
