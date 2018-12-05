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
    // EA
    int a_ea, b_ea;
    if (a > b){
        a_ea = a;
        b_ea = b;
    }
    else {
        a_ea = b;
        b_ea = a;
    }
    int r1; int r2; int q0; int r0; int p_r = -1;;
    r1 = a_ea; r2 = b_ea; q0=0; r0=1;
    // EA
    while (r0 > 0) {
        p_r = r2;
        q0 = r1/r2;
        r0 = r1-q0*r2;

        r1 = r2;
        r2 = r0;
    }
    gcd = p_r;

    // EEA
    int[] m = new int[3];
    m[0] = 1; m[1] = 0; m[2] = 0;
    int[] n = new int[3];
    n[0] = 0; n[1] = 1; n[2] = 0;
    int q = gcd +100;
    int r;
    int largest;
    int lowest;
    if (a >= b){
        largest = a;
        lowest = b;
    }
    else {
        largest = b;
        lowest = a;
    }
    if (lowest != 0){ q = largest / lowest; }
    r = largest - lowest*q;
    if ( r == 0){
        if (a > b){
            s = 0;
            t = 1;
        }
        else {
            s = 1*q;
            t = 0;
        }
    }
    if ( q == 1 || q == largest){
        if (a > b){
            s = 0;
            t = 1;
        }
        else {
            s = 1;
            t = 0;
        }
	
    }
    while (r>gcd){
        q = largest / lowest;
        m[2] = m[0] - q*m[1];
        m[0]=m[1];
        m[1]=m[2];

        n[2] = n[0] - q*n[1];
        n[0]=n[1];
        n[1]=n[2];
        System.out.printf("%d %d %d%n",q, r,gcd);
        r = largest - lowest*q;
        largest = lowest;
        lowest = r;

        if (a > b){
            s = m[2];
            t = n[2];
        }
        else {
            s = n[2];
            t = m[2];
        }
	
    }
    int[] result = new int[3];
    // s = n[2]; t = m[2];
   	result[0] = gcd;
		result[1] = s;
		result[2] = t;
		return result;
	}

	/**
	 * Returns Euler's Totient for value "n".
	 **/
	public static int EulerPhi(int n) {
		return -1;
	}

	/**
	 * Returns the value "v" such that "n*v = 1 (mod m)". Returns 0 if the
	 * modular inverse does not exist.
	 **/
	public static int ModInv(int n, int m) {
		return -1;
	}

	/**
	 * Returns 0 if "n" is a Fermat Prime, otherwise it returns the lowest
	 * Fermat Witness. Tests values from 2 (inclusive) to "n/3" (exclusive).
	 **/
	public static int FermatPT(int n) {
		return -1;
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
