package com.matthew.track.services;

import com.matthew.track.exceptions.ActivityNotFoundException;
import com.matthew.track.models.Activity;
import com.matthew.track.repos.ActivityRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ActivityServiceTest {


    @Mock private ActivityRepo activityRepo;
    private ActivityService underTest;

    @BeforeEach
    void setUp() {
        underTest = new ActivityService(activityRepo);
    }

    @Test
    void canSaveActivity() {
        // given
        Activity activity = new Activity();
        activity.setTitle("Test title");
        activity.setDescription("Test Description");
        activity.setCreated(new Date());

        //when
        underTest.saveActivity(activity);

        // then
        ArgumentCaptor<Activity> activityArgumentCaptor = ArgumentCaptor.forClass(Activity.class);

        verify(activityRepo).save(activityArgumentCaptor.capture());

        Activity capturedActivity = activityArgumentCaptor.getValue();

        assertThat(capturedActivity).isEqualTo(activity);
    }

    @Test
    void canGetActivityById() {
        // given
        long ACTIVITY_ID = 1;

        Activity activity = new Activity();
        activity.setTitle("Test title");
        activity.setDescription("Test Description");
        activity.setCreated(new Date());
        activity.setId(ACTIVITY_ID);

        // when
        when(activityRepo.findById(ACTIVITY_ID)).thenReturn(Optional.of(activity));

        // then
        assertThat(underTest.getActivityById(ACTIVITY_ID)).isEqualTo(activity);
    }

    @Test
    void canNotGetActivityById() {
        // given
        long ACTIVITY_ID = 1;

        given(activityRepo.findById(ACTIVITY_ID))
                .willThrow(new ActivityNotFoundException("Activity by id " + ACTIVITY_ID + " was not found"));

        // when
        // then
        assertThatThrownBy(() -> underTest.getActivityById(ACTIVITY_ID))
                .isInstanceOf(ActivityNotFoundException.class)
                .hasMessageContaining("Activity by id " + ACTIVITY_ID + " was not found");
    }

    @Test
    void canGetAllActivities() {
        // when
        underTest.getAllActivities();
        //then
        verify(activityRepo).findAll();
    }

    @Test
    void canDeleteActivity() {
        // given
        long ACTIVITY_ID = 1;

        Activity activity = new Activity();
        activity.setTitle("Test title");
        activity.setDescription("Test Description");
        activity.setCreated(new Date());
        activity.setId(ACTIVITY_ID);

        // when
        underTest.deleteActivity(ACTIVITY_ID);

        // then
        verify(activityRepo).deleteById(ACTIVITY_ID);
    }


}
