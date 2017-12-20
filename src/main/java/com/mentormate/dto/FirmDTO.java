package com.mentormate.dto;


import javax.validation.constraints.*;

public class FirmDTO {
    @NotNull(message = "name field can not be null or empty.")
    private String name;

    @Pattern(regexp="[\\d]{9}")
    @NotNull(message = "bulstat field can not be null or empty.")
    private String bulstat;

    @NotNull(message = "isActive field can not be null or empty.")
    private Boolean isActive;

    @DecimalMin(value = "0.00")
    @NotNull(message = "balance field can not be null or empty.")
    private Float balance;

    @Min(1)
    @NotNull(message = "addressId field can not be null or empty.")
    private Integer addressId;

    @Min(1)
    @NotNull(message = "contactId field can not be null or empty.")
    private Integer contactId;

    public FirmDTO() {
    }

    public FirmDTO(String name, String bulstat, Boolean isActive, Float balance, Integer addressId, Integer contactId) {
        this.name = name;
        this.bulstat = bulstat;
        this.isActive = isActive;
        this.balance = balance;
        this.addressId = addressId;
        this.contactId = contactId;
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

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }
}
