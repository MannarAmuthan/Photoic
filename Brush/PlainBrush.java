/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Brushes;


import java.util.ArrayList;
import org.opencv.core.Point;

/**
 *
 * @author ELCOT
 */
public class PlainBrush extends Brush {
    public static PlainBrush brush;
    private PlainBrush(){}
    public static PlainBrush getInstance(){
    if(brush==null){brush=new PlainBrush();}
    return brush;
    }
    @Override
    public ArrayList<ScalarPixel> getScalarPoints(int x,int y,int radius) {
        ArrayList<Point> points = midPointCircleDraw(x,y,radius);
        return null;
    }
    
    ArrayList<Point> midPointCircleDraw(int x_centre, 
							int y_centre, int r) 
	{       
		ArrayList<Point> points=new ArrayList<>();
		int x = r, y = 0; 
                
                points.add(new Point(x + x_centre,y + y_centre));
		if (r > 0) { 
			points.add(new Point(x + x_centre,-y + y_centre)); 
			points.add(new Point(y + x_centre,x + y_centre));	
                        points.add(new Point(-y + x_centre,x + y_centre));
		} 
		int P = 1 - r; 
		while (x > y) { 
			
			y++; 
			if (P <= 0) 
				P = P + 2 * y + 1; 
			else { 
				x--; 
				P = P + 2 * y - 2 * x + 1; 
			} 
			if (x < y) 
				break; 
                        points.add(new Point(x + x_centre,y + y_centre));
                        points.add(new Point(-x + x_centre,y + y_centre));
                        points.add(new Point(x + x_centre,-y + y_centre));
                        points.add(new Point(-x + x_centre,-y + y_centre)); 

			if (x != y) { 
			points.add(new Point(y + x_centre,x + y_centre));	
                        points.add(new Point(-y + x_centre,x + y_centre));
                        points.add(new Point(y + x_centre,-x + y_centre));
                        points.add(new Point(-y+ x_centre,-x + y_centre));
			} 
		}
                
                return points;
	} 

    @Override
    public ArrayList<Point> getPoints(int x, int y, int radius) {
    ArrayList<Point> points = midPointCircleDraw(x,y,radius);
    return points;
    }
    
}
