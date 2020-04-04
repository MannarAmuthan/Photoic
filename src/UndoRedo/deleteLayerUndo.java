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
public class deleteLayerUndo extends Undoable {
    Photoic p;
    int index;
    Layer l;
    public deleteLayerUndo(WorkPlace place,Photoic p,Layer l,int index) {
        super(place);
        this.p=p;
        this.l=l;
        this.index=index;
    }

    @Override
    public void undo() {
        p.addLayer(l,index);
        
    }

    @Override
    public boolean isSame() {
        return false;
    }
    
}
