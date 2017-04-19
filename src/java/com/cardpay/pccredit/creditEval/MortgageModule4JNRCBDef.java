package com.cardpay.pccredit.creditEval;

/**
 * 接受模型
 *
 * @author rankai
 * createTime 2017-04-2017/4/1 15:05
 */
public class MortgageModule4JNRCBDef {
    private double spouseIncomeAnnual;
    private double cashAndDepositsWithBanks;
    private double accountsReceivable;
    private double prepaidAccounts;
    private double inventory;
    private double shortTermLiabilities;
    private double longTermLiabilities;
    private double otherLiabilities;
    private double contingentLiabilities;
    private double ownerEquity;
    private double retainedProfitsMonthly;
    private double householdExpensesMonthly;
    private double paymentInstalmentsMonthly;
    private double otherExpensesMonthly;
    private double otherIncomeMonthly;
    private double housePrice;
    private double capitalAsset;
    private double operatingYears;
    private double marriage;
    private double education;

    public double getSpouseIncomeAnnual() {
        return spouseIncomeAnnual;
    }

    public void setSpouseIncomeAnnual(double spouseIncomeAnnual) {
        this.spouseIncomeAnnual = spouseIncomeAnnual;
    }

    public double getCashAndDepositsWithBanks() {
        return cashAndDepositsWithBanks;
    }

    public void setCashAndDepositsWithBanks(double cashAndDepositsWithBanks) {
        this.cashAndDepositsWithBanks = cashAndDepositsWithBanks;
    }

    public double getAccountsReceivable() {
        return accountsReceivable;
    }

    public void setAccountsReceivable(double accountsReceivable) {
        this.accountsReceivable = accountsReceivable;
    }

    public double getPrepaidAccounts() {
        return prepaidAccounts;
    }

    public void setPrepaidAccounts(double prepaidAccounts) {
        this.prepaidAccounts = prepaidAccounts;
    }

    public double getInventory() {
        return inventory;
    }

    public void setInventory(double inventory) {
        this.inventory = inventory;
    }

    public double getShortTermLiabilities() {
        return shortTermLiabilities;
    }

    public void setShortTermLiabilities(double shortTermLiabilities) {
        this.shortTermLiabilities = shortTermLiabilities;
    }

    public double getLongTermLiabilities() {
        return longTermLiabilities;
    }

    public void setLongTermLiabilities(double longTermLiabilities) {
        this.longTermLiabilities = longTermLiabilities;
    }

    public double getOtherLiabilities() {
        return otherLiabilities;
    }

    public void setOtherLiabilities(double otherLiabilities) {
        this.otherLiabilities = otherLiabilities;
    }

    public double getContingentLiabilities() {
        return contingentLiabilities;
    }

    public void setContingentLiabilities(double contingentLiabilities) {
        this.contingentLiabilities = contingentLiabilities;
    }

    public double getOwnerEquity() {
        return ownerEquity;
    }

    public void setOwnerEquity(double ownerEquity) {
        this.ownerEquity = ownerEquity;
    }

    public double getRetainedProfitsMonthly() {
        return retainedProfitsMonthly;
    }

    public void setRetainedProfitsMonthly(double retainedProfitsMonthly) {
        this.retainedProfitsMonthly = retainedProfitsMonthly;
    }

    public double getHouseholdExpensesMonthly() {
        return householdExpensesMonthly;
    }

    public void setHouseholdExpensesMonthly(double householdExpensesMonthly) {
        this.householdExpensesMonthly = householdExpensesMonthly;
    }

    public double getPaymentInstalmentsMonthly() {
        return paymentInstalmentsMonthly;
    }

    public void setPaymentInstalmentsMonthly(double paymentInstalmentsMonthly) {
        this.paymentInstalmentsMonthly = paymentInstalmentsMonthly;
    }

    public double getOtherExpensesMonthly() {
        return otherExpensesMonthly;
    }

    public void setOtherExpensesMonthly(double otherExpensesMonthly) {
        this.otherExpensesMonthly = otherExpensesMonthly;
    }

    public double getOtherIncomeMonthly() {
        return otherIncomeMonthly;
    }

    public void setOtherIncomeMonthly(double otherIncomeMonthly) {
        this.otherIncomeMonthly = otherIncomeMonthly;
    }

    public double getHousePrice() {
        return housePrice;
    }

    public void setHousePrice(double housePrice) {
        this.housePrice = housePrice;
    }

    public double getCapitalAsset() {
        return capitalAsset;
    }

    public void setCapitalAsset(double capitalAsset) {
        this.capitalAsset = capitalAsset;
    }

    public double getOperatingYears() {
        return operatingYears;
    }

    public void setOperatingYears(double operatingYears) {
        this.operatingYears = operatingYears;
    }

    public double getMarriage() {
        return marriage;
    }

    public void setMarriage(double marriage) {
        this.marriage = marriage;
    }

    public double getEducation() {
        return education;
    }

    public void setEducation(double education) {
        this.education = education;
    }

    // 获取Mortgage模型对象
    public MortgageModule4JNRCB getMortgageModel() {
        MortgageModule4JNRCB mortgageModule4JNRCB = new MortgageModule4JNRCB();
        double current_asset = cashAndDepositsWithBanks + accountsReceivable + prepaidAccounts + inventory;
        double total_liabilities = shortTermLiabilities + longTermLiabilities + otherLiabilities +
                contingentLiabilities;
        double owner_equity = ownerEquity;
        double disposable_asset_monthly = retainedProfitsMonthly - householdExpensesMonthly - paymentInstalmentsMonthly
                + otherExpensesMonthly + otherIncomeMonthly;
        double spouse_income_annual = spouseIncomeAnnual;
        double house_price = housePrice;
        double capital_asset = capitalAsset;
        double operating_years = operatingYears;
        double marriage = this.marriage;
        double education = this.education;
        mortgageModule4JNRCB.setCurrentAsset(current_asset);
        mortgageModule4JNRCB.setTotalLiabilities(total_liabilities);
        mortgageModule4JNRCB.setOwnerEquity(owner_equity);
        mortgageModule4JNRCB.setDisposableAssetMonthly(disposable_asset_monthly);
        mortgageModule4JNRCB.setSpouseIncomeAnnual(spouse_income_annual);
        mortgageModule4JNRCB.setHousePrice(house_price);
        mortgageModule4JNRCB.setOperatingYears(operating_years);
        mortgageModule4JNRCB.setCapitalAsset(capital_asset);
        mortgageModule4JNRCB.setMarriage(marriage);
        mortgageModule4JNRCB.setEducation(education);
        return mortgageModule4JNRCB;
    }
}
