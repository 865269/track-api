package com.matthew.track.filestorage;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepo extends JpaRepository<UploadedFile, Long> {

}
