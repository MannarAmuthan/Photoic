/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LayerPackage;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.opencv.core.Mat;
import photoic.ImageUtilities;
import photoic.WorkPlace;

/**
 *
 * @author ELCOT
 */
// see documentation in StyleObject




public class LayerStyle {
    static private Stage stage;
    static private WorkPlace place;
    public LayerStyle(WorkPlace place){this.place=place;}
    
    static VBox getBlendTools(Layer selected){
    VBox main=new VBox();
    Label title=new Label("Blend mode");
    RadioButton normal=new RadioButton("Normal");
    RadioButton multiply=new RadioButton("Multiply");
    RadioButton lighten=new RadioButton("Lighten");
    RadioButton darken=new RadioButton("Darken");
    ToggleGroup group=new ToggleGroup();
    group.getToggles().addAll(normal,multiply,lighten,darken);
    HBox h=new HBox();
    h.getChildren().addAll(normal,multiply,lighten,darken);
    h.setSpacing(5);
    main.getChildren().addAll(title,h);
    main.setSpacing(5);
    
    BlendMode mode=selected.getStyleObj().getBlendMode();
    if(mode==BlendMode.NORMAL){group.selectToggle(normal);}
    if(mode==BlendMode.DARKEN){group.selectToggle(darken);}
    if(mode==BlendMode.LIGHTEN){group.selectToggle(lighten);}
    if(mode==BlendMode.MULTIPLY){group.selectToggle(multiply);}
        
    group.selectedToggleProperty().addListener(new ChangeListener() {
        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            if(newValue==normal){selected.getStyleObj().setBlendMode(BlendMode.NORMAL);}
            if(newValue==darken){selected.getStyleObj().setBlendMode(BlendMode.DARKEN);}
            if(newValue==lighten){selected.getStyleObj().setBlendMode(BlendMode.LIGHTEN);}
            if(newValue==multiply){selected.getStyleObj().setBlendMode(BlendMode.MULTIPLY);}
            place.update();
        }
    });
    
    return main;
    }
        
    static HBox getColorTool(Layer selected){
    HBox main=new HBox();
    RadioButton fillProp=new RadioButton("Color Overlay");
    RadioButton none=new RadioButton("None");
    RadioButton strokeProp=new RadioButton("Stroke");
    ToggleGroup group=new ToggleGroup();
    group.getToggles().addAll(fillProp,strokeProp,none);
    

    Label space=new Label("      ");
    ColorPicker fillC=new ColorPicker();
    ColorPicker strokeC=new ColorPicker();
    Slider slider=new Slider();
    slider.setMax(10);
    slider.setMin(1);
    slider.setValue(selected.getStyleObj().getStrokeWidth());
    slider.showTickLabelsProperty();
    fillC.setValue(selected.getStyleObj().fillColor);
    strokeC.setValue(selected.getStyleObj().strokeColor);
    main.getChildren().addAll(none,fillProp,fillC,space,strokeProp,strokeC,slider);
    main.setSpacing(5);
    
    boolean isFill=selected.getStyleObj().isFill();
    if(isFill){group.selectToggle(fillProp);}
    else{group.selectToggle(strokeProp);}
    group.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) -> {
        if(newValue==fillProp){selected.getStyleObj().setFill(true);selected.getStyleObj().setStroke(false);}
        else if(newValue==strokeProp){selected.getStyleObj().setFill(false);selected.getStyleObj().setStroke(true);}
        else{selected.getStyleObj().setFill(false);selected.getStyleObj().setStroke(false);}
        place.update();
        
    });
    
    fillC.setOnAction((ActionEvent event) -> {
        selected.getStyleObj().setFillColor(fillC.getValue());
        place.update();
    });
    
    strokeC.setOnAction((ActionEvent event) -> {
        selected.getStyleObj().setStrokeColor(strokeC.getValue());
        place.update();
    });
    slider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
    selected.getStyleObj().setStrokeWidth((int)((double)newValue));
    System.out.println("val.. "+(int)((double)newValue));
    place.update();
    });
    
    return main;
    }
    
    public void launch(Layer selected){
    if(stage!=null){stage.close();stage=null;}    
    if(stage==null){
    
    stage=new Stage();    
    VBox main=new VBox();
    Button close=new Button("Close");
    close.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            stage.close();
            stage=null;
        }
    });
    main.getChildren().addAll(getBlendTools(selected),getColorTool(selected),close);
    System.out.println(selected.getStyleObj());
    main.setSpacing(20);
    Scene sc=new Scene(main);
    stage.setScene(sc);
    stage.setMinWidth(500);
    stage.setMinHeight(500);
    stage.setTitle("Layer Settings");
    stage.show();
    }  
    }
    
    
    static public Mat applyStyle(Mat m,StyleObject style){
    Mat newMat=m.clone();
    if(style.fillProperty){
    newMat=ImageUtilities.applyColor(newMat,style.getFillColor());
    }
    if(style.strokeProperty){
    newMat=ImageUtilities.strokeColor(newMat,style.getStrokeColor(),style.getStrokeWidth());
    }
    return newMat;
    }
    
}


