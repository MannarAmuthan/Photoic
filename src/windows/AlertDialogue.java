/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windows;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author ELCOT
 */
public class AlertDialogue {
    static Stage alert;
    private AlertDialogue(){};
    public static void doAlert(String s){
        if(alert==null){alert=new Stage();}
    VBox h=new VBox();
    Label l=new Label(s);
    Button ok=new Button("OK");
    h.getChildren().addAll(l,ok);
    h.setSpacing(10);
    Scene scene=new Scene(h);
    alert.setScene(scene);
    alert.setResizable(false);
    alert.centerOnScreen();
    alert.show();
    alert.setMinWidth(400);
    alert.setMinHeight(100);
    alert.setAlwaysOnTop(true);
    ok.setOnAction((ActionEvent event) -> {
        alert.close();       
        });   
    }
      
}
