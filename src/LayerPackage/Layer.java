/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LayerPackage;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import photoic.ImageUtilities;
import photoic.Universal;

import java.io.Serializable;

/**
 * @author ELCOT
 */
enum LayerBlend{NORMAL, MULTIPLY};


enum hiddenProp{VISIBLE, INVISIBLE};


public abstract class Layer implements Serializable{
	 public LayerContainer preview;
	 public Mat stageMat, backstageMat, losslessMat;
	 public Rect rectangle;
	 public StyleObject styleObj;
	 public String name;
	 public int type; //1-solid layer, 0-vitual layer

	 abstract public void drawScale();
	 abstract public void updateMat();
	 abstract public void opacity(int op);
	 abstract public void updatePreview();
	 abstract public LayerContainer getPreview();

	 Layer(String n, int type){
		  if (n == null || n.equals("")){
			   n = "Layer " + Universal.currentLayer;
			   Universal.currentLayer = Universal.currentLayer + 1;
		  }
		  preview = new LayerContainer(n);
		  name = this.preview.getLayerName();
		  rectangle = new Rect();
		  rectangle.x = 0;
		  rectangle.y = 0;
		  rectangle.height = Universal.canvasHeight;
		  rectangle.width = Universal.canvasWidth;
		  stageMat = new Mat(Universal.canvasWidth, Universal.canvasHeight, CvType.CV_8UC4);
		  this.backstageMat = new Mat();
		  losslessMat = new Mat();
		  styleObj = new StyleObject();
		  this.type = type;
	 }

	 public void left(int count){
		  backstageMat = ImageUtilities.ShiftLeft(backstageMat, rectangle, count);
		  //System.out.println("debug.. "+rectangle+" "+backstageMat.width()+" "+backstageMat.height());
		  stageMat = ImageUtilities.adjustImage(backstageMat, rectangle);
		  saveLossless();
	 }

	 public void up(int count){
		  backstageMat = ImageUtilities.ShiftUp(backstageMat, rectangle, count);
		  //System.out.println("debug.. "+rectangle+" "+backstageMat.width()+" "+backstageMat.height());
		  stageMat = ImageUtilities.adjustImage(backstageMat, rectangle);
		  saveLossless();
	 }

	 public void down(int count){
		  backstageMat = ImageUtilities.ShiftDown(backstageMat, rectangle, count);
		  //System.out.println("debug.. "+rectangle+" "+backstageMat.width()+" "+backstageMat.height());
		  stageMat = ImageUtilities.adjustImage(backstageMat, rectangle);
		  saveLossless();
	 }

	 public void right(int count){
		  backstageMat = ImageUtilities.ShiftRight(backstageMat, rectangle, count);
		  //System.out.println("debug.. "+rectangle+" "+backstageMat.width()+" "+backstageMat.height());
		  stageMat = ImageUtilities.adjustImage(backstageMat, rectangle);
		  saveLossless();
	 }

	 public void shiftBy(int x, int y){
		  backstageMat = ImageUtilities.shiftBy(backstageMat, rectangle, x, y);
		  stageMat = ImageUtilities.adjustImage(backstageMat, rectangle);
		  saveLossless();
	 }

	 public void shrinkW(int size){
		  int width = (int) (backstageMat.width() - size);
		  //System.out.println("passed "+width+" "+backstageMat.height()+" "+losslessMat.width()+" "+losslessMat.height());
		  backstageMat = ImageUtilities.shrinkImage(losslessMat, rectangle, width, backstageMat.height());
		  //System.out.println("new bug "+backstageMat.width()+" "+backstageMat.height());
		  //System.out.println("rect "+rectangle.toString());
		  stageMat = ImageUtilities.adjustImage(backstageMat, rectangle);
	 }

	 public void shrinkH(int size){
		  int height = (int) (backstageMat.height() - size);
		  backstageMat = ImageUtilities.shrinkImage(losslessMat, rectangle, backstageMat.width(), height);
		  stageMat = ImageUtilities.adjustImage(backstageMat, rectangle);
	 }

	 public void expandW(int size){
		  int width = (int) (backstageMat.width() + size);
		  backstageMat = ImageUtilities.shrinkImage(losslessMat, rectangle, width, backstageMat.height());
		  stageMat = ImageUtilities.adjustImage(backstageMat, rectangle);
	 }

	 public void expandH(int size){
		  int height = (int) (backstageMat.height() + size);
		  backstageMat = ImageUtilities.shrinkImage(losslessMat, rectangle, backstageMat.width(), height);
		  stageMat = ImageUtilities.adjustImage(backstageMat, rectangle);
	 }

	 public void shrinkRatio(){
		  int height = (int) Math.round(backstageMat.height() * (95.0 / 100));
		  int width = (int) Math.round(backstageMat.width() * (95.0 / 100));
		  System.out.println("try to shrink " + height + " " + width);
		  backstageMat = ImageUtilities.shrinkImage(losslessMat, rectangle, width, height);
		  stageMat = ImageUtilities.adjustImage(backstageMat, rectangle);
	 }

	 public void expandRatio(){
		  int height = (int) (backstageMat.height() * 1.2);
		  int width = (int) (backstageMat.width() * 1.2);
		  backstageMat = ImageUtilities.shrinkImage(losslessMat, rectangle, width, height);
		  stageMat = ImageUtilities.adjustImage(backstageMat, rectangle);
	 }

	 public Mat getMat(){
		  return this.stageMat;
	 }

	 public void setMat(Mat m, Rect rect){
		  backstageMat = m;
		  stageMat = ImageUtilities.adjustImage(backstageMat, rect);
	 }

	 public void clearMat(){
		  backstageMat = ImageUtilities.clear(backstageMat);
		  stageMat = ImageUtilities.adjustImage(backstageMat, rectangle);
	 }

	 public void MergeWith(Layer l){
		  backstageMat = ImageUtilities.merge(backstageMat, rectangle, l.backstageMat);
		  stageMat = ImageUtilities.adjustImage(backstageMat, rectangle);
	 }

	 public void add(Layer l){
		  backstageMat = ImageUtilities.add(l.stageMat, stageMat);
		  rectangle.x = 0;
		  rectangle.y = 0;
		  stageMat = ImageUtilities.adjustImage(backstageMat, rectangle);
	 }

	 @Override
	 public String toString(){
		  return name; //To change body of generated methods, choose Tools | Templates.
	 }

	 public String getName(){
		  return this.name;
	 }

	 public void setName(String name){
		  this.name = name;
	 }

	 public void saveLossless(){
		  losslessMat = backstageMat;
	 }

	 public void setLossless(Mat m){
		  losslessMat = m;
	 }

	 public StyleObject getStyleObj(){
		  return styleObj;
	 }

	 public void setStyleObj(StyleObject styleObj){
		  this.styleObj = styleObj;
	 }
}



