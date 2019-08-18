/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package melan_rashitha_tests;

import cs_calculation.cs_calculation;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author melan
 */
public class CsCalculationTests {

    public CsCalculationTests() {
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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void testCsCalculations() {

        assertTest01();
        assertTest02();
        assertTest03();

    }

    public void assertTest01() {
        String sampleCodeChunk = "public static void main";
        cs_calculation csCalc = new cs_calculation();

        int resultOutputMapper = csCalc.CsCalculateChunk(sampleCodeChunk);
        assertEquals(resultOutputMapper, 2);
    }

    public void assertTest02() {
        String sampleCodeChunk = "public static long fibonacci(long number) { ";
        cs_calculation csCalc = new cs_calculation();

        int resultOutputMapper = csCalc.CsCalculateChunk(sampleCodeChunk);
        assertEquals(resultOutputMapper, 4);
    }

    public void assertTest03() {
        String sampleCodeChunk = "for (int count = 0; count <= 10; count++){ ";
        cs_calculation csCalc = new cs_calculation();

        int resultOutputMapper = csCalc.CsCalculateChunk(sampleCodeChunk);
        assertEquals(resultOutputMapper, 9);
    }
}
