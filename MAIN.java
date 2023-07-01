package SNAKE.GAME;

import javax.swing.*;
import java.awt.*;

public class MAIN {
    public static void main(String[] args) {
          JFrame f=new JFrame();
          f.setTitle("Snake Game");
          f.setBounds(10,10,905,700);
          f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          f.setResizable(false);

          GamePanel panel=new GamePanel();
          panel.setBackground(Color.DARK_GRAY);
          f.add(panel);
          f.setVisible(true);
    }
}
