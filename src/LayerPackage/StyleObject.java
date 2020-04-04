/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LayerPackage;

import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * @author ELCOT
 */
// this object will be used when image is going to shown in canvas (update()-in workspace class)
//using layerstyle these parameters were setted, it wont'modify the image in real Layer
public class StyleObject{
	 public BlendMode blendMode;/// priority index=0
	 public BlurMode blurMode;/// priority index=1
	 public int blurAmound;
	 public boolean fillProperty, strokeProperty;//priority index=2 and 3
	 public Color fillColor;
	 public Color strokeColor;
	 public int StrokeWidth;

	 public StyleObject(){
		  blendMode = BlendMode.NORMAL;
		  blurMode = BlurMode.NONE;
		  blurAmound = 0;
		  fillProperty = false;
		  strokeProperty = false;
		  fillColor = Color.BLACK;
		  strokeColor = Color.BLACK;
		  StrokeWidth = 1;
	 }

	 public StyleObject(BlendMode blendMode, BlurMode blurMode, int blurAmound, boolean fill, boolean stroke, Color fillColor, Color strokeColor, ArrayList<Integer> order){
		  this.blendMode = blendMode;
		  this.blurMode = blurMode;
		  this.blurAmound = blurAmound;
		  this.fillProperty = fill;
		  this.strokeProperty = stroke;
		  this.fillColor = fillColor;
		  this.strokeColor = strokeColor;
	 }

	 public BlendMode getBlendMode(){
		  return blendMode;
	 }

	 public void setBlendMode(BlendMode blendMode){
		  this.blendMode = blendMode;
	 }

	 public BlurMode getBlurMode(){
		  return blurMode;
	 }

	 public void setBlurMode(BlurMode blurMode){
		  this.blurMode = blurMode;
	 }

	 public int getBlurAmound(){
		  return blurAmound;
	 }

	 public void setBlurAmound(int blurAmound){
		  this.blurAmound = blurAmound;
	 }

	 public boolean isFill(){
		  return fillProperty;
	 }

	 public void setFill(boolean fill){
		  this.fillProperty = fill;
	 }

	 public boolean isStroke(){
		  return strokeProperty;
	 }

	 public void setStroke(boolean stroke){
		  this.strokeProperty = stroke;
	 }

	 public Color getFillColor(){
		  return fillColor;
	 }

	 public void setFillColor(Color fillColor){
		  this.fillColor = fillColor;
	 }

	 public Color getStrokeColor(){
		  return strokeColor;
	 }

	 public void setStrokeColor(Color strokeColor){
		  this.strokeColor = strokeColor;
	 }

	 public int getStrokeWidth(){
		  return StrokeWidth;
	 }

	 public void setStrokeWidth(int StrokeWidth){
		  this.StrokeWidth = StrokeWidth;
	 }

	 @Override
	 public String toString(){
		  return "StyleObject{" + "blendMode=" + blendMode + ", blurMode=" + blurMode + ", blurAmound=" + blurAmound + ", fillProperty=" + fillProperty + ", strokeProperty=" + strokeProperty + ", fillColor=" + fillColor + ", strokeColor=" + strokeColor + ", StrokeWidth=" + StrokeWidth + '}';
	 }
}
