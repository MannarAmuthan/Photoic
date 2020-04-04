/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UndoRedo;

import photoic.WorkPlace;

/**
 *
 * @author ELCOT
 */
public abstract class Undoable {
    WorkPlace place;
    public int tag=0;
    public Undoable(WorkPlace place){this.place=place;};
    public abstract void undo();
    public abstract boolean isSame();   
}
