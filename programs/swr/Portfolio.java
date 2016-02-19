public class Portfolio
{
	private Double bondPercent;
	private Double inflationLinkedPercent;
	private Double stockPercent;
	private int retirementLength;
	private Double initialWithdrawalAmount;
	private Double cashPercent;
	private String marketHistoryFileName;

	/**
	 * Checks if all inputs are legal.
	 */
	public void checkLegal()
	{
		if (this.cashPercent < 0.0)
		{
			System.err.println("The percents add up to above 100%. Try Again.");
			System.exit(1);
		} else if (this.stockPercent < 0.0 || this.inflationLinkedPercent < 0.0
				|| this.bondPercent < 0.0)
		{
			System.err.println("Invalid input for Stock, Bond, or Infl. Linked");
			System.exit(1);
		}
	}

	/**
	 * Calculates the yearly profit for a specific year.
	 * 
	 * @param fileInfoArray
	 *           Array to get data from.
	 * @param withdrawalAmount
	 *           withdrawlAmount, entered by user.
	 * @param year
	 *           Specific year to calculate profit from.
	 * @param portfolioPercent
	 *           portfolioPercent, starts at 1.0.
	 * @return returns the amount of money made for a year in percent.
	 */
	public Double yearProfit(Double[][] fileInfoArray, Double withdrawalAmount,
			int year, Double portfolioPercent)
	{
		Double stockProfit = this.stockPercent * (fileInfoArray[year][3] + .02)
				* (portfolioPercent - withdrawalAmount);
		Double inflationLinkedProfit = this.inflationLinkedPercent
				* (fileInfoArray[year][2] + .005)
				* (portfolioPercent - withdrawalAmount);
		Double bondProfit = this.bondPercent * (fileInfoArray[year][1] + .005)
				* (portfolioPercent - withdrawalAmount);
		return stockProfit + inflationLinkedProfit + bondProfit;
	}

	/**
	 * Prints information about object.
	 */
	public void printInfo()
	{
		System.out.printf("Bond allocation: %.2f%%%n", this.bondPercent * 100);
		System.out.printf("Inflation-Linked allocation: %.2f%%%n",
				this.inflationLinkedPercent * 100);
		System.out.printf("Stock allocation: %.2f%%%n", this.stockPercent * 100);
		System.out.printf("Cash allocation: %.2f%%%n", this.cashPercent * 100);
		System.out.printf("Initial Withdraw Rate: %.2f%%%n",
				this.initialWithdrawalAmount * 100);
		System.out.printf("Years of Retirement: %d%n", this.retirementLength);
	}

	// Getters and Setters
	public Double getInflationLinkedPercent()
	{
		return inflationLinkedPercent;
	}

	public void setInflationLinkedPercent(Double inflationLinkedPercent)
	{
		this.inflationLinkedPercent = inflationLinkedPercent;
	}

	public Double getStockPercent()
	{
		return stockPercent;
	}

	public void setStockPercent(Double stockPercent)
	{
		this.stockPercent = stockPercent;
	}

	public int getRetirementLength()
	{
		return retirementLength;
	}

	public void setRetirementLength(int retirementLength)
	{
		this.retirementLength = retirementLength;
	}

	public Double getInitialWithdrawalAmount()
	{
		return initialWithdrawalAmount;
	}

	public void setInitialWithdrawalAmount(Double initialWithdrawalAmount)
	{
		this.initialWithdrawalAmount = initialWithdrawalAmount;
	}

	public Double getCashPercent()
	{
		return cashPercent;
	}

	public void setCashPercent(Double cashPercent)
	{
		this.cashPercent = cashPercent;
	}

	public Double getBondPercent()
	{
		return bondPercent;
	}

	public void setBondPercent(Double bondPercent)
	{
		this.bondPercent = bondPercent;
	}

	public String getMarketHistoryFileName()
	{
		return marketHistoryFileName;
	}

	public void setMarketHistoryFileName(String marketHistoryFileName)
	{
		this.marketHistoryFileName = marketHistoryFileName;
	}

}
