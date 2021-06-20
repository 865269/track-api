package com.matthew.track.repos;

import java.util.List;
import java.util.Optional;


import com.matthew.track.models.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepo extends JpaRepository<Activity, Long> { }
