package com.mentormate.service.impl;


import com.mentormate.dto.PersonDTO;
import com.mentormate.dto.TransferBalanceDTO;
import com.mentormate.entity.*;
import com.mentormate.repository.*;
import com.mentormate.service.PersonService;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private PartRepository partRepository;
    @Autowired
    private FirmRepository firmRepository;

    @Override
    public List<Person> getAllPersons() {
        List<Person> persons = new ArrayList<Person>();
        personRepository.findAll().forEach(persons::add);
        return persons;
    }

    @Override
    public Page<Person> getAllPersons(Pageable pageable) {
        return personRepository.findAll(pageable);
    }

    @Override
    public Person getPerson(Integer id) {
        Person person = personRepository.findOne(id);
        if(person == null) {
            throw new EntityNotFoundException("Operation get failed. Person with id=" + id + " Not Found!");
        }
        else {
            return person;
        }
    }

    @Override
    public void addPerson(PersonDTO personDTO){
        Address address = addressRepository.findOne(personDTO.getAddressId());
        Contact contact = contactRepository.findOne(personDTO.getContactId());
        if(address == null || contact == null) {
            throw new EntityNotFoundException("Operation add failed. Address with id=" + personDTO.getAddressId() + " or Contact with id=" + personDTO.getContactId() + " Not Found!");
        }
        else {
            Person person = new Person();
            person.setAddress(address);
            person.setContact(contact);
            BeanUtils.copyProperties(personDTO, person);
            personRepository.save(person);
        }
    }

    @Override
    public void updatePerson(Integer id, Person person) {
        boolean isPersonExists = personRepository.exists(id);
        if(!isPersonExists) {
            throw new EntityNotFoundException("Operation add failed. Person with id=" + id + " Not Found!");
        }

        Address address = addressRepository.findOne(person.getAddress().getAddress_id());
        Contact contact = contactRepository.findOne(person.getContact().getContact_id());
        if(address == null || contact == null) {
            throw new EntityNotFoundException("Operation update failed. Address with id=" + person.getAddress().getAddress_id() + " or Contact with id=" + person.getContact().getContact_id() + " Not Found!");
        }
        else {
            person.setAddress(address);
            person.setContact(contact);
            person.setPersonId(id);
            personRepository.save(person);
        }
    }

    @Transactional
    @Override
    public void deletePerson(Integer id) {
        Person person = personRepository.findOne(id);
        if(person == null) {
            throw new EntityNotFoundException("Operation delete failed. Person with id=" + id + " Not Found!");
        }
        List<Part> parts = partRepository
                .findAllByPerson_PersonId(id); // Find all firms belong to person with id

        List<Part> listOfFirmsWithOneOwner = parts
                .stream()
                .filter(p -> p.getPercents() == 100)
                .collect(Collectors.toList());

        List<Part> listOfFirmsWithManyOwners = parts
                .stream()
                .filter(p -> p.getPercents() < 100)
                .collect(Collectors.toList());

        for(Part part : listOfFirmsWithOneOwner) {
            Firm firm = part.getFirm();
            firm.setActive(false);
            firmRepository.saveAndFlush(firm);
        }
        for(Part part : listOfFirmsWithManyOwners) {
            int firmId = part.getFirm().getFirmId(); // Get part of person candidate to delete next firm id
            List<Part> ownersForFirm = partRepository
                    .findAllByFirm_FirmId(firmId); // Find all owners for firmId

            float percentsForFirm = part.getPercents(); // Get percents on person for firmId
            int numOfHeirs = ownersForFirm.size() - 1;
            float sharedPercents = percentsForFirm / numOfHeirs; // Divide them equally on the number of candidates

            for (Part part1 : ownersForFirm) {
                int idOfHeir = part1.getPerson().getPersonId();
                if(idOfHeir != id) {
                    part1.addToPercents(sharedPercents);
                    partRepository.saveAndFlush(part1);
                }
            }
        }
        personRepository.delete(person);
    }

    @Override
    public void transferBalanceTo(TransferBalanceDTO transferBalanceDTO) {
        String bulstatOfCompanySenderMoney = transferBalanceDTO.getBulstaOfCompanySenderMoney();
        String bulstatOfCompanyReceivesMoney = transferBalanceDTO.getBulstatOfCompanyReceivesMoney();
        Integer idOfPersonSenderMoney = transferBalanceDTO.getIdOfpersonSenderMoney();
        Integer idOfPersonReceivesMoney = transferBalanceDTO.getIdOfPersonReceivesMoney();


        Firm firmSender = firmRepository.findByBulstat(bulstatOfCompanySenderMoney);
        Firm firmReceiver = firmRepository.findByBulstat(bulstatOfCompanyReceivesMoney);
        if(firmSender == null) {
            throw new EntityNotFoundException("Operation transfer failed. Firm with bulstat=" + bulstatOfCompanySenderMoney + " Not Found!");
        }
        if(firmReceiver == null) {
            throw new EntityNotFoundException("Operation transfer failed. Firm with bulstat=" + bulstatOfCompanyReceivesMoney + " Not Found!");
        }

        float quantity = transferBalanceDTO.getQuantity();
        boolean isSenderFirmHaveEnoughMoney = (firmSender.getBalance() - quantity) >= 0 ? true : false;
        if(!isSenderFirmHaveEnoughMoney) {
            throw new EntityNotFoundException("Operation transfer failed. Firm sender with bulstat="
                    + bulstatOfCompanySenderMoney + " not have enough money!");
        }

        Person personSender = personRepository.findOne(idOfPersonSenderMoney);
        Person personReceiver = personRepository.findOne(idOfPersonReceivesMoney);
        if(personSender == null){
            throw new EntityNotFoundException("Operation transfer failed. Sender person with id="
                    + idOfPersonSenderMoney + " Not Found!");
        }
        if(personReceiver == null){
            throw new EntityNotFoundException("Operation transfer failed. Receiver person with id="
                    + idOfPersonReceivesMoney + " Not Found!");
        }

        boolean isSenderPersonHaveSuchFirm = personSender
                .getParts()
                .stream()
                .anyMatch(p -> p.getFirm().getBulstat().equals(bulstatOfCompanySenderMoney));

        boolean isReceiverPersonHaveSuchFirm = personReceiver
                .getParts()
                .stream()
                .anyMatch(p -> p.getFirm().getBulstat().equals(bulstatOfCompanyReceivesMoney));

        if(!isSenderPersonHaveSuchFirm) {
            throw  new EntityNotFoundException("Sender person with id="
                    + idOfPersonSenderMoney
                    + "not have such firm with bulstat=" + bulstatOfCompanySenderMoney);
        }

        if(!isReceiverPersonHaveSuchFirm) {
            throw  new EntityNotFoundException("Receiver person with id="
                    + idOfPersonReceivesMoney
                    + "not have such firm with bulstat=" + bulstatOfCompanyReceivesMoney);
        }

        firmSender.subtractionToBalance(quantity);
        firmReceiver.addToBalance(quantity);
        firmRepository.saveAndFlush(firmSender);
        firmRepository.saveAndFlush(firmReceiver);
    }
}
