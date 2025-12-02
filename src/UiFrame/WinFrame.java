package UiFrame;

import javax.swing.*;

public class WinFrame extends JFrame {
    public WinFrame(){
        initFrame();
        initView();
        this.setVisible(true);
    }
    public void initFrame() {
        //initFrame is the starting frame, creating a basic frame with nothing on it.
        this.setSize(225,114);
        this.setTitle("3-Match Game");
        this.setDefaultCloseOperation(3);
        //使窗口居中
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        this.setLayout(null);
        this.setResizable(false);
    }
    public void initView() {
        // set icon to hippo.
        this.setIconImage(icon.getImage());
        // to see the path if is correct.
        JLabel backgroundlable = new JLabel(win);
        backgroundlable.setSize(win.getIconWidth(), win.getIconHeight());
        this.getLayeredPane().add(backgroundlable, -100);
    }
    public String path= System.getProperty("user.dir")+"\\src\\Images\\";
    ImageIcon win=new ImageIcon(path+"win.png");
    ImageIcon icon=new ImageIcon(path+"icon.png");
}
