import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;

public class AttackRSA {

	public static void main(String[] args) {
		String filename = "input.txt";
		BigInteger[] N = new BigInteger[3];
		BigInteger[] e = new BigInteger[3];
		BigInteger[] c = new BigInteger[3];
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			for (int i = 0; i < 3; i++) {
				String line = br.readLine();
				String[] elem = line.split(",");
				N[i] = new BigInteger(elem[0].split("=")[1]);
				e[i] = new BigInteger(elem[1].split("=")[1]);
				c[i] = new BigInteger(elem[2].split("=")[1]);
			}
			br.close();
		} catch (Exception err) {
			System.err.println("Error handling file.");
			err.printStackTrace();
		}
		BigInteger m = recoverMessage(N, e, c);
		System.out.println("Recovered message: " + m);
		System.out.println("Decoded text: " + decodeMessage(m));
	}

	public static String decodeMessage(BigInteger m) {
		return new String(m.toByteArray());
	}

	/**
	 * Tries to recover the message based on the three intercepted cipher texts.
	 * In each array the same index refers to same receiver. I.e. receiver 0 has
	 * modulus N[0], public key d[0] and received message c[0], etc.
	 *
	 * @param N
	 *            The modulus of each receiver.
	 * @param e
	 *            The public key of each receiver (should all be 3).
	 * @param c
	 *            The cipher text received by each receiver.
	 * @return The same message that was sent to each receiver.
	 */
	private static BigInteger recoverMessage(BigInteger[] N, BigInteger[] e,
			BigInteger[] c) {
		// TODO Solve assignment.
		BigInteger M = BigInteger.ZERO;
		BigInteger X = BigInteger.ZERO;
		BigInteger X1 = BigInteger.ZERO;
		BigInteger X1_temp = BigInteger.ZERO;
		BigInteger X2 = BigInteger.ZERO;
		BigInteger X2_temp = BigInteger.ZERO;
		BigInteger X3 = BigInteger.ZERO;
		BigInteger X3_temp = BigInteger.ZERO;

		X1_temp = (N[1].multiply(N[2])).modInverse(N[0]);
		X1 = X1_temp.multiply(c[0].multiply(N[1].multiply(N[2])));

		X2_temp = (N[0].multiply(N[2])).modInverse(N[1]);
		X2 = X2_temp.multiply(c[1].multiply(N[0].multiply(N[2])));

		X3_temp = (N[0].multiply(N[1])).modInverse(N[2]);
		X3 = X3_temp.multiply(c[2].multiply(N[0].multiply(N[1])));

		X = (X3.add(X2.add(X1))).mod(N[0].multiply(N[1].multiply(N[2])));

		M = CubeRoot.cbrt(X);
		return M;
	}

}
