package com.mentormate.service.impl;

import com.mentormate.dto.FirmDTO;
import com.mentormate.entity.Address;
import com.mentormate.entity.Contact;
import com.mentormate.entity.Firm;
import com.mentormate.repository.AddressRepository;
import com.mentormate.repository.ContactRepository;
import com.mentormate.repository.FirmRepository;
import com.mentormate.repository.PartRepository;
import com.mentormate.service.FirmService;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class FirmServiceImpl implements FirmService {
    @Autowired
    private FirmRepository firmRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private PartRepository partRepository;

    @Override
    public List<Firm> getAllFirms() {
        List<Firm> firms = new ArrayList<Firm>();
        firmRepository.findAll().forEach(firms::add);
        return firms;
    }

    @Override
    public Page<Firm> getAllFirms(Pageable pageable) {
        return firmRepository.findAll(pageable);
    }

    @Override
    public Firm getFirm(Integer id)  {
        Firm firm = new Firm();
        firm = firmRepository.findOne(id);
        if(firm == null) {
            throw new EntityNotFoundException("Operation get failed. Firm with id= " + id + " Not Found!");
        }
        else {
            return firm;
        }
    }

    @Override
    public void addFirm(FirmDTO firmDTO) {
        Address address = addressRepository.findOne(firmDTO.getAddressId());
        Contact contact = contactRepository.findOne(firmDTO.getContactId());

        if(address == null || contact == null) {
            throw new EntityNotFoundException("Operation add failed. Address with id=" +
                    firmDTO.getAddressId() + " or Contact with id=" +
                    firmDTO.getContactId() + " Not Found!");
        }
        else {
            try {
                Firm firm = new Firm();
                firm.setAddress(address);
                firm.setContact(contact);
                BeanUtils.copyProperties(firmDTO, firm);
                firmRepository.save(firm);
            }
            catch (DataIntegrityViolationException ex) {
                throw new DataIntegrityViolationException(ex.getMostSpecificCause().getMessage()); // for unique bulstat
            }
        }
    }

    @Override
    public void updateFirm(Integer id, Firm firm) {
        Address address = addressRepository.findOne(firm.getAddress().getAddress_id());
        Contact contact = contactRepository.findOne(firm.getContact().getContact_id());
        boolean isFirmExist = firmRepository.exists(id);

        if(!isFirmExist) {
            throw new EntityNotFoundException("Operation update failed. Firm with id=" + id + " Not Found!");
        }
        if(address == null || contact == null) {
            throw new EntityNotFoundException("Operation update failed. Given Address id=" + firm.getAddress().getAddress_id() + " or Given Contact id=" + firm.getContact().getContact_id() + " Not Found!");
        }
        else {
            firm.setAddress(address);
            firm.setContact(contact);
            firm.setFirmId(id);
            firmRepository.save(firm);
        }
    }

    @Override
    public void deleteFirm(Integer id) {
        Firm firm = firmRepository.findOne(id);
        if(firm == null) {
            throw new EntityNotFoundException("Operation delete failed. Firm with id=" + id + " Not Found!");
        }
        else {
            firm.setActive(false);
            firmRepository.save(firm);
        }
    }

    @Override
    public List<Firm> getActiveFirms() {
        return firmRepository.findAllByIsActiveTrue();
    }

    @Override
    public List<Firm> getInactiveFirms() {
        return firmRepository.findAllByIsActiveFalse();
    }

    @Override
    public List<Firm> getFirmsWithBalanceGreaterThan(Float money) {
        return firmRepository.findAllByBalanceGreaterThan(money);
    }

    @Override
    public List<Firm> getFirmsWithBalanceLessThan(Float money) {
        return firmRepository.findAllByBalanceLessThan(money);
    }

    @Override
    public List<Firm> getFirmsWithNameContains(String namePart) {
        return firmRepository.findByNameContainsAllIgnoreCase(namePart);
    }
}
