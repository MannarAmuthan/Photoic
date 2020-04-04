/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectManager;


import LayerPackage.BlendMode;
import LayerPackage.BlurMode;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.text.Font;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import LayerPackage.Layer;
import LayerPackage.LayerContainer;
import LayerPackage.StyleObject;
import LayerPackage.TextLayer;
import javafx.scene.paint.Color;
import photoic.Universal;

/**
 *
 * @author ELCOT
 */

public class SerializableObject {
int layerType; // 1 for ImageLayer 0 for Textlayer 1 
public LayerContainer preview;
public Rect rectangle;
public String name;

// for TextLayer --
public String content;
public java.awt.Color fontColor; 
public String fontName;
public String fontFamily;
public int fontSize;
public double LineSpace;
public double letterSpace;


//style
public int StyleBlendMode,StyleBlurMode,StyleBlurAmount,StyleStrokeWidth;
public boolean StyleFill,StyleStroke;
public Color StyleFillColor,StyleStrokeColor;


//for only layer, it is enough

public int canvasWidth;
public int canvasHeight;
public int oriWidth;
public int oriHeight;

public SerializableObject(){}



public SerializableObject(Layer la){
canvasWidth=Universal.canvasWidth;
canvasHeight=Universal.canvasHeight;
oriWidth=Universal.oriWidth;
oriHeight=Universal.oriHeight;

layerType=0;
preview=la.getPreview();
rectangle=la.rectangle;
StyleObject styleObj = la.getStyleObj();
name=la.getName();


BlendMode nn = styleObj.blendMode;
if(nn==BlendMode.NORMAL){StyleBlendMode=1;}if(nn==BlendMode.MULTIPLY){StyleBlendMode=2;}
if(nn==BlendMode.LIGHTEN){StyleBlendMode=3;}if(nn==BlendMode.DARKEN){StyleBlendMode=4;}

BlurMode nnn=styleObj.blurMode;
if(nnn==BlurMode.NONE){StyleBlurMode=1;}if(nnn==BlurMode.AVERAGE){StyleBlurMode=2;}
if(nnn==BlurMode.GAUSSIAN){StyleBlurMode=3;}if(nnn==BlurMode.MEDIAN){StyleBlurMode=4;}

StyleBlurAmount=styleObj.blurAmound;
StyleStrokeWidth=styleObj.StrokeWidth;
StyleFill=styleObj.fillProperty;
StyleStroke=styleObj.strokeProperty;
StyleFillColor=styleObj.fillColor;
StyleStrokeColor=styleObj.strokeColor;


if(la instanceof TextLayer){
layerType=1;
TextLayer l=(TextLayer) la;   
content=l.getContent();
fontColor=l.getFontColor(); 
if(l.getFont()!=null){fontName=l.getFont().getName();fontFamily=l.getFont().getFamily();}
else{fontName=Universal.font;fontFamily=Universal.font;}
fontSize=l.getFontSize();
LineSpace=l.getLineSpace();
letterSpace=l.getLetterSpace();}
}
    
}

