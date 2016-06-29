package com.cardpay.pccredit.jnpad.model;

/**
 * 通知
 * @author songchen
 *
 */
public class NotifyMsgListVo {
	private int shendaihui;/*审贷会通知*/
	private int yuanshiziliao;/*客户原始资料变更通知*/
	private int peixun;///*培训通知*/ 
	private int kaocha;/*考察成绩通知*/
	private int qita;/*其他通知*/
	public int getShendaihui() {
		return shendaihui;
	}
	public void setShendaihui(int shendaihui) {
		this.shendaihui = shendaihui;
	}
	public int getYuanshiziliao() {
		return yuanshiziliao;
	}
	public void setYuanshiziliao(int yuanshiziliao) {
		this.yuanshiziliao = yuanshiziliao;
	}
	public int getPeixun() {
		return peixun;
	}
	public void setPeixun(int peixun) {
		this.peixun = peixun;
	}
	public int getKaocha() {
		return kaocha;
	}
	public void setKaocha(int kaocha) {
		this.kaocha = kaocha;
	}
	public int getQita() {
		return qita;
	}
	public void setQita(int qita) {
		this.qita = qita;
	}




}
