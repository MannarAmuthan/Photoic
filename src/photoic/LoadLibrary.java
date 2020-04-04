package photoic;

import org.opencv.core.Core;
import projectManager.ZipFolder;

import java.io.File;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 * @author ELCOT
 */
public class LoadLibrary{
	 public static boolean loadOpenCV(String mainPath, String[] args){
		  String osName = System.getProperty("os.name");
		  try{
			   File bitx86 = new File(mainPath + "/photoic_32.config");
			   File bitx64 = new File(mainPath + "/photoic_64.config");
			   String arch = System.getProperty("sun.arch.data.model");
			   if (osName.equals("Mac OS X")){
					System.setProperty("java.library.path", "libopencv_java411.dylib");
					System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
					System.out.println("opening mac os lib....  ");
					return true;
			   } else{
					if (arch != null && !arch.equals("")){
						 if (arch.contains("64")){
							  if (bitx64.exists()){
								   ZipFolder.unzip(bitx64.getAbsolutePath(), mainPath);
								   System.setProperty("java.library.path", "opencv_java411.dll");
								   System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
								   System.out.println("opening 64 bit architecture....  ");
								   return true;
							  }
						 }
						 if (arch.contains("32")){
							  if (bitx86.exists()){
								   ZipFolder.unzip(bitx86.getAbsolutePath(), mainPath);
								   System.setProperty("java.library.path", mainPath + "/opencv_java411.dll");
								   System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
								   System.out.println("opening 32 bit architecture....  ");
								   return true;
							  }
						 }
					} else{
						 System.out.println("cannot determine arch...");
						 if (bitx86.exists()){
							  ZipFolder.unzip(bitx86.getAbsolutePath(), mainPath);
							  System.setProperty("java.library.path", mainPath + "/opencv_java411.dll");
							  System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
							  return true;
						 } else if (bitx64.exists()){
							  ZipFolder.unzip(bitx64.getAbsolutePath(), mainPath);
							  System.setProperty("java.library.path", mainPath + "/opencv_java411.dll");
							  System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
							  return true;
						 } else{
							  System.out.println("Please check your config files...");
						 }
					}
			   }
		  } catch (Exception e){
			   System.out.println("Please check your config files...");
			   System.out.println(e.toString());
			   e.printStackTrace();
		  }
		  return false;
	 }

	 public static boolean loadOpenCVori(String mainPath, String[] args){
		  try{
			   System.setProperty("java.library.path", mainPath + "/opencv_java411.dll");
			   System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
			   return true;
		  } catch (Exception e){
			   System.out.println("Please check your config files...");
			   System.out.println(e.toString());
			   e.printStackTrace();
		  }
		  return false;
	 }
}
    