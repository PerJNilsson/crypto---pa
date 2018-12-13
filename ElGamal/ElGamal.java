import java.io.BufferedReader;
import java.io.FileReader;
//import java.math.BigInteger;
import java.math.*;


public class ElGamal {

  public static String decodeMessage(BigInteger m) {
    return new String(m.toByteArray());
  }  

  public static void main(String[] arg) {
    String filename = "input.txt";
    try {
      BufferedReader br = new BufferedReader(new FileReader(filename));
      BigInteger p = new BigInteger(br.readLine().split("=")[1]);
      BigInteger g = new BigInteger(br.readLine().split("=")[1]);
      BigInteger y = new BigInteger(br.readLine().split("=")[1]);
      String line = br.readLine().split("=")[1];
      String date = line.split(" ")[0];
      String time = line.split(" ")[1];
      int year  = Integer.parseInt(date.split("-")[0]);
      int month = Integer.parseInt(date.split("-")[1]);
      int day   = Integer.parseInt(date.split("-")[2]);
      int hour   = Integer.parseInt(time.split(":")[0]);
      int minute = Integer.parseInt(time.split(":")[1]);
      int second = Integer.parseInt(time.split(":")[2]);
      BigInteger c1 = new BigInteger(br.readLine().split("=")[1]);
      BigInteger c2 = new BigInteger(br.readLine().split("=")[1]);
      br.close();


      /* We doesn't know the last factor millisecond. So we create a loop where we test all the possibilites.
       * This can be checked since we know c1 = g ^r, where c1 and g is known. 
       */
      int millisecond = 0;
      BigInteger rn;
      BigInteger c1_test = BigInteger.valueOf(0);
      for(int i=0; i<1000; i++){ 
              rn = getRandomNumber(year, month, day, hour, minute, second, i);
              c1_test = g.modPow(rn, p); // Takes g to the power of rn with modulus p
              if ( c1_test.equals(c1)) {
                     millisecond  = i;
              }
      }
      System.out.println("The correct millisecond is " + millisecond);

      rn = getRandomNumber(year, month, day, hour, minute, second, millisecond);

      BigInteger yr_inverse = BigInteger.valueOf(0);
      BigInteger yr = BigInteger.valueOf(0);

      /* We know the r, so should be able to compute the message. Since y, r, c2, p is given, and only m is unkown.
       * We calculate yr_invers = 1 / yr (mod p).
       * Then the message can be calculated with simple multiplication  m = c2 * yr_invers (mod p)
       */
      
      BigInteger one = BigInteger.valueOf(-1);
      yr = y.modPow(rn, p);
      yr_inverse = yr.modPow(one, p); // Calculating 1/ hr (mod p)

      BigInteger m = BigInteger.valueOf(0);
      m = c2.multiply(yr_inverse);
      m = m.mod(p);

      System.out.println("Recovered message: " + m);
      System.out.println("Decoded text: " + decodeMessage(m));


    } catch (Exception err) {
      System.err.println("Error handling file.");
      err.printStackTrace();
      System.exit(1);
    }
               
  }
  
        public static BigInteger recoverSecret(BigInteger p, BigInteger g,
                                               BigInteger y, int year, int month, int day, int hour, int minute,
                                               int second, BigInteger c1, BigInteger c2) {
                return c1;
  }


        public static BigInteger getRandomNumber(int year, int month, int day, int hour, int minute, int second, int millisecond){
                BigInteger randomNumber = BigInteger.valueOf(0);
                
                BigInteger BigYear = BigInteger.valueOf(year);
                //month*(108) + days*(106) + hours*(104) + minute*(102) + sec + millisecs
                randomNumber = randomNumber.add(BigDecimal.valueOf(year*Math.pow(10,10)).toBigInteger());
                randomNumber = randomNumber.add(BigDecimal.valueOf(month*Math.pow(10,8)).toBigInteger());
                randomNumber = randomNumber.add(BigDecimal.valueOf(day*Math.pow(10,6)).toBigInteger());
                randomNumber = randomNumber.add(BigDecimal.valueOf(hour*Math.pow(10,4)).toBigInteger());
                randomNumber = randomNumber.add(BigDecimal.valueOf(minute*Math.pow(10,2)).toBigInteger());
                randomNumber = randomNumber.add(BigDecimal.valueOf(second+millisecond).toBigInteger());
                return randomNumber;
        }
  
}
