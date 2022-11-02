package com.epam.esm.mapper;

/**
 * Interface with methods for mapping
 * @param <T>
 * @param <E>
 */
public interface Mapper<T, E> {
    /**
     * Method for mapping to DTO
     * @param entity
     * @return E
     */
    E mapToDto(T entity);

    /**
     * Method for mappting map to entity
     * @param dto
     * @return T
     */
    T mapToEntity(E dto);
}