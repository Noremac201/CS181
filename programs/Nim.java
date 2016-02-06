
/** Very simple program for the game Nim, where last person to choose a token
 *  loses. This program determines randomly who goes first, computer skill, and 
 *  number of tokens.
 *  @author Cameron Moberg
 *  @date Feb 1st, 2016
 */
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
      String winner = executeGame(computerMode, firstPlayer, numOfTokens,
            inputReader);

      if (winner.equals("human"))
         System.out.println("Human Wins!");
      else
         System.out.println("Computer Wins");
      
      inputReader.close();
   }

   /**
    * Executes the game and returns the winner.
    * 
    * @param computerMode
    *           Either string "simple" or "smart". Determines how computer plays
    *           the game.
    * @param firstPlayer
    *           A string "computer" or "human" determines whose move is first.
    * @param numOfTokens
    *           The integer number of tokens the game is going to be played
    *           with.
    * @param inputReader
    *           Where the human inputs their moves. In this case System.in
    * @return The winner in String based on who chose the last token. Either
    *         "human" or "computer".
    */
   public static String executeGame(String computerMode, String firstPlayer,
         int numOfTokens, Scanner inputReader)
   {
      System.out.printf("There are %d tokens left in the pile.\n", numOfTokens);
      if (firstPlayer.equals("human"))
      {
         numOfTokens = numOfTokens - humanMove(numOfTokens, inputReader);
         System.out.printf("There are %d tokens left in the pile.\n",
               numOfTokens);
      }

      while (numOfTokens > 0)
      {
         int tokensChosen;

         tokensChosen = numOfTokens;
         numOfTokens = numOfTokens - computerMove(computerMode, numOfTokens);
         tokensChosen -= numOfTokens;
         System.out.printf("Tokens chosen by the computer: %d%n", tokensChosen);
         System.out.printf("There are %d tokens left in the pile.%n",
                            numOfTokens);
         //if 0 is passed to human it returns human to winner, else it 
         //won't exit loop.
         if (numOfTokens == 0)
         {
            return "human";
         }
         numOfTokens = numOfTokens - humanMove(numOfTokens, inputReader);
         System.out.printf("There are %d tokens left in the pile.%n",
               numOfTokens);
      }
      return "computer";
   }

   /**
    * Randomly chooses a number of tokens based on a minimum and maximum wanted.
    * 
    * @param min
    *           The minimum number of tokens to choose from.
    * @param max
    *           The maximum number of tokens to choose from.
    * @return Returns the randomly generated number of tokens.
    */
   public static int setNumOfTokens(int min, int max)
   {
      // randomly sets number of tokens using Java's built in Random class.
      Random numberGenerator = new Random();
      return numberGenerator.nextInt((max - min) + 1) + min;
   }

   /**
    * Determines whether the computer is in smart or simple mode. Uses
    * Math.random() to determine the mode.
    * 
    * @return String of either "simple" or "smart".
    */
   public static String setComputerMode()
   {
      // Math.random() returns a double from 0 inclusive, to 1 exclusive.
      if (Math.random() < .5)
      {
         System.out.println("Computer is now in simple mode.");
         return "simple";
      } else
      {
         System.out.println("Computer is now in smart mode.");
         return "smart";
      }
   }

   /**
    * Determines who has the first move using Math.random().
    * 
    * @return String of "computer" or "human" depending on Math.random().
    */
   public static String setFirstPlayer()
   {
      if (Math.random() < .5)
      {
         System.out.println("Computer has first move");
         return "computer";
      } else
      {
         System.out.println("Human has first move.");
         return "human";
      }
   }

   /**
    * Calculates how many tokens the computer removes from the total.
    * 
    * @param computerMode
    *           The mode the computer is in, "simple" or "smart"
    * @param numOfTokens
    *           The current number of tokens in the pile.
    * @return The computers either calculated or random choice of how many
    *         tokens to pick.
    */
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
            // subtracts the next highest power of 2 by one from numOfTokens.
            // Found by using bits.
            return numOfTokens - (Integer.highestOneBit(numOfTokens) - 1);
         }
      } else
      {
         return movePicker.nextInt(numOfTokens / 2) + 1;
      }
   }

   /**
    * Prompts user for how many tokens based on numOfTokens.
    * 
    * @param numOfTokens
    *           The current number of tokens that are in the pile.
    * @param inputReader
    *           Where the program gets the user's move from.
    * @return Returns number of tokens to be removed from the pile.
    */
   public static int humanMove(int numOfTokens, Scanner inputReader)
   {
      // reset maxTokensTaken to 1, so integer division does not mess up
      // program when the tokens are < 3
      int maxTokensTaken = 1;

      if (numOfTokens <= 3)
         System.out.printf("Human, you must choose %d token: ", maxTokensTaken);
      else
      {
         maxTokensTaken = (numOfTokens / 2);
         System.out.printf("Human, choose between %d and %d: ", 1,
                            maxTokensTaken);
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
