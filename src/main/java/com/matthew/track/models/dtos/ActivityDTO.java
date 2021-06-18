package com.matthew.track.models.dtos;

import com.matthew.track.models.Event;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ActivityDTO {

    private long id;
    private Date created;
    private String title;
    private String description;
    private String imageUrl;
    private List<Event> events = new ArrayList<>();
}
