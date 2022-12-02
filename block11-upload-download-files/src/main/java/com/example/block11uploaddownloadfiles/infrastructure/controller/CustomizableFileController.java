package com.example.block11uploaddownloadfiles.infrastructure.controller;

import com.example.block11uploaddownloadfiles.application.FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@AllArgsConstructor
public class CustomizableFileController {

    private FileService fileService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void upload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        fileService.saveFile(file);
    }

    @GetMapping("setPath")
    public void setPath(@RequestParam String path) {
        fileService.changePath(path);
    }
}
