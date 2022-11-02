package com.epam.esm.dao;

import com.epam.esm.entity.Order;

import java.util.List;
import java.util.Optional;

/**
 * Interface {@code OrderDAO} describes abstract behavior for working with order table in database.
 */
public interface OrderDao {
    /**
     * Method for read order by id
     * @param id
     * @return order
     */
    Optional<Order> readById(Long id);

    /**
     * Method for read orders
     * @param page
     * @param size
     * @return list of orders
     */
    List<Order> read(int page, int size);

    /**
     * Method for read user orders
     * @param userId
     * @param page
     * @param size
     * @return list of orders
     */
    List<Order> readUserOrders(Long userId, int page, int size);

    /**
     * Method for create order
     * @param order
     * @return order
     */
    Order create(Order order);

    /**
     * Method for delete order
     * @param id
     */
    void delete(Long id);
}