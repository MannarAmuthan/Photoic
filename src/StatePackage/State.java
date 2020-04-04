/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StatePackage;

import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import LayerPackage.Layer;
import photoic.WorkPlace;

/**
 *
 * @author ELCOT
 */
enum Arrow{UP,DOWN,LEFT,RIGHT};
abstract public class State {
    
double lastX=-1,lastY=-1;
double prevx,prevy;
boolean mouseHolded=false;
public Arrow arrow;
WorkPlace place;
public State(WorkPlace place){
this.place=place;
}
abstract public void onMouseDragged(MouseEvent event,Layer l);
abstract public void onMousePressed(MouseEvent event,Layer l);
abstract public void onMouseReleased(MouseEvent event,Layer l);
abstract public void onMouseClicked(MouseEvent event,Layer l);   
abstract public void onMouseMoved(MouseEvent event,Layer l);
abstract public void deletePressed();
abstract public void saveState();
}
