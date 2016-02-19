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
		Portfolio portfolio = new Portfolio();
		parseArgs(args, portfolio);

		String fileName = portfolio.getMarketHistoryFileName();
		int yearsInHistory = countLines(fileName) - 1;

		portfolio.setCashPercent(1.0 - (portfolio.getBondPercent()
				+ portfolio.getInflationLinkedPercent()
				+ portfolio.getStockPercent()));
		portfolio.checkLegal();

		Double[][] fileInfoArray = new Double[yearsInHistory][4];
		fileInfoArray = loadArray(fileName, fileInfoArray);

		portfolio.printInfo();
		System.out.printf("==> %.2f%% probability of success!%n",
				calculateSuccess(yearsInHistory, portfolio, fileInfoArray));
	}

	/**
	 * Prints the required arguments for the program to run successfully.
	 */
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

	/**
	 * Simulates retirement success based upon historical data.
	 * 
	 * @param yearsInHistory
	 *           Number of years of history to be used.
	 * @param portfolio
	 *           Object with data needed to simulate.
	 * @param fileInfoArray
	 *           Array that has all
	 * @return Percent chance of success.
	 * @throws IOException
	 */
	public static Double calculateSuccess(int yearsInHistory,
			Portfolio portfolio, Double[][] fileInfoArray) throws IOException
	{
		Double portfolioPercent;
		Double withdrawalAmount;
		int count = 0;
		int totalCount = 0;

		for (int startYear = 0; startYear <= yearsInHistory
				- portfolio.getRetirementLength(); startYear++)
		{
			// reset portfolio stats
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

			if (portfolioPercent > 0)
				count++;
			totalCount++;
		}
		return (100.0 * count / totalCount);
	}

	public static void parseArgs(String[] args, Portfolio portfolio)
	{
		if (args.length != 12)
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
				case 'f':
					portfolio.setMarketHistoryFileName(args[i + 1]);
					break;
				case 'b':
					portfolio.setBondPercent(Double.parseDouble(args[i + 1]));
					break;
				case 's':
					portfolio.setStockPercent(Double.parseDouble(args[i + 1]));
					break;
				case 'i':
					portfolio.setInflationLinkedPercent(
							Double.parseDouble(args[i + 1]));
					break;
				case 'w':
					portfolio.setInitialWithdrawalAmount(
							Double.parseDouble(args[i + 1]));
					break;
				case 'n':
					portfolio.setRetirementLength(Integer.parseInt(args[i + 1]));
					break;
				default:
					break;
				}
			}
		}
	}

	/**
	 * Adds data to array from file.
	 * 
	 * @param fileName
	 *           Name of file to read from.
	 * @param fileInfoArray
	 *           The array to load info into.
	 * @return Returns 2D array loaded with data.
	 * @throws FileNotFoundException
	 */
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

	/**
	 * counts number of lines in a file by checking for newline character.
	 * 
	 * @author martinus, a user on Stack Overflow
	 * @param filename
	 *           The name of the file to count lines.
	 * @return Returns number of lines in file.
	 * @throws IOException
	 */
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
