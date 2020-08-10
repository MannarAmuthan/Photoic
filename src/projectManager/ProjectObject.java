/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectManager;


import java.util.ArrayList;
import LayerPackage.Layer;
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
