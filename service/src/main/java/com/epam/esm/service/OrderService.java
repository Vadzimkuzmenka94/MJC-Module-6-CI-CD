package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;

import java.util.List;

/**
 * Service class for processing order-related operations
 */
public interface OrderService {
    /**
     * Method for read order dto by id
     * @param id
     * @return order dto
     */
    OrderDto read(Long id);

    /**
     * Method for read all orders by id
     * @param page
     * @param size
     * @return list of order dto
     */
    List<OrderDto> readAll(int page, int size);

    /**
     * Method for read users and orders
     * @param userId
     * @param page
     * @param size
     * @return list of order dto
     */
    List<OrderDto> readUserOrders(Long userId, int page, int size);

    /**
     * Method for create order dto
     * @param userId
     * @param certificateId
     * @return order dto
     */
    OrderDto create(Long userId, Long certificateId);

    /**
     * Method for delete order
     * @param id
     */
    void delete(Long id);
}