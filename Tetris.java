import java.awt.Point;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
public class Tetris{
    
 // Variaveis Publicas
    public JFrame jframe;
    public Board board;
    public Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    public static Tetris tetris;
    public static final int WIDTH = 307, HEIGHT = 630;
  
    public Tetris(){
        
        //Tela
        jframe = new JFrame("Tetris");
        jframe.setVisible(true);
        jframe.setSize(WIDTH,HEIGHT);
        jframe.setLocation(dim.width/2 - jframe.getWidth()/2, dim.height/2 - jframe.getHeight()/2);
        jframe.add(board = new Board());
        jframe.addKeyListener(board);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
    }
         
   public void actionPerfomed(ActionEvent e){
         board.repaint();
   }
    
   public static void main(String [] args){
       tetris = new Tetris();
}
    
}


