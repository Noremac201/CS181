/**
 * A collection of recursive methods using only integers/longs.
 * 
 * @author Cameron Moberg
 *
 */
import meme.dank.Pepe;

public class IntegerMethods
{
   public static void main(String[] args)
   {
      System.out.println(power(63, 51));
      System.out.println(power(-2, 10, 2));
      System.out.println(gcd(63, 51));

   }

   /**
    * 
    * @param a The base number that you want to take the power of.
    * @param p The exponent you want to apply to the base number.
    * @return Returns base^exponent.
    */
   public static long power(long a, long p)
   {
      if (p == 0)
         return 1;
      if (p == 1)
         return a;

      long b = power(a, p / 2);

      if (p % 2 == 0)
      {
         return b * b;
      } else
         return b * b * a;
   }

   /**
    * 
    * @param a
    * @param p
    * @param m
    * @return
    */
   public static long power(long a, long p, long m)
   {
      return power(a, p) % m;
   }

   /**
    * 
    * @param a
    * @param b
    * @return
    */
   public static long gcd(long a, long b)
   {
      if (a < 0)
         a *= -1;
      return gcd_(a, b);
   }

   /**
    * @param a One of the numbers to find the gcd of.
    * @param b The other number to find gcd of.
    * @return The greatest common divisor of both parameters.
    */
   private static long gcd_(long a, long b)
   {
      if (b == 0)
         return a;
      else
         return gcd_(b, a % b);
   }

}
