/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UndoRedo;

import org.opencv.core.Point;
import LayerPackage.Layer;
import photoic.WorkPlace;

/**
 *
 * @author ELCOT
 */
public class MoveUndo extends Undoable {
    int movedX,movedY;
    Layer l;
    public MoveUndo(int movedX,int movedY,Layer l,WorkPlace place){
    super(place);    
    this.movedX=movedX;
    this.movedY=movedY;
    this.l=l;
    }

    @Override
    public void undo() {
     this.place.shiftBy(l,-1*movedX,-1*movedY);
    }

    @Override
    public boolean isSame() {
        return false;
    }

}
