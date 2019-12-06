/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectManager;

import Brushes.Brush;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.paint.Color;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import LayerPackage.Layer;
import LayerPackage.LayerContainer;
import photoic.Photoic;
import photoic.Universal;

/**
 *
 * @author ELCOT
 */

public class ProjectObject implements java.io.Serializable {
    public ArrayList<SerializableObject>layers;
    public ArrayList<Layer>layer;
    public Universal u;
    
    public ProjectObject(Photoic p,Universal u){
        ArrayList<Layer> l = p.place.layers;
        layer=new ArrayList<>();
        layers=new ArrayList<>();
        for(int i=0;i<l.size();i++){
            if(l.get(i).type==1){ 
        layers.add(Serializer.getSerialbleLayer(l.get(i))); 
        layer.add(l.get(i));
            }
          
            
        }
        this.u=u;
    }
    
    

    
    
    
    
    
    
}
