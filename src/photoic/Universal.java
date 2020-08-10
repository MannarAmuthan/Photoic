/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package photoic;

import Brushes.Brush;
import UndoRedo.Undoable;
import javafx.scene.paint.Color;

import java.util.Stack;

/**
 * @author ELCOT
 */
public class Universal{
	 public static int canvasWidth;
	 public static int canvasHeight;
	 public static int currentLayer = 0;
	 public static boolean inLosslessMode;
	 public static Stack<Undoable> undos;
	 public static Color currentColor;
	 public static Brush brush;
	 public static int strokeWidth = 10;
	 public static String font;
	 public static int fontSize;
	 public static int oriWidth = 1000;
	 public static int oriHeight = 600;

	 public static void addUndo(Undoable u){
		  if (undos == null){
			   undos = new Stack<>();
		  }
		  undos.add(u);
	 }

	 public static void addUndo(Undoable u, int tag){
		  if (undos == null){
			   undos = new Stack<>();
		  }
		  u.tag = tag;
		  undos.add(u);
	 }

	 public static void undo(){
		  boolean separateProcess = false;
		  //while(!separateProcess){
		  int tag = 0;
		  if (!undos.isEmpty()){
			   undos.pop().undo();
		  }
//    if(undos.isEmpty()){break;}
//    if(tag==undos.peek().tag&&tag!=0){separateProcess=false;}
//    else{separateProcess=true;}
//    } 
	 }
}
