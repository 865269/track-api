package com.matthew.track.repos;

import com.matthew.track.models.Activity;
import com.matthew.track.models.UploadedFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepo extends JpaRepository<UploadedFile, Long> {

}
