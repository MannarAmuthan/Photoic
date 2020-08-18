/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StatePackage;

import javafx.event.ActionEvent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import LayerPackage.Layer;
import LayerPackage.LayerFactory;
import UndoRedo.LayerStateUndo;
import UndoRedo.addLayerUndo;
import photoic.Universal;
import photoic.WorkPlace;

/**
 *
 * @author ELCOT
 */

public class StateShapeElipse extends State {
    int polySides;
    Layer l;
    boolean holded,drawed;
    Point lastPoint;
    Point current;
    ContextMenu menu;
    
    public StateShapeElipse(WorkPlace place) {
        super(place);
        l=new LayerFactory().getImageLayer("",1);
        Universal.addUndo(new addLayerUndo(place,place.mainWindow,l));
        place.mainWindow.addLayer(l,0);
        lastPoint=new Point();
        menu=new ContextMenu();
        MenuItem cancel=new MenuItem("Cancel All");
        MenuItem fill=new MenuItem("Fill Shape");
        MenuItem stroke=new MenuItem("Stroke Shape");
        menu.getItems().addAll(cancel,fill,stroke);
        place.getCanvas().setOnContextMenuRequested((ContextMenuEvent event) -> {
            menu.show(place.getMainStage(), event.getScreenX(), event.getSceneY());
        }); 
        cancel.setOnAction((ActionEvent event) -> {
            l.clearMat();
            drawed=false;
            place.update();
         });
        stroke.setOnAction((ActionEvent event) -> {
            if(drawed){
                l.clearMat();
                int blue=(int)(Universal.currentColor.getBlue()*255);
                int green=(int)(Universal.currentColor.getGreen()*255);
                int red=(int)(Universal.currentColor.getRed()*255);
                int w=Math.subtractExact((int)current.x, (int)lastPoint.x);
                int h=Math.subtractExact((int)current.y, (int)lastPoint.y);
                Imgproc.ellipse(l.backstageMat, current, new Size(h,w), 0, 0, 360,new Scalar(blue,green,red,255), Universal.strokeWidth);
                drawed=false;
                place.update();
            }
        });
        fill.setOnAction((ActionEvent event) -> {
            if(drawed){
                l.clearMat();
                int blue=(int)(Universal.currentColor.getBlue()*255);
                int green=(int)(Universal.currentColor.getGreen()*255);
                int red=(int)(Universal.currentColor.getRed()*255);
                int w=Math.subtractExact((int)current.x, (int)lastPoint.x);
                int h=Math.subtractExact((int)current.y, (int)lastPoint.y);
                Imgproc.ellipse(l.backstageMat, current, new Size(h,w), 0, 0, 360,new Scalar(blue,green,red,255), -1);
                drawed=false;
                place.update();
            }
        });
        
    }

    @Override
    public void onMouseDragged(MouseEvent event, Layer l) {
        if(holded){
        l.clearMat();
        double x=event.getX()+l.rectangle.x;
        double y=event.getY()+l.rectangle.y;
        current=new Point(x,y);
        System.out.println("drawn.. "+current);
        int w=Math.subtractExact((int)current.x, (int)lastPoint.x);
        int h=Math.subtractExact((int)current.y, (int)lastPoint.y);
        Imgproc.ellipse(l.backstageMat, current, new Size(h,w), 0, 0, 360,new Scalar(0,0,0,255), 2);
                
        drawed=true;
        place.update();
        }        
        
    }

    @Override
    public void onMousePressed(MouseEvent event, Layer l) {
        if(drawed==false){
        holded=true;
        lastPoint.x=event.getX()+l.rectangle.x;
        lastPoint.y=event.getY()+l.rectangle.y;
        Universal.addUndo(new LayerStateUndo(place,l,place.layers.indexOf(l)));}
    }

    @Override
    public void onMouseReleased(MouseEvent event, Layer l) {
        holded=false;
    }

    @Override
    public void onMouseClicked(MouseEvent event, Layer l) {

    }

    @Override
    public void onMouseMoved(MouseEvent event, Layer l) {
    }

    @Override
    public void deletePressed() {
    }

    @Override
    public void saveState() {
    }
    
}
