package com.mentormate.controller;

import com.mentormate.dto.FirmProfitDTO;
import com.mentormate.entity.Orders;
import com.mentormate.service.impl.OrderServiceImpl;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class OrderController {
    @Autowired
    OrderServiceImpl orderService;

    @GetMapping("/orders")
    public List<Orders> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping(value = "/orders-pageable")
    public Page<Orders> getAllOrdersPageable(Pageable pageable) {
        return orderService.getAllOrders(pageable);
    }

    @GetMapping("/orders/{id}")
    public Orders getOrder(@PathVariable Integer id) {
        return orderService.getOrder(id);
    }

    @PostMapping(value = "/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public void addOrder(@Valid @RequestBody Orders order) throws NotFoundException {
        orderService.addOrder(order);
    }

    @PutMapping(value = "/orders/{id}")
    public void updateOrder(@Valid @RequestBody Orders order, @PathVariable Integer id) throws NotFoundException {
        orderService.updateOrder(id, order);
    }

    @DeleteMapping(value = "/orders/{id}")
    public void deleteFirm(@PathVariable Integer id) throws NotFoundException {
        orderService.deleteOrder(id);
    }

    @GetMapping("/orders/{id}/sales-orders")
    public List<Orders> getSalesOrdersForFirm(@PathVariable Integer id) {
        return orderService.getSalesOrdersForFirm(id);
    }

    @GetMapping("/orders/{id}/purchases-orders")
    public List<Orders> getPurchasesOrdersForFirm(@PathVariable Integer id) {
        return orderService.getPurchasesOrdersForFirm(id);
    }

    @PostMapping(value = "/orders/profit-between")
    public String getProfitForFirmBetweenDates(@Valid @RequestBody FirmProfitDTO firmProfitDTO) throws NotFoundException {
        return orderService.getProfitForFirmBetweenDates(firmProfitDTO);
    }
}
