/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package photoic;

import java.awt.Dimension;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author ELCOT
 */

public class RatioScaler {
    
    public static Dimension getScaleDimension(Dimension actual,Dimension boundary){
        
    int original_width = actual.width;
    int original_height = actual.height;
    int bound_width = boundary.width;
    int bound_height = boundary.height;
    
    if(original_width<=bound_width&&original_height<=bound_height){return actual;}
    
    int new_width = original_width;
    int new_height = original_height;

    // first check if we need to scale width
    if (original_width > bound_width) {
        //scale width to fit
        new_width = bound_width;
        //scale height to maintain aspect ratio
        new_height = (new_width * original_height) / original_width;
    }

    // then check if we need to scale even with the new height
    if (new_height > bound_height) {
        //scale height to fit instead
        new_height = bound_height;
        //scale width to maintain aspect ratio
        new_width = (new_height * original_width) / original_height;
    }

    return new Dimension(new_width, new_height);
    
    
    }
    
    public static Image scale(Image source, int targetWidth, int targetHeight) {
    ImageView imageView = new ImageView(source);
    imageView.setFitWidth(targetWidth);
    imageView.setFitHeight(targetHeight);
    return imageView.snapshot(null, null);
}
}
