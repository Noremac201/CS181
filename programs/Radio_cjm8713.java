import java.util.Scanner;

public class Radio_cjm8713
{
   public static Double calculateFrequency(Double inductance, Double cValue) {
     /*Using variableCapacitor and given f,
      *  solve for Inductance using the given formula / Rearranged for L is       
      * L = 4pi^2 / f^2*C                                                                      
      */
     return 1/(2*Math.PI*Math.sqrt(inductance*cValue));
   }
   
   public static Double calculateInductance(Double variableCapacitor, Double frequency) {
     /*Finding range of frequencies
      * using calculated L and cMin and cMax
      * fMin =  1/2*pi*sqrt(L*cMax)
     */
     return (4*Math.pow(Math.PI, 2)) / (variableCapacitor * Math.pow(Math.PI*frequency, 2));
   }
   
   public static void main(String[] args)
   {
      Scanner inputReader = new Scanner(System.in);
      double cMin;
      double cMax;
      double frequency;
      double variableCapacitor;
      double inductance = 0; //Initialize
      
      //Converts to pF, pF, and mHz, respectively
      System.out.print("Please enter C min (in F): ");
      cMin = inputReader.nextDouble();
      System.out.print("Please enter C max (in F): ");
      cMax = inputReader.nextDouble();
      System.out.print("Please enter the Frequency in (Hz): ");
      frequency = inputReader.nextDouble();
      inputReader.close();

      //To find C take sqrt(cMin * cMax)
      variableCapacitor = Math.sqrt(cMin*cMax);
      System.out.println("The Required inductance is:" + calculateInductance(variableCapacitor, frequency));
      System.out.printf("The Minimum Frequency is: %.2f Hz\n", calculateFrequency(inductance,cMax));
      System.out.printf("The Minimum Frequency is: %.2f Hz\n", calculateFrequency(inductance,cMax));
   }
}
