/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UndoRedo;

import org.opencv.core.Point;
import LayerPackage.Layer;
import java.util.ArrayList;
import java.util.Stack;
import photoic.WorkPlace;

/**
 *
 * @author ELCOT
 */
public class MergeUndo extends Undoable {
    Stack<deleteLayerUndo> deleteUndos=new Stack<>();
    LayerStateUndo rootLayerUndo;
    public MergeUndo(WorkPlace place,Layer root){
    super(place);    
    rootLayerUndo=new LayerStateUndo(place,root,place.layers.indexOf(root));
    }

    @Override
    public void undo() {
     rootLayerUndo.undo();   
     while(!deleteUndos.isEmpty()){
     deleteUndos.pop().undo();
     }
     
    }
    
    @Override
    public boolean isSame() {
        return false;
    }
    
    public void addDeleteUndo(deleteLayerUndo u){
    deleteUndos.push(u);
    }

}
