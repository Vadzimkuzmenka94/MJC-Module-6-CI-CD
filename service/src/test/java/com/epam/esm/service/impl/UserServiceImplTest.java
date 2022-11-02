package com.epam.esm.service.impl;

import com.epam.esm.dto.UserDto;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private static final int FIRST_PAGE = 0;
    private static final int LAST_PAGE = 5;
    private static final long CORRECT_ID = 1;
    private UserDto userDto;
    private List<UserDto> userDtos;

    @Mock
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(userService);
        userDto = new UserDto();
        userDtos = new ArrayList<>();
        userDtos.add(userDto);
    }

    @Test
    void readAllTest() {
        when(userService.readAll(FIRST_PAGE, LAST_PAGE)).thenReturn(userDtos);
        List<UserDto> actualUser = userService.readAll(FIRST_PAGE, LAST_PAGE);
        Assertions.assertEquals(userDtos, actualUser);
    }

    @Test
    void readAll_shouldThrow_USER_NOT_FOUND() {
        doThrow(new AppException(ErrorCode.USER_NOT_FOUND, new Object())).when(userService).readAll(FIRST_PAGE, LAST_PAGE);
        assertThrows(AppException.class, () -> {
            userService.readAll(FIRST_PAGE, LAST_PAGE);
        });
    }

    @Test
    public void readByIdTest() {
        when(userService.readById(anyLong())).thenReturn(userDto);
        UserDto actualUser = userService.readById(CORRECT_ID);
        Assertions.assertEquals(userDto, actualUser);
    }

    @Test
    void readById_shouldThrow_USER_NOT_FOUND() {
        doThrow(new AppException(ErrorCode.USER_NOT_FOUND, new Object())).when(userService).readById(userDto.getId());
        assertThrows(AppException.class, () -> {
            userService.readById(userDto.getId());
        });
    }

    @Test
    void create() {
        when(userService.create(new UserDto())).thenReturn(userDto);
        UserDto savedUser = userService.create(new UserDto());
        Assertions.assertEquals(userDto, savedUser);
    }
}