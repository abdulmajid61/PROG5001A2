import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Random;
import java.util.Vector;
import java.util.stream.Collectors;

public class AM_GameBoard extends JPanel implements ActionListener {

    public static final String GAME_DESCRIPTION = "<html> PROG5001: 2021 <br /><br /> Movements: Arrow Keys (→ ↑ ← ↓) <br /> Pause: SPACE </html>";
    public static final String SCORE = "Score: %d (%s)";
    public static final int SCORE_PER_APPLE = 5;

    private String queuedDirection = null;
    private Rectangle board;

    private Random rand;

    private final int SCREEN_WIDTH = 800;
    private final int SCREEN_HEIGHT = 420;

    private Image snakepart;
    private Image apple;
    private Image snakehead;

    private JLabel gameDescription;
    private JLabel score;
    private JTable highScores;
    private JLabel centerText;
    private JButton playAgain;

    private AM_Prey prey;
    private AM_Snake snake;

    private Timer timer;
    private int tick = 0;
    private int maxTicks = Integer.MAX_VALUE - 10;

    private String currentPlayer;
    private Long currentScore = 0L;
    private boolean gameOver;
    private boolean gamePaused;
    private boolean highScoresSynced;

    int ticksToMoveSnake;

    public AM_GameBoard(String player) {
        rand = new Random();
        this.currentPlayer = player;
        initGameBoard();
    }

    private void setControls() {
        getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "pause");
        getInputMap().put(KeyStroke.getKeyStroke("UP"), "up");
        getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "down");
        getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "right");
        getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "left");
        getActionMap().put("pause", new AbstractAction()  {
            @Override
            public void actionPerformed(ActionEvent e) {
                togglePause();
            }
        });
        getActionMap().put("up", new AbstractAction()  {
            @Override
            public void actionPerformed(ActionEvent e) {
                queueDirection(AM_Snake.UP);
            }
        });
        getActionMap().put("down", new AbstractAction()  {
            @Override
            public void actionPerformed(ActionEvent e) {
                queueDirection(AM_Snake.DOWN);
            }
        });
        getActionMap().put("left", new AbstractAction()  {
            @Override
            public void actionPerformed(ActionEvent e) {
                queueDirection(AM_Snake.LEFT);
            }
        });
        getActionMap().put("right", new AbstractAction()  {
            @Override
            public void actionPerformed(ActionEvent e) {
                queueDirection(AM_Snake.RIGHT);
            }
        });
    }

    private void initGameBoard(){

        setControls();

        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setFocusable(true);
        loadAssets();
        setLayout(null);

        setGame();

        timer = new Timer(50, this);
        timer.start();
        EventQueue.invokeLater(this::grabFocus);
        EventQueue.invokeLater(this::addUI);
    }

    private void setGame() {
        this.gameOver = false;
        this.gamePaused = true;
        this.currentScore = 0L;
        this.ticksToMoveSnake = 10;
        board = new Rectangle(10, 10, 480, 400);
        prey = new AM_Prey();
        randomizePrey();
        snake = new AM_Snake((int) board.getCenterX(), (int) board.getCenterY() + 50);
    }

    private void loadAssets() {

        ImageIcon iisnakepart = new ImageIcon("resources/dot.png");
        snakepart = iisnakepart.getImage();

        ImageIcon iiapple = new ImageIcon("resources/apple.png");
        apple = iiapple.getImage();

        ImageIcon iisnakehead = new ImageIcon("resources/head.png");
        snakehead = iisnakehead.getImage();
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawBoard((Graphics2D) g);

        g.setColor(Color.white);
        doDrawing(g);
    }

    public void addUI() {
        this.gameDescription = new JLabel(GAME_DESCRIPTION);
        this.gameDescription.setBounds(500, 250, 290, 100);
        this.add(this.gameDescription);

        this.score = new JLabel(String.format(SCORE, currentScore, currentPlayer));
        this.score.setBounds(500, 5, 290, 50);
        this.add(this.score);

        TableModel scoreData = loadHighScoreData();
        this.highScores = new JTable(scoreData);
        JScrollPane highScoreView = new JScrollPane(new JTable(scoreData));
        highScoreView.setBounds(500, 50, 290, 200);
        this.add(highScoreView);
        this.highScoresSynced = true;


        JButton quit = new JButton("QUIT");
        quit.setBounds(610, 360, 100, 50);
        quit.addActionListener((e) -> {
            System.exit(0);
        });
        this.add(quit);

        this.playAgain = new JButton("PLAY AGAIN");
        this.playAgain.setEnabled(false);
        this.playAgain.setBounds(500, 360, 100, 50);
        this.playAgain.addActionListener((e) -> {
            setGame();
            resetUI();
        });
        this.add(this.playAgain);

        this.centerText = new JLabel("PAUSED");
        centerText.setForeground(Color.white);
        this.centerText.setBounds((int) board.getCenterX() - 50, (int) board.getCenterY() - 50, 100, 25);
        this.add(this.centerText);
    }

    private void resetUI() {
        this.playAgain.setEnabled(false);
        this.centerText.setText("PAUSED");
        this.score.setForeground(Color.black);
        score.setText(String.format(SCORE, currentScore, currentPlayer));
        grabFocus();
    }


    private void drawBoard(Graphics2D g) {
        g.setColor(Color.black);
        g.fillRect(board.x, board.y, board.width, board.height);
        g.setColor(Color.red);
        Stroke oldStroke = g.getStroke();
        g.setStroke(new BasicStroke(3));
        g.drawRect(board.x, board.y, board.width, board.height);
        g.setStroke(oldStroke);
        g.setColor(Color.black);
    }

    private void doDrawing(Graphics g) {
        drawPrey(g);
        drawSnake(g);
    }

    private boolean drawPrey(Graphics g) {
        return g.drawImage(apple, prey.x_cordinate, prey.y_cordinate, prey.size, prey.size, this);
    }

    private void drawSnake(Graphics g) {
        if (gameIsRunning() && shouldUpdateFrame()) {
            moveSnake();
            detectCollision(g);
        }

        //draw head
        g.drawImage(snakehead, snake.x_cordinate, snake.y_cordinate, snake.size, snake.size, this);

        //draw body
        for (int i = 0; i < snake.length; i++) {
            g.drawImage(snakepart, snake.body.get(i).getXCoordinate(), snake.body.get(i).getYCoordinate(), snake.size, snake.size, this);
        }
    }

    private boolean shouldUpdateFrame() {
        return tick % ticksToMoveSnake == 0;
    }

    private TableModel loadHighScoreData() {
        Vector<String> columns = highScoreColumns();
        return new DefaultTableModel(deserializeHighScores(), columns) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    private Vector<String> highScoreColumns() {
        Vector<String>  columns = new Vector<>();
        columns.add("Name");
        columns.add("Score");
        return columns;
    }

    private void saveTopTenScores() {
        if (currentScore <= 0) {
            return;
        }

        Vector<String> userScore = userScoreVector();

        Vector<Vector> data = ((DefaultTableModel) this.highScores.getModel()).getDataVector();
        data.add(userScore);

        int scoreIndex = 1;

        //Java Stream - https://www.tutorialspoint.com/java8/java8_streams.htm
        Vector<Vector> topTenScores = data.stream()
            .distinct() // remove duplicates
            .sorted((row1, row2) -> Integer.parseInt(row2.get(scoreIndex).toString()) - Integer.parseInt(row1.get(scoreIndex).toString())) //sorting by score in descending order
            .limit(10)
            .collect(Collectors.toCollection(Vector::new));

        ((DefaultTableModel) this.highScores.getModel()).setDataVector(topTenScores, highScoreColumns());

        serializeHighScores(topTenScores);
    }

    private Vector<String> userScoreVector() {
        Vector<String> userScore = new Vector<>();
        userScore.add(currentPlayer);
        userScore.add(String.valueOf(currentScore));
       return userScore;
    }


    // Java Serialization - https://www.tutorialspoint.com/java/java_serialization.htm
    private void serializeHighScores(Vector<Vector> data) {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("highScores.ser")));
            out.writeObject(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Vector<Vector> deserializeHighScores() {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new BufferedInputStream(new FileInputStream("highScores.ser")));
            return (Vector<Vector>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            //No saved high scores/ ignore error display empty high scores
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return new Vector<>();
    }

    private boolean gameIsRunning() {
        return !gameOver && !gamePaused;
    }

    private void moveSnake() {
        //move snake
        snake.move(queuedDirection);

        //reset input direction
        queuedDirection = null;
    }

    private void detectCollision(Graphics g) {
        if (!board.contains(snake.x_cordinate, snake.y_cordinate)) {
            gameOver(g);
        } else if (snake.didSnakeEatPrey(prey)) {
            //reset prey position
            randomizePrey();

            //grow snake tail
            snake.grow();

            increaseScore(SCORE_PER_APPLE);

            int X = 2;
            //increase speed after every X prey
            if (currentScore % (SCORE_PER_APPLE * X) == 0) {
                ticksToMoveSnake = Math.max(ticksToMoveSnake - 1, 1);
            }
        } else if (snake.didSnakeEatItself()) {
            //game over if snake head touches its body
            gameOver(g);
        }
    }

    private void gameOver(Graphics g) {
        gameOver = true;
        score.setForeground(Color.red);
        centerText.setText("GameOver");
        saveTopTenScores();
        this.playAgain.setEnabled(true);
    }

    private void increaseScore(int scoreToIncrease) {
        currentScore += scoreToIncrease;
        score.setText(String.format(SCORE, currentScore, currentPlayer));
    }

    private void randomizePrey() {
        int randomX = randomNumber(board.x+10, board.width-20);
        int randomY = randomNumber(board.y+10, board.height-20);
        prey.setXCordinate(randomX);
        prey.setYCordinate(randomY);
    }

    private int randomNumber(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }

    //events
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer) {
            tick();
            repaint();
        }
    }

    private void tick() {
        tick++;
        if (tick > maxTicks) {
            tick = 0;
        }
    }

    private void togglePause() {
        gamePaused = !gamePaused;

        if (gamePaused) {
            centerText.setText("PAUSED");
        } else {
            centerText.setText("");
        }

    }

    private void queueDirection(String dir) {
        if (queuedDirection == null) {
            queuedDirection = (dir);
        }
    }
}
