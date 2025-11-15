/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connect4gui;

import javax.swing.*;
import java.text.*;
import java.util.*;
import java.io.*;
import java.nio.*;
/**
 *
 * @author Παναγιώτης
 */
public class GameRecords {
    JList List;
    public static String Difficulty = null;
    public boolean PlayerWon;
    SimpleDateFormat Formatter;
    Date date;
    File file;
    
    public GameRecords() {
        Formatter = new SimpleDateFormat("yyyy.MM.dd - HH:mm");
        date = new Date();
        //System.out.println(formatter.format(date));
        //File = new File(".");
    }
}
