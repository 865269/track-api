package com.matthew.track.repos;

import com.matthew.track.models.Activity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class ActivityRepoTest {

    @Autowired
    private ActivityRepo underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldGetActivityById() {
        // given
        Activity activity = new Activity();
        activity.setTitle("Test title");
        activity.setDescription("Test Description");
        activity.setCreated(new Date());
        underTest.save(activity);

        long id = activity.getId();

        // when
        Optional<Activity> expected = underTest.findActivityById(id);


        //then
        assertThat(expected.get()).hasFieldOrPropertyWithValue("id", id);
    }

    @Test
    void itShouldGetNotActivityById() {
        // given
        long id = 1;

        // when
        Optional<Activity> expected = underTest.findActivityById(id);

        //then
        assertThat(expected).isEmpty();
    }
}
