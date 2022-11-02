package com.epam.esm.mapper.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.Mapper;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class for mapping tags
 */
@Service
public class TagMapper implements Mapper<Tag, TagDto> {
    @Override
    public TagDto mapToDto(Tag entity) {
        TagDto tagDto = new TagDto();
        tagDto.setTag_id(entity.getTag_id());
        tagDto.setTag_name(entity.getTag_name());
        return tagDto;
    }

    @Override
    public Tag mapToEntity(TagDto dto) {
        Tag tag = new Tag();
        tag.setTag_id(dto.getTag_id());
        tag.setTag_name(dto.getTag_name());
        return tag;
    }

    public Set<TagDto> mapToListDto(Set<Tag> entities) {
        return entities.stream()
                .map(this::mapToDto)
                .collect(Collectors.toSet());
    }

    public Set<Tag> mapToListEntity(Set<TagDto> dtos) {
        return dtos.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toSet());
    }
}