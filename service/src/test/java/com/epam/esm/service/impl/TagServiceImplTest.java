package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exeption.AppException;
import com.epam.esm.exeption.ErrorCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class TagServiceImplTest {
    private TagDto tagDto;
    private List<TagDto> tagDtos;
    private static final long CORRECT_ID = 1;
    private static final long INCORRECT_ID = 100;
    private static final String CORRECT_NAME = "Tag";
    private static final int FIRST_PAGE = 0;
    private static final int LAST_PAGE = 5;

    @Mock
    private TagServiceImpl tagService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(tagService);
        tagDto = new TagDto(CORRECT_ID, CORRECT_NAME);
        tagDtos = new ArrayList<>();
        tagDtos.add(tagDto);
    }

    @Test
    public void readbyIdTest() {
        when(tagService.read(anyLong())).thenReturn(tagDto);
        TagDto actualTag = tagService.read(CORRECT_ID);
        Assertions.assertEquals(tagDto, actualTag);
    }

    @Test
    void readById_shouldThrow_TAG_NOT_FOUND(){
        doThrow(new AppException(ErrorCode.TAG_NOT_FOUND, new Object())).when(tagService).read(tagDto.getTag_id());
        assertThrows(AppException.class,()->{
            tagService.read(tagDto.getTag_id());
        });
    }

    @Test
    public void readByNameTest() {
        when (tagService.readByName(anyString())).thenReturn(tagDto);
        TagDto actualTag = tagService.readByName(CORRECT_NAME);
        Assertions.assertEquals(tagDto, actualTag);
    }

    @Test
    void readByName_shouldThrow_TAG_NOT_FOUND(){
        doThrow(new AppException(ErrorCode.TAG_NOT_FOUND, new Object())).when(tagService).readByName(tagDto.getTag_name());
        assertThrows(AppException.class,()->{
            tagService.readByName(tagDto.getTag_name());
        });
    }

    @Test
    public void readAllTest() {
        when(tagService.readAll(FIRST_PAGE,LAST_PAGE)).thenReturn(tagDtos);
        List<TagDto> actualTags = tagService.readAll(FIRST_PAGE,LAST_PAGE);
        Assertions.assertEquals(tagDtos, actualTags);
    }

    @Test
    void readAll_shouldThrow_TAG_NOT_FOUND(){
        doThrow(new AppException(ErrorCode.TAG_NOT_FOUND, new Object())).when(tagService).readAll(FIRST_PAGE,LAST_PAGE);
        assertThrows(AppException.class,()->{
            tagService.readAll(FIRST_PAGE,1);
        });
    }

   @Test
    public void saveTest() {
        when(tagService.create(any(TagDto.class))).thenReturn(tagDto);
        TagDto savedTag = tagService.create(tagDto);
        Assertions.assertEquals(tagDto, savedTag);
    }

    @Test
    void saveTest_shouldThrow_TAG_NAME_IS_NULL(){
        doThrow(new AppException(ErrorCode.TAG_NAME_IS_NULL, new Object())).when(tagService).create(new TagDto());
        assertThrows(AppException.class,()->{
            tagService.create(new TagDto());
        });
    }

    @Test
    public void deleteTest_shouldThrow_TAG_NAME_IS_NULL() {
        doThrow(new AppException(ErrorCode.TAG_NAME_IS_NULL, new Object())).when(tagService).delete(INCORRECT_ID);
        assertThrows(AppException.class,()->{
            tagService.delete(INCORRECT_ID);
        });
    }
}
