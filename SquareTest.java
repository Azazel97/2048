import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class SquareTest provides unit test cases for CITS1001 Project 1.
 * @author Lyndon While
 * @version 10/5/16
 */
public class SquareTest
{
    private Square[] sqs;
    int tests = 10;

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        sqs = new Square[tests];
        for (int i = 0; i < tests; i++) sqs[i] = new Square(i); 
    }

    @Test
    public void testTerm() 
    {
        assertEquals("new Square(0) should set x to 0", 0, sqs[0].getSquare());
        for (int x = 2; x < tests; x *= 2)
            assertEquals("new Square(" + x + ") should set x to " + x, x, sqs[x].getSquare());
        for (int x = 1; x <= 5; x += 2)
            assertEquals("new Square(" + x + ") should set x to 0", 0, sqs[x].getSquare());
    }
    
    @Test
    public void testsetSquare()
    {
        for (int x = 0; x < 3; x++) 
        {
            try   {sqs[x].setSquare(1); 
                   assertTrue("Exception expected when setting " + x + " to " + 1, false);}
            catch (Exception e) {assertEquals("sqs[" + x + "] should still be " + (x / 2 * 2), x / 2 * 2, sqs[x].getSquare());}
            try   {sqs[x].setSquare(2); 
                   assertEquals("sqs[" + x + "] should now be 2", 2, sqs[x].getSquare());}
            catch (Exception e) {assertTrue("No exception expected when setting " + x + " to " + 2, false);}
        }
    }
}