/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package photoic;

import Brushes.PlainBrush;
import LayerPackage.Layer;
import LayerPackage.LayerStyle;
import StatePackage.*;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.opencv.core.Mat;

import java.util.ArrayList;

/**
 * @author ELCOT
 */
enum MouseState{MOVE, BRUSH, ERASER, PENTOOL, PAINTFILL, SAVETEMP, S_RECT, S_SQU, S_ELI, S_POLY, TEXT}


public class WorkPlace{
	 public Canvas canvas;
	 public GraphicsContext context;
	 public ArrayList<Layer> layers;
	 public Layer selected;
	 Pane root;
	 public MouseState mouseState;
	 State state;
	 public Photoic mainWindow;

	 public Canvas getCanvas(){
		  return canvas;
	 }

	 public void resize(int w, int h){
		  canvas.setHeight(h);
		  canvas.setWidth(w);
		  Universal.canvasHeight = h;
		  Universal.canvasWidth = w;
		  refresh(Color.WHITE);
	 }

	 WorkPlace(int w, int h){
		  canvas = new Canvas(w, h);
		  Universal.canvasHeight = h;
		  Universal.canvasWidth = w;
		  context = canvas.getGraphicsContext2D();
		  refresh(Color.WHITE);
		  layers = new ArrayList<>();
		  root = new Pane();
		  this.mouseState = MouseState.MOVE;
		  Universal.brush = PlainBrush.getInstance();
		  setOnActions();
	 }

	 public Pane getPane(){
		  root.setStyle("-fx-padding: 10;");
		  refresh(Color.WHITE);
		  root.getChildren().add(this.getCanvas());
		  return root;
	 }

	 public Photoic getMainWindow(){
		  return mainWindow;
	 }

	 public Stage getMainStage(){
		  return mainWindow.stage;
	 }

	 public void refresh(Color c){
		  context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		  context.setFill(c);
		  context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
	 }

	 public void putImage(Image image){
		  context.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight());
	 }

	 public void addLayer(Layer r, int index){
		  this.layers.add(index, r);
		  update();
	 }

	 public void deleteLayer(Layer r){
		  this.layers.remove(r);
		  update();
	 }

	 public void moveLeft(Layer l, int count){
		  l.left(count);
		  update();
	 }

	 public void moveUp(Layer l, int count){
		  l.up(count);
		  update();
	 }

	 public void moveDown(Layer l, int count){
		  l.down(count);
		  update();
	 }

	 public void moveRight(Layer l, int count){
		  l.right(count);
		  update();
	 }

	 public void shiftBy(Layer l, int x, int y){
		  l.shiftBy(x, y);
		  update();
	 }

	 public void shrinkWidth(Layer l, int size){
		  SwitchState(MouseState.SAVETEMP);
		  Universal.inLosslessMode = true;
		  l.shrinkW(size);
		  update();
	 }

	 public void shrinkHeight(Layer l, int size){
		  SwitchState(MouseState.SAVETEMP);
		  l.shrinkH(size);
		  update();
	 }

	 public void expandWidth(Layer l, int size){
		  SwitchState(MouseState.SAVETEMP);
		  l.expandW(size);
		  update();
	 }

	 public void expandHeight(Layer l, int size){
		  SwitchState(MouseState.SAVETEMP);
		  l.expandH(size);
		  update();
	 }

	 public void shrinkRatio(Layer l){
		  SwitchState(MouseState.SAVETEMP);
		  l.shrinkRatio();
		  update();
	 }

	 public void expandRatio(Layer l){
		  SwitchState(MouseState.SAVETEMP);
		  l.expandRatio();
		  update();
	 }

	 public void saveState(){
		  state.saveState();
	 }

	 public void saveStateShow(){
		  new Photoic().saveLossless.setVisible(true);
	 }

	 public void update(){
		  refresh(Color.WHITE);
		  Mat mat = null;
		  for (int i = layers.size() - 1; i > -1; i--){
			   mat = layers.get(i).getMat();
			   mat = LayerStyle.applyStyle(mat, layers.get(i).getStyleObj());
			   Image fin = ImageUtilities.getImage(mat);
			   context.drawImage(fin, 0, 0);
		  }
	 }

	 public void SwitchState(MouseState newState){
		  if (newState != mouseState){
			   state.saveState();
		  }
		  if (newState == MouseState.MOVE){
			   state = new StateMove(this);
			   mouseState = MouseState.MOVE;
		  }
		  if (newState == MouseState.PENTOOL){
			   state = new StatePen(this);
			   mouseState = MouseState.PENTOOL;
		  }
		  if (newState == MouseState.BRUSH){
			   state = new StateBrush(this);
			   mouseState = MouseState.BRUSH;
		  }
		  if (newState == MouseState.ERASER){
			   state = new StateEraser(this);
			   mouseState = MouseState.ERASER;
		  }
		  if (newState == MouseState.S_RECT){
			   state = new StateShapeRect(this);
			   mouseState = MouseState.S_RECT;
		  }
		  if (newState == MouseState.S_ELI){
			   state = new StateShapeElipse(this);
			   mouseState = MouseState.S_ELI;
		  }
		  if (newState == MouseState.TEXT){
			   state = new StateText(this);
			   mouseState = MouseState.TEXT;
		  }
		  if (newState == MouseState.PAINTFILL){
			   state = new StateColorPicker(this);
			   mouseState = MouseState.PAINTFILL;
		  }
		  if (newState == MouseState.SAVETEMP){
			   if (!(state instanceof StateTempSave)) state = new StateTempSave(this, selected);
			   saveStateShow();
			   mouseState = MouseState.SAVETEMP;
		  }
	 }

	 private void setOnActions(){
		  root.setOnMouseDragged((MouseEvent event) -> {
			   if (selected != null){
					state.onMouseDragged(event, selected);
			   }
		  });
		  root.setOnMousePressed((MouseEvent event) -> {
			   if (selected != null)
					state.onMousePressed(event, selected);
		  });
		  root.setOnMouseReleased((MouseEvent event) -> {
			   if (selected != null)
					state.onMouseReleased(event, selected);
		  });
		  root.setOnMouseClicked((MouseEvent event) -> {
			   if (selected != null)
					state.onMouseClicked(event, selected);
		  });
		  root.setOnMouseMoved((MouseEvent event) -> {
			   if (selected != null)
					state.onMouseMoved(event, selected);
		  });
	 }

	 public Image merge(ArrayList<Layer> list){
		  refresh(Color.TRANSPARENT);
		  Mat mat = null;
		  for (int i = layers.size() - 1; i > -1; i--){
			   if (list.contains(layers.get(i))){
					mat = layers.get(i).getMat();
					mat = LayerStyle.applyStyle(mat, layers.get(i).getStyleObj());
					Image fin = ImageUtilities.getImage(mat);
					context.drawImage(fin, 0, 0);
			   }
		  }
		  SnapshotParameters params = new SnapshotParameters();
		  params.setFill(Color.TRANSPARENT);
		  Image snapshot = this.canvas.snapshot(params, null);
		  this.update();
		  return snapshot;
	 }
}
