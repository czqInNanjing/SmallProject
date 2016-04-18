import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class ALUTest13 {
	ALU alu;
	@Before
	public void setUp() throws Exception {
		alu=new ALU();
	}

	@Test
	public void test0() {
		assertEquals("00", alu.integerAddition("0", "0", '0', 1));
	}
	@Test
	public void test1(){
		assertEquals("01", alu.integerAddition("1", "1", '0', 1));
	}
	@Test
	public void test2(){
		//TODO 如果严格按照pdf实现，这是一个及其困难的bug
		assertEquals("11", alu.integerAddition("1", "1", '1', 1));
	}
	@Test
	public void test3(){
		assertEquals("10010", alu.integerAddition("1", "1010", '0', 4));
	}
	@Test
	public void test4(){
		assertEquals("10000", alu.integerAddition("1", "1001", '0', 4));
	}
	@Test
	public void test5(){
		assertEquals("000000", alu.integerAddition("110", "00010", '0', 5));
	}
	@Test
	public void test6(){
		assertEquals("1000001", alu.integerAddition("011111", "00", '1', 6));
	}
	@Test
	public void test7(){
		assertEquals("100000000000", alu.integerAddition("10000000001", "1", '0', 11));
	}
}
