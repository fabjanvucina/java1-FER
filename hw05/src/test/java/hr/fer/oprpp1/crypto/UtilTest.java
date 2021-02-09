package hr.fer.oprpp1.crypto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UtilTest {

	@Test
	public void testHexToByte(){
		String hexString = "01aE22";
		byte[] expectedArray = new byte[] {1, -82, 34};
		
		assertEquals(expectedArray[0], Util.hexToByte(hexString)[0]);
		assertEquals(expectedArray[1], Util.hexToByte(hexString)[1]);
		assertEquals(expectedArray[2], Util.hexToByte(hexString)[2]);
		assertEquals(3, Util.hexToByte(hexString).length);
	}
	
	@Test
	public void testByteToHex(){
		byte[] byteArray = new byte[] {1, -82, 34};
		String expectedString = "01ae22";
		
		assertEquals(expectedString, Util.byteToHex(byteArray));
	}
}
