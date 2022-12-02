package com.example.block11uploaddownloadfiles.infrastructure.mapper;

import com.example.block11uploaddownloadfiles.domain.entity.FileMetadata;
import com.example.block11uploaddownloadfiles.infrastructure.dto.FileOutputDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FileMapper {
    FileMapper INSTANCE = Mappers.getMapper(FileMapper.class);

    FileOutputDto fileMetadataToFileOutputDto(FileMetadata fileMetadata);
}
