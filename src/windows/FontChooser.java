/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windows;

import java.util.List;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.util.Callback;
import photoic.Universal;

/**
 *
 * @author ELCOT
 */
public class FontChooser {
     static Label styled;
     static ComboBox<String> mainList;
   public static  HBox getFontList(){
       
        mainList = new ComboBox<>();
        List<String> familiesList = Font.getFamilies();
        Universal.font=familiesList.get(0);
        for(int i=0;i<familiesList.size();i++){
        mainList.getItems().add(familiesList.get(i));
        }
        mainList.getSelectionModel().selectFirst();
        
        
        mainList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
           @Override
           public void changed(ObservableValue observable, Object oldValue, Object newValue) {
             styled.setStyle("-fx-font-family:"+newValue.toString().trim()+";");
             System.out.println("setting style "+newValue.toString().trim());
             Universal.font=newValue.toString().trim();
             }
        });
        
        
        styled=new Label("Sample Text");
        styled.setStyle("-fx-font-family:"+Universal.font+";");
        
        HBox h=new HBox();
        mainList.setFocusTraversable(false); 
        h.getChildren().addAll(mainList,styled);
        h.setSpacing(10);
        h.setFocusTraversable(false); 
        return h;
    
    }
}
