package org.jfree.data;

import static org.junit.Assert.*;
import org.jmock.Mockery;
import org.junit.*;
import org.jfree.data.Values2D;
import org.jmock.Expectations;
import org.jfree.data.DataUtilities;
import org.junit.Test;

public class DataUtilitiesTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
	}
	
	
	/*****
	 *createNumberArray is tested against wrong array type
	 */
	@Test(expected = IllegalArgumentException.class)
	public void createNumberArrayWrongType() {
		//What our output should be
		//double max value is 1.79 e
		Number[] expectednegative = {-1e10, -2e10 };
		//Passing a 1D double array
		double[] passednegative = {-1e10, -2e10 };
		
		Number[] output = DataUtilities.createNumberArray(null);

		assertEquals("The IllegalArgumentException is thrown", expectednegative, output);

	}
	
	/*****
	 *createNumberArray is tested against boundary conditions
	 */
	@Test
	public void createNumberArrayTestBoundary() {
		//What our output should be
		//double max value is 1.79 e
		Number[] expectednegative = {-1e10, -2e10 };
		//Passing a 1D double array
		double[] passednegative = {-1e10, -2e10 };
		
		Number[] expectedpositive = {1e10, 2e10};
		//Passing a 2D double array
		double[] passedpositive = {1e10,2e10};
		
		//Creating the Number[][] array using the createNumberArray2D function
		Number[] outputneg = DataUtilities.createNumberArray(passednegative);
		Number[] outputpos = DataUtilities.createNumberArray(passedpositive);

		//verify
		assertArrayEquals("createNumberArray2D failed, correct output is.", expectednegative, outputneg);
		assertArrayEquals("createNumberArray2D failed, correct output is.", expectedpositive, outputpos);

	}
	
	/*****
	 * createNumberArray2D is tested to see if it creates the right array
	 */
	@Test
	public void createNumberArray2DTest() {
		//What our output should be
		Number[][] expected = { { 1.0, 2.0, 3.0, 4.0, 5.0, 6.0}, { 5.0, 6.0, 7.0, 8.0, 9.0, 10.0} };
		//Passing a 2D double array
		double[][] passed = { { 1.0, 2.0, 3.0, 4.0, 5.0, 6.0 }, { 5.0, 6.0, 7.0, 8.0, 9.0, 10.0 } };
		
		//Creating the Number[][] array using the createNumberArray2D function
		Number[][] output = DataUtilities.createNumberArray2D(passed);
		//verify
		assertArrayEquals("createNumberArray2D failed, correct output is.", expected, output);
	}

	/*****
	 * createNumberArray2D is tested in 1x1 array
	 */
	@Test
	public void createNumberArray2DTestSmall() {
		//What our output should be
		Number[][] expected = { { 1.0}, { 1.0} };
		//Passing a 2D double array
		double[][] passed = { { 1.0} , { 1.0} };
		
		//Creating the Number[][] array using the createNumberArray2D function
		Number[][] output = DataUtilities.createNumberArray2D(passed);
		//verify
		assertArrayEquals("createNumberArray2D failed, correct output is.", expected, output);
	}
	
	/*****
	 * createNumberArray2D is tested against null inputs
	 */
	@Test
	public void createNumberArray2DTestNull() {
		//What our output should be
		Number[][] expected = {{} ,{} };
		//Passing a 2D double array
		double[][] passed = {{} ,{} };
		
		//Creating the Number[][] array using the createNumberArray2D function
		Number[][] output = DataUtilities.createNumberArray2D(passed);
		//verify
		assertArrayEquals("createNumberArray2D failed, correct output is.", expected, output);
	}
	
	/*****
	 * createNumberArray2D is tested against boundary conditions
	 */
	@Test
	public void createNumberArray2DTestBoundary() {
		//What our output should be
		//double max value is 1.79 e
		Number[][] expectednegative = {{-1e10} ,{-1e10} };
		//Passing a 2D double array
		double[][] passednegative = {{-1e10} ,{-1e10} };
		
		Number[][] expectedpositive = {{1e10} ,{1e10} };
		//Passing a 2D double array
		double[][] passedpositive = {{1e10} ,{1e10} };
		
		//Creating the Number[][] array using the createNumberArray2D function
		Number[][] outputneg = DataUtilities.createNumberArray2D(passednegative);
		Number[][] outputpos = DataUtilities.createNumberArray2D(passedpositive);

		//verify
		assertArrayEquals("createNumberArray2D failed, correct output is.", expectednegative, outputneg);
		assertArrayEquals("createNumberArray2D failed, correct output is.", expectedpositive, outputpos);

	}
	
	/**
	 * The following test compares the calculated total of the columns with the expected
	 * keeps negative computations in check.
	 */
	@Test
	public void calculateColumnTotalnegBoundary() {
		Mockery mockingContext = new Mockery();
		final Values2D values = mockingContext.mock(Values2D.class);

		mockingContext.checking(new Expectations() {
			{
				one(values).getRowCount();
				will(returnValue(2));

				//Adding index 0, test negative boundary
				one(values).getValue(0, 0);
				will(returnValue(-1e10));

				one(values).getValue(1, 0);
				will(returnValue(-1e10));		
			
			}
		});

		//Adding index 0, testing negative boundary
		double resultneg = DataUtilities.calculateColumnTotal(values, 0);
		assertEquals("The column total is adding up to -2e10", -2e10, resultneg, .000001d);
	
	}
	
	/**
	 * The following test compares the calculated total of the columns with the expected
	 * only positive computations
	 */
	@Test
	public void calculateColumnTotalposBoundary() {
		Mockery mockingContext = new Mockery();
		final Values2D values = mockingContext.mock(Values2D.class);

		mockingContext.checking(new Expectations() {
			{
				one(values).getRowCount();
				will(returnValue(2));

				//Adding index 0, test negative boundary
				one(values).getValue(0, 0);
				will(returnValue(1e10));

				one(values).getValue(1, 0);
				will(returnValue(1e10));		
			
			}
		});

		//Adding index 0, testing positive boundary
		double resultpos = DataUtilities.calculateColumnTotal(values, 0);
		assertEquals("The column total is adding up to 2e10", 2e10, resultpos, .000001d);
	
	}
	
	/**
	 * The following test compares the calculated total of the columns with the expected
	 */
	@Test(expected = IllegalArgumentException.class)
	public void calculateColumnTotalInvalidIndex() {
		Mockery mockingContext = new Mockery();
		final Values2D values = mockingContext.mock(Values2D.class);

		mockingContext.checking(new Expectations() {
			{
				one(values).getRowCount();
				will(returnValue(2));
				one(values).getValue(0, -1);
				will(returnValue(5.0));
				one(values).getValue(1, -1);
				will(returnValue(5.0));
				
			}
		});
		double result = DataUtilities.calculateColumnTotal(values, -1);
		assertEquals("The column value cannot be negative", 10, result,.0001d);
	}
	
	/**
	 * The following test compares the calculated total of the columns with the expected
	 */
	@Test(expected = IllegalArgumentException.class)
	public void calculateColumnTotalBlank() {
		Mockery mockingContext = new Mockery();
		final Values2D values = mockingContext.mock(Values2D.class);

		mockingContext.checking(new Expectations() {
			{
				one(values).getRowCount();
					will(returnValue(0)); //empty
			}
		});
		
		double result = DataUtilities.calculateColumnTotal(null,1);
		assertEquals("The column total is adding up to 0", 0, result);
	}
	
	/**
	 * The following test compares the calculated total of the columns with the expected
	 * testing the calculateRowTotal(Values2D, int, int[])
	 */
	 @Test
		public void calculateColumnTotalAllRows() {
		  	Mockery mockingContext = new Mockery();
		  	final Values2D table= mockingContext.mock(Values2D.class);
		  	mockingContext.checking(new Expectations(){ {
			    one(table).getRowCount();
			    will(returnValue(3));
			
			    one(table).getValue(0, 0);
			    will(returnValue(4));   
			    
			    one(table).getValue(1, 0);
			    will(returnValue(3)); 
			    
			    one(table).getValue(2, 0);
			    will(returnValue(3));
		    } });
		    
			int[] validRows = {0,1,2};	//calculate all rows
			double result =  DataUtilities.calculateColumnTotal(table, 0, validRows);
		    assertEquals("calculateColumnTotal should return 10.0", 10.0, result, 0.0000001d);
		}
	
	
	/**
	 * The following test compares the calculated total of the columns with the expected
	 * testing the calculateRowTotal(Values2D, int, int[])
	 */
	  @Test
	    public void calculateRowTotalNullValidArray(){
		  Mockery mockingContext = new Mockery();
		  final Values2D table= mockingContext.mock(Values2D.class);
		  mockingContext.checking(new Expectations(){
			  {
	            one(table).getColumnCount();
	            will(returnValue(1));
	            
	            one(table).getValue(0, 0);
	            will(returnValue(null));
	         }
		  });
	        
	        int[] valid_Rows = {0};
	        double result =  DataUtilities.calculateRowTotal(table, 0, valid_Rows);
	        assertEquals("calculateRowTotal with null value and valid Array should return 0.0", 0.0, result,
	                 0.00001d);
	    }
	  
	  
	
	/*****
	 * calculateRowTotal is tested against negative boundary conditions
	 */
	@Test
	public void calculateRowTotalnegBoundary() {
		Mockery mockingContext = new Mockery();
		final Values2D values = mockingContext.mock(Values2D.class);

		mockingContext.checking(new Expectations() {
			{
				one(values).getColumnCount();
				will(returnValue(2));

				//Adding index 0, test negative boundary
				one(values).getValue(0, 0);
				will(returnValue(-1e10));

				one(values).getValue(0, 1);
				will(returnValue(-1e10));		
			
			}
		});

		//Adding index 0, testing negative boundary
		double resultneg = DataUtilities.calculateRowTotal(values,0);
		assertEquals("The row total is adding up to -2e10", -2e10, resultneg, .000001d);
	
	}
	
	/*****
	 * calculateRowTotal is tested against positive boundary conditions
	 */
	@Test
	public void calculateRowTotalposBoundary() {
		Mockery mockingContext = new Mockery();
		final Values2D values = mockingContext.mock(Values2D.class);

		mockingContext.checking(new Expectations() {
			{
				one(values).getColumnCount();
				will(returnValue(2));

				//Adding index 0, test negative boundary
				one(values).getValue(0, 0);
				will(returnValue(1e10));

				one(values).getValue(0, 1);
				will(returnValue(1e10));		
			
			}
		});

		//Adding index 0, testing positive boundary
		double resultpos = DataUtilities.calculateRowTotal(values, 0);
		assertEquals("The row total is adding up to 2e10", 2e10, resultpos, .000001d);	
	}
	

	/*****
	 * calculateRowTotal is tested against an invalid index
	 * returns fail and throws IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void calculateRowTotalInvalidIndex() {
		Mockery mockingContext = new Mockery();
		final Values2D values = mockingContext.mock(Values2D.class);

		mockingContext.checking(new Expectations() {
			{
				//Enter column values
				one(values).getColumnCount();
				will(returnValue(2));
				one(values).getValue(-1, 0);
				will(returnValue(6.1));
				one(values).getValue(-1, 1);
				will(returnValue(6.1));
				
			}
		});
		double result = DataUtilities.calculateRowTotal(values, -1);
		assertEquals("The row value cannot be negative", 12.2, result,.0001d);
	}
	
	/*****
	 * calculateRowTotal is tested against blank/null values
	 */
	@Test(expected = IllegalArgumentException.class)
	public void calculateRowTotalBlank() {
		//Create mock object
		Mockery mockingContext = new Mockery();
		final Values2D values = mockingContext.mock(Values2D.class);

		//Return no columns
		mockingContext.checking(new Expectations() {
			{
				one(values).getColumnCount();
					will(returnValue(0)); //empty
			}
		});
		
		//Check for null rows
		double result = DataUtilities.calculateRowTotal(null,1);
		assertEquals("The row total is adding up to 0", 0, result);
	}
	
	/**
	 * The following test compares the arrays and 
	 * checks whether they match with respect to all elements
	 */
    @Test
    public void EqualCheckTest() 
    {
    	//both arrays are empty
        double[][] arr1 = null;
        double[][] arr2 = null;
        //empty array and populated
        double[][] arr3 = null;
        double[][] arr4 = {{100, 200, 300, 8, 400}, {430, 423, 975}};
        //negative array and single row array
        double[][] arr5 = {{100,200,300}};
        double[][] arr6 = {{-100, -200, -300, -8, -400}, {-100, -200, -300, -8, -400}};
        //compare empty arrays
        boolean check1 = DataUtilities.equal(arr1, arr2);
        //compare empty and non empty
        boolean check2 = DataUtilities.equal(arr3, arr4);
        //compare single row and multiple row elements
        boolean check3 = DataUtilities.equal(arr5, arr6);
        
        //assert equal statements
        assertEquals("Expected check should return true", true, check1);
        assertEquals("Expected check should return false", false, check2);
        assertEquals("Expected check should return false", false, check3);
    }
    
    /**
   	 * The following test compares the equal arrays to check  
   	 * if the function recognizes that the arrays are equal
   	 */
    @Test
   	public void EqualCheckPopulatedTest() 
   	{
   		//same arrays
   		double[][] arr1 = {{100, 200, 300, 8, 400}, {100, 200, 300, 8, 400}};
   		double[][] arr2 = {{100, 200, 300, 8, 400}, {100, 200, 300, 8, 400}};
   		//equal function testing
   		boolean check1 = DataUtilities.equal(arr1, arr2);
   		//assert statement
   		assertEquals("Expected check should return true", true, check1);
   	}
	

	/**
	 * The following test compares the empty arrays to check  
	 * if the function recognizes that the arrays are equal and as a reuslt empty
	 */
	@Test
    public void EqualCheckNotPopulatedTest() 
    {
		//empty array
        double[][] arr1 = {,};
        double[][] arr2 = {,};
        //equal function test
        boolean check1 = DataUtilities.equal(arr1, arr2);
        //assert statement
        assertEquals("Expected check should return true", true, check1);
    }
	
	
	/*****
	 * clone method is tested against filled double arrays
	 */
	@Test
    public void cloneTest() {
			//What our output should be
            double[][] expected = { { 1.0,  1.0} };
            //Passing a 2D double array
            double[][] passed = { { 1.0,  1.0} };
            //Creating the Double[][] array using the .clone function
            double[][] output = DataUtilities.clone(passed);
        assertEquals("The cloned double array is 1.0, 1.0", expected, output);
    }
	
	/*****
	 * clone method is tested against empty double arrays
	 */
	@Test
    public void cloneTestEmpty() {
			//What our output should be
            double[][] expected = { , };
            //Passing a 2D double array
            double[][] passed = { , };
            //Creating the Double[][] array using the .clone function
            double[][] output = DataUtilities.clone(passed);
        assertEquals("The cloned double array is { , }", expected, output);
    }
	
	/*****
	 * clone method is tested against null values
	 */
	@Test(expected = IllegalArgumentException.class)
    public void cloneTestNull() {
			//What our output should be
            double[][] expected = { , };
            //Passing a 2D double array
            double[][] passed = { , };
            //Creating the Double[][] array using the .clone function
            double[][] output = DataUtilities.clone(null);
        assertEquals("The cloned double array is { , }", expected, output);
    }
	
	
	/***
	 * getcumulative percentages, testing 10%, should expect 0.1
	 ***/
	
	@Test
	public void getCumuPercentageAtZeroTenPercent() {
		
		Mockery MockKeyedValues = new Mockery();
		final KeyedValues table = MockKeyedValues.mock(KeyedValues.class);
		
		MockKeyedValues.checking(new Expectations() {{
			atLeast(1).of(table).getItemCount();
			will(returnValue((3)));
			
			atLeast(1).of(table).getValue(0);
			will(returnValue(1));
			
			atLeast(1).of(table).getValue(1);
			will(returnValue(3));
			
			atLeast(1).of(table).getValue(2);
			will(returnValue(6));
			
			atLeast(1).of(table).getKey(0);
			will(returnValue(10));
			
			atLeast(1).of(table).getKey(1);
			will(returnValue(30));
				
			atLeast(1).of(table).getKey(2);
			will(returnValue(60));
		}});
		
		KeyedValues KV = DataUtilities.getCumulativePercentages(table);
		assertEquals("The cumulative percentage at index 0 should be 0.1", 0.1, KV.getValue(0).doubleValue(), .0000001d);
	}
	
	/***
	 * getcumulative percentages, testing 50%, expect 0.5
	 ***/
	
	
	@Test
	public void getCumuPercentageAtFiftyPercent() {
		
		Mockery MockKeyedValues = new Mockery();
		final KeyedValues table = MockKeyedValues.mock(KeyedValues.class);
		
		MockKeyedValues.checking(new Expectations() {{
			atLeast(1).of(table).getItemCount();
			will(returnValue((3)));
			
			atLeast(1).of(table).getValue(0);
			will(returnValue(1));
			
			atLeast(1).of(table).getValue(1);
			will(returnValue(4));
			
			atLeast(1).of(table).getValue(2);
			will(returnValue(5));
			
			atLeast(1).of(table).getKey(0);
			will(returnValue(10));
			
			atLeast(1).of(table).getKey(1);
			will(returnValue(40));
			
			atLeast(1).of(table).getKey(2);
			will(returnValue(50));
		}});
		
		KeyedValues KV = DataUtilities.getCumulativePercentages(table);
		assertEquals("The cumulative percentage at index 1 should be 0.5", 0.5, KV.getValue(1).doubleValue(), .000001d);
	}
	
	
	/***
	 * getcumulative percentages, testing 100%, expect 1.0
	 ***/
	
	@Test
	public void getCumuPercentageAtHundredPercent() {
		
		Mockery MockKeyedValues = new Mockery();
		final KeyedValues table = MockKeyedValues.mock(KeyedValues.class);
		
		MockKeyedValues.checking(new Expectations() {{
			atLeast(1).of(table).getItemCount();
			will(returnValue((3)));
			
			atLeast(1).of(table).getValue(0);
			will(returnValue(1));
			
			atLeast(1).of(table).getValue(1);
			will(returnValue(4));
			
			atLeast(1).of(table).getValue(2);
			will(returnValue(5));
			
			atLeast(1).of(table).getKey(0);
			will(returnValue(10));
			
			atLeast(1).of(table).getKey(1);
			will(returnValue(40));
			
			atLeast(1).of(table).getKey(2);
			will(returnValue(50));
		}});
		
		KeyedValues KV = DataUtilities.getCumulativePercentages(table);
		assertEquals("The cumulative percentage at index 2 should be 1.0", 1.0, KV.getValue(2).doubleValue(), .000001d);
	}
	
	/***
	 * getcumulative percentages, testing null in the table, should not affect output
	 * expect 0.2
	 ***/
	
	@Test
	public void getCumuPercentageAtTwentyPercentWithNull() {
		
		Mockery MockKeyedValues = new Mockery();
		final KeyedValues table = MockKeyedValues.mock(KeyedValues.class);
		
		MockKeyedValues.checking(new Expectations() {{
			atLeast(1).of(table).getItemCount();
			will(returnValue((3)));
			
			atLeast(1).of(table).getValue(0);
			will(returnValue(2));
			
			atLeast(1).of(table).getValue(1);
			will(returnValue(8));
			
			atLeast(1).of(table).getValue(2);
			will(returnValue(null));
			
			atLeast(1).of(table).getKey(0);
			will(returnValue(10));
			
			atLeast(1).of(table).getKey(1);
			will(returnValue(40));
			
			atLeast(1).of(table).getKey(2);
			will(returnValue(50));
		}});
		
		KeyedValues KV = DataUtilities.getCumulativePercentages(table);
		assertEquals("The cumulative percentage at index 0 should be 0.2", 0.2, KV.getValue(0).doubleValue(), .00001d);
	}
	
		
	
	
	@After
	public void tearDown() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
	}


}