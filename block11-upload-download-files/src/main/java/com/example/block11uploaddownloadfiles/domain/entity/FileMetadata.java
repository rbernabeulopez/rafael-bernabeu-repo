package com.example.block11uploaddownloadfiles.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@ToString
public class FileMetadata {
    @Id
    @GeneratedValue
    private long id;

    private String fileName;

    private LocalDate uploadDate;
}
