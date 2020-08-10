/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LayerPackage;

import java.io.Serializable;
import javafx.scene.image.Image;
import org.opencv.core.Mat;
import photoic.ImageUtilities;

/**
 *
 * @author ELCOT
 */
public class ImageLayer extends Layer {
    

    public ImageLayer(Image m,String name,int type){
    super(name,type);
    Mat temp=new ImageUtilities().getMat(m);
    temp.copyTo(backstageMat);
    backstageMat=ImageUtilities.adjustImage(backstageMat);
    stageMat=backstageMat.submat(rectangle);
    System.out.println("adjusted by "+backstageMat.size()+" "+stageMat.size()); 
    saveLossless();
    }
    public ImageLayer(String name,int type){
    super(name,type);
    }

    @Override
    public void drawScale() {
        
    }


    @Override
    public void opacity(int op) {
    
    }

    /**
     *
     * @param blend
     */


    /**
     *
     * @param hp
     */


    @Override
    public void updatePreview() {
        
    }

    @Override
    public LayerContainer getPreview() {
        return preview;
    }

    @Override
    public void updateMat() {
        
    }
}

