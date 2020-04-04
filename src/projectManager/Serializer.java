/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectManager;

import LayerPackage.Layer;
import photoic.Universal;

/**
 *
 * @author ELCOT
 */
public class Serializer {
    
    public static SerializableObject getSerialbleLayer(Layer l){
    SerializableObject obj=new SerializableObject(l);
    return obj;
    }
    
}
