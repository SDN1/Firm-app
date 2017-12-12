package com.mentormate.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.*;

public class TransferBalanceDTO {
    @Min(1)
    @NotNull(message = "idOfpersonSenderMoney field can not be null or empty.")
    private Integer idOfpersonSenderMoney;

    @Pattern(regexp="[\\d]{9}")
    @NotNull(message = "bulstaOfCompanySenderMoney field can not be null or empty.")
    private String bulstaOfCompanySenderMoney;

    @Min(1)
    @NotNull(message = "idOfpersonSenderMoney field can not be null or empty.")
    private Integer idOfPersonReceivesMoney;

    @Pattern(regexp="[\\d]{9}")
    @NotNull(message = "bulstatOfCompanyReceivesMoney field can not be null or empty.")
    private String bulstatOfCompanyReceivesMoney;

    @DecimalMin("10.00")
    @NotNull(message = "quantity field can not be null or empty.")
    private Float quantity;

    TransferBalanceDTO() {
    }

    public TransferBalanceDTO(Integer idOfpersonSenderMoney, String bulstaOfCompanySenderMoney,
                              Integer idOfPersonReceivesMoney, String bulstatOfCompanyReceivesMoney, Float quantity) {
        this.idOfpersonSenderMoney = idOfpersonSenderMoney;
        this.bulstaOfCompanySenderMoney = bulstaOfCompanySenderMoney;
        this.idOfPersonReceivesMoney = idOfPersonReceivesMoney;
        this.bulstatOfCompanyReceivesMoney = bulstatOfCompanyReceivesMoney;
        this.quantity = quantity;
    }

    public Integer getIdOfpersonSenderMoney() {
        return idOfpersonSenderMoney;
    }

    public void setIdOfpersonSenderMoney(Integer idOfpersonSenderMoney) {
        this.idOfpersonSenderMoney = idOfpersonSenderMoney;
    }

    public String getBulstaOfCompanySenderMoney() {
        return bulstaOfCompanySenderMoney;
    }

    public void setBulstaOfCompanySenderMoney(String bulstaOfCompanySenderMoney) {
        this.bulstaOfCompanySenderMoney = bulstaOfCompanySenderMoney;
    }

    public Integer getIdOfPersonReceivesMoney() {
        return idOfPersonReceivesMoney;
    }

    public void setIdOfPersonReceivesMoney(Integer idOfPersonReceivesMoney) {
        this.idOfPersonReceivesMoney = idOfPersonReceivesMoney;
    }

    public String getBulstatOfCompanyReceivesMoney() {
        return bulstatOfCompanyReceivesMoney;
    }

    public void setBulstatOfCompanyReceivesMoney(String bulstatOfCompanyReceivesMoney) {
        this.bulstatOfCompanyReceivesMoney = bulstatOfCompanyReceivesMoney;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }
}