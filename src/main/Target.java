/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Nguyen Quoc Nhan
 */
public class Target {
    private int x,y;
    private BufferedImage namXanhImg;
    private BufferedImage namDoImg;
    private BufferedImage boomImg;
    public static enum TARGET_TYPE{BOOM,NAM}
    private TARGET_TYPE typeTarget;
    
    private void Initialize() {
    }

    private void LoadContent() {
        try {
            URL namxanh = this.getClass().getResource("/image/namxanh.png");
            namXanhImg = ImageIO.read(namxanh);
            URL namdo = this.getClass().getResource("/image/namdo.png");
            namDoImg = ImageIO.read(namdo);
            URL boom = this.getClass().getResource("/image/boom.png");
            boomImg = ImageIO.read(boom);
        } catch (IOException ex) {
        }
        
    }
    public Target(){
        this.x = Framework.frameWidth;
        this.y = 0;
        Initialize();
        LoadContent();
    }
    
    public void Update(){
        this.x -= 3;
    }
    public boolean isLeftScreen(){
        if(x < -100) return true;
        return false;
    }
    public boolean isRightScreen(){
        if(x > Framework.frameWidth) return true;
        return false;
    }
    public void Draw(Graphics2D g2d){
        if(this.typeTarget.equals(TARGET_TYPE.NAM)){
            g2d.drawImage(namXanhImg, null, x, y);
        }
        else
        {
            g2d.drawImage(boomImg, null, x, y);
        }
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public void setTarget(int y, TARGET_TYPE  type){
        this.y = y;
        this.typeTarget = type;
    }
    public boolean isBoom(){
        return this.typeTarget.equals(TARGET_TYPE.BOOM);
    }
    public int getWidth(){
        if(typeTarget.equals(TARGET_TYPE.BOOM)) return boomImg.getWidth();
        return namXanhImg.getWidth();
    }
    public int getHeight(){
        if(typeTarget.equals(TARGET_TYPE.BOOM)) return boomImg.getHeight();
        return namXanhImg.getHeight();
    }
}
