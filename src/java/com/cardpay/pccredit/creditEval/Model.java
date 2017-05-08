package com.cardpay.pccredit.creditEval;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pzwxy on 4/1/17.
 */
public class Model {
    private String message;
    private int modelType;
    private double value;
    private Map<String, String> modelAttributes;

    public double getValue() {
        setValue();
        return value;
    }

    public void setValue() {
        if (this.getModelType() == 0) {
            CreditModule4JNRCBDef credit = new CreditModule4JNRCBDef();
            try {
                credit.setEducation(Double.parseDouble(getModelAttributes().get("education")));
            } catch (Exception e) {
                credit.setEducation(0);
            }
            try {
                credit.setCashAndDepositsWithBanks(Double.parseDouble(getModelAttributes()
                        .get("cashAndDepositsWithBanks")));
            } catch (Exception e) {
                credit.setCashAndDepositsWithBanks(0);
            }
            try {
                credit.setAccountsReceivable(Double.parseDouble(getModelAttributes().get("accountsReceivable")));
            } catch (Exception e) {
                credit.setAccountsReceivable(0);
            }
            try {
                credit.setPrepaidAccounts(Double.parseDouble(getModelAttributes().get("prepaidAccounts")));
            } catch (Exception e) {
                credit.setPrepaidAccounts(0);
            }
            try {
                credit.setInventory(Double.parseDouble(getModelAttributes().get("inventory")));
            } catch (Exception e) {
                credit.setInventory(0);
            }
            try {
                credit.setShortTermLiabilities(Double.parseDouble(getModelAttributes().get("shortTermLiabilities")));
            } catch (Exception e) {
                credit.setShortTermLiabilities(0);
            }
            try {
                credit.setLongTermLiabilities(Double.parseDouble(getModelAttributes().get("longTermLiabilities")));
            } catch (Exception e) {
                credit.setLongTermLiabilities(0);
            }
            try {
                credit.setOtherLiabilities(Double.parseDouble(getModelAttributes().get("otherLiabilities")));
            } catch (Exception e) {
                credit.setOtherLiabilities(0);
            }
            try {
                credit.setContingentLiabilities(Double.parseDouble(getModelAttributes().get("contingentLiabilities")));
            } catch (Exception e) {
                credit.setContingentLiabilities(0);
            }
            try {
                credit.setOwnerEquity(Double.parseDouble(getModelAttributes().get("ownerEquity")));
            } catch (Exception e) {
                credit.setOwnerEquity(0);
            }
            try {
                credit.setRetainedProfitsMonthly(Double.parseDouble(getModelAttributes().
                        get("retainedProfitsMonthly")));
            } catch (Exception e) {
                credit.setRetainedProfitsMonthly(0);
            }
            try {
                credit.setHouseholdExpensesMonthly(Double.parseDouble(getModelAttributes().
                        get("householdExpensesMonthly")));
            } catch (Exception e) {
                credit.setHouseholdExpensesMonthly(0);
            }
            try {
                credit.setPaymentInstalmentsMonthly(Double.parseDouble(getModelAttributes().
                        get("paymentInstalmentsMonthly")));
            } catch (Exception e) {
                credit.setPaymentInstalmentsMonthly(0);
            }
            try {
                credit.setOtherExpensesMonthly(Double.parseDouble(getModelAttributes().get("otherExpensesMonthly")));
            } catch (Exception e) {
                credit.setOtherExpensesMonthly(0);
            }
            try {
                credit.setOtherIncomeMonthly(Double.parseDouble(getModelAttributes().get("otherIncomeMonthly")));
            } catch (Exception e) {
                credit.setOtherIncomeMonthly(0);
            }
            try {
                credit.setSpouseIncomeAnnual(Double.parseDouble(getModelAttributes().get("spouseIncomeAnnual")));
            } catch (Exception e) {
                credit.setSpouseIncomeAnnual(0);
            }
            try {
                credit.setMarriage(Double.parseDouble(getModelAttributes().get("marriage")));
            } catch (Exception e) {
                credit.setMarriage(0);
            }

            this.value = credit.getCreditModel().getAmount();
        } else {
            MortgageModule4JNRCBDef mortgage = new MortgageModule4JNRCBDef();

            try {
                mortgage.setSpouseIncomeAnnual(Double.parseDouble(getModelAttributes().get("spouseIncomeAnnual")));
            } catch (Exception e) {
                mortgage.setSpouseIncomeAnnual(0);
            }
            try {
                mortgage.setCashAndDepositsWithBanks(Double.parseDouble(getModelAttributes()
                        .get("cashAndDepositsWithBanks")));
            } catch (Exception e) {
                mortgage.setCashAndDepositsWithBanks(0);
            }
            try {
                mortgage.setAccountsReceivable(Double.parseDouble(getModelAttributes().get("accountsReceivable")));
            } catch (Exception e) {
                mortgage.setAccountsReceivable(0);
            }
            try {
                mortgage.setPrepaidAccounts(Double.parseDouble(getModelAttributes().get("prepaidAccounts")));
            } catch (Exception e) {
                mortgage.setPrepaidAccounts(0);
            }
            try {
                mortgage.setInventory(Double.parseDouble(getModelAttributes().get("inventory")));
            } catch (Exception e) {
                mortgage.setInventory(0);
            }
            try {
                mortgage.setShortTermLiabilities(Double.parseDouble(getModelAttributes().get("shortTermLiabilities")));
            } catch (Exception e) {
                mortgage.setShortTermLiabilities(0);
            }
            try {
                mortgage.setLongTermLiabilities(Double.parseDouble(getModelAttributes().get("longTermLiabilities")));
            } catch (Exception e) {
                mortgage.setLongTermLiabilities(0);
            }
            try {
                mortgage.setOtherLiabilities(Double.parseDouble(getModelAttributes().get("otherLiabilities")));
            } catch (Exception e) {
                mortgage.setOtherLiabilities(0);
            }
            try {
                mortgage.setContingentLiabilities(Double.parseDouble(getModelAttributes().get("contingentLiabilities")));
            } catch (Exception e) {
                mortgage.setContingentLiabilities(0);
            }
            try {
                mortgage.setOwnerEquity(Double.parseDouble(getModelAttributes().get("ownerEquity")));
            } catch (Exception e) {
                mortgage.setOwnerEquity(0);
            }
            try {
                mortgage.setRetainedProfitsMonthly(Double.parseDouble(getModelAttributes().
                        get("retainedProfitsMonthly")));
            } catch (Exception e) {
                mortgage.setRetainedProfitsMonthly(0);
            }
            try {
                mortgage.setHouseholdExpensesMonthly(Double.parseDouble(getModelAttributes().
                        get("householdExpensesMonthly")));
            } catch (Exception e) {
                mortgage.setHouseholdExpensesMonthly(0);
            }
            try {
                mortgage.setPaymentInstalmentsMonthly(Double.parseDouble(getModelAttributes().
                        get("paymentInstalmentsMonthly")));
            } catch (Exception e) {
                mortgage.setPaymentInstalmentsMonthly(0);
            }
            try {
                mortgage.setOtherExpensesMonthly(Double.parseDouble(getModelAttributes().get("otherExpensesMonthly")));
            } catch (Exception e) {
                mortgage.setOtherExpensesMonthly(0);
            }
            try {
                mortgage.setOtherIncomeMonthly(Double.parseDouble(getModelAttributes().get("otherIncomeMonthly")));
            } catch (Exception e) {
                mortgage.setOtherIncomeMonthly(0);
            }
            try {
                mortgage.setHousePrice(Double.parseDouble(getModelAttributes().get("housePrice")));
            } catch (Exception e) {
                mortgage.setHousePrice(0);
            }
            try {
                mortgage.setCapitalAsset(Double.parseDouble(getModelAttributes().get("capitalAsset")));
            } catch (Exception e) {
                mortgage.setCapitalAsset(0);
            }
            try {
                mortgage.setOperatingYears(Double.parseDouble(getModelAttributes().get("operatingYears")));
            } catch (Exception e) {
                mortgage.setCapitalAsset(0);
            }
            try {
                mortgage.setMarriage(Double.parseDouble(getModelAttributes().get("marriage")));
            } catch (Exception e) {
                mortgage.setMarriage(0);
            }
            try {
                mortgage.setEducation(Double.parseDouble(getModelAttributes().get("education")));
            } catch (Exception e) {
                mortgage.setEducation(0);
            }
            this.value = mortgage.getMortgageModel().getAmount();
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getModelType() { return modelType; }

    public void setModelType(int modelType) { this.modelType = modelType; }

    public void setModelAttributes(Map<String, String> modelAttributes) { this.modelAttributes = modelAttributes; }

    public Map<String, String> getModelAttributes() { return modelAttributes; }

    // 以下代码仅供测试和使用实例
    public static void main(String[] args) {
        // 这里给出了四十多条数据，使用时实例化Model对象，指定它的模型类型modelType，0为信用类贷款，1为抵押类贷款，并将个人信息
        // 封装成一个hashMap<String，String>，以参数形式传递给model对象，调用getValue方法，即可得到相应的预测值。
        /**
         * 每条数据对应一个人的信息，字段对应于mortgage模型
         *  private double spouseIncomeAnnual;  //配偶年收入
            private double cashAndDepositsWithBanks;//现金及银行存款
            private double accountsReceivable;//应收账款
            private double prepaidAccounts;//预付账款
            private double inventory;//存货
            private double shortTermLiabilities;//短期负债
            private double longTermLiabilities;//长期负债
            private double otherLiabilities;//其他负债
            private double contingentLiabilities;//或有负债
            private double ownerEquity;//所有者权益
            private double retainedProfitsMonthly;//月平均净利润
            private double householdExpensesMonthly;//月平均家庭开支
            private double paymentInstalmentsMonthly;//月平均分期付款
            private double otherExpensesMonthly;//月平均其他开支
            private double otherIncomeMonthly;//月平均其他收入
            private double housePrice;//房屋购买价格
            private double capitalAsset;//固定资产
            private double operatingYears;//经营年限
            private double marriage;//婚姻
            private double education;//学历
         */
        double[][] mortgage = {
                {100000,187918.12,70000,0,0,180785.5,0,0,100000,605332.62,309696.2125,15000,11715,0,0,396000,5,1,0},
                {0,26492,1000000,0,610000,318652,0,0,0,2703020.3,81104.16667,6833.333333,0,0,0,1366430.3,6,1,0},
                {0,0 ,3004607,0,2705441,697106,729524,900000,0,4453010,188358.0113,15000,0,0,0,989000,3,1,1},
                {0,9886,0,0,102700,0,70000,0,0,174092,28532.66667,4000,0,0,0,121506,0,1,0},
                {0,1024.56,0,0,822,0,0,0,0,89403.56,6634,1000,0,0,0,22157,1,0,0},
                {0,16120.81,617306,0,2014577.71,1790044,281943,0,0,1611547.52,176606.75,7000,3987,0,0,1027430,12,1,0},
                {0,274810,408214,0,758500,248449,0,0,0,1238075,87363.16667,5000,3066.25,1683.333333,0,0,4,1,0},
                {0,100188.9,0,0,0,3383,487494,0,0,1219711.9,0,0,0,0,0,638400,0,0,1},
                {0,561582,0,0,0,371178,0,0,0,4970404,70704.83333,10000,4273,0,0,1300000,2,1,0},
                {100000,334213,0,0,0,106175,0,0,0,3765438,91680,10000,2857,0,7500,57400,2,1,0},
                {0,5183,11380,0,18560,108817,96000,0,0,142606,17678,3591.666667,3863,2158.3,1541.666667,132300,1,1,0},
                {104076,30205,0,0,0,136352,372624,0,0,2842139,0,0,0,0,0,3320910,0,1,0},
                {0,169210,414288,54000,940522.5,30689,0,0,1153900,1464203.53,66312.07,7091.666667,0,0,0,1302780,5,1,0},
                {0,42500,5856,0,260000,796337,0,0,0,546397,21087.80917,3500,0,0,0,964378,3,1,0},
                {80000,19366,0,0,3433126,62939,0,0,0,3829553,51001.82333,5277.777778,0,0,10833.33333,440000,2,1,1},
                {100000,7300,0,0,0,329220,0,0,0,978080,18997.91667,4000,1710,0,8747.940833,1300000,0,1,0},
                {0,32355,0,0,30000,202193,737387,0,0,674927,34416.03333,3000,3044,0,0,1520996,2,1,0},
                {0,29599,316897,0,325000,125000,586289,0,0,2064787,109465.6667,6330,3399.5,0,0,2104580,4,1,0},
                {0,107489,120576,0,30000,86315,0,0,0,411750,29616.66667,3083.333333,0,0,0,240000,6,1,0},
                {0,77548,59152,0,3610723,954031,604668,0,0,3243543,96487.5,7500,14407,0,0,1038708,3,1,0},
                {0,75555,39285,0,1100000,279427,500000,0,0,831223,37043.58333,3000,2192.166667,0,0,50960,0,1,0},
                {0,86890.1,45300,0,63886,5891,0,0,0,208168.1,13266.66667,4587.5,0,0,0,2400,10,0,0},
                {400000,86830,272896,0,1024303,455791,537208,0,0,2728962.43,64832,5000,10397,0,3308.5,2283968,15,1,0},
                {0,89279,0,0,1136008,323415,681037,0,0,1647206,37624.27583,5000,3820,0,0,1419260,3,1,0},
                {0,48767,0,0,0,122589,0,0,200000,886178,0,0,0,0,0,1160000,0,0,1},
                {12000,57429.11,0,0,0,4028,0,0,0,1298401.11,0,0,0,0,0,1245000,0,1,0},
                {0,41454,105303,0,107225,5511,0,0,0,294596,44528.08333,5000,0,1400,0,37800,11,1,0},
                {200000,26565,232600,0,0,57965,0,0,0,304430,33875.5,9000,0,0,0,23230,0,1,1},
                {0,594944.26,0,0,600000,0,0,0,0,2002444.26,117165.525,8000,0,0,3166.666667,807500,0,1,0},
                {24000,240424.95,0,0,0,1373,892180,0,0,1395855.95,74850,10000,0,0,0,2048984,0,1,0},
                {102000,623650.23,0,0,0,504766,155955,0,0,1916446.23,0,0,0,0,0,1953517,0,1,0},
                {72000,479050,0,0,0,0,0,0,0,1507142,228238.0833,20000,0,0,0,1028092,0,1,0},
                {0,141567,573888,554400,1415858.73,1667360,0,0,0,1100553.73,86953.44167,8000,1747,0,0,77200,6,1,0},
                {0,695720,0,0,1546296,1159531,0,0,90194,1132624,60603.25,6000,6174.75,0,0,132000,0,1,0},
                {0,39859.87,459005,0,200000,21799,108482,0,0,838606.87,37884.66667,7000,2721.333333,0,0,202500,4,1,0},
                {0,40543.95,0,0,0,1773,67157,0,100000,261313.95,93789.83333,10277.77778,0,0,0,389700,3,1,0},
                {44000,54925.72,0,0,193930,191663,14127,0,0,174259.72,47666.33333,3225,1965.083333,0,0,16400,2,1,1},
                {0,10647.34,0,0,23384,4754,181667,0,0,468876.34,25293.37167,4000,3847.5,3000,1800,614758,2.6,1,0},
                {80000,35757.15,88000,0,100000,765091,0,0,0,654666,21752.08667,4000,3076,0,3120.904333,1180000,4,1,1},
                {0,24381,972685,0,1023000,2248445,0,0,0,3910321,118905.4167,17591,3701.75,3430.5,1804.8,4138700,16,1,0},
                {0,103122.15,0,0,11024,0,0,0,0,1802901.15,0,0,0,0,0,1558450,6,1,0},
                {0,690000,207200,0,200000,681873,271044,0,0,1042783,66800,4000,5984,0,0,176000,4,1,0},
                {0,216049,115865.5,0,325005,33392,0,0,0,1926202.5,173830.9817,16083.33333,0,0,0,764820,3,0,0},
                {36000,134687,117259,0,0,50566,585905,0,0,586660,33382.25333,3000,3290,2000,3000,967018,3,1,0}
        };
        // 指定使用哪一种模型
        int modelType = 1;
        // 组装参数的HashMap
        Map<String, String> attributes = new HashMap<String, String>();
        Model model = new Model();

        // 为参数赋值，并计算结果
        for (double[] item: mortgage) {
            attributes.put("spouseIncomeAnnual", String.valueOf(item[0]));
            attributes.put("cashAndDepositsWithBanks", String.valueOf(item[1]));
            attributes.put("accountsReceivable", String.valueOf(item[2]));
            attributes.put("prepaidAccounts", String.valueOf(item[3]));
            attributes.put("inventory", String.valueOf(item[4]));
            attributes.put("shortTermLiabilities", String.valueOf(item[5]));
            attributes.put("longTermLiabilities", String.valueOf(item[6]));
            attributes.put("otherLiabilities", String.valueOf(item[7]));
            attributes.put("contingentLiabilities", String.valueOf(item[8]));
            attributes.put("ownerEquity", String.valueOf(item[9]));
            attributes.put("retainedProfitsMonthly", String.valueOf(item[10]));
            attributes.put("householdExpensesMonthly", String.valueOf(item[11]));
            attributes.put("paymentInstalmentsMonthly", String.valueOf(item[12]));
            attributes.put("otherExpensesMonthly", String.valueOf(item[13]));
            attributes.put("otherIncomeMonthly", String.valueOf(item[14]));
            attributes.put("capitalAsset", String.valueOf(item[15]));
            attributes.put("operatingYears", String.valueOf(item[16]));
            attributes.put("marriage", String.valueOf(item[17]));
            attributes.put("education", String.valueOf(item[18]));

            model.setModelType(modelType);
            model.setModelAttributes(attributes);
            System.out.println(model.getValue());
        }
    }
}
