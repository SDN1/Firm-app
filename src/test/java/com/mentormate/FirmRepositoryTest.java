package com.mentormate;

import com.mentormate.entity.Address;
import com.mentormate.entity.Contact;
import com.mentormate.entity.Firm;
import com.mentormate.repository.AddressRepository;
import com.mentormate.repository.ContactRepository;
import com.mentormate.repository.FirmRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class FirmRepositoryTest {

    @Autowired
    private FirmRepository firmRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Before
    public void setUp() throws Exception {
        Firm firm = new Firm(1, "Exodus", "867576867", true, 1000.0F);
        Firm secondFirm = new Firm(2, "Ponus", "746564756", true, 1000.0F);
        Address firmAddress = new Address(1, "Bulgaria", "Popovo", "Donska", "77", "7800");
        Contact firmContact = new Contact(1, "089666666", "mentormate@gmail.com", "http://ssfdsfds.com", "http://ssfdsfds.com", "http://ssfdsfds.com" );

        firm.setAddress(firmAddress);
        firm.setContact(firmContact);
        secondFirm.setAddress(firmAddress);
        secondFirm.setContact(firmContact);

        this.addressRepository.save(firmAddress);
        this.contactRepository.save(firmContact);
        this.firmRepository.save(firm);
        this.firmRepository.save(secondFirm);

        assertNotNull(firm.getFirmId());
        assertNotNull(secondFirm.getFirmId());
    }

    @Test
    public void fetchData() {
        Firm firmA = firmRepository.findByBulstat("867576867");

        assertNotNull(firmA);
        assertEquals("Exodus", firmA.getName());
        assertEquals(true, firmA.getActive());
        assertEquals(1000F, firmA.getBalance(), 0.0002); //3rd param, maximum difference between expected and given

        Address address = firmA.getAddress();
        assertEquals(Integer.valueOf(1), address.getAddress_id());
        assertEquals("Bulgaria", address.getCountry());
        assertEquals("Popovo", address.getCity());
        assertEquals("Donska", address.getStreet());
        assertEquals("77", address.getStreetNumber());
        assertEquals("7800", address.getPostalCode());

        Contact contact = firmA.getContact();
        assertEquals(Integer.valueOf(1), contact.getContact_id());
        assertEquals("089666666", contact.getPhoneNumber());
        assertEquals("mentormate@gmail.com", contact.getEmailAddress());
        assertEquals("http://ssfdsfds.com", contact.getWebSiteURL());
        assertEquals("http://ssfdsfds.com", contact.getLinkedInURL());
        assertEquals("http://ssfdsfds.com", contact.getFacebookURL());

        Iterable<Firm> firms = firmRepository.findAll();
        int count = 0;

        for(Firm f : firms) {
            count++;
        }
        assertEquals(count, 2);
    }
}
