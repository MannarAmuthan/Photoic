/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windows;

import com.google.gson.Gson;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import photoic.ImageUtilities;
import photoic.Photoic;
import photoic.RatioScaler;
import photoic.Universal;
import projectManager.ProjectObject;
import java.util.zip.Deflater;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javafx.scene.SnapshotParameters;
import javafx.scene.text.Text;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import projectManager.ImgAndFileManager;
import projectManager.Gzip;
import projectManager.ZipFolder;



/**
 *
 * @author ELCOT
 */
public class SaveProject {
    static Stage stage=new Stage();
    
    public static void launchSave(ProjectObject project){
    if(stage==null)stage=new Stage();    
    if(stage.isShowing()){stage.close();}
    FileChooser fileChooser = new FileChooser();
 
            //Set extension filter for text files
    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Photoic files (*.po)", "*.po");
    fileChooser.getExtensionFilters().add(extFilter);
 
            //Show save file dialog
    File file = fileChooser.showSaveDialog(stage);
            
 
            if (file != null) { 
        try {
            
            String path=makedir(project, file);
            File f=new File(path+"/"+file.getName());
            if(f.exists()){ f.delete();
            f=new File(path+"/"+file.getName());}
            
            saveTextToFile(project, f);
            }
        catch (Exception ex) {
            System.out.println(ex.toString());
            AlertDialogue.doAlert("do proper save "); 
        }
            }
    
    }
    private static String makedir(ProjectObject project, File file){
    //System.out.println(file.getParent()+" "+file.getName());
    String[] name=file.getName().split(".po");
    File newf=new File(file.getParent()+"/"+name[0]);
    newf.mkdir();
    return newf.getAbsolutePath();
    //System.out.println(newf.getAbsolutePath()+" "+newf.getName());
    }
    
    private static void saveTextToFile(ProjectObject project, File file) throws FileNotFoundException, IOException, Exception {   

            
            Gson gs=new Gson();
            FileWriter fw=new FileWriter(file);
            for(int i=0;i<project.layer.size();i++){
            Image image=ImageUtilities.getImage(project.layer.get(i).backstageMat);
            File im=new File(file.getParent()+"/"+project.layer.get(i).getName()+".png");
            ImgAndFileManager.saveImage("png",im,image);
            System.out.println("storing "+im.getName()+" "+im.getParent());
            }
            
            
            String json=gs.toJson(project.layers); 
            byte[] pressed=Gzip.compress(json);
            fw.write(ImgAndFileManager.toStrings(pressed));
            
            fw.close();

            ZipFolder zf = new ZipFolder();
            String folderToZip =file.getParent();
            File m=new File(file.getParent());
            String zipName =m.getParent()+"/"+file.getName();
            zf.zipFolder(Paths.get(folderToZip), Paths.get(zipName));
            



            File[] filesArrr = m.listFiles();
            ArrayList<File> files=new ArrayList<File>(Arrays.asList(filesArrr));
            System.out.println(files.size()+" sized...."); 
            //Thread.sleep(2000); 
            System.gc();  
            for(int i=0;i<files.size();i++){
                if(files.get(i).delete()){
                  System.out.println(files.get(i).getName()+" deleted");
                }
                else{System.out.println(files.get(i).getName()+"not deleted");}
            }
            m.delete();
            
            stage.close();
        
    }
    
    public static void launchSaveAs(Photoic c ){
    FileChooser fileChooser = new FileChooser();
                 
                //Set extension filter

                 fileChooser.getExtensionFilters().addAll(
                 new FileChooser.ExtensionFilter("All Images", "*.*"),
                 new FileChooser.ExtensionFilter("JPG (.jpg)", "*.jpg"),
                 new FileChooser.ExtensionFilter("PNG  (.png)", "*.png"),
                 new FileChooser.ExtensionFilter("Photoic Format  (.po)", "*.po"),
                 new FileChooser.ExtensionFilter("BMP (.bmp)", "*.bmp")
            );
               
                //Show save file dialog
                File file = fileChooser.showSaveDialog(stage);
                 
                if(file != null){
                    try {
                        Image snapshot = c.place.merge(c.place.layers);
                        BufferedImage image = new BufferedImage(Universal.oriWidth,Universal.oriHeight,BufferedImage.TYPE_INT_ARGB);
                        
                        Mat m=new Mat(new Size(image.getWidth(),image.getHeight()),c.place.layers.get(0).backstageMat.type());
                        Imgproc.resize(ImageUtilities.getMat(snapshot), m, new Size(Universal.oriWidth,Universal.oriHeight));
                        snapshot=ImageUtilities.getImage(m);
                        int w=(int)snapshot.getWidth();
                        int h=(int)snapshot.getHeight();
                        for(int i=0;i<w;i++){for(int j=0;j<h;j++){
                        int rgb=snapshot.getPixelReader().getArgb(i, j);
                        image.setRGB(i, j, rgb); 
                        }}
                       ImageIO.write(image,ImgAndFileManager.getFileExtension(file), file);
                        
                    } catch (IOException ex) {
                        System.out.println(ex.toString());
                    }
                }
            
    }
    
    





    
}
