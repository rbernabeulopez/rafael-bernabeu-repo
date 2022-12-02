package com.example.block11uploaddownloadfiles.application;

import com.example.block11uploaddownloadfiles.domain.entity.FileMetadata;
import com.example.block11uploaddownloadfiles.domain.exception.FileNotFoundException;
import com.example.block11uploaddownloadfiles.domain.repository.FileMetadataRepository;
import com.example.block11uploaddownloadfiles.domain.repository.FileRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService {
    private FileMetadataRepository fileMetadataRepository;

    private FileRepository fileRepository;

    @Override
    public FileMetadata saveFile(MultipartFile file) {
        Objects.requireNonNull(file.getOriginalFilename());
        log.info("Saving file '{}'", file.getOriginalFilename());

        fileRepository.save(file);

        Optional<FileMetadata> optionalFileMetadata = fileMetadataRepository.findByFileName(file.getOriginalFilename());
        FileMetadata metadata = new FileMetadata();
        if(optionalFileMetadata.isPresent()) {
            metadata = optionalFileMetadata.get();
        } else {
            metadata.setFileName(file.getOriginalFilename());
        }
        metadata.setUploadDate(LocalDate.now());
        return fileMetadataRepository.save(metadata);
    }

    @Override
    public Resource findById(long id) {
        log.info("Searching file with id {}", id);

        Optional<FileMetadata> optionalMetadata = fileMetadataRepository.findById(id);
        if(optionalMetadata.isEmpty()) {
            throw new FileNotFoundException("Requested file data not stored in database");
        }
        FileMetadata metadata = optionalMetadata.get();
        return this.findByName(metadata.getFileName());
    }

    @Override
    public Resource findByName(String name) {
        return fileRepository.findByName(name);
    }

    @Override
    public void changePath(String path) {
        fileRepository.setPath(path);
    }
}
