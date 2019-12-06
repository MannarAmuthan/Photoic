/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StatePackage;

import java.util.ArrayList;
import javafx.scene.input.MouseEvent;
import static javafx.scene.paint.Color.color;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import photoic.ImageUtilities;
import LayerPackage.Layer;
import LayerPackage.LayerFactory;
import UndoRedo.LayerStateUndo;
import photoic.Universal;
import photoic.WorkPlace;

/**
 *
 * @author ELCOT
 */
public class StateBrush extends State {
    boolean holded; 
    Point lastPoint;
    
    Layer forUndo;
    public StateBrush(WorkPlace place) {
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
              Imgproc.line(l.backstageMat,lastPoint,current,new Scalar(blue,green,red,255),Universal.strokeWidth);
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
      double x=event.getX()+l.rectangle.x;
      double y=event.getY()+l.rectangle.y;   
      Point current=new Point(x,y);
      ArrayList<Point> points = Universal.brush.getPoints((int)x,(int)y,Universal.strokeWidth/2);
      ImageUtilities.fillPoly(l.backstageMat,points,ImageUtilities.getScalar(Universal.currentColor));
      place.update();
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
    
}
