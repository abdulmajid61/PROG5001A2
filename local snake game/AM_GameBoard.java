package snakegame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

public class AM_GameBoard extends JPanel {    
    int xcells = 60;    
    int ycells = 60;
    int szcell = 20;
    
    private final int SCREEN_WIDTH = 800;
    private final int SCREEN_HEIGHT = 420;
    
    private Image snakepart;
    private Image apple;
    private Image snakehead;
    
    private JButton topPlayerScore;
    private JButton currentPlayerScore;
    private JButton yourScore;
    private JButton quit;

       
    public AM_GameBoard() {
    	
        setPreferredSize(new Dimension(xcells * szcell, ycells * szcell));        
        setFocusable(true);
        
        initGameBoard();
    }
    
    private void initGameBoard(){
    	
    	setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setFocusable(true);

        loadIcons();
    }
    
    private void loadIcons() {

        ImageIcon iisnakepart = new ImageIcon("src/resources/dot.png");
        snakepart = iisnakepart.getImage();

        ImageIcon iiapple = new ImageIcon("src/resources/apple.png");
        apple = iiapple.getImage();

        ImageIcon iisnakehead = new ImageIcon("src/resources/head.png");
        snakehead = iisnakehead.getImage();
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
        
        removeAll();
        
        g.setColor(Color.black);
        g.fillRect(10,10,480,400);

        
        this.topPlayerScore = new JButton("TO PLAYER'S SCORE");
    	this.topPlayerScore.setBounds(500, 10, 290, 50);
    	this.topPlayerScore.setLayout(null);
        
        this.add(this.topPlayerScore);
        
        this.currentPlayerScore = new JButton("CURRENT PLAYER SCORE");
    	this.currentPlayerScore.setBounds(500, 60, 290, 50);
    	this.currentPlayerScore.setLayout(null);
        
        this.add(this.currentPlayerScore);
        
        
        this.yourScore = new JButton("<html>PROG5001: 2021 <br /> your score</html>");
    	this.yourScore.setBounds(500, 300, 290, 50);
    	this.yourScore.setLayout(null);
        
        this.add(this.yourScore);
        
        
        this.quit = new JButton("QUIT");
    	this.quit.setBounds(500, 360, 290, 50);
    	this.quit.setLayout(null);
        
        this.add(this.quit);
        
        
        doDrawing(g);
    }
    
    private void doDrawing(Graphics g) {
    	
    }
    
}
