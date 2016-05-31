 /**
 * This class models the 2048 game board.
 * 
 * @author Lyndon While
 * @version 1.0
 */

public class GameState
{
    public static final int noOfSquares = 4; // the extent of the board in both directions

    private Square[][] board; // the current state of the board 
    private int score;        // the current score

    // initialise the board and the score directly from the arguments 
    // intended principally for testing 
    public GameState(Square[][] board, int score) 
    {
        this.board = board;
        this.score = score;
    }
    // initialise the board from the file file 
    // you may assume that the file has four lines, each with four integers separated by a space 
    // throw an exception if an empty board is generated 
    public GameState(String file) throws Exception 
    {
        FileIO fio = new FileIO(file);
        
        int counter_string = 0;
        board = new Square[noOfSquares][noOfSquares];   
        for( String line : fio.lines ){
            String[] number= line.split("\\s+");
            for(int i = 0 ; i < number.length ; i++){
                board[counter_string][i] = new Square( Integer.parseInt( number[i] ) );
            }
            counter_string++;
        }

        if( checkempty(board) ){
            throw new Exception ("Board is Empty!");
        }
    }
    //Check whether the board is empty
    private boolean checkempty(Square[][] board){
        int sum = 0; 
        for(int i = 0 ; i < board.length; i++){
            for(int j = 0; j < board[i].length ; j++){
                sum = sum + board[i][j].getSquare();                
            }
        }

        if(sum == 0){
            return true;
        }else{
            return false;
        }
    }
    // initialise the board randomly; each square should be set to 2 with probability p, and to empty otherwise
    // throw an exception if an empty board is generated 
    public GameState(double p) throws Exception 
    {
        if( p == 0 || p > 1){
            throw new Exception ("p : " + p + "must be between 0 and 1");
        }

        board = new Square[noOfSquares][noOfSquares];

        for(int i = 0 ; i < board.length; i++){
            for(int j = 0; j < board[i].length ; j++){
                if(Math.random() < p){
                    board[i][j] = new Square( 2 );
                } else {
                    board[i][j] = new Square( 0 );
                }
            }
        }

        if( checkempty(board) ){
            throw new Exception ("Board is Empty!");
        }
    }

    public GameState clonegame(GameState gametobe){
    	Square[][] boardclone = new Square[noOfSquares][noOfSquares];
        for(int i = 0 ; i < boardclone.length; i++){
            for(int j = 0; j < boardclone[i].length ; j++){
            	boardclone[i][j] = new Square(gametobe.getBoard()[i][j].getSquare());
            }
        }
        
        int score = gametobe.getScore();
        GameState game = new GameState(boardclone, score);
        return game;
    }
    
    // return the current state of the board
    public Square[][] getBoard() 
    {
        return board;
    }

    // return the current score
    public int getScore()
    {
        return score;
    }

    public boolean forward(Square[] row)
    {
        boolean result = false;
        int sum = 0;

        for(int i = 0 ; i < row.length ; i++){
            if( row[i].getSquare() == 0 ){
                sum++;
            }
        }

        if( sum == row.length ){
            return false;
        }

        for (int j = 1, i = 0; j < row.length; j++) {
            if (row[i].getSquare() == 0 && row[j].getSquare() != 0) {
                i = j;
            } else if (row[j].getSquare() != 0 && i != j) {
                if (row[j].getSquare() == row[i].getSquare() ) {
                    result = true;
                    try{
                        row[i].setSquare( row[i].getSquare() * 2 );
                    } catch ( Exception e ){
                        System.out.println(e.getMessage());
                    }

                    try{
                        row[j].setSquare( 0 );
                    } catch ( Exception e ){
                        System.out.println(e.getMessage());
                    }

                    i = j;
                } else {
                    i = j;
                }
            }
        }

        for (int j = 0, i = 0; j < row.length; j++) {
            if (row[j].getSquare() != 0) {
                try{
                    row[i].setSquare( row[j].getSquare() );
                } catch ( Exception e ){
                    System.out.println(e.getMessage());
                }

                if(i++ != j){
                    result = true;
                    try{
                        row[j].setSquare( 0 );
                    } catch ( Exception e ){
                        System.out.println(e.getMessage());
                    }
                }

            }
        }

        return result;
    }

    public void printboard(){

        for(int i = 0 ; i < board.length ; i++){
            for(int j = 0 ; j < board[i].length ; j++){
                System.out.print(" " + board[i][j].getSquare());
            }
            System.out.println(" ");
        }

    }

    // make a Left move
    public void left()
    {
        int result = 0;
        for(int i = 0 ; i < board.length ; i++){
            if (  forward(board[i]) == true ){
                result++;
            }
        }

        if(result > 0){
            score++;
            addSquare('l');
        } else if (result == 0){
            score--;
        } 
    }

    public void addSquare(char a){
        if( a == 'u'){
            for(int i = 0 ; i < noOfSquares ; i++){
                if( board[noOfSquares-1][i].getSquare() == 0 ){
                    try{
                        board[noOfSquares-1][i].setSquare( 2 );
                    } catch ( Exception e ){
                        System.out.println(e.getMessage());
                    }
                    break;
                }
            }
        } else if ( a == 'd' ){
            for(int i = noOfSquares - 1  ; i >= 0 ; i--){
                if( board[0][i].getSquare() == 0 ){
                    try{
                        board[0][i].setSquare( 2 );
                    } catch ( Exception e ){
                        System.out.println(e.getMessage());
                    }
                    break;
                }
            }
        } else if ( a == 'l' ){
            for(int i = noOfSquares - 1  ; i >= 0 ; i--){
                if( board[i][noOfSquares-1].getSquare() == 0 ){
                    try{
                        board[i][noOfSquares-1].setSquare( 2 );
                    } catch ( Exception e ){
                        System.out.println(e.getMessage());
                    }
                    break;
                }
            }
        } else if ( a == 'r' ){
            for(int i = 0 ; i < noOfSquares ; i++){
                if( board[i][0].getSquare() == 0 ){
                    try{
                        board[i][0].setSquare( 2 );
                    } catch ( Exception e ){
                        System.out.println(e.getMessage());
                    }
                    break;
                }
            }
        }
    }

    // make a Right move
    public void right()
    {
        int result = 0;
        clockwise(); 
        clockwise();
        for(int i = 0 ; i < board.length ; i++){
            if (  forward(board[i]) == true ){
                result++;
            }
        }
        anticlockwise(); 
        anticlockwise();
        if(result > 0){
            score++;
            addSquare('r');
        } else if (result == 0){
            score--;
        } 
    }

    // make an Up move
    public void up()
    {
        int result = 0;
        anticlockwise(); 
        for(int i = 0 ; i < board.length ; i++){
            if (  forward(board[i]) == true ){
                result++;
            }
        }
        clockwise(); 
        if(result > 0){
            score++;
            addSquare('u');
        } else if (result == 0){
            score--;
        } 
    }

    // make a Down move
    public void down()
    {
        int result = 0;
        clockwise();
        for(int i = 0 ; i < board.length ; i++){
            if (  forward(board[i]) == true ){
                result++;
            }
        }
        anticlockwise(); 
        if(result > 0){
            score++;
            addSquare('d');
        } else if (result == 0){
            score--;
        } 
    }

    private void copymap(Square[][] org, Square[][] copy){

        for(int i = 0 ; i < org.length ; i++){
            for(int j = 0; j < org.length ; j++){
                try{
                    copy[i][j] = new Square( org[i][j].getSquare() );
                } catch ( Exception e ){
                    System.out.println(e.getMessage());
                }
            }
        }
        
    }

    // rotate the board clockwise 90 degrees
    public void clockwise()
    {
        Square[][] boardcpy = new Square[noOfSquares][noOfSquares];
        copymap(board, boardcpy);

        for (int i = 0; i < noOfSquares; ++i) {
            for (int j = 0; j < noOfSquares; ++j) {
                try{
                    board[i][j].setSquare( boardcpy[noOfSquares-j-1][i].getSquare() );
                } catch ( Exception e ){
                    System.out.println(e.getMessage());
                }
            }
        }

    }

    // rotate the board anti-clockwise 90 degrees
    public void anticlockwise()
    {
        Square[][] boardcpy = new Square[noOfSquares][noOfSquares];
        copymap(board, boardcpy);

        for (int i = 0; i < noOfSquares; ++i) {
            for (int j = 0; j < noOfSquares; ++j) {
                try{
                    board[i][j].setSquare( boardcpy[j][noOfSquares - 1 - i].getSquare() );
                } catch ( Exception e ){
                    System.out.println(e.getMessage());
                }
            }
        }

    }
    // return true iff the game is over, i.e. no legal moves are possible
    public boolean gameOver()
    {
        Square[][] boardcopy = new Square[noOfSquares][noOfSquares];
        int scorecopy = score;
        copymap(board,boardcopy);
        up();
        down();
        left();
        right();
        
        if( (scorecopy - score) == 4 ){
        	copymap(boardcopy,board);
        	score = scorecopy;
            return true;
        } else {
        	copymap(boardcopy,board);
        	score = scorecopy;
            return false;
        }
        
    }
}
