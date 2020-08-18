/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StatePackage;

import LayerPackage.ImageLayer;
import LayerPackage.Layer;
import LayerPackage.TextLayer;
import UndoRedo.LayerStateUndo;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import photoic.ImageUtilities;
import photoic.Universal;
import photoic.WorkPlace;
import windows.AlertDialogue;

/**
 * @author ELCOT
 */
public class StateText extends State{
	 Layer l;
	 boolean holded, drawed;
	 public Point lastPoint;
	 Rect textBound;
	 Point current;
	 ContextMenu menu;

	 public StateText(WorkPlace place){
		  super(place);
		  lastPoint = new Point();
		  menu = new ContextMenu();
		  MenuItem cancel = new MenuItem("Cancel All ");
		  MenuItem edit = new MenuItem("Edit Text..");
		  MenuItem alig = new MenuItem("Edit Allignment");
		  textBound = new Rect();
		  menu.getItems().addAll(edit, alig, cancel);
		  place.getCanvas().setOnContextMenuRequested((ContextMenuEvent event) -> {
			   menu.show(place.getMainStage(), event.getScreenX(), event.getSceneY());
		  });
		  cancel.setOnAction((ActionEvent event) -> {
			   drawed = false;
			   l.clearMat();
			   place.update();
		  });
		  edit.setOnAction((ActionEvent event) -> {
			   if (l != null){
					Editor.launch(place, (TextLayer) l, textBound);
			   } else if (place.selected instanceof TextLayer){
					l = place.selected;
					Editor.launch(place, (TextLayer) l, textBound);
			   }
		  });
		  alig.setOnAction((ActionEvent event) -> {
		  });
	 }

	 @Override
	 public void onMouseDragged(MouseEvent event, Layer l){
		  if (l instanceof ImageLayer){
			   return;
		  }
		  if (holded){
			   this.l = l;
			   l.clearMat();
			   double x = event.getX() + l.rectangle.x;
			   double y = event.getY() + l.rectangle.y;
			   current = new Point(x, y);
			   System.out.println("drawn.. " + current);
			   textBound.x = (int) lastPoint.x - l.rectangle.x;
			   textBound.y = (int) lastPoint.y - l.rectangle.y;
			   Imgproc.rectangle(l.backstageMat, lastPoint, current, new Scalar(0, 0, 0, 255), 2);
			   drawed = true;
			   place.update();
		  }
	 }

	 @Override
	 public void onMousePressed(MouseEvent event, Layer l){
		  if (l instanceof ImageLayer){
			   return;
		  }
		  if (drawed == false){
			   holded = true;
			   this.l = l;
			   lastPoint.x = event.getX() + l.rectangle.x;
			   lastPoint.y = event.getY() + l.rectangle.y;
			   Universal.addUndo(new LayerStateUndo(place, l, place.layers.indexOf(l)));
		  }
	 }

	 @Override
	 public void onMouseReleased(MouseEvent event, Layer l){
		  if (l instanceof ImageLayer){
			   return;
		  }
		  holded = false;
	 }

	 @Override
	 public void onMouseClicked(MouseEvent event, Layer l){
	 }

	 @Override
	 public void onMouseMoved(MouseEvent event, Layer l){
	 }

	 @Override
	 public void deletePressed(){
	 }

	 @Override
	 public void saveState(){
	 }
}


class Editor{
	 static Stage stage;

	 Editor(){
	 }

	 static void launch(WorkPlace p, TextLayer l, Rect m){
		  if (stage == null){
			   stage = new Stage();
		  }
		  if (stage.isShowing()){
			   stage.close();
			   return;
		  }
		  VBox main = new VBox();
		  HBox h = new HBox();
		  TextField size = new TextField();
		  Label label = new Label("Size");
		  h.getChildren().addAll(label, size);
		  h.setSpacing(10);
		  ScrollPane pane = new ScrollPane();
		  pane.setMaxHeight(200);
		  pane.setMaxWidth(300);
		  TextArea area = new TextArea();
		  area.setMaxHeight(200);
		  area.setMaxWidth(300);
		  pane.setContent(area);
		  Button close = new Button("Close");
		  Button commit = new Button("Commit");
		  close.setOnAction((ActionEvent event) -> {
			   int fontsize = Integer.parseInt(size.getText());
			   l.backstageMat = Editor.putText(l, m, area.getText(), fontsize);
			   l.setMat(l.backstageMat, l.rectangle);
			   stage.close();
		  });
		  size.setText(String.valueOf(l.getFontSize()));
		  area.setText(l.getContent());
		  commit.setOnAction((ActionEvent event) -> {
			   l.clearMat();
			   l.setContent(area.getText());
			   int fontsize = 0;
			   try{
					fontsize = Integer.parseInt(size.getText());
					l.setFontSize(fontsize);
					if (l.getFont() == null)
						 l.setFont(Font.font(Universal.font));
			   } catch (Exception e){
					AlertDialogue.doAlert("Please fill valid size (Integer)");
			   }
			   //Imgproc.putText(l.backstageMat, area.getText(),new Point(m.x,m.y+fontsize) ,Imgproc.FONT_HERSHEY_PLAIN,fontsize,ImageUtilities.getScalar(Universal.currentColor),2);
			   l.backstageMat = Editor.putText(l, m, area.getText(), fontsize);
			   l.setMat(l.backstageMat, l.rectangle);
			   // l.stageMat=l.backstageMat.submat(l.rectangle);
			   p.update();
		  });
		  main.setSpacing(10);
		  main.getChildren().addAll(h, pane, new HBox(close, commit));
		  Scene sc = new Scene(main);
		  stage.setScene(sc);
		  stage.show();
	 }

	 public static Mat putText(TextLayer l, Rect m, String s, int size){
		  Canvas c = new Canvas(l.backstageMat.width(), l.backstageMat.height());
		  GraphicsContext gc = c.getGraphicsContext2D();
		  gc.setFill(Color.TRANSPARENT);
		  gc.fillRect(0, 0, c.getWidth(), c.getHeight());
		  Font font = Font.font(Universal.font, size);
		  gc.setFont(font);
		  gc.setFill(Universal.currentColor);
		  gc.fillText(s, m.x + l.rectangle.x, m.y + l.rectangle.y + size);
		  SnapshotParameters params = new SnapshotParameters();
		  params.setFill(Color.TRANSPARENT);
		  Image snapshot = c.snapshot(params, null);
		  Mat mat = ImageUtilities.getMat(snapshot);
		  return mat;
	 }
}