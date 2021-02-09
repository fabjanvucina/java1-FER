package hr.fer.oprpp1.crypto;

/**
 * A utility class which contains methods for conversion between hexadecimal strings and byte arrays.
 * 
 * @author fabjanvucina
 */
public class Util {

	/**
	 * A method which converts a hexadecimal coded string into an array of bytes. One hexadecimal char has 4 bits.
	 * One byte consists of 8 bits. That means that the algorithm needs to consume two characters to generate one byte.
	 * @param hexString input string 
	 * @return array of bytes
	 */
	public static byte[] hexToByte(String hexString) {
		
		//empty string
		if(hexString.length() == 0) {
			return new byte[0];
		}
		
		//odd-sized string 
		if(hexString.length() % 2 != 0) {
			throw new IllegalArgumentException("Input has to be even-sized.");
		}
		
		//conversion
		byte[] bytes = new byte[hexString.length() / 2];
		
		for(int i = 0, j = 0, n = hexString.length(); i < n; i += 2) {
			//get characters from input string
			char moreSignificantChar = hexString.charAt(i);
			char lessSignificantChar = hexString.charAt(i + 1);
			
			//convert chars into their numeric value
			int moreSignificantDigit = toDigit(moreSignificantChar);
			int lessSignificantDigit = toDigit(lessSignificantChar);
			
			//convert digits into byte
			//we are shifiting the more significant digit into the more significant 4 bits of a byte
			bytes[j++] =  (byte) ((moreSignificantDigit << 4) + lessSignificantDigit);
			
		}
		
		return bytes;
		
	}
	
	/**
	 * A private helper method which returns a numeric value of a hex character.
	 * @param c the character
	 * @return numeric value of the passed character
	 * @throws IllegalArgumentException when character is not a hex symbol
	 */
	private static int toDigit(char c) {
		
		//convert character c into digit in specified radix(base)
		int digit = Character.digit(c, 16);
		
		//cannot convert
	    if(digit == -1) {
	        throw new IllegalArgumentException("Input has to consist of digits and letters");
	    }
	    
	    return digit;
	}

	/**
	 * A method which converts an array of bytes into a hexadecimal coded string. One byte consists of 8 bits.
	 * One hexadecimal character has 4 bits. That means that the algorithm needs to generate two characters for every byte.
	 * @param byteArray input array
	 * @return hexadecimal coded string
	 */
	public static String byteToHex(byte[] byteArray) {
		
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < byteArray.length; i++) {
			char[] hexDigits = new char[2];
			
			//Character.forDigit is a method which generates a char representation for a specific digit in the specified base.
		    hexDigits[0] = Character.forDigit((byteArray[i] >> 4) & 0xF, 16);
		    hexDigits[1] = Character.forDigit((byteArray[i] & 0xF), 16);
		    
		    //append the string representation of the input byte(constructor accepts a char array)
		    sb.append(new String(hexDigits));
		}
		
		return sb.toString();
	}
}
