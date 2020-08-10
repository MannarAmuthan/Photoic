/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windows;

import LayerPackage.Layer;
import com.google.gson.Gson;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import org.opencv.core.Mat;
import photoic.ImageUtilities;
import photoic.Photoic;
import photoic.Universal;
import projectManager.Deserializer;
import projectManager.ImgAndFileManager;
import projectManager.Gzip;
import projectManager.SerializableObject;

/**
 *
 * @author ELCOT
 */
public class OpenProject {
    
    public static void launch(Photoic p){
    
    FileChooser open=new FileChooser();
           File selected=open.showOpenDialog(null);
           if(selected!=null){
           System.out.println(selected.toURI()+" "+selected.getName());
           if(!selected.getName().contains(".po")){AlertDialogue.doAlert("Your project file is invalid or corrupted");return;}
           
           try {
                   
                   System.out.println(selected.getAbsolutePath()+" original");
                   ZipFile zipFile = new ZipFile(selected);
                   Enumeration<? extends ZipEntry> entries = zipFile.entries();
                   HashMap<String,Mat> map=new HashMap<>();
                   StringBuffer sourceFile=new StringBuffer();
                   
                    while(entries.hasMoreElements()){
                       ZipEntry entry = entries.nextElement();
                       InputStream stream = zipFile.getInputStream(entry);
                       
                       if(entry.getName().contains(".png")){
                       System.out.println(entry.getName().split(".png")[0]+" inside zip");
                       BufferedImage bf = ImageIO.read(stream);
                       Image image = ImageUtilities.getImage(bf);
                       if(image!=null){
                       Mat mat=ImageUtilities.getMat(image);
                       map.put(entry.getName().split(".png")[0],mat);
                       }
                       }
                       
                       else{
                       sourceFile.append(ImgAndFileManager.fileToBuffer(stream,sourceFile));
                       //System.out.println("len  "+sourceFile.length());
                       }
                       stream.close();
                       
                      }
                    
                    String originalSrc=Gzip.decompress(ImgAndFileManager.toBytes(sourceFile.toString()));
                    Deserializer.deserialize(originalSrc,map); 
                    p.deleteAll();
                    setupProject(p);
                    //System.out.println("check... "+Deserializer.layers.size()+" "+Deserializer.layers.get(0).backstageMat.size());
                    
                    
            } catch (Exception ex) {
                AlertDialogue.doAlert("Your project file is invalid or corrupted"); 
                System.out.println(ex.toString());
                return;
            }
           
                 
    
    
    
    
           }
    }
    

    

    


    private static void setupProject(Photoic p) {
        p.deleteAll();
        
        System.out.println("canvas with "+Deserializer.canvasWidth+" "+Deserializer.canvasHeight);
        p.place.resize(Deserializer.canvasWidth, Deserializer.canvasHeight);
        Universal.oriHeight=Deserializer.oriHeight;
        Universal.oriWidth=Deserializer.oriWidth;
        for(int i=Deserializer.layers.size()-1;i>-1;i--){
        p.addLayer(Deserializer.layers.get(i), 0); 
        }
        Deserializer.layers.clear();
        p.place.update();      
    }
    


}

