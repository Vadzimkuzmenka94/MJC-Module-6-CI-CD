package com.epam.esm.dao;

import com.epam.esm.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * Interface {@code UserDAO} describes abstract behavior for working with users table in database.
 */
public interface UserDao {
    /**
     * Method for read users
     * @param page
     * @param size
     * @return list of users
     */
    List<User> read(int page, int size);

    /**
     * Method for read users by id
     * @param id
     * @return users
     */
    Optional<User> readById(Long id);

    /**
     * Method for create user
     * @param user
     * @return user
     */
    User create(User user);
}