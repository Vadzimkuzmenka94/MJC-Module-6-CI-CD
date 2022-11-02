package com.epam.esm.service;

import com.epam.esm.dto.UserDto;

import java.util.List;

public interface UserService {
    /**
     * Method for read all users
     * @param page
     * @param size
     * @return list of userDto
     */
    List<UserDto> readAll(int page, int size);

    /**
     * Method for read users by id
     * @param id
     * @return user dto
     */
    UserDto readById(Long id);

    /**
     * Method for create user dto
     * @param newUser
     * @return user dto
     */
    UserDto create(UserDto newUser);
}