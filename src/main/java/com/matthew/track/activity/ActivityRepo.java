package com.matthew.track.activity;


import com.matthew.track.activity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepo extends JpaRepository<Activity, Long> { }
