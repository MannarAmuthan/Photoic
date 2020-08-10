/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StatePackage;

import javafx.scene.input.MouseEvent;
import LayerPackage.Layer;
import UndoRedo.MoveUndo;
import photoic.Universal;
import photoic.WorkPlace;

/**
 *
 * @author ELCOT
 */
public class StateMove extends State{
    int movedX,movedY;
    int moveAmountSide,moveAmountStraight;
    public StateMove(WorkPlace place){
    super(place);
    moveAmountSide=5;
    moveAmountStraight=5;
    }
    @Override
    public void onMouseDragged(MouseEvent event,Layer l) {
                    if(mouseHolded){
                    System.out.println("moved "+event.getX()+" "+event.getY());
              double newX=event.getX();
              double newY=event.getY();
              if(newX>lastX){place.moveRight(l,moveAmountSide);movedX=movedX+moveAmountSide;}
              if(newX<lastX){place.moveLeft(l,moveAmountSide);movedX=movedX-moveAmountSide;}
              if(newY<lastY){place.moveUp(l,moveAmountStraight);movedY=movedY+moveAmountStraight;}
              if(newY>lastY){place.moveDown(l,moveAmountStraight);movedY=movedY-moveAmountStraight;} 
              lastX=newX;lastY=newY;
            }
                    place.update();
    }

    @Override
    public void onMousePressed(MouseEvent event,Layer l) {
         mouseHolded=true;
         movedX=0;
         movedY=0;
         
    }

    @Override
    public void onMouseReleased(MouseEvent event,Layer l) {
        mouseHolded=false;
        Universal.addUndo(new MoveUndo(movedX,movedY,l,place));
        movedX=0;
        movedY=0;
        place.update();
    }

    @Override
    public void onMouseClicked(MouseEvent event,Layer l) {
    }

    @Override
    public void onMouseMoved(MouseEvent event, Layer l) {
    }

    @Override
    public void saveState() {
    }

    @Override
    public void deletePressed() {
    }


}
