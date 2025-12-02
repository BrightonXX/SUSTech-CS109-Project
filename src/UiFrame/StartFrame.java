package UiFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class StartFrame extends JFrame implements MouseListener {
    //Below are the constants that will be used.
    public String path= System.getProperty("user.dir")+"\\src\\Images\\";
    JButton easy = new JButton();
    JButton onlineMode = new JButton();
    JButton connectTo = new JButton();
    JButton hard =new JButton();
    ImageIcon background=new ImageIcon(path+"background.png");

    ImageIcon icon=new ImageIcon(path+"icon.png");
    JLabel animal=new JLabel(icon);
    ImageIcon easyImage=new ImageIcon(path+"登录按钮.png");
    ImageIcon easyPressedImage =new ImageIcon(path+"登录按下.png");
    ImageIcon[] e1={ new ImageIcon(path+"fox.png"),
                     new ImageIcon(path+"hippo.png"),
                     new ImageIcon(path+"frog.png"),
                     new ImageIcon(path+"eagle.png")
                                                            };

    public StartFrame(){
        // Write in this way will make code-reading easier.
        this.initFrame();
        this.initView();
        this.setVisible(true);
    }

    public void initFrame() {
    //initFrame is the starting frame, creating a basic frame with nothing on it.
        this.setSize(background.getIconWidth()+17,background.getIconHeight()+40);
        this.setTitle("3-Match Game");
        this.setDefaultCloseOperation(3);
        //使窗口居中
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        this.setLayout(null);
        this.setResizable(false);
    }
    public void initView(){
        // set icon to hippo.
        this.setIconImage(icon.getImage());
        // to see the path if is correct.
        System.out.println(path+"background.png");

        JLabel backgroundlable = new JLabel(background);
        //开始添加组件
        easy.setBounds(153, 160, 204, 45);
        easy.setText("本地游戏");
        easy.setBorderPainted(true);
        easy.setContentAreaFilled(true);
        easy.addMouseListener(this);
        easy.setOpaque(false);
        this.getContentPane().add(easy);
        onlineMode.setBounds(153, 240, 204, 45);
        onlineMode.setBorderPainted(true);
        onlineMode.addMouseListener(this);
        onlineMode.setText("多人模式（创建新服务器）");
        onlineMode.setContentAreaFilled(true);
        onlineMode.setOpaque(false);
        onlineMode.addActionListener(e -> {
                    System.out.println("点击了多人模式");
                    this.setVisible(false);
                    JDialog jDialog = new JDialog();
                    setContentPane(jDialog.getContentPane());
                    jDialog.setBounds(400,300,340,256);
                    jDialog.setLocationRelativeTo(null);
                    JLabel jl = new JLabel("端口：");
                    jl.setBounds(52,33,54,15);
                    jDialog.getLayeredPane().add(jl);
                    JTextField jTextField = new JTextField(6);
                    jTextField.setBounds(116,30,139,21);
                    jDialog.getLayeredPane().add(jTextField);
                    JLabel jl1 = new JLabel("难度（默认为简单）：");
                    jl1.setBounds(52,74,54,15);
                    jDialog.getLayeredPane().add(jl1);
                    JTextField jTextField1 = new JTextField(15);
                    jTextField1.setBounds(116,71,139,21);
                    jDialog.getLayeredPane().add(jTextField1);
                    JButton jButton = new JButton("创建");
                    jButton.setBounds(64,116,69,23);
                    jButton.addActionListener(e1 -> {
                        System.out.println("点击了创建");
                        int port = Integer.parseInt(jTextField.getText());
                        int dif = jTextField1.getText().equals("")?4:Integer.parseInt(jTextField1.getText());
                        jDialog.setVisible(false);
                        try {
                            new GameFrame(port,dif);
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null,"端口被占用！");
                            throw new RuntimeException(ex);
                        }
                    });
                    jDialog.getLayeredPane().add(jButton);
                    JButton closeButton = new JButton("退出");
                    closeButton.setBounds(174,116,69,23);
                    closeButton.addActionListener(e1 -> {
                        System.out.println("点击了退出");
                        jDialog.setVisible(false);
                        jDialog.dispose();
                        this.initFrame();
                        this.initView();
                        this.setVisible(true);
                    });
                    jDialog.getLayeredPane().add(closeButton);
                    jDialog.setVisible(true);
                    jDialog.setIconImage(icon.getImage());
                    jDialog.setTitle("3-Match Game");
                    jDialog.setResizable(false);
        });
        this.getLayeredPane().add(onlineMode,0);
        connectTo.setBounds(153, 320, 204, 45);
        connectTo.setBorderPainted(true);
        connectTo.setText("多人模式（连接服务器）");
        connectTo.setContentAreaFilled(true);
        connectTo.addActionListener(e -> {
            System.out.println("点击了多人模式");
            this.setVisible(false);
            JDialog jDialog = new JDialog();
            setContentPane(jDialog.getContentPane());
            jDialog.setBounds(400,300,340,256);
            jDialog.setLocationRelativeTo(null);
            JLabel jl1 = new JLabel("IP：");
            jl1.setBounds(52,74,54,15);
            jDialog.getLayeredPane().add(jl1);
            JTextField jTextField1 = new JTextField(15);
            jTextField1.setBounds(116,71,139,21);
            jDialog.getLayeredPane().add(jTextField1);
            JLabel jl = new JLabel("端口：");
            jl.setBounds(52,33,54,15);
            jDialog.getLayeredPane().add(jl);
            JTextField jTextField = new JTextField(6);
            jTextField.setBounds(116,30,139,21);
            jDialog.getLayeredPane().add(jTextField);
            //this is for the user to set the diffculty
            JLabel jl2 = new JLabel("难度（默认为简单）：");
            jl2.setBounds(52,93,54,15);
            jDialog.getLayeredPane().add(jl2);
            JTextField jTextField2 = new JTextField(6);
            jTextField2.setBounds(116,90,139,21);
            jDialog.getLayeredPane().add(jTextField2);
            JButton jButton = new JButton("连接");
            jButton.setBounds(64,116,69,23);
            jButton.addActionListener(e1 -> {
                System.out.println("点击了连接");
                jDialog.setVisible(false);
                int port = jTextField.getText().equals("")?0:Integer.parseInt(jTextField.getText());
                String IP = jTextField1.getText().equals("")?"127.0.0.1":jTextField1.getText();
                int dif = jTextField2.getText().equals("")?4:Integer.parseInt(jTextField2.getText());
                try {
                    new GameFrame(port,IP,dif);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            jDialog.getLayeredPane().add(jButton);
            JButton closeButton = new JButton("退出");
            closeButton.setBounds(174,116,69,23);
            closeButton.addActionListener(e1 -> {
                System.out.println("点击了退出");
                jDialog.setVisible(false);
                this.initFrame();
                this.initView();
                this.setVisible(true);
            });
            jDialog.getLayeredPane().add(closeButton);
            jDialog.setVisible(true);
        });
        connectTo.setOpaque(false);
        this.getLayeredPane().add(connectTo,0);
        animal.setBounds(193,400,120,120);
        this.getContentPane().add(animal);
        backgroundlable.setSize(background.getIconWidth(),background.getIconHeight());
        this.getLayeredPane().add(backgroundlable,-100);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource()== this.easy){
            System.out.println("点击了开始");
            animal.setIcon(e1[(int)(Math.random()*4)]);
            this.setVisible(false);
            JDialog selectDif= new JDialog();
            selectDif.setBounds(400,300,340,256);
            selectDif.setLocationRelativeTo(null);
            selectDif.setVisible(true);
            Font fout= new Font("微软雅黑",Font.BOLD,20);
            JLabel jl = new JLabel("请选择难度：");
            jl.setFont(fout);
            jl.setBounds(62,33,140,35);
            selectDif.getLayeredPane().add(jl,1);
            JButton easy = new JButton("简单");
            easy.setBounds(64,116,89,23);
            easy.addActionListener(e1 -> {
                System.out.println("点击了简单");
                selectDif.setVisible(false);
                GameFrame gf = new GameFrame(8,8,2,false);
                gf.RefreshGame(true);
            });
            selectDif.getLayeredPane().add(easy,1);
            selectDif.setIconImage(icon.getImage());
            selectDif.setTitle("3-Match Game");
            selectDif.setResizable(false);
            JButton hard = new JButton("困难");
            hard.setBounds(174,116,89,23);
            hard.addActionListener(e1 -> {
                System.out.println("点击了困难");
                selectDif.setVisible(false);
                GameFrame gf = new GameFrame(10,10,8,false);
                gf.RefreshGame(true);
            });
            selectDif.getLayeredPane().add(hard,1);
            JButton custom = new JButton("自定义");
            custom.setBounds(64,146,89,23);
            custom.addActionListener(e1 -> {
                System.out.println("点击了自定义");
                selectDif.setVisible(false);
                JDialog customDif= new JDialog();
                customDif.setBounds(400,300,340,256);
                customDif.setLocationRelativeTo(null);
                JLabel jl1 = new JLabel("请输入行数：");
                jl1.setBounds(42,33,84,15);
                customDif.getLayeredPane().add(jl1);
                JTextField jTextField1 = new JTextField(6);
                jTextField1.setBounds(116,30,139,21);
                customDif.getLayeredPane().add(jTextField1);
                JLabel jl2 = new JLabel("请输入列数：");
                jl2.setBounds(42,63,84,15);
                customDif.getLayeredPane().add(jl2);
                JTextField jTextField2 = new JTextField(6);
                jTextField2.setBounds(116,60,139,21);
                customDif.getLayeredPane().add(jTextField2);
                JLabel jl3 = new JLabel("请输入难度：");
                jl3.setBounds(42,93,84,15);
                customDif.getLayeredPane().add(jl3);
                JTextField jTextField3 = new JTextField(6);
                jTextField3.setBounds(116,90,139,21);
                selectDif.getLayeredPane().add(jTextField3);
                customDif.getLayeredPane().add(jTextField3);
                customDif.setIconImage(icon.getImage());
                customDif.setTitle("3-Match Game");
                customDif.setResizable(false);
                JButton confirm = new JButton("确定");
                confirm.setBounds(64,146,89,23);
                confirm.addActionListener(e2 -> {
                    System.out.println("点击了确定");
                    customDif.setVisible(false);
                    int row = Integer.parseInt(jTextField1.getText());
                    int col = Integer.parseInt(jTextField2.getText());
                    int dif = Integer.parseInt(jTextField3.getText());
                    GameFrame gf = new GameFrame(col,row,dif,false);
                    gf.RefreshGame(true);
                });
                customDif.getLayeredPane().add(confirm,1);
                JButton closeButton = new JButton("退出");
                closeButton.setBounds(174,146,89,23);
                closeButton.addActionListener(e2 -> {
                    System.out.println("点击了退出");
                    customDif.setVisible(false);
                    this.setVisible(true);
                });
                customDif.getLayeredPane().add(closeButton,1);
                customDif.setVisible(true);
            });
            selectDif.getLayeredPane().add(custom,1);
            JButton closeButton = new JButton("退出");
            closeButton.setBounds(174,146,89,23);
            closeButton.addActionListener(e1 -> {
                System.out.println("点击了退出");
                selectDif.setVisible(false);
                this.setVisible(true);
            });
            selectDif.getLayeredPane().add(closeButton,1);
        }else {
            System.out.println("啥都没点到");
        }
    }


    public void mousePressed(MouseEvent e) {
        if (e.getSource() == this.easy) {
            this.easy.setIcon(easyPressedImage);
        }
    }


    public void mouseReleased(MouseEvent e) {
        if (e.getSource() == this.easy) {
            this.easy.setIcon(easyImage);
        }
    }


    public void mouseEntered(MouseEvent e) {

    }


    public void mouseExited(MouseEvent e) {

    }
}
