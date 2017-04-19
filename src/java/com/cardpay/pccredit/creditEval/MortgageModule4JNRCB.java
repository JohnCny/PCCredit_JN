package com.cardpay.pccredit.creditEval;

/**
 * 抵押模型
 *
 * @author rankai
 * createTime 2017-04-2017/4/1 15:04
 */
public class MortgageModule4JNRCB {
    private double spouseIncomeAnnual;
    private double currentAsset;
    private double totalLiabilities;
    private double ownerEquity;
    private double disposableAssetMonthly;
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

    public double getCurrentAsset() {
        return currentAsset;
    }

    public void setCurrentAsset(double currentAsset) {
        this.currentAsset = currentAsset;
    }

    public double getTotalLiabilities() {
        return totalLiabilities;
    }

    public void setTotalLiabilities(double totalLiabilities) {
        this.totalLiabilities = totalLiabilities;
    }

    public double getOwnerEquity() {
        return ownerEquity;
    }

    public void setOwnerEquity(double ownerEquity) {
        this.ownerEquity = ownerEquity;
    }

    public double getDisposableAssetMonthly() {
        return disposableAssetMonthly;
    }

    public void setDisposableAssetMonthly(double disposableAssetMonthly) {
        this.disposableAssetMonthly = disposableAssetMonthly;
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

    // 模型计算方法
    public double getAmount() {
        normalization();
        if (disposableAssetMonthly <= -0.196) {
            if (housePrice <= 0.8682) {
                if (capitalAsset <= 2.9966) {
                    if (totalLiabilities < -0.6117) {
                        if (housePrice <= -0.4496) {
                            return 26.0;
                        }else {
                            return 43.5;
                        }
                    } else {
                        if (ownerEquity < 0.3383) {
                            return 22.25;
                        } else {
                            return 5.0;
                        }
                    }
                } else {
                    return 50.0;
                }
            } else {
                return 65.0;
            }
        } else {
            if (housePrice <= -.02603) {
                if (capitalAsset <= -0.0852) {
                    if (totalLiabilities <= 0.1117){
                        if (capitalAsset <= -0.2751) {
                            return 44.0;
                        } else {
                            return 70.0;
                        }
                    } else {
                        return 80.0;
                    }
                } else {
                    if (disposableAssetMonthly <= 1.2057) {
                        return 170.0;
                    } else {
                        return 130.0;
                    }
                }
            } else {
                if (ownerEquity <= -0.0097) {
                    if (operatingYears < -0.5265) {
                        return 90.0;
                    } else {
                        if (housePrice <= 1.4947) {
                            return 67.0;
                        } else {
                            return 20.0;
                        }
                    }
                } else {
                    if (totalLiabilities <= 0.4625) {
                        if (currentAsset <= 2.4709) {
                            return 50.0;
                        } else {
                            return 40.0;
                        }
                    } else {
                        if (operatingYears < -0.361) {
                            return 20.0;
                        } else {
                            return 15.0;
                        }
                    }
                }
            }
        }
    }

    // 参数归一化
    private void normalization() {
        double[][] meansAndSTD = {{30517.36364  ,1230696.104  ,732335.8182 , 3085470.75  ,73611.10294 , 803830.1136 ,
                2316192.598 , 6.590909091}, {80795.05036, 1595887.662,1044795.495,4891632.743  ,69449.71191 ,
                993712.6308 , 5025321.382  ,2.987231782}};
        this.currentAsset = (this.currentAsset - meansAndSTD[0][1]) / meansAndSTD[1][1];
        this.totalLiabilities = (this.totalLiabilities - meansAndSTD[0][2]) / meansAndSTD[1][2];
        this.ownerEquity = (this.ownerEquity - meansAndSTD[0][3]) / meansAndSTD[1][3];
        this.disposableAssetMonthly = (this.disposableAssetMonthly - meansAndSTD[0][4]) / meansAndSTD[1][4];
        this.spouseIncomeAnnual = (this.spouseIncomeAnnual - meansAndSTD[0][0]) / meansAndSTD[1][0];
        this.housePrice = (this.housePrice - meansAndSTD[0][5]) / meansAndSTD[1][5];
        this.capitalAsset = (this.capitalAsset - meansAndSTD[0][6]) / meansAndSTD[1][6];
        this.operatingYears = (this.operatingYears - meansAndSTD[0][7]) / meansAndSTD[1][7];
    }
}
