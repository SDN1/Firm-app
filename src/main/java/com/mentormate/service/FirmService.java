package com.mentormate.service;

import com.mentormate.dto.FirmDTO;
import com.mentormate.entity.Firm;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface FirmService {
    List<Firm> getAllFirms();
    Page<Firm> getAllFirms(Pageable pageable);
    Firm getFirm(Integer id);
    void addFirm(FirmDTO firmDTO);
    void updateFirm(Integer id, Firm firm);
    void deleteFirm(Integer id);

    List<Firm> getActiveFirms();
    List<Firm> getInactiveFirms();
    List<Firm> getFirmsWithBalanceGreaterThan(Float money);
    List<Firm> getFirmsWithBalanceLessThan(Float money);
    List<Firm> getFirmsWithNameContains(String namePart);
}
