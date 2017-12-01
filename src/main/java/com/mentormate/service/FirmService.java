package com.mentormate.service;

import com.mentormate.dto.BalanceTransferDTO;
import com.mentormate.entity.Firm;
import javassist.NotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.PersistenceException;
import java.sql.SQLException;
import java.util.List;

public interface FirmService {
    List<Firm> getAllFirms();
    Firm getFirm(Integer id) throws NotFoundException;
    void addFirm(Firm firm) throws NotFoundException, DataIntegrityViolationException ;
    void updateFirm(Integer id, Firm firm) throws NotFoundException;
    void deleteFirm(Integer id) throws NotFoundException;

    List<Firm> getActiveFirms();
    List<Firm> getActiveFirms(Pageable pageable);
    List<Firm> getInactiveFirms();
    List<Firm> getInactiveFirms(Pageable pageable);

    List<Firm> getFirmsWithBalanceGreaterThan(Float money);
    List<Firm> getFirmsWithBalanceGreaterThan(Float money, Pageable pageable);
    List<Firm> getFirmsWithBalanceLessThan(Float money);
    List<Firm> getFirmsWithBalanceLessThan(Float money, Pageable pageable);

    List<Firm> getFirmsWithNameContains(String namePart);
    void transferMoney(BalanceTransferDTO balanceTransferDTO) throws NotFoundException;
}
