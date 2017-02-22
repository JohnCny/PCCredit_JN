package com.cardpay.pccredit.report.model;

import com.wicresoft.jrad.base.web.form.BaseForm;

/**
 */
public class PercentForm extends BaseForm {

	private static final long serialVersionUID = -8860955438817002631L;

    private String atr;
    private String numStr;
	public String getAtr() {
		return atr;
	}
	public void setAtr(String atr) {
		this.atr = atr;
	}
	public String getNumStr() {
		return numStr;
	}
	public void setNumStr(String numStr) {
		this.numStr = numStr;
	}
    
    
	
	
	
}