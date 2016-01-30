import java.util.Random;
import java.util.Scanner;

public class Nim
{
   public static void main(String[] args)
   {
      Scanner inputReader = new Scanner(System.in);

      int numOfTokens = setNumOfTokens(10, 100);
      String computerMode = setComputerMode();
      String firstPlayer = setFirstPlayer();
      String winner = executeGame(computerMode, firstPlayer, numOfTokens, inputReader);

      if (winner.equals("human"))
         System.out.println("Human Wins!");
      else
         System.out.println("Computer Wins");

      inputReader.close();
   }

   public static String executeGame(String computerMode, String firstPlayer, int numOfTokens, Scanner inputReader)
   {
      System.out.printf("There are %d tokens left in the pile.\n", numOfTokens);
      if (firstPlayer.equals("human"))
      {
         numOfTokens = numOfTokens - humanMove(numOfTokens, inputReader);
         System.out.printf("There are %d tokens left in the pile\n", numOfTokens);
      }

      while (numOfTokens > 0)
      {
         if (numOfTokens == 1)
         {
            return "human";
         }
         numOfTokens = numOfTokens - computerMove(computerMode, numOfTokens);
         System.out.printf("There are %d tokens left in the pile\n", numOfTokens);

         numOfTokens = numOfTokens - humanMove(numOfTokens, inputReader);
         System.out.printf("There are %d tokens left in the pile\n", numOfTokens);
      }

      return "computer";

   }

   public static int setNumOfTokens(int min, int max)
   {
      Random numberGenerator = new Random();
      int Tokens = numberGenerator.nextInt((max - min) + 1) + min;
      return Tokens;
   }

   public static String setComputerMode()
   {
      double decider = Math.random();
      if (decider < .5)
      {
         System.out.println("Computer is now in simple mode.");
         return "simple";
      } else
      {
         System.out.println("Computer is now in smart mode.");
         return "smart";
      }
   }

   public static String setFirstPlayer()
   {
      double decider = Math.random();
      if (decider < .5)
      {
         System.out.println("Computer has first move");
         return "computer";
      } else
      {
         System.out.println("Human has first move.");
         return "human";
      }
   }

   public static int computerMove(String computerMode, int numOfTokens)
   {
      Random movePicker = new Random();
      if (numOfTokens <= 3)
         return 1;

      if (computerMode.equals("smart"))
      {
         // checks if power of 2 - 1, using bitwise comparison.
         if (((numOfTokens + 1) & (-numOfTokens - 1)) == (numOfTokens + 1))
         {
            return movePicker.nextInt(numOfTokens / 2) + 1;
         } else
         {
            return numOfTokens - (Integer.highestOneBit(numOfTokens - 1) - 1);
         }
      } else
      {
         return movePicker.nextInt(numOfTokens / 2) + 1;
      }
   }

   public static int humanMove(int numOfTokens, Scanner inputReader)
   {
      int maxTokensTaken = 1;
      if (numOfTokens <= 3)
         System.out.print("Human, you must choose 1 token: ");
      else
      {
         maxTokensTaken = (numOfTokens / 2);
         System.out.printf("Human, choose between %d and %d: ", 1, maxTokensTaken);
      }

      int humanAnswer = inputReader.nextInt();
      while (humanAnswer < 1 || humanAnswer > maxTokensTaken)
      {
         System.out.print("Illegal move, try again: ");
         humanAnswer = inputReader.nextInt();
      }
      return humanAnswer;
   }
}
