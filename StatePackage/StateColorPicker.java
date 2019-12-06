/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StatePackage;

import javafx.scene.input.MouseEvent;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import photoic.ImageUtilities;
import LayerPackage.Layer;
import UndoRedo.LayerStateUndo;
import java.awt.image.BufferedImage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import photoic.Universal;
import photoic.WorkPlace;
import windows.StrokeSettings;

/**
 *
 * @author ELCOT
 */
public class StateColorPicker extends State {
    int side=1;
    public StateColorPicker(WorkPlace place) {
        super(place);
    }

    @Override
    public void onMouseDragged(MouseEvent event, Layer l) {
        Image snapshot = place.merge(place.layers);
        try{
        Color c= snapshot.getPixelReader().getColor((int)event.getX(),(int)event.getY());
        Universal.currentColor=c;
        
        Canvas ca=StrokeSettings.ca;
        GraphicsContext context=ca.getGraphicsContext2D();
        context.setFill(Universal.currentColor);
        context.fillOval(0, 0, ca.getWidth(), ca.getHeight());}
        catch(Exception e){}
        
        
    }

    @Override
    public void onMousePressed(MouseEvent event, Layer l) {
    }

    @Override
    public void onMouseReleased(MouseEvent event, Layer l) {
    }

    @Override
    public void onMouseClicked(MouseEvent event, Layer l) {
        Image snapshot = place.merge(place.layers);
        Color c= snapshot.getPixelReader().getColor((int)event.getX(),(int)event.getY());
        Universal.currentColor=c;
        
        Canvas ca=StrokeSettings.ca;
        GraphicsContext context=ca.getGraphicsContext2D();
        context.setFill(Universal.currentColor);
        context.fillOval(0, 0, ca.getWidth(), ca.getHeight());
        
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
