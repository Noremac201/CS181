//Sample command line arguments "-s 0.45 -b 0.25 -i 0.25 -w 0.04 -n 30 -f MarketData.csv"

package swr;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class SafeWithdrawalRate
{
   public static void main(String[] args) throws IOException
   {
      Portfolio portfolio = new Portfolio(args);
      String fileName = portfolio.getMarketHistoryFileName();
      Double[][] fileInfoArray = new Double[countLines(fileName) - 1][4];

      portfolio.setCashPercent(1 - (portfolio.getBondPercent()
            + portfolio.getInflationLinkedPercent()
            + portfolio.getStockPercent()));

      if (portfolio.getCashPercent() < 0.0)
      {
         System.err.println("The percents add up to above 100%. Try Again.");
         System.exit(1);
      }

      portfolio.printInfo();

      try
      {
         fileInfoArray = loadArray(fileName, fileInfoArray);
      } catch (FileNotFoundException e)
      {
         e.printStackTrace();
         System.out.println("File could not be opened for reading.");
      }

      System.out.printf("==> %.2f%% probability of success!",
            calculateSuccess(fileName, portfolio, fileInfoArray));

   }

   public static Double calculateSuccess(String fileName, Portfolio portfolio,
         Double[][] fileInfoArray) throws IOException
   {
      Double portfolioPercent;
      Double withdrawalAmount;
      int count = 0;
      int totalCount = 0;

      for (int startYear = 0; startYear < countLines(fileName)
            - portfolio.getRetirementLength(); startYear++)
      {
         portfolioPercent = 1.0;
         withdrawalAmount = portfolio.getInitialWithdrawalAmount();

         for (int year = startYear; year < startYear
               + portfolio.getRetirementLength() & portfolioPercent > 0; year++)
         {
            portfolioPercent = (portfolioPercent - withdrawalAmount)
                  + portfolio.yearProfit(fileInfoArray, withdrawalAmount, year,
                        portfolioPercent);
            withdrawalAmount *= (fileInfoArray[year][2] + 1.0);
         }
         totalCount++;
         if (portfolioPercent > 0)
         {
            count++;
         }
      }
      return (100.0 * count / totalCount);
   }

   public static Double[][] loadArray(String fileName, Double[][] fileInfoArray)
         throws FileNotFoundException
   {
      int i = 0;
      Scanner inFile = new Scanner(new File(fileName));

      inFile.useDelimiter(",|\\n");
      inFile.nextLine(); // To skip the first line.
      while (inFile.hasNextDouble())
      {
         for (int j = 0; j < 4; j++)
         {
            fileInfoArray[i][j] = inFile.nextDouble();
         }
         i++;
      }
      inFile.close();
      return fileInfoArray;
   }

   public static int countLines(String filename) throws IOException
   {
      InputStream is = new BufferedInputStream(new FileInputStream(filename));
      try
      {
         byte[] c = new byte[1024];
         int count = 0;
         int readChars = 0;
         boolean empty = true;
         while ((readChars = is.read(c)) != -1)
         {
            empty = false;
            for (int i = 0; i < readChars; ++i)
            {
               if (c[i] == '\n')
               {
                  ++count;
               }
            }
         }
         return (count == 0 && !empty) ? 1 : count;
      } finally
      {
         is.close();
      }
   }
}
