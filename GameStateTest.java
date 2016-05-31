import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class GameStateTest provides unit test cases for CITS1001 Project 1.
 * @author Lyndon While
 * @version 19/5/16 
 */
public class GameStateTest
{
    private GameState gb, gf, g5, g0, gx, gd;
    private Square s2, s4;

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        try   {gb = new GameState("B1.txt");}
        catch (Exception e) {assertTrue("B1.txt shouldn't cause an exception", false);}
        try   {gf = new GameState("NO_SUCH_FILE"); assertTrue("NO_SUCH_FILE should cause an exception", false);}
        catch (Exception e) {}
        try   {g5 = new GameState(0.5);}
        catch (Exception e) {assertTrue("0.5 won't usually cause an exception", false);}
        try   {g0 = new GameState(0); assertTrue("0 should cause an exception", false);}
        catch (Exception e) {}
        s2 = new Square(2);
        s4 = new Square(4);
        gx = new GameState(new Square[][] {{new Square(8),new Square(4),new Square(2),new Square(0)}
                                          ,{new Square(8),new Square(0),new Square(0),new Square(0)}
                                          ,{new Square(2),new Square(2),new Square(2),new Square(0)}
                                          ,{new Square(2),new Square(4),new Square(4),new Square(0)}}, 666);
        gd = new GameState(new Square[][] {{s2,s4,s2,s4}, {s4,s2,s4,s2}, {s2,s4,s2,s4}, {s4,s2,s4,s2}}, 99);
    }
    
    // b is the presented solution, xs describes the expected solution
    // the return value is "" if the solution presented is correct, 
    // o/w it contains a description of the error 
    private String equals(Square[][] b, int[][] xs)
    {
        if (b.length != xs.length) 
           return "board is the wrong size";
        for (int i = 0; i < b.length; i++) if (b[i].length != xs[i].length) 
            return "board is not rectangular";
        String s = "";
        for (int i = 0; i < b.length; i++) 
            for (int j = 0; j < b[i].length; j++)
                if (b[i][j].getSquare() != xs[i][j]) 
                   s += ", <" + i + ", " + j + ">";
       if (s == "") return s;
       else         return "errors at Squares " + s.substring(2);
    }

    @Test
    public void testGameState() 
    {
        assertEquals("gb has a bad square", "", equals(gb.getBoard(), new int[][] {{  2,4,8,16}
                                                                                  ,{ 32,0,0, 0}
                                                                                  ,{ 64,0,0, 0}
                                                                                  ,{128,0,0, 0}}));
        assertEquals("gb score should be 0", 0, gb.getScore());
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                assertTrue("g5 has a bad square", g5.getBoard()[i][j].getSquare() == 0 || g5.getBoard()[i][j].getSquare() == 2);
        assertEquals("g5 score should be 0", 0, g5.getScore());
    }
    
    @Test
    public void testforward()
    {
        boolean b;
        Square[] z; 
        z = new Square[] {new Square(0), new Square(0), new Square(0), new Square(0)};
        GameState g = new GameState(new Square[][] {z}, 0);
        
        b = g.forward(z);
        assertEquals("forward failed Test 1", "", equals(new Square[][] {z}, new int[][] {{0,0,0,0}}));
        
        assertTrue("Test 1 makes no changes", !b);
        z = new Square[] {new Square(2), new Square(8), new Square(4), new Square(0)};
        b = g.forward(z);
        assertTrue("Test 2 makes no changes", !b);
        assertEquals("forward failed Test 2", "", equals(new Square[][] {z}, new int[][] {{2,8,4,0}}));
        
        z = new Square[] {new Square(0), new Square(2), new Square(0), new Square(4)};
        b = g.forward(z);
        assertTrue("Test 3 makes changes", b);
        assertEquals("forward failed Test 3", "", equals(new Square[][] {z}, new int[][] {{2,4,0,0}}));
        
        z = new Square[] {new Square(4), new Square(2), new Square(0), new Square(4)};
        b = g.forward(z);
        assertTrue("Test 4 makes changes", b);
        assertEquals("forward failed Test 4", "", equals(new Square[][] {z}, new int[][] {{4,2,4,0}}));
        
        z = new Square[] {new Square(2), new Square(2), new Square(0), new Square(0)};
        b = g.forward(z);
        assertTrue("Test 5 makes changes", b);
        assertEquals("forward failed Test 5", "", equals(new Square[][] {z}, new int[][] {{4,0,0,0}}));
        
        z = new Square[] {new Square(0), new Square(2), new Square(0), new Square(2)};
        b = g.forward(z);
        assertTrue("Test 6 makes changes", b);
        assertEquals("forward failed Test 6", "", equals(new Square[][] {z}, new int[][] {{4,0,0,0}}));
        
        z = new Square[] {new Square(2), new Square(2), new Square(4), new Square(4)};
        b = g.forward(z);
        assertTrue("Test 7 makes changes", b);
        assertEquals("forward failed Test 7", "", equals(new Square[][] {z}, new int[][] {{4,8,0,0}}));
        
        z = new Square[] {new Square(2), new Square(0), new Square(2), new Square(4)};
        b = g.forward(z);
        assertTrue("Test 8 makes changes", b);
        assertEquals("forward failed Test 8", "", equals(new Square[][] {z}, new int[][] {{4,4,0,0}}));
        
        z = new Square[] {new Square(2), new Square(2), new Square(2), new Square(2)};
        b = g.forward(z);
        assertTrue("Test 9 makes changes", b);
        assertEquals("forward failed Test 9", "", equals(new Square[][] {z}, new int[][] {{4,4,0,0}}));
    }
    
    @Test
    public void testleft()
    {
        gb.left();
        assertEquals("gb has a bad square after left", "", 
                                          equals(gb.getBoard(), new int[][] {{  2,4,8,16}
                                                                            ,{ 32,0,0, 0}
                                                                            ,{ 64,0,0, 0}
                                                                            ,{128,0,0, 0}}));
        assertEquals("gb score should be -1 after left", -1, gb.getScore());
        gx.left();
        assertEquals("gx has a bad square after left", "", 
                                          equals(gx.getBoard(), new int[][] {{  8, 4, 2, 0}
                                                                            ,{  8, 0, 0, 0}
                                                                            ,{  4, 2, 0, 0}
                                                                            ,{  2, 8, 0, 2}}));
        assertEquals("gx score should be 667 after left", 667, gx.getScore());
        gd.left();
        assertEquals("gd has a bad square after left", "", 
                                          equals(gd.getBoard(), new int[][] {{  2, 4, 2, 4}
                                                                            ,{  4, 2, 4, 2}
                                                                            ,{  2, 4, 2, 4}
                                                                            ,{  4, 2, 4, 2}}));
        assertEquals("gd score should be 98 after left", 98, gd.getScore());
    }
    
    @Test
    public void testright()
    {
        gb.right();
        assertEquals("gb has a bad square after right", "", 
                                          equals(gb.getBoard(), new int[][] {{  2, 4, 8, 16}
                                                                            ,{  2, 0, 0, 32}
                                                                            ,{  0, 0, 0, 64}
                                                                            ,{  0, 0, 0,128}}));
        assertEquals("gb score should be 1 after right", 1, gb.getScore());
        gx.right();
        assertEquals("gx has a bad square after right", "", 
                                          equals(gx.getBoard(), new int[][] {{  2, 8, 4, 2}
                                                                            ,{  0, 0, 0, 8}
                                                                            ,{  0, 0, 2, 4}
                                                                            ,{  0, 0, 2, 8}}));
        assertEquals("gx score should be 667 after right", 667, gx.getScore());
        gd.right();
        assertEquals("gd has a bad square after right", "", 
                                          equals(gd.getBoard(), new int[][] {{  2, 4, 2, 4}
                                                                            ,{  4, 2, 4, 2}
                                                                            ,{  2, 4, 2, 4}
                                                                            ,{  4, 2, 4, 2}}));
        assertEquals("gd score should be 98 after right", 98, gd.getScore());
    }
    
    @Test
    public void testup()
    {
        gb.up();
        assertEquals("gb has a bad square after up", "", 
                                          equals(gb.getBoard(), new int[][] {{  2,4,8,16}
                                                                            ,{ 32,0,0, 0}
                                                                            ,{ 64,0,0, 0}
                                                                            ,{128,0,0, 0}}));
        assertEquals("gb score should be -1 after up", -1, gb.getScore());
        gx.up();
        assertEquals("gx has a bad square after up", "", 
                                          equals(gx.getBoard(), new int[][] {{ 16, 4, 4, 0}
                                                                            ,{  4, 2, 4, 0}
                                                                            ,{  0, 4, 0, 0}
                                                                            ,{  2, 0, 0, 0}}));
        assertEquals("gx score should be 667 after up", 667, gx.getScore());
        gd.up();
        assertEquals("gd has a bad square after up", "", 
                                          equals(gd.getBoard(), new int[][] {{  2, 4, 2, 4}
                                                                            ,{  4, 2, 4, 2}
                                                                            ,{  2, 4, 2, 4}
                                                                            ,{  4, 2, 4, 2}}));
        assertEquals("gd score should be 98 after up", 98, gd.getScore());
    }
    
    @Test
    public void testdown()
    {
        gb.down();
        assertEquals("gb has a bad square after down", "", 
                                          equals(gb.getBoard(), new int[][] {{  2,0,0, 2}
                                                                            ,{ 32,0,0, 0}
                                                                            ,{ 64,0,0, 0}
                                                                            ,{128,4,8,16}}));
        assertEquals("gb score should be 1 after down", 1, gb.getScore());
        gx.down();
        assertEquals("gx has a bad square after down", "", 
                                          equals(gx.getBoard(), new int[][] {{  0, 0, 0, 2}
                                                                            ,{  0, 4, 0, 0}
                                                                            ,{ 16, 2, 4, 0}
                                                                            ,{  4, 4, 4, 0}}));
        assertEquals("gx score should be 667 after down", 667, gx.getScore());
        gd.down();
        assertEquals("gd has a bad square after down", "", 
                                          equals(gd.getBoard(), new int[][] {{  2, 4, 2, 4}
                                                                            ,{  4, 2, 4, 2}
                                                                            ,{  2, 4, 2, 4}
                                                                            ,{  4, 2, 4, 2}}));
        assertEquals("gd score should be 98 after down", 98, gd.getScore());
    }
    
    @Test
    public void testclockwise()
    {
        gb.clockwise();
        assertEquals("gb has a bad square after clockwise", "", 
                                          equals(gb.getBoard(), new int[][] {{128,64,32, 2}
                                                                            ,{  0, 0, 0, 4}
                                                                            ,{  0, 0, 0, 8}
                                                                            ,{  0, 0, 0,16}}));
        assertEquals("gb score shouldn't be changed", 0, gb.getScore());
        gx.clockwise();
        assertEquals("gx has a bad square after clockwise", "", 
                                          equals(gx.getBoard(), new int[][] {{  2, 2, 8, 8}
                                                                            ,{  4, 2, 0, 4}
                                                                            ,{  4, 2, 0, 2}
                                                                            ,{  0, 0, 0, 0}}));
        assertEquals("gx score shouldn't be changed", 666, gx.getScore());
    }
    
    @Test
    public void testanticlockwise()
    {
        gb.anticlockwise();
        assertEquals("gb has a bad square after anticlockwise", "", 
                                          equals(gb.getBoard(), new int[][] {{16, 0, 0,  0}
                                                                            ,{ 8, 0, 0,  0}
                                                                            ,{ 4, 0, 0,  0}
                                                                            ,{ 2,32,64,128}}));
        assertEquals("gb score shouldn't be changed", 0, gb.getScore());
        gx.anticlockwise();
        assertEquals("gx has a bad square after anticlockwise", "", 
                                          equals(gx.getBoard(), new int[][] {{  0, 0, 0, 0}
                                                                            ,{  2, 0, 2, 4}
                                                                            ,{  4, 0, 2, 4}
                                                                            ,{  8, 8, 2, 2}}));
        assertEquals("gx score shouldn't be changed", 666, gx.getScore());
    }
    
    @Test
    public void testgameOver()
    {
        assertTrue("gameOver false for gb", !gb.gameOver());
        assertTrue("gameOver false for g5", !g5.gameOver());
        assertTrue("gameOver false for gx", !gx.gameOver());
        assertTrue("gameOver true for gd", gd.gameOver());
        gd.getBoard()[1][1] = s4;
        assertTrue("gameOver false(1) for gd", !gd.gameOver());
        gd.getBoard()[1][1] = new Square(0);
        assertTrue("gameOver false(2) for gd", !gd.gameOver());
        gd.getBoard()[1][1] = s2;
        assertTrue("gameOver true for gd", gd.gameOver());
    }
}