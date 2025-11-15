/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connect4gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;


/**
 *
 * @author Παναγιώτης
 */
public class Connect4GUI_Frame extends JFrame {
    public static final int ROWS = 6;
    public static final int COLS = 7;
    public int Depth = 1;
    public boolean PlayerTurn = true;
    JButton[][] board;
    JButton History, Help;
    JPanel Grid, MainMenu;
    JMenu Difficulties;
    //JMenu FirstTurn;
    JButton FirstTurn;
    JPopupMenu Popup;
    JMenuItem Easy, Intermediate, Expert;
    JRadioButtonMenuItem AI, You;
    JMenuBar Bar;
    public boolean Win, Endgame = false;
    public int[][] BackEndBoard = new int[ROWS][COLS];
    
    public Connect4GUI_Frame() {}
    
    public Connect4GUI_Frame(int width, int height) {
        super("Connect4!");
        this.setSize(width, height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Grid = new JPanel( new GridLayout(ROWS, COLS, ROWS, ROWS) );
        MainMenu = new JPanel( new FlowLayout(FlowLayout.LEFT) );
        
        this.Difficulties = new JMenu("New Game");
        
        this.Easy = new JMenuItem("Trivial");
        this.Intermediate = new JMenuItem("Medium");
        this.Expert = new JMenuItem("Hard");
        
        this.Difficulties.add(this.Easy);
        this.Difficulties.add(this.Intermediate);
        this.Difficulties.add(this.Expert);
        
        
        this.Difficulties.setVisible(true);
        this.Difficulties.setLayout( new FlowLayout() );
        this.DifficultiesEvent();
        
        this.Bar = new JMenuBar();
       
        this.Bar.add(this.Difficulties,BorderLayout.NORTH);
        this.setJMenuBar(this.Bar);
        
        board = new JButton[ROWS][COLS];
        for(int row = 0; row < ROWS; row++) {
            for(int col = 0; col < COLS; col++) {
                board[row][col] = new JButton();
                board[row][col].setPreferredSize(new Dimension(20,40));
                board[row][col].setMargin(new Insets(0,0,0,0));
                board[row][col].setBackground(Color.LIGHT_GRAY);
                board[row][col].setVisible(true);
                Grid.add(board[row][col]);
                //this.add(board[row][col]);
            }
        }
        this.boardListener(PlayerTurn);
        
        //this.setHeight(height);
        //this.setWidth(width);
        
        MainMenu.add(Bar);
        MainMenu.setVisible(true);
        
        Grid.setVisible(true);
        
        /*
        //Edw to first turn einai JMenu
        FirstTurn = new JMenu("1st Player");
        Popup = new JPopupMenu("1stPlayer");
        AI = new JRadioButtonMenuItem("AI", true);
        You = new JRadioButtonMenuItem("You", false);
        this.FirstPlayerEvent();
        FirstTurn.setVisible(true);
        
        FirstTurn.add(AI);
        FirstTurn.add(You);
        */
        FirstTurn = new JButton("1st Player");
        Popup = new JPopupMenu("1stPlayer");
        AI = new JRadioButtonMenuItem("AI", true);
        You = new JRadioButtonMenuItem("You", false);
        Popup.add(AI);
        Popup.add(You);
        //FirstTurn.add(Popup);
        FirstTurn.setBackground(Color.LIGHT_GRAY);
        FirstTurn.setVisible(true);
        this.FirstPlayerEvent();
        
        History = new JButton("History");
        History.setBackground(Color.LIGHT_GRAY);
        
        Help = new JButton("Help");
        Help.setBackground(Color.LIGHT_GRAY);
        this.HelpEvent();
        
        MainMenu.add(FirstTurn);
        MainMenu.add(History);
        MainMenu.add(Help);
        this.add(Grid, BorderLayout.SOUTH);
        this.add(MainMenu, BorderLayout.NORTH);
        
        /*
        this.setLayout( new BorderLayout() );
        this.setPreferredSize(new Dimension( width, height ));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        */
        this.pack();
        this.setVisible(true);
    }
    
    public void setDepth(int depth) {
        this.Depth = depth;
    }
    
    public void DifficultiesEvent() {
        this.Easy.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GameRecords.Difficulty = "Trivial";
                setDepth(1);
            }
        } );
        
        this.Intermediate.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GameRecords.Difficulty = "Medium";
                setDepth(2);
            }
        } );
        
        this.Expert.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GameRecords.Difficulty = "Hard";
                setDepth(7);
            }
        } );
    }
    
    public void whoGoesFirst() {
        this.AI.addMouseListener( new MouseListener() {
            @Override
             public void mouseClicked(MouseEvent e) {
                AI.setSelected(true);
                You.setSelected(false);
                Popup.setVisible(false);
            }
                    
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {
                AI.setSelected(true);
                You.setSelected(false);
                Popup.setVisible(false);
            }
            @Override
            public void mousePressed(MouseEvent e) {
                AI.setSelected(true);
                You.setSelected(false);
                Popup.setVisible(false);
            }
            @Override
            public void mouseExited(MouseEvent e) {}
        } );
        
        this.You.addMouseListener( new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AI.setSelected(false);
                You.setSelected(true);
                Popup.setVisible(false);
            }
                    
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {
                AI.setSelected(false);
                You.setSelected(true);
                Popup.setVisible(false);
            }
            @Override
            public void mousePressed(MouseEvent e) {
                AI.setSelected(false);
                You.setSelected(true);
                Popup.setVisible(false);
            }
            @Override
            public void mouseExited(MouseEvent e) {}
        } );
    }
    
    public void FirstPlayerEvent() {
        this.FirstTurn.addMouseListener( new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Popup.setVisible(true);
                showPopup(e);
                whoGoesFirst();
            }
                    
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {
                Popup.setVisible(true);
                showPopup(e);
                whoGoesFirst();
            }
            @Override
            public void mousePressed(MouseEvent e) {
                Popup.setVisible(true);
                showPopup(e);
                whoGoesFirst();
            }
            @Override
            public void mouseExited(MouseEvent e) {}
        } );
    }
    
    public void showPopup(MouseEvent e) {
        if( e.isPopupTrigger() && FirstTurn.isEnabled() ) {
            Popup.show(e.getComponent(), e.getX(), e.getY());
        }
    }
    
    public void HelpEvent() {
        this.Help.addMouseListener( new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        History.setEnabled(!History.isEnabled());
                        FirstTurn.setEnabled(!FirstTurn.isEnabled());
                        Difficulties.setEnabled(!Difficulties.isEnabled());
                    }
                    
                    @Override
                    public void mouseEntered(MouseEvent e) {}
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        History.setEnabled(!History.isEnabled());
                        FirstTurn.setEnabled(!FirstTurn.isEnabled());
                        Difficulties.setEnabled(!Difficulties.isEnabled());
                    }
                    @Override
                    public void mousePressed(MouseEvent e) {
                        History.setEnabled(!History.isEnabled());
                        FirstTurn.setEnabled(!FirstTurn.isEnabled());
                        Difficulties.setEnabled(!Difficulties.isEnabled());
                    }
                    @Override
                    public void mouseExited(MouseEvent e) {}
        } );
        Popup.setVisible(false);
    }
    
    public void HistoryHandler() {}
    
    public void boardListener(boolean PlayerTurn) {
        for(int row = 0; row < ROWS; row++) {
            for(int col = 0; col < COLS; col++) {
                final int col2 = col;
                final int row2 = row;
                board[row][col].addMouseListener( new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if( e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1 ) {
                            buttonListener4(col2);
                            
                            AIMove();
                        }
                    }
                    @Override
                    public void mouseEntered(MouseEvent e) {}
                    @Override
                    public void mouseReleased(MouseEvent e) {}
                    @Override
                    public void mousePressed(MouseEvent e) {}
                    @Override
                    public void mouseExited(MouseEvent e) {}
                } );
                
                board[row][col].addKeyListener( new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        boolean digitKeyTyped = e.getKeyCode() == KeyEvent.VK_0 || e.getKeyCode() == KeyEvent.VK_1 ||
                                e.getKeyCode() == KeyEvent.VK_2 || e.getKeyCode() == KeyEvent.VK_3 || 
                                e.getKeyCode() == KeyEvent.VK_4 || e.getKeyCode() == KeyEvent.VK_5 || 
                                e.getKeyCode() == KeyEvent.VK_6 || e.getKeyCode() == KeyEvent.VK_NUMPAD0 ||
                                e.getKeyCode() == KeyEvent.VK_NUMPAD1 || e.getKeyCode() == KeyEvent.VK_NUMPAD2 ||
                                e.getKeyCode() == KeyEvent.VK_NUMPAD3 || e.getKeyCode() == KeyEvent.VK_NUMPAD4 ||
                                e.getKeyCode() == KeyEvent.VK_NUMPAD5 || e.getKeyCode() == KeyEvent.VK_NUMPAD6;
                        if(digitKeyTyped) {
                            buttonListener4(col2);
                            AIMove();
                        }
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                        boolean digitKeyTyped = e.getKeyCode() == KeyEvent.VK_0 || e.getKeyCode() == KeyEvent.VK_1 ||
                                e.getKeyCode() == KeyEvent.VK_2 || e.getKeyCode() == KeyEvent.VK_3 || 
                                e.getKeyCode() == KeyEvent.VK_4 || e.getKeyCode() == KeyEvent.VK_5 || 
                                e.getKeyCode() == KeyEvent.VK_6 || e.getKeyCode() == KeyEvent.VK_NUMPAD0 ||
                                e.getKeyCode() == KeyEvent.VK_NUMPAD1 || e.getKeyCode() == KeyEvent.VK_NUMPAD2 ||
                                e.getKeyCode() == KeyEvent.VK_NUMPAD3 || e.getKeyCode() == KeyEvent.VK_NUMPAD4 ||
                                e.getKeyCode() == KeyEvent.VK_NUMPAD5 || e.getKeyCode() == KeyEvent.VK_NUMPAD6;
                        if(digitKeyTyped) {
                            buttonListener4(col2);
                            AIMove();
                        }
                    }
                    
                    @Override
                    public void keyReleased(KeyEvent e) {}
                } );
            }
        }
    }
    
    
    //SWSTO
    ///////////////////////////////////////////////////////////
    public void buttonListener4(int col) {
        int row;
        for(row = 0; row < ROWS && board[row][col].getBackground() == Color.LIGHT_GRAY; row++) {}
        
        if( row > 0 ) {
            row--;
            this.BackEndBoard[row][col] = PlayerTurn ? 1 : 2;
            
            Color TempColor = PlayerTurn ? Color.GREEN : Color.ORANGE;
            Color PermColor = PlayerTurn ? Color.RED : Color.YELLOW;
            board[row][col].setBackground(TempColor);
            //PlayerTurn = !PlayerTurn;
            final int frow = row;
            Timer timer = new Timer(1000, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    board[frow][col].setBackground(PermColor);
                }
            });
            timer.setInitialDelay(1000);
            timer.setRepeats(false);
            timer.start();
        }
        
    }
    
    public void AIMove() {
        
        final JFrame thisFrame = this;
        
        if( Connect4_prof.checkWin(this.BackEndBoard, PlayerTurn ? 1 : 2) ) {
            Endgame = true;
            JOptionPane.showMessageDialog(thisFrame, PlayerTurn ? "YOU WIN!" : "YOU LOSE!");
            Win = PlayerTurn;
            return;
        }
        
        PlayerTurn = !PlayerTurn;
        
        Timer timer = new Timer(3000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int alpha = Integer.MIN_VALUE;
                int beta = Integer.MAX_VALUE;
                int[] bestMove = Connect4_prof.minimax(BackEndBoard, Depth, PlayerTurn ? 1 : 2, alpha, beta);
                int nextMove = bestMove[0];
                
                buttonListener4(nextMove);
                
                if( Connect4_prof.checkWin(BackEndBoard, PlayerTurn ? 1 : 2) ) {
                    Endgame = true;
                    JOptionPane.showMessageDialog(thisFrame, PlayerTurn ? "YOU WIN!" : "YOU LOSE!");
                    Win = PlayerTurn;
                }
                
                PlayerTurn = !PlayerTurn;
            }
        });
        timer.setInitialDelay(3000);
        timer.setRepeats(false);
        timer.start();
    }
    //////////////////////////////////////////////////////////////
    
}
