package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class responsible for tag-related operations
 */
@Service
public interface TagService {
    /**
     * Method for create tagDTO
     * @param tagDto
     * @return tag dto
     */
    TagDto create(TagDto tagDto);

    /**
     * Method for read tag dto
     * @param id
     * @return tag dto
     */
    TagDto read(Long id);

    /**
     * Method for read all tag service
     * @param page
     * @param size
     * @return list of tagdto
     */
    List<TagDto> readAll(int page, int size);

    /**
     * Method for read tag dto by name
     * @param name
     * @return tag dto
     */
    TagDto readByName(String name);

    /**
     * Method for detele tag
     * @param id
     */
    void delete(Long id);

    /**
     * Method for read most widely ised tag
     * @return tag dto
     */
    TagDto readMostWidelyUsedTag();
}