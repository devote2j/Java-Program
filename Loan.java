
public class Loan {
	private double annualInterestRate;
	private int termInYears;
	private double loanAmount;
	private double monthlyPayment;
	private double totalPayment;
	
	public Loan(double annualInterestRate, int termInYears, double loanAmount, double monthlyPayment,
			double totalPayment) {
		super();
		this.annualInterestRate = annualInterestRate;
		this.termInYears = termInYears;
		this.loanAmount = loanAmount;
		this.monthlyPayment = monthlyPayment;
		this.totalPayment = totalPayment;
	}

	public Loan() {
		super();
	}

	public double getAnnualInterestRate() {
		return annualInterestRate;
	}

	public void setAnnualInterestRate(double annualInterestRate) {
		this.annualInterestRate = annualInterestRate;
	}

	public int getTermInYears() {
		return termInYears;
	}

	public void setTermInYears(int termInYears) {
		this.termInYears = termInYears;
	}

	public double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public double getMonthlyPayment() {
		return monthlyPayment;
	}

	public double getTotalPayment() {
		return totalPayment;
	}
	
	public void calcMonthlyPayment() {
		double monthlyInterestRate = annualInterestRate / 12;
		int termInMonths = termInYears * 12;
		double annuityFactor = ((1 - ( 1 / Math.pow((1 + monthlyInterestRate), termInMonths))) / monthlyInterestRate);
		monthlyPayment = loanAmount / annuityFactor;
	}
	
	public void calcTotalPayment() {
		totalPayment = monthlyPayment * termInYears * 12;
	}
}
