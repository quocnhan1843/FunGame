
package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class Framework extends Canvas {
    
    public static int frameWidth;
    public static int frameHeight;
    public static final long secInNanosec = 1000000000L;
    public static final long milisecInNanosec = 1000000L;
    private final int GAME_FPS = 60;
    private final long GAME_UPDATE_PERIOD = secInNanosec / GAME_FPS;
    public static enum GameState{STARTING, VISUALIZING, GAME_CONTENT_LOADING, MAIN_MENU, OPTIONS, PLAYING, GAMEOVER, DESTROYED}
    public static GameState gameState;
    private long gameTime;
    private long lastTime;
    private Game game;
    private Font font;
    
    private Point mousePosition() {
        try
        {
            Point mp = this.getMousePosition();
            
            if(mp != null)
                return this.getMousePosition();
            else
                return new Point(0, 0);
        }
        catch (HeadlessException e)
        {
            return new Point(0, 0);
        }
    }

    private void Initialize() {
        font = new Font("GAMECUBEN",Font.BOLD,30);
    }
    
    private BufferedImage gameTitleImg;
    private BufferedImage menuBorderImg;
    private BufferedImage skyColorImg;
    private BufferedImage backGroundImg;
    private BufferedImage cloudLayer2Img;
    private BufferedImage loadingImg;
    private BufferedImage gameOverImg;
    private BufferedImage booomImg;
    
    private void LoadContent() {
        try {
            URL background =this.getClass().getResource("/image/background.jpg");
            backGroundImg = ImageIO.read(background);
            URL loading = this.getClass().getResource("/image/loading.gif");
            loadingImg = ImageIO.read(loading);
            URL gameover = this.getClass().getResource("/image/gameover.png");
            gameOverImg = ImageIO.read(gameover);
            URL booom = this.getClass().getResource("/image/Booom.png");
            booomImg = ImageIO.read(booom);
        } catch (IOException ex) {
        }
    }
    
    public Framework(){
        super();
        
        gameState = GameState.VISUALIZING;
        
        Thread gameThread = new Thread() {
            @Override
            public void run(){
                GameLoop();
            }
        };
        gameThread.start();
    }
    private void GameLoop() {
        long visualizingTime = 0, lastVisualizingTime = System.nanoTime();
        long beginTime, timeTaken, timeLeft;
        
        while(true)
        {
            beginTime = System.nanoTime();
            
            switch (gameState)
            {
                case PLAYING:
                    gameTime += System.nanoTime() - lastTime;
                    
                    game.UpdateGame(gameTime, mousePosition());
                    
                    lastTime = System.nanoTime();
                break;
                case GAMEOVER:
                break;
                case MAIN_MENU:
                break;
                case OPTIONS:
                break;
                case GAME_CONTENT_LOADING:
                break;
                case STARTING:
                    Initialize();
                    LoadContent();
                    gameState = GameState.MAIN_MENU;
                break;
                case VISUALIZING:
                    if(this.getWidth() > 1 && visualizingTime > secInNanosec)
                    {
                        frameWidth = this.getWidth();
                        frameHeight = this.getHeight();                        
                        gameState = GameState.STARTING;
                    }
                    else
                    {
                        visualizingTime += System.nanoTime() - lastVisualizingTime;
                        lastVisualizingTime = System.nanoTime();
                    }
                break;
            }
            
            repaint();
            
            timeTaken = System.nanoTime() - beginTime;
            timeLeft = (GAME_UPDATE_PERIOD - timeTaken) / milisecInNanosec;
            
            if (timeLeft < 10) 
                timeLeft = 10;
            try {
                 Thread.sleep(timeLeft);
            } catch (InterruptedException ex) { }
        }
    }
    @Override
    public void Draw(Graphics2D g2d) {
        switch (gameState)
        {
            case PLAYING:
                game.Draw(g2d, mousePosition(), gameTime);
                g2d.setFont(new Font("GAMECUBEN",Font.BOLD,16));
                g2d.setColor(Color.red);
                g2d.drawString("SCORE: ",20,20);
                g2d.drawString("Height: ",20,40);
            break;
            case GAMEOVER:
                
                g2d.drawImage(backGroundImg, null, WIDTH, WIDTH);
                g2d.drawImage(gameOverImg, 0,0,frameWidth/2,frameHeight/2,null);
                g2d.setFont(font);
                g2d.setColor(Color.red);
                g2d.drawString("YOUR SCORE: ",frameWidth/2,frameHeight/2);
                g2d.drawString(Integer.toString(game.getScore() ),frameWidth/2 + 370,frameHeight/2);
                g2d.setFont(new Font("Time New Roman",Font.BOLD, 20));
                g2d.drawString("Cận thận, nhấn Enter để chơi lại nào....",frameWidth/2,frameHeight/2 + 60);
                g2d.drawImage(booomImg, 0,frameHeight/2,frameWidth/2,frameHeight/2 + 100,null);
            break;
            case MAIN_MENU:
                g2d.setFont(new Font("Time New Roman",Font.BOLD, 20));
                g2d.setColor(Color.gray);
                g2d.drawImage(backGroundImg, null, WIDTH, WIDTH);
                g2d.drawString("Sử dụng phím Space để bay lên.", frameWidth / 2 - frameWidth / 3, frameHeight / 2 - 30);
                g2d.drawString("Chú ý: Bạn sẽ thua khi bay đụng boom hoặc nhân vật của bạn bay quá thấp ( < 100 ) và sẽ tự rơi nếu bạn bay quá cao", frameWidth / 2 - frameWidth / 3, frameHeight / 2);
                g2d.drawString("CHÚC BẠN CHƠI GAME VUI VẼ", frameWidth / 2 - frameWidth / 3, frameHeight / 2 + 30);
                g2d.drawString("Nhấn phím bất kỳ đễ vào game và ESC khi muốn thoát khỏi game bất cứ lúc nào", frameWidth / 2 - frameWidth / 3, frameHeight / 2 + 60);
                
            break;
            case OPTIONS:
                //...
            break;
            case GAME_CONTENT_LOADING:
                g2d.setColor(Color.blue);
                g2d.setFont(font);
                g2d.drawImage(backGroundImg, null, WIDTH, WIDTH);
                g2d.drawString("Loading...", frameWidth/2 - frameWidth/3 + frameWidth/8, frameHeight/2);
                //g2d.drawImage(loadingImg, null, WIDTH, WIDTH);
            break;
        }
    }

    @Override
    public void keyReleasedFramework(KeyEvent e) {
           if(e.getKeyCode() == KeyEvent.VK_ESCAPE) System.exit(0);
           
            switch(gameState)
            {
                case GAMEOVER:
                    if(e.getKeyCode() == KeyEvent.VK_ENTER)
                        restartGame();
                break;
                case MAIN_MENU:
                    newGame();
                break;
            }
    }
    
    private void restartGame() {
        gameTime = 0;
        lastTime = System.nanoTime();
        
        game.RestartGame();
        gameState = GameState.PLAYING;
    }

    private void newGame() {
        gameTime = 0;
        lastTime = System.nanoTime();
        
        game = new Game();
    }
}