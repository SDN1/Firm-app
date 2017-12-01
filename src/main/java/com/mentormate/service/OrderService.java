package com.mentormate.service;

import com.mentormate.dto.FirmProfitDTO;
import com.mentormate.entity.Orders;
import javassist.NotFoundException;

import java.util.List;

public interface OrderService {
    List<Orders> getAllOrders();
    Orders getOrder(Integer id) throws NotFoundException;
    void addOrder(Orders order) throws NotFoundException;
    void updateOrder(Integer id, Orders order) throws NotFoundException;
    void deleteOrder(Integer id) throws NotFoundException;

    List<Orders> getSalesOrdersForFirm(Integer firmId);
    List<Orders> getPurchasesOrdersForFirm(Integer firmId);

    String getProfitForFirmBetweenDates(FirmProfitDTO firmProfitDTO) throws NotFoundException;
}
