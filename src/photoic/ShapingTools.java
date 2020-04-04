/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package photoic;

import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

/**
 * @author ELCOT
 */
public class ShapingTools{
	 static ArrayList<ToggleButton> list;
	 static ToggleButton rectangle, elipse, polygon;

	 static ArrayList<ToggleButton> getToggleButtons(){
		  rectangle = new ToggleButton();
		  rectangle.setTooltip(new Tooltip("Rectangle"));
		  rectangle.setId("Rectangle");
		  ImageView rec = new ImageView("Images/rectangle.png");
		  rec.setFitHeight(22);
		  rec.setFitWidth(22);
		  rectangle.setGraphic(rec);
		  rectangle.setFocusTraversable(false);
		  elipse = new ToggleButton();
		  elipse.setTooltip(new Tooltip("Elipse"));
		  elipse.setId("Elipse");
		  ImageView cir = new ImageView("Images/circle.png");
		  cir.setFitHeight(22);
		  cir.setFitWidth(22);
		  elipse.setGraphic(cir);
		  elipse.setFocusTraversable(false);
		  list = new ArrayList<>();
		  list.add(rectangle);
		  list.add(elipse);
		  ;
		  return list;
	 }

	 static HBox getShapingTools(){
		  HBox h = new HBox();
		  h.getChildren().addAll(rectangle, elipse);
		  return h;
	 }
}
