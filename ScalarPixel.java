/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Brushes;

import org.opencv.core.Point;
import org.opencv.core.Scalar;

/**
 *
 * @author ELCOT
 */
public class ScalarPixel {
    public Point pixel;
    public Scalar sc;
    public ScalarPixel(){}
    public ScalarPixel(Point pixel, Scalar sc) {
        this.pixel = pixel;
        this.sc = sc;
    }
    
    public Point getPixel() {
        return pixel;
    }

    public void setPixel(Point pixel) {
        this.pixel = pixel;
    }

    public Scalar getSc() {
        return sc;
    }

    public void setSc(Scalar sc) {
        this.sc = sc;
    }
    
}
