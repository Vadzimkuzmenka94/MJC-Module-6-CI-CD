package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.Order;
import com.epam.esm.mapper.impl.OrderMapper;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao;
    private final OrderMapper mapper;
    private final UserService userService;
    private final GiftCertificateService certificateService;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, OrderMapper orderMapper, UserService userService, GiftCertificateService certificateService) {
        this.orderDao = orderDao;
        this.mapper = orderMapper;
        this.certificateService = certificateService;
        this.userService = userService;
    }

    @Override
    public OrderDto read(Long id) {
        Optional<Order> optionalOrder = orderDao.readById(id);
        return mapper.mapToDto(optionalOrder.get());
    }

    @Override
    public List<OrderDto> readAll(int page, int size) {
        List<Order> orders = orderDao.read(page, size);
        return orders.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> readUserOrders(Long userId, int page, int size) {
        List<Order> orders = orderDao.readUserOrders(userId, page, size);
        return orders.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());       }

    @Transactional
    @Override
    public OrderDto create(Long userId, Long certificateId) {
        GiftCertificateDto certificateDto = certificateService.read(certificateId).get(0);
        UserDto user = userService.readById(userId);
        OrderDto orderDto = new OrderDto();
        orderDto.setCertificate(certificateDto);
        orderDto.setCost(certificateDto.getPrice());
        orderDto.setUser(user);
        Order order = mapper.mapToEntity(orderDto);
        return mapper.mapToDto(orderDao.create(order));
    }

    @Transactional
    @Override
    public void delete(Long id) {
        orderDao.delete(id);
    }
}