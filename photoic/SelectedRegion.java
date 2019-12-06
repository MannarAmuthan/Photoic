/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package photoic;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import LayerPackage.Layer;
import LayerPackage.LayerFactory;

/**
 *
 * @author ELCOT
 */
public class SelectedRegion {
    public ArrayList<Point> points;
    public Layer l;
    public  Scalar scalar;
    public SelectedRegion(){points=new ArrayList<>();l=new LayerFactory().getImageLayer("",0);scalar=new Scalar(0,0,0,255);};
    
    public  void markPoint(int x,int y){ 
     l.clearMat();
     points.add(new Point(x,y));
     System.out.println("marked  "+x+" "+y);
     drawSelected();    
    }
    public void removeLastPoint(){
     l.clearMat();
     points.remove(points.size()-1);
     drawSelected(); 
    }
    
    public  void drawSelected(){  
    for(int i=0;i<points.size();i++){
    Point start,end;
    start=points.get(i);
    if(points.size()<=i+1){break;}
    end=points.get(i+1);
    Imgproc.line(l.backstageMat, start, end, new Scalar(0,0,0,255),1);
    }
    }

    public void drawStroke(Scalar sc,int width){    
    for(int i=0;i<points.size();i++){
    Point start,end;
    start=points.get(i);
    if(points.size()<=i+1){break;}
    end=points.get(i+1);
    Imgproc.line(l.backstageMat, start, end,sc,width);
    }   
    }
            
}
