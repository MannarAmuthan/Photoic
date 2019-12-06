/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Spatial;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import photoic.ImageUtilities;
import LayerPackage.Layer;
import photoic.Photoic;
import UndoRedo.LayerStateUndo;
import photoic.Universal;
import photoic.WorkPlace;

/**
 *
 * @author ELCOT
 */
public class GrayScale extends SpatialFilter {
    Mat performed;
    int ker;
    public GrayScale(Photoic p) {
        super(p);
    }

    public void grayScale(Layer l) {
        Mat m=new Mat(l.backstageMat.size(),l.backstageMat.type());
        Universal.addUndo(new LayerStateUndo(p.place,l,p.place.layers.indexOf(l)));
        l.backstageMat=ImageUtilities.toGray(l.backstageMat);
        l.stageMat=l.backstageMat.submat(l.rectangle);
        p.place.update();
        }

    @Override
    public void filter(int amount, Layer l,int tag) {
    }

    @Override
    public void launch(Layer l) {
        grayScale(l);
    }
        
    
    
}
