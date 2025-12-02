package Storage;

import javax.swing.*;
import java.io.*;
import java.util.Scanner;

public class IOReader {
    public static String path= System.getProperty("user.dir")+"\\src\\Storage\\";
    //需要保存的内容：n*n的棋子类型-->int n*n的数组，数组里面存储0,1,2,3,4等棋子类型，然后存储分数，道具数量和步数信息，难度等级
    //Update by 石萃翔：将字节流改为字符流，将char数组改为int数组，将分隔符改为"-"，使得存档文件可以被人类读懂。
    public static void OutPut(String filePath,int[][] chessStoreage, int score, int Function1, int Function2, int Function3, int steps, int difficulty) throws IOException {
        //wrap就是IO流里面的换行符号
        String wrap="\r\n";
        //保存逻辑： 保存char数组作为保存格式 保存文件的第一个数字是CHESSBOARDX,然后分隔符"-"随后是CHESSBOARDY,-,int[][],-,....顺序按照本函数的构造方法。
        File fil=new File(path+filePath+".txt");
        PrintWriter outPrintWriter = new PrintWriter(fil);

        int CHESSBOARDX=chessStoreage.length;
        int CHESSBOARDY=chessStoreage[0].length;

        outPrintWriter.print(CHESSBOARDX);
        outPrintWriter.print('-');
        outPrintWriter.print(CHESSBOARDY);
        outPrintWriter.print('-');
        outPrintWriter.println();
        for (int i = 0; i < CHESSBOARDX; i++) {
            for (int i1 = 0; i1 < CHESSBOARDY; i1++) {
                outPrintWriter.print(chessStoreage[i][i1]);
            }
            outPrintWriter.println();
        }
        outPrintWriter.print('-');
        outPrintWriter.print(score);
        outPrintWriter.print('-');
        outPrintWriter.print(Function1);
        outPrintWriter.print('-');
        outPrintWriter.print(Function2);
        outPrintWriter.print('-');
        outPrintWriter.print(Function3);
        outPrintWriter.print('-');
        outPrintWriter.print(steps);
        outPrintWriter.print('-');
        outPrintWriter.print(difficulty);
        outPrintWriter.close();
    }
    public static int[][] InPut(String filePath) throws IOException {
        String[] parts = filePath.split("\\.");
        if (parts.length > 1 && parts[1].equals("txt")) {
        } else {
            JOptionPane.showMessageDialog(null,"文件格式错误，代码101");
            return null;
        }
        File fil=new File(path+filePath);
        //assert fil.exists();
        if(!fil.exists()){
            JOptionPane.showMessageDialog(null,"文件不存在");
            return null;
        }
        Scanner sc = new Scanner(fil);
        char receiver;
        int CHESSBOARDX=0;
        int CHESSBOARDY=0;
        int score=0;
        int Function1=0;
        int Function2=0;
        int Function3=0;
        int steps=0;
        int difficulty=0;
        String[]arr = sc.next().split("-");
        CHESSBOARDX=Integer.parseInt(arr[0]);
        CHESSBOARDY=Integer.parseInt(arr[1]);
        int[][] returnArray=new int[CHESSBOARDX+2][Math.max(CHESSBOARDY,7)];
        /*数组的[0][0]&[0][1]-->CX,CY
         *从第二行开始，就是chesspiece的数据
         *数组的最后一行依次排名输入顺序  */
        for (int i = 0; i < CHESSBOARDX; i++) {
            String arr1 = sc.next();
            for (int i1 = 0; i1 < CHESSBOARDY; i1++) {
                if((returnArray[i+1][i1]=arr1.charAt(i1)-'0')/6!=0){
                    JOptionPane.showMessageDialog(null,"图片并非指定的几种类型","错误编码：103",JOptionPane.ERROR_MESSAGE);
                    return null;
                }
                System.out.print(returnArray[i+1][i1]+" ");
            }
            System.out.println();
        }
        String[] arr2 = sc.next().split("-");
            score=score*10+Integer.parseInt(arr2[1]);
            Function1=Function1*10+Integer.parseInt(arr2[2]);
            Function2=Function2*10+Integer.parseInt(arr2[3]);
            Function3=Function3*10+Integer.parseInt(arr2[4]);
            steps=steps*10+Integer.parseInt(arr2[5]);
            difficulty=difficulty*10+Integer.parseInt(arr2[6]);
        returnArray[0][0]=CHESSBOARDX;
        returnArray[0][1]=CHESSBOARDY;
        returnArray[CHESSBOARDX+1][0]=score;
        returnArray[CHESSBOARDX+1][1]=Function1;
        returnArray[CHESSBOARDX+1][2]=Function2;
        returnArray[CHESSBOARDX+1][3]=Function3;
        returnArray[CHESSBOARDX+1][4]=steps;
        returnArray[CHESSBOARDX+1][5]=difficulty;
        System.out.println(CHESSBOARDX);
        System.out.println(CHESSBOARDY);
        System.out.println("保存的score："+score);
        sc.close();
        return returnArray;
    }
}
