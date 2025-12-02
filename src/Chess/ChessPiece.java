package Chess;

import javax.swing.*;

public class ChessPiece {
    //0-hippo 1-fox 2-frog 3-eagle 4-chicken 5-block
    public int chessKind;
    //x varies from {0,1,2,3,4,5,6,7}*70 x->R, y too.
    public int x;
    public int y;
    public int temp;
    public static int baodi;//抽卡需要保底吧
    public static boolean combo=false;//为了让block更加连贯
    public int chesswidth=80;
    ImageIcon thisChessPic;
    String path=System.getProperty("user.dir")+"\\src\\Images\\";

    public ChessPiece(int x, int y, int Kind){
        this.x=x;
        this.y=y;
        this.chessKind=Kind;
        if (!combo){
            temp=(int)(Math.random()*20);
        } else temp=(int)(Math.random()*2.7);//20,2.7为我觉得比较合适的刷新概率
        //在随机数中如果等于1，那么设置为方块，并且不会出现在最顶端
        if (y!=70&&temp==1 | baodi>=50) {
            chessKind=5;
            combo=true;
            baodi=0;
        }   else {
            combo=false;
            baodi++;
        }
        switch (chessKind){
            case 0: {
                thisChessPic=new ImageIcon(path+"hippo.png");
                break;
            }
            case 1: {
                thisChessPic=new ImageIcon(path+"fox.png");
                break;
            }
            case 2: {
                thisChessPic=new ImageIcon(path+"frog.png");
                break;
            }
            case 3: {
                thisChessPic=new ImageIcon(path+"eagle.png");
                break;
            }
            case 4: {
                thisChessPic=new ImageIcon(path+"chicken.png");
                break;
            }
            case 5: {
                thisChessPic=new ImageIcon(path+"block.png");
            }
        }
    }
    public ChessPiece(int x, int y, int Kind,boolean tempp){
        //通过方法重构创造不是block的chess
        this.x=x;
        this.y=y;
        this.chessKind=Kind;

        switch (chessKind){
            case 0: {
                thisChessPic=new ImageIcon(path+"hippo.png");
                break;
            }
            case 1: {
                thisChessPic=new ImageIcon(path+"fox.png");
                break;
            }
            case 2: {
                thisChessPic=new ImageIcon(path+"frog.png");
                break;
            }
            case 3: {
                thisChessPic=new ImageIcon(path+"eagle.png");
                break;
            }
            case 4: {
                thisChessPic=new ImageIcon(path+"chicken.png");
                break;
            }
            case 5: {
                //由于导入的时候，仍然可能是block。
                thisChessPic=new ImageIcon(path+"block.png");
            }
        }
    }

    public int getChessKind() {
        return chessKind;
    }
    public void setChessKind(int chessKind) {
        this.chessKind = chessKind;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getChesswidth() {
        return chesswidth;
    }

    public void setChesswidth(int chesswidth) {
        this.chesswidth = chesswidth;
    }

    public ImageIcon getThisChessPic() {
        return thisChessPic;
    }
    public ImageIcon getThisActChessPic(){
        switch (this.chessKind){
            case 0:{
                return new ImageIcon(path+"hippoAct.png");
            }
            case 1:{
                return new ImageIcon(path+"foxAct.png");
            }
            case 2:{
                return new ImageIcon(path+"frogAct.png");
            }
            case 3:{
                return new ImageIcon(path+"eagleAct.png");
            }
            case 4:{
                return new ImageIcon(path+"chickenAct.png");
            }
            case 5:{
                return new ImageIcon(path+"blockAct.png");
            }
        }
        return new ImageIcon();
    }

    public void setThisChessPic(ImageIcon thisChessPic) {
        this.thisChessPic = thisChessPic;
    }
    //下面为专属block的方法，因为block不能共享消除逻辑
    public boolean equals(ChessPiece comparePiece){
        if (chessKind==5) return false;
        return comparePiece.chessKind==this.chessKind;
    }
    public ImageIcon canNotBeSelected() {
        return new ImageIcon(path+"blockNo.png");
    }
    public ImageIcon blocknormal(){
        return new ImageIcon(path+"block.png");
    }
}
