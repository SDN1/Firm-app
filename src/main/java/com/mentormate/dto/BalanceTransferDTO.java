package com.mentormate.dto;

public class BalanceTransferDTO {
	private Integer aCompanyGivesMoney;
	private Integer aCompanyReceivesMoney;
	private Float quantity;

	BalanceTransferDTO() {
		aCompanyGivesMoney = -1;
		aCompanyReceivesMoney = -1;
		quantity = (float) 0;
	}

	BalanceTransferDTO(Integer aCompanyGivesMoney, Integer aCompanyReceivesMoney, Float quantity) {
		this.aCompanyGivesMoney = aCompanyGivesMoney;
		this.aCompanyReceivesMoney = aCompanyReceivesMoney;
		this.quantity = quantity;
	}

	public Integer getaCompanyGivesMoney() {
		return aCompanyGivesMoney;
	}

	public void setaCompanyGivesMoney(Integer aCompanyGivesMoney) {
		this.aCompanyGivesMoney = aCompanyGivesMoney;
	}

	public Integer getaCompanyReceivesMoney() {
		return aCompanyReceivesMoney;
	}

	public void setaCompanyReceivesMoney(Integer aCompanyReceivesMoney) {
		this.aCompanyReceivesMoney = aCompanyReceivesMoney;
	}

	public Float getQuantity() {
		return quantity;
	}

	public void setQuantity(Float quantity) {
		this.quantity = quantity;
	}
}
