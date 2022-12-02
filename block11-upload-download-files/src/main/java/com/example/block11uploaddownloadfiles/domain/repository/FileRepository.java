package com.example.block11uploaddownloadfiles.domain.repository;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileRepository {
    void save(MultipartFile file);

    Resource findByName(String fileName);

    void setPath(String path);
}
