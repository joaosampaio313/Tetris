import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Shape{
     // Variaveis a utilizar                 
         public int [][] coords;
         public int x, y, i, j;
         private BufferedImage block;
         private Board board;
         private int row = 0;
         private int col = 0;
         private int color;
         private int deltaX = 0;
         private boolean moveX = true;
         private long time, lastTime;
         private int normalSpeed = 600, speedDown = 100, currentSpeed;
         private boolean collision = false;
         
  
    public Shape(BufferedImage block, Board board, int [][] coords,int color){
        this.block = block;
        this.board = board;
        this.coords = coords;
        this.color = color;
        
        currentSpeed = normalSpeed;
        time = 0;
        lastTime = System.currentTimeMillis();
        
        x = 4;
        y = 0;
    }
      
    // Atualizaçao do Jogo
    public void update(){
        
        time += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis(); 
        
          if (collision){
               for (row = 0 ; row < coords.length; row++){
                  for (col = 0 ; col < coords[row].length; col++)
                    if (coords[row][col] != 0)
                        board.getBoard()[y + row][x + col] = color;
                    
                    checkLine();
                    board.setNextShape();
               }
          }
        
        if ((x + deltaX + coords[0].length <= 10) && !(x + deltaX < 0)){
               for (row = 0 ; row < coords.length; row++){
                  for (col = 0 ; col < coords[row].length; col++)
                    if (coords[row][col] != 0){
                        if (board.getBoard()[y + row][x + deltaX + col] != 0)
                            moveX = false;
                  }
            }
                if (moveX)
                   x+=deltaX;
         
        if (!(y + 1 + coords.length > 20)){
               
              for (row = 0 ; row < coords.length; row++){
                  for (col = 0 ; col < coords[row].length; col++)
                      if (coords[row][col] != 0){
                          if (board.getBoard()[row + y + 1][col + x] != 0)
                              collision = true;
                    }
             }
         
             if (time > currentSpeed){
             y++;
             time = 0;
           }
        }
        else 
            collision = true;
        
            deltaX = 0;
            moveX = true;
        }
    }
    // Desenho das Peças
    public void render(Graphics g){
         for (row = 0; row < coords.length; row++){
            for(col = 0; col < coords[row].length; col++){
                 if (coords[row][col] != 0)
                 g.drawImage(block,col*board.getBlockSize() + x * board.getBlockSize(),
                 row*board.getBlockSize() + y * board.getBlockSize(),null);
            }
        }
    }
    
    // Rotaçao das Peças
    public void rotate(){
        
        int [][] rotatedMatrix = null;
        
        rotatedMatrix = getTranspose(coords);
        
        rotatedMatrix = getReversedMatrix(rotatedMatrix);
        
        if (x  + rotatedMatrix[0].length > 10 || y + rotatedMatrix.length > 20)
        return;
        
        coords = rotatedMatrix;
    }
     // Matriz transposta da matriz que defini a peça.
    private int[][] getTranspose(int [][] matrix){
     
        int[][] newMatrix = new int[matrix[0].length][matrix.length];
        
        for(int i = 0; i < matrix.length; i++)
            for(int j = 0; j < matrix[i].length; j++)
                newMatrix[j][i] = matrix[i][j];
        
        return newMatrix;
    }
    
    // Reverso da matriz transposta da peça.
    private int[][] getReversedMatrix(int [][] matrix){
       int middle = matrix.length / 2;
                for(int i = 0; i < middle; i++){
           int[] m = matrix[i];
           matrix[i] = matrix[matrix.length - i - 1];
           matrix[matrix.length - i - 1] = m;
    }
        return matrix;
    }
   
    // Verifica se todos os elementos da linha boardHeight - 1 da board sao diferentes de 0.
    // Em caso positivo, puxa os elementos da linha l para a linha l - 1.
    
    public void checkLine(){
        
        int height = board.getBoard().length - 1;
        
            for(i = height; i > 0;i--){
                
                int count  = 0;
                for (j = 0; j < board.getBoard()[0].length; j++){
                    if (board.getBoard()[i][j] != 0)
                        count++;
                        
                    board.getBoard()[height][j] = board.getBoard()[i][j];
                }
                    
                if (count < board.getBoard()[0].length)
                    height--;
            
        }
    }
    
    // Returnar o bloco.
    public BufferedImage getBlock(){
        return block;
    }
    
    // Retornar a matriz correspondente a peça desejada.
    public int [][] getCoords(){
        return coords;
    } 
    
    public int getColor(){
        return color;
    }
    
    public void setDeltaX(int deltaX){
        this.deltaX = deltaX;
    }
    
    public void normalSpeed(){
        currentSpeed = normalSpeed;
    }
    public void speedDown(){
        currentSpeed = speedDown;
    }
}
