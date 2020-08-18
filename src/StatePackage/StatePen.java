/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StatePackage;

import LayerPackage.Layer;
import UndoRedo.LayerStateUndo;
import UndoRedo.PenUndo;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.*;
import org.opencv.core.Scalar;
import photoic.ImageUtilities;
import photoic.SelectedRegion;
import photoic.Universal;
import photoic.WorkPlace;

/**
 * @author ELCOT
 */
public class StatePen extends State{
	 SelectedRegion r;
	 boolean connected, completed;
	 ContextMenu menu;

	 public StatePen(WorkPlace place){
		  super(place);
		  r = new SelectedRegion();
		  this.place.addLayer(r.l, 0);
		  menu = new ContextMenu();
		  MenuItem cancel = new MenuItem("Cancel All");
		  MenuItem fill = new MenuItem("Fill");
		  MenuItem stroke = new MenuItem("Stroke");
		  menu.getItems().addAll(cancel, fill, stroke);
		  menu.setHideOnEscape(true);
		  place.getCanvas().setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
			   @Override
			   public void handle(ContextMenuEvent contextMenuEvent){
					menu.show(place.getMainStage(), contextMenuEvent.getScreenX(), contextMenuEvent.getSceneY());
			   }
		  });


		  cancel.setOnAction((ActionEvent event) -> {
			   if (r != null){
					r.l.clearMat();
					r.points.clear();
					r.drawSelected();
					place.update();
					completed = false;
					menu.hide();
			   }
		  });
		  stroke.setOnAction((ActionEvent event) -> {
			   if (r != null){
					Universal.addUndo(new LayerStateUndo(place, place.selected, place.layers.indexOf(place.selected)));
					r.l.clearMat();
					Scalar sc = ImageUtilities.getScalar(Universal.currentColor);
					r.drawStroke(sc, Universal.strokeWidth);
					System.out.println("drawen ii" + r.points.size());
					place.selected.MergeWith(r.l);
					place.deleteLayer(r.l);
					place.update();
					completed = false;
					menu.hide();
			   }
		  });
		  fill.setOnAction((ActionEvent event) -> {
			   if (r != null){
					Universal.addUndo(new LayerStateUndo(place, place.selected, place.layers.indexOf(place.selected)));
					r.l.clearMat();
					Scalar sc = ImageUtilities.getScalar(Universal.currentColor);
					ImageUtilities.fillPoly(r.l.backstageMat, r.points, sc);
					place.selected.MergeWith(r.l);
					place.deleteLayer(r.l);
					place.update();
					completed = false;
					menu.hide();
			   }
		  });
	 }

	 @Override
	 public void onMouseDragged(MouseEvent event, Layer l){
	 }

	 @Override
	 public void onMousePressed(MouseEvent event, Layer l){
	 }

	 @Override
	 public void onMouseReleased(MouseEvent event, Layer l){
	 }

	 @Override
	 public void onMouseClicked(MouseEvent event, Layer l){

			   if (!completed){
					// if pen layer is previously completed, then we have to add for new process
					if (!place.layers.contains(r.l)){
						 r.points.clear();
						 place.addLayer(r.l, 0);
					}
					Universal.addUndo(new PenUndo(place, r));
					r.markPoint((int) event.getX(), (int) event.getY());
					place.update();
			   }
			   if (connected){
					if (r.points.size() > 2 && (r.points.get(r.points.size() - 1) != r.points.get(r.points.size() - 2)))
						 completed = true;
			   }

	 }

	 @Override
	 public void onMouseMoved(MouseEvent event, Layer l){
		  try{
			   if (event.getX() > r.points.get(0).x - 5 && event.getX() < r.points.get(0).x + 5){
					if (event.getY() > r.points.get(0).y - 5 && event.getY() < r.points.get(0).y + 5){
						 connected = true;
					} else
						 connected = false;
			   }
		  } catch (Exception e){
		  }
	 }

	 @Override
	 public void saveState(){
	 }

	 @Override
	 public void deletePressed(){
		  if (completed){
			   r.l.clearMat();
			   ImageUtilities.clearPoly(place.selected.backstageMat, place.selected.rectangle, r.points);
			   place.deleteLayer(r.l);
			   place.update();
			   completed = false;
		  }
	 }
}
