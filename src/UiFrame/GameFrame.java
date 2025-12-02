package UiFrame;

import Chess.ChessPiece;
import Logic.Logic;
import Sounds.Player;
import Storage.IOReader;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

public class GameFrame extends JFrame implements KeyListener, ActionListener, MouseListener {
    public String path = System.getProperty("user.dir") + "\\src\\Images\\";
    public String path1 = System.getProperty("user.dir")+"\\src\\Storage\\";
    ImageIcon icon = new ImageIcon(path + "icon.png");
    public static int port;
    public ServerSocket serverSocket =null;
    public Socket socket =null;
    public final boolean flag;
    public static server server;
    //结束主程序区，下面是变量的定义
    public int boxlength = 79;
    public int CHESSBOARDXCOUNT;
    public int CHESSBOARDYCOUNT;
    public int INITIALX = 70;
    public int INITIALY = 70;
    public int score = 0;
    public int function1left = 3;
    public int function2left = 3;
    public int function3left = 3;
    public int difficulty = 0;
    public int[] receive;
    public int countFlames;
    public int step = 0;
    public JLabel stepCount;
    public JLabel scoreCount;
    public JLabel functionBar;
    public JLabel backGroundLabel;
    public ImageIcon unDoMapPic = new ImageIcon(path + "unDoMap.png");
    public ImageIcon unDoMapSelectPic = new ImageIcon(path + "unDoMapSelect.png");
    public ImageIcon unDoMapActPic = new ImageIcon(path+"unDoMapAct.png");
    public ImageIcon bombPic = new ImageIcon(path + "bomb.png");
    public ImageIcon bombActPic = new ImageIcon(path + "bombAct.png");
    public ImageIcon bombClickedPic = new ImageIcon(path + "bombClicked.png");
    public ImageIcon smallBombPic = new ImageIcon(path + "smallbomb.png");
    public ImageIcon smallHammerPic = new ImageIcon(path + "smallHammer.png");
    public ImageIcon hammerPic = new ImageIcon(path + "hammer.png");
    public ImageIcon hammerActPic = new ImageIcon(path + "hammerAct.png");
    public ImageIcon hammerClickedPic = new ImageIcon(path + "hammerClicked.png");
    public ImageIcon boxPic = new ImageIcon(path + "box.png");
    public ImageIcon boxselectedPic = new ImageIcon(path + "boxSelected.png");
    public ImageIcon gameBackgroundPic = new ImageIcon(path + "gameBackground.png");
    public ImageIcon boxHalfSelectedPic = new ImageIcon(path + "boxHalfSelected.png");
    public ImageIcon confirmPic = new ImageIcon(path + "登录按钮.png");
    public ImageIcon confirmedPic = new ImageIcon(path + "登录按下.png");
    public ImageIcon functionBarPic = new ImageIcon(path + "FunctionBar.png");

    public JMenuItem replayItem = new JMenuItem("Replay");
    public JMenuItem infiniteItem = new JMenuItem("Get Infinite Items!!!");
    public JMenuItem exitItem = new JMenuItem("Exit");
    public JMenuItem saveItem = new JMenuItem("Save");
    public JMenuItem loadInItem = new JMenuItem("Load In");
    public JMenuItem eight2Item = new JMenuItem("8 * 8");
    public JMenuItem ten2Item = new JMenuItem("10 * 10");
    public JMenuItem selfChangeItem = new JMenuItem("Customization");
    public JMenuItem howItem = new JMenuItem("How to play?");

    public JButton[][] boxlabel = new JButton[CHESSBOARDXCOUNT][CHESSBOARDYCOUNT];
    public JLabel[][] chessJLabel = new JLabel[CHESSBOARDXCOUNT][CHESSBOARDYCOUNT];
    public JLabel protectWhenNotUpdated = new JLabel();
    public JButton unDoMap = new JButton();
    public JButton bombButton = new JButton();
    public JButton hammerButton = new JButton();
    public JButton confirmButton = new JButton();
    public JButton updateButton = new JButton();
    public JButton hintButton = new JButton();
    public JButton regretButton = new JButton();
    public JRadioButton autoMode = new JRadioButton("Auto Mode");
    public static JButton onlineButton = new JButton("同步棋盘");
    public ChessPiece[][] chessStorage = new ChessPiece[CHESSBOARDXCOUNT][CHESSBOARDYCOUNT];
    public int[][]temp;
    public boolean[][] boxclicked = new boolean[CHESSBOARDXCOUNT][CHESSBOARDYCOUNT];
    public boolean[][] isTheBoxNew = new boolean[CHESSBOARDXCOUNT][CHESSBOARDYCOUNT];
    public boolean[][] ifNeedDropping = new boolean[CHESSBOARDXCOUNT][CHESSBOARDYCOUNT];
    public boolean bomb=false;
    public boolean hammer=false;
    public boolean winOrNot=true;
    //第一个存储个数 第二组:第一个存储x坐标 第二个存储y坐标
    public int[] clicked1 = new int[2];
    public int[] clicked2 = new int[2];
    public int clickedNumber = 0;
    boolean ziYici=true;

    //在线模式开局(作为服务端），需要传个端口号进来
    public GameFrame(int port,int dif) throws IOException {
        this.flag=true;
        this.difficulty=dif;
        this.serverSocket = new ServerSocket(port);
        GameFrame gf = new GameFrame(8,8,difficulty,flag);
        gf.RefreshGame(true);
        this.server = new server(serverSocket,gf);
        new Thread(server).start();
    }
    //在线模式开局（作为客户端），需要传个端口号和IP地址进来
    public GameFrame(int port,String IP,int dif) throws IOException {
        this.flag=false;
        this.difficulty=dif;
        GameFrame gf = new GameFrame(8,8,difficulty,flag);
        gf.RefreshGame(true);
        this.socket = new Socket(IP,port);
        new Thread(new client(socket,gf)).start();
    }
    public GameFrame(boolean flag) {
    //无参构造，默认8*8开局*已弃用该方法*
        this.flag = flag;
    }
    public GameFrame(int[][] data){
        this.flag=false;
        //导入数据开局*作用在本地读取存档时*
        System.out.println("下面是导入的数据：");
        for (int[] datum : data) {
            for (int j : datum) {
                System.out.print(j + " ");
            }
            System.out.println();
        }
        CHESSBOARDXCOUNT=data[0][0];
        CHESSBOARDYCOUNT=data[0][1];
        System.out.println("A new game with size:"+CHESSBOARDXCOUNT+" * "+CHESSBOARDYCOUNT);

        score=data[CHESSBOARDXCOUNT+1][1];
        function1left=data[CHESSBOARDXCOUNT+1][2];
        function2left=data[CHESSBOARDXCOUNT+1][3];
        function3left=data[CHESSBOARDXCOUNT+1][4];
        step=data[CHESSBOARDXCOUNT+1][5];
        difficulty=data[CHESSBOARDXCOUNT+1][6];
        boxlabel = new JButton[CHESSBOARDXCOUNT][CHESSBOARDYCOUNT];
        chessJLabel = new JLabel[CHESSBOARDXCOUNT][CHESSBOARDYCOUNT];
        chessStorage = new ChessPiece[CHESSBOARDXCOUNT][CHESSBOARDYCOUNT];
        boxclicked = new boolean[CHESSBOARDXCOUNT][CHESSBOARDYCOUNT];
        isTheBoxNew = new boolean[CHESSBOARDXCOUNT][CHESSBOARDYCOUNT];
        ifNeedDropping = new boolean[CHESSBOARDXCOUNT][CHESSBOARDYCOUNT];
        this.initFrame();
        this.initBar();
        this.initFunctions(true);
        this.setVisible(true);
        protectWhenNotUpdated.setBounds(70, 70, 79 * CHESSBOARDXCOUNT, 79 * CHESSBOARDYCOUNT);
        stepCount.setText("Steps: "+ step+"/"+Math.ceil(40/this.difficulty));
        scoreCount.setText("Scores: " +score*10+"/"+ Math.ceil(100*Math.pow(1.2,this.difficulty)));
        functionBar.setText("Difficulty:" + this.difficulty);
        for (int i = 0; i < CHESSBOARDXCOUNT; i++) {
            for (int i1 = 0; i1 < CHESSBOARDYCOUNT; i1++) {
                boxlabel[i][i1] = new JButton();
                boxlabel[i][i1].setIcon(boxPic);
                boxlabel[i][i1].setBorderPainted(false);
                boxlabel[i][i1].setContentAreaFilled(false);
                boxlabel[i][i1].addMouseListener(this);
                boxlabel[i][i1].setOpaque(false);
                boxlabel[i][i1].setBounds(INITIALX + boxlength * i, INITIALY + boxlength * i1, boxlength, boxlength);
                this.getLayeredPane().add(boxlabel[i][i1], -100);
                boxclicked[i][i1] = false;
                //set the ini chess(运用到了data的数据,同时不能够随机生成方块)
                chessStorage[i][i1] = new ChessPiece(INITIALX + boxlength * i, INITIALY + boxlength * i1, data[i+1][i1],true);
                chessJLabel[i][i1] = new JLabel(chessStorage[i][i1].getThisChessPic());
                chessJLabel[i][i1].setBounds(chessStorage[i][i1].getX(), chessStorage[i][i1].getY(), boxlength, boxlength);
                chessJLabel[i][i1].setOpaque(false);
                this.getLayeredPane().add(chessJLabel[i][i1], 0);
                isTheBoxNew[i][i1]=false;
                ifNeedDropping[i][i1]=false;
            }
        }
        //结束生成棋盘,开始消除已经存在的组合。
        while (Logic.checkIfCanUpdate(chessStorage)) {
            updateChessboard(true);
            dropChessboard(true);
        }
        getBackGround();
    }
    public GameFrame(int X, int Y,int difficulty,boolean flag) {
        //初始改变棋盘大小的构造方法
        this.flag=flag;
        this.CHESSBOARDXCOUNT = X;
        this.CHESSBOARDYCOUNT = Y;
        this.difficulty=difficulty;
        System.out.println("A new game with size:"+CHESSBOARDXCOUNT+" * "+CHESSBOARDYCOUNT);
    }
    //在需要更新整个画面的时候使用。对Boolean的解释：
    // false：已有数据在对象里面，就不用再initGame（随机生成棋子）
    //true:新建对象的时候需要生成棋子
    public void RefreshGame(boolean b){
        this.initFrame();
        this.initBar();
        this.initFunctions(b);
        this.setVisible(true);
        if(b){
            this.initGame();
        }
    }
    public void UpdateGame(int[][]data){
        //在线模式下(已拓展到本地读档，使用接收到的数据更新原GameFrame
        System.out.println("下面是导入的数据：");
        for (int[] datum : data) {
            for (int j : datum) {
                System.out.print(j + " ");
            }
            System.out.println();
        }
        CHESSBOARDXCOUNT=data[0][0];
        CHESSBOARDYCOUNT=data[0][1];
        System.out.println("A new game with size:"+CHESSBOARDXCOUNT+" * "+CHESSBOARDYCOUNT);
        this.score=data[CHESSBOARDXCOUNT+1][0];
        this.function1left=data[CHESSBOARDXCOUNT+1][1];
        this.function2left=data[CHESSBOARDXCOUNT+1][2];
        this.function3left=data[CHESSBOARDXCOUNT+1][3];
        this.step=data[CHESSBOARDXCOUNT+1][4];
        this.difficulty=data[CHESSBOARDXCOUNT+1][5];
        this.boxlabel = new JButton[CHESSBOARDXCOUNT][CHESSBOARDYCOUNT];
        this.chessJLabel = new JLabel[CHESSBOARDXCOUNT][CHESSBOARDYCOUNT];
        this.chessStorage = new ChessPiece[CHESSBOARDXCOUNT][CHESSBOARDYCOUNT];
        this.boxclicked = new boolean[CHESSBOARDXCOUNT][CHESSBOARDYCOUNT];
        this.isTheBoxNew = new boolean[CHESSBOARDXCOUNT][CHESSBOARDYCOUNT];
        this.ifNeedDropping = new boolean[CHESSBOARDXCOUNT][CHESSBOARDYCOUNT];
        this.protectWhenNotUpdated.setBounds(70, 70, 79 * CHESSBOARDXCOUNT, 79 * CHESSBOARDYCOUNT);
        this.stepCount.setText("Steps: "+ step+"/"+Math.ceil(40/this.difficulty));
        this.scoreCount.setText("Scores: " +score*10+"/"+Math.ceil(100*Math.pow(1.2,this.difficulty)));
        this.functionBar.setText("Difficulty:" + this.difficulty);
        for (int i = 0; i < CHESSBOARDXCOUNT; i++) {
            for (int i1 = 0; i1 < CHESSBOARDYCOUNT; i1++) {
                this.boxlabel[i][i1] = new JButton();
                this.boxlabel[i][i1].setIcon(boxPic);
                this.boxlabel[i][i1].setBorderPainted(false);
                this.boxlabel[i][i1].setContentAreaFilled(false);
                this.boxlabel[i][i1].addMouseListener(this);
                this.boxlabel[i][i1].setOpaque(false);
                this.boxlabel[i][i1].setBounds(INITIALX + boxlength * i, INITIALY + boxlength * i1, boxlength, boxlength);
                this.getLayeredPane().add(boxlabel[i][i1], -100);
                this.boxclicked[i][i1] = false;
                //set the ini chess(运用到了data的数据,同时不能够随机生成方块)
                this.chessStorage[i][i1] = new ChessPiece(INITIALX + boxlength * i, INITIALY + boxlength * i1, data[i+1][i1],true);
                this.chessJLabel[i][i1] = new JLabel(chessStorage[i][i1].getThisChessPic());
                this.chessJLabel[i][i1].setBounds(chessStorage[i][i1].getX(), chessStorage[i][i1].getY(), boxlength, boxlength);
                this.chessJLabel[i][i1].setOpaque(false);
                this.getLayeredPane().add(this.chessJLabel[i][i1], 0);
                this.isTheBoxNew[i][i1]=false;
                this.ifNeedDropping[i][i1]=false;
            }
        }
        //结束生成棋盘,开始消除已经存在的组合。
        while (Logic.checkIfCanUpdate(chessStorage)) {
            updateChessboard(true);
            dropChessboard(true);
        }
        getBackGround();
    }

    public void initFrame() {
        //initFrame is the starting frame, creating a basic frame with nothing on it.
        this.setSize(Math.max(790 + (CHESSBOARDXCOUNT - 8) * 79,730), 900 + (CHESSBOARDYCOUNT - 8) * 79);
        this.setTitle("3-Match Game");
        this.setDefaultCloseOperation(3);
        //使窗口居中
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(false);
        this.setLayout(null);
        this.setResizable(false);
    }

    public void initBar() {
        // the bar on the top
        this.setIconImage(icon.getImage());
        JMenuBar MenuBar = new JMenuBar();
        JMenu functionMenu = new JMenu("Functions");
        JMenu storageMenu = new JMenu("Storage");
        JMenu chessboardMenu = new JMenu("Chessboard-Size");
        JMenu aboutMenu = new JMenu("About");
        //add items into menu;
        functionMenu.add(replayItem);
        functionMenu.add(infiniteItem);
        functionMenu.add(exitItem);
        storageMenu.add(saveItem);
        storageMenu.add(loadInItem);
        chessboardMenu.add(eight2Item);
        chessboardMenu.add(ten2Item);
        chessboardMenu.add(selfChangeItem);
        aboutMenu.add(howItem);
        MenuBar.add(functionMenu);
        MenuBar.add(storageMenu);
        MenuBar.add(chessboardMenu);
        MenuBar.add(aboutMenu);
        replayItem.addActionListener(this);
        infiniteItem.addActionListener(this);
        exitItem.addActionListener(this);
        saveItem.addActionListener(this);
        loadInItem.addActionListener(this);
        eight2Item.addActionListener(this);
        ten2Item.addActionListener(this);
        selfChangeItem.addActionListener(this);
        howItem.addActionListener(this);
        this.setJMenuBar(MenuBar);
    }

    public void initFunctions(boolean b) {
        Font fout= new Font("微软雅黑",Font.BOLD,20);
        //放置一个小框显示步数
        stepCount = new JLabel("Steps:" + this.step+"/"+Math.ceil(40/this.difficulty));
        stepCount.setBounds(70, 23, 200, 20);
        stepCount.setFont(fout);
        this.getLayeredPane().add(stepCount, 1);
        //放置一个小框显示分数
        scoreCount = new JLabel("Scores:" + this.score*10+"/"+Math.ceil(100*Math.pow(1.2,this.difficulty)));
        scoreCount.setFont(fout);
        scoreCount.setBounds(70, 43, 200, 20);
        this.getLayeredPane().add(scoreCount, 1);
        //放置一个小框显示难度
        functionBar = new JLabel("Difficulty:" + this.difficulty);
        functionBar.setFont(fout);
        functionBar.setBounds(370, 23, 200, 20);
        this.getLayeredPane().add(functionBar, 1);
        //放置一个小框显示当前服务器端口&IP
        JLabel portLabel = new JLabel("Port:"+port);
        portLabel.setFont(fout);
        portLabel.setBounds(370, 43, 200, 20);
        this.getLayeredPane().add(portLabel, 1);
        autoMode.setBounds(570 +  (CHESSBOARDXCOUNT - 8) * 79,53,140,18);
        autoMode.setBorderPainted(false);
        autoMode.setContentAreaFilled(false);
        autoMode.setFont(fout);
        autoMode.addActionListener(e -> {
        });
        this.getLayeredPane().add(autoMode,1);
        confirmButton.setBounds(437, 735 + (CHESSBOARDYCOUNT - 8) * 79, 124, 40);
        confirmButton.setBorderPainted(true);
        confirmButton.setText("交换");
        confirmButton.setContentAreaFilled(true);
        confirmButton.setOpaque(false);
        this.getLayeredPane().add(confirmButton,1);

        updateButton.setBounds(437, 785 + (CHESSBOARDYCOUNT - 8) * 79, 124, 40);
        updateButton.setBorderPainted(true);
        updateButton.setContentAreaFilled(true);
        updateButton.setText("更新");
        updateButton.setOpaque(false);
        this.getLayeredPane().add(updateButton,1);
        //道具们
        unDoMap.setBounds(75, 735 + (CHESSBOARDYCOUNT - 8) * 79, 113, 109);
        unDoMap.setIcon(unDoMapPic);
        unDoMap.setBorderPainted(false);
        unDoMap.setContentAreaFilled(false);
        unDoMap.setOpaque(false);
        this.getLayeredPane().add(unDoMap,1);
        bombButton.setBounds(193, 722 + (CHESSBOARDYCOUNT - 8) * 79, 105, 117);
        bombButton.setIcon(bombPic);
        bombButton.setBorderPainted(false);
        bombButton.setContentAreaFilled(false);
        bombButton.addActionListener(e -> {
            System.out.println("点击了炸弹");
            if (function2left > 0) {
                bomb = true;
                function2left--;
                Player.fireSound();
            } else {
                JOptionPane.showMessageDialog(null, "您的炸弹已经用完了");
            }
        });
        bombButton.setOpaque(false);
        this.getLayeredPane().add(bombButton,1);
        hammerButton.setBounds(312, 735 + (CHESSBOARDYCOUNT - 8) * 79, 105, 95);
        hammerButton.setIcon(hammerPic);
        hammerButton.setBorderPainted(false);
        hammerButton.setContentAreaFilled(false);
        hammerButton.addActionListener(e -> {
            System.out.println("锤子");
            if (function3left > 0) {
                hammer = true;
                function3left--;
            } else {
                JOptionPane.showMessageDialog(null, "您的锤子已经用完了");
            }
        });
        hammerButton.setOpaque(false);
        this.getLayeredPane().add(hammerButton,1);

        hintButton.setBounds(571,735+ (CHESSBOARDYCOUNT - 8) * 79,124,40);
        hintButton.setBorderPainted(true);
        hintButton.setContentAreaFilled(true);
        hintButton.setText("提示");
        hintButton.addActionListener(e -> {
            int[]temp=Logic.checkIfPlayable(chessStorage);
            if(Logic.checkIfHaveNull(chessStorage)){
                JOptionPane.showMessageDialog(null, "请先更新棋盘","出错啦！",JOptionPane.WARNING_MESSAGE);
            }
            if (clickedNumber==1){
                unSelect(clicked1[0],clicked1[1]);
            } else if(clickedNumber==2){
                unSelect(clicked2[0],clicked2[1]);
                unSelect(clicked1[0],clicked1[1]);
            }
            select(temp[0],temp[1]);
            select(temp[2],temp[3]);

        });
        this.getLayeredPane().add(hintButton,1);
        regretButton.setBounds(570 +  (CHESSBOARDXCOUNT - 8) * 79,23,124,30);
        regretButton.setBorderPainted(true);
        regretButton.setContentAreaFilled(true);
        regretButton.setText("悔棋");
        regretButton.addActionListener(e -> {
            try {
                    this.getLayeredPane().removeAll();
                    this.UpdateGame(IOReader.InPut("regret.txt"));
                    this.RefreshGame(false);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Failed to load in! Better check if you have saved");
            }

        });
        this.getLayeredPane().add(regretButton,1);
        onlineButton.setBounds(571,785+ (CHESSBOARDYCOUNT - 8) * 79,124,40);
        onlineButton.setBorderPainted(true);
        onlineButton.setContentAreaFilled(true);
        onlineButton.addActionListener(l->{
            System.out.println("点击了同步棋盘");
            if (!this.flag) {
                int[][] saveTemp = new int[CHESSBOARDXCOUNT][CHESSBOARDYCOUNT];
                for (int i = 0; i < CHESSBOARDXCOUNT; i++) {
                    for (int j = 0; j < CHESSBOARDYCOUNT; j++) {
                        saveTemp[i][j] = chessStorage[i][j].getChessKind();
                    }
                }
                try {
                    IOReader.OutPut("SendFromClient",saveTemp, score, function1left, function2left, function3left, step, difficulty);
                    JOptionPane.showMessageDialog(this, "Saved successfully!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Failed to save, better check the integrity of the Game%n"+ex);
                }
                new Thread(new Client_send(socket)).start();
            } else {
                int[][] saveTemp = new int[CHESSBOARDXCOUNT][CHESSBOARDYCOUNT];
                for (int i = 0; i < CHESSBOARDXCOUNT; i++) {
                    for (int j = 0; j < CHESSBOARDYCOUNT; j++) {
                        saveTemp[i][j] = chessStorage[i][j].getChessKind();
                    }
                }
                try {
                    IOReader.OutPut("SendFromServer",saveTemp, score, function1left, function2left, function3left, step, difficulty);
                    JOptionPane.showMessageDialog(this, "Saved successfully!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Failed to save, better check the integrity of the Game%n"+ex);
                }
                    new Thread(new Server_send(1)).start();
            }
        });
        if(b){
            hammerButton.addMouseListener(this);
            bombButton.addMouseListener(this);
            unDoMap.addMouseListener(this);
            confirmButton.addMouseListener(this);
            updateButton.addMouseListener(this);
            onlineButton.addMouseListener(this);
            hintButton.addMouseListener(this);
            this.repaint();
        }
        this.getLayeredPane().add(onlineButton,1);
        protectWhenNotUpdated.setBounds(70, 70, 79 * CHESSBOARDXCOUNT, 79 * CHESSBOARDYCOUNT);
        updateButton.setOpaque(true);
        //由于在未更新表盘的时候，点击cheache空白区域会产生无法解决的bug,这个为这个bug的补丁，即为强制顶层一个图层阻挡点击。
        this.getLayeredPane().add(protectWhenNotUpdated, 100);
    }

    //主程序逻辑
    public void initGame() {
        //生成初始棋盘,同时初始化所有数组，因为刚开始由于chessboard不存在，数组并没有被定义(长度为00)
        boxlabel = new JButton[CHESSBOARDXCOUNT][CHESSBOARDYCOUNT];
        chessJLabel = new JLabel[CHESSBOARDXCOUNT][CHESSBOARDYCOUNT];
        chessStorage = new ChessPiece[CHESSBOARDXCOUNT][CHESSBOARDYCOUNT];
        boxclicked = new boolean[CHESSBOARDXCOUNT][CHESSBOARDYCOUNT];
        isTheBoxNew = new boolean[CHESSBOARDXCOUNT][CHESSBOARDYCOUNT];
        ifNeedDropping = new boolean[CHESSBOARDXCOUNT][CHESSBOARDYCOUNT];
        protectWhenNotUpdated.setBounds(70, 70, 79 * CHESSBOARDXCOUNT, 79 * CHESSBOARDYCOUNT);
        for (int i = 0; i < CHESSBOARDXCOUNT; i++) {
            for (int i1 = 0; i1 < CHESSBOARDYCOUNT; i1++) {
                //paint the background box
                boxlabel[i][i1] = new JButton();
                boxlabel[i][i1].setIcon(boxPic);
                boxlabel[i][i1].setBorderPainted(false);
                boxlabel[i][i1].setContentAreaFilled(false);
                boxlabel[i][i1].addMouseListener(this);
                boxlabel[i][i1].setOpaque(false);
                boxlabel[i][i1].setBounds(INITIALX + boxlength * i, INITIALY + boxlength * i1, boxlength, boxlength);
                this.getLayeredPane().add(boxlabel[i][i1], -99);
                boxclicked[i][i1] = false;
                //set the ini chess
                chessStorage[i][i1] = new ChessPiece(INITIALX + boxlength * i, INITIALY + boxlength * i1, (int) (Math.random() * 5));
                chessJLabel[i][i1] = new JLabel(chessStorage[i][i1].getThisChessPic());
                chessJLabel[i][i1].setBounds(chessStorage[i][i1].getX(), chessStorage[i][i1].getY(), boxlength, boxlength);
                chessJLabel[i][i1].setOpaque(false);
                this.getLayeredPane().add(chessJLabel[i][i1], 1);
                isTheBoxNew[i][i1]=false;
                ifNeedDropping[i][i1]=false;
            }
        }
        //结束生成棋盘,开始消除已经存在的组合。
        while (Logic.checkIfCanUpdate(chessStorage)) {
            updateChessboard(true);
            dropChessboard(true);
        }
        //getBackGround的位置不能移动，必须放在代码的最后面！！！（原理未知）
        getBackGround();
    }

    public void updateChessboard(boolean where) {
        //对update的解释：消除成3的方块
        //对where的解释：由于开局需要消除已经成三的方块，但是开局又不能计分，所以当where为true的时候，说明是开局调用的，不计分。如果是false，则需要计分；
        int[][] updateReceiver = Logic.checkChessboard(chessStorage);
        for (int i = 0; i < CHESSBOARDXCOUNT; i++) {
            for (int i1 = 0; i1 < CHESSBOARDYCOUNT; i1++) {
                if (updateReceiver[i][i1] != 0 && chessStorage[i][i1].chessKind != 5) {
                    chessJLabel[i][i1].setIcon(new ImageIcon());
                    chessStorage[i][i1] = null;
                    if(!where) {
                        score+=updateReceiver[i][i1];
//                        if(score*10>= Math.ceil(Math.pow(1.2,this.difficulty)*100)){
//                            //JOptionPane.showMessageDialog(this, "恭喜您，您已经通关了！");
//                            this.dispose();
//                            JDialog dialog = getDialog();
//                            dialog.setLocationRelativeTo(null);
//                            dialog.setVisible(true);
//                        }
                        scoreCount.setText("Scores:" + score*10+"/"+Math.ceil(100*Math.pow(1.2,this.difficulty)));
                    }
                }
            }
        }
    }

    private JDialog getDialog() {
        JDialog dialog = new JDialog();
        dialog.setIconImage(icon.getImage());
        dialog.setTitle("3-Match Game");
        dialog.setResizable(false);
        dialog.setTitle("过关啦！");
        dialog.setSize(400,300);
        dialog.setDefaultCloseOperation(2);
        //to give the user a choice whether to continue the game or not
        JLabel label = new JLabel("恭喜您，您通过了此关！");
        label.setFont(new Font("微软雅黑",Font.BOLD,25));
        label.setBounds(50,50,400,60);
        JButton button = new JButton("继续游戏(难度+1)");
        button.setBorderPainted(true);
        button.setContentAreaFilled(true);
        button.setBounds(45,150,160,20);
        button.addActionListener(e -> {
            dialog.dispose();
            this.dispose();
            GameFrame gf = new GameFrame(this.CHESSBOARDXCOUNT,this.CHESSBOARDYCOUNT,(int)(this.difficulty+1),this.flag);
            gf.RefreshGame(true);
        });
        JButton button1 = new JButton("返回开始界面");
        button1.setBounds(215,150,120,20);
        button1.addActionListener(e -> {
            dialog.dispose();
            this.dispose();
            new StartFrame();
        });
        dialog.getLayeredPane().add(label,1);
        dialog.getLayeredPane().add(button,1);
        dialog.getLayeredPane().add(button1,1);
        return dialog;
    }

    public void dropChessboard(boolean where) {
        //使得被消除的棋盘掉落
        protectWhenNotUpdated.removeMouseListener(this);
        boolean temp;
        do {
            temp = true;
            for (int j = CHESSBOARDYCOUNT - 2; j > -1; j--) {
                for (int i = 0; i < CHESSBOARDXCOUNT; i++) {
                    //如果此处棋子存在
                    if (chessStorage[i][j] != null && chessStorage[i][j + 1] == null && chessStorage[i][j].chessKind != 5) {
                        chessStorage[i][j + 1] = chessStorage[i][j];
                        chessStorage[i][j + 1].y = chessStorage[i][j + 1].y + boxlength;
                        chessStorage[i][j] = null;
                        if (!where) ifNeedDropping[i][j+1]=true;
                        temp = false;
                    }
                }
            }
            System.out.println("掉落");
            if (temp) {
                System.out.println("掉落结束");
                break;
            }
        } while (true);
            fillTheNull(where);
            refresh(where);
            receive=Logic.checkIfPlayable(chessStorage);
            if(score*10>= Math.ceil(Math.pow(1.2,this.difficulty)*100)){
                this.dispose();
                if (winOrNot) {
                    winOrNot=false;
                    JDialog dialog = getDialog();
                    dialog.setLocationRelativeTo(null);
                    dialog.setVisible(true);
                }
            }
            if(!Logic.checkIfCanUpdate(chessStorage)&&receive[2]==-1){
                //证明无解
                JOptionPane.showMessageDialog(this, "无解，请使用道具改变棋盘");
            }else {
                System.out.println("第一个棋子x:"+receive[0]+",y:"+receive[1]+",第二个棋子x:"+receive[2]+",y:"+receive[3]);
            }
        if (autoMode.isSelected()){
            int[] flames = {0};
            Timer timerForAutoMode = new Timer(0, eeee -> {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                flames[0]++;
                if (flames[0] == 500) {
                    if (Logic.checkIfHaveNull(chessStorage)) {
                        System.out.println("掉！");
                        dropChessboard(false);
                    } else {
                        System.out.println("不掉！");
                        updateChessboard(false);
                        if (!Logic.checkIfCanUpdate(chessStorage) && this.step == Math.ceil(40 / this.difficulty)) {
                            //未能在规定步数内达到指定分数
                            JOptionPane.showMessageDialog(this, "未能在规定步数内达到指定分数，将返回开始界面");
                            this.setVisible(false);
                            new StartFrame();
                        }
                    }
                }
                if (flames[0]==501)  {
                    if (Logic.checkIfHaveNull(chessStorage)){
                        dropChessboard(false);
                    }
                    ((javax.swing.Timer)eeee.getSource()).stop();
                }
            });
            timerForAutoMode.start();
        }

    }

    public void fillTheNull(boolean where) {
        for (int i = 0; i < CHESSBOARDXCOUNT; i++) {
            for (int i1 = 0; i1 < CHESSBOARDYCOUNT; i1++) {
                if (chessStorage[i][i1] == null) {
                    chessStorage[i][i1] = new ChessPiece(INITIALX + i * boxlength, INITIALY + i1 * boxlength, (int) (Math.random() * 5), true);
                    if (!where) isTheBoxNew[i][i1] = true;
                }
            }
        }
    }

    public void refresh(boolean where) {
       //where判断其触发位置，通过where激发不同的指令
        if (where) {
           for (int i = 0; i < CHESSBOARDXCOUNT; i++) {
               for (int i1 = 0; i1 < CHESSBOARDYCOUNT; i1++) {
                   chessJLabel[i][i1].setIcon(chessStorage[i][i1].getThisChessPic());
                   boxlabel[i][i1].validate();
                   ifNeedDropping[i][i1] = false;
               }
           }
       }else {
            //新下落
           for (int i = 0; i < CHESSBOARDXCOUNT; i++) {
               for (int i1 = 0; i1 < CHESSBOARDYCOUNT; i1++) {
                   if (chessStorage[i][i1]!=null&!isTheBoxNew[i][i1]){
                       chessJLabel[i][i1].setIcon(chessStorage[i][i1].getThisChessPic());
                   }else chessJLabel[i][i1].setIcon(null);
                   boxlabel[i][i1].validate();
                   if (isTheBoxNew[i][i1]) chessJLabel[i][i1].setLocation(chessJLabel[i][i1].getX(), -9);
                   if (ifNeedDropping[i][i1]) chessJLabel[i][i1].setLocation(chessJLabel[i][i1].getX(),chessJLabel[i][i1].getY()-79);
               }
           }

           javax.swing.Timer time = getDroppingAnimation();
            time.start();
       }
    }

    //true代表下一次点击的时候进行消除操作，false代表下一次点击的时候进行下移和生成新棋子操作;
    //以下代码均为输入模块
    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println(e.getSource().toString());
        int clX = -1;
        int clY = -1;
        for (int i = 0; i < CHESSBOARDXCOUNT; i++) {
            for (int j = 0; j < CHESSBOARDYCOUNT; j++) {
                if (e.getSource() == this.boxlabel[i][j]) {
                    clX = i;
                    clY = j;
                }
            }
        }
        //如果clx不等于-1，那么说明点击到了
        if (clX != -1) {
            if (Logic.checkIfHaveNull(chessStorage)) {
                JOptionPane.showMessageDialog(null, "请先更新棋盘","出错啦！",JOptionPane.WARNING_MESSAGE);
            } else {
                if (clickedNumber == 0) {
                    if (bomb){
                        //开炸代码
                        bomb(clX,clY);
                        bomb=false;
                    }else if(hammer){
                       hammer(clX,clY);
                       hammer=false;
                    }else if (chessStorage[clX][clY] != null && chessStorage[clX][clY].chessKind != 5) select(clX, clY);
                } else if (clickedNumber == 1) {
                    if ((clicked1[0] == clX & clicked1[1] == clY) | (clicked2[0] == clX & clicked2[1] == clY)) {
                        //点击到了被选择的一项
                        unSelect(clX, clY);
                    } else {
                        //点击到非选择的一项，判断是不是在其周围
                        if (Math.abs(clX - clicked1[0]) + Math.abs(clY - clicked1[1]) == 1 | Math.abs(clX - clicked2[0]) + Math.abs(clY - clicked2[1]) == 1) {
                            if (chessStorage[clX][clY].chessKind != 5) {
                                select(clX, clY);
                                if (autoMode.isSelected()) {
                                    System.out.println("调换位置");
                                    changePosition();
                                    if (Logic.checkIfCanUpdate(chessStorage)) {
                                        //交换有效
                                        Player.slideSound();
                                        javax.swing.Timer time = getChangePositionAnimation();
                                        time.start();
                                        int[] flames = {0};
                                        Timer timerForAutoMode = new Timer(0, eeee -> {
                                            try {
                                                Thread.sleep(1);
                                            } catch (InterruptedException ex) {
                                                throw new RuntimeException(ex);
                                            }
                                            flames[0]++;
                                            if (flames[0] == 500) {
                                                if (Logic.checkIfHaveNull(chessStorage)) {
                                                    System.out.println("掉！");
                                                    dropChessboard(false);
                                                } else {
                                                    System.out.println("不掉！");
                                                    updateChessboard(false);
                                                    if (!Logic.checkIfCanUpdate(chessStorage) && this.step == Math.ceil(40 / this.difficulty)) {
                                                        //未能在规定步数内达到指定分数
                                                        JOptionPane.showMessageDialog(this, "未能在规定步数内达到指定分数，将返回开始界面");
                                                        this.setVisible(false);
                                                        new StartFrame();
                                                    }
                                                }
                                            }
                                            if (flames[0]==501)  {
                                                if (Logic.checkIfHaveNull(chessStorage)){
                                                    dropChessboard(false);
                                                }
                                                ((javax.swing.Timer)eeee.getSource()).stop();
                                            }
                                        });
                                        timerForAutoMode.start();

                                    }else {
                                        changePosition();
                                        JOptionPane.showMessageDialog(this, "交换无效");
                                        clickedNumber = 0;
                                        boxlabel[clicked1[0]][clicked1[1]].setIcon(boxPic);
                                        boxlabel[clicked2[0]][clicked2[1]].setIcon(boxPic);
                                        //代码复用，重新打印棋盘和棋子
                                        unSelect(clicked1[0], clicked1[1]);
                                        unSelect(clicked2[0], clicked2[1]);
                                        clicked1[0] = -1;
                                        clicked1[1] = -1;
                                        clicked2[0] = -1;
                                        clicked2[1] = -1;
                                    }
                                }
                            }
                        } else {
                            //取消原来选择，改为新的选择
                            unSelect(clicked1[0], clicked1[1]);
                            select(clX, clY);
                        }
                    }
                } else if (clickedNumber == 2) {
                    if ((clicked1[0] == clX & clicked1[1] == clY) | (clicked2[0] == clX & clicked2[1] == clY)) {
                        //点击到了被选择的一项
                        unSelect(clX, clY);
                    }
                }
            }
            //点击的不是box
            //交换命令
        } else {
            if (e.getSource() == this.confirmButton && clickedNumber == 2) {
                //超长一段代码放在这里好难看
                System.out.println("调换位置");
                int[][] saveTemp = new int[CHESSBOARDXCOUNT][CHESSBOARDYCOUNT];
                for (int i = 0; i < CHESSBOARDXCOUNT; i++) {
                    for (int j = 0; j < CHESSBOARDYCOUNT; j++) {
                        saveTemp[i][j] = chessStorage[i][j].getChessKind();
                    }
                }
                try {
                    IOReader.OutPut("regret",saveTemp, score, function1left, function2left, function3left, step, difficulty);
                } catch (IOException ignored) {}
                changePosition();
                if (Logic.checkIfCanUpdate(chessStorage)) {
                    //交换有效
                    Player.slideSound();
                    javax.swing.Timer time = getChangePositionAnimation();
                    time.start();
                } else {
                    //交换无效
                    //再换回来（因为之前需要通过交换判断是否合理）
                    changePosition();
                    JOptionPane.showMessageDialog(this, "交换无效");
                    clickedNumber = 0;
                    boxlabel[clicked1[0]][clicked1[1]].setIcon(boxPic);
                    boxlabel[clicked2[0]][clicked2[1]].setIcon(boxPic);
                    //代码复用，重新打印棋盘和棋子
                    unSelect(clicked1[0], clicked1[1]);
                    unSelect(clicked2[0], clicked2[1]);
                    clicked1[0] = -1;
                    clicked1[1] = -1;
                    clicked2[0] = -1;
                    clicked2[1] = -1;
                }
            }//结束点击box逻辑
            else if (e.getSource() == this.updateButton) {
                if (Logic.checkIfHaveNull(chessStorage)) {
                    System.out.println("掉！");
                    dropChessboard(false);
                }else {
                    System.out.println("不掉！");
                    updateChessboard(false);
                    if(!Logic.checkIfCanUpdate(chessStorage)&&this.step==Math.ceil(40/this.difficulty)){
                        //未能在规定步数内达到指定分数
                        JOptionPane.showMessageDialog(this, "未能在规定步数内达到指定分数，将返回开始界面");
                        this.setVisible(false);
                        new StartFrame();
                    }
                }
            } else if (e.getSource() == this.protectWhenNotUpdated) {
                JOptionPane.showMessageDialog(null, "请先更新棋盘","出错啦！",JOptionPane.WARNING_MESSAGE);
            } else if (e.getSource() == this.unDoMap){
                if (function1left > 0) {
                    for (int i = 0; i < CHESSBOARDXCOUNT; i++) {
                        for (int i1 = 0; i1 < CHESSBOARDYCOUNT; i1++) {
                            if (chessStorage[i][i1].chessKind!=5) {
                                chessJLabel[i][i1].setIcon((new ImageIcon()));
                                chessStorage[i][i1]=null;
                            }
                        }
                    }
                    dropChessboard(false);
                    while (Logic.checkIfCanUpdate(chessStorage)) {
                        updateChessboard(true);
                        dropChessboard(true);
                    }
                    function1left--;
                } else {
                    JOptionPane.showMessageDialog(null, "你的重置地图道具已经用完");
                }
            }
        }//结束逻辑判断，bomb, hammer, hintButton集合在initFunction内了。unDoMap无法被集成。
    }


    private void bomb(int x,int y)  {
        Player.bombSound();
        int whichkind=((int) (Math.random() * 5));
        chessStorage[x][y].chessKind=whichkind;
        int loactionx=chessStorage[x][y].x;
        int loactiony=chessStorage[x][y].y;
        for (int a=-1 ; a<=1 ; a++){
            for (int b=-1 ; b<=1 ; b++){
                try {
                    System.out.println("processing:x="+(x+a)+" y:"+(y+b));
                    chessStorage[x+a][y+b]=new ChessPiece(loactionx,loactiony,whichkind,true);
                }catch (ArrayIndexOutOfBoundsException ignored){}
            }
            updateChessboard(true);
        }
    }
    private void hammer(int clX, int clY) {
        chessStorage[clX][clY]=null;
        chessJLabel[clX][clY].setIcon((new ImageIcon()));
        dropChessboard(false);
    }

    public void changePosition(){
        int tempx = chessStorage[clicked1[0]][clicked1[1]].x;
        int tempy = chessStorage[clicked1[0]][clicked1[1]].y;
        chessStorage[clicked1[0]][clicked1[1]].x = chessStorage[clicked2[0]][clicked2[1]].x;
        chessStorage[clicked1[0]][clicked1[1]].y = chessStorage[clicked2[0]][clicked2[1]].y;
        chessStorage[clicked2[0]][clicked2[1]].x = tempx;
        chessStorage[clicked2[0]][clicked2[1]].y = tempy;
        //对主程序棋子储存进行调换
        ChessPiece temp = chessStorage[clicked2[0]][clicked2[1]];
        chessStorage[clicked2[0]][clicked2[1]] = chessStorage[clicked1[0]][clicked1[1]];
        chessStorage[clicked1[0]][clicked1[1]] = temp;
    }

    //下面是点击到box时候的动画
    @Override
    public void mousePressed(MouseEvent e) {
        int clX = -1;
        int clY = -1;
        for (int i = 0; i < CHESSBOARDXCOUNT; i++) {
            for (int j = 0; j < CHESSBOARDYCOUNT; j++) {
                if (e.getSource() == this.boxlabel[i][j]) {
                    clX = i;
                    clY = j;
                }
            }
        }
        //如果clx不等于-1，那么说明点击到了
        if (clX != -1) {
            if (chessStorage[clX][clY] != null && chessStorage[clX][clY].chessKind == 5) {
                chessJLabel[clX][clY].setIcon(chessStorage[clX][clY].canNotBeSelected());
            } else {
                if (chessStorage[clX][clY] != null) halfSelect(clX, clY);
            }
        } else {
            //点击的不是box
            if (e.getSource() == confirmButton) {
                confirmButton.setIcon(confirmedPic);
            }
            if (e.getSource() == updateButton) {
                updateButton.setIcon(confirmedPic);
            }
            if (e.getSource()==bombButton){
                bombButton.setIcon(bombActPic);
            }
            if (e.getSource()==hammerButton){
                hammerButton.setIcon(hammerActPic);
            }
            if (e.getSource()==unDoMap){
                unDoMap.setIcon(unDoMapSelectPic);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int clX = -1;
        int clY = -1;
        for (int i = 0; i < CHESSBOARDXCOUNT; i++) {
            for (int j = 0; j < CHESSBOARDYCOUNT; j++) {
                if (e.getSource() == this.boxlabel[i][j]) {
                    clX = i;
                    clY = j;
                }
            }
        }
        //如果clx不等于-1，那么说明点击到了
        if (clX != -1) {
            if (chessStorage[clX][clY] != null && !boxclicked[clX][clY]) {
                if (chessStorage[clX][clY].chessKind == 5) {
                    chessJLabel[clX][clY].setIcon(chessStorage[clX][clY].blocknormal());
                } else {
                    unHalfSelect(clX, clY);
                }
            } else {
                boxlabel[clX][clY].setIcon(boxselectedPic);
                if (chessStorage[clX][clY] != null) reLoad(clX, clY);
            }
        } else {
            //没点击到box
            if (e.getSource() == confirmButton) confirmButton.setIcon(confirmPic);
            if (e.getSource() == updateButton) updateButton.setIcon(confirmPic);
            if (e.getSource()==bombButton){
                bombButton.setIcon(bombPic);
            }
            if (e.getSource()==hammerButton){
                hammerButton.setIcon(hammerPic);
            }
            if (e.getSource()==unDoMap){
                unDoMap.setIcon(unDoMapPic);
            }
        }
    }

    //绘制进入时候的动画
    @Override
    public void mouseEntered(MouseEvent e) {
        countFlames++;
        int clX = -1;
        int clY = -1;
        for (int i = 0; i < CHESSBOARDXCOUNT; i++) {
            for (int j = 0; j < CHESSBOARDYCOUNT; j++) {
                if (e.getSource() == this.boxlabel[i][j]) {
                    clX = i;
                    clY = j;
                }
            }
        }
        if (clX != -1) {
            if (!boxclicked[clX][clY] & chessStorage[clX][clY] != null) {
                if (bomb){
                    chessJLabel[clX][clY].setIcon(smallBombPic);
                }else if (hammer){
                    chessJLabel[clX][clY].setIcon(smallHammerPic);
                }else {
                    chessJLabel[clX][clY].setIcon(chessStorage[clX][clY].getThisActChessPic());
                }
            }
        }else {
            if (e.getSource()==bombButton){
                bombButton.setIcon(bombClickedPic);
            }
            if (e.getSource()==hammerButton){
                hammerButton.setIcon(hammerClickedPic);
            }
            if (e.getSource()==unDoMap){
                unDoMap.setIcon(unDoMapActPic);
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        int clX = -1;
        int clY = -1;
        for (int i = 0; i < CHESSBOARDXCOUNT; i++) {
            for (int j = 0; j < CHESSBOARDYCOUNT; j++) {
                if (e.getSource() == this.boxlabel[i][j]) {
                    clX = i;
                    clY = j;
                }
            }
        }
        if (clX != -1) {
            if (!boxclicked[clX][clY] & chessStorage[clX][clY] != null) {
                chessJLabel[clX][clY].setIcon(chessStorage[clX][clY].getThisChessPic());
            }
        }else {
            if (e.getSource()==bombButton){
                bombButton.setIcon(bombPic);
            }
            if (e.getSource()==hammerButton){
                hammerButton.setIcon(hammerPic);
            }
            if (e.getSource()==unDoMap){
                unDoMap.setIcon(unDoMapPic);
            }
        }
    }

    //下面为变化格子形状的四种操作
    public void select(int clX, int clY) {
        boxlabel[clX][clY].setIcon(boxselectedPic);
        boxclicked[clX][clY] = true;
        if (clickedNumber == 0) {
            clicked1[0] = clX;
            clicked1[1] = clY;
        } else {
            clicked2[0] = clX;
            clicked2[1] = clY;
        }
        clickedNumber++;
        System.out.println("点击box加一：" + clickedNumber);
        reLoad(clX, clY);
    }

    public void unSelect(int clX, int clY) {
        boxlabel[clX][clY].setIcon(boxPic);
        boxclicked[clX][clY] = false;
        if (clickedNumber == 1) {
            clicked1[0] = -1;
            clicked1[1] = -1;
        } else if (clickedNumber == 2) {
            if (clicked1[0] == clX & clicked1[1] == clY) {
                clicked1[0] = clicked2[0];
                clicked1[1] = clicked2[1];
            }
            clicked2[0] = -1;
            clicked2[1] = -1;
        }
        if (clickedNumber != 0) clickedNumber--;
        System.out.println("点击的方格减一，还剩：" + clickedNumber);
        reLoad(clX, clY);
    }

    public void halfSelect(int clX, int clY) {
        boxlabel[clX][clY].setIcon(boxHalfSelectedPic);
        reLoad(clX, clY);
    }

    public void unHalfSelect(int clX, int clY) {
        boxlabel[clX][clY].setIcon(boxPic);
        reLoad(clX, clY);
    }

    //重新绘制操作处的方格图像
    public void reLoad(int clX, int clY) {
        boxlabel[clX][clY].setBounds(INITIALX + boxlength * clX, INITIALY + boxlength * clY, boxlength, boxlength);
        chessJLabel[clX][clY].setIcon(chessStorage[clX][clY].getThisChessPic());
        chessJLabel[clX][clY].setBounds(chessStorage[clX][clY].getX(), chessStorage[clX][clY].getY(), boxlength, boxlength);
        this.getLayeredPane().add(chessJLabel[clX][clY], 0);
    }

    @Override
    // there is to catch the action on the bar;
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj == replayItem) {
            System.out.println("重新游戏");
            this.step = 0;
            this.setVisible(false);
            new StartFrame();
        } else if (obj == this.exitItem) {
            System.out.println("退出");
            this.setVisible(false);
            System.exit(0);
        } else if (obj == this.infiniteItem) {
            function1left=999999;
            function2left=999999;
            function3left=999999;
            JOptionPane.showMessageDialog(this, "恭喜！你获得了无限的道具！");
        } else if (obj == this.howItem) {
            System.out.println("外币八部");
            JOptionPane.showMessageDialog(this,
                    "三消游戏是一种益智类游戏，玩家需要在棋盘上交换相邻的棋子，使得三个或以上颜色相同的棋子连成一线，从而消除它们。" +
                            "游戏中还有各种道具，例如重置地图、炸弹、锤子等，可以帮助玩家更快地消除棋子。希望这份介绍能够帮助你更好地了解三消游戏。");
        } else if (obj == this.saveItem) {
            JDialog jDialog = new JDialog();
            setContentPane(jDialog.getContentPane());
            jDialog.setBounds(400,300,340,256);
            jDialog.setLocationRelativeTo(null);
            JLabel jl = new JLabel("存档名：");
            jDialog.setIconImage(icon.getImage());
            jDialog.setTitle("3-Match Game");
            jDialog.setResizable(false);
            jl.setBounds(52,33,54,15);
            jDialog.getLayeredPane().add(jl);
            JTextField jTextField = new JTextField(10);
            jTextField.setBounds(116,30,139,21);
            jDialog.getLayeredPane().add(jTextField);
            JButton jButton = new JButton("创建存档");
            jButton.setBounds(64,116,69,23);
            jButton.addActionListener(e1 -> {if (!Logic.checkIfHaveNull(chessStorage)) {
                jDialog.setVisible(false);
                System.out.println("保存");
                int[][] saveTemp = new int[CHESSBOARDXCOUNT][CHESSBOARDYCOUNT];
                for (int i = 0; i < CHESSBOARDXCOUNT; i++) {
                    for (int j = 0; j < CHESSBOARDYCOUNT; j++) {
                        saveTemp[i][j] = chessStorage[i][j].getChessKind();
                    }
                }
                try {
                    IOReader.OutPut(jTextField.getText(),saveTemp, score, function1left, function2left, function3left, step, difficulty);
                    JOptionPane.showMessageDialog(this, "Saved successfully!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Failed to save, better check the integrity of the Game");
                }
            }else JOptionPane.showMessageDialog(this, "Please update the chessboard first!");
            });
            jDialog.getLayeredPane().add(jButton);
            jDialog.setVisible(true);
        } else if (obj == this.loadInItem) {
            JDialog jDialog = new JDialog();
            setContentPane(jDialog.getContentPane());
            jDialog.setBounds(400,300,340,256);
            jDialog.setLocationRelativeTo(null);
            JLabel jl = new JLabel("存档名：");
            jl.setBounds(52,33,54,15);
            jDialog.getLayeredPane().add(jl);
            jDialog.setIconImage(icon.getImage());
            jDialog.setTitle("3-Match Game");
            jDialog.setResizable(false);
            JTextField jTextField = new JTextField(6);
            jTextField.setBounds(116,30,139,21);
            jDialog.getLayeredPane().add(jTextField);
            JButton jButton = new JButton("读取该存档");
            jButton.setBounds(54,116,99,23);
            jDialog.getLayeredPane().add(jButton);
            jButton.addActionListener(e1 -> {
                jDialog.setVisible(false);
                try {
                    if (IOReader.InPut(jTextField.getText())!=null) {
                        this.getLayeredPane().removeAll();
                        this.UpdateGame(IOReader.InPut(jTextField.getText()));
                        this.RefreshGame(false);
                    }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Failed to load in! Better check if you have saved");
            }
            });
            jDialog.setVisible(true);
        } else if (obj == this.eight2Item) {
            if (CHESSBOARDXCOUNT == 8 && CHESSBOARDYCOUNT == 8) {
                JOptionPane.showMessageDialog(this, "Already 8 * 8");
            } else {
                this.setVisible(false);
                GameFrame gf = new GameFrame(8,8,difficulty,false);
                gf.RefreshGame(true);
            }
        } else if (obj == this.ten2Item) {
            if (CHESSBOARDXCOUNT == 10 && CHESSBOARDYCOUNT == 10) {
                JOptionPane.showMessageDialog(this, "Already 10 * 10");
            } else {
                this.setVisible(false);
                GameFrame gf = new GameFrame(10,10,difficulty,false);
                gf.RefreshGame(true);
            }
        } else if (obj == this.selfChangeItem) {
            this.setVisible(false);
            new ChangeBoxFrame();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyPressed(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
    public void getBackGround(){
        functionBar = new JLabel(functionBarPic);
        functionBar.setBounds(65,715+(CHESSBOARDYCOUNT - 8) * 79,functionBarPic.getIconWidth(),functionBarPic.getIconHeight());
        functionBar.setOpaque(true);
        this.getLayeredPane().add(functionBar,-10);
        backGroundLabel = new JLabel();
        backGroundLabel.setIcon(gameBackgroundPic);
        backGroundLabel.addMouseListener(this);
        backGroundLabel.setOpaque(false);
        backGroundLabel.setBounds(0,20,gameBackgroundPic.getIconWidth(),gameBackgroundPic.getIconHeight());
        this.getLayeredPane().add(backGroundLabel, -100);
    }
    //下面为动画模块
    private javax.swing.Timer getChangePositionAnimation() {
        int movekind1;
        if (clicked1[0]==clicked2[0]+1){
            movekind1=1;
        }else if (clicked1[0]+1==clicked2[0]){
            movekind1=3;
        }else if (clicked1[1]==clicked2[1]+1){
            movekind1=5;
        }else{
            movekind1=2;
        }
        final int[] indexf = {0};
        javax.swing.Timer time =new javax.swing.Timer(0,q->{
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            if (movekind1 == 1) {
                chessJLabel[clicked1[0]][clicked1[1]].setLocation(chessJLabel[clicked1[0]][clicked1[1]].getX() - 1, chessJLabel[clicked1[0]][clicked1[1]].getY());
                chessJLabel[clicked2[0]][clicked2[1]].setLocation(chessJLabel[clicked2[0]][clicked2[1]].getX() + 1, chessJLabel[clicked1[0]][clicked1[1]].getY());
            }
            if (movekind1 == 3) {
                chessJLabel[clicked1[0]][clicked1[1]].setLocation(chessJLabel[clicked1[0]][clicked1[1]].getX() + 1, chessJLabel[clicked1[0]][clicked1[1]].getY());
                chessJLabel[clicked2[0]][clicked2[1]].setLocation(chessJLabel[clicked2[0]][clicked2[1]].getX() - 1, chessJLabel[clicked1[0]][clicked1[1]].getY());
            }
            if (movekind1 == 5) {
                chessJLabel[clicked1[0]][clicked1[1]].setLocation(chessJLabel[clicked1[0]][clicked1[1]].getX() , chessJLabel[clicked1[0]][clicked1[1]].getY()-1);
                chessJLabel[clicked2[0]][clicked2[1]].setLocation(chessJLabel[clicked2[0]][clicked2[1]].getX() , chessJLabel[clicked2[0]][clicked2[1]].getY()+1);
            }
            if (movekind1 == 2) {
                chessJLabel[clicked1[0]][clicked1[1]].setLocation(chessJLabel[clicked1[0]][clicked1[1]].getX() , chessJLabel[clicked1[0]][clicked1[1]].getY()+1);
                chessJLabel[clicked2[0]][clicked2[1]].setLocation(chessJLabel[clicked2[0]][clicked2[1]].getX() , chessJLabel[clicked2[0]][clicked2[1]].getY()-1);
            }
            indexf[0]++;
            if(indexf[0] ==79){
                step++;
                if(step>Math.ceil(40/this.difficulty)){
                    JOptionPane.showMessageDialog(this, "你输了！");
                    this.setVisible(false);
                    new StartFrame();
                }
                stepCount.setText("Steps:" + step + "/" +Math.ceil(40/this.difficulty));
                clickedNumber = 0;
                boxlabel[clicked1[0]][clicked1[1]].setIcon(boxPic);
                boxlabel[clicked2[0]][clicked2[1]].setIcon(boxPic);
                //代码复用，重新打印棋盘和棋子
                unSelect(clicked1[0], clicked1[1]);
                unSelect(clicked2[0], clicked2[1]);
                clicked1[0] = -1;
                clicked1[1] = -1;
                clicked2[0] = -1;
                clicked2[1] = -1;
                updateChessboard(false);
                protectWhenNotUpdated.addMouseListener(this);
                countFlames=0;
                ((javax.swing.Timer)q.getSource()).stop();
            }
        });
        return time;
    }
    private javax.swing.Timer getDroppingAnimation() {
        int[] indexf = {0};
        boolean[] whichAnimation= {true};
        for (int i = 0; i < CHESSBOARDXCOUNT; i++) {
            for (int i1 = 0; i1 < CHESSBOARDYCOUNT; i1++) {
                if (ifNeedDropping[i][i1]) {
                    whichAnimation[0] = false;
                    break;
                }
            }
        }
        javax.swing.Timer time = new javax.swing.Timer(0, q -> {
            if (whichAnimation[0]){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (indexf[0] >= 0 & indexf[0] <= 79) {
                    for (int i = 0; i < CHESSBOARDXCOUNT; i++) {
                        for (int i1 = 0; i1 < CHESSBOARDYCOUNT; i1++) {
                            if (isTheBoxNew[i][i1]) {
                                chessJLabel[i][i1].setIcon(chessStorage[i][i1].getThisChessPic());
                                chessJLabel[i][i1].setLocation(chessJLabel[i][i1].getX(), chessJLabel[i][i1].getY() + (i1 + 1));
                            }
                        }
                    }
                }
                indexf[0]++;
                if (indexf[0] == 1) Player.fallSound();
                if (indexf[0] == 79) {
                    //end
                    for (int i = 0; i < CHESSBOARDXCOUNT; i++) {
                        for (int i1 = 0; i1 < CHESSBOARDYCOUNT; i1++) {
                            isTheBoxNew[i][i1] = false;
                            ifNeedDropping[i][i1] = false;
                        }
                    }
                    ((javax.swing.Timer) q.getSource()).stop();
                }
            }else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (indexf[0] >= 0 & indexf[0] <= 79) {
                    for (int i = 0; i < CHESSBOARDXCOUNT; i++) {
                        for (int i1 = 0; i1 < CHESSBOARDYCOUNT; i1++) {
                            if (ifNeedDropping[i][i1]) {
                                chessJLabel[i][i1].setLocation(chessJLabel[i][i1].getX(), chessJLabel[i][i1].getY() + 1);
                            }
                        }
                    }
                }
                if (indexf[0] >= 400 & indexf[0] <= 479) {
                    if (ziYici){
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        ziYici=false;
                    }
                    for (int i = 0; i < CHESSBOARDXCOUNT; i++) {
                        for (int i1 = 0; i1 < CHESSBOARDYCOUNT; i1++) {
                            if (isTheBoxNew[i][i1]) {
                                if (chessStorage[i][i1]!=null) chessJLabel[i][i1].setIcon(chessStorage[i][i1].getThisChessPic());
                                chessJLabel[i][i1].setLocation(chessJLabel[i][i1].getX(), chessJLabel[i][i1].getY() + (i1 + 1));
                            }
                        }
                    }
                }
                indexf[0]++;
                if (indexf[0] == 1) Player.fallSound();
                if (indexf[0] == 479) {
                    //end
                    for (int i = 0; i < CHESSBOARDXCOUNT; i++) {
                        for (int i1 = 0; i1 < CHESSBOARDYCOUNT; i1++) {
                            isTheBoxNew[i][i1] = false;
                            ifNeedDropping[i][i1] = false;
                        }
                    }
                    ((javax.swing.Timer) q.getSource()).stop();
                }
            }
        });
        return time;
    }
    //模糊效果，暂时未投入使用。
    private static BufferedImage blurImage(BufferedImage image, float blurRadius) {
        BufferedImage blurredImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = blurredImage.createGraphics();
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, blurRadius));  // 设置透明度，这里的0.5f代表50%的不透明度
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();
        return blurredImage;
    }
}
class server implements Runnable{
    public ServerSocket serverSocket;
    public static Socket socket;
    public GameFrame gf;
    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public server (ServerSocket serverSocket,GameFrame gf){
        this.serverSocket=serverSocket;
        this.gf=gf;
    }
    @Override
    public void run() {
        try {
            System.out.println("Socket服务器开始运行...");
                Socket socket = serverSocket.accept();
                this.socket=socket;
                JOptionPane.showMessageDialog(null,"有客户端连接到了服务器");
                new Thread(new Server_listen(socket,gf)).start();
                //if the user clicked the onlineButton,start a new thread to send the chessboard to the client
                //if (GameFrame.onlineButton.isEnabled()) new Thread(new Server_send(socket)).start();

        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"出错了！");
        }

    }
}
class Server_listen implements Runnable{
    private Socket socket;
    private GameFrame gf;
    private String path= System.getProperty("user.dir")+"\\src\\Storage\\";
    public Server_listen(Socket socket,GameFrame gf){
        this.socket=socket;
        this.gf=gf;
    }
    public void run(){
        try{
            while (true) {
                InputStream ips = socket.getInputStream();
                byte[] bytes = new byte[1024];
                int len = ips.read(bytes);
                FileOutputStream fis = new FileOutputStream(path+"ReceivedFromClient.txt");
                fis.write(bytes,0,len);
                fis.close();
                gf.getLayeredPane().removeAll();
                gf.revalidate();
                gf.repaint();
                gf.UpdateGame(Objects.requireNonNull(IOReader.InPut("ReceivedFromClient.txt")));
                gf.RefreshGame(false);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
class Server_send implements Runnable{
    public static Socket socket;
    private int a;
    private String path= System.getProperty("user.dir")+"\\src\\Storage\\";
    public Server_send(int a){
        this.a=a;
    }
    public void run(){
        try{
            try {
                OutputStream ops = server.socket.getOutputStream();
                FileInputStream fis =new FileInputStream(path+"SendFromServer.txt");
                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len=fis.read(bytes))!=-1){
                    ops.write(bytes,0,len);
                }
                fis.close();
                ops.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
class client implements Runnable{
    private GameFrame gf;
    public static Socket socket;
    public client(Socket socket,GameFrame gf){
        this.socket=socket;
        this.gf=gf;
        JOptionPane.showMessageDialog(null,"连接成功");
    }
    @Override
    public void run(){
            new client_listen(socket,gf).run();
    }
}
class client_listen implements Runnable{
    private Socket socket;
    private GameFrame gf;
    private String path= System.getProperty("user.dir")+"\\src\\Storage\\";
    public client_listen(Socket socket,GameFrame gf){
        this.socket=socket;
        this.gf=gf;
    }
    public void run(){
        try {
            while (true){
                InputStream ips = socket.getInputStream();
                byte[] bytes = new byte[1024];
                int len=ips.read(bytes);
                FileOutputStream fos = new FileOutputStream(path+"ReceivedFromSever.txt");
                fos.write(bytes,0,len);
                fos.close();
                gf.getLayeredPane().removeAll();
                gf.revalidate();
                gf.repaint();
                gf.UpdateGame(IOReader.InPut("ReceivedFromSever.txt"));
                gf.RefreshGame(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
            assert false;
            throw new RuntimeException(e);
        }
    }
}
class Client_send implements Runnable{
    private Socket socket;
    private String path= System.getProperty("user.dir")+"\\src\\Storage\\";
    public Client_send(Socket socket){
        this.socket=socket;
    }
    public void run(){
        try{
            try {
                OutputStream ops = client.socket.getOutputStream();
                FileInputStream fis =new FileInputStream(path+"SendFromClient.txt");
                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len=fis.read(bytes))!=-1){
                    ops.write(bytes,0,len);
                }
                fis.close();
                ops.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }catch (Exception ignored){
        }
    }
}