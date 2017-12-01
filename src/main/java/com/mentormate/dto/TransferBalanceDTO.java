package com.mentormate.dto;

public class TransferBalanceDTO {
    private Integer idOfpersonSenderMoney;
    private String bulstaOfCompanySenderMoney;

    private Integer idOfPersonReceivesMoney;
    private String bulstatOfCompanyReceivesMoney;

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