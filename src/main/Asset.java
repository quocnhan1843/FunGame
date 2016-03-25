/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.nio.file.Paths;

/**
 *
 * @author Nguyen Quoc Nhan
 */
class Asset {
}
class ScreenSize{
    public static int width;
    public static int height;
    public void Initialize(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int) screenSize.getWidth();
        height = (int) screenSize.getHeight();
    }
    public ScreenSize(){
        Initialize();
    }
}
