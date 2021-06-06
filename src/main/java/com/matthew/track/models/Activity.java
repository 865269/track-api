package com.matthew.track.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

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

    @OneToMany(mappedBy = "activity")
    private List<Event> events;

    public void addEvent(Event event) {
        event.setActivity(this);
        this.events.add(event);
    }

}
