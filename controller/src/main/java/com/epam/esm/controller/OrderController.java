package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.exeption.AppException;
import com.epam.esm.exeption.ErrorCode;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.epam.esm.controller.AuthController.ADMIN;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Class RestController represent rest api which allows to perform operations on order.
 */
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Method for read all orders
     *
     * @param page
     * @param size
     * @return status body
     */
    @GetMapping
    public ResponseEntity<List<OrderDto>> readAll(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                                  @RequestParam(value = "size", required = false, defaultValue = "5") int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.
                getAuthorities()
                .stream()
                .anyMatch(r -> r.getAuthority().equals(ADMIN))) {
            List<OrderDto> orders = orderService.readAll(page, size);
            for (OrderDto order : orders) {
                addLinks(order);
            }
            return ResponseEntity.status(HttpStatus.OK).body(orders);
        } else {
            List<OrderDto> orders = orderService.readAll(page, size);
            for (OrderDto order : orders) {
                addLinksForSimpleUser(order);
            }
            return ResponseEntity.status(HttpStatus.OK).body(orders);
        }
    }

    /**
     * Method for read orders
     *
     * @param userId
     * @param page
     * @param size
     * @return status body
     */
    @GetMapping(params = {"userId"})
    public ResponseEntity<List<OrderDto>> readUserOrders(@RequestParam Long userId,
                                                         @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                                         @RequestParam(value = "size", required = false, defaultValue = "5") int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.
                getAuthorities()
                .stream()
                .anyMatch(r -> r.getAuthority().equals(ADMIN))) {
            List<OrderDto> orders = orderService.readUserOrders(userId, page, size);
            for (OrderDto order : orders) {
                addLinks(order);
            }
            return ResponseEntity.status(HttpStatus.OK).body(orders);
        } else {
            List<OrderDto> orders = orderService.readUserOrders(userId, page, size);
            for (OrderDto order : orders) {
                addLinksForSimpleUser(order);
            }
            return ResponseEntity.status(HttpStatus.OK).body(orders);
        }
    }

    /**
     * Method for read by id
     *
     * @param id
     * @return status body
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> readById(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.
                getAuthorities()
                .stream()
                .anyMatch(r -> r.getAuthority().equals(ADMIN))) {
            OrderDto order = orderService.read(id);
            addLinks(order);
            return ResponseEntity.status(HttpStatus.OK).body(order);
        } else {
            OrderDto order = orderService.read(id);
            addLinksForSimpleUser(order);
            return ResponseEntity.status(HttpStatus.OK).body(order);
        }
    }

    /**
     * Method for create order
     * @param
     * @param
     * @return status body
     */

    @PostMapping
    public ResponseEntity<OrderDto> create(@RequestParam Long userId,
                                           @RequestParam Long certificateId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.create(userId, certificateId));
    }

    /**
     * Method for delete orders
     * @param id
     * @return status body
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (orderService.read(id) == null) {
            throw new AppException(ErrorCode.ORDER_NOT_FOUND, new Object());
        }
        orderService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    /**
     * Method for all links
     * @param orderDto
     */
    private void addLinks(OrderDto orderDto) {
        orderDto.add(linkTo(methodOn(OrderController.class).readById(orderDto.getId())).withSelfRel());
        orderDto.add(linkTo(methodOn(OrderController.class).create(0L, 0L)).withRel("order link -> create"));
        orderDto.add(linkTo(methodOn(OrderController.class).delete(orderDto.getId())).withRel("order link -> delete"));
        orderDto.add(linkTo(methodOn(GiftCertificateController.class).readById(orderDto.getCertificate().getCertificate_id())).withRel("gift certificate link -> read"));
        orderDto.add(linkTo(methodOn(GiftCertificateController.class).create(orderDto.getCertificate())).withRel("gift certificate link -> create"));
        orderDto.add(linkTo(methodOn(GiftCertificateController.class).update(new GiftCertificateDto(), orderDto.getCertificate().getCertificate_id())).withRel("gift certificate link -> update"));
        orderDto.add(linkTo(methodOn(GiftCertificateController.class).delete(orderDto.getCertificate().getCertificate_id())).withRel("gift certificate link -> delete"));
        orderDto.add(linkTo(methodOn(UserController.class).readById(orderDto.getUser().getId())).withRel("user link -> get"));
    }

    /**
     * Method for create links for simple role users
     *
     * @param orderDto
     */
    private void addLinksForSimpleUser(OrderDto orderDto) {
        orderDto.add(linkTo(methodOn(OrderController.class).readById(orderDto.getId())).withSelfRel());
        orderDto.add(linkTo(methodOn(OrderController.class).create(0L, 0L)).withRel("order link -> create"));
        orderDto.add(linkTo(methodOn(GiftCertificateController.class).readById(orderDto.getCertificate().getCertificate_id())).withRel("gift certificate link -> read"));
        orderDto.add(linkTo(methodOn(UserController.class).readById(orderDto.getUser().getId())).withRel("user link -> read"));
    }
}