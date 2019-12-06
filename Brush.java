/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Brushes;


import java.util.ArrayList;
import org.opencv.core.Point;

/**
 *
 * @author ELCOT
 */

public abstract class Brush {
    public abstract ArrayList<ScalarPixel>   getScalarPoints(int x,int y,int radius);
    public abstract ArrayList<Point> getPoints(int x,int y,int radius);
    
}
