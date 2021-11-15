package com.matthew.track.service.impl;

import java.util.List;

import com.matthew.track.exceptions.ActivityNotFoundException;
import com.matthew.track.model.activity.Activity;

import com.matthew.track.repo.ActivityRepo;
import com.matthew.track.service.inter.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepo activityRepo;

    public ActivityServiceImpl(ActivityRepo activityRepo) {
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
