/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StatePackage;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.input.MouseEvent;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import photoic.ImageUtilities;
import LayerPackage.Layer;
import LayerPackage.LayerFactory;
import UndoRedo.LayerStateUndo;
import java.util.logging.Level;
import java.util.logging.Logger;
import photoic.Universal;
import photoic.WorkPlace;



/**
 *
 * @author ELCOT
 */
public class StateEraser extends State {
    boolean holded; 
    Point lastPoint;
    
    Layer forUndo;
    public StateEraser(WorkPlace place) {
        super(place);
    }

    @Override
    public void onMouseDragged(MouseEvent event, Layer l) {
        if(holded){
            double x=event.getX()+l.rectangle.x;
            double y=event.getY()+l.rectangle.y;
            if(lastPoint!=null){
              Point current=new Point(x,y);
              int blue=(int)(Universal.currentColor.getBlue()*255);
              int green=(int)(Universal.currentColor.getGreen()*255);
              int red=(int)(Universal.currentColor.getRed()*255);
              Erase((int)lastPoint.x,(int)lastPoint.y,(int)current.x,(int)current.y,l);
              //Imgproc.line(l.backstageMat,lastPoint,current,new Scalar(blue,green,red,255),Universal.strokeWidth);
              lastPoint=current;
              place.update();
            }  
            else{lastPoint=new Point(x,y);}
        }
        
    }

    @Override
    public void onMousePressed(MouseEvent event, Layer l) {
        holded=true;
        forUndo=new LayerFactory().copy(l);
    }

    @Override
    public void onMouseReleased(MouseEvent event, Layer l) {
        holded=false;
        lastPoint=null;
        Universal.addUndo(new LayerStateUndo(place,forUndo,place.layers.indexOf(l)));
        forUndo=null;
    }

    @Override
    public void onMouseClicked(MouseEvent event, Layer l) {
        if(l!=null){
           double x=event.getX()+l.rectangle.x;
           double y=event.getY()+l.rectangle.y;   
           Point current=new Point(x,y);
           Erase((int)current.x,(int)current.y,(int)current.x,(int)current.y,l);
           place.update();}
    }

    @Override
    public void onMouseMoved(MouseEvent event, Layer l) {
    }

    @Override
    public void saveState() {
    }

    @Override
    public void deletePressed() {
        
    }
    


    
    public static void Erase(int x0, int y0, int x1, int y1,Layer l) 

    {                    
        List<java.awt.Point> line = new ArrayList<java.awt.Point>();
        int dx = Math.abs(x1 - x0);int dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1; 
        int sy = y0 < y1 ? 1 : -1; 
        int err = dx-dy;
        int e2;
        while (true) 
         {
          Imgproc.circle(l.backstageMat, new Point(x0,y0),Universal.strokeWidth, new Scalar(255,255,255), -1);
          if (x0 == x1 && y0 == y1) 
             break;
          e2 = 2 * err;
          if (e2 > -dy) 
           {
            err = err - dy;
            x0 = x0 + sx;
            }
             if (e2 < dx) 
              {
               err = err + dx;
               y0 = y0 + sy;
              }
              }           
 }
}
