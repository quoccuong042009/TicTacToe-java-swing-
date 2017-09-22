
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Tictactoe extends JFrame implements ActionListener {

// Make private the components so they are protected
    private JButton cells[][]; // these will hold the pictures to display
    private JPanel board;		// hold the array Buttons
    //private int gameStatus[][] = {{2, 2, 2}, {2, 2, 2}, {2, 2, 2}}; // the 2D array, position of Xs will change into 1, position of Os will change into 2.
        private boolean[][] gameStatusX = new boolean[3][3];
        private boolean[][] gameStatusO = new boolean[3][3];

    private static final int ROW = 3; // row of the 2D array will be final
    private static final int COLUMN = 3; // column of the 2D array will be final
	boolean isXFirst;

    private ImageIcon xImage = new ImageIcon("X.jpg"); // images for the X, O and Default
    private ImageIcon oImage = new ImageIcon("O.jpg");
    private ImageIcon blankImage = new ImageIcon("blank.jpg");
   
   private int count = 0;	// since there are 9 boxes, the count will increase 1 every time a new box is clicked.
    private boolean xWin = false; // signal the result when X wins
    private boolean oWin = false; // signal the result when O wins
    private int xScore = 0; // keep track of score for X
    private int oScore = 0; // keep track of score for O


    public Tictactoe() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar mnuBar = new JMenuBar();// create an instance of the menu
        setJMenuBar(mnuBar);

		
		
        // construct and populate the File menu
        JMenu mnuFile = new JMenu("File", true); // Menu File
        mnuBar.add(mnuFile);
        mnuFile.setMnemonic(KeyEvent.VK_F);
        JMenuItem mnuFileNew = new JMenuItem("New Game"); // Menu Item New Game
        mnuFile.add(mnuFileNew);
        mnuFileNew.setMnemonic(KeyEvent.VK_N);
        JMenuItem mnuFileScore = new JMenuItem("Show Score"); // Menu Item Show Score
        mnuFile.add(mnuFileScore);
        mnuFileScore.setMnemonic(KeyEvent.VK_S);
        JMenuItem mnuFileExit = new JMenuItem("Exit"); // Menu Item Exit
        mnuFile.add(mnuFileExit);
        mnuFileExit.setMnemonic(KeyEvent.VK_X);
        JMenu mnuAbout = new JMenu("About", true);// Menu About
        mnuBar.add(mnuAbout);
        JMenuItem mnuAboutTictactoe = new JMenuItem("About Tic Tac Toe"); // Menu Item About Tictactoe
        mnuAbout.add(mnuAboutTictactoe);

        // add the ActionListener to each menu item
        mnuFileNew.addActionListener(this);
        mnuFileScore.addActionListener(this);
        mnuFileExit.addActionListener(this);
        mnuAboutTictactoe.addActionListener(this);

        // assign an ActionCommand to each menu item
        mnuFileNew.setActionCommand("New");
        mnuFileScore.setActionCommand("Score");
        mnuFileExit.setActionCommand("Exit");
        mnuAboutTictactoe.setActionCommand("About");

        // construct components and intialize beginning values
        board = new JPanel();
        board.setBackground(Color.DARK_GRAY);
        cells = new JButton[ROW][COLUMN];

        // set Frame and board layout to grid layout
        setLayout(new BorderLayout());
        board.setLayout(new GridLayout(3, 3, 10, 10));

		// double for loop will step thru the 2D array and set pictures, add actionListioner
        for (int row = 0; row < ROW; row++) {
            for (int column = 0; column < COLUMN; column++) {
                cells[row][column] = new JButton(blankImage);
                cells[row][column].setBorderPainted(false);// the border will not be painted !!!!
                board.add(cells[row][column]);
                cells[row][column].addActionListener(this);
            }
        }

        add(board, BorderLayout.CENTER); //set the board game at the center

		
        addWindowListener(
                new WindowAdapter() {

                    public void widowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                });
				
		// ask if X or O goes first
		int ask= JOptionPane.showConfirmDialog(null, "Which one you want to go first? Yes = X, No = O ", "Confirmation",
        JOptionPane.YES_NO_OPTION);
        if (ask == JOptionPane.YES_OPTION) 
            isXFirst = true;
		else
			isXFirst = false;
    } // end of constructor method


    public void actionPerformed(ActionEvent e) {
        // Start the section of commands for Menu
        String arg = e.getActionCommand();

        if (arg == "New"){ // reset the board when user select New Game
            count = 0;
            xWin = false;
            oWin = false;
            for (int row = 0; row < ROW; row++) {
                for (int column = 0; column < COLUMN; column++) {
                    cells[row][column].setEnabled(true);
                    cells[row][column].setIcon(blankImage);
                    gameStatusO[row][column]=false;
                    gameStatusX[row][column]=false;
                }
            }
			
			int ask= JOptionPane.showConfirmDialog(null, "Which one you want to go first? Yes = X, No = O ", "Confirmation",
                    JOptionPane.YES_NO_OPTION);
            if (ask == JOptionPane.YES_OPTION) 
                isXFirst = true;
			else
				isXFirst = false;
        }
        if (arg == "Score"){ // Display Score
            JOptionPane.showMessageDialog(null, "X Won: " + xScore + "\nO Won: " + oScore , "Result", JOptionPane.INFORMATION_MESSAGE);
        }
        if (arg == "Exit") { // Quit the program
            int answer = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Confirmation",
                    JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
        if (arg == "About") {//Display About
             JOptionPane.showMessageDialog(null, "This is Tictactoe 1.0 ", "About", JOptionPane.INFORMATION_MESSAGE);
        }

        // end menu commands section
        // start game section
        // the for loop will go thru the 2D array and perform setIcon accordingly,
        // for loop condition: go thru every element of the 2D array
        for (int row = 0; row < ROW; row++) {
            for (int column = 0; column < COLUMN && xWin == oWin && oWin == false; column++) {
                // as long as there is no winner, the game will continue and gameStatus's elements will be changed to
                // number 1 whenever the corresponded elements on the board are clicked.
                if (cells[row][column] == e.getSource() && ( gameStatusX[row][column] == false &&  gameStatusX[row][column] == false) && count <= 8) {
                    if (count == 0 || count == 2 || count == 4 || count == 6 || count == 8) {
                        //count variable is increasing everytime a button is clicked. Odd number for X
						if (isXFirst){
							cells[row][column].setIcon(xImage);
                                                        gameStatusX[row][column] = true;
                                                }
                                                else{
							cells[row][column].setIcon(oImage);
                                                        gameStatusO[row][column] = true;
                                                }
                        count++;
                        break;
                    } else if (count == 1 || count == 3 || count == 5 || count == 7 && count <= 8) {
                        //count variable is increasing everytime a button is clicked. Even number for O
                                                if (isXFirst){
							cells[row][column].setIcon(oImage);
                                                        gameStatusO[row][column] = true;
                                                }
                                                else{
                                                        cells[row][column].setIcon(xImage);
                                                        gameStatusX[row][column] = true;
							
                                                }
                        count++;
                        break;
                    }
                }
            }
        }


 if (count <= 9) // There are 9 boxes on the board.
        {
            // the for loop use to check every column and detect winning
            for (int i = 0; i < 3; i++) {
                if (gameStatusO[0][i] == true && gameStatusO[1][i] == true && gameStatusO[2][i] == true) {
                    changePicture(false,0,i,1,i,2,i);
                    oWin();
                }
                if (gameStatusX[0][i] == true && gameStatusX[1][i] == true && gameStatusX[2][i] == true) {
                    changePicture(true,0,i,1,i,2,i);
                    xWin();
                }
            }
            // the for loop use to check every row
            for (int i = 0; i < 3; i++) {
                if (gameStatusO[i][0] == true && gameStatusO[i][1] == true && gameStatusO[i][2] == true) {
                    changePicture(false,i,0,i,1,i,2);
                    oWin();
                }
                if (gameStatusX[i][0] == true && gameStatusX[i][1] == true && gameStatusX[i][2] == true) {
                    changePicture(true,i,0,i,1,i,2);
                    xWin();
                }
            }
            // check the first diagonal
            if (gameStatusO[0][0] == true && gameStatusO[1][1] == true && gameStatusO[2][2] == true) {
                    changePicture(false,0,0,1,1,2,2);
                    oWin();
                 }
                    
             if (gameStatusX[0][0] == true && gameStatusX[1][1] == true && gameStatusX[2][2] == true) {                      
                    changePicture(true,0,0,1,1,2,2);
                    xWin();
            
            }
           
            // check the second diagonal
            if (gameStatusO[2][0] == true && gameStatusO[1][1] == true && gameStatusO[0][2]== true) {
                    changePicture(false,2,0,1,1,0,2);
                    oWin();
            }
            if (gameStatusX[2][0] == true && gameStatusX[1][1] == true && gameStatusX[0][2]== true) {
                    changePicture(true,2,0,1,1,0,2);
                    xWin();
            }
        }
        if (count == 9 && xWin == false && oWin == false) //check Drawing
        {
            JOptionPane.showMessageDialog(null, "GAME TIES !!!", "Result", JOptionPane.PLAIN_MESSAGE);
            count = 10; // avoid re-display the result
        }
    } // end of actionPerformed


    public void xWin() // show message and add point for X
    {
        JOptionPane.showMessageDialog(null, "X Wins!!!\n You've earned 1 point", "Congratulations!!!", JOptionPane.PLAIN_MESSAGE);
        xWin = true;
        oWin = false;
        xScore++;
        count = 10; // avoid re-display the result
    }

    public void oWin() // show message and add point for O
    {
        JOptionPane.showMessageDialog(null, "O Wins!!!\n You've earned 1 point", "Congratulations!!!", JOptionPane.PLAIN_MESSAGE);
        xWin = false;
        oWin = true;
        oScore++;
        count = 10;// avoid re-display the result
    }
    public static void main(String args[]) {
        // set frame properties
        JFrame.setDefaultLookAndFeelDecorated(true);
        Tictactoe f = new Tictactoe();
        f.setTitle("The Game TicTacToe");
        f.setBounds(200, 200, 300, 350);
        f.setVisible(true);
        f.setResizable(false);

    }   // end of main

	// this method will light up the winning row or column or diagonal
    private void changePicture(boolean isXWin, int startRow,int startCol,int midRow, int midCol ,int endRow,int endCol){
      //  public void changePicture
        ImageIcon xWin = new ImageIcon("XWin.jpg");
        ImageIcon oWin = new ImageIcon("OWin.jpg");
        for (int row = 0; row < ROW; row++) {
            for (int column = 0; column < COLUMN; column++) {
                cells[row][column].setEnabled(false);
                    gameStatusO[row][column]=false;
                    gameStatusX[row][column]=false;
            }
        }
        if (isXWin){
            cells[startRow][startCol].setEnabled(true);
            cells[midRow][midCol].setEnabled(true);
            cells[endRow][endCol].setEnabled(true);
            cells[startRow][startCol].setIcon(xWin);
            cells[midRow][midCol].setIcon(xWin);
            cells[endRow][endCol].setIcon(xWin);
        }
        else {
            cells[startRow][startCol].setEnabled(true);
            cells[midRow][midCol].setEnabled(true);
            cells[endRow][endCol].setEnabled(true);
            cells[startRow][startCol].setIcon(oWin);
            cells[midRow][midCol].setIcon(oWin);
            cells[endRow][endCol].setIcon(oWin);
        }
    }
} // end of class