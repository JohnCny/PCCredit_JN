package com.cardpay.pccredit.creditEval;



/**
 * Created by pzwxy on 4/1/17.
 */
public class CreditModule4JNRCB {
    private double currentAsset;
    private double totalLiabilities;
    private double ownerEquity;
    private double disposableAssetMonthly;
    private double incomeAnnual;
    private double marriage;
    private double education;

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

    public double getIncomeAnnual() {
        return incomeAnnual;
    }

    public void setIncomeAnnual(double incomeAnnual) {
        this.incomeAnnual = incomeAnnual;
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

    public CreditModule4JNRCB(double currentAsset,
                              double totalLiabilities,
                              double ownerEquity,
                              double disposableAssetMonthly,
                              double incomeAnnual,
                              double marriage,
                              double education)
    {
        this.currentAsset = currentAsset;
        this.totalLiabilities = totalLiabilities;
        this.ownerEquity = ownerEquity;
        this.disposableAssetMonthly = disposableAssetMonthly;
        this.incomeAnnual = incomeAnnual;
        this.marriage = marriage;
        this.education = education;
    }

    public CreditModule4JNRCB() {}

    // 模型参数归一化
    private void normalization() {
        double[][] meansAndSTD = {{450331.4088,  303898.1458 , 649708.3125 , 18966.64229,  318880.9167},
                {622860.6697 , 696798.7145  ,725499.5358 , 16719.55606,  246566.8874}};
        this.currentAsset = (this.currentAsset - meansAndSTD[0][0]) / meansAndSTD[1][0];
        this.totalLiabilities = (this.totalLiabilities - meansAndSTD[0][1]) / meansAndSTD[1][1];
        this.ownerEquity = (this.ownerEquity - meansAndSTD[0][2]) / meansAndSTD[1][2];
        this.disposableAssetMonthly = (this.disposableAssetMonthly - meansAndSTD[0][3]) / meansAndSTD[1][3];
        this.incomeAnnual = (this.incomeAnnual - meansAndSTD[0][4]) / meansAndSTD[1][4];
    }

    // 模型计算方法
    public double getAmount() {
        normalization();
        if (this.disposableAssetMonthly < -0.0646) {
            if (this.ownerEquity <= -0.6318) {
                if (this.disposableAssetMonthly <= -0.9745) {
                    return 10.0;
                } else {
                    if (this.disposableAssetMonthly <= -0.4578) {
                        if (this.incomeAnnual <= -0.4455) {
                            return 5.0;
                        } else {
                            return 8.0;
                        }
                    } else {
                        return 10.0;
                    }
                }
            } else {
                if (this.incomeAnnual <= -1.0944) {
                    return 5.0;
                } else {
                    if (this.ownerEquity <= 0.7939) {
                        if (this.currentAsset <= 0.5917) {
                            return 9.3864;
                        } else {
                            return 7.0;
                        }
                    } else {
                        return 6.0;
                    }
                }
            }
        } else {
            if (this.incomeAnnual <= 1.7273) {
                if (this.currentAsset <= -0.5007) {
                    return 10.0;
                } else {
                    if (this.currentAsset <= -0.4022) {
                        return 20.0;
                    } else {
                        if (this.disposableAssetMonthly <= 1.2254) {
                            return 11.25;
                        } else {
                            return 16.6667;
                        }
                    }
                }
            } else {
                if (this.education <= 0.5) {
                    return 20.0;
                } else {
                    return 25.0;
                }
            }
        }
    }
}