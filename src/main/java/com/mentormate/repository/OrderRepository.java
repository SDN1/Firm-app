package com.mentormate.repository;

import com.mentormate.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Integer> {
    public List<Orders> findAllByFirm_FirmIdAndOrderTypeTrue(Integer firmId);
    public List<Orders> findAllByFirm_FirmIdAndOrderTypeFalse(Integer firmId);
    public List<Orders> findByDateBetweenAndOrderTypeTrueAndFirm_FirmId(Date startDate, Date endDate, Integer firmId);
    public List<Orders> findByDateBetweenAndOrderTypeFalseAndFirm_FirmId(Date startDate, Date endDate, Integer firmId);
}
