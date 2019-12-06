/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LayerPackage;

import javafx.scene.image.Image;
import org.opencv.core.Mat;
import photoic.ImageUtilities;
import photoic.Universal;

/**
 *
 * @author ELCOT
 */
public class LayerFactory {
    
   public Layer getImageLayer(String name,int flag){
       Mat m=ImageUtilities.getPlainImage();
       Image n=ImageUtilities.getImage(m);
       Layer l=getImageLayer(n,name,flag);
       return l;  
   }
   public Layer getImageLayer(Image m,String name,int flag){
    Layer layer=new ImageLayer(m,name,flag);
    return layer;
   }
   public Layer copy(Layer l){
       if(l instanceof ImageLayer){
       Layer m=getImageLayer(null,((ImageLayer) l).type);
       m.setMat(((ImageLayer) l).backstageMat.clone(),((ImageLayer) l).rectangle.clone());
       m.setLossless(((ImageLayer) l).losslessMat.clone());
       return m;
       }
       return null; 
   }
   
   public Layer getTextLayer(String name,int flag){
   Layer l=new TextLayer(name,flag);
   return l;
   }
   

    
}
