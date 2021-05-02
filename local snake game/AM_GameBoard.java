import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

public class AM_GameBoard extends JPanel {    
    int xcells = 30;    
    int ycells = 30;
    int szcell = 10;       
       
    public AM_GameBoard() {
        setPreferredSize(new Dimension(xcells * szcell, ycells * szcell));        
        setBackground(Color.black);
        setFocusable(true);        
    }
    
    public void drawGameBoard(){
        //this method will draw gameboard as canvas and initialize
        //it with proper values
    }
    
    public int getValue(int x,int y){
        //it will get the value at particular cell x and y cordinate
        return 0;
    }
    
    public int setValue(int x,int y,int value){
        //it will set the value at particular cell x and y cordinate
        return 0;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
    
}
