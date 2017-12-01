package com.mentormate.service.impl;


import com.mentormate.dto.TransferBalanceDTO;
import com.mentormate.entity.*;
import com.mentormate.repository.*;
import com.mentormate.service.PersonService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
    public Person getPerson(Integer id) throws NotFoundException {
        Person person = personRepository.findOne(id);
        if(person == null) {
            throw new NotFoundException("Operation get failed. Person with id=" + id + " Not Found!");
        }
        else {
            return person;
        }
    }

    @Override
    public void addPerson(Person person) throws NotFoundException {
        Address address = addressRepository.findOne(person.getAddress().getAddress_id());
        Contact contact = contactRepository.findOne(person.getContact().getContact_id());
        if(address == null || contact == null) {
            throw new NotFoundException("Operation add failed. Address with id=" + person.getAddress().getAddress_id() + " or Contact with id=" + person.getContact().getContact_id() + " Not Found!");
        }
        else {
            person.setAddress(address);
            person.setContact(contact);
            personRepository.save(person);
        }
    }

    @Override
    public void updatePerson(Integer id, Person person) throws NotFoundException {
        boolean isPersonExists = personRepository.exists(id);
        if(!isPersonExists) {
            throw new NotFoundException("Operation add failed. Person with id=" + id + " Not Found!");
        }

        Address address = addressRepository.findOne(person.getAddress().getAddress_id());
        Contact contact = contactRepository.findOne(person.getContact().getContact_id());
        if(address == null || contact == null) {
            throw new NotFoundException("Operation update failed. Address with id=" + person.getAddress().getAddress_id() + " or Contact with id=" + person.getContact().getContact_id() + " Not Found!");
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
    public void deletePerson(Integer id) throws NotFoundException
    {
        Person person = personRepository.findOne(id);
        if(person == null)
        {
            throw new NotFoundException("Operation delete failed. Person with id=" + id + " Not Found!");
        }
        else
        {
            List<Part> parts = partRepository.findAllByPerson_PersonId(id); // Find all firms belong to person with id
            int size = parts.size(); // Get him sizes

            if(size == 1 && parts.get(0).getPercents() == 100) // This person  is have only one firm? and is he is the only owner?
            {
                Firm firm = parts.get(0).getFirm();
                firm.setActive(false);
                firmRepository.saveAndFlush(firm);
            }
            else
            {
                for(int i = 0; i < size; i++)
                {
                    int firmId = parts.get(i).getFirm().getFirmId(); // Get id of firm from parts of person candidate to delete
                    List<Part> ownersForFirm = partRepository.findAllByFirm_FirmId(firmId); // Find all owners for firmId
                    if(ownersForFirm.size() == 1) // if this firm have only 1 owner
                    {
                        Firm firm = ownersForFirm.get(0).getFirm();
                        firm.setActive(false);
                        firmRepository.saveAndFlush(firm);
                    }
                    else
                    {
                        float percentsForFirm = parts.get(i).getPercents(); // Get percents on person for firmId
                        int numOfHeirs = ownersForFirm.size() - 1;
                        float sharedPercents = percentsForFirm / numOfHeirs; // Divide them equally on the number of candidates
                        // Find all these people and add that percentage.

                        for(int j = 0; j < ownersForFirm.size(); j++)
                        {
                            int idOfHeir = ownersForFirm.get(j).getPerson().getPersonId();
                            if(idOfHeir != id)
                            {
                                Part part = ownersForFirm.get(j);
                                part.addToPercents(sharedPercents);
                                partRepository.saveAndFlush(part);
                            }
                        }
                    }
                }
            }
            personRepository.delete(person);
        }
    }

    @Override
    public void transferBalanceTo(TransferBalanceDTO transferBalanceDTO) throws NotFoundException {
        Firm firmSender = firmRepository.findByBulstat(transferBalanceDTO.getBulstaOfCompanySenderMoney());
        Firm firmReceiver = firmRepository.findByBulstat(transferBalanceDTO.getBulstatOfCompanyReceivesMoney());
        float quantity = transferBalanceDTO.getQuantity();
        boolean isSenderFirmHaveEnoughMoney = (firmSender.getBalance() - quantity) >= 0 ? true : false;

        if(!isSenderFirmHaveEnoughMoney) {
            throw new NotFoundException("Operation transfer failed. Firm sender with bulstat="
                    + transferBalanceDTO.getBulstaOfCompanySenderMoney() + " not have enough money!");
        }

        Person personSender = personRepository.findOne(transferBalanceDTO.getIdOfpersonSenderMoney());
        Person personReceiver = personRepository.findOne(transferBalanceDTO.getIdOfPersonReceivesMoney());

        if(personSender == null){
            throw new NotFoundException("Operation transfer failed. Sender person with id="
                    + transferBalanceDTO.getIdOfpersonSenderMoney()+ " Not Found!");
        }

        if(personReceiver == null){
            throw new NotFoundException("Operation transfer failed. Receiver person with id="
                    + transferBalanceDTO.getIdOfPersonReceivesMoney()+ " Not Found!");
        }

        boolean isSenderPersonHaveSuchFirm = personSender
                .getParts()
                .stream()
                .anyMatch(p -> p.getFirm().getBulstat().equals(transferBalanceDTO.getBulstaOfCompanySenderMoney()));

        boolean isReceiverPersonHaveSuchFirm = personReceiver
                .getParts()
                .stream()
                .anyMatch(p -> p.getFirm().getBulstat().equals(transferBalanceDTO.getBulstatOfCompanyReceivesMoney()));

        if(!isSenderPersonHaveSuchFirm) {
            throw  new NotFoundException("Sender person with id="
                    + transferBalanceDTO.getIdOfpersonSenderMoney()
                    + "not have such firm with bulstat=" + transferBalanceDTO.getBulstaOfCompanySenderMoney());
        }

        if(!isReceiverPersonHaveSuchFirm) {
            throw  new NotFoundException("Receiver person with id="
                    + transferBalanceDTO.getIdOfPersonReceivesMoney()
                    + "not have such firm with bulstat=" + transferBalanceDTO.getBulstaOfCompanySenderMoney());
        }

        firmSender.subtractionToBalance(quantity);
        firmReceiver.addToBalance(quantity);
        firmRepository.saveAndFlush(firmSender);
        firmRepository.saveAndFlush(firmReceiver);
    }
}
