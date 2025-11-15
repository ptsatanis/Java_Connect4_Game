/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package connect4gui;

/**
 *
 * @author Παναγιώτης
 */
public class Connect4GUI {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        Connect4GUI_Frame gui = new Connect4GUI_Frame(1000, 1500);
        gui.pack();
        gui.setVisible(true);
    }
    
}
