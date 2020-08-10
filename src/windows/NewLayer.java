/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windows;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import LayerPackage.Layer;
import LayerPackage.LayerFactory;
import photoic.Photoic;
import UndoRedo.addLayerUndo;
import photoic.Universal;

/**
 *
 * @author ELCOT
 */
public class NewLayer {
    
    
    public static void launch(Photoic p){
        Label label=new Label("Layer Name");
        TextField field=new TextField();
        HBox hbox1=new HBox();
        hbox1.setSpacing(5); 
        hbox1.getChildren().addAll(label,field);
        RadioButton r1=new RadioButton("Image Layer");
        RadioButton r2=new RadioButton("Text Layer");
        ToggleGroup group=new ToggleGroup();
        r1.setSelected(true);
        group.getToggles().addAll(r1,r2);
        HBox hbox2=new HBox();
        hbox2.getChildren().addAll(r1,r2); 
        Button add=new Button("ADD");
        Label alert=new Label("");
        Stage stage=new Stage();
        VBox vbox=new VBox();vbox.getChildren().addAll(hbox1,hbox2,alert,add);
        vbox.setSpacing(10);
        stage.setScene(new Scene(vbox));
        stage.show();
        add.setOnAction((ActionEvent event1) -> {
            if(field.getText().toString()==""){
            AlertDialogue.doAlert("Enter layer name");return;
            }
            if(group.getSelectedToggle()==null){AlertDialogue.doAlert("Select Layer Type");return;}
            if(group.getSelectedToggle()==r1){
                String name=field.getText().toString();
                if(!p.isLayerExists(name)){
                Image m=null;
                Layer newL=p.addLayer(m,0,name);
                Universal.addUndo(new addLayerUndo(p.place,p,newL));
                stage.close();}
                else{
                AlertDialogue.doAlert("Layer name already exists ");
                }
            }
            if(group.getSelectedToggle()==r2){
                String name=field.getText().toString();
                if(!p.isLayerExists(name)){
                Image m=null;
                Layer newL=new LayerFactory().getTextLayer(name,1);
                p.addLayer(newL, 0);
                Universal.addUndo(new addLayerUndo(p.place,p,newL));
                stage.close();}
                else{
                AlertDialogue.doAlert("Layer name already exists ");
                }
            }
        });
    }
    
    
}
