package com.example.block11uploaddownloadfiles.infrastructure.controller;

import com.example.block11uploaddownloadfiles.application.FileService;
import com.example.block11uploaddownloadfiles.domain.entity.FileMetadata;
import com.example.block11uploaddownloadfiles.infrastructure.dto.FileOutputDto;
import com.example.block11uploaddownloadfiles.infrastructure.mapper.FileMapper;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("file")
@AllArgsConstructor
public class FileController {
    private FileService fileService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public FileOutputDto uploadFile(@RequestParam("file") MultipartFile file) {
        FileMetadata metadata = fileService.saveFile(file);
        return FileMapper.INSTANCE.fileMetadataToFileOutputDto(metadata);
    }

    @GetMapping("{id}")
    public Resource downloadById(@PathVariable long id) {
        return fileService.findById(id);
    }

    @GetMapping("name/{name}")
    public Resource downloadByName(@PathVariable String name) {
        return fileService.findByName(name);
    }
}
