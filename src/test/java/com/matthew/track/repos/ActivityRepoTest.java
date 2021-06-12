package com.matthew.track.repos;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ActivityRepoTest {

    @Autowired
    private ActivityRepo underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }
}
