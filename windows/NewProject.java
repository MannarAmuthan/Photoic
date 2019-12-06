/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windows;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.opencv.core.Scalar;
import photoic.ImageUtilities;
import LayerPackage.Layer;
import photoic.Photoic;
import photoic.RatioScaler;
import UndoRedo.addLayerUndo;
import photoic.Universal;

/**
 *
 * @author ELCOT
 */
public class NewProject {
    Photoic p;
    static Stage newProjectWindow;
    static TextField width,height;
    static ChoiceBox<ratios>cbox;
    static Button button;
    static ratios currentRatio;
    public NewProject(Photoic p){this.p=p;}
    VBox customRatio(){

    width=new TextField();
    height=new TextField();
    
    HBox a=new HBox();a.setSpacing(5);
    a.getChildren().addAll(new Label("Width"),width);
    HBox b=new HBox();b.setSpacing(5);
    b.getChildren().addAll(new Label("Height"),height);
    HBox fields=new HBox();fields.getChildren().addAll(a,b);fields.setSpacing(10);
    VBox main=new VBox();
    main.getChildren().addAll(fields);
    return main;
    
    }
    
   VBox defaultRatio(){
    VBox main=new VBox();
    cbox=new ChoiceBox();
    cbox.getItems().addAll(new ratios(841,1189,"A0"),new ratios(595,841,"A1"),new ratios(420,595,"A2"),
            new ratios(296,420,"A3"),new ratios(210,296,"A4"),new ratios(149,210,"A5"));
    cbox.getItems().addAll(new ratios(1024,600,"(1024x600)"),new ratios(1024,768,"(1024x768)")
    ,new ratios(2048,1536,"(2048x1536)"));
    main.getChildren().add(cbox);
    return main;
    
    }
   
   VBox fromImage(){
   button=new Button("SELECT FROM IMAGES");
   VBox main=new VBox();
   main.getChildren().add(button);
   button.setOnAction(new EventHandler() {
       @Override
       public void handle(Event event) {
           FileChooser open=new FileChooser();
           File selected=open.showOpenDialog(null);
           if(selected!=null){
           System.out.println(selected.toURI()+" "+selected.getName());
           Image image=null;
            try {
                image = new Image(selected.toURL().toString());
            } catch (MalformedURLException ex) {
                AlertDialogue.doAlert("Invalid image format"); 
                Logger.getLogger(Photoic.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
            try{
           int width=ImageUtilities.getMat(image).width();
           int height=ImageUtilities.getMat(image).height();
           Universal.oriWidth=width;Universal.oriHeight=height;
           Dimension d=RatioScaler.getScaleDimension(new Dimension(width,height), new Dimension(1000,600));
           image=RatioScaler.scale(image, d.width, d.height);
           create(new ratios(d.width,d.height,""));
           Layer newL=p.addLayer(image,0,"");
           Universal.addUndo(new addLayerUndo(p.place,p,newL));
            }
            catch(Exception e){AlertDialogue.doAlert("Invalid image format");}
           
        }
           
       }
   });
   
   return main;
   }
    
   public void launch(){
   if(newProjectWindow==null){newProjectWindow=new Stage();}
   if(newProjectWindow.isShowing()){newProjectWindow.close();}
   VBox v=new VBox();
   Button b=new Button("OK");
   RadioButton custom=new RadioButton("Use custom ratios");
   RadioButton def=new RadioButton("Default ratios");
   ToggleGroup group=new ToggleGroup();
   group.getToggles().addAll(custom,def);
   group.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) -> {
   if(newValue==custom){cbox.setDisable(true);}
   else{width.setDisable(true);height.setDisable(true);}
   });
   
   b.setOnAction((ActionEvent event) -> {
       if(!cbox.isDisabled()){
        create(cbox.getSelectionModel().getSelectedItem());
       }
       else{
           try{
       int w=Integer.parseInt(width.getText().toString());
       int h=Integer.parseInt(height.getText().toString());
       Universal.oriWidth=w;Universal.oriHeight=h;
       Dimension d=RatioScaler.getScaleDimension(new Dimension(w,h), new Dimension(1000,600));
       w=d.width;h=d.height;
       create(new ratios(w,h,""));}catch(Exception e){AlertDialogue.doAlert("please enter numeric values");}
       
       
       }
   });
   
   v.getChildren().addAll(custom,customRatio(),def,defaultRatio(),fromImage(),b);
   v.setSpacing(10);
   Scene sc=new Scene(v);
   newProjectWindow.setScene(sc);
   newProjectWindow.setMinHeight(500);
   newProjectWindow.setMinWidth(500);
   newProjectWindow.show();
   
   }
   
   void create(ratios size){
   System.out.println("layer size "+p.place.layers.size());
   p.deleteAll();
   int width=size.width;
   int height=size.height;
   p.place.resize(width, height);
   newProjectWindow.close();
   newProjectWindow=null;
   }
    
    
}

class ratios{
int width,height;
String name;

    public ratios(int width, int height, String name) {
        this.width = width;
        this.height = height;
        this.name = name;
    }

    @Override
    public String toString() {
        return name; //To change body of generated methods, choose Tools | Templates.
    }



}


