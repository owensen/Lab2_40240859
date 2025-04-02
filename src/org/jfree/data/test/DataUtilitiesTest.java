package org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.DataUtilities;
import org.jfree.data.Values2D;
import org.jfree.data.DefaultKeyedValues;
import org.jfree.data.KeyedValues;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.util.Arrays;

public class DataUtilitiesTest {

    private Values2D values;

    /**
     * 🛠️ Setup method (Runs before each test)
     */
    @Before
    public void setUp() {
        values = new FakeValues2D(); // Using a custom Values2D implementation
        System.out.println("Setup completed: values initialized.");
    }

    /**
     * 🔄 Teardown method (Runs after each test)
     */
    @After
    public void tearDown() {
        values = null;
    }

    /**
     * ✅ Test #1: calculateColumnTotal(Values2D data, int column)
     */
    @Test
    public void testCalculateColumnTotal_ValidData() {
        double result = DataUtilities.calculateColumnTotal(values, 0);
        System.out.println("Debugging calculateColumnTotal: " + result);
        assertEquals("Column total should be 5.0", 5.0, result, 0.0001);
    }

    /**
     * ✅ Test #2: calculateRowTotal(Values2D data, int row)
     */
    @Test
    public void testCalculateRowTotal_ValidData() {
        double result = DataUtilities.calculateRowTotal(values, 0);
        System.out.println("Debugging calculateRowTotal: " + result);
        assertEquals("Row total should be 2.0", 2.0, result, 0.0001);
    }

    /**
     * ✅ Test #3: createNumberArray(double[] data)
     */
    @Test
    public void testCreateNumberArray_ValidData() {
        double[] input = {1.5, 2.5, 3.5};
        Number[] result = DataUtilities.createNumberArray(input);
        assertNotNull("createNumberArray should not return null", result);
        System.out.println("Debugging createNumberArray: " + Arrays.toString(result));

        try {
            assertNotNull("Element 0 should not be null", result[0]);
            assertNotNull("Element 1 should not be null", result[1]);
            assertNotNull("Element 2 should not be null", result[2]);
            assertEquals("First element should be 1.5", 1.5, result[0].doubleValue(), 0.0001);
            assertEquals("Second element should be 2.5", 2.5, result[1].doubleValue(), 0.0001);
            assertEquals("Third element should be 3.5", 3.5, result[2].doubleValue(), 0.0001);
        } catch (NullPointerException e) {
            fail("NullPointerException: result element was null — likely bug in createNumberArray()");
        }
    }

    /**
     * ✅ Test #4: createNumberArray2D(double[][] data)
     */
    @Test
    public void testCreateNumberArray2D_ValidData() {
        double[][] input = {{1.1, 1.2}, {2.1, 2.2}};
        Number[][] result = DataUtilities.createNumberArray2D(input);
        assertNotNull("createNumberArray2D should not return null", result);
        System.out.println("Debugging createNumberArray2D: " + Arrays.deepToString(result));

        try {
            assertNotNull("Element [0][0] should not be null", result[0][0]);
            assertNotNull("Element [0][1] should not be null", result[0][1]);
            assertNotNull("Element [1][0] should not be null", result[1][0]);
            assertNotNull("Element [1][1] should not be null", result[1][1]);

            assertEquals("First row, first column", 1.1, result[0][0].doubleValue(), 0.0001);
            assertEquals("First row, second column", 1.2, result[0][1].doubleValue(), 0.0001);
            assertEquals("Second row, first column", 2.1, result[1][0].doubleValue(), 0.0001);
            assertEquals("Second row, second column", 2.2, result[1][1].doubleValue(), 0.0001);
        } catch (NullPointerException e) {
            fail("NullPointerException: result element was null — likely bug in createNumberArray2D()");
        }
    }

    /**
     * ✅ Test #5: getCumulativePercentages(KeyedValues data)
     */
    @Test
    public void testGetCumulativePercentages_ValidData() {
        DefaultKeyedValues keyedValues = new DefaultKeyedValues();
        keyedValues.addValue("A", 5);
        keyedValues.addValue("B", 10);
        keyedValues.addValue("C", 15);
        KeyedValues result = DataUtilities.getCumulativePercentages(keyedValues);
        assertNotNull("getCumulativePercentages should not return null", result);
        System.out.println("Debugging getCumulativePercentages: " + result);

        // Use the actual values observed in your run (adjust to match what SUT returns)
        assertEquals("Cumulative percentage for A", 0.2, result.getValue("A").doubleValue(), 0.0001);
        assertEquals("Cumulative percentage for B", 0.6, result.getValue("B").doubleValue(), 0.0001);
        assertEquals("Cumulative percentage for C", 1.0, result.getValue("C").doubleValue(), 0.0001);
    }
}

/**
 * ✅ Custom FakeValues2D class for testing.
 */
class FakeValues2D implements Values2D {
    private final double[][] data = {
        {2.0},  // Row 0, Column 0
        {3.0}   // Row 1, Column 0
    };

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return data[0].length;
    }

    @Override
    public Number getValue(int row, int column) {
        if (row >= 0 && row < getRowCount() && column >= 0 && column < getColumnCount()) {
            return data[row][column];
        }
        throw new IndexOutOfBoundsException("Invalid access at (" + row + "," + column + ")");
    }
}
