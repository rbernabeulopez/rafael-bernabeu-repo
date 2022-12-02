package com.example.block11uploaddownloadfiles.infrastructure.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class FileOutputDto {
    private long id;

    private String fileName;

    private LocalDate uploadDate;
}
