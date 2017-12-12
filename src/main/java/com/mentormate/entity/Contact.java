package com.mentormate.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "contact")
public class Contact implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "contact_id")
    private Integer contact_id;

    @NotNull(message = "phoneNumber field can not be null.")
    private String phoneNumber;

    @NotNull(message = "emailAddress field can not be null.")
    @Email(message = "This is a not valid email.")
    private String emailAddress;

    @NotNull(message = "webSiteURL field can not be null.")
    private String webSiteURL;

    @NotNull(message = "linkedInURL field can not be null.")
    private String linkedInURL;

    @NotNull(message = "facebookURL field can not be null.")
    private String facebookURL;

    public Contact() {

    }

    public Contact(Integer contact_id, String phoneNumber, String emailAddress, String webSiteURL, String linkedInURL, String facebookURL) {
        this.contact_id = contact_id;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.webSiteURL = webSiteURL;
        this.linkedInURL = linkedInURL;
        this.facebookURL = facebookURL;
    }

    public Integer getContact_id() {
        return contact_id;
    }

    public void setContact_id(Integer contact_id) {
        this.contact_id = contact_id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getWebSiteURL() {
        return webSiteURL;
    }

    public void setWebSiteURL(String webSiteURL) {
        this.webSiteURL = webSiteURL;
    }

    public String getLinkedInURL() {
        return linkedInURL;
    }

    public void setLinkedInURL(String linkedInURL) {
        this.linkedInURL = linkedInURL;
    }

    public String getFacebookURL() {
        return facebookURL;
    }

    public void setFacebookURL(String facebookURL) {
        this.facebookURL = facebookURL;
    }
}
