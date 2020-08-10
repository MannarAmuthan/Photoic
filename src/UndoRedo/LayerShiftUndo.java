/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UndoRedo;

import LayerPackage.Layer;
import photoic.Photoic;
import photoic.WorkPlace;

/**
 *
 * @author ELCOT
 */
public class LayerShiftUndo extends Undoable{
    Layer l;
    int oldpos,newpos;
    Photoic p;
    public LayerShiftUndo(WorkPlace place,Photoic p,Layer l,int oldpos,int newpos) {
        super(place);
        this.oldpos=oldpos;
        this.newpos=newpos;
        this.l=l;
        this.p=p;
    }

    @Override
    public void undo() {
        p.moveLayer(l, newpos, oldpos);
        place.update();
        
    }

    @Override
    public boolean isSame() {
        return false;
    }
    
}
