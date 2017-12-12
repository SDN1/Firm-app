package com.mentormate.service.impl;

import com.mentormate.dto.FirmProfitDTO;
import com.mentormate.entity.Firm;
import com.mentormate.entity.Orders;
import com.mentormate.repository.FirmRepository;
import com.mentormate.repository.OrderRepository;
import com.mentormate.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.*;

import static java.time.LocalDate.parse;

@Transactional
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    FirmRepository firmRepository;

    @Override
    public List<Orders> getAllOrders() {
        List<Orders> orders = new ArrayList<Orders>();
        orderRepository.findAll().forEach(orders::add);
        return orders;
    }

    @Override
    public Page<Orders> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public Orders getOrder(Integer id) {
        Orders order = orderRepository.findOne(id);
        if(order == null) {
            throw new EntityNotFoundException("Operation get failed. Order with id=" + id + " Not Found!");
        }
        else {
            return order;
        }
    }

    @Override
    public void addOrder(Orders order) {
        Firm firm = firmRepository.findOne(order.getFirm().getFirmId());
        if(firm == null) {
            throw new EntityNotFoundException("Operation add failed. Firm with id=" + order.getFirm().getFirmId() + " Not Found!");
        }
        else {
            order.setFirm(firm);
            orderRepository.save(order);
        }
    }

    @Override
    public void updateOrder(Integer id, Orders order) {
        Orders orderExists = orderRepository.findOne(id);
        Firm firm = firmRepository.findOne(order.getFirm().getFirmId());

        if(orderExists == null) {
            throw new EntityNotFoundException("Operation update failed. Order with id=" + id + " Not Found!");
        }
        else if(firm == null){
            throw new EntityNotFoundException("Operation update failed. Given Firm  id=" + order.getFirm().getFirmId() + " Not Found!");
        }
        else {
            order.setOrderId(id);
            order.setFirm(firm);
            orderRepository.save(order);
        }
    }

    @Override
    public void deleteOrder(Integer id) {
        Orders order = orderRepository.findOne(id);

        if(order == null) {
            throw new EntityNotFoundException("Operation delete failed. Order with id=" + id + " Not Found!");
        }
        else {
            orderRepository.delete(id);
        }
    }

    @Override
    public List<Orders> getSalesOrdersForFirm(Integer firmId) {
        return orderRepository.findAllByFirm_FirmIdAndOrderTypeTrue(firmId);
    }

    @Override
    public List<Orders> getPurchasesOrdersForFirm(Integer firmId) {
        return orderRepository.findAllByFirm_FirmIdAndOrderTypeFalse(firmId);
    }

    @Override
    public String getProfitForFirmBetweenDates(FirmProfitDTO firmProfitDTO){
        Integer firmId = firmProfitDTO.getFirmId();
        if(!firmRepository.exists(firmId)) {
            throw new EntityNotFoundException("Firm with id=" + firmId + " Not found.");
        }
        Date startDate = firmProfitDTO.getStartDate();
        Date endDate = firmProfitDTO.getEndDate();

        List<Orders> sales = orderRepository.findByDateBetweenAndOrderTypeTrueAndFirm_FirmId(startDate, endDate, firmId);
        List<Orders> purchases = orderRepository.findByDateBetweenAndOrderTypeFalseAndFirm_FirmId(startDate, endDate, firmId);
        float sumOfSales = 0;
        float sumOfPurchases = 0;

        for (int i = 0; i < sales.size(); i++) {
           sumOfSales += sales.get(i).getDealPrice();
        }
        for (int j = 0; j < purchases.size(); j++) {
            sumOfPurchases += purchases.get(j).getDealPrice();
        }
        float result = sumOfSales - sumOfPurchases;
        return "The profit of firm on period " + String.valueOf(startDate) + " - " + String.valueOf(endDate) + " is " + String.valueOf(result) + "$";
    }
}
