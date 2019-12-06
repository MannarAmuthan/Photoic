/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectManager;

import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;

/**
 *
 * @author ELCOT
 */
public class ImgAndFileManager {
    
    public static byte[] toBytes(String str) throws IOException
{
   try (ByteArrayOutputStream out = new ByteArrayOutputStream())
   {
       try (GZIPOutputStream gzip = new GZIPOutputStream(out))
       {
           gzip.write(str.getBytes(StandardCharsets.UTF_8));
       }
       return out.toByteArray();
   }
}

public static String toStrings(byte[] str) throws IOException
{
   ByteArrayOutputStream baos = new ByteArrayOutputStream();
   try (GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(str)))
   {
       int b;
       while ((b = gis.read()) != -1) {
           baos.write((byte) b);
       }
   }
   return new String(baos.toByteArray(), StandardCharsets.UTF_8);
}

    public static String fileToBuffer(InputStream is, StringBuffer strBuffer) throws IOException {
    StringBuilder sb = new StringBuilder(strBuffer);
    try (BufferedReader rdr = new BufferedReader(new InputStreamReader(is))) { 
        for (int c; (c = rdr.read()) != -1;) {
            sb.append((char) c);

        }
    }
    return sb.toString();
    }
    
    public static String getFileExtension(File file) {
    String name = file.getName();
    int lastIndexOf = name.lastIndexOf(".");
    if (lastIndexOf == -1) {
        return ""; // empty extension
    }
    return name.substring(lastIndexOf+1);
}

public static void saveImage(String ext,File file,Image m) throws IOException{
      RenderedImage renderedImage = SwingFXUtils.fromFXImage(m, null);
      System.out.println(renderedImage.getData().getHeight()+" hei");
      if(!ImageIO.write(renderedImage,ext,file)){
       System.out.println("false.......");
      }
      

}
    
}
