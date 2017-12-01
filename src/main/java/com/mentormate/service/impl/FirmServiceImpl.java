package com.mentormate.service.impl;

import com.mentormate.dto.BalanceTransferDTO;
import com.mentormate.entity.Address;
import com.mentormate.entity.Contact;
import com.mentormate.entity.Firm;
import com.mentormate.repository.AddressRepository;
import com.mentormate.repository.ContactRepository;
import com.mentormate.repository.FirmRepository;
import com.mentormate.repository.PartRepository;
import com.mentormate.service.FirmService;
import javassist.NotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
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

    public Page<Firm> getAllFirms(Pageable pageable) {
        return firmRepository.findAll(pageable);
    }

    @Override
    public Firm getFirm(Integer id) throws NotFoundException {
        Firm firm = new Firm();
        firm = firmRepository.findOne(id);
        if(firm == null) {
            throw new NotFoundException("Operation get failed. Firm with id=" + id + " Not Found!");
        }
        else {
            return firm;
        }
    }

    @Override
    public void addFirm(Firm firm) throws NotFoundException, DataIntegrityViolationException  {
        Address address = addressRepository.findOne(firm.getAddress().getAddress_id());
        Contact contact = contactRepository.findOne(firm.getContact().getContact_id());

        if(address == null || contact == null) {
            throw new NotFoundException("Operation add failed. Address with id=" +
                    firm.getAddress().getAddress_id() + " or Contact with id=" +
                    firm.getContact().getContact_id() + " Not Found!");
        }
        else {
            try {
                firm.setAddress(address);
                firm.setContact(contact);
                firmRepository.save(firm);
            } catch (DataIntegrityViolationException  ex) {
                throw new DataIntegrityViolationException(ex.getMostSpecificCause().getMessage());
              }
        }
    }

    @Override
    public void updateFirm(Integer id, Firm firm) throws NotFoundException {
        Address address = addressRepository.findOne(firm.getAddress().getAddress_id());
        Contact contact = contactRepository.findOne(firm.getContact().getContact_id());
        boolean firmExists = firmRepository.exists(id);

        if(!firmExists) {
            throw new NotFoundException("Operation update failed. Firm with id=" + id + " Not Found!");
        }
        else if(address == null || contact == null) {
            throw new NotFoundException("Operation update failed. Given Address id=" + firm.getAddress().getAddress_id() + " or Given Contact id=" + firm.getContact().getContact_id() + " Not Found!");
        }
        else {
            firm.setAddress(address);
            firm.setContact(contact);
            firm.setFirmId(id);
            firmRepository.save(firm);
        }
    }

    @Override
    public void deleteFirm(Integer id) throws NotFoundException {
        Firm firm = firmRepository.findOne(id);
        if(firm == null) {
            throw new NotFoundException("Operation delete failed. Firm with id=" + id + " Not Found!");
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
    public List<Firm> getActiveFirms(Pageable pageable) {
        return firmRepository.findAllByIsActiveTrue(pageable);
    }

    @Override
    public List<Firm> getFirmsWithBalanceGreaterThan(Float money, Pageable pageable) {
        return firmRepository.findByBalanceGreaterThan(money, pageable);
    }

    @Override
    public List<Firm> getFirmsWithBalanceLessThan(Float money, Pageable pageable) {
        return firmRepository.findByBalanceLessThan(money, pageable);
    }

    @Override
    public List<Firm> getInactiveFirms() {
        return firmRepository.findAllByIsActiveFalse();
    }

    @Override
    public List<Firm> getInactiveFirms(Pageable pageable) {
        return firmRepository.findAllByIsActiveFalse(pageable);
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

    @Override
    public void transferMoney(BalanceTransferDTO balanceTransferDTO) throws NotFoundException {
        Integer idSenderFirm = balanceTransferDTO.getaCompanyGivesMoney();
        Integer idReceiverFirm = balanceTransferDTO.getaCompanyReceivesMoney();

        Firm senderFirm = firmRepository.findOne(idSenderFirm);
        Firm receiverFirm = firmRepository.findOne(idReceiverFirm);

        if(senderFirm == null || receiverFirm == null) {
            throw new NotFoundException("Operation transfer failed. Firm sender with id=" + idSenderFirm + "or" + "Firm receiver with id=" + idReceiverFirm +  " Not Found!");
        }
        else {
            Float quantity = balanceTransferDTO.getQuantity();
            boolean isHaveEnoughMoney = (senderFirm.getBalance() - quantity) >= 0 ? true : false;
            if(isHaveEnoughMoney) {
                senderFirm.subtractionToBalance(quantity);
                receiverFirm.addToBalance(quantity);
                firmRepository.save(receiverFirm);
                firmRepository.save(senderFirm);
            }
            else {
                throw new NotFoundException("Operation transfer failed. Firm sender with id=" + idSenderFirm + " not have enough money!");
            }
        }
    }
}
