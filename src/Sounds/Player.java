package Sounds;
import javax.sound.sampled.*;
import java.io.*;
public class Player {
    public static String path= System.getProperty("user.dir")+"\\src\\Sounds\\";
    public static File fall = new File(path+"fall.wav");
    public static File slide = new File(path+"slide.wav");
    public static File bomb = new File(path+"bomb.wav");
    public static File fire = new File(path+"fire.wav");

    public static void slideSound() {playSound(slide);}
    public static void fallSound() {playSound(fall);}
    public static void bombSound() {playSound(bomb);}
//    public static void bgmSound() {
//        new Thread(() -> {
//            try {
//                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bgm);
//                Clip clip = AudioSystem.getClip();
//                clip.open(audioInputStream);
//                clip.start();
//                Thread.sleep(140000); // Allow the sound to play
//                clip.close();
//            } catch (Exception e) {
//                System.out.println("Error playing sound: " + e.getMessage());
//            }
//        }).start();
//    }
    public static void fireSound() {playSound(fire);}
    private static void playSound(File file) {
        new Thread(() -> {
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
                Thread.sleep(1200); // Allow the sound to play
                clip.close();
            } catch (Exception e) {
                System.out.println("Error playing sound: " + e.getMessage());
            }
        }).start();
    }
}