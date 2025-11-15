/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connect4gui;

import static connect4gui.Connect4GUI_Frame.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Παναγιώτης
 */
public class ButtonTimers implements ActionListener {
    public Timer[][] timer = null;
    
    public ButtonTimers() {
        timer = new Timer[ROWS][COLS];
    }

    @Override
    public void actionPerformed(ActionEvent e) {}
}
