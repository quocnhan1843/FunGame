/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

/**
 *
 * @author Nguyen Quoc Nhan
 */
public class Position {
    private int x, y;
    public Position(){
        this.x = 0;
        this.y = 0;
    }
    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public void setPoint(int x, int y){
        this.x = x;
        this.y = y;
    }
    public void addY(int val){
        this.y += val;
    }
    public void addX(int val){
        this.x += val;
    }
}
