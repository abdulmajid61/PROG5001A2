import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
 
/**
 * This program demonstrates how to use JFrame and LayoutManager.
 * @author Vinh Bui
 */

public class AM_LoginForm extends JFrame implements ActionListener {
    private JLabel labelUsername;
    private JLabel labelPassword;   
    private JTextField textUsername;
    private JPasswordField fieldPassword;
    private JButton buttonLogin;
    private AM_PlayerList playerList;
    
    private JPanel panelLogin;
 
    public AM_LoginForm() {
        super("Login Form");
        labelUsername = new JLabel("Enter username: ");
        labelPassword = new JLabel("Enter password: ");   
        textUsername = new JTextField(20);
        fieldPassword = new JPasswordField(20);
        buttonLogin = new JButton("Login");
        
        // create a new panel with GridBagLayout manager
        panelLogin = new JPanel(new GridBagLayout());
        
        //use contrains to control the gridbaglayout
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
         
        // add components to the panel
        constraints.gridx = 0;
        constraints.gridy = 0;     
        panelLogin.add(labelUsername, constraints);
 
        constraints.gridx = 1;
        panelLogin.add(textUsername, constraints);
         
        constraints.gridx = 0;
        constraints.gridy = 1;     
        panelLogin.add(labelPassword, constraints);
         
        constraints.gridx = 1;
        panelLogin.add(fieldPassword, constraints);
         
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panelLogin.add(buttonLogin, constraints);
                  
        // add the panel to this frame
        add(panelLogin);
         
        pack();
        
        //make the form apprear in the screen centre
        setLocationRelativeTo(null);
        
        //add ActionListener to the button
        buttonLogin.addActionListener(this);
        
        //instantiate the playerList
        playerList = new AM_PlayerList();
        
        try {
            readPlayerFromFile("players.txt");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "File Not Found");
        }
    }
    
    public void actionPerformed(ActionEvent e) {
        String username = textUsername.getText();
        String password = fieldPassword.getText();
        
        if (playerList.matchPlayer(username, password)) {
            
            remove(panelLogin);

            AM_GameBoard gameBoard = new AM_GameBoard(username);
            add(gameBoard);
            setResizable(false);
            
            pack();
            
            setTitle("My Snake Game (C)");
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
        } else {
            JOptionPane.showMessageDialog(this, "wrong username or password");
        }

    }
    
    private void readPlayerFromFile(String fileName) throws FileNotFoundException{
        File file = new File(fileName);
     
        //Creating Scanner instnace to read File in Java
        Scanner scnr = new Scanner(file);
     
        //Reading each line of file using Scanner class
        while(scnr.hasNextLine()){
            String line = scnr.nextLine();
            Scanner uscanner = new Scanner(line);
            String username = uscanner.next();
            String password = uscanner.next(); 
            playerList.add(username, password);
            //JOptionPane.showMessageDialog(this, username + ": " + password);
        }      
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
//                new AM_LoginForm().setVisible(true);
            }
        });
    }
}