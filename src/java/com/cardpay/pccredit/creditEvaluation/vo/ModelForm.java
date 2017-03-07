package com.cardpay.pccredit.creditEvaluation.vo;

import com.wicresoft.jrad.base.web.form.BaseForm;


/**
 * 四维模型评估参数 form
 * @author songchen
 * @time 2017年3月1日 13:31:40
 */
public class ModelForm extends BaseForm{
	
	private String excelId;
	
	/*ApplicantInfoVo*/
	private String cardNo;
	private String cname;
	private String sex;
	private String domicileLocation;
	private String address;
	private String phoneNo;
	private String spouseIdNo;
	private String companyAddress;
	private String industry;
	private int    operatingTime;
	
	/*CreditConditionVo*/
	private String maritalStatus;
	private String highestDegree;
	private String familyEvaluationOfApplicants;
	private String  neighborEvaluation;
	private String  evaluationOfImportantContactPerson;
	private String  evaluationOfBusinessAssociates;
	private String  socialWelfareSituation;
	private String violationOfLaw;
	private String familyHarmony;
	private String economicDependence;
	private String  badHabits;
	private String  badPublicRecords;
	private String politicalSituation;
	private String commercialInsurance;
	private String socialRelations;
	private String parentalSupport;
	private String dfamilyHarmony;
	private String creditStatus;
	private String  creditCardOverdue;
	private String creditCardTotalNum;
	
	/*LivingConditionVo*/
	private String dwellingType;
	private String decorationSituation;
	private String housingArea;
	private String ownedPropertyQuantity;
	private String numberOfMortgage;
	private String  housePrice;
	private String  totalPropertyArea;
	private String  numberOfPrivateVehicles;
	private String  numberOfLoans;
	private String  vehiclePrice;
	private String others;
	private String  personalBankAccountBalance;
	private String businessAccountBalance;
	private String totalCreditCardCredit;
	private String averageMonthlyRepaymentAmountOfIncome;
	private String guaranteeForOthers;
	private String theProportionOfTheAmountOfTheSecuredAssets;
	private String securedUse;
	private String guaranteePeriod;
	
	/*OperateConditionVo*/
	private String organizationType;
	private String operatingArea;
	private String proportionofShareholders;
	private String employees;
	private String businessLicense;
	private String storeType;
	private String shopDecoration;
	
	
	/*RepayAbilitiesVo*/
	private double ownFunds;
	private double spouseIncome;
	private double totalNonOperatingAssets;
	private double  monthlyProfit;
	private int applicationPeriod;
	private double nonPperatingTotalLiabilities;
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getDomicileLocation() {
		return domicileLocation;
	}
	public void setDomicileLocation(String domicileLocation) {
		this.domicileLocation = domicileLocation;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getSpouseIdNo() {
		return spouseIdNo;
	}
	public void setSpouseIdNo(String spouseIdNo) {
		this.spouseIdNo = spouseIdNo;
	}
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public int getOperatingTime() {
		return operatingTime;
	}
	public void setOperatingTime(int operatingTime) {
		this.operatingTime = operatingTime;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public String getHighestDegree() {
		return highestDegree;
	}
	public void setHighestDegree(String highestDegree) {
		this.highestDegree = highestDegree;
	}
	public String getFamilyEvaluationOfApplicants() {
		return familyEvaluationOfApplicants;
	}
	public void setFamilyEvaluationOfApplicants(String familyEvaluationOfApplicants) {
		this.familyEvaluationOfApplicants = familyEvaluationOfApplicants;
	}
	public String getNeighborEvaluation() {
		return neighborEvaluation;
	}
	public void setNeighborEvaluation(String neighborEvaluation) {
		this.neighborEvaluation = neighborEvaluation;
	}
	public String getEvaluationOfImportantContactPerson() {
		return evaluationOfImportantContactPerson;
	}
	public void setEvaluationOfImportantContactPerson(
			String evaluationOfImportantContactPerson) {
		this.evaluationOfImportantContactPerson = evaluationOfImportantContactPerson;
	}
	public String getEvaluationOfBusinessAssociates() {
		return evaluationOfBusinessAssociates;
	}
	public void setEvaluationOfBusinessAssociates(
			String evaluationOfBusinessAssociates) {
		this.evaluationOfBusinessAssociates = evaluationOfBusinessAssociates;
	}
	public String getSocialWelfareSituation() {
		return socialWelfareSituation;
	}
	public void setSocialWelfareSituation(String socialWelfareSituation) {
		this.socialWelfareSituation = socialWelfareSituation;
	}
	public String getViolationOfLaw() {
		return violationOfLaw;
	}
	public void setViolationOfLaw(String violationOfLaw) {
		this.violationOfLaw = violationOfLaw;
	}
	public String getFamilyHarmony() {
		return familyHarmony;
	}
	public void setFamilyHarmony(String familyHarmony) {
		this.familyHarmony = familyHarmony;
	}
	public String getEconomicDependence() {
		return economicDependence;
	}
	public void setEconomicDependence(String economicDependence) {
		this.economicDependence = economicDependence;
	}
	public String getBadHabits() {
		return badHabits;
	}
	public void setBadHabits(String badHabits) {
		this.badHabits = badHabits;
	}
	public String getBadPublicRecords() {
		return badPublicRecords;
	}
	public void setBadPublicRecords(String badPublicRecords) {
		this.badPublicRecords = badPublicRecords;
	}
	public String getPoliticalSituation() {
		return politicalSituation;
	}
	public void setPoliticalSituation(String politicalSituation) {
		this.politicalSituation = politicalSituation;
	}
	public String getCommercialInsurance() {
		return commercialInsurance;
	}
	public void setCommercialInsurance(String commercialInsurance) {
		this.commercialInsurance = commercialInsurance;
	}
	public String getSocialRelations() {
		return socialRelations;
	}
	public void setSocialRelations(String socialRelations) {
		this.socialRelations = socialRelations;
	}
	public String getParentalSupport() {
		return parentalSupport;
	}
	public void setParentalSupport(String parentalSupport) {
		this.parentalSupport = parentalSupport;
	}
	public String getDfamilyHarmony() {
		return dfamilyHarmony;
	}
	public void setDfamilyHarmony(String dfamilyHarmony) {
		this.dfamilyHarmony = dfamilyHarmony;
	}
	public String getCreditStatus() {
		return creditStatus;
	}
	public void setCreditStatus(String creditStatus) {
		this.creditStatus = creditStatus;
	}
	public String getCreditCardOverdue() {
		return creditCardOverdue;
	}
	public void setCreditCardOverdue(String creditCardOverdue) {
		this.creditCardOverdue = creditCardOverdue;
	}
	public String getCreditCardTotalNum() {
		return creditCardTotalNum;
	}
	public void setCreditCardTotalNum(String creditCardTotalNum) {
		this.creditCardTotalNum = creditCardTotalNum;
	}
	public String getDwellingType() {
		return dwellingType;
	}
	public void setDwellingType(String dwellingType) {
		this.dwellingType = dwellingType;
	}
	public String getDecorationSituation() {
		return decorationSituation;
	}
	public void setDecorationSituation(String decorationSituation) {
		this.decorationSituation = decorationSituation;
	}
	public String getHousingArea() {
		return housingArea;
	}
	public void setHousingArea(String housingArea) {
		this.housingArea = housingArea;
	}
	public String getOwnedPropertyQuantity() {
		return ownedPropertyQuantity;
	}
	public void setOwnedPropertyQuantity(String ownedPropertyQuantity) {
		this.ownedPropertyQuantity = ownedPropertyQuantity;
	}
	public String getNumberOfMortgage() {
		return numberOfMortgage;
	}
	public void setNumberOfMortgage(String numberOfMortgage) {
		this.numberOfMortgage = numberOfMortgage;
	}
	public String getHousePrice() {
		return housePrice;
	}
	public void setHousePrice(String housePrice) {
		this.housePrice = housePrice;
	}
	public String getTotalPropertyArea() {
		return totalPropertyArea;
	}
	public void setTotalPropertyArea(String totalPropertyArea) {
		this.totalPropertyArea = totalPropertyArea;
	}
	public String getNumberOfPrivateVehicles() {
		return numberOfPrivateVehicles;
	}
	public void setNumberOfPrivateVehicles(String numberOfPrivateVehicles) {
		this.numberOfPrivateVehicles = numberOfPrivateVehicles;
	}
	public String getNumberOfLoans() {
		return numberOfLoans;
	}
	public void setNumberOfLoans(String numberOfLoans) {
		this.numberOfLoans = numberOfLoans;
	}
	public String getVehiclePrice() {
		return vehiclePrice;
	}
	public void setVehiclePrice(String vehiclePrice) {
		this.vehiclePrice = vehiclePrice;
	}
	public String getOthers() {
		return others;
	}
	public void setOthers(String others) {
		this.others = others;
	}
	public String getPersonalBankAccountBalance() {
		return personalBankAccountBalance;
	}
	public void setPersonalBankAccountBalance(String personalBankAccountBalance) {
		this.personalBankAccountBalance = personalBankAccountBalance;
	}
	public String getBusinessAccountBalance() {
		return businessAccountBalance;
	}
	public void setBusinessAccountBalance(String businessAccountBalance) {
		this.businessAccountBalance = businessAccountBalance;
	}
	public String getTotalCreditCardCredit() {
		return totalCreditCardCredit;
	}
	public void setTotalCreditCardCredit(String totalCreditCardCredit) {
		this.totalCreditCardCredit = totalCreditCardCredit;
	}
	public String getAverageMonthlyRepaymentAmountOfIncome() {
		return averageMonthlyRepaymentAmountOfIncome;
	}
	public void setAverageMonthlyRepaymentAmountOfIncome(
			String averageMonthlyRepaymentAmountOfIncome) {
		this.averageMonthlyRepaymentAmountOfIncome = averageMonthlyRepaymentAmountOfIncome;
	}
	public String getGuaranteeForOthers() {
		return guaranteeForOthers;
	}
	public void setGuaranteeForOthers(String guaranteeForOthers) {
		this.guaranteeForOthers = guaranteeForOthers;
	}
	public String getTheProportionOfTheAmountOfTheSecuredAssets() {
		return theProportionOfTheAmountOfTheSecuredAssets;
	}
	public void setTheProportionOfTheAmountOfTheSecuredAssets(
			String theProportionOfTheAmountOfTheSecuredAssets) {
		this.theProportionOfTheAmountOfTheSecuredAssets = theProportionOfTheAmountOfTheSecuredAssets;
	}
	public String getSecuredUse() {
		return securedUse;
	}
	public void setSecuredUse(String securedUse) {
		this.securedUse = securedUse;
	}
	public String getGuaranteePeriod() {
		return guaranteePeriod;
	}
	public void setGuaranteePeriod(String guaranteePeriod) {
		this.guaranteePeriod = guaranteePeriod;
	}
	public String getOrganizationType() {
		return organizationType;
	}
	public void setOrganizationType(String organizationType) {
		this.organizationType = organizationType;
	}
	public String getOperatingArea() {
		return operatingArea;
	}
	public void setOperatingArea(String operatingArea) {
		this.operatingArea = operatingArea;
	}
	public String getProportionofShareholders() {
		return proportionofShareholders;
	}
	public void setProportionofShareholders(String proportionofShareholders) {
		this.proportionofShareholders = proportionofShareholders;
	}
	public String getEmployees() {
		return employees;
	}
	public void setEmployees(String employees) {
		this.employees = employees;
	}
	public String getBusinessLicense() {
		return businessLicense;
	}
	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}
	public String getStoreType() {
		return storeType;
	}
	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}
	public String getShopDecoration() {
		return shopDecoration;
	}
	public void setShopDecoration(String shopDecoration) {
		this.shopDecoration = shopDecoration;
	}
	public double getOwnFunds() {
		return ownFunds;
	}
	public void setOwnFunds(double ownFunds) {
		this.ownFunds = ownFunds;
	}
	public double getSpouseIncome() {
		return spouseIncome;
	}
	public void setSpouseIncome(double spouseIncome) {
		this.spouseIncome = spouseIncome;
	}
	public double getTotalNonOperatingAssets() {
		return totalNonOperatingAssets;
	}
	public void setTotalNonOperatingAssets(double totalNonOperatingAssets) {
		this.totalNonOperatingAssets = totalNonOperatingAssets;
	}
	public double getMonthlyProfit() {
		return monthlyProfit;
	}
	public void setMonthlyProfit(double monthlyProfit) {
		this.monthlyProfit = monthlyProfit;
	}
	public int getApplicationPeriod() {
		return applicationPeriod;
	}
	public void setApplicationPeriod(int applicationPeriod) {
		this.applicationPeriod = applicationPeriod;
	}
	public double getNonPperatingTotalLiabilities() {
		return nonPperatingTotalLiabilities;
	}
	public void setNonPperatingTotalLiabilities(double nonPperatingTotalLiabilities) {
		this.nonPperatingTotalLiabilities = nonPperatingTotalLiabilities;
	}
	public String getExcelId() {
		return excelId;
	}
	public void setExcelId(String excelId) {
		this.excelId = excelId;
	}
	
	
}
