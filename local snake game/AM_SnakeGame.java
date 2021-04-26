import java.awt.EventQueue;
import javax.swing.JFrame;

public class AM_SnakeGame extends JFrame {

    public AM_SnakeGame(String gameTille) {        
        setTitle(gameTille);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        String [] args = {};
        AM_LoginForm.main(args);
    }
    
    public static void main(String[] args) {        
        EventQueue.invokeLater(() -> {
            //create the game with a game title as follow: The Snake Game (C) Your_Name
            JFrame sgame = new AM_SnakeGame("The Snake Game (C) Abdul_majid");
            sgame.setVisible(true);
        });
    }
}
