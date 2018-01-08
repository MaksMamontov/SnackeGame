import javax.swing.*;

public class WindowMain extends JFrame {

    public WindowMain(){
        setTitle("My game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(320, 345);
        setLocation(400,400);
        add(new Field());
        setVisible(true);
    }

    public static void main(String[] args) {
        WindowMain wm = new WindowMain();
    }
}
