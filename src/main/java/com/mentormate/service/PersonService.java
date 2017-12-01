package com.mentormate.service;

import com.mentormate.dto.TransferBalanceDTO;
import com.mentormate.entity.Person;
import javassist.NotFoundException;

import java.util.List;

public interface PersonService {
    List<Person> getAllPersons();
    Person getPerson(Integer id) throws NotFoundException;
    void addPerson(Person person) throws NotFoundException;
    void updatePerson(Integer id, Person person) throws NotFoundException;
    void deletePerson(Integer id) throws NotFoundException;

    void transferBalanceTo(TransferBalanceDTO transferBalanceDTO) throws NotFoundException;
}
