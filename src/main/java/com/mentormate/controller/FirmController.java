package com.mentormate.controller;

import com.mentormate.dto.FirmDTO;
import com.mentormate.entity.Firm;
import com.mentormate.service.impl.FirmServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class FirmController {
    @Autowired
    private FirmServiceImpl firmService;

    @GetMapping("/firms")
    public List<Firm> getAllFirms() {
        return firmService.getAllFirms();
    }

    @GetMapping(value = "/firms-pageable")
    public Page<Firm> getAllFirmsPageable(Pageable pageable) {
        return firmService.getAllFirms(pageable);
    }

    @GetMapping("/firms/{id}")
    public Firm getFirm(@PathVariable Integer id) {
        return firmService.getFirm(id);
    }

    @PostMapping(value = "/firms")
    @ResponseStatus(HttpStatus.CREATED)
    public void addFirm(@Valid @RequestBody FirmDTO firmDTO){
        firmService.addFirm(firmDTO);
    }

    @PutMapping(value = "/firms/{id}")
    public void updateFirm(@Valid @RequestBody Firm firm, @PathVariable Integer id)  {
        firmService.updateFirm(id, firm);
    }

    @DeleteMapping(value = "/firms/{id}")
    public void deleteFirm(@PathVariable Integer id) {
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

    @GetMapping(value = "/firms/money-great-than/{quantity}")
    public List<Firm> getFirmsWithBalanceGreaterThan(@PathVariable Float quantity) {
        return firmService.getFirmsWithBalanceGreaterThan(quantity);
    }

    @GetMapping(value = "/firms/money-less-than/{quantity}")
    public List<Firm> getFirmsWithBalanceLessThan(@PathVariable Float quantity) {
        return firmService.getFirmsWithBalanceLessThan(quantity);
    }

    @GetMapping(value = "/firms/search-by-name")
    public List<Firm> getFirmsWithNameContains(@RequestParam("content") String content) {
        return firmService.getFirmsWithNameContains(content);
    }
}

