/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Nguyen Quoc Nhan
 */
public class Player {
   private int x;
   private int y;
   private BufferedImage rocketImg;
   private BufferedImage rocketCrashedImg;
   private BufferedImage rocketFireImg;
   private BufferedImage rocketLandeImg;
   private int rocketImgWidth;
   private int rocketImgHeight;
   private int xSpeed;
   private double ySpeed;
   public Player(){
       Initialize();
       LoadContent();
   }
   
    private void Initialize() {

        x = Framework.frameWidth/2 - 100;
        y = 100;
        xSpeed = 0;
        ySpeed = 0;
    }

    private void LoadContent() {
       try {
           URL rocket = this.getClass().getResource("/image/xitrum.png");
           rocketImg = ImageIO.read(rocket);
           rocketImgWidth = rocketImg.getWidth();
           rocketImgHeight = rocketImg.getHeight();
           URL rocket_crashed = this.getClass().getResource("/image/rocket_crashed.png");
           rocketCrashedImg = ImageIO.read(rocket_crashed);
           URL rocket_fire = this.getClass().getResource("/image/rocket_fire.png");
           rocketFireImg = ImageIO.read(rocket_fire);
           URL rocket_landed = this.getClass().getResource("/image/rocket_landed.png");
           rocketLandeImg = ImageIO.read(rocket_landed);
           
       } catch (IOException ex) {
       }
        
    }
    public int getY(){
        return this.y;
    }
    public void Update(){
        if(Canvas.keyboardKeyState(KeyEvent.VK_SPACE)){
            if( y >= - 60) ySpeed -= 0.7;
            else ySpeed += 0.7;
        }else
        {
            if(y <= Framework.frameHeight - 100) ySpeed += 0.7;
        }
        y += (int) ySpeed;
    }
    
    public void Draw(Graphics2D g2d){
        if(Canvas.keyboardKeyState(KeyEvent.VK_DOWN)){
            g2d.drawImage(rocketFireImg,null, x - 12, y + rocketImg.getHeight()/2 + 10);
        }
        g2d.drawImage(rocketImg, null, x, y);
   }
   public boolean vaCham(Target tg){
       if( (this.x <= tg.getX() && tg.getX() <= this.x + rocketImg.getWidth() )
              &&( (this.y <= tg.getY() && tg.getY() <= this.y + rocketImg.getHeight())
              || (tg.getY() <= this.y && this.y <= tg.getY() + tg.getHeight() ) )
         )return true;
        if( (this.x <= tg.getX() + tg.getWidth() && tg.getX() + tg.getWidth() <= rocketImg.getWidth() )
              &&( (this.y <= tg.getY() && tg.getY() <= this.y + rocketImg.getHeight())
              || (tg.getY() <= this.y && this.y <= tg.getY() + tg.getHeight() ) )
          ) return true;
       return false;
   }
}
