/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import static java.lang.Math.max;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import main.Target.TARGET_TYPE;

/**
 *
 * @author Nguyen Quoc Nhan
 */
public class Game {

    private BufferedImage backGroundImg;
    private MovingImage backGroundMoving;
    private Player player;
    private Vector<Target> arrayTarget = new Vector<Target>();
    private Vector<Target> arrayTarget1 = new Vector<Target>();
    private Vector<Target> arrayTarget2 = new Vector<Target>();
    private int gameScore;
    public int getScore(){
        return this.gameScore;
    }
    public Game()
    {
        gameScore = 0;
        Framework.gameState = Framework.GameState.GAME_CONTENT_LOADING;
        
        Thread threadForInitGame = new Thread() {
            @Override
            public void run(){
                Initialize();
                LoadContent();
                Framework.gameState = Framework.GameState.PLAYING;
            }
        };
        threadForInitGame.start();
    }
    private void Initialize()
    {
           player = new Player();
           arrayTarget = new Vector<Target>();
    }
    private void LoadContent()
    {
        try {
            URL background =this.getClass().getResource("/image/background.jpg");
            backGroundImg = ImageIO.read(background);
        } catch (IOException ex) {
        }
        backGroundMoving = new MovingImage(backGroundImg, -1.0,0 );
        
    }
    public void RestartGame()
    {
        player = new Player();
        gameScore = 0;
        arrayTarget.clear();
        arrayTarget1.clear();
        arrayTarget2.clear();
    }
    public void UpdateGame(long gameTime, Point mousePosition)
    {
        player.Update();
        if(player.getY() > Framework.frameHeight - 100) {  Framework.gameState = Framework.GameState.GAMEOVER; }
        for(int i=0;i< arrayTarget.size(); i++){
            arrayTarget.get(i).Update();
        }
        for(int i=0;i< arrayTarget1.size(); i++){
            arrayTarget1.get(i).Update();
        }
        for(int i=0;i< arrayTarget2.size(); i++){
            arrayTarget2.get(i).Update();
        }
        for(int i=0;i< arrayTarget.size(); i++){
            if(player.vaCham(arrayTarget.get(i)) ){
                if(arrayTarget.get(i).isBoom()) {  Framework.gameState = Framework.GameState.GAMEOVER; }
                else
                {
                    gameScore+=1;
                    arrayTarget.remove(i);
                }
            }
        }
        for(int i=0;i< arrayTarget1.size(); i++){
            if(player.vaCham(arrayTarget1.get(i)) ){
                if(arrayTarget1.get(i).isBoom()) Framework.gameState = Framework.GameState.GAMEOVER;
                else
                {
                    gameScore+=1;
                    arrayTarget1.remove(i);
                }
            }
        }
        for(int i=0;i< arrayTarget2.size(); i++){
            if(player.vaCham(arrayTarget2.get(i)) ){
                if(arrayTarget2.get(i).isBoom()) Framework.gameState = Framework.GameState.GAMEOVER;
                else
                {
                    this.gameScore+=1;
                    arrayTarget2.remove(i);
                }
            }
        }
        for(int i=0;i< arrayTarget.size(); i++){
            if(arrayTarget.get(i).isLeftScreen()){
                arrayTarget.remove(i);
            }
        }
        for(int i=0;i< arrayTarget1.size(); i++){
            if(arrayTarget1.get(i).isLeftScreen()){
                arrayTarget1.remove(i);
            }
        }
        for(int i=0;i< arrayTarget2.size(); i++){
            if(arrayTarget2.get(i).isLeftScreen()){
                arrayTarget2.remove(i);
            }
        }
        if(arrayTarget.isEmpty() || arrayTarget.get(arrayTarget.size() - 1).getX() < Framework.frameWidth - 400){
            int l = new Random().nextInt(3);
            
            Target tgb1 = new Target();
            Target tgb2 = new Target();
            Target tgn = new Target();
            if(l == 0){
                tgn.setTarget(10,TARGET_TYPE.NAM);
                arrayTarget.add(tgn);
                
                tgb1.setTarget(Framework.frameWidth/4 - 100,TARGET_TYPE.BOOM);
                arrayTarget1.add(tgb1);
                tgb2.setTarget(Framework.frameWidth/2 - 200,TARGET_TYPE.BOOM);
                arrayTarget2.add(tgb2);
            }
            else if(l == 1){
                tgn.setTarget(Framework.frameWidth/4 - 100,TARGET_TYPE.NAM);
                arrayTarget1.add(tgn);
                
                tgb1.setTarget(0,TARGET_TYPE.BOOM);
                arrayTarget.add(tgb1);
                tgb2.setTarget(Framework.frameWidth /2- 200,TARGET_TYPE.BOOM);
                arrayTarget2.add(tgb2);
            }else{
                tgn.setTarget(Framework.frameWidth/2 - 200,TARGET_TYPE.NAM);
                arrayTarget2.add(tgn);
                
                tgb1.setTarget(0,TARGET_TYPE.BOOM);
                arrayTarget.add(tgb1);
                tgb2.setTarget(Framework.frameWidth/4 - 100,TARGET_TYPE.BOOM);
                arrayTarget1.add(tgb2);
            }
        } 
    }
    public void Draw(Graphics2D g2d, Point mousePosition, long gameTime)
    {
        backGroundMoving.Draw(g2d);
        player.Draw(g2d);
        for(int i=0;i< arrayTarget.size(); i++){
            arrayTarget.get(i).Draw(g2d);
        }
        for(int i=0; i<arrayTarget1.size(); i++){
            arrayTarget1.get(i).Draw(g2d);
        }
        for(int i=0; i<arrayTarget2.size(); i++){
            arrayTarget2.get(i).Draw(g2d);
        }
        g2d.setFont(new Font("GAMECUBEN",Font.BOLD,16));
        g2d.setColor(Color.red);
        g2d.drawString(Integer.toString(gameScore),130,20);
        g2d.drawString(Integer.toString( Framework.frameHeight - player.getY()),130,40);
    }
}
class MovingImage {
    private BufferedImage image;
    private double speed;
    private double xPositions[];
    private int yPosition;
    public MovingImage(BufferedImage image, double speed, int yPosition)
    {
        this.image = image;
        this.speed = speed;

        this.yPosition = yPosition;

        int numberOfPositions = (Framework.frameWidth / this.image.getWidth()) + 2;
        xPositions = new double[numberOfPositions];

        for (int i = 0; i < xPositions.length; i++)
        {
            xPositions[i] = i * image.getWidth();
        }
    }
    private void Update()
    {
        for (int i = 0; i < xPositions.length; i++)
        {
            xPositions[i] += speed;
            if (xPositions[i] <= -image.getWidth())
            {
                xPositions[i] = image.getWidth() * (xPositions.length - 1);
            }   
        }
    }
    public void Draw(Graphics2D g2d)
    {
        this.Update();
        
        for (int i = 0; i < xPositions.length; i++)
        {
            g2d.drawImage(image, (int)xPositions[i], yPosition, null);
        }
    }
}

