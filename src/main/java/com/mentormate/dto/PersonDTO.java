package com.mentormate.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class PersonDTO {
    @NotNull(message = "firstName field can not be null or empty.")
    private String firstName;

    @NotNull(message = "middleName field can not be null or empty.")
    private String middleName;

    @NotNull(message = "lastName field can not be null or empty.")
    private String lastName;

    @NotNull(message = "gender field can not be null or empty.")
    private String gender;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    @NotNull(message = "birthday field can not be null or empty.")
    private Date birthday;

    @Min(1)
    @NotNull(message = "addressId field can not be null or empty.")
    private Integer addressId;

    @Min(1)
    @NotNull(message = "contactId field can not be null or empty.")
    private Integer contactId;

    public PersonDTO() {
    }

    public PersonDTO(String firstName, String middleName, String lastName, String gender, Date birthday, Integer addressId, Integer contactId) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthday = birthday;
        this.addressId = addressId;
        this.contactId = contactId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
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
