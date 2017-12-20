package com.mentormate.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "firm")
@JsonIgnoreProperties({"parts", "hibernateLazyInitializer", "handler"})
public class Firm implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "firm_id")
    private Integer firmId;

    @Column(name = "name")
    @NotNull(message = "name field can not be null.")
    private String name;

    @Column(name = "bulstat")
    @Pattern(regexp="[\\d]{9}")
    @NotNull(message = "bulstat field can not be null.")
    private String bulstat;

    @Column(name = "is_active")
    @NotNull(message = "isActive field can not be null.")
    private Boolean isActive;

    @Column(name = "balance")
    @DecimalMin(value = "0.00")
    @NotNull(message = "balance field can not be null.")
    private Float balance;

    @ManyToOne
    @JoinColumn(name = "address_id")
    @NotNull(message = "address field can not be null.")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "contact_id")
    @NotNull(message = "contact field can not be null.")
    private Contact contact;

    @JsonIgnore
    @OneToMany(mappedBy = "firm")
    private List<Part> parts = new ArrayList<Part>(0);

    public Firm() {

    }

    public Firm(Integer firmId, String name, String bulstat, Boolean isActive, Float balance, Address address, Contact contact, List<Part> parts) {
        this.firmId = firmId;
        this.name = name;
        this.bulstat = bulstat;
        this.isActive = isActive;
        this.balance = balance;
        this.address = address;
        this.contact = contact;
        this.parts = parts;
    }

    public Firm(Integer firmId, String name, String bulstat, Boolean isActive, Float balance) {
        this.firmId = firmId;
        this.name = name;
        this.bulstat = bulstat;
        this.isActive = isActive;
        this.balance = balance;
    }
    public Firm(String name, String bulstat, Boolean isActive, Float balance) {
        this.firmId = firmId;
        this.name = name;
        this.bulstat = bulstat;
        this.isActive = isActive;
        this.balance = balance;
    }

    public Integer getFirmId() {
        return firmId;
    }

    public void setFirmId(Integer firmId) {
        this.firmId = firmId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBulstat() {
        return bulstat;
    }

    public void setBulstat(String bulstat) {
        this.bulstat = bulstat;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public void addToBalance(Float quantity) {
        this.balance += quantity;
    }

    public void subtractionToBalance(Float quantity) {
        this.balance -= quantity;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public List<Part> getParts() {
        return parts;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }
}
