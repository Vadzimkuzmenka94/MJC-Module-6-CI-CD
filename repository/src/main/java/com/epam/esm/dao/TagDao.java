package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.Optional;
import java.util.Set;

/**
 * Interface {@code TagDAO} describes abstract behavior for working with tag table in database.
 */
public interface TagDao {
    /**
     * Method for read tags
     * @param page
     * @param size
     * @return tags
     */
    Set<Tag> read(int page, int size);

    /**
     * Method for read tags by id
     * @param id
     * @return tag
     */
    Optional<Tag> readById(Long id);

    /**
     * Method for read tag by name
     * @param name
     * @return tag
     */
    Optional<Tag> readByName(String name);

    /**
     * Method for create tag
     * @param obj
     * @return tag
     */
    Tag create(Tag obj);

    /**
     * Method for delete tag
     * @param id
     */
    void delete(Long id);

    /**
     * Method for read most widely user tag
     * @return tag
     */
    Tag readMostWidelyUsedTag();
}