package hr.fer.oprpp1.hw01;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


/**
 * A class created with the purpose of performing junit tests on the class <code>ComplexNumber</code>
 * 
 * @author fabjanvucina
 *
 */
public class ComplexNumberTest {
	
	@Test
	public void testfromReal(){
		assertEquals(new ComplexNumber(3, 0), ComplexNumber.fromReal(3));
	}
	
	@Test
	public void testfromImaginary(){
		assertEquals(new ComplexNumber(0, 3), ComplexNumber.fromImaginary(3));
	}

	@Test
	public void testfromMagnitudeAndAngle(){		
		assertEquals(new ComplexNumber(0.0015926534214665267, 1.9999993658636692), ComplexNumber.fromMagnitudeAndAngle(2, 1.57));
	}
	
	@Test
	public void testParseSpecialCase1(){		
		assertEquals(new ComplexNumber(0, 1), ComplexNumber.parse("i"));
	}
	
	@Test
	public void testParseSpecialCase2(){		
		assertEquals(new ComplexNumber(0, -1), ComplexNumber.parse("-i"));
	}
	
	@Test
	public void testParseRealNumber1(){		
		assertEquals(new ComplexNumber(3, 0), ComplexNumber.parse("3"));
	}
	
	@Test
	public void testParseRealNumber2(){		
		assertEquals(new ComplexNumber(-3.6, 0), ComplexNumber.parse("-3.6"));
	}
	
	@Test
	public void testParseImaginaryNumber1(){		
		assertEquals(new ComplexNumber(0, 3), ComplexNumber.parse("3i"));
	}
	
	@Test
	public void testParseImaginaryNumber2(){		
		assertEquals(new ComplexNumber(0, -3.6), ComplexNumber.parse("-3.6i"));
	}
	
	@Test
	public void testParseComplexNumber(){		
		assertEquals(new ComplexNumber(3.5, -4.76), ComplexNumber.parse("3.5-4.76i"));
	}
	
	@Test
	public void testParseInvalidFormatShouldThrow1(){		
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("x"));
	}
	
	@Test
	public void testParseInvalidFormatShouldThrow2(){		
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("ii"));
	}
	
	@Test
	public void testParseInvalidFormatShouldThrow3(){		
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("i+4"));
	}
	
	@Test
	public void testParseInvalidFormatShouldThrow4(){		
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("4+3"));
	}
	
	@Test
	public void testGetReal(){
		assertEquals(3.0, (new ComplexNumber(3, 0)).getReal());
	}
	
	@Test
	public void testGetImaginary(){
		assertEquals(3.0, (new ComplexNumber(0, 3)).getImaginary());
	}
	
	@Test
	public void testGetMagnitude(){
		assertEquals(2, (new ComplexNumber(0.0015926534214665267, 1.9999993658636692)).getMagnitude());
	}
	
	@Test
	public void testGetAngle(){
		assertEquals(1.57, (new ComplexNumber(0.0015926534214665267, 1.9999993658636692)).getAngle());
	}
	
	@Test
	public void testAddMethodNullArgumentShouldThrow(){
		assertThrows(NullPointerException.class, () -> (new ComplexNumber(1, 1)).add(null));
	}
	
	@Test
	public void testAddMethodShouldAddElement(){
		var cn1 = new ComplexNumber(1, 0);
		var cn2 = new ComplexNumber(0, 1);
		var res = new ComplexNumber(1, 1);
		
		assertEquals(res, cn1.add(cn2));
	}
	
	@Test
	public void testSubMethodNullArgumentShouldThrow(){
		assertThrows(NullPointerException.class, () -> (new ComplexNumber(1, 1)).sub(null));
	}
	
	@Test
	public void testSubMethodShouldSubtractElement(){
		var cn1 = new ComplexNumber(1, 1);
		var cn2 = new ComplexNumber(0, 1);
		var res = new ComplexNumber(1, 0);
		
		assertEquals(res, cn1.sub(cn2));
	}
	
	@Test
	public void testMulMethodNullArgumentShouldThrow(){
		assertThrows(NullPointerException.class, () -> (new ComplexNumber(1, 1)).mul(null));
	}
	
	@Test
	public void testMulMethodShouldMultiplyWithElement(){
		var cn = new ComplexNumber(1, 1);
		var res = new ComplexNumber(0, 2);
		
		assertEquals(res, cn.mul(cn));
	}
	
	@Test
	public void testDivMethodNullArgumentShouldThrow(){
		assertThrows(NullPointerException.class, () -> (new ComplexNumber(1, 1)).div(null));
	}
	
	@Test
	public void testDivMethodShouldDivideWithElement(){
		var cn1 = new ComplexNumber(1, 1);
		var cn2 = new ComplexNumber(1, -1);
		var res = new ComplexNumber(0, 1);
		
		assertEquals(res, cn1.div(cn2));
	}
	
	@Test
	public void testPowerMethodInvalidArgumentShouldThrow(){
		assertThrows(IllegalArgumentException.class, () -> (new ComplexNumber(1, 1)).power(-1));
	}
	
	@Test
	public void testPowerMethodShouldRaiseElementToPower(){
		var cn = new ComplexNumber(1, 1);
		var res = new ComplexNumber(-2, 2);
		
		assertEquals(res, cn.power(3));
	}
	
	@Test
	public void testRootMethodInvalidArgumentShouldThrow(){
		assertThrows(IllegalArgumentException.class, () -> (new ComplexNumber(1, 1)).root(-1));
	}
	
	@Test
	public void testRootMethodShouldCalculateRoots(){
		var cn = new ComplexNumber(32, 0);
		var res = new ComplexNumber[] {new ComplexNumber(2, 0), new ComplexNumber(0.6180339887, 1.902113033),
									   new ComplexNumber(-1.618033989, 1.175570505), new ComplexNumber(-1.618033989, -1.175570505),
									   new ComplexNumber(0.6180339887, -1.902113033)};
		
		assertArrayEquals(res, cn.root(5));
	}
	
	@Test
	public void testToString() {
		assertEquals("-2.5-4.0i", (new ComplexNumber(-2.5, -4)).toString());
	}
}
