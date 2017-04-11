package com.cardpay.pccredit.creditEvaluation;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javassist.expr.NewArray;

import org.json.*;

import com.cardpay.pccredit.creditEvaluation.vo.ApplicantInfoVo;
import com.cardpay.pccredit.creditEvaluation.vo.CreditConditionVo;
import com.cardpay.pccredit.creditEvaluation.vo.LivingConditionVo;
import com.cardpay.pccredit.creditEvaluation.vo.OperateConditionVo;
import com.cardpay.pccredit.creditEvaluation.vo.RepayAbilitiesVo;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * This class is used to apply the WebService.
 * Via this webService, you are able get the 
 * credit evaluate report for a loan applicant;
 */
public class EvaluateApplicantService {
	private static Applicant app;
	
	public static void main(String[] args) {
		JsonParser jp = new JsonParser();
		JSONObject result = null;
		try {
			JSONObject info = jp.getJsonFromFile("./app.json");//传入客户信息
			JSONObject model = jp.getJsonFromFile("./model.json");//评估模型
			//String modeljson = "{\"livingCondition\":{\"居住类型\":[0.3,{\"自有\":1,\"自建房\":0.9,\"住经营场所\":0.7,\"租住\":0.5,\"其他\":0.2}],\"装修情况\":[0.06,{\"好\":1,\"中\":0.8,\"差\":0.5,\"其他\":0.2}],\"住房面积\":[0.1,{\"≤100\":0.4,\"100-200\":0.5,\"200-300\":0.6,\"300-400\":0.7,\"400-500\":0.8,\"≥500\":1}],\"自有房产数量\":[0.06,{\"0\":0,\"1\":0.5,\"2\":0.6,\"3\":0.7,\"4\":0.8,\"≥5\":1}],\"按揭房产数量\":[0.05,{\"0\":0,\"1\":0.9,\"2\":0.8,\"3\":0.7,\"4\":0.6,\"≥5\":0.5}],\"房产总价\":[0.1,{\"≤100\":0.3,\"100-300\":0.4,\"300-500\":0.5,\"500-1000\":0.6,\"1000-3000\":0.7,\"3000-5000\":0.8,\"5000-10000\":0.9,\"≥10000\":1}],\"自有房产总面积\":[0.04,{\"≤100\":0.4,\"100-200\":0.5,\"200-300\":0.6,\"300-400\":0.7,\"400-500\":0.8,\"≥500\":1}],\"自有车辆数量\":[0.06,{\"0\":0,\"1\":0.6,\"2\":0.7,\"3\":0.8,\"4\":0.9,\"≥5\":1}],\"贷款车辆数量\":[0.02,{\"0\":0,\"1\":0.9,\"2\":0.8,\"3\":0.7,\"4\":0.6,\"≥5\":0.5}],\"车辆总价\":[0.06,{\"≤10\":0.1,\"10-20\":0.2,\"20-50\":0.3,\"50-100\":0.4,\"100-300\":0.5,\"300-500\":0.6,\"500-1000\":0.7,\"1000-2000\":0.8,\"2000-3000\":0.9,\"≥3000\":1}],\"除经营生意外是否有其他工作\":[0.04,{\"是\":1,\"否\":0.8}],\"个人银行帐户余额\":[0.06,{\"≤50\":0.5,\"50-100\":0.6,\"100-300\":0.7,\"300-500\":0.8,\"500-1000\":0.9,\"≥1000\":1}],\"生意帐户余额\":[0.04,{\"≤300\":0.5,\"300-500\":0.6,\"500-1000\":0.7,\"1000-3000\":0.8,\"3000-5000\":0.9,\"≥5000\":1}],\"信用卡授信总额\":[0.01,{\"≤5\":0.7,\"5-10\":0.9,\"≥10\":1}],\"月平均还款金额占收入比例\":[0.02,{\"≥70%\":-1,\"50%-70%\":-0.8,\"30%-50%\":-0.5,\"10%-30%\":-0.2,\"≤10%\":0}],\"是否为他人担保\":[0.01,{\"是\":-1,\"否\":0}],\"担保金额占本人自有资产比例\":[0.03,{\"≥100%\":-1,\"50%-100%\":-0.9,\"10%-50%\":-0.8,\"5%-10%\":-0.7,\"≤5%\":-0.6}],\"担保用途\":[0.02,{\"经营\":-1,\"朋友买房\\/车\\/消费\":-0.8}],\"担保期限\":[0.02,{\"1\":-0.7,\"2\":-0.8,\">5\":-1,\"3-5\":-0.9,\"<1\":-0.6}]},\"operateCondition\":{\"组织类型\":[0.1,{\"个体\":0.4,\"民营\":0.6,\"股份制公司\":0.8,\"上市公司\":1}],\"经营场所面积\":[0.15,{\"≤100\":0.5,\"100-200\":0.6,\"200-300\":0.7,\"300-400\":0.8,\"400-500\":0.9,\"≥500\":1}],\"股东占比情况\":[0.2,{\">70%\":1,\"50%-70%\":0.8,\"30%-50%\":0.7,\"10%-30%\":0.6,\"<10%\":0.5}],\"雇员人数\":[0.1,{\">200\":1,\"100-200\":0.9,\"50-100\":0.8,\"30-50\":0.7,\"10-30\":0.6,\"<10\":0.5}],\"营业执照\":[0.1,{\"有\":1,\"无\":0}],\"店铺类型\":[0.2,{\"自有产权\":1,\"按揭商铺\":0.9,\"租用\":0.6,\"其他\":0}],\"店铺装修情况\":[0.15,{\"好\":1,\"一般\":0.6,\"差\":0.2}]},\"repayAbilities\":{\"weight\":0.7},\"creditCondition\":{\"婚姻状况\":[0.15,{\"已婚\":1,\"未婚\":0.6,\"离异\":0.3}],\"最高学位\":[0.05,{\"本科及以上\":1,\"大专\":0.8,\"高中或中专\":0.6,\"初中及以下\":0.4}],\"家人对申请人评价\":[0.025,{\"好\":1,\"一般\":0.6,\"差\":0.2}],\"邻居对申请人评价\":[0.05,{\"好\":1,\"一般\":0.6,\"差\":0.2}],\"重要联系人对申请人评价\":[0.075,{\"好\":1,\"一般\":0.6,\"差\":0.2}],\"生意关联人对申请人评价\":[0.05,{\"好\":1,\"一般\":0.6,\"差\":0.2}],\"社会公益状况\":[0.09,{\"有\":1,\"无\":0.7}],\"违法违纪情况\":[0.1,{\"有\":0,\"无\":1}],\"家庭是否和睦\":[0.04,{\"是\":1,\"否\":0.6}],\"经济上依赖的人数\":[0.06,{\"0\":1,\"1\":0.6,\"≥2\":0.2}],\"不良嗜好\":[0.05,{\"有\":0,\"无\":1}],\"不良公共记录\":[0.1,{\"有\":0,\"无\":1}],\"政治情况\":[0.05,{\"党员\":1,\"群众\":0.8}],\"商业保险情况\":[0.05,{\"有\":1,\"无\":0.5}],\"社会关系\":[0.025,{\"好\":1,\"一般\":0.6,\"差\":0.2}],\"赡养父母状况\":[0.05,{\"好\":1,\"一般\":0.6,\"差\":0.2}],\"亲属和睦状况\":[0.025,{\"好\":1,\"一般\":0.6,\"差\":0.2}],\"信用状况\":[0.1,{\"正常\":1,\"不正常\":0,\"无记录\":0.2}],\"信用卡逾期情况\":[0.2,{\"征信报告无逾期\":0,\"24个月内无逾期，5年内有逾期\":-0.2,\"24个月内有逾期（逾期<5次），无连续逾期，5年内有逾期\":-0.4,\"24个月内有逾期（逾期<5次），无连续逾期，5年内无逾期\":-0.6,\"24个月内有逾期（逾期<5次），且为连续逾期，5年内有逾期\":-0.8,\"24个月内逾期≥5次\":-1}],\"信用卡总数\":[0.06,{\"0\":0,\"1\":0.9,\"2\":1,\"3\":0.8,\"4\":0.7,\"≥5\":0}]}}";
			//JSONObject model = new org.json.JSONObject(modeljson);
			app = new Applicant(info, model);
			app.init();
			result = app.getEvaluateData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(result.toString());
	}
	
	//======================================================================
	public static ApplicantInfoVo returnVo(){
		/*"身份证号": "62010219860718003x",
	    "姓名": "张钰森",
	    "性别": "男",
	    "户籍所在地": "陕西西安",
	    "详细地址": "济南市市中区济南大学计算机系100号",
	    "电话": "13800138000",
	    "配偶身份证号": "130102197707210039",
	    "店铺/企业地址": "济南市历下区会展路225号",
	    "所属行业": "网店经营",
	    "经营时间": 3*/
		ApplicantInfoVo vo  = new ApplicantInfoVo();
		vo.setCardNo("62010219860718003x");
		vo.setCname("张钰森");
		vo.setSex("男");
		vo.setDomicileLocation("陕西西安");
		vo.setAddress("济南市市中区济南大学计算机系100号");
		vo.setPhoneNo("13800138000");
		vo.setSpouseIdNo("130102197707210039");
		vo.setCompanyAddress("济南市历下区会展路225号");
		vo.setIndustry("网店经营");
		vo.setOperatingTime(3);
		return vo;
	}
	
	
	public static CreditConditionVo returnVo1(){
		/*"婚姻状况": "已婚",
	    "最高学位": "本科及以上",
	    "家人对申请人评价": "好",
	    "邻居对申请人评价": "好",
	    "重要联系人对申请人评价": "好",
	    "生意关联人对申请人评价": "好",
	    "社会公益状况": "有",
	    "违法违纪情况": "无",
	    "家庭是否和睦": "是",
	    "经济上依赖的人数": "1",
	    "不良嗜好": "无",
	    "不良公共记录": "无",
	    "政治情况": "党员",
	    "商业保险情况": "有",
	    "社会关系": "一般",
	    "赡养父母状况": "好",
	    "亲属和睦状况": "一般",
	    "信用状况": "正常",
	    "信用卡逾期情况": "征信报告无逾期",
	    "信用卡总数": "2"*/
		CreditConditionVo vo  = new CreditConditionVo();
		vo.setMaritalStatus("已婚");
		vo.setHighestDegree("本科及以上");
		vo.setFamilyEvaluationOfApplicants("好");
		vo.setNeighborEvaluation("好");
		vo.setEvaluationOfImportantContactPerson("好");
		vo.setEvaluationOfBusinessAssociates("好");
		vo.setSocialWelfareSituation("有");
		vo.setViolationOfLaw("无");
		vo.setFamilyHarmony("是");
		vo.setEconomicDependence("1");
		vo.setBadHabits("无");
		vo.setBadPublicRecords("无");
		vo.setPoliticalSituation("党员");
		vo.setCommercialInsurance("有");
		vo.setSocialRelations("一般");
		vo.setParentalSupport("好");
		vo.setDfamilyHarmony("一般");
		vo.setCreditStatus("正常");
		vo.setCreditCardOverdue("征信报告无逾期");
		vo.setCreditCardTotalNum("2");
		return vo;
	}
	
	public static LivingConditionVo returnVo2(){
		/*"居住类型": "自有",
	    "装修情况": "好",
	    "住房面积": "≤100",
	    "自有房产数量": 3,
	    "按揭房产数量": 2,
	    "房产总价": "100-300",
	    "自有房产总面积": "100-200",
	    "自有车辆数量": 3,
	    "贷款车辆数量": 2,
	    "车辆总价": "50-100",
	    "除经营生意外是否有其他工作": "是",
	    "个人银行帐户余额": "300-500",
	    "生意帐户余额": "500-1000",
	    "信用卡授信总额": "5-10",
	    "月平均还款金额占收入比例": "≥70%",
	    "是否为他人担保": "是",
	    "担保金额占本人自有资产比例": "10%-50%",
	    "担保用途": "朋友买房/车/消费",
	    "担保期限": "3-5"*/
		LivingConditionVo vo  = new LivingConditionVo();
	    vo.setDwellingType("自有");
		vo.setDecorationSituation("好");
		vo.setHousingArea("≤100");
		vo.setOwnedPropertyQuantity("3");
		vo.setNumberOfMortgage("2");
		vo.setHousePrice("100-300");
		vo.setTotalPropertyArea("100-200");
		vo.setNumberOfPrivateVehicles("3");
		vo.setNumberOfLoans("2");
		vo.setVehiclePrice("50-100");
		vo.setOthers("是");
		vo.setPersonalBankAccountBalance("300-500");
		vo.setBusinessAccountBalance("500-1000");
		vo.setTotalCreditCardCredit("5-10");
		vo.setAverageMonthlyRepaymentAmountOfIncome("≥70%");
		vo.setGuaranteeForOthers("是");
		vo.setTheProportionOfTheAmountOfTheSecuredAssets("10%-50%");
		vo.setSecuredUse("朋友买房/车/消费");
		vo.setGuaranteePeriod("3-5");
		return vo;
	}
	
	
	public static OperateConditionVo returnVo3(){
		/*  "组织类型": "个体",
	    "经营场所面积": "100-200",
	    "股东占比情况": "30%-50%",
	    "雇员人数": "10-30",
	    "营业执照": "有",
	    "店铺类型": "自有产权",
	    "店铺装修情况": "好"*/
		OperateConditionVo vo  = new OperateConditionVo();
		vo.setOrganizationType("个体");
		vo.setOperatingArea("100-200");
		vo.setProportionofShareholders("30%-50%");
		vo.setEmployees("10-30");
		vo.setBusinessLicense("有");
		vo.setStoreType("自有产权");
		vo.setShopDecoration("好");
		return vo;
	}
	
	public static RepayAbilitiesVo returnVo4(){
		/*"自有资金": 20,
	    "配偶年收入": 25,
	    "非经营总资产": 300,
	    "月利润": 8,
	    "申请期限": 2,
	    "非经营总负债": -60*/
		RepayAbilitiesVo vo  = new RepayAbilitiesVo();
	    vo.setOwnFunds(20);                               
		vo.setSpouseIncome(25);                           
		vo.setTotalNonOperatingAssets(300);                
		vo.setMonthlyProfit(8);                          
		vo.setApplicationPeriod(2);                      
		vo.setNonPperatingTotalLiabilities(-60);           

		return vo;
	}
	
	//======================================================================
	
	
	// TODO
	
	//This method is used to get applicant's information from the outside
	public void pushApplicantInfo(JSONObject applicantInfo) {
		//applicant = new Applicant();
		//if(!applicant.checkExist()) {
		//	DatabaseConnector db = new DatabaseConnector();
			
		//	JSONObject obj = new JSONObject();
		//	Applicant app = new Applicant(obj);
			
			
			// TODO
			/*
			 * toDo:
			 * 	push applicant info into the database
			 *  and initiate the object applicant at the same time
			 */
		//	applicant.setApplicantInfo(applicantInfo);
		//}
		//else {
			
			// TODO
			/*
			 * toDo:
			 * 	the different from if condition is that you do not need to insert 
			 *  the applicant basic info, you just need to updated it and push the 
			 *  new inquery information in to the database, such as the living conditions.
			 *  besides, you also need to initial the object application at the same time;
			 */
			//DatabaseConnector db = new DatabaseConnector();
			//applicant.setApplicantInfo(applicantInfo);
		//}
	}
	
	// get applicant infomation
	public void sendInfomation(JSONObject info, JSONObject model) {
		app = new Applicant(info, model);
		try {
			app.init();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("sendInfomation");
		}
	}
	
	//This method is used to get the evaluate report
	public JSONObject getEvaluateReport() {
		JSONObject result = new JSONObject();
		try {
			result = app.getEvaluateData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("getEvaluateReport");
		} finally {
			
		}
		return result;
	}
	
	
}
