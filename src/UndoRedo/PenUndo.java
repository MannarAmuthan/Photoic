/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UndoRedo;

import photoic.SelectedRegion;
import photoic.WorkPlace;

/**
 *
 * @author ELCOT
 */
public class PenUndo extends Undoable {
    SelectedRegion reg;
    public PenUndo(WorkPlace place,SelectedRegion reg) {
        super(place);
        this.reg=reg;
    }

    @Override
    public void undo() {
        reg.removeLastPoint();
        place.update();
    }

    @Override
    public boolean isSame() {
        return false;
    }
    
}
