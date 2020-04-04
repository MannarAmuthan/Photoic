/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StatePackage;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import org.opencv.core.Core;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import photoic.ImageUtilities;
import LayerPackage.Layer;
import LayerPackage.LayerFactory;
import photoic.SelectedRegion;
import UndoRedo.LayerStateUndo;
import UndoRedo.PenUndo;
import photoic.Universal;
import photoic.WorkPlace;

/**
 *
 * @author ELCOT
 */
public class StatePen extends State{ 
    SelectedRegion r;
    boolean connected,completed;
    ContextMenu menu;
    public StatePen(WorkPlace place) {
        super(place);
        r=new SelectedRegion();
        this.place.addLayer(r.l,0);
        menu=new ContextMenu();
        
        MenuItem cancel=new MenuItem("Cancel All");
        MenuItem fill=new MenuItem("Fill");
        MenuItem stroke=new MenuItem("Stroke");
        menu.getItems().addAll(cancel,fill,stroke);
        place.getCanvas().setOnContextMenuRequested((ContextMenuEvent event) -> {
            menu.show(place.getCanvas(), event.getScreenX(), event.getSceneY());
        });  
        cancel.setOnAction((ActionEvent event) -> {
            if(r!=null){
            r.l.clearMat();
            r.points.clear();
            r.drawSelected();
            place.update();
            completed=false;
            }
        });
        stroke.setOnAction((ActionEvent event) -> {
            if(r!=null){
            Universal.addUndo(new LayerStateUndo(place,place.selected,place.layers.indexOf(place.selected)));    
            r.l.clearMat();
            Scalar sc=ImageUtilities.getScalar(Universal.currentColor);
            
            r.drawStroke(sc,Universal.strokeWidth);
            
            System.out.println("drawen "+r.points.size());
            place.selected.MergeWith(r.l);
            place.deleteLayer(r.l);
            place.update();
            completed=false;
            }
        });
        fill.setOnAction((ActionEvent event) -> {
            if(r!=null){
                Universal.addUndo(new LayerStateUndo(place,place.selected,place.layers.indexOf(place.selected)));    
                r.l.clearMat();
                Scalar sc=ImageUtilities.getScalar(Universal.currentColor);
                
                ImageUtilities.fillPoly(r.l.backstageMat,r.points, sc);
                
                place.selected.MergeWith(r.l);
                place.deleteLayer(r.l);
                place.update();
                completed=false;
            }
        });
        
    }

    @Override
    public void onMouseDragged(MouseEvent event, Layer l) {
    }

    @Override
    public void onMousePressed(MouseEvent event, Layer l) {
    }

    @Override
    public void onMouseReleased(MouseEvent event, Layer l) {
    }

    @Override
    public void onMouseClicked(MouseEvent event, Layer l) {
       if(menu.isShowing()){menu.hide();} 
       else{
          if(!completed){
          // if pen layer is previously completed, then we have to add for new process    
          if(!place.layers.contains(r.l)){r.points.clear();place.addLayer(r.l,0);}   
          Universal.addUndo(new PenUndo(place,r));
          r.markPoint((int)event.getX(),(int)event.getY());
          place.update();
                        }
          if(connected){if(r.points.size()>2&&(r.points.get(r.points.size()-1)!=r.points.get(r.points.size()-2)))completed=true;}
           }
    }

    @Override
    public void onMouseMoved(MouseEvent event, Layer l) {
        try{
        if(event.getX()>r.points.get(0).x-2&&event.getX()<r.points.get(0).x+2){
            if(event.getY()>r.points.get(0).y-2&&event.getY()<r.points.get(0).y+2){
            connected=true;
            System.out.println("save....");
            }
            else
            connected=false;
        }}
        catch(Exception e){}
    }

    @Override
    public void saveState() {
       
    }

    @Override
    public void deletePressed() {
        if(completed){
           r.l.clearMat();
           Scalar sc=new Scalar(0,0,0,0);
           ImageUtilities.clearPoly(place.selected.backstageMat,place.selected.rectangle,r.points);
           place.deleteLayer(r.l);
           place.update();
           completed=false;
        }
    }
    
}
