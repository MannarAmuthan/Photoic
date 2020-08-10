/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UndoRedo;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import LayerPackage.Layer;
import static photoic.Photoic.saveLossless;
import StatePackage.StateMove;
import photoic.WorkPlace;

/**
 *
 * @author ELCOT
 */
public class ResizeUndo extends Undoable{
    int paddX,paddY;
    Mat lossless;
    Layer l;
    public ResizeUndo(int paddX,int paddY,Mat lossless,Layer l,WorkPlace place) {
        super(place);
        this.paddX=paddX;
        this.paddY=paddY;
        this.l=l;    
        this.lossless=lossless;
    }

    @Override
    public void undo() {
        l.setLossless(lossless);
        if(paddX>0){this.place.shrinkWidth(l,paddX);}
        if(paddX<0){this.place.expandWidth(l,-1*paddX);}
        if(paddY>0){this.place.shrinkHeight(l,paddY);}
        if(paddY<0){this.place.expandHeight(l,-1*paddY);} 
        l.saveLossless();
        saveLossless.setVisible(false);
    }

    @Override
    public boolean isSame() {
        return false;
    }
    
}
