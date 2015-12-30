package com.github.inventorytracker.model;

import org.junit.*;

import static com.github.inventorytracker.model.DateUnit.*;
import static org.junit.Assert.assertEquals;

public class DateUnitTest {
    
    public DateUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of fromString method, of class DateUnit.
     */
    @Test
    public void testFromString() {
        assertEquals(DAY, fromString("DAY"));
        assertEquals(WEEK, fromString("WEEK"));
        assertEquals(MONTH, fromString("MONTH"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromString_shouldFail(){
        fromString("Dummy");
    }
    
}
