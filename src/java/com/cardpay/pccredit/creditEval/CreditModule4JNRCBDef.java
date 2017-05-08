package com.cardpay.pccredit.creditEval;
/**
 * 接受参数
 *
 * @author rankai
 * createTime 2017-04-2017/4/1 14:40
 */
public class CreditModule4JNRCBDef {
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
    private double spouseIncomeAnnual;
    private double marriage;
    private double education;
    private String message;

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

    public double getSpouseIncomeAnnual() {
        return spouseIncomeAnnual;
    }

    public void setSpouseIncomeAnnual(double spouseIncomeAnnual) {
        this.spouseIncomeAnnual = spouseIncomeAnnual;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // 获取credit模型对象
    public CreditModule4JNRCB getCreditModel() {
        CreditModule4JNRCB creditModule4JNRCB = new CreditModule4JNRCB();
        double current_asset = cashAndDepositsWithBanks + accountsReceivable + prepaidAccounts + inventory;
        double total_liabilities = shortTermLiabilities + longTermLiabilities + otherLiabilities +
                contingentLiabilities;
        double owner_equity = ownerEquity;
        double disposable_asset_monthly = retainedProfitsMonthly - householdExpensesMonthly - paymentInstalmentsMonthly
                + otherExpensesMonthly + otherIncomeMonthly;
        double income_annual = disposable_asset_monthly * 12 + spouseIncomeAnnual;
        double marriage = this.marriage;
        double education = this.education;
        creditModule4JNRCB.setCurrentAsset(current_asset);
        creditModule4JNRCB.setTotalLiabilities(total_liabilities);
        creditModule4JNRCB.setOwnerEquity(owner_equity);
        creditModule4JNRCB.setDisposableAssetMonthly(disposable_asset_monthly);
        creditModule4JNRCB.setIncomeAnnual(income_annual);
        creditModule4JNRCB.setMarriage(marriage);
        creditModule4JNRCB.setEducation(education);
        return creditModule4JNRCB;
    }
}
