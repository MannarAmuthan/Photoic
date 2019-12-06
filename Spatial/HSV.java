/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Spatial;

import static Spatial.SpatialFilter.apply;
import static Spatial.SpatialFilter.stage;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import LayerPackage.Layer;
import photoic.Photoic;
import UndoRedo.LayerStateUndo;
import photoic.Universal;
import photoic.WorkPlace;
import static Spatial.SpatialFilter.val;

/**
 *
 * @author ELCOT
 */
public class HSV extends SpatialFilter {
    Mat performed;
    int ker;
    public HSV(Photoic p) {
        super(p);
    }
    
        
    
    @Override
    public void launch(Layer l){
    if(stage.isShowing()){stage.close();}    
   
    
    hval=new Slider();hval.setMax(100);hval.setMin(1);hval.setValue(h);hval.showTickLabelsProperty();
    sval=new Slider();sval.setMax(100);sval.setMin(1);sval.setValue(s);sval.showTickLabelsProperty();
    vval=new Slider();vval.setMax(100);vval.setMin(1);vval.setValue(v);vval.showTickLabelsProperty();
    
    Label hlabel=new Label(String.valueOf(h));
    Label slabel=new Label(String.valueOf(s));
    Label vlabel=new Label(String.valueOf(v));
    
    hval.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
     int am=(int)((double)newValue);
     h =am;
     hlabel.setText(String.valueOf(am));
     this.filter(am,l,0);
    });
    
    sval.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
     int am=(int)((double)newValue);
     s =am;
     slabel.setText(String.valueOf(am));
     this.filter(am,l,1);
    });
    
    vval.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
     int am=(int)((double)newValue);
     v =am;
     vlabel.setText(String.valueOf(am));
     this.filter(am,l,2);
    });
    
    apply=new Button("Apply");
    
    VBox main=new VBox();
    main.setSpacing(10);
    
    main.getChildren().addAll(hval,hlabel,apply);
    Scene sc=new Scene(main);
    
    stage.setScene(sc);
    stage.setMinHeight(100);
    stage.setMinWidth(300);
    stage.setTitle("Filter Window");
    stage.show();
    }


    @Override
    public void filter(int amount,Layer l,int tag) {
        if(amount==1){l.stageMat=l.backstageMat.clone().submat(l.rectangle);}
        
        else
        {
        if(performed==null){performed=l.stageMat;}
        System.out.println("filtering.. "+p.place.layers.indexOf(l));
        l.stageMat=l.backstageMat.clone().submat(l.rectangle);
        int imgSize=Math.min(l.backstageMat.width(), l.backstageMat.height());
        ker=amount*5;
        try{
        l.stageMat.convertTo(l.stageMat,-1,1, ker);performed=l.stageMat;}catch(Exception e){l.stageMat=performed;}
        apply.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            @SuppressWarnings("empty-statement")
            public void handle(ActionEvent event) {
            try{
                Universal.addUndo(new LayerStateUndo(p.place,l,p.place.layers.indexOf(l)));
                 l.backstageMat.convertTo(l.backstageMat,-1,1, ker);}catch(Exception e){}
                 
                p.place.update();
                stage.close();
            }
        });
        
        }
        
        stage.setOnHiding((WindowEvent event) -> {
        l.stageMat=l.backstageMat.clone().submat(l.rectangle);
        p.place.update();
        });
        
        p.place.update();
    }
    
}
