/**
 * Write a description of class Snake here.
 *
 * @author Abdul Majid
 * @version 24-04-2021
 */
public class AM_Snake
{
    int length;
    String color;
    int x_cordinate;
    int y_cordinate;
    /**
     * Constructor for objects of class Snake
     */
    public AM_Snake() {
        // initialise instance variables        
    }

    public int getLength(){
        //getter function for length
        return this.length;
    }
    
    public void setLength(int len){
        this.length = len;
    }
    
    public String getColor(){
        //getter function for length
        return this.color;
    }
    
    public void setColor(String col){
        this.color = col;
    }
    
    public void getHeadLocation(){
        //get the current location of head of snake
    }
    
    public void getTailLocation(){
        //get the current location of tail of snake
    }
    
    public void moveForward(){
        //set the current location of head of snake
    }
    
    public void moveRight(){
        //set the current location of head of snake
    }
    
    public void moveLeft(){
        //set the current location of head of snake
    }
}
