import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class ALUTest {
	ALU alu;
	
	@Before
	public void setUp() throws Exception {
		alu = new ALU();
	}

	
	
	@Test
	public void test1() {
		assertEquals(alu.integerRepresentation("-20", 12), "111111101100");
		
		assertEquals(alu.integerRepresentation("150", 12), "000010010110");
		
		
	}
	
	@Test
	public void test2() {
		assertEquals(alu.negation("00010010"), "11101101");
		assertEquals(alu.negation("110000010010"), "001111101101");
	}
	
	@Test
	public void test3() {
		assertEquals(alu.integerTrueValue("010000111"), "11101101");
		assertEquals(alu.negation("110000010010"), "001111101101");
	}
	@Test
	public void test() {
//		assertEquals(expected, actual);
		
		
		
		
	}

}
