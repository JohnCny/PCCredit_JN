package com.cardpay.pccredit.creditEvaluation;
import java.util.*;

import org.json.*;

import java.math.RoundingMode;
import java.sql.*;
import java.text.DecimalFormat;

/*
 * This class has pakaged all the information of
 * the loan applicant, include the basic infomation
 * and his living, finance conditions.
 */
public class Applicant {
	private String ID;
	private String Name;
	private int Age;
	private String gender;
	private JSONObject information;
	private JSONObject model;
	private double[] livingConditions;
	private double[] operateConditions;
	private double repayAbilities;
	private double[] creditConditions;
	private int livingConditionNotContained;
	private int operateConditionNotContained;
	private int creditConditionNotContained;
	
	// structure method
	public Applicant(JSONObject _information, JSONObject _model) {
		information = _information;
		model = _model;
	}
	
	// initial method
	public void init() throws Exception {
		JSONObject basicInfo = information.getJSONObject("applicantInfo");
		ID = basicInfo.getString("身份证号");
		Name = basicInfo.getString("姓名");
		gender = basicInfo.getString("性别");
		
		// get Applicant age
		Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
		Age = year - Integer.parseInt(basicInfo.getString("身份证号").substring(6, 10));	
		
		JSONObject model_livingConditions = model.getJSONObject("livingCondition");
		JSONObject model_operateConditions = model.getJSONObject("operateCondition");
		//JSONObject model_repayAbilities = model.getJSONObject("repayAbilities");
		JSONObject model_creditConditions = model.getJSONObject("creditCondition");
		
		JSONObject info_livingConditions = information.getJSONObject("livingCondition");
		JSONObject info_operateConditions = information.getJSONObject("operateCondition");
		JSONObject info_repayAbilities = information.getJSONObject("repayAbilities");
		JSONObject info_creditConditions = information.getJSONObject("creditCondition");
		
		livingConditions = getScore(model_livingConditions, info_livingConditions, livingConditionNotContained);
		operateConditions = getScore(model_operateConditions, info_operateConditions, operateConditionNotContained);
		repayAbilities = getRepayAbility(info_repayAbilities);
		creditConditions = getScore(model_creditConditions, info_creditConditions, creditConditionNotContained); 
	}
	
	// set Applicant info
	public void setApplicantInfo(JSONObject applicantInfo) {
		
	}
	
	// get evaluate result
	public JSONObject getEvaluateData() throws Exception {
		JSONObject result = new JSONObject();
		//JSONArray array = new JSONArray();
		JSONObject obj = new JSONObject();
		JSONObject tmp = information.getJSONObject("applicantInfo");
		int loanPeriod = information.getJSONObject("repayAbilities").getInt("申请期限");
		int operatePeriod = tmp.getInt("经营时间");
		String usage = tmp.getString("所属行业");
		String[] usageArray = {"钢材", "木材", "石材", "低俗娱乐业（洗浴、KTV、游戏厅等）", "房地产", "光伏", "化工行业", "有色金属行业", 
				"水泥行业", "纺织业", "造纸业", "煤炭", "多晶硅", "风电制造", "平行玻璃"};
		Boolean whether = false;
		
		for(int i=0; i<usageArray.length; i++) {
			if(usage == usageArray[i]) {
				whether = false;
			} else {
				whether = true;
			}
		}
		
		//if(Age >= 18 && whether && loanPeriod <= 65-Age && operatePeriod > 1) {
		if(Age >= 18 && whether  && operatePeriod > 1) {
			try {
				DecimalFormat df = new DecimalFormat("0.00");  
		        df.setRoundingMode(RoundingMode.HALF_UP);  
		        
				double livingResult = caculateScore(livingConditions);
				double operateResult = caculateScore(operateConditions);
				double creditResult = caculateScore(creditConditions);
				double loanResult = repayAbilities * livingResult * operateResult * creditResult;
				
				obj.append("评估结果", "允许");
				obj.append("姓名", Name);
				obj.append("身份证号", ID);
				obj.append("性别", gender);
				obj.append("生存状况得分", df.format(livingResult * 100));
				obj.append("经营状况得分", df.format(operateResult * 100));
				obj.append("还款能力（万元）", repayAbilities);
				obj.append("道德品质得分", df.format(creditResult * 100));
				obj.append("拟授信额度（万元）", df.format(loanResult));
				obj.append("未评估项/总项", "道德品质：" + creditConditionNotContained + "/" + information.getJSONObject("creditCondition").length() + ","
						+ "生存状况：" + livingConditionNotContained + "/" + information.getJSONObject("livingCondition").length() + ","
						+ "经营状况" + operateConditionNotContained + "/" + information.getJSONObject("operateCondition").length());
				//array.put(obj);
				//result.put("允许", obj);
				result = obj;
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			obj.append("评估结果", "允许");
			obj.append("姓名", Name);
			obj.append("身份证号", ID);
			obj.append("性别", gender);
			obj.append("生存状况得分", 0);
			obj.append("经营状况得分", 0);
			obj.append("还款能力（万元）", 0);
			obj.append("道德品质得分", 0);
			obj.append("拟授信额度（万元）", 0);
			obj.append("未评估项/总项", "null");
		}
		return result;
	}
	
	// caculate the score
	private double caculateScore(double[] score) {
		double result = 0;
		for(int i=0; i<score.length; i++) {
			result += score[i];
		}
		return result;
	}
	
	
	// to check this whether this applicant exist in the Database or not
	private Boolean checkExist(String ID) throws Exception {
		DatabaseConnector db = new DatabaseConnector();
		String sql = "SELECT Name FROM tb_applicant WHERE ID =" + ID;
		ResultSet rs = db.getSelectOperator(sql);
		
		if(rs.last()) {
			return true;
		} else {
			return false;
		}
	}
	
	// get evaluate score
	private double[] getScore(JSONObject model, JSONObject info, int notContained) throws Exception {
		int i = 0;
		String key, value;
		double[] result = new double[info.length()];
		Iterator iterator = info.keys();
		JSONArray array = new JSONArray();
		notContained = 0;
		
        while(iterator.hasNext()) {
        	key = iterator.next().toString();
        	value = info.getString(key);
        	//System.out.println(value);
        	if(value != "null") {
        		array = model.getJSONArray(key);
            	result[i] = array.getDouble(0) * array.getJSONObject(1).getDouble(value);
        	} else {
        		array = model.getJSONArray(key);
        		result[i] = array.getDouble(0);
        		notContained++;
        	}
        	i++;
        }
       
        return result;
	}
	
	// get repay ability
	private double getRepayAbility(JSONObject obj) throws Exception {
		double result = 0;
		result = obj.getDouble("自有资金") + obj.getDouble("配偶年收入") * 0.7 + obj.getDouble("非经营总资产") + obj.getDouble("月利润") * obj.getInt("申请期限") + obj.getDouble("非经营总负债");
		return result;
	}
}
