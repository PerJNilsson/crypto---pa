import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.nio.charset.StandardCharsets;
import javax.xml.bind.DatatypeConverter;

public class CBCXor {

	public static void main(String[] args) {
                int block_counter = 0;
                int total_blocks;
                int length_block = 12;
                String filename = "input.txt";
		byte[] first_block = null;
		byte[] encrypted = null;
		byte[] tmp_block = null;
		byte[] c0 = new byte[length_block];
                byte[] IV = new byte[length_block];
                byte[] key = null;
               
                
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			first_block = br.readLine().getBytes(); // My personal number
                        encrypted = DatatypeConverter.parseHexBinary(br.readLine());
                        br.close();
		} catch (Exception err) {
			System.err.println("Error handling file. WHYYYYYYYYY");
			err.printStackTrace();
			System.exit(1);
		}

                IV = getBlocks(encrypted, block_counter);
                block_counter++;
                //System.out.println(IV);
                c0 = getBlocks(encrypted, block_counter);
                block_counter++;
                key = getKey(IV, c0, first_block);
                //System.out.println(key);
                total_blocks = encrypted.length / length_block; // Says we have a total of 9 blocks.
                byte[] all_message = new byte[total_blocks*length_block];
                //System.out.println(total_blocks);
                // dectrypt whole input - c0 == message
                for (int i=0; i<total_blocks-2; i++){
                        tmp_block = decyption_scheme(encrypted, block_counter, key);
                        block_counter++;
                        for (int j=0; j<length_block; j++){
                                all_message[i*length_block+j] = tmp_block[j];
                        }
                }
                
		String m = recoverMessage(all_message);
		System.out.println("Recovered message: " + m);
	}

	/**
	 * Recover the encrypted message (CBC encrypted with XOR, block size = 12).
	 * 
	 * @param first_block
	 *            We know that this is the value of the first block of plain
	 *            text.
	 * @param encrypted
	 *            The encrypted text, of the form IV | C0 | C1 | ... where each
	 *            block is 12 bytes long.
	 */
	private static String recoverMessage(byte[] message) {
                int length_block = 12;
    
		return new String(message);
	}

        public static byte[] getBlocks(byte [] encrypted, int block_counter) {
                int length_block = 12;
                byte[] block = new byte[length_block];
                for (int i = 0; i<length_block; i++) {
                        block[i] = encrypted[i+block_counter*length_block];
                }
                return block;
        }

        public static byte[] getKey(byte[] IV, byte[] c0, byte[] first_block){
                int length_block = 12;
                byte[] key = new byte[length_block];
                byte[] tmp_byte = new byte[length_block];
                /* We know m0 creates c0. We know IV and that c0 = key ^ (m0 ^ IV)
                 * By xor:ing each side with (m0^IV) we can get the key:
                 * c0 ^ (m0^IV) = key ^ (m0 ^ IV) ^ (m0 ^ IV) ----->
                 * c0 ^ (m0^IV) = key
                 */
                tmp_byte = XOR(first_block, IV);
                key = XOR(tmp_byte, c0);
                return key;
                }

        public static byte[] XOR(byte[] block_i, byte[] block_j) {
                int length_block = 12;
                byte tmp_bit;
                byte[] xor_result = new byte[length_block];

                for (int i=0; i<length_block; i++) {
                        tmp_bit = (byte)(block_i[i] ^ block_j[i]);
                        xor_result[i] = (byte) (0xff & tmp_bit); // Removes the first 24 bits, and saved the 8 LSB.
                }
                
                return xor_result;
        }
        public static byte[] decyption_scheme(byte[] encryption, int block_counter, byte[] key){
                // Decryption scheme: m_i = (K ^c_i) ^c_(i-1)
                int length_block = 12;
                byte[] tmp_byte1 = new byte[length_block];
                byte[] tmp_byte2 = new byte[length_block];
                byte[] message = new byte[length_block];

                tmp_byte1 = XOR(key, getBlocks(encryption, block_counter-1));
                tmp_byte2 = XOR(tmp_byte1, getBlocks(encryption, block_counter));
                message = tmp_byte2;
                return message;
        }
}
