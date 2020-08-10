/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StatePackage;

import javafx.scene.input.MouseEvent;
import LayerPackage.Layer;
import photoic.WorkPlace;

/**
 *
 * @author ELCOT
 */
public class StateTempSave extends State {
    Layer l;
    public StateTempSave(WorkPlace place,Layer l) {
        super(place);
        this.l=l;
    }

    @Override
    public void onMouseDragged(MouseEvent event, Layer l) {
    }

    @Override
    public void onMousePressed(MouseEvent event, Layer l) {
    }

    @Override
    public void onMouseReleased(MouseEvent event, Layer l) {
    }

    @Override
    public void onMouseClicked(MouseEvent event, Layer l) {
    }

    @Override
    public void onMouseMoved(MouseEvent event, Layer l) {
    }

    @Override
    public void saveState() {
       //l.saveLossless();
    }

    @Override
    public void deletePressed() {
        
     }
    
}
