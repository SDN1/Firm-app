package com.mentormate.controller;

import com.mentormate.dto.BalanceTransferDTO;
import com.mentormate.dto.TransferBalanceDTO;
import com.mentormate.entity.Person;
import com.mentormate.service.impl.PersonServiceImpl;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {
    @Autowired
    private PersonServiceImpl personService;

    @GetMapping("/persons")
    public List<Person> getAllPerson() {
        return personService.getAllPersons();
    }

    @GetMapping("/persons/{id}")
    public Person getPerson(@PathVariable Integer id) throws NotFoundException {
        return personService.getPerson(id);
    }

    @PostMapping(value = "/persons")
    public void addPerson(@RequestBody Person person) throws NotFoundException {
        personService.addPerson(person);
    }

    @PutMapping(value = "/persons/{id}")
    public void updatePerson(@RequestBody Person person, @PathVariable Integer id) throws NotFoundException {
        personService.updatePerson(id, person);
    }

    @DeleteMapping(value = "/persons/{id}")
    public void deletePerson(@PathVariable Integer id) throws NotFoundException {
        personService.deletePerson(id);
    }

    @PostMapping(value = "persons/transfer-balance")
    public void transferBalanceBetweenFirm(@RequestBody TransferBalanceDTO transferBalanceDTO) throws NotFoundException {
        personService.transferBalanceTo(transferBalanceDTO);
    }
}
