/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LayerPackage;

import javafx.scene.text.Font;
import photoic.ImageUtilities;

import java.awt.*;

/**
 * @author ELCOT
 */
public class TextLayer extends Layer{
	 public String content;
	 public Color fontColor;
	 public Font font;
	 public double LineSpace;
	 public double letterSpace;
	 public int fontSize;

	 public TextLayer(String name, int type){
		  super(name, type);
		  backstageMat = ImageUtilities.getPlainImage();
		  backstageMat = ImageUtilities.adjustImage(backstageMat);
		  stageMat = backstageMat.submat(rectangle);
		  saveLossless();
	 }

	 @Override
	 public void drawScale(){
	 }

	 @Override
	 public void opacity(int op){
	 }

	 @Override
	 public void updatePreview(){
	 }

	 @Override
	 public LayerContainer getPreview(){
		  return preview;
	 }

	 @Override
	 public void updateMat(){
	 }

	 public Color getFontColor(){
		  return fontColor;
	 }

	 public void setFontColor(Color fontColor){
		  this.fontColor = fontColor;
	 }

	 public Font getFont(){
		  return font;
	 }

	 public void setFont(Font font){
		  this.font = font;
	 }

	 public double getLineSpace(){
		  return LineSpace;
	 }

	 public void setLineSpace(double LineSpace){
		  this.LineSpace = LineSpace;
	 }

	 public double getLetterSpace(){
		  return letterSpace;
	 }

	 public void setLetterSpace(double letterSpace){
		  this.letterSpace = letterSpace;
	 }

	 public String getContent(){
		  return content;
	 }

	 public void setContent(String content){
		  this.content = content;
	 }

	 public int getFontSize(){
		  return fontSize;
	 }

	 public void setFontSize(int fontSize){
		  this.fontSize = fontSize;
	 }
}

