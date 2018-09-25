import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Toolkit;

import java.awt.image.BufferedImage;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

    public class Board extends JPanel implements KeyListener{
     // Variaveis Publicas
        public Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        public static final int FPS = 60,delay = 1000/FPS;
        public int index;
        private int boardWidth = 10;
        private int boardHeight = 20;
     
     // Variaveis Privadas
     // Formaçao do Quadro de Jogo
        private int i = 0;
        private int j = 0;
        private int row;
        private int col;
        
        private final int blockSize = 30;
        private BufferedImage blocks;
     // Formas do Jogo
        private Shape [] shapes = new Shape[7]; 
        private int[][]board = new int[boardHeight][boardWidth];
        private Shape currentShape;
        private Timer timer;
        
     // Definiçao do Quadro do Jogo
    
     public Board(){
        try{
        blocks = ImageIO.read(Board.class.getResource("/tiles.png"));
    }   catch (IOException e){
            e.printStackTrace();
    }   

        timer = new Timer(delay, new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e){
                updateBoard();
                repaint();
            }
        });
        timer.start();
   
        // Definiçao das Formas do Jogo
     
        // I-shape
        shapes[0] = new Shape(blocks.getSubimage(0,0,blockSize,blockSize),this ,new int [][]{{1,1,1,1}},1);
        // Z-shape
        shapes[1] = new Shape(blocks.getSubimage(blockSize,0,blockSize,blockSize),this ,new int [][]{{1,1,0},{0,1,1}},2);
        // S-shape
        shapes[2] = new Shape(blocks.getSubimage(blockSize * 2,0,blockSize,blockSize),this ,new int [][]{{0,1,1},{1,1,0}},3);
        // J-shape
        shapes[3] = new Shape(blocks.getSubimage(blockSize * 3,0,blockSize,blockSize),this ,new int [][]{{1,1,1},{0,0,1}},4);
        // L-shape
        shapes[4] = new Shape(blocks.getSubimage(blockSize * 4,0,blockSize,blockSize),this ,new int [][]{{1,1,1},{1,0,0}},5);
        // T-shape
        shapes[5] = new Shape(blocks.getSubimage(blockSize * 5,0,blockSize,blockSize),this ,new int [][]{{1,1,1},{0,1,0}},6);
        // O-shape
        shapes[6] = new Shape(blocks.getSubimage(blockSize * 6,0,blockSize,blockSize),this ,new int [][]{{1,1},{1,1}},7);
        
        setNextShape();
    }
    
    // Atualizaçao do Painel do jogo
    public void updateBoard(){
        currentShape.update();
    }
      
    // Desenho das linhas de fundo
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        currentShape.render(g);
        
        for (row = 0 ; row < board.length; row++){
                for (col = 0 ; col < board[row].length; col++)
                    if (board[row][col] != 0)
                        g.drawImage(blocks.getSubimage((board[row][col] - 1) * blockSize,0,blockSize,blockSize), col * blockSize, row * blockSize, null);
        } 
        
        for (i = 0; i <= boardHeight; i++){
        g.drawLine(0, i * blockSize, boardWidth * blockSize, i * blockSize);
     }
     
        for (j = 0; j <= boardWidth; j++){
        g.drawLine(j * blockSize,0, j * blockSize, boardHeight * blockSize);
    } 
  }
  
   // Permite que aleatoriamente seja escolhida a peça que ira descer.
    public void setNextShape(){
        index = (int)(Math.random() * shapes.length);
        
        Shape newShape = new Shape(shapes[index].getBlock(),this, shapes[index].getCoords(),shapes[index].getColor());
        
        currentShape = newShape;
        
    }
    
    // Retorna a Tela
    public int[][] getBoard(){
        return board;
    }
    
    // Returnar o tamanho de um bloco
     public int getBlockSize(){
        return blockSize;
    
    }
    
    // Reaçao ao carregamento nas setas
  @Override
    public void keyPressed(KeyEvent e){
        
        if ( e.getKeyCode() == KeyEvent.VK_LEFT)
            currentShape.setDeltaX(-1);
        
        if ( e.getKeyCode() == KeyEvent.VK_RIGHT)
            currentShape.setDeltaX(1);
            
        if (e.getKeyCode() == KeyEvent.VK_DOWN)
            currentShape.speedDown();
            
        if (e.getKeyCode() == KeyEvent.VK_UP)
            currentShape.rotate();
       }   
    
    // Reaçao que impede a movimentaçao demasiado rapida da peça que desce.
   @Override
    public void keyReleased(KeyEvent e){
        
         if (e.getKeyCode() == KeyEvent.VK_DOWN)
            currentShape.normalSpeed();
   }   
  
   @Override
    public void keyTyped(KeyEvent e){
    }
   
   
}
