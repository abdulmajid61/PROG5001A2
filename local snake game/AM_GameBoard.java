import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.*;

public class AM_GameBoard extends JPanel implements ActionListener, KeyListener {
    final int UP_CODE = 38;
    final int DOWN_CODE = 40;
    final int LEFT_CODE = 39;
    final int RIGHT_CODE = 37;

    private String queuedDirection = null;

    private int xStart = 10;
    private int yStart = 10;
    private int boardWidth = 480;
    private int boardHeight = 400;

    private Random rand;

    private final int SCREEN_WIDTH = 800;
    private final int SCREEN_HEIGHT = 420;

    private Image snakepart;
    private Image apple;
    private Image snakehead;

    private JButton topPlayerScore;
    private JButton currentPlayerScore;
    private JButton yourScore;
    private JButton quit;

    private AM_Prey prey;
    private AM_Snake snake;

    private Timer timer;
    private int tick = 0;
    private int maxTicks = Integer.MAX_VALUE - 10;

    String currentPlayer;
    Long currentScore = 0L;
    boolean gameOver;

    int ticksToMoveSnake = 10;

    public AM_GameBoard(String player) {
        rand = new Random();
        this.currentPlayer = player;
        this.gameOver = false;
        initGameBoard();
    }

    private void initGameBoard(){
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        addKeyListener(this);
        grabFocus();
        setFocusable(true);

        loadIcons();

        prey = new AM_Prey();
        randomizePrey();

        // generate random co ordinates for snake facing upward
        // subtract 15 as buffer so snake is always show on the screen at the start
        int snakeX = randomNumber(xStart, boardWidth - 15);
        int snakeY = randomNumber(yStart, boardHeight - 15);
        snake = new AM_Snake(snakeX, snakeY);

        timer = new Timer(50, this);
        timer.start();
    }

    private void loadIcons() {

        ImageIcon iisnakepart = new ImageIcon("resources/dot.png");
        snakepart = iisnakepart.getImage();

        ImageIcon iiapple = new ImageIcon("resources/apple.png");
        apple = iiapple.getImage();

        ImageIcon iisnakehead = new ImageIcon("resources/head.png");
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

        g.setColor(Color.black);
        g.fillRect(xStart, yStart, boardWidth, boardHeight);
        removeAll();

        this.topPlayerScore = new JButton("TO PLAYER'S SCORE");
        this.topPlayerScore.setBounds(500, 10, 290, 50);
        this.topPlayerScore.setLayout(null);

        this.add(this.topPlayerScore);

        this.currentPlayerScore = new JButton("CURRENT PLAYER SCORE");
        this.currentPlayerScore.setBounds(500, 60, 290, 50);
        this.currentPlayerScore.setLayout(null);

        this.add(this.currentPlayerScore);


        this.yourScore = new JButton(String.format("<html>PROG5001: 2021 <br /> %s: %d</html>", currentPlayer, currentScore));
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
        drawPrey(g);
        drawSnake(g);

        if (gameOver) {
            yourScore.setForeground(Color.red);
            JLabel gameOverLabel = new JLabel("Game Over");
            gameOverLabel.setBounds(500, 100, 200, 50);
            gameOverLabel.setForeground(Color.red);
            this.add(gameOverLabel);
        }
    }

    private boolean drawPrey(Graphics g) {
        return g.drawImage(apple, prey.x_cordinate, prey.y_cordinate, prey.size, prey.size, this);
    }

    private void drawSnake(Graphics g) {
        if (!gameOver && (tick % ticksToMoveSnake == 0)) {
            //move snake
            snake.move(queuedDirection, xStart, yStart, boardWidth, boardHeight);

            //reset input direction
            queuedDirection = null;
        }

        //check if snake ate apple
        if (snake.didSnakeEatPrey(prey)) {
            //reset prey position
            randomizePrey();

            //grow snake tail
            snake.grow();

            //inccrease score
            currentScore += 5;
            yourScore.setText(String.format("<html>PROG5001: 2021 <br /> %s: %d</html>", currentPlayer, currentScore));

            //increase speed after every 2 prey
            if (currentScore % 10 == 0) {
                ticksToMoveSnake = Math.max(ticksToMoveSnake - 1, 1);
            }
        } else if (snake.didSnakeEatItself()) {
            //game over if snake head touches its body
            gameOver = true;
        }

        //draw head
        g.drawImage(snakehead, snake.x_cordinate, snake.y_cordinate, snake.size, snake.size, this);

        //draw body
        for (int i = 0; i < snake.length; i++) {
            g.drawImage(snakepart, snake.body.get(i).getXCoordinate(), snake.body.get(i).getYCoordinate(), snake.size, snake.size, this);
        }
    }

    private void randomizePrey() {
        int randomX = randomNumber(xStart/10, boardWidth/10);
        int randomY = randomNumber(yStart/10, boardHeight/10);
        prey.setXCordinate(randomX * 10);
        prey.setYCordinate(randomY * 10);
    }

    private int randomNumber(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer) {
            tick++;
            if (tick > maxTicks) {
                tick = 0;
            }
            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case UP_CODE:
                if (queuedDirection == null) {
                    queuedDirection = (AM_Snake.UP);
                }
                break;
            case DOWN_CODE:
                if (queuedDirection == null) {
                    queuedDirection = (AM_Snake.DOWN);
                }
                break;
            case RIGHT_CODE:
                if (queuedDirection == null) {
                    queuedDirection = (AM_Snake.RIGHT);
                }
                break;
            case LEFT_CODE:
                if (queuedDirection == null) {
                    queuedDirection = (AM_Snake.LEFT);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
