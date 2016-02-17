package swr;

public class Portfolio
{
   private Double bondPercent;
   private Double inflationLinkedPercent;
   private Double stockPercent;
   private int retirementLength;
   private Double initialWithdrawlAmount;
   private Double cashPercent;
   private String marketHistoryFileName;

   public Portfolio(String[] args) 
   {
      if (args.length != 1 && args.length != 12) {
         System.out.println(args.length);
         System.out.println("Please enter the correct number of arguments");
      }
      for (int i = 0; i < args.length; i++)
      {
         switch (args[i].charAt(0)) 
         {
         case '-':
            if (args[i].charAt(1) == 'h')
               //things
               break;
            else if (args[i].charAt(1) == 'f')
               this.setMarketHistoryFileName(args[i+1]);
            else if (args[i].charAt(1) == 'b')
               this.setBondPercent(Double.parseDouble(args[i+1]));
            else if (args[i].charAt(1) == 's')
               this.setStockPercent(Double.parseDouble(args[i+1]));
            else if (args[i].charAt(1) == 'i')
               this.setInflationLinkedPercent(Double.parseDouble(args[i+1]));
            else if (args[i].charAt(1) == 'w')
               this.setInitialWithdrawlAmount(Double.parseDouble(args[i+1]));
            else if (args[i].charAt(1) == 'n')
               this.setRetirementLength(Integer.parseInt(args[i+1]));
         default:
            break;
         }
      }
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

   public Double getInitialWithdrawlAmount()
   {
      return initialWithdrawlAmount;
   }

   public void setInitialWithdrawlAmount(Double initialWithdrawlAmount)
   {
      this.initialWithdrawlAmount = initialWithdrawlAmount;
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


   public Double yearProfit(Double[][] fileInfoArray, Double withdrawlAmount,
         int year, Double portfolioPercent)
   {
      Double stockProfit = this.stockPercent * (fileInfoArray[year][3] + .02)
            * (portfolioPercent - withdrawlAmount);
      Double inflationLinkedProfit = this.inflationLinkedPercent
            * (fileInfoArray[year][2] + .005)
            * (portfolioPercent - withdrawlAmount);
      Double bondProfit = this.bondPercent * (fileInfoArray[year][1] + .005)
            * (portfolioPercent - withdrawlAmount);
      return stockProfit + inflationLinkedProfit + bondProfit;
   }
}
