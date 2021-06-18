package com.matthew.track.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.matthew.track.models.Activity;
import com.matthew.track.models.Event;
import com.matthew.track.models.Rating;
import com.matthew.track.services.ActivityService;
import com.matthew.track.services.FileStorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class ActivityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ActivityService activityService;

    @MockBean
    private FileStorageService fileStorageService;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldGetActivityById() throws Exception {

        // given
        long ACTIVITY_ID = 1;

        Activity activity = new Activity();
        activity.setId(ACTIVITY_ID);
        activity.setTitle("Test Activity");
        activity.setDescription("Test Description");
        activity.setCreated(new Date());

        // when
        when(activityService.getActivityById(ACTIVITY_ID)).thenReturn(activity);

        // then
        this.mockMvc.perform(get("/activities/"+ACTIVITY_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ACTIVITY_ID))
                .andReturn();
    }

    @Test
    void shouldGetAllActivities() throws Exception {
        // given
        long ACTIVITY_ID_ONE = 1;
        Activity activity_one = new Activity();
        activity_one.setId(ACTIVITY_ID_ONE);
        activity_one.setTitle("Test Activity");
        activity_one.setDescription("Test Description");
        activity_one.setCreated(new Date());

        long ACTIVITY_ID_TWO = 2;
        Activity activity_two = new Activity();
        activity_two.setId(ACTIVITY_ID_TWO);
        activity_two.setTitle("Test Activity");
        activity_two.setDescription("Test Description");
        activity_two.setCreated(new Date());

        List<Activity> activityList = List.of(activity_one, activity_two);

        // when
        when(activityService.getAllActivities()).thenReturn(activityList);

        // then
        this.mockMvc.perform(get("/activities/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(ACTIVITY_ID_ONE))
                .andExpect(jsonPath("$[1].id").value(ACTIVITY_ID_TWO))
                .andReturn();
    }

    @Test
    void addActivity() throws Exception {

        // given
        Activity activity = new Activity();
        activity.setTitle("Test Title");
        activity.setDescription("Test Description");
        activity.setCreated(new Date());

       objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
       ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();

       String requestJson = objectWriter.writeValueAsString(activity);

        // when
        // then
        this.mockMvc.perform(post("/activities/add")
                .contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    void updateActivity() throws Exception {

        // given
        long ACTIVITY_ID = 1;
        Activity activity = new Activity();
        activity.setTitle("Test Title");
        activity.setDescription("Test Description");
        activity.setCreated(new Date());
        activity.setId(ACTIVITY_ID);

        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();

        String requestJson = objectWriter.writeValueAsString(activity);

        // when
        when(activityService.getActivityById(ACTIVITY_ID)).thenReturn(activity);

        // then
        this.mockMvc.perform(put("/activities/update")
                .contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void deleteActivity() throws Exception {
        // given
        long ACTIVITY_ID = 1;
        Activity activity = new Activity();
        activity.setTitle("Test Title");
        activity.setDescription("Test Description");
        activity.setCreated(new Date());
        activity.setId(ACTIVITY_ID);

        // when
        when(activityService.getActivityById(ACTIVITY_ID)).thenReturn(activity);

        // then
        this.mockMvc.perform(delete("/activities/delete/"+ACTIVITY_ID))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @Disabled
    void uploadFile() {
    }

    @Test
    void addEvent() throws Exception {

        // given
        long ACTIVITY_ID = 1;
        Activity activity = new Activity();
        activity.setTitle("Test Title");
        activity.setDescription("Test Description");
        activity.setCreated(new Date());
        activity.setId(ACTIVITY_ID);

        long EVENT_ID = 1;
        Event event = new Event();
        event.setTitle("Test Title");
        event.setDescription("Test Description");
        event.setRating(Rating.EXCELLENT);
        event.setId(EVENT_ID);

        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();

        String requestJson = objectWriter.writeValueAsString(event);

        // when
        when(activityService.getActivityById(ACTIVITY_ID)).thenReturn(activity);

        // then
        this.mockMvc.perform(post("/activities/"+ACTIVITY_ID+"/event/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andReturn();
    }


}