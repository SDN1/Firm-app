package com.mentormate.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "orders")
public class Orders implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Integer order_id;

    @NotNull(message = "operation firm name can not be null or empty.")
    private String operationFirmName;

    @NotNull(message = "order-type can not be null or empty.")
    private Boolean orderType;

    @NotNull(message = "deal price can not be null or empty.")
    private Float dealPrice;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "firm_id")
    private Firm firm; // Една фирма, може да има много поръчки.

    public Orders() {

    }

    public Orders(Integer order_id, String operationFirmName, Boolean orderType, Float dealPrice, Date date, Firm firm) {
        this.order_id = order_id;
        this.operationFirmName = operationFirmName;
        this.orderType = orderType;
        this.dealPrice = dealPrice;
        this.date = date;
        this.firm = firm;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
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
