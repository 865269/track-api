package com.matthew.track.repo;

import com.matthew.track.model.UploadedFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepo extends JpaRepository<UploadedFile, Long> {

}
