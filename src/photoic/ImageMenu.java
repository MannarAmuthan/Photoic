/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package photoic;


import Spatial.BrightNess;
import Spatial.DarkenImage;
import Spatial.GaussianBlur;
import Spatial.GrayScale;
import Spatial.Invert;
import Spatial.MedianBlur;
import Spatial.NormalBlur;
import Spatial.Sharpen;
import Spatial.SpatialFilter;
import Spatial.ThresHold;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import windows.AlertDialogue;

/**
 *
 * @author ELCOT
 */
public class ImageMenu {
    
public static Menu getMenu(Menu item,Photoic p) {
Menu blur=new Menu("Blurs");
MenuItem gaus=new MenuItem("Gaussian Blur");
MenuItem nor=new MenuItem("Normal Blur");
MenuItem med=new MenuItem("Median Blur");
MenuItem inv=new MenuItem("Invert");
MenuItem thres=new MenuItem("Threshold");
MenuItem bri=new MenuItem("Brightness");
MenuItem dar=new MenuItem("Darken Image");
MenuItem gra=new MenuItem("Grayscale");

gaus.setOnAction((ActionEvent event) -> {
    SpatialFilter filter=new GaussianBlur(p);
    if(p.selected!=null)
    filter.launch(p.selected);
    else
        AlertDialogue.doAlert("Please select a layer");
});
nor.setOnAction((ActionEvent event) -> {
    SpatialFilter filter=new NormalBlur(p);
    if(p.selected!=null)
    filter.launch(p.selected);
    else
        AlertDialogue.doAlert("Please select a layer");
});
med.setOnAction((ActionEvent event) -> {
    SpatialFilter filter=new MedianBlur(p);
    if(p.selected!=null)
    filter.launch(p.selected);
    else
        AlertDialogue.doAlert("Please select a layer");
});
inv.setOnAction((ActionEvent event) -> {
    SpatialFilter filter=new Invert(p);
    if(p.selected!=null)
    filter.launch(p.selected); 
    else
        AlertDialogue.doAlert("Please select a layer");
});
thres.setOnAction((ActionEvent event) -> {
    SpatialFilter filter=new ThresHold(p);
    if(p.selected!=null)
    filter.launch(p.selected);
    else
        AlertDialogue.doAlert("Please select a layer");
});
bri.setOnAction((ActionEvent event) -> {
    SpatialFilter filter=new BrightNess(p);
    if(p.selected!=null)
    filter.launch(p.selected);
    else
        AlertDialogue.doAlert("Please select a layer");
});

dar.setOnAction((ActionEvent event) -> {
    SpatialFilter filter=new DarkenImage(p);
    if(p.selected!=null)
    filter.launch(p.selected);
    else
        AlertDialogue.doAlert("Please select a layer");
});
gra.setOnAction((ActionEvent event) -> {
    SpatialFilter filter=new GrayScale(p);
    if(p.selected!=null)
    filter.launch(p.selected);
    else
        AlertDialogue.doAlert("Please select a layer");
});
    blur.getItems().addAll(gaus,nor,med);
    item.getItems().addAll(bri,dar,blur,inv,thres,gra);
    return item;
}  
    
}
