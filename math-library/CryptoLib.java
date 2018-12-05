// Compilation (CryptoLibTest contains the main-method):
//   javac CryptoLibTest.java
// Running:
//   java CryptoLibTest

public class CryptoLib {

	/**
	 * Returns an array "result" with the values "result[0] = gcd",
	 * "result[1] = s" and "result[2] = t" such that "gcd" is the greatest
	 * common divisor of "a" and "b", and "gcd = a * s + b * t".
	 **/
	public static int[] EEA(int a, int b) {
		// Note: as you can see in the test suite,
		// your function should work for any (positive) value of a and b.
		int gcd = -1;
		int s = -1;
		int t = -1;

    int r1;
    int r2;
    int q0;
    int r0=1;
    int p_r = -1;

    if (a > b){
        r1 = a;
        r2 = b;
    }
    else {
        r2 = b;
        r1 = a;
    }

    // Euclidian Algorithm
    while (r0 > 0) {
        p_r = r2;
        q0 = r1/r2;
        r0 = r1-q0*r2;
        r1 = r2;
        r2 = r0;
    }
    gcd = p_r;

    // Extended Euclidean Algorithm
    int[] m = new int[3];
    int[] n = new int[3];
    int quotient;
    int remainder;
    int largest;
    int lowest;
    int check =0;
    int[] result = new int[3];
    int [][] listm = new int[2][4];

    m[0] = 1;
    m[1] = 0;
    m[2] = 0;

    n[0] = 0;
    n[1] = 1;
    n[2] = 0;


    if ( a>=b){
        largest=a;
        lowest=b;
    }

    else {
        largest=b;
        lowest=a;
    }

    listm[0][0] = largest; listm[0][1] = 1; listm[0][2] = 0; listm[0][3] = Integer.MAX_VALUE;
    listm[1][0] = lowest; listm[1][1] = 0; listm[1][2] = 1; listm[1][3] = largest / lowest;

    if (listm[0][0] == gcd){m[2]=listm[0][1]; n[2]=listm[0][2]; check =1;}

    while(check == 0){
        if (listm[1][0] == gcd){m[2]=listm[1][1]; n[2]=listm[1][2]; break;}
        quotient = listm[0][0] / listm[1][0];
        remainder = listm[0][0] - listm[1][0]*quotient;

        int tmpx = listm[0][1];
        int tmpy = listm[0][2];

        listm[0][0] = listm[1][0];
        listm[0][1] = listm[1][1];
        listm[0][2] = listm[1][2];
        listm[0][3] = listm[1][3];

        listm[1][0] = remainder;
        listm[1][1] = tmpx - quotient*listm[1][1];
        listm[1][2] = tmpy - quotient*listm[1][2];
        listm[1][3] = quotient;
    }

    if (a>=b){
        s=m[2];
        t=n[2];
    }

    else{
        s = n[2];
        t=m[2];
    }

    result[0] = gcd;
		result[1] = s;
		result[2] = t;
		return result;
	}

	/**
	 * Returns Euler's Totient for value "n".
	 **/
	public static int EulerPhi(int n) {
      int phi_n = 1;
      int gcd;
      for (int i=2; i<n; i++){
          int r1; int r2; int q0; int r0; int p_r = -1;;
          r1 = n; r2 = i; q0=0; r0=1;
          // EA
          while (r0 > 0) {
              p_r = r2;
              q0 = r1/r2;
              r0 = r1-q0*r2;
              r1 = r2;
              r2 = r0;
          }
          gcd = p_r;
          if (gcd == 1){
              phi_n++;
          }
      }
      if( n < 0){
          phi_n = 0;
      }
		return phi_n;
	}

	/**
	 * Returns the value "v" such that "n*v = 1 (mod m)". Returns 0 if the
	 * modular inverse does not exist.
	 **/
	public static int ModInv(int n, int m) {

      while(n < 0){
          n = n+m;
      }
      int v = -1;
      int smt;
      int gcd;
      int a_ea; int b_ea;

      // m is always larger than n;
      a_ea = m;
      b_ea = n;
      int r1; int r2; int q0; int r0; int p_r = -1;
      r1 = a_ea; r2 = b_ea; q0=0; r0=1;

      // EA to check if modular inverse exists

      while (r0 > 0) {
          p_r = r2;
          q0 = r1/r2;
          r0 = r1-q0*r2;

          r1 = r2;
          r2 = r0;
      }
      gcd = p_r;
      if (gcd < 1){
          v = 0; // if not exists
      }
      else {
          int three[] = new int[3];
          three = EEA(n, m); // solve the equation n*s + m*t = gcd
          v = three[1];
          if (v < 0){
              v = v + m; // sets v to a positive interger
          }
      }

      return v;
	}

	/**
	 * Returns 0 if "n" is a Fermat Prime, otherwise it returns the lowest
	 * Fermat Witness. Tests values from 2 (inclusive) to "n/3" (exclusive).
	 **/
	public static int FermatPT(int n) {

		int lowestFermatWitness = 0;
		int loopMax = (int) Math.floor(n/3);

		for(int i = 2; i < loopMax; i++){
			if((i^(n-1) % n) =! 1){
				lowestFermatWitness = i;
				break;
			}
		}
		return lowestFermatWitness;
	}

	/**
	 * Returns the probability that calling a perfect hash function with
	 * "n_samples" (uniformly distributed) will give one collision (i.e. that
	 * two samples result in the same hash) -- where "size" is the number of
	 * different output values the hash function can produce.
	 **/
	public static double HashCP(double n_samples, double size) {
		return -1;
	}

}
