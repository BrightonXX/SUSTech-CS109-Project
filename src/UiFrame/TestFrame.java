package UiFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
public class TestFrame {
    public static void main(String[] args) {

       String path= System.getProperty("user.dir")+"\\src\\Images\\";
        ImageIcon icon=new ImageIcon(path+"icon.png");
        // 创建一个JFrame窗口
        JFrame frame = new JFrame("设置图片透明度");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);

        // 创建一个JLabel，并设置透明度
        JLabel label= new JLabel();
        try {
            BufferedImage originalImage = ImageIO.read(new File(path+"icon.png"));  // 替换为你的图片路径
            BufferedImage blurredImage = blurImage(originalImage, 0.1F);  // 10是模糊程度，可以根据需要调整
            ImageIcon imageIcon = new ImageIcon(blurredImage);
            label.setIcon(imageIcon);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 将JLabel添加到JFrame中
        frame.getContentPane().add(label);

        // 显示窗口
        frame.setVisible(true);
    }
    private static BufferedImage blurImage(BufferedImage image, float blurRadius) {
        BufferedImage blurredImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = blurredImage.createGraphics();
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, blurRadius));  // 设置透明度，这里的0.5f代表50%的不透明度
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();
        return blurredImage;
    }
}