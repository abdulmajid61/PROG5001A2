package snakegame;
/**
 * Class Prey represents any prey object.
 *
 * @author 
 * @version
 */
public class AM_Prey {
    
    int size;
    int x_cordinate;
    int y_cordinate;
    /**
     * Constructor for objects of class Prey
     */
    
    public AM_Prey()
    {
    }
    
    public int getSize(){
        //getter function for size
        return this.size;
    }
    
    public void setSize(int s){
        //getter function for size
        this.size = s;
    }
    
    public int getXCordinate(){
        //getter function for x-cordinate
        return this.x_cordinate;
    }
    
    public void setXCordinate(int x){
        //getter function for x-cordinate
        this.x_cordinate = x;
    }
    
    public int getYCordinate(){
        //getter function for y-cordinate
        return this.y_cordinate;
    }
    
    public void setYCordinate(int y){
        //getter function for y-cordinate
        this.y_cordinate = y;
    }
    
}
