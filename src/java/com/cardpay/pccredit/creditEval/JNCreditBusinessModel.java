package com.cardpay.pccredit.creditEval;

import java.io.PrintStream;
import java.text.DecimalFormat;

public class JNCreditBusinessModel {
	private int badHabit;
	private int badPublicRecord;
	private int industryCategory;
	private int violationLaw;
	private int creditCardOverdueTime;
	private int creditCardOverdueCount;
	private int loanOverdueTime;
	private int loanOverdueCount;
	private int credit;
	private int creditQueryCount;
	private double applyAmount;
	private double annualDisposableCapital;
	private double annualIncome;
	private double ownersEquity;
	private double receivableRatio;
	private double quickRatio;
	private int businessYears;
	private int cooperationYears;
	private int storeSituation;
	private int mortgageRemaining;
	private int age;
	private int sex;
	private int education;
	private int residence;
	private int marriage;
	private int children;
	private int spouse;
	private int ownedPropertyQuantity;
	private int mortgagePropertyQuantity;
	private int ownedCarsQuantity;
	private Message result;

	public int getBadHabit() {
		return this.badHabit;
	}

	public void setBadHabit(int badHabit) {
		this.badHabit = badHabit;
	}

	public int getBadPublicRecord() {
		return this.badPublicRecord;
	}

	public void setBadPublicRecord(int badPublicRecord) {
		this.badPublicRecord = badPublicRecord;
	}

	public int getIndustryCategory() {
		return this.industryCategory;
	}

	public void setIndustryCategory(int industryCategory) {
		this.industryCategory = industryCategory;
	}

	public int getViolationLaw() {
		return this.violationLaw;
	}

	public void setViolationLaw(int violationLaw) {
		this.violationLaw = violationLaw;
	}

	public int getCreditCardOverdueTime() {
		return this.creditCardOverdueTime;
	}

	public void setCreditCardOverdueTime(int creditCardOverdueTime) {
		this.creditCardOverdueTime = creditCardOverdueTime;
	}

	public int getCreditCardOverdueCount() {
		return this.creditCardOverdueCount;
	}

	public void setCreditCardOverdueCount(int creditCardOverdueCount) {
		this.creditCardOverdueCount = creditCardOverdueCount;
	}

	public int getLoanOverdueTime() {
		return this.loanOverdueTime;
	}

	public void setLoanOverdueTime(int loanOverdueTime) {
		this.loanOverdueTime = loanOverdueTime;
	}

	public int getLoanOverdueCount() {
		return this.loanOverdueCount;
	}

	public void setLoanOverdueCount(int loanOverdueCount) {
		this.loanOverdueCount = loanOverdueCount;
	}

	public int getCredit() {
		return this.credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public int getCreditQueryCount() {
		return this.creditQueryCount;
	}

	public void setCreditQueryCount(int creditQueryCount) {
		this.creditQueryCount = creditQueryCount;
	}

	

	public double getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(double applyAmount) {
		this.applyAmount = applyAmount;
	}

	public void setResult(Message result) {
		this.result = result;
	}

	public double getAnnualDisposableCapital() {
		return this.annualDisposableCapital;
	}

	public void setAnnualDisposableCapital(double annualDisposableCapital) {
		this.annualDisposableCapital = annualDisposableCapital;
	}

	public double getAnnualIncome() {
		return this.annualIncome;
	}

	public void setAnnualIncome(double annualIncome) {
		this.annualIncome = annualIncome;
	}

	public double getOwnersEquity() {
		return this.ownersEquity;
	}

	public void setOwnersEquity(double ownersEquity) {
		this.ownersEquity = ownersEquity;
	}

	public double getReceivableRatio() {
		return this.receivableRatio;
	}

	public void setReceivableRatio(double receivableRatio) {
		this.receivableRatio = receivableRatio;
	}

	public double getQuickRatio() {
		return this.quickRatio;
	}

	public void setQuickRatio(double quickRatio) {
		this.quickRatio = quickRatio;
	}

	public int getBusinessYears() {
		return this.businessYears;
	}

	public void setBusinessYears(int businessYears) {
		this.businessYears = businessYears;
	}

	public int getCooperationYears() {
		return this.cooperationYears;
	}

	public void setCooperationYears(int cooperationYears) {
		this.cooperationYears = cooperationYears;
	}

	public int getStoreSituation() {
		return this.storeSituation;
	}

	public void setStoreSituation(int storeSituation) {
		this.storeSituation = storeSituation;
	}

	public int getMortgageRemaining() {
		return this.mortgageRemaining;
	}

	public void setMortgageRemaining(int mortgageRemaining) {
		this.mortgageRemaining = mortgageRemaining;
	}

	public int getAge() {
		return this.age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getSex() {
		return this.sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getEducation() {
		return this.education;
	}

	public void setEducation(int education) {
		this.education = education;
	}

	public int getResidence() {
		return this.residence;
	}

	public void setResidence(int residence) {
		this.residence = residence;
	}

	public int getMarriage() {
		return this.marriage;
	}

	public void setMarriage(int marriage) {
		this.marriage = marriage;
	}

	public int getChildren() {
		return this.children;
	}

	public void setChildren(int children) {
		this.children = children;
	}

	public int getSpouse() {
		return this.spouse;
	}

	public void setSpouse(int spouse) {
		this.spouse = spouse;
	}

	public int getOwnedPropertyQuantity() {
		return this.ownedPropertyQuantity;
	}

	public void setOwnedPropertyQuantity(int ownedPropertyQuantity) {
		this.ownedPropertyQuantity = ownedPropertyQuantity;
	}

	public int getMortgagePropertyQuantity() {
		return this.mortgagePropertyQuantity;
	}

	public void setMortgagePropertyQuantity(int mortgagePropertyQuantity) {
		this.mortgagePropertyQuantity = mortgagePropertyQuantity;
	}

	public int getOwnedCarsQuantity() {
		return this.ownedCarsQuantity;
	}

	public void setOwnedCarsQuantity(int ownedCarsQuantity) {
		this.ownedCarsQuantity = ownedCarsQuantity;
	}

	public JNCreditBusinessModel() {
	}

	public JNCreditBusinessModel(int badHabit, int badPublicRecord,
			int industryCategory, int violationLaw, int creditCardOverdueTime,
			int creditCardOverdueCount, int loanOverdueTime,
			int loanOverdueCount, int credit, int creditQueryCount,
			int applyAmount, double annualDisposableCapital,
			double annualIncome, double ownersEquity, double receivableRatio,
			double quickRatio, int businessYears, int cooperationYears,
			int storeSituation, int mortgageRemaining, int age, int sex,
			int education, int residence, int marriage, int children,
			int spouse, int ownedPropertyQuantity,
			int mortgagePropertyQuantity, int ownedCarsQuantity) {
		this.badHabit = badHabit;
		this.badPublicRecord = badPublicRecord;
		this.industryCategory = industryCategory;
		this.violationLaw = violationLaw;
		this.creditCardOverdueTime = creditCardOverdueTime;
		this.creditCardOverdueCount = creditCardOverdueCount;
		this.loanOverdueTime = loanOverdueTime;
		this.loanOverdueCount = loanOverdueCount;
		this.credit = credit;
		this.creditQueryCount = creditQueryCount;
		this.applyAmount = applyAmount;
		this.annualDisposableCapital = annualDisposableCapital;
		this.annualIncome = annualIncome;
		this.ownersEquity = ownersEquity;
		this.receivableRatio = receivableRatio;
		this.quickRatio = quickRatio;
		this.businessYears = businessYears;
		this.cooperationYears = cooperationYears;
		this.storeSituation = storeSituation;
		this.mortgageRemaining = mortgageRemaining;
		this.age = age;
		this.sex = sex;
		this.education = education;
		this.residence = residence;
		this.marriage = marriage;
		this.children = children;
		this.spouse = spouse;
		this.ownedPropertyQuantity = ownedPropertyQuantity;
		this.mortgagePropertyQuantity = mortgagePropertyQuantity;
		this.ownedCarsQuantity = ownedCarsQuantity;
		setResult();
	}

	public Message getResult() {
		return this.result;
	}

	public void setResult() {
		if (accessJudgment()) {
			this.result = new Message();
			experienceEvaluation();
			calculateCreditInterval();
		}
	}

	private void experienceEvaluation() {
		double quickRatioCoefficient;
		if (this.quickRatio < 0.3D) {
			quickRatioCoefficient = 0.8D;
		} else {
			if (this.quickRatio < 0.5D) {
				quickRatioCoefficient = 0.9D;
			} else {
				quickRatioCoefficient = 1.0D;
			}
		}
		double businessYearsCoefficient;
		if (this.businessYears <= 1) {
			businessYearsCoefficient = 0.8D;
		} else {
			if (this.businessYears <= 2) {
				businessYearsCoefficient = 0.9D;
			} else {
				businessYearsCoefficient = 1.0D;
			}
		}
		double cooperationYearsCoefficient;
		if (this.cooperationYears < 1) {
			cooperationYearsCoefficient = 0.9D;
		} else {
			if (this.cooperationYears < 2) {
				cooperationYearsCoefficient = 0.95D;
			} else {
				cooperationYearsCoefficient = 1.0D;
			}
		}
		double storeSituationCoefficient;
		if (this.storeSituation == 0) {
			storeSituationCoefficient = 1.0D;
		} else {
			if (this.storeSituation == 1) {
				storeSituationCoefficient = 0.95D;
			} else {
				if (this.storeSituation == 2) {
					storeSituationCoefficient = 0.9D;
				} else {
					storeSituationCoefficient = 0.85D;
				}
			}
		}
		double ageCoefficient;
		if (this.age < 30) {
			ageCoefficient = 0.8D;
		} else {
			if (this.age <= 45) {
				ageCoefficient = 1.0D;
			} else {
				ageCoefficient = 0.8D;
			}
		}
		double marriageCoefficient;
		if (this.marriage == 0) {
			marriageCoefficient = 1.0D;
		} else {
			if (this.marriage == 1) {
				marriageCoefficient = 0.9D;
			} else {
				marriageCoefficient = 0.8D;
			}
		}
		double informationSynthesisCoefficient = quickRatioCoefficient
				* businessYearsCoefficient * cooperationYearsCoefficient
				* storeSituationCoefficient * ageCoefficient
				* marriageCoefficient;

		double topBase = informationSynthesisCoefficient
				* this.annualDisposableCapital;

		double top = topBase > this.annualDisposableCapital * 0.7D ? this.annualDisposableCapital * 0.7D
				: topBase;
		top = top > this.applyAmount ? this.applyAmount : top;
		DecimalFormat decimalFormat = new DecimalFormat(".#");
		top = Double.parseDouble(decimalFormat.format(top));
		this.result.setTop(top);
		this.result.setFlag(0);
		this.result.setRefuseReason(null);
	}

	private void calculateCreditInterval() {
		normalization();
		double bottom;
		if (this.annualDisposableCapital <= -0.8036D) {
			if (this.businessYears <= -0.8007D) {
				bottom = 0.0D;
			} else {
				if (this.annualDisposableCapital <= -0.8325D) {
					if (this.annualDisposableCapital <= -0.8494D) {
						bottom = 3.2415D;
					} else {
						bottom = 3.9221D;
					}
				} else {
					if (this.cooperationYears <= -1.1607D) {
						bottom = 12.0906D;
					} else {
						bottom = 4.7853D;
					}
				}
			}
		} else {
			if (this.marriage <= 0.6207D) {
				if (this.annualDisposableCapital <= -0.7459D) {
					if (this.annualDisposableCapital <= -0.7773D) {
						bottom = 5.888D;
					} else {
						bottom = 6.9072D;
					}
				} else {
					if (this.age <= -0.8529D) {
						bottom = 7.1751D;
					} else {
						bottom = 8.4384D;
					}
				}
			} else {
				if (this.age <= -0.8529D) {
					if (this.quickRatio <= -1.2903D) {
						bottom = 5.174D;
					} else {
						bottom = 6.147D;
					}
				} else {
					if (this.quickRatio <= -1.2916D) {
						bottom = 6.1115D;
					} else {
						bottom = 7.1865D;
					}
				}
			}
		}
		bottom = bottom > this.applyAmount ? this.applyAmount : bottom;
		DecimalFormat decimalFormat = new DecimalFormat(".#");
		bottom = Double.parseDouble(decimalFormat.format(bottom));
		if (this.result.getTop() < bottom) {
			this.result.setBottom(this.result.getTop());
			this.result.setTop(bottom);
		} else {
			this.result.setBottom(bottom);
		}
	}

	private boolean accessJudgment() {
		if (this.badHabit == 1) {
			this.result = new Message(0.0D, 0.0D, 1, "有不良嗜好");
			return false;
		}
		if (this.badPublicRecord == 1) {
			this.result = new Message(0.0D, 0.0D, 1, "有不良公共记录");
			return false;
		}
		if (this.industryCategory == 0) {
			this.result = new Message(0.0D, 0.0D, 1, "所属行业为慎入行业");
			return false;
		}
		if (this.violationLaw == 1) {
			this.result = new Message(0.0D, 0.0D, 1, "有违法违纪行为");
			return false;
		}
		if (this.creditCardOverdueCount > 8) {
			this.result = new Message(0.0D, 0.0D, 1, "2年内信用卡逾期次数超过8次");
			return false;
		}
		if (this.creditCardOverdueTime > 60) {
			this.result = new Message(0.0D, 0.0D, 1, "2年内信用卡逾期天数超过60天");
			return false;
		}
		if (this.loanOverdueCount > 8) {
			this.result = new Message(0.0D, 0.0D, 1, "2年内贷款逾期次数超过8次");
			return false;
		}
		if (this.loanOverdueTime > 60) {
			this.result = new Message(0.0D, 0.0D, 1, "2年内贷款逾期天数超过60天");
			return false;
		}
		if (this.credit == 1) {
			this.result = new Message(0.0D, 0.0D, 1, "信用状况为不正常");
			return false;
		}
		if (this.creditQueryCount > 5) {
			this.result = new Message(0.0D, 0.0D, 1, "近半年征信被查询超过5次");
			return false;
		}
		if (this.age < 18) {
			this.result = new Message(0.0D, 0.0D, 1, "年龄小于18周岁");
			return false;
		}
		if (this.residence == 0) {
			if (this.businessYears < 1) {
				this.result = new Message(0.0D, 0.0D, 1, "济南本地户籍，但经营年限少于1年");
				return false;
			}
		} else if (this.residence == 1) {
			if ((this.ownedPropertyQuantity == 0)
					&& (this.mortgagePropertyQuantity == 0)
					&& (this.businessYears < 2)) {
				this.result = new Message(0.0D, 0.0D, 1,
						"本省外地户籍，但本人（或配偶）在济南无房产，或者经营年限低于2年");
				return false;
			}
		} else if ((this.ownedPropertyQuantity == 0)
				&& (this.mortgagePropertyQuantity == 0)
				&& (this.businessYears < 3)) {
			this.result = new Message(0.0D, 0.0D, 1,
					"外省户籍，但本人（或配偶）在济南无房产，或者经营年限低于3年");
			return false;
		}
		return true;
	}

	public void normalization() {
		double[] means = { 50.872031710000002D, 168.1210691D, 1.992136515D,
				5.742382849D, 41.049686569999999D, 0.994029465D };
		double[] std = { 53.866580999999996D, 207.93298010000001D,
				1.156503309D, 4.516504024D, 13.54108943D, 0.815175867D };
		this.annualDisposableCapital = ((this.annualDisposableCapital - means[0]) / std[0]);
		this.cooperationYears = ((int) ((this.cooperationYears - means[1]) / std[1]));
		this.quickRatio = ((this.quickRatio - means[0]) / std[0]);
		this.cooperationYears = ((int) ((this.cooperationYears - means[0]) / std[0]));
		this.age = ((int) ((this.age - means[0]) / std[0]));
		this.marriage = ((int) ((this.marriage - means[0]) / std[0]));
	}

	public static void main(String[] args) {
		/*JNCreditBusinessModel jnCreditBusinessModel = new JNCreditBusinessModel(
				0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 18, 210.7393285D,
				620.17314280000005D, 208.1977364D, 0.173248683D, 1.746595453D,
				18, 11, 1, 1, 64, 1, 1, 0, 2, 2, 2, 0, 0, 0);*/

		/*JNCreditBusinessModel jnCreditBusinessModel = new JNCreditBusinessModel(
				0,
				0,
				1,
				0,
				0,
				0,
				0,
				0,
				0,
				0,
				18,
				210.7393285,
				620.17314280000005,
				208.1977364,
				0.173248683,
				1.746595453,
				18,
				11,
				1,
				1,
				64,
				1,
				1,
				0,
				2,
				2,
				2,
				0,
				0,
				0);
*/
		JNCreditBusinessModel jnCreditBusinessModel = new JNCreditBusinessModel(
				0,
				0,
				1,
				0,
				0,
				0,
				0,
				0,
				0,
				0,
				18,
				12.00,
				12.00,
				12.00,
				0.3,
				1.7,
				5,
				1,
				0,
				0,
				31,
				0,
				0,
				0,
				0,
				0,
				0,
				0,
				0,
				0);

		System.out.println(jnCreditBusinessModel.getResult());
		
		Message msg = jnCreditBusinessModel.getResult();
		
		System.out.println(msg.getFlag());
		System.out.println(msg.getBottom());
		System.out.println(msg.getTop());
		System.out.println(msg.getRefuseReason());
	}
}
