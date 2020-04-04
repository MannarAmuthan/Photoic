/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windows;

import java.util.HashSet;
import java.util.Set;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import photoic.Universal;

/**
 *
 * @author ELCOT
 */
public class StrokeSettings{
    static Stage stage;
    public static Label l;
    public static Canvas ca;
    public StrokeSettings(){
    stage=new Stage();
    Universal.currentColor=Color.BLACK;
    }
    
    public static VBox getLayout(){
    VBox vbox=new VBox();    
    ca=new Canvas(25,25);
    GraphicsContext context=ca.getGraphicsContext2D();
    Pane root=new Pane();
    root.getChildren().addAll(ca);
    context.setFill(Universal.currentColor);
    context.fillOval(0, 0, ca.getWidth(), ca.getHeight());
    l=new Label(String.valueOf(Universal.strokeWidth));
    vbox.getChildren().addAll(root,l);
    vbox.setSpacing(5);
    root.setOnMouseClicked((MouseEvent event) -> {
        launch();
    });
    return vbox;
    }
    
    public static void launch(){
    VBox main=new VBox();
    Label la=new Label(String.valueOf(Universal.strokeWidth));
    Button save=new Button("save");
    Canvas c=new Canvas(100,100);
    GraphicsContext context=c.getGraphicsContext2D();
    Pane root=new Pane();
    
    Slider slider=new Slider();
    slider.setMax(100);
    slider.setMin(1);
    slider.setShowTickLabels(true);
    slider.setShowTickMarks(true); 
    slider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
           la.setText(String.valueOf(newValue.intValue()));
           context.clearRect(0, 0,c.getWidth(), c.getHeight());
           context.fillOval(0,0,newValue.intValue(),newValue.intValue());
    });
    slider.setValue((int)Universal.strokeWidth);

    root.getChildren().addAll(c);
    context.setFill(Universal.currentColor);
    context.fillOval(0, 0, Universal.strokeWidth, Universal.strokeWidth);
    save.setOnAction(new EventHandler() {
        @Override
        public void handle(Event event) {
            Universal.strokeWidth=(int)slider.getValue();
            l.setText(String.valueOf(Universal.strokeWidth));
            stage.close();
        }
    });
    ColorPicker picker=new ColorPicker();
    picker.setValue(Universal.currentColor);
    GraphicsContext ct=ca.getGraphicsContext2D();
    picker.setOnAction((ActionEvent event) -> {
        Universal.currentColor=picker.getValue();
        context.clearRect(0, 0, c.getWidth(),c.getHeight());
        ct.clearRect(0, 0, ca.getWidth(),ca.getHeight());
        
        ct.setFill(Universal.currentColor);
        context.setFill(Universal.currentColor);
        
        context.fillOval(0, 0, ca.getWidth(), ca.getHeight());
        ct.fillOval(0, 0, ca.getWidth(), ca.getHeight());
    });
    main.setSpacing(10);
    main.getChildren().addAll(root,slider,la,picker,save);
    main.setFocusTraversable(false); 
    Scene sc=new Scene(main);
    stage.setScene(sc);
    //stage.impl_setPrimary(true);
    stage.show();
    }
}
