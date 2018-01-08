import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Field extends JPanel implements ActionListener {
    private final int SIZE = 320;
    private final int SIZE_DOT = 16; //Размер ячейки
    private final int ALL_DOT = 400;

    private Image dot;
    private Image apple;

    private int appleX;
    private int appleY;
    private int[] x = new int[ALL_DOT];
    private int[] y = new int[ALL_DOT];

    private int dots;
    private Timer time;

    private boolean left;
    private boolean rigth = true;
    private boolean up;
    private boolean down;
    private boolean inGame = true;


    public Field(){
        setBackground(Color.BLACK);
        LoadImages();
        initGame();
        addKeyListener(new KeyListener());
        setFocusable(true);
    }

    public void initGame(){
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 48 - i*SIZE_DOT;
            y[i] = 48;
        }
        time = new Timer(250, this);
        time.start();
        createApple();
    }

    public void createApple(){
        appleX = new Random().nextInt(20)*SIZE_DOT;
        appleY = new Random().nextInt(20)*SIZE_DOT;
    }

    public void LoadImages(){
        ImageIcon iia = new ImageIcon(this.getClass().getClassLoader().getResource("apple.png"));
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon(this.getClass().getClassLoader().getResource("dot.png"));
        dot = iid.getImage();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if(inGame){
            graphics.drawImage(apple, appleX, appleY, this);
            for (int i = 0; i < dots; i++) {
                graphics.drawImage(dot, x[i], y[i], this);
            }
        } else {
            String str = new String("GAME OVER");
            graphics.setColor(Color.WHITE);
            graphics.drawString(str, 125, SIZE/2);
        }
    }

    public void move(){
        for (int i = SIZE_DOT; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if (rigth){
            x[0] +=SIZE_DOT;
        }
        if (left){
            x[0] -=SIZE_DOT;
        }
        if (up){
            y[0] -=SIZE_DOT;
        }
        if (down){
            y[0] +=SIZE_DOT;
        }
    }

    public void checkApple(){
        if(x[0] == appleX && y[0] == appleY){
            dots++;
            createApple();
        }
    }

    public void checkCollisions(){
        for (int i = dots; i > 0; i--) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i]){
                inGame = false;
            }
        }

        if(x[0] > SIZE){
            inGame = false;
        }
        if(x[0] < 0){
            inGame = false;
        }
        if(y[0] > SIZE){
            inGame = false;
        }
        if(y[0] < 0){
            inGame = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (inGame){
            checkApple();
            checkCollisions();
            move();
        }
        repaint();
    }

    class KeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent k) {
            super.keyPressed(k);
            int key = k.getKeyCode();
            if (key == KeyEvent.VK_LEFT && ! rigth){
                left = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_RIGHT && ! left){
                rigth = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_DOWN && ! up){
                down = true;
                rigth = false;
                left = false;
            }
            if (key == KeyEvent.VK_UP && ! down){
                up = true;
                rigth = false;
                left = false;
            }
        }
    }
}
