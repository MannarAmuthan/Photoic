/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectManager;

import Brushes.Brush;
import LayerPackage.BlendMode;
import LayerPackage.BlurMode;
import LayerPackage.ImageLayer;
import LayerPackage.Layer;
import LayerPackage.StyleObject;
import LayerPackage.TextLayer;
import UndoRedo.Undoable;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import photoic.ImageUtilities;

/**
 *
 * @author ELCOT
 */
public class Deserializer {
    static public ArrayList<Layer>layers=new ArrayList<>();
    public static int canvasWidth;
    public static int canvasHeight;
    public static int oriWidth;
    public static int oriHeight;
    
    public static void deserialize(String json,HashMap<String,Mat> images){
    Gson gson=new Gson();
                    
    ArrayList<SerializableObject> l=new ArrayList<>();   
    l=gson.fromJson(json,l.getClass());
    for(int i=0;i<l.size();i++){
        Layer layerObj=null;
        SerializableObject layer; 
        layer=gson.fromJson(gson.toJson(l.get(i)),SerializableObject.class);
        if(layer.layerType==0){layerObj=new ImageLayer(layer.name,1);}
        if(layer.layerType==1){layerObj=new TextLayer(layer.name,1);}
        layerObj.preview=layer.preview;
        layerObj.rectangle=layer.rectangle;
        
        layerObj.styleObj=new StyleObject(); 
        
        
        int nn=layer.StyleBlendMode;
        if(nn==1){layerObj.styleObj.setBlendMode(BlendMode.NORMAL);}if(nn==2){layerObj.styleObj.setBlendMode(BlendMode.MULTIPLY);}
        if(nn==3){layerObj.styleObj.setBlendMode(BlendMode.LIGHTEN);}if(nn==4){layerObj.styleObj.setBlendMode(BlendMode.DARKEN);}
        
        int nnn=layer.StyleBlurMode;
        if(nnn==1){layerObj.styleObj.setBlurMode(BlurMode.NONE);}if(nnn==2){layerObj.styleObj.setBlurMode(BlurMode.AVERAGE);}
        if(nnn==3){layerObj.styleObj.setBlurMode(BlurMode.GAUSSIAN);}if(nnn==4){layerObj.styleObj.setBlurMode(BlurMode.MEDIAN);}
        
        
        layerObj.styleObj.setBlurAmound(layer.StyleBlurAmount);
        layerObj.styleObj.setFill(layer.StyleFill);
        layerObj.styleObj.setStroke(layer.StyleStroke);
        
        Color fillC=getColor(layer.StyleFillColor);
        Color strokeC=getColor(layer.StyleStrokeColor);
        layerObj.styleObj.setFillColor(fillC);
        layerObj.styleObj.setStrokeColor(strokeC);
        layerObj.styleObj.setStrokeWidth(layer.StyleStrokeWidth);
        
       
        
        
        layerObj.setMat(images.get(layerObj.name), layerObj.rectangle);
        
        canvasWidth=layer.canvasWidth;
        canvasHeight=layer.canvasHeight;
        oriHeight=layer.oriHeight;
        oriWidth=layer.oriWidth;
        
        if(layer.layerType==1){
        TextLayer m=(TextLayer)layerObj;
        m.LineSpace=layer.LineSpace;
        m.content=layer.content;
        m.fontColor=layer.fontColor;
        m.fontSize=layer.fontSize;
        m.letterSpace=layer.letterSpace;
        m.font=Font.font(layer.fontFamily);
        layers.add(m);
        continue;
        }
        layers.add(layerObj);    
    }    
    }
    
    static double[] convertRange(double[] d){
      d[0]=d[0]/255.0;
      d[1]=d[1]/255.0;
      d[2]=d[2]/255.0;
      d[3]=d[3]/255.0;
      return d;
      }
      
      static Color getColor(Color c){
      Scalar f=ImageUtilities.getScalar(c);    
      double d[]=convertRange(f.val);    
      Scalar sc=new Scalar(d[2],d[1],d[0],d[3]);
      return ImageUtilities.getColor(sc);
      }
    
    
}
