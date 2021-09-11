package com.matthew.track.activity;

import java.util.List;

import com.matthew.track.exceptions.ActivityNotFoundException;
import com.matthew.track.activity.Activity;
import com.matthew.track.activity.ActivityRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        return activityRepo.findById(id)
                .orElseThrow(() -> new ActivityNotFoundException("Activity by id " + id + " was not found"));
    }

    public List<Activity> getAllActivities() { return activityRepo.findAll(); }

    public void deleteActivity(Long id) {
        activityRepo.deleteById(id);
    }

    public Page<Activity> getPageOfActivities(Pageable pageable) {
        return activityRepo.findAll(pageable);
    }
}
