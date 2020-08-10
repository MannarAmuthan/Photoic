/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package photoic;

import LayerPackage.Layer;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import static java.util.Arrays.stream;
import java.util.List;
import java.util.Stack;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritablePixelFormat;
import javafx.scene.paint.Color;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

enum Direction{UP,DOWN,LEFT,RIGHT}
public class ImageUtilities {
    public Mat mat = new Mat();
    BufferedImage img;
    byte[] dat;
    public ImageUtilities() {
    }
    
    public ImageUtilities(Mat mat) {
        getSpace(mat);
    }
    
    public static double getDifference(double a, double b){
    if(a>b) return a-b;
    else if(b>a)return b-a;
    else return 0;
    }
    public static double getDifference(int a, int b){
    if(a>b) return a-b;
    else if(b>a)return b-a;
    else return 0;
    }
    
    public  void getSpace(Mat mat) {
        this.mat = mat;
        int w = mat.cols(), h = mat.rows();
        if (dat == null || dat.length != w * h * 4)
            dat = new byte[w * h * 4];
        if (img == null || img.getWidth() != w || img.getHeight() != h
            || img.getType() != BufferedImage.TYPE_4BYTE_ABGR)
                img = new BufferedImage(w, h, 
                            BufferedImage.TYPE_4BYTE_ABGR);
        Imgproc.cvtColor(mat,mat,Imgproc.COLOR_RGB2BGR);
        
    }
    
    public BufferedImage getBufferedImage(Mat mat){
            getSpace(mat);
            mat.get(0, 0, dat);
            img.getRaster().setDataElements(0, 0,mat.cols(), mat.rows(), dat);
            return img;
    }
    
    
   public static Image getImage(Mat frame){
        MatOfByte buffer=new MatOfByte();
        Imgcodecs.imencode(".png", frame, buffer);
        ByteArrayInputStream stream = new ByteArrayInputStream(buffer.toArray());
        Image img1=new Image(stream);
        return img1;
    }
    
            
   
   public static Image getImage(BufferedImage buf){
    Image card = SwingFXUtils.toFXImage(buf, null );
    return card;
   }
   public static  Mat getMat(Image image) {
    int width = (int) image.getWidth();
    int height = (int) image.getHeight();
    byte[] buffer = new byte[width * height * 4];

    PixelReader reader = image.getPixelReader();
    WritablePixelFormat<ByteBuffer> format = WritablePixelFormat.getByteBgraInstance();
    reader.getPixels(0, 0, width, height, format, buffer, 0, width * 4);

    Mat mat = new Mat(height, width, CvType.CV_8UC4);
    mat.put(0, 0, buffer);
    return mat;
    }
   
   public static Image getFinalImage(Mat m){
                        BufferedImage image = new BufferedImage(Universal.oriWidth,Universal.oriHeight,BufferedImage.TYPE_INT_ARGB);
                        int w=m.width();
                        int h=m.height();
                        for(int i=0;i<w;i++){for(int j=0;j<h;j++){
                        double s[]=m.get(i,j);
                        java.awt.Color c=new java.awt.Color((float)s[0],(float)s[1],(float)s[2],(float)s[3]);    
                        int rgb=c.getRGB();
                        image.setRGB(i, j, rgb); 
                        }}
                        return getImage(image);
   
   }
   
   
   
   public static Mat adjustImage(Mat src){    
   if(src.width()<=Universal.canvasWidth){
   src=expandRightImage(src,(Universal.canvasWidth-src.width()));
   System.out.println("in ex width "+src.width()+" "+src.height());
   }
   if(src.height()<=Universal.canvasHeight){
   src=expandDownImage(src,(Universal.canvasHeight-src.height()));
   System.out.println("in ex height"+src.width()+" "+src.height());
   }
   //for bug...we have to move image randomly
   return src;    
   }
   
   public static Mat adjustImage(Mat src, Rect rect){
    if(rect.height+rect.y>src.height()){
    src=expandDownImage(src,((rect.height+rect.y)-src.height())+2);
    }
    if(rect.width+rect.x>src.width()){
    src=expandRightImage(src,((rect.width+rect.x)-src.width())+2);
    }
    try{
    src=src.submat(rect);}catch(Exception e){}
   return src;    
   }
   
   public static Mat cropImage(Mat src,Rect rect) {
      BufferedImage isrc=new ImageUtilities().getBufferedImage(src);
      BufferedImage dest =isrc.getSubimage(rect.x,rect.y,rect.width,rect.height);
      Image im=getImage(dest);
      return getMat(im); 
   }
   
   public static Mat ShiftLeft(Mat src,Rect rect,int count){
   
   int j=src.width();
   if(src.width()<=rect.x+rect.width+count)
   src=expandRightImage(src,count);
   rect.x=rect.x+count; 
   return src;
   }
   
   public static Mat ShiftRight(Mat src,Rect rect,int count){
   
   if(rect.x==0){
   src=expandLeftImage(src,count);}
   else{
   rect.x=rect.x-count;
   }  
   return src;
   }
   
   public static Mat ShiftDown(Mat src,Rect rect,int count){
   if(rect.y==0){
   src=expandUpImage(src,count);}
   else{
   rect.y=rect.y-count;
   }  
   return src;
   }
   

   
   public static Mat ShiftUp(Mat src,Rect rect,int count){
   int i=0;
   int j=src.height();
   if(rect.y+count+rect.height>=src.height())
   src=expandDownImage(src,count);
   rect.y=rect.y+count;     
   return src;
   }
   public static Mat shiftBy(Mat src,Rect rect,int X,int Y){
   if(X>0){src=ShiftRight(src,rect,X);}
   else{src=ShiftLeft(src,rect,-1*X);}
   if(Y>0){src=ShiftUp(src,rect,Y);}
   else{src=ShiftDown(src,rect,-1*Y);}
   return src;
   }
   
   public static Mat expandDownImage(Mat src,int count){
       Mat temp=new Mat();
       Mat padd=new Mat(count,src.width(),CvType.CV_8UC4,new Scalar(255,255,255,0));
       List<Mat>list=new ArrayList<>();list.add(src.clone());list.add(padd);
       Core.vconcat(list,temp);
       return temp;
   }
   
      public static Mat expandUpImage(Mat src,int count){
       Mat temp=new Mat();
       Mat padd=new Mat(count,src.width(),CvType.CV_8UC4,new Scalar(255,255,255,0));
       List<Mat>list=new ArrayList<>();list.add(padd);list.add(src.clone());
       Core.vconcat(list,temp);
       return temp;
   }
   public static Mat expandLeftImage(Mat src,int count){
       Mat temp=new Mat();
       Mat padd=new Mat(src.height(),count,CvType.CV_8UC4,new Scalar(255,255,255,0));
       List<Mat>list=new ArrayList<>();list.add(padd);list.add(src.clone());
       Core.hconcat(list,temp);
       System.out.println("inside "+temp.width()+" "+temp.height());
       return temp;
   }
    
   public static Mat expandRightImage(Mat src,int count){
       Mat temp=new Mat();
       Mat padd=new Mat(src.height(),count,CvType.CV_8UC4,new Scalar(255,255,255,0));
       List<Mat>list=new ArrayList<>();list.add(src.clone());list.add(padd);
       Core.hconcat(list,temp);
       System.out.println("inside "+temp.width()+" "+temp.height());
       return temp;
   }

   
   public static Mat shrinkImage(Mat src,Rect rect,int width,int height){
   Mat n=src.clone();
   Imgproc.resize(src, n,new Size(width,height),0,0,Imgproc.INTER_AREA);
   n=adjustImage(n);
   //we should this below, because of bug
   //n=expandUpImage(n, (int) ((int)width/10.0));
   
   return n;
   }
   
   public static Mat getPlainImage(){
   Mat mat=new Mat(Universal.canvasHeight,Universal.canvasWidth,CvType.CV_8UC4,new Scalar(255,255,255,0));
   return mat;
   }
   
   public static Mat getColoredImage(Color c){
   Scalar sc=ImageUtilities.getScalar(c);
   Mat mat=new Mat(Universal.canvasHeight,Universal.canvasWidth,CvType.CV_8UC4,sc);
   return mat;
   }
   
   public static Mat clear(Mat m){
   Mat n=Mat.zeros(m.size(), m.type());
   return n;
   }
   
   public static Mat merge(Mat under,Rect place,Mat top){
     List<Mat> mv = new ArrayList<>();
     Core.split(top, mv);
     top.copyTo(under.submat(place),mv.get(3)); 
     return under;
   }
   public static Mat add(Mat one,Mat two){  
   Mat res=new Mat(one.size(),one.type());    
   Core.add(two, one, res);
   
   return res;
   }
   
   public static Scalar getScalar(Color c){
   int blue=(int)(c.getBlue()*255);
   int green=(int)(c.getGreen()*255);
   int red=(int)(c.getRed()*255);
   Scalar sc=new Scalar(blue,green,red,255);
   return sc;
   }
   public static Color getOppColor(Color c){
   if(c.getRed()*255>20){
   return new Color(0,0,0,1);
   }
   else{
     return new Color(1,0,0,1);
   }
   
   }
   public static boolean awtAndFxColor(Color c,java.awt.Color co){
    int redCo=co.getRed();int redC=(int)c.getRed()*255;
    int blCo=co.getBlue();int blC=(int)c.getBlue()*255;
    int grCo=co.getGreen();int grC=(int)c.getGreen()*255;
    if(getDifference(redCo,redC)<2&&getDifference(blCo,blC)<2&&getDifference(grCo,grC)<2){
    return true;
    }
    return false;
   }
   
   public static Color getColor(Scalar c){
   Color r=new Color(c.val[0],c.val[1],c.val[2],c.val[3]);
   return r;
   }
   //readd the documentation of fillPoly, same as that
   public static Mat clearPoly(Mat base,Rect r,ArrayList<Point> points){
       Rect padd=r;
       
       MatOfPoint p=new MatOfPoint();
       p.fromList(points);
       ArrayList<MatOfPoint> list=new ArrayList<>();
       list.add(p);
       
       if(r==null){padd=new Rect(0,0,Universal.canvasHeight,Universal.canvasWidth);}
       Mat dub=new Mat(Universal.canvasHeight,Universal.canvasWidth,CvType.CV_8UC4,new Scalar(0,0,0,0));
       Imgproc.fillPoly(dub, list, new Scalar(255,255,255,255));
       List<Mat> mv = new ArrayList<>();
       Core.split(dub, mv);
       for(int i=0;i<dub.height();i++){
         for(int j=0;j<dub.width();j++){
            double d[]=base.get(i,j);
            try{
            if(mv.get(3).get(i, j)[0]==255){d[3]=0;base.put(i+r.y,j+r.x,d);}}catch(Exception e){}
                }
                                  }
                  return base;
     }
   
   public static Mat fillPoly(Mat m,ArrayList<Point> points,Scalar sc){
                MatOfPoint p=new MatOfPoint();
                p.fromList(points);
                ArrayList<MatOfPoint> list=new ArrayList<>();
                list.add(p);
                Imgproc.fillPoly(m,list, sc);
                return m;
   }
   
   public static Mat applyColor(Mat m,Color c){
    double data[]={c.getBlue()*255,c.getGreen()*255,c.getRed()*255,0};
    List<Mat> mv = new ArrayList<>();
    Core.split(m, mv);
    for(int i=0;i<m.height();i++){
    for(int j=0;j<m.width();j++){
       data[3]=mv.get(3).get(i, j)[0];
       m.put(i, j, data);
    }
    }
    return m;
   }
   
   public static Mat strokeColor(Mat m,Color c,int count){
      List<Mat> mv = new ArrayList<>();
      Core.split(m, mv); 
      double data[]={c.getBlue()*255,c.getGreen()*255,c.getRed()*255,c.getOpacity()*255};
      int starter=1;
      int width=m.width();
      int height=m.height();
      for(int co=0;co<count;co++){
      for(int i=starter;i<height-1;i++){
        for(int j=starter;j<width-1;j++){
          if(mv.get(3).get(i, j)[0]==0&&mv.get(3).get(i, j+1)[0]!=0){m.put(i, j, data);}
          if(mv.get(3).get(i, j)[0]==0&&mv.get(3).get(i+1, j)[0]!=0){m.put(i, j, data);}
          if(mv.get(3).get(i, j)[0]==0&&mv.get(3).get(i-1, j)[0]!=0){m.put(i, j, data);}
          if(mv.get(3).get(i, j)[0]==0&&mv.get(3).get(i, j-1)[0]!=0){m.put(i, j, data);}
        }
      }
      }
   
   return m;
  }
   
  public static Mat invert(Mat m){
  
  for(int i=0;i<m.height();i++){
    for(int j=0;j<m.width();j++){
        double data[]=m.get(i,j);
        data[0]=Math.subtractExact(255,(int)data[0]);
        data[1]=Math.subtractExact(255,(int)data[1]);
        data[2]=Math.subtractExact(255,(int)data[2]);
       m.put(i, j, data);
    }
    }
  
  return m;
  } 
  
  public static Mat toGray(Mat m){
  
  for(int i=0;i<m.height();i++){
    for(int j=0;j<m.width();j++){
        double data[]=m.get(i,j);
        double d=(data[0]+data[1]+data[2])/3;
        data[0]=d;
        data[1]=d;
        data[2]=d;
       m.put(i, j, data);
    }
    }
  
  return m;
  }
  
 public static Mat bucket(Mat m,double d[],double fillC[],int x,int y){     
int traversed[][]=new int[m.width()][m.height()];     
m=fillColor(m,d,fillC,x,y);
return m;
 } 
 
 private static Mat fillColor(Mat m,double d[],double fillC[],int x,int y){
  try{ 
  
  if(x>=m.width()||y>=m.height()){return m;}
  if(x<0||y<0){return m;} 
  double c[]=m.get(y, x);
  for(int i=0;i<3;i++){
  if((getDifference(d[i],c[i])>5)){
      return m;
  }
  }

 
  double old[]=m.get(y,x);
  m.put(y, x, fillC);
  
  m=fillColor(m,d,fillC,x+1,y);
  m=fillColor(m,d,fillC,x-1,y);
  m=fillColor(m,d,fillC,x,y+1);
  m=fillColor(m,d,fillC,x,y-1);
  return m;
  }
 catch(Exception e){return m;}
 
 }
 
  private static Mat floodFill(Mat m,double d[],double fillC[],Point p){
  try{ 
  
  //System.out.println("Sizeof arr "+d.length+" "+fillC.length);
  Stack<Point> stack=new Stack<>();
  stack.push(p);
  while(stack.size()>0){
  Point curr=stack.pop();
  int x=(int)curr.x;
  int y=(int)curr.y;
  
  if(x>=m.width()||y>=m.height()){continue;}
  if(x<0||y<0){continue;} 
  double c[]=m.get(y, x);
  for(int i=0;i<3;i++){
  if((getDifference(d[i],c[i])>5)){
      continue;
  }
  }

  double old[]=m.get(y,x);
  m.put(y, x, fillC);
  
  stack.push(new Point(x+1,y));
  stack.push(new Point(x-1,y));
  stack.push(new Point(x,y+1));
  stack.push(new Point(x,y-1));
  }
   return m;
  }
 catch(Exception e){return m;}
 
 }
  
  
  public static Mat mergelist(ArrayList<Layer> layers){
  ArrayList<Mat> mats=new ArrayList<>(); 
  for(int j=0;j<layers.size();j++){mats.add(0,layers.get(j).getMat().clone());}  
  Mat m3=mats.get(0);
  if(mats.size()>1){
  for(int i=1;i<mats.size();i++){
 // Core.addWeighted(m3, 1, layers.get(i).getMat().clone(), 0, 0, m3);
 // Core.bitwise_and(m3,mats.get(i), m3);
  m3=addWithAlpha(m3,mats.get(i));
  System.out.println("iterating "+i);
  }
  
  }
  return m3;
  }
  
  public static Mat addWithAlpha(Mat top,Mat bottom){
  Mat res=new Mat(top.size(),top.type());
//  System.out.println("size of one "+top.size());
//  System.out.println("size of two "+bottom.size());
  for(int i=0;i<res.width();i++){
      for(int j=0;j<res.height();j++){
       double one[]=top.get(j, i);
       double two[]=bottom.get(j, i);
       double alpha=one[3]/255.0;
       double[] color={(1-alpha)*one[0]+alpha*two[0],
                       (1-alpha)*one[1]+alpha*two[1],
                       (1-alpha)*one[2]+alpha*two[2],
                       (1-alpha)*one[3]+alpha*two[3]};
       res.put(j, i, color);
     }
  }
    
  return res;  
  }
  
 
}
