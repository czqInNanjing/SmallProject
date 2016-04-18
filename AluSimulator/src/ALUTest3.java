import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class ALUTest3 {
	String s;
	ALU alu;
	//
	@Before
	public void setUp() throws Exception {
		alu = new ALU();
	}
	

	@Test
	public void test0() {
		assertEquals("00000000000000000000000000000000", 
				alu.floatRepresentation("0.0", 23, 8));
	}
	@Test
	public void test1(){
		assertEquals("10000000000000000000000000000000", 
				alu.floatRepresentation("-0.0", 23, 8));
	}
	@Test
	public void test2(){
		assertEquals("00001011000110111100010011010111", 
				alu.floatRepresentation("0.00000000000000000000000000000003", 23, 8));
	}
	@Test
	public void test3(){
		assertEquals("00000000000000000000000000000000", 
				alu.floatRepresentation("0.00000000000000000000000000000000000000000000000003", 23, 8));
	}
	@Test
	public void test4(){
		assertEquals("00000000000000000000000000000010", 
				alu.floatRepresentation("0.000000000000000000000000000000000000000000003", 23, 8));
	}

	@Test
	public void test6(){
		assertEquals("11000110000111000100000111010101", 
				alu.floatRepresentation("-10000.458", 23, 8));
	}
	@Test
	public void test7(){
		assertEquals("0000000000000000000000000000000000000000000000000000000000000000",
			alu.floatRepresentation("0.0", 52,11)	);
	}
	@Test
	public void test8(){
		assertEquals("0011111111101000010011001000100011001111000101011000001110100111", 
			alu.floatRepresentation("0.75934257932759837523", 52, 11)	);
	}
	@Test
	public void test9(){
		assertEquals("1100101111011011", alu.floatRepresentation("-3.482", 12, 3));
	}
}
