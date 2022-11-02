package com.epam.esm.service.impl;

import com.epam.esm.dto.OrderDto;
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
class OrderServiceImplTest {
    private static final long CORRECT_ID = 1;
    private static final long INCORRECT_ID = 100;
    private static final int FIRST_PAGE = 0;
    private static final int LAST_PAGE = 5;
    private OrderDto orderDto;
    private List<OrderDto> orderDtos;

    @Mock
    private OrderServiceImpl orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(orderService);
        orderDto = new OrderDto();
        orderDtos = new ArrayList<>();
        orderDtos.add(orderDto);
    }

    @Test
    public void readbyIdTest() {
        when(orderService.read(anyLong())).thenReturn(orderDto);
        OrderDto actualOrder = orderService.read(CORRECT_ID);
        Assertions.assertEquals(orderDto, actualOrder);
    }

    @Test
    void readById_shouldThrow_USER_NOT_FOUND(){
        doThrow(new AppException(ErrorCode.USER_NOT_FOUND, new Object())).when(orderService).read(orderDto.getId());
        assertThrows(AppException.class,()->{
            orderService.read(orderDto.getId());
        });
    }

    @Test
    public void readAllTest() {
        when(orderService.readAll(FIRST_PAGE,LAST_PAGE)).thenReturn(orderDtos);
        List<OrderDto> actualOrder = orderService.readAll(FIRST_PAGE,LAST_PAGE);
        Assertions.assertEquals(orderDtos, actualOrder);
    }

    @Test
    void readAll_shouldThrow_ORDER_NOT_FOUND(){
        doThrow(new AppException(ErrorCode.ORDER_NOT_FOUND, new Object())).when(orderService).readAll(FIRST_PAGE,LAST_PAGE);
        assertThrows(AppException.class,()->{
            orderService.readAll(FIRST_PAGE,LAST_PAGE);
        });
    }

    @Test
    public void saveTest() {
        when(orderService.create(CORRECT_ID, CORRECT_ID)).thenReturn(orderDto);
        OrderDto savedOrder = orderService.create(CORRECT_ID, CORRECT_ID);
        Assertions.assertEquals(orderDto, savedOrder);
    }

    @Test
    void saveTest_shouldThrow_TAG_NAME_IS_NULL(){
        doThrow(new AppException(ErrorCode.ORDER_NO_CONTENT, new Object())).when(orderService).create(null, null);
        assertThrows(AppException.class,()->{
            orderService.create(null, null);
        });
    }

    @Test
    public void deleteTest_shouldThrow_TAG_NAME_IS_NULL() {
        doThrow(new AppException(ErrorCode.ORDER_NOT_FOUND, new Object())).when(orderService).delete(INCORRECT_ID);
        assertThrows(AppException.class,()->{
            orderService.delete(INCORRECT_ID);
        });
    }
}