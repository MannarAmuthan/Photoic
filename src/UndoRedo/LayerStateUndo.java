/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UndoRedo;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import LayerPackage.Layer;
import photoic.WorkPlace;

/**
 *
 * @author ELCOT
 */
public class LayerStateUndo extends Undoable {
    Layer old;
    Mat m;
    Rect r;
    int index;
    public LayerStateUndo(WorkPlace place,Layer old,int index) {
        super(place);
        this.old=old;
        m=old.backstageMat.clone();
        r=old.rectangle.clone();
    }

    @Override
    public void undo() {
        old.setMat(m, r);
        place.mainWindow.deleteLayer(place.selected);
        place.mainWindow.addLayer(old, index);
        place.selected=old;
        place.update();
    }

    @Override
    public boolean isSame() {
        return false;
    }
    
}
