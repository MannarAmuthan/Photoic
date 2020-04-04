/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Spatial;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import LayerPackage.Layer;
import photoic.Photoic;
import photoic.WorkPlace;

/**
 *
 * @author ELCOT
 */
public abstract class SpatialFilter {
    public int amount=1;
    public static Stage stage;
    public static Slider val;
    
    public static  Slider hval,sval,vval;
    public static int h=1,s=1,v=1;
    
    public static Button apply;
    public Photoic p;
    public abstract void filter(int amount,Layer l,int Tag);
    public abstract void launch(Layer l);
    public SpatialFilter(Photoic p){
    this.p=p;
    if(stage==null){stage=new Stage();}
    }
    


       
}
