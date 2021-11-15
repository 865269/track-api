package com.matthew.track.service.inter;

import com.matthew.track.exceptions.ActivityNotFoundException;
import com.matthew.track.model.activity.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ActivityService {

    Activity saveActivity(Activity activity);

    Activity getActivityById(Long id);

    List<Activity> getAllActivities();

    void deleteActivity(Long id);

    Page<Activity> getPageOfActivities(Pageable pageable);
}
