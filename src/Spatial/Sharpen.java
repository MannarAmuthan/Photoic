/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Spatial;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.opencv.core.Core;
import org.opencv.core.CvType;
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
public class Sharpen extends SpatialFilter {
    Mat performed;
    int ker;
    public Sharpen(Photoic p) {
        super(p);
    }

    public void sharpen(Layer l) {
        int num=3;
            Mat m=Mat.ones(num, num, CvType.CV_64F);
            for(int i=0;i<num;i++){
            for(int j=0;j<num;j++){  
                  if(j==num/2&&i==num/2)
                 m.put(i,j,9);
                else
                 m.put(i,j,-1);                    
            }
            }
            
            
             Mat n=Mat.ones(3, 3, CvType.CV_32F);
              n.put(0,0,-2); n.put(0,1,-1); n.put(0,2,0);
              n.put(1,0,-1); n.put(1,1,1); n.put(1,2,1);
              n.put(2,0,0); n.put(2,1,1); n.put(2,2,2);
              
              
        Universal.addUndo(new LayerStateUndo(p.place,l,p.place.layers.indexOf(l)));
        l.backstageMat=ImageUtilities.invert(l.backstageMat);
        Imgproc.filter2D(l.backstageMat, l.backstageMat, -1, n);
        l.stageMat=l.backstageMat.submat(l.rectangle);
        p.place.update();
        }

    @Override
    public void filter(int amount, Layer l,int tag) {
    }

    @Override
    public void launch(Layer l) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        
    
    
}
