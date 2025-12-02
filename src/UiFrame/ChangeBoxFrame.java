package UiFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ChangeBoxFrame extends JFrame implements MouseListener {
    public ChangeBoxFrame(){
        // Write in this way will make code-reading easier.
        this.initFrame();
        this.initView();
        this.setVisible(true);
    }

    public void initFrame() {
        //initFrame is the starting frame, creating a basic frame with nothing on it.
        this.setSize(240,235);
        this.setTitle("3-Match Game");
        this.setDefaultCloseOperation(3);
        //使窗口居中
        this.setLocationRelativeTo((Component)null);
        this.setAlwaysOnTop(true);
        this.setLayout((LayoutManager)null);
        this.setResizable(false);

    }
    public void initView(){
        this.setIconImage(icon.getImage());
        JLabel inputX=new JLabel("Input the Horizontal Boxes Number");
        inputX.setBounds(10,5,300,30);
        this.add(inputX);

        textFieldX.setBounds(10,35,200,30);
        JLabel inputY=new JLabel("Input the Vertical Boxes Number");
        inputY.setBounds(10,75,300,30);
        this.add(inputY);
        textFieldY.setBounds(10,105,200,30);
        this.add(textFieldX);
        this.add(textFieldY);
        buttonConfirm.setBounds(60,150,100,40);
        buttonConfirm.addMouseListener(this);
        this.add(buttonConfirm);

    }
    public String path= System.getProperty("user.dir")+"\\src\\Images\\";
    ImageIcon icon=new ImageIcon(path+"icon.png");
    JTextField textFieldX=new JTextField();
    JButton buttonConfirm=new JButton("Confirm");
    JTextField textFieldY=new JTextField();
    Object X;
    Object Y;
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource()==buttonConfirm){
            try {
                Object X=Integer.parseInt(textFieldX.getText());
                Object Y=Integer.parseInt(textFieldY.getText());
                if (X instanceof Integer&&Y instanceof Integer){
                    int tempx= (Integer) X;
                    int tempy= (Integer) Y;
                    if (tempx >=4 && tempx<=20 ) {
                        if(tempy >=4 && tempy <=12 ) {
                            this.setVisible(false);
                            GameFrame gf = new GameFrame(tempx,tempy,1,false);
                            gf.RefreshGame(true);
                        }else{
                            JOptionPane.showMessageDialog(this, "The Y should be a integer in [4,12]");
                        }
                    }else {
                        JOptionPane.showMessageDialog(this, "The X should be a integer in [4,20]");
                    }
                }
            } catch (NumberFormatException e1){
                JOptionPane.showMessageDialog(this, "The input should be a integer!");
            }

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
