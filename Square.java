/**
 * This class models one square on the 2048 board.
 * A square can be empty, denoted by 0; 
 * or it can hold a tile with a number in {2,4,8,16,32,64,...}. 
 * 
 * @author Lyndon While
 * @version 1.0
 */

public class Square
{
    private int x; // the value of the tile sitting on the square, or 0 for empty

    // create a square with the value x, or empty if x is not legal 
    public Square(int x)
    {
        try{
            setSquare(x);
        } catch ( Exception e ){
            System.out.println("Exception : the value of Square must be a square of 2");
        }
    }

    // return the current value of the square 
    public int getSquare()
    {
        return x;
    }
    
    // return true iff the square is empty
    public boolean isEmpty()
    {
        return x == 0;
    }
    
    // put a tile of value x on the square, if x is legal; 
    // otherwise throw an Exception with a suitable error message 
    public void setSquare(int x) throws Exception
    {
        if( checkSqrt(x) ){
            this.x = x;
        } else {
            throw new Exception("x :" + x + " must be a square of 2");
        }
    }
    
    private boolean checkSqrt(int n){
        if( n == 1){
            return false;
        } else if ( (n & (n - 1)) == 0 ){ 
            return true;
        } else {
            return false;
        }
    }
    
    
    
}
