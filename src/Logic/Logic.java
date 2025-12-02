package Logic;

import Chess.ChessPiece;
import UiFrame.GameFrame;

import javax.swing.*;

public class Logic {

    public static int[][] checkChessboard(ChessPiece[][] chessStoreage){
        int[][] result=new int[chessStoreage.length][chessStoreage[0].length];
        boolean[][] combox=new boolean[chessStoreage.length][chessStoreage[0].length];
        boolean[][] comboy=new boolean[chessStoreage.length][chessStoreage[0].length];
        for(int i=0;i<chessStoreage.length;i++){
            for(int j=0;j<chessStoreage[i].length;j++){
                result[i][j]=0;
                combox[i][j]=false;
                comboy[i][j]=false;
            }
        }
        for (int x = 0; x < chessStoreage.length-2; x++) {
            for (int y = 0; y < chessStoreage[x].length; y++) {
                if(chessStoreage[x][y]!=null&&chessStoreage[x+1][y]!=null&&chessStoreage[x+2][y]!=null&&chessStoreage[x][y].equals(chessStoreage[x+1][y])&&chessStoreage[x][y].equals(chessStoreage[x+2][y])){
                    combox[x+1][y]=true;
                    if (combox[x][y]){
                        result[x + 2][y]++;
                    }else {
                        result[x][y]++;
                        result[x + 1][y]++;
                        result[x + 2][y]++;
                    }
                }
            }
        }
        for (int x = 0; x < chessStoreage.length; x++) {
            for (int y = 0; y < chessStoreage[x].length-2; y++) {
                if(chessStoreage[x][y]!=null&&chessStoreage[x][y+1]!=null&&chessStoreage[x][y+2]!=null&&chessStoreage[x][y].equals(chessStoreage[x][y+1])&&chessStoreage[x][y].equals(chessStoreage[x][y+2])){
                    comboy[x][y+1]=true;
                    if (comboy[x][y]){
                        result[x][y+2]++;
                    }else {
                        result[x][y]++;
                        result[x][y + 1]++;
                        result[x][y + 2]++;
                    }
                }
            }
        }
        return result;
    }
    public static boolean checkIfCanUpdate(ChessPiece[][] chessStoreage){
        int[][] temp=checkChessboard(chessStoreage);
        boolean retturrn=false;
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[i].length; j++) {
                if (temp[i][j]!=0) retturrn=true;
            }
        }
        return retturrn;
    }
    public static boolean checkIfHaveNull(ChessPiece[][] chessStorage){
        boolean temp=false;
        for (int i = 0; i < chessStorage.length; i++) {
            for (int i1 = 0; i1 < chessStorage[i].length; i1++) {
                if (chessStorage[i][i1]==null) temp=true;
            }
        }
        return temp;
    }
    public static boolean checkIfDropped(ChessPiece[][] chessStorage){
        boolean temp=false;
        boolean[] index=new boolean[chessStorage.length];
        for (int i = 0; i < chessStorage.length; i++) {
            if (chessStorage[i][1]==null) {
                index[i]=true;
            }else index[i]=false;
            for (int i1 = 1; i1 < chessStorage[i].length; i1++) {
                if (chessStorage[i][i1] == null && !index[i]) {
                    temp = true;
                    break;
                }
            }
        }
        return temp;
    }
    public static int[] checkIfPlayable(ChessPiece[][] chessStoreage){
        if(checkIfHaveNull(chessStoreage)){
            JOptionPane.showMessageDialog(null, "请先更新棋盘","出错啦！",JOptionPane.WARNING_MESSAGE);
        }
        int[] a=new int[4];
        //0存储x,1存储y,2存储方向————1左 5上 2下 3右
        a[0]=-1;
        a[1]=-1;
        a[2]=-1;
        a[3]=-1;
        for (int x = 0; x < chessStoreage.length-2; x++) {
            for (int y = 0; y < chessStoreage[x].length-1; y++) {
                if(chessStoreage[x][y].equals(chessStoreage[x+1][y+1])&&chessStoreage[x][y].equals(chessStoreage[x+2][y+1])&&chessStoreage[x][y+1].chessKind!=5&&chessStoreage[x][y].chessKind!=5){
                    //检查 O12
                    //    3OO的情况，而且3不能为砖块
                    a[0]=x;
                    a[1]=y;
                    a[2]=x;
                    a[3]=y+1;
                }
                if(chessStoreage[x+1][y].equals(chessStoreage[x][y+1])&&chessStoreage[x+1][y].equals(chessStoreage[x+2][y+1])&&chessStoreage[x+1][y+1].chessKind!=5&&chessStoreage[x+1][y].chessKind!=5){
                    //检查 102
                    //    03O的情况，而且3不能为砖块
                    a[0]=x+1;
                    a[1]=y;
                    a[2]=x+1;
                    a[3]=y+1;
                }
                if(chessStoreage[x+2][y].equals(chessStoreage[x][y+1])&&chessStoreage[x+2][y].equals(chessStoreage[x+1][y+1])&&chessStoreage[x+2][y+1].chessKind!=5&&chessStoreage[x+2][y].chessKind!=5){
                    //检查 120
                    //    0O3的情况，而且3不能为砖块
                    a[0]=x+2;
                    a[1]=y;
                    a[2]=x+2;
                    a[3]=y+1;
                }
                //
                if(chessStoreage[x+2][y+1].equals(chessStoreage[x][y])&&chessStoreage[x+2][y+1].equals(chessStoreage[x+1][y])&&chessStoreage[x+2][y].chessKind!=5&&chessStoreage[x+2][y+1].chessKind!=5){
                    //检查 003
                    //    120的情况，而且3不能为砖块
                    a[0]=x+2;
                    a[1]=y+1;
                    a[2]=x+2;
                    a[3]=y;
                }
                if(chessStoreage[x+1][y+1].equals(chessStoreage[x][y])&&chessStoreage[x+1][y+1].equals(chessStoreage[x+2][y])&&chessStoreage[x+1][y].chessKind!=5&&chessStoreage[x+1][y+1].chessKind!=5){
                    //检查 030
                    //    102的情况，而且3不能为砖块
                    a[0]=x+1;
                    a[1]=y+1;
                    a[2]=x+1;
                    a[3]=y;
                }
                if(chessStoreage[x][y+1].equals(chessStoreage[x+1][y])&&chessStoreage[x][y+1].equals(chessStoreage[x+2][y])&&chessStoreage[x][y].chessKind!=5&&chessStoreage[x][y+1].chessKind!=5){
                    //检查 300
                    //    012的情况，而且3不能为砖块
                    a[0]=x;
                    a[1]=y+1;
                    a[2]=x;
                    a[3]=y;
                }
            }
        }


        for (int x = 0; x < chessStoreage.length-1; x++) {
            for (int y = 0; y < chessStoreage[x].length-2; y++) {
                if(chessStoreage[x+1][y+2].equals(chessStoreage[x][y])&&chessStoreage[x+1][y+2].equals(chessStoreage[x][y+1])&&chessStoreage[x][y+2].chessKind!=5&&chessStoreage[x+1][y+2].chessKind!=5){
                    //检查 01
                    //    02
                    //    3O的情况，而且3不能为砖块
                    a[0]=x+1;
                    a[1]=y+2;
                    a[2]=x;
                    a[3]=y+2;
                }
                if(chessStoreage[x+1][y+1].equals(chessStoreage[x][y])&&chessStoreage[x+1][y+1].equals(chessStoreage[x][y+2])&&chessStoreage[x][y+1].chessKind!=5&&chessStoreage[x+1][y+1].chessKind!=5){
                    //检查 01
                    //    30
                    //    O2的情况，而且3不能为砖块
                    a[0]=x+1;
                    a[1]=y+1;
                    a[2]=x;
                    a[3]=y+1;
                }
                if(chessStoreage[x+1][y].equals(chessStoreage[x][y+1])&&chessStoreage[x+1][y].equals(chessStoreage[x][y+2])&&chessStoreage[x][y].chessKind!=5&&chessStoreage[x+1][y].chessKind!=5){
                    //检查 30
                    //    01
                    //    O2的情况，而且3不能为砖块
                    a[0]=x+1;
                    a[1]=y;
                    a[2]=x;
                    a[3]=y;
                }
                //
                if(chessStoreage[x][y].equals(chessStoreage[x+1][y+1])&&chessStoreage[x][y].equals(chessStoreage[x+1][y+2])&&chessStoreage[x+1][y].chessKind!=5&&chessStoreage[x][y].chessKind!=5){
                    //检查 03
                    //    20
                    //    1O的情况，而且3不能为砖块
                    a[0]=x;
                    a[1]=y;
                    a[2]=x+1;
                    a[3]=y;
                }
                if(chessStoreage[x][y+1].equals(chessStoreage[x+1][y])&&chessStoreage[x][y+1].equals(chessStoreage[x+1][y+2])&&chessStoreage[x+1][y+1].chessKind!=5&&chessStoreage[x][y+1].chessKind!=5){
                    //检查 10
                    //    03
                    //    20的情况，而且3不能为砖块
                    a[0]=x;
                    a[1]=y+1;
                    a[2]=x+1;
                    a[3]=y+1;
                }
                if(chessStoreage[x][y+2].equals(chessStoreage[x+1][y])&&chessStoreage[x][y+2].equals(chessStoreage[x+1][y+1])&&chessStoreage[x+1][y+2].chessKind!=5&&chessStoreage[x][y+2].chessKind!=5){
                    //检查 10
                    //    20
                    //    O3的情况，而且3不能为砖块
                    a[0]=x;
                    a[1]=y+2;
                    a[2]=x+1;
                    a[3]=y+2;
                }
            }
        }
        for (int x = 0; x < chessStoreage.length-3; x++) {
            for (int y = 0; y < chessStoreage[x].length; y++) {
                if(chessStoreage[x+3][y].equals(chessStoreage[x][y])&&chessStoreage[x+3][y].equals(chessStoreage[x+1][y])&&chessStoreage[x+2][y].chessKind!=5&&chessStoreage[x+3][y].chessKind!=5){
                    //检查 0030的情况而且3不能是砖块
                    a[0]=x+3;
                    a[1]=y;
                    a[2]=x+2;
                    a[3]=y;
                }
                if(chessStoreage[x][y].equals(chessStoreage[x+2][y])&&chessStoreage[x][y].equals(chessStoreage[x+3][y])&&chessStoreage[x+1][y].chessKind!=5&&chessStoreage[x][y].chessKind!=5){
                    //检查 0300的情况而且3不能s砖块
                    a[0]=x;
                    a[1]=y;
                    a[2]=x+1;
                    a[3]=y;
                }
            }
        }
        for (int x = 0; x < chessStoreage.length; x++) {
            for (int y = 0; y < chessStoreage[x].length-3; y++) {
                if(chessStoreage[x][y+3].equals(chessStoreage[x][y])&&chessStoreage[x][y+3].equals(chessStoreage[x][y+1])&&chessStoreage[x][y+2].chessKind!=5&&chessStoreage[x][y+3].chessKind!=5){
                    //检查 0
                    //    0
                    //    3
                    //    0的情况而且3不能是砖块
                    a[0]=x;
                    a[1]=y+3;
                    a[2]=x;
                    a[3]=y+2;
                }
                if(chessStoreage[x][y].equals(chessStoreage[x][y+2])&&chessStoreage[x][y].equals(chessStoreage[x][y+3])&&chessStoreage[x][y+1].chessKind!=5&&chessStoreage[x][y].chessKind!=5){
                    //检查 0
                    //    3
                    //    0
                    //    0的情况而且3不能s砖块
                    a[0]=x;
                    a[1]=y;
                    a[2]=x;
                    a[3]=y+1;
                }
            }
        }
        return a;
    }
}
