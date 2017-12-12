package com.mentormate.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "orders")
public class Orders implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "orderId")
    private Integer orderId;

    @NotNull(message = "operationFirmName field can not be null")
    private String operationFirmName;

    @NotNull(message = "orderType field can not be null")
    private Boolean orderType;

    @DecimalMin("10.00")
    @NotNull(message = "dealPrice field can not be null")
    private Float dealPrice;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    @NotNull(message = "date field can not be null")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "firm_id")
    @NotNull(message = "firm field can not be null")
    private Firm firm; // Една фирма, може да има много поръчки.

    public Orders() {

    }

    public Orders(Integer order_id, String operationFirmName, Boolean orderType, Float dealPrice, Date date, Firm firm) {
        this.orderId = order_id;
        this.operationFirmName = operationFirmName;
        this.orderType = orderType;
        this.dealPrice = dealPrice;
        this.date = date;
        this.firm = firm;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOperationFirmName() {
        return operationFirmName;
    }

    public void setOperationFirmName(String operationFirmName) {
        this.operationFirmName = operationFirmName;
    }

    public Boolean getOrderType() {
        return orderType;
    }

    public void setOrderType(Boolean orderType) {
        this.orderType = orderType;
    }

    public Float getDealPrice() {
        return dealPrice;
    }

    public void setDealPrice(Float dealPrice) {
        this.dealPrice = dealPrice;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Firm getFirm() {
        return firm;
    }

    public void setFirm(Firm firm) {
        this.firm = firm;
    }
}
