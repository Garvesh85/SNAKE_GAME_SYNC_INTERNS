package SNAKE.GAME;

import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private int[] snakexlength = new int[750];
    private int[] snakeylength = new int[750];
    private int lengthOfSnake = 3;
    int[] xPos={25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850};
    int[] yPos={75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625};

    private Random random=new Random();
    private int enemyX, enemyY;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private int moves = 0;
    private int score = 0;
    private boolean gameover=false;

    private ImageIcon snakeTitle = new ImageIcon(ClassLoader.getSystemResource("Icon/snaketitle.jpg"));
    private ImageIcon leftMouth = new ImageIcon(ClassLoader.getSystemResource("Icon/leftmouth.png"));
    private ImageIcon rightMouth = new ImageIcon(ClassLoader.getSystemResource("Icon/rightmouth.png"));
    private ImageIcon upMouth = new ImageIcon(ClassLoader.getSystemResource("Icon/upmouth.png"));
    private ImageIcon downMouth = new ImageIcon(ClassLoader.getSystemResource("Icon/downmouth.png"));
    private ImageIcon snakeImage = new ImageIcon(ClassLoader.getSystemResource("Icon/snakeimage.png"));
    private ImageIcon enemy = new ImageIcon(ClassLoader.getSystemResource("Icon/enemy.png"));
    private Timer timer;
    private int delay = 100;

    GamePanel() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);
        timer = new Timer(delay, this);
        timer.start();
        newEnemy();
    }

    private void newEnemy() {
        enemyX=xPos[random.nextInt(34)];
        enemyY=yPos[random.nextInt(23)];
        for(int i=lengthOfSnake-1;i>=0;i--){
            if(snakexlength[i]==enemyX && snakeylength[i]==enemyY){
                newEnemy();
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.WHITE);
        g.drawRect(24,10,851,55);
        g.drawRect(24,74,851,576);

        snakeTitle.paintIcon(this,g,25,11);
        g.setColor(Color.BLACK);
        g.fillRect(25,75,850,875);

        if(moves==0){
            snakexlength[0]=100;
            snakexlength[1]=75;
            snakexlength[2]=50;

            snakeylength[0]=100;
            snakeylength[1]=100;
            snakeylength[2]=100;
        }
        if(left){
            leftMouth.paintIcon(this,g,snakexlength[0],snakeylength[0]);
        }
        if(right){
            rightMouth.paintIcon(this,g,snakexlength[0],snakeylength[0]);
        }
        if(up){
            upMouth.paintIcon(this,g,snakexlength[0],snakeylength[0]);
        }
        if(down){
            downMouth.paintIcon(this,g,snakexlength[0],snakeylength[0]);
        }
        for(int i=1;i<lengthOfSnake;i++){
            snakeImage.paintIcon(this,g,snakexlength[i],snakeylength[i]);
        }

        enemy.paintIcon(this,g,enemyX,enemyY);
        if(gameover){
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial",Font.BOLD,50));
            g.drawString("Game Over",300,300);

            g.setFont(new Font("Arial",Font.PLAIN,50));
            g.drawString("Press Space to Restart",320,350);
        }
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial",Font.PLAIN,14));
        g.drawString("Score :"+score,750,30);
        g.drawString("Length :"+lengthOfSnake,790,80);
        g.dispose();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i=lengthOfSnake-1;i>0;i--){
            snakexlength[i]=snakexlength[i-1];
            snakeylength[i]=snakeylength[i-1];
        }
        if(left){
            snakexlength[0]=snakexlength[0]-25;
        }
        if(right){
            snakexlength[0]=snakexlength[0]+25;
        }
        if(up){
            snakeylength[0]=snakeylength[0]-25;
        }
        if(down){
            snakeylength[0]=snakeylength[0]+25;
        }
        if(snakexlength[0]>850){
            snakexlength[0]=25;
        }
        if(snakexlength[0]<25){
            snakexlength[0]=850;
        }
        if(snakeylength[0]>625){
            snakeylength[0]=75;
        }
        if(snakeylength[0]<75){
            snakeylength[0]=625;
        }
        collidesWithEnemy();
        collidesWithBody();
        repaint();
    }

    private void collidesWithBody() {
        for(int i=lengthOfSnake-1;i>0;i--){
            if(snakexlength[i]==snakexlength[0] && snakeylength[i]==snakeylength[0]){
                timer.stop();
                gameover=true;
            }
        }
    }

    private void collidesWithEnemy() {
        if(snakexlength[0]==enemyX && snakeylength[0]==enemyY){
            newEnemy();
            lengthOfSnake++;
            score++;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_SPACE) {
            restart();
        }
        if(e.getKeyCode()==KeyEvent.VK_LEFT && (!right)){
            moves++;
            left=true;
            right=false;
            up=false;
            down=false;
        }
        if(e.getKeyCode()==KeyEvent.VK_RIGHT && (!left)){
            moves++;
            left=false;
            right=true;
            up=false;
            down=false;
        }
        if(e.getKeyCode()==KeyEvent.VK_UP && (!down)){
            moves++;
            left=false;
            right=false;
            up=true;
            down=false;
        }
        if(e.getKeyCode()==KeyEvent.VK_DOWN && (!up)){
            moves++;
            left=false;
            right=false;
            up=false;
            down=true;
        }
    }

    private void restart() {
        gameover=false;
        moves=0;
        score=0;
        lengthOfSnake=3;
        left=false;
        right=true;
        up=false;
        down=false;
        timer.start();
        repaint();
    }

    public static void main(String[] args) {

    }


    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
}
