package com.cardpay.pccredit.jnpad.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BaseModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 客户家庭信息
 */
@ModelParam(table = "customerinformation_jt")
public class CustomerFamilyInfo extends BaseModel{
	
	
	private static final long serialVersionUID = 1L;
	private String familyNum;
	private String familyHarmony;
	private String economicNum;
	private String mateName;
	private String mateCardId;
	private String mateJobAdress;
	private String mateIncome;
	private String mateTel;
	private String mateOtherInfo;
	private String childJob;
	private String childEducation;
	private String customerId;
	private Date updateDate;
	private Date createDate;
	private String fatherName;
	private String fatherDomicile;
	private String fatherAge;
	private String fatherMinzu;
	private String fatherCompany;
	private String fatherCompanyAddress;
	private String fatherIncome;
	private String fatherContact;
	private String fatherSchool;
	private String fatherEducation;
	private String motherName;
	private String motherDomicile;
	private String motherAge;
	private String motherMinzu;
	private String motherCompany;
	private String motherCompanyAddress;
	private String motherIncome;
	private String motherContact;
	private String motherSchool;
	private String motherEducation;
	private String brotherName;
	private String brotherDomicile;
	private String brotherAge;
	private String brotherMinzu;
	private String brotherCompany;
	private String brotherCompanyAddress;
	private String brotherIncome;
	private String brotherContact;
	private String brotherSchool;
	private String brotherEducation;
	
	
	
	
	public String getFatherName() {
		return fatherName;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	public String getFatherDomicile() {
		return fatherDomicile;
	}
	public void setFatherDomicile(String fatherDomicile) {
		this.fatherDomicile = fatherDomicile;
	}
	public String getFatherAge() {
		return fatherAge;
	}
	public void setFatherAge(String fatherAge) {
		this.fatherAge = fatherAge;
	}
	public String getFatherMinzu() {
		return fatherMinzu;
	}
	public void setFatherMinzu(String fatherMinzu) {
		this.fatherMinzu = fatherMinzu;
	}
	public String getFatherCompany() {
		return fatherCompany;
	}
	public void setFatherCompany(String fatherCompany) {
		this.fatherCompany = fatherCompany;
	}
	public String getFatherCompanyAddress() {
		return fatherCompanyAddress;
	}
	public void setFatherCompanyAddress(String fatherCompanyAddress) {
		this.fatherCompanyAddress = fatherCompanyAddress;
	}
	public String getFatherIncome() {
		return fatherIncome;
	}
	public void setFatherIncome(String fatherIncome) {
		this.fatherIncome = fatherIncome;
	}
	public String getFatherContact() {
		return fatherContact;
	}
	public void setFatherContact(String fatherContact) {
		this.fatherContact = fatherContact;
	}
	public String getFatherSchool() {
		return fatherSchool;
	}
	public void setFatherSchool(String fatherSchool) {
		this.fatherSchool = fatherSchool;
	}
	public String getFatherEducation() {
		return fatherEducation;
	}
	public void setFatherEducation(String fatherEducation) {
		this.fatherEducation = fatherEducation;
	}
	public String getMotherName() {
		return motherName;
	}
	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}
	public String getMotherDomicile() {
		return motherDomicile;
	}
	public void setMotherDomicile(String motherDomicile) {
		this.motherDomicile = motherDomicile;
	}
	public String getMotherAge() {
		return motherAge;
	}
	public void setMotherAge(String motherAge) {
		this.motherAge = motherAge;
	}
	public String getMotherMinzu() {
		return motherMinzu;
	}
	public void setMotherMinzu(String motherMinzu) {
		this.motherMinzu = motherMinzu;
	}
	public String getMotherCompany() {
		return motherCompany;
	}
	public void setMotherCompany(String motherCompany) {
		this.motherCompany = motherCompany;
	}
	public String getMotherCompanyAddress() {
		return motherCompanyAddress;
	}
	public void setMotherCompanyAddress(String motherCompanyAddress) {
		this.motherCompanyAddress = motherCompanyAddress;
	}
	public String getMotherIncome() {
		return motherIncome;
	}
	public void setMotherIncome(String motherIncome) {
		this.motherIncome = motherIncome;
	}
	public String getMotherContact() {
		return motherContact;
	}
	public void setMotherContact(String motherContact) {
		this.motherContact = motherContact;
	}
	public String getMotherSchool() {
		return motherSchool;
	}
	public void setMotherSchool(String motherSchool) {
		this.motherSchool = motherSchool;
	}
	public String getMotherEducation() {
		return motherEducation;
	}
	public void setMotherEducation(String motherEducation) {
		this.motherEducation = motherEducation;
	}
	public String getBrotherName() {
		return brotherName;
	}
	public void setBrotherName(String brotherName) {
		this.brotherName = brotherName;
	}
	public String getBrotherDomicile() {
		return brotherDomicile;
	}
	public void setBrotherDomicile(String brotherDomicile) {
		this.brotherDomicile = brotherDomicile;
	}
	public String getBrotherAge() {
		return brotherAge;
	}
	public void setBrotherAge(String brotherAge) {
		this.brotherAge = brotherAge;
	}
	public String getBrotherMinzu() {
		return brotherMinzu;
	}
	public void setBrotherMinzu(String brotherMinzu) {
		this.brotherMinzu = brotherMinzu;
	}
	public String getBrotherCompany() {
		return brotherCompany;
	}
	public void setBrotherCompany(String brotherCompany) {
		this.brotherCompany = brotherCompany;
	}
	public String getBrotherCompanyAddress() {
		return brotherCompanyAddress;
	}
	public void setBrotherCompanyAddress(String brotherCompanyAddress) {
		this.brotherCompanyAddress = brotherCompanyAddress;
	}
	public String getBrotherIncome() {
		return brotherIncome;
	}
	public void setBrotherIncome(String brotherIncome) {
		this.brotherIncome = brotherIncome;
	}
	public String getBrotherContact() {
		return brotherContact;
	}
	public void setBrotherContact(String brotherContact) {
		this.brotherContact = brotherContact;
	}
	public String getBrotherSchool() {
		return brotherSchool;
	}
	public void setBrotherSchool(String brotherSchool) {
		this.brotherSchool = brotherSchool;
	}
	public String getBrotherEducation() {
		return brotherEducation;
	}
	public void setBrotherEducation(String brotherEducation) {
		this.brotherEducation = brotherEducation;
	}
	public String getFamilyNum() {
		return familyNum;
	}
	public void setFamilyNum(String familyNum) {
		this.familyNum = familyNum;
	}
	public String getFamilyHarmony() {
		return familyHarmony;
	}
	public void setFamilyHarmony(String familyHarmony) {
		this.familyHarmony = familyHarmony;
	}
	public String getEconomicNum() {
		return economicNum;
	}
	public void setEconomicNum(String economicNum) {
		this.economicNum = economicNum;
	}
	public String getMateName() {
		return mateName;
	}
	public void setMateName(String mateName) {
		this.mateName = mateName;
	}
	public String getMateCardId() {
		return mateCardId;
	}
	public void setMateCardId(String mateCardId) {
		this.mateCardId = mateCardId;
	}
	public String getMateJobAdress() {
		return mateJobAdress;
	}
	public void setMateJobAdress(String mateJobAdress) {
		this.mateJobAdress = mateJobAdress;
	}
	public String getMateIncome() {
		return mateIncome;
	}
	public void setMateIncome(String mateIncome) {
		this.mateIncome = mateIncome;
	}
	public String getMateTel() {
		return mateTel;
	}
	public void setMateTel(String mateTel) {
		this.mateTel = mateTel;
	}
	public String getMateOtherInfo() {
		return mateOtherInfo;
	}
	public void setMateOtherInfo(String mateOtherInfo) {
		this.mateOtherInfo = mateOtherInfo;
	}
	public String getChildJob() {
		return childJob;
	}
	public void setChildJob(String childJob) {
		this.childJob = childJob;
	}
	public String getChildEducation() {
		return childEducation;
	}
	public void setChildEducation(String childEducation) {
		this.childEducation = childEducation;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
