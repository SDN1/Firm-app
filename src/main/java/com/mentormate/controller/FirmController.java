package com.mentormate.controller;

import com.mentormate.dto.BalanceTransferDTO;
import com.mentormate.entity.Firm;
import com.mentormate.service.impl.FirmServiceImpl;
import javassist.NotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PersistenceException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
public class FirmController {
    @Autowired
    private FirmServiceImpl firmService;

    @GetMapping("/firms")
    public List<Firm> getAllFirms() {
        return firmService.getAllFirms();
    }

    @GetMapping(value = "/firms-pageable")
    Page<Firm> firmsPageable(Pageable pageable) {
        return firmService.getAllFirms(pageable);
    }

    @GetMapping("/firms/{id}")
    public Firm getFirm(@PathVariable Integer id) throws NotFoundException {
        return firmService.getFirm(id);
    }

    @PostMapping(value = "/firms")
    public void addFirm(@RequestBody Firm firm) throws NotFoundException, DataIntegrityViolationException  {
        firmService.addFirm(firm);
    }

    @PutMapping(value = "/firms/{id}")
    public void updateFirm(@RequestBody Firm firm, @PathVariable Integer id) throws NotFoundException {
        firmService.updateFirm(id, firm);
    }

    @DeleteMapping(value = "/firms/{id}")
    public void deleteFirm(@PathVariable Integer id) throws NotFoundException {
        firmService.deleteFirm(id);
    }

    @GetMapping(value = "/firms/actives")
    public List<Firm> getActiveFirms() {
        return firmService.getActiveFirms();
    }

    @GetMapping(value = "/firms/inactives")
    public List<Firm> getInactiveFirms() {
        return firmService.getInactiveFirms();
    }

    @GetMapping(value = "/firms/money-great-than/{quantity}", consumes = "application/json")
    public List<Firm> getFirmsWithBalanceGreaterThan(@PathVariable Float quantity) {
        return firmService.getFirmsWithBalanceGreaterThan(quantity);
    }

    @GetMapping(value = "/firms/money-less-than/{quantity}", consumes = "application/json")
    public List<Firm> getFirmsWithBalanceLessThan(@PathVariable Float quantity) {
        return firmService.getFirmsWithBalanceLessThan(quantity);
    }

    @GetMapping(value = "/firms/search-by-name", consumes = "application/json")
    public List<Firm> getFirmsWithNameContains(@RequestParam("content") String content) {
        return firmService.getFirmsWithNameContains(content);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/firms/transfer", consumes = "application/json")
    public void transferBalance(@RequestBody BalanceTransferDTO balanceTransferDTO) throws NotFoundException {
        firmService.transferMoney(balanceTransferDTO);
    }
}

