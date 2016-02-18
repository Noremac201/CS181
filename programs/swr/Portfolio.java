package swr;

public class Portfolio
{
   private Double bondPercent;
   private Double inflationLinkedPercent;
   private Double stockPercent;
   private int retirementLength;
   private Double initialWithdrawalAmount;
   private Double cashPercent;
   private String marketHistoryFileName;

   public Portfolio(String[] args)
   {
      if (args.length != 1 && args.length != 12)
      {
         System.out.println("Please enter the correct number of arguments");
         System.exit(1);
      }
      for (int i = 0; i < args.length; i++)
      {
         if (args[i].charAt(0) == '-')
         {
            switch (args[i].charAt(1))
            {
            case 'h':
               printRequiredArgs();
               System.exit(0);
            case 'f':
               this.setMarketHistoryFileName(args[i + 1]);
               break;
            case 'b':
               this.setBondPercent(Double.parseDouble(args[i + 1]));
               break;
            case 's':
               this.setStockPercent(Double.parseDouble(args[i + 1]));
               break;
            case 'i':
               this.setInflationLinkedPercent(Double.parseDouble(args[i + 1]));
               break;
            case 'w':
               this.setInitialWithdrawalAmount(Double.parseDouble(args[i + 1]));
               break;
            case 'n':
               this.setRetirementLength(Integer.parseInt(args[i + 1]));
               break;
            default:
               break;

            }
         }
      }
   }

   public static void printRequiredArgs()
   {
      System.out.println("-f <filename>      File containing historical data.");
      System.out.println("-b <bonds>      Portfolio percent in bonds.");
      System.out.println("-s <stocks>      Portfolio percent in stocks.");
      System.out.println("-i <infl>    Portfolio percent in inflation linked.");
      System.out.println("-w <withdraw>      Initial withdrawal rate.");
      System.out.println("-n <years>      Years of retirement.");
      System.out.println("-h <help>       Prints the help and all arguments.");
      System.out.printf("%n Returns the historical success of a portfolio.%n");
   }

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
