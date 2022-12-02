package com.example.block11uploaddownloadfiles.application;

import com.example.block11uploaddownloadfiles.domain.entity.FileMetadata;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    FileMetadata saveFile(MultipartFile file);

    Resource findById(long id);

    Resource findByName(String name);

    void changePath(String path);
}
