package com.mentormate.controller;

import com.mentormate.dto.PersonDTO;
import com.mentormate.dto.TransferBalanceDTO;
import com.mentormate.entity.Person;
import com.mentormate.service.impl.PersonServiceImpl;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PersonController {
    @Autowired
    private PersonServiceImpl personService;

    @GetMapping("/persons")
    public List<Person> getAllPerson() {
        return personService.getAllPersons();
    }

    @GetMapping("/persons-pageable")
    public Page<Person> getAllPersonsPageable(Pageable pageable) {
        return personService.getAllPersons(pageable);
    }

    @GetMapping("/persons/{id}")
    public Person getPerson(@PathVariable Integer id) throws NotFoundException {
        return personService.getPerson(id);
    }

    @PostMapping(value = "/persons")
    @ResponseStatus(HttpStatus.CREATED)
    public void addPerson(@Valid @RequestBody PersonDTO personDTO) throws NotFoundException {
        personService.addPerson(personDTO);
    }

    @PutMapping(value = "/persons/{id}")
    public void updatePerson(@Valid @RequestBody Person person, @PathVariable Integer id) throws NotFoundException {
        personService.updatePerson(id, person);
    }

    @DeleteMapping(value = "/persons/{id}")
    public void deletePerson(@PathVariable Integer id) throws NotFoundException {
        personService.deletePerson(id);
    }

    @PostMapping(value = "persons/transfer-balance")
    public void transferBalanceBetweenFirm(@Valid @RequestBody TransferBalanceDTO transferBalanceDTO) throws NotFoundException {
        personService.transferBalanceTo(transferBalanceDTO);
    }
}
