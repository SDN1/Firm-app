package com.mentormate.service;

import com.mentormate.dto.PersonDTO;
import com.mentormate.dto.TransferBalanceDTO;
import com.mentormate.entity.Person;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PersonService {
    List<Person> getAllPersons();
    Page<Person> getAllPersons(Pageable pageable);
    Person getPerson(Integer id);
    void addPerson(PersonDTO personDTO);
    void updatePerson(Integer id, Person person);
    void deletePerson(Integer id);

    void transferBalanceTo(TransferBalanceDTO transferBalanceDTO);
}
