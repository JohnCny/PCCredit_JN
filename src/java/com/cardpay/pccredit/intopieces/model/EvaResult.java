package com.cardpay.pccredit.intopieces.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 四维授信评估结果表
 * @author songchen
 * @time 2017年3月1日 09:29:03
 *
 */
@ModelParam(table = "eva_result")
public class EvaResult extends BusinessModel {

	private static final long serialVersionUID = -8860955438817002631L;
     
	 private String  excelId;
	 private String  result;
	 private String  cname;
	 private String  cardNo;
	 private String  sex;
	 private String  busScore;
	 private String  habScore;
	 private String  payAbli;
	 private String  money;
	 private String  project;
	 private String  liveScore;
	public String getExcelId() {
		return excelId;
	}
	public void setExcelId(String excelId) {
		this.excelId = excelId;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBusScore() {
		return busScore;
	}
	public void setBusScore(String busScore) {
		this.busScore = busScore;
	}
	public String getHabScore() {
		return habScore;
	}
	public void setHabScore(String habScore) {
		this.habScore = habScore;
	}
	public String getPayAbli() {
		return payAbli;
	}
	public void setPayAbli(String payAbli) {
		this.payAbli = payAbli;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getLiveScore() {
		return liveScore;
	}
	public void setLiveScore(String liveScore) {
		this.liveScore = liveScore;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	 
	
}