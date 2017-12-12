package com.mentormate.service;

import com.mentormate.dto.FirmProfitDTO;
import com.mentormate.entity.Orders;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    List<Orders> getAllOrders();
    Page<Orders> getAllOrders(Pageable pageable);
    Orders getOrder(Integer id);
    void addOrder(Orders order);
    void updateOrder(Integer id, Orders order);
    void deleteOrder(Integer id);

    List<Orders> getSalesOrdersForFirm(Integer firmId);
    List<Orders> getPurchasesOrdersForFirm(Integer firmId);
    String getProfitForFirmBetweenDates(FirmProfitDTO firmProfitDTO);
}
