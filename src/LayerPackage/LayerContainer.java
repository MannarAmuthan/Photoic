/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LayerPackage;

/**
 * @author ELCOT
 */
public class LayerContainer{
	 Layer layer;
	 String LayerName;

	 public LayerContainer(String name){
		  LayerName = name;
	 }

	 public String getLayout(){
		  return LayerName;
	 }

	 public Layer getLayer(){
		  return layer;
	 }

	 public void setLayer(Layer layer){
		  this.layer = layer;
	 }

	 public String getLayerName(){
		  return LayerName;
	 }

	 public void setLayerName(String LayerName){
		  this.LayerName = LayerName;
	 }
}
