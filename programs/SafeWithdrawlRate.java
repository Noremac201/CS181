
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class SafeWithdrawlRate
{
   public static void main(String[] args) throws IOException
   {
      String fileName = parseArgs(args, 'f'); //"MarketData.csv";
      Double[][] fileInfoArray = new Double[countLines(fileName)-1][4];
      Double bondPercentage = Double.parseDouble(parseArgs(args, 'b'));//.25;
      Double inflationLinkedPercentage = Double.parseDouble(parseArgs(args, 'i'));//.25;
      Double stockPercentage = Double.parseDouble(parseArgs(args, 's'));//.45;
      int retirementLength = Integer.parseInt(parseArgs(args, 'n'));//30;
      Double withdrawlAmount = Double.parseDouble(parseArgs(args, 'w'));//.04;
      Double cashPercent = 1.0 - stockPercentage + inflationLinkedPercentage + bondPercentage;
      Double portfolioPercent = 1.0;
      int count = 0;
      int totalCount = 0;
      
      try
      {
         fileInfoArray = loadArray(fileName, fileInfoArray);
      } catch (FileNotFoundException e)
      {
         e.printStackTrace();
         System.out.println("File could not be opened for reading.");
      }
      for (int startYear = 0; startYear <= countLines(fileName) - retirementLength - 1; startYear++) {
         portfolioPercent = 1.0;
         withdrawlAmount = .04;

         for (int year = startYear; year < startYear + retirementLength & portfolioPercent > 0; year++) {            
            portfolioPercent = (portfolioPercent - withdrawlAmount) + yearProfit(stockPercentage, fileInfoArray, withdrawlAmount, year, portfolioPercent, inflationLinkedPercentage, bondPercentage);
            withdrawlAmount = withdrawlAmount * (fileInfoArray[year][2] + 1.0);
         }
         totalCount++;
         if (portfolioPercent > 0) {
            count++;
         }
      }
      System.out.println("==> " + ((double)count/(double)totalCount)*100 + "% probability of success!");
   }
   public static String parseArgs(String[] args, char argToParse) 
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
            {
               //things
               return null;
            }
            else if (args[i].charAt(1) == 'f' && argToParse == 'f')
            {
               return args[i+1];
            }
            else if (args[i].charAt(1) == 'b' && argToParse == 'b')
            {
               return args[i+1];
            }
            else if (args[i].charAt(1) == 's' && argToParse == 's')
            {
               return args[i+1];
            }
            else if (args[i].charAt(1) == 'i' && argToParse == 'i')
            {
               return args[i+1];
            }
            else if (args[i].charAt(1) == 'w' && argToParse == 'w')
            {
               return args[i+1];
            }
            else if (args[i].charAt(1) == 'n' && argToParse == 'n')
            {
               return args[i+1];
            }
         default:
            break;
         }
      }
      return null;
   }
   public static Double yearProfit (Double stockPercent, Double[][] fileInfoArray, Double initialWithdrawl, int year, Double portfolioPercent,Double inflationLinkedPercent, Double bondPercent)
   {
      Double stockProfit = stockPercent * (fileInfoArray[year][3] + .02) * (portfolioPercent-initialWithdrawl);
      Double inflationLinkedProfit = inflationLinkedPercent * (fileInfoArray[year][2] + .005) * (portfolioPercent-initialWithdrawl);
      Double bondProfit = bondPercent * (fileInfoArray[year][1] + .005) * (portfolioPercent-initialWithdrawl);
      return stockProfit + inflationLinkedProfit + bondProfit;
   }
   /*public static Double stockProfit(Double stockPercent, Double[][] fileInfoArray, Double initialWithdrawl, int year, Double portfolioPercent) {
      Double returnPercent = stockPercent * (fileInfoArray[year][3] + .02) * (portfolioPercent-initialWithdrawl);
      return returnPercent;
   }
   public static Double inflationLinkedProfit(Double inflationLinkedPercent, Double[][] fileInfoArray, Double initialWithdrawl, int year, Double portfolioPercent) {
      Double returnPercent = inflationLinkedPercent * (fileInfoArray[year][2] + .005) * (portfolioPercent-initialWithdrawl);
      return returnPercent;
   }
   public static Double bondProfit(Double bondPercent, Double[][] fileInfoArray, Double initialWithdrawl, int year, Double portfolioPercent) {
      Double returnPercent = bondPercent * (fileInfoArray[year][1] + .005) * (portfolioPercent-initialWithdrawl);
      return returnPercent;
   }
   */
   public static Double[][] loadArray(String fileName, Double[][] fileInfoArray) throws FileNotFoundException
   {
      int i = 0;

      Scanner inFile = new Scanner(new File(fileName));
      inFile.useDelimiter(",|\\n");
      inFile.nextLine(); // To skip the first line.
         while(inFile.hasNextDouble()) {
            for (int j = 0; j < 4; j++) {
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
