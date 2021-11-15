package com.matthew.track.model.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Activity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -7684008074968425893L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    private String title;
    private String description;
    private String imageUrl;

    @Getter(AccessLevel.NONE)
    @OneToMany(mappedBy = "activity", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Event> events = new ArrayList<>();

    @JsonIgnore
    public List<Event> getEvents() {
        return this.events;
    }

    public void addEvent(Event event) {
        event.setActivity(this);
        this.events.add(event);
    }

}
