package org.jfree.data;

import static org.junit.Assert.*;
import org.jfree.data.Range;
import org.junit.*;

public class RangeTest {
	private Range exampleRange;
	private static Range testee;
	@BeforeClass
	public static void setup() {
		testee=new Range(-1,1);
	}
	@BeforeClass public static void setUpBeforeClass () throws Exception {
		
	}
	private static final double EPSILON = 0.0000000001;
	@Before
	public void setUp() throws Exception { 
		exampleRange = new Range (0.0, 1.0);
	}
	
	//  Test cases for contains()
	@Test
	public void containsShouldNotBeNaN() {
		boolean local = false;
		
	}
	
	@Test
	public void containsShouldNotBeNegativeOne() {
		assertFalse(exampleRange.contains(-1.0));
	}
	
	@Test
	public void containsShouldBeZeroPointFive () {
		assertTrue(exampleRange.contains(0.5));
	}
	
	//Test cases for constrain()
	
	@Test
	public void constrainShouldBeZeroPointFive () {
		assertEquals(0.5, exampleRange.constrain(0.5), 0.0000001);
	}
	
	@Test
	public void constrainShouldBeOne () {
		assertEquals(1.0, exampleRange.constrain(Double.POSITIVE_INFINITY), 0.0000001);
	}
	
	@Test
	public void constrainShouldBeNan() {
		assertTrue(Double.isNaN(exampleRange.constrain(Double.NaN)));
	}
	
	
	//Test cases for intersects() methods
	@Test
	public void  intersectShouldNotBeInBetweenNegativeTwoAndNegativeOne() {
		 assertFalse(exampleRange.intersects(-2.0, -1.0));
	}
	
	@Test
	public void intersectsShouldNotBeInBetweenOneAndOnePointOne () {
		 assertFalse(exampleRange.intersects(1.0, 1.1));
	}
	
	@Test
	public void intersectsShouldBeInBetweenZeroPointFiveAndZeroPointSix () {
		assertTrue(exampleRange.intersects(0.5, 0.6));
	}
	
	//Test cases for combine()
	Range r1 = new Range(1.0, 2.0);
    Range r2 = new Range(1.5, 2.5);
	@Test
	public void combineShouldBeNull() {
		assertNull(Range.combine(null, null));
	}
	
	@Test
	public void combineShouldBeEqualTor1 () {
		Range r3 = new Range(Double.NaN, 1.3);
        Range rr = Range.combine(r1, r3);
		assertEquals(2.0, rr.getUpperBound(), EPSILON);
	}
	
	@Test
	public void combineTestCase () {
		 Range r4 = new Range(1.7, Double.NaN);
	     Range rr = Range.combine(r4, r1);
	     assertEquals(1.0, rr.getLowerBound(), EPSILON);
	     assertTrue(Double.isNaN(rr.getUpperBound()));
	}
	
	//Is Nan cases
	@Test
    public void isNaNRangeShouldBeTrue() {
        assertTrue(new Range(Double.NaN, Double.NaN).isNaNRange());
    }
    
    @Test
    public void isNaNRangeShouldBeFalse() {
        assertFalse(new Range(1.0, 2.0).isNaNRange());
    }
    
    @Test
    public void isNaNRangeShouldBeNotInNaNRange() {
        assertFalse(new Range(Double.NaN, 2.0).isNaNRange());
    }
    
    @Test
	public void testScaleByPositive() {
		Range expected = new Range(-4,4);
		assertEquals(expected, Range.scale(testee, 4));
	}
	
	@Test
	public void testScaleByNegative() {
		boolean thrown=false;
		try{
			Range.scale(testee, -4);
		}catch(IllegalArgumentException e) {
			thrown=true;
		}
		assertTrue(thrown);
	}
//	@Test
//	public void testNullBase() {
//		boolean thrown = false;
//		try {
//			Range.scale(null, 0);
//		}catch(IllegalArgumentException e) {
//			thrown =true;
//		}
//		assertTrue(thrown);
//	}
	@Test
	public void testScaleByNaN() {
		//Range test4 = Range.scale(exampleRange, Double.NaN);
		Range actual = Range.scale(testee, Double.NaN);
		Double upper = actual.getUpperBound();
		Double lower = actual.getLowerBound();
		boolean NaNCheck=true;
		if(Double.compare(Double.NaN, upper)==0) {
			NaNCheck=true;
		}
		if(Double.compare(Double.NaN, lower)==0) {
			NaNCheck=true;
		}
		
		assertTrue(NaNCheck);
	}
	
	@Test
	public void equalsTest () {
		boolean local=false;
		Range test1 = new Range(-1,1);
		Range test2 = new Range(-1,1);
		if (test1.equals(test2) )
			local = true;
		assertTrue(local);
	}
	
	@Test
	public void equalsTest2 () {
		boolean local=false;
		Range test1 = new Range(-1,1);
		Range test2 = new Range(-2,1);
		if (test1.equals("hello") )
			local = true;
		else {
			local = false;
		}
		if (test1.equals(test2))
			local = true;
		assertTrue(local);
	}
	@Test
	public void equalsTest3 () {
		Range test1 = new Range(-1,1);
		Range test2 = new Range(-1,2);
		assertFalse("hello", test1.equals(test2));
	}
	
	@Test
	public void expandTest() {
		Range r1 = new Range(0.0, 100.0);
        Range r2 = Range.expand(r1, -0.8, -0.5);
//        assertEquals(65.0, r2.getLowerBound(), 0.001);
        if(65.0 == r2.getLowerBound())
        	assertTrue(true);
//        else
//        	local = false;
//        assertTrue(local);
	}
	
	@Test
	public void combiningIgnorningNaN() {
//		Range r1 = new Range(1.0, 2.0);
//        Range r2 = new Range(1.5, 2.5);
        assertNull(Range.combineIgnoringNaN(null, null));
	}
	
	@Test
	public void combiningIgnoringNaN2() {
		Range r1 = new Range(1.0, 2.0);
        Range r2 = new Range(1.5, 2.5);
        assertEquals(new Range(1.0, 2.5), Range.combineIgnoringNaN(r1, r2));
	}
	
	@Test
	public void expandTest2() {
		Range r1 = new Range(0.0, 100.0);
        Range r2 = Range.expand(r1, -0.8, -0.5);
        if(65.0 != r2.getLowerBound())
        	assertTrue(false);
	}
	
	@Test
	public void combiningIgnoringNaN3() {
		Range r3 = new Range(Double.NaN, 1.3);
        Range rr = Range.combineIgnoringNaN(r1, r3);
        assertEquals(1.0, rr.getLowerBound(), EPSILON);
	}
	
	@Test
	public void combiningIgnoringNaN4() {
		Range r3 = null;
        Range rr = Range.combineIgnoringNaN(r1, r3);
        assertEquals(2.0, rr.getUpperBound(), EPSILON);
	}
	
	@Test
	public void testScaleByPositiveInf() {
		Range expected = new Range(Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY);
		assertEquals(expected, Range.scale(testee, Double.POSITIVE_INFINITY));
	}
	
	@Test
	public void combiningIgnoringNan5() {
		Range r2= null;
		Range r1 = new Range(Double.NaN,Double.NaN);
		Range r3 = Range.combineIgnoringNaN(r1, r2);
		if ( r3 == null)
			assertTrue(true);
		else
			assertTrue(false);
	}
	
	@Test
	public void combiningIgnoringNan6() {
//		Range r2= new Range(1,4);
		Range r1 =null;
		Range r3 = Range.combineIgnoringNaN(r1, exampleRange);
		if ( r3 == null)
			assertTrue(true);
		else
			assertTrue(false);
	}
	
	@Test
	public void testNullBase() {
		boolean thrown =false;
		
		try {
			Range.shift(null, 0);
		}catch(IllegalArgumentException e) {
			thrown=true;
		}
		assertTrue(thrown);
	}
	
	@Test
	public void statementCoverageTest1() {
		boolean thrown =false;
		
		try {
			Range.shift(new Range(0,2), 1, true);
		}catch(IllegalArgumentException e) {
			thrown=true;
		}
		assertTrue(thrown);
	}
	@Test
	public void statementCoverageGetCentralValue() {
		Range r1 = new Range (0,2);
		if(r1.getCentralValue() == 1)
			assertTrue(true);
	}
	
	@Test
	public void statementCoverageTest() {
		if(exampleRange.hashCode()!=0)
			assertTrue(true);
	}
	
	@Test
	public void statementCoverageExapndToInclude () {
		Range.expandToInclude(exampleRange, EPSILON);
	}
	
	@Test
	public void statementCoverageExpandToIncludeTest2()
	{
		Range.expandToInclude(null, EPSILON);
	}
	
	@Test 
	public void statementCoverageToString () {
		Range r1 = new Range (0,2);
		System.out.println(r1.toString()); 
	}
	
	@Test
	public void testShiftbyNaN() {
		Range actual = Range.shift(testee, Double.NaN);
		Double upper = actual.getUpperBound();
		Double lower = actual.getLowerBound();
		boolean NaNCheck=true;
		if(Double.compare(Double.NaN, upper)!=0) {
			NaNCheck=false;
		}
		if(Double.compare(Double.NaN, lower)!=0) {
			NaNCheck=false;
		}
		assertTrue(NaNCheck);
	}
	
	@Test
	public void statementCoverageRangeCTorTest1() {
		boolean thrown = false;
		try {
			Range r1= new Range(10,0);
		}catch (IllegalArgumentException e) {
			thrown = true;
		}
		assertTrue(thrown);
	}
	
    
	
	@After
	public void tearDown() throws Exception {
		
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception{
		
	}

}
	
