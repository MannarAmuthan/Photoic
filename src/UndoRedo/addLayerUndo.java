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
public class addLayerUndo extends Undoable {
    Photoic p;
    Layer l;
    public addLayerUndo(WorkPlace place,Photoic p,Layer l) {
        super(place);
        this.p=p;
        this.l=l;
    }

    @Override
    public void undo() {
        p.deleteLayer(l);
    }

    @Override
    public boolean isSame() {
        return false;
    }
    
}
