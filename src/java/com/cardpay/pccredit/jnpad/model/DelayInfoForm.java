package com.cardpay.pccredit.jnpad.model;

public class DelayInfoForm {
	private String name;
	private String cardId;
	private int dlaymat;
	private int delayinterestdays;
	private int delayamtdays; 
	private long money; 
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public int getDlaymat() {
		return dlaymat;
	}
	public void setDlaymat(int dlaymat) {
		this.dlaymat = dlaymat;
	}
	public int getDelayinterestdays() {
		return delayinterestdays;
	}
	public void setDelayinterestdays(int delayinterestdays) {
		this.delayinterestdays = delayinterestdays;
	}
	public int getDelayamtdays() {
		return delayamtdays;
	}
	public void setDelayamtdays(int delayamtdays) {
		this.delayamtdays = delayamtdays;
	}
	public long getMoney() {
		return money;
	}
	public void setMoney(long money) {
		this.money = money;
	}

	
	
}
