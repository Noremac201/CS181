
/** Check if the first string is a compliment of the second string. DNA-wise.
 * @Author Cameron Moberg
 * @Date January 22nd, 2016
 **/

import java.util.Scanner;

public class ReverseComplement
{

   public static void main(String[] args)
   {
      Scanner inputReader = new Scanner(System.in);
      System.out.print("Enter the first sequence: ");
      String sequenceOne = inputReader.nextLine().toUpperCase();
 
      //reverses to easily compare to seqTwo later.
      String sequenceOneReversed = new StringBuilder(sequenceOne).reverse().toString();
      
      System.out.print("Enter the second sequence: ");
      String sequenceTwo = inputReader.nextLine().toUpperCase();
      
      inputReader.close();

      if (compareSequence(findComplement(sequenceOneReversed), sequenceTwo))
         System.out.println("These are complements.");
      else
         System.out.println("These are not complements");
   }

   public static String findComplement(String seqOne)
   {
      String sequenceOneComplement = "";
      for (int i = 0; i < seqOne.length(); i++)
      {
         switch (seqOne.charAt(i))
         {
         //adds to new string based on complement char.
         case 'A':
            sequenceOneComplement += 'T';
            break;
         case 'C':
            sequenceOneComplement += 'G';
            break;
         case 'G':
            sequenceOneComplement += 'C';
            break;
         case 'T':
            sequenceOneComplement += 'A';
            break;
         }
      }
      //returns the complement of seqOne.
      return sequenceOneComplement;
   }

   public static boolean compareSequence(String seqOne, String seqTwo)
   {
      if (seqOne.equals(seqTwo))
         return true;

      return false;
   }
}
