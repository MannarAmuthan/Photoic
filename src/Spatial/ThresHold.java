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
import photoic.ImageUtilities;
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
public class ThresHold extends SpatialFilter {
    Mat performed;
    int ker;
    public ThresHold(Photoic p) {
        super(p);
    }
    
    
    @Override
    public void launch(Layer l){
    if(stage.isShowing()){stage.close();}    
    val=new Slider();
    val.setMax(100);
    val.setMin(1);
    val.setValue(amount);
    val.showTickLabelsProperty();
    Label la=new Label(String.valueOf(amount));
    val.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
     int am=(int)((double)newValue);
     amount=am;
     la.setText(String.valueOf(am));
     this.filter(am,l,0);
    });
    
    apply=new Button("Apply");
    
    VBox v=new VBox();
    v.setSpacing(10);
    v.getChildren().addAll(val,la,apply);
    Scene sc=new Scene(v);
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
        int kernalSize=(int)((amount/100.0)*255.0);
        ker=kernalSize;
        l.stageMat=ImageUtilities.toGray(l.stageMat);
        try{
        Imgproc.threshold(l.stageMat, l.stageMat, ker, 255,Imgproc.THRESH_BINARY);performed=l.stageMat;}catch(Exception e){l.stageMat=performed;}
        
        apply.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            @SuppressWarnings("empty-statement")
            public void handle(ActionEvent event) {
            try{
                Universal.addUndo(new LayerStateUndo(p.place,l,p.place.layers.indexOf(l)));
                l.backstageMat=ImageUtilities.toGray(l.backstageMat);
                Imgproc.threshold(l.backstageMat, l.backstageMat,ker, 255,Imgproc.THRESH_BINARY);}catch(Exception e){}
                
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
