/******************************************************************************
 * @author AuD WS 2018/2019
 *
 * @tags image analysis, image threshold processing 
 *****************************************************************************/


import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Processing{
	
	// pixel intensities range from 0 to 255, inclusive
	   protected final static int GRAYSCALEMAX = 256;


   /**
    * Create a histogram out of a given gray-scaled pixelArray.
    * Runs in O(m*n), where m is the number of rows in the pixelArray and
    * n is the number of columns in the pixelArray.
    * @param pixelArray, pixel array of input image
    * @return int []- array, representing the gray value histogram
    */
   private static int[] histogram(int[][] pixelArray){                          //erstellt Histogram aller Grauwerte
       int[] histo = new int[256];                                              //array, wo wir Anzahl von vorkommenden Grauwerten speichern
       for (int k=0; k<256;k++) {
           int count = 0;                                                       //damit zahlen wir die Grauwerte
           for (int i=0;i<pixelArray.length;i++) {                              //Laufen durch Matrix von Grauwerten
               for (int j=0;j<pixelArray[0].length;j++) {
                   if (pixelArray[i][j]==k){                                    //Wenn Grauwert ist gleich dem gesuchten Grauwert
                       count++;                                                 //addiren wir 1
                   }
               }
           }
           histo[k]=count;                                                       //und speichern in die Matrix mit gesuchtem
       }
     return histo;
   }


   /**
    * Returns the sum of all the pixel gray values occurrences in the pixelArray 
    * of the input image.
    * Runs in constant (O(M)), where M = GRAYSCALEMAX.
    * @param histo = grayscale occurrence count histogram
    * @return sum of all gray values
    */
   private static int sumGrayvalues(int[] histo) {
       int sum = 0;
       for (int i=0; i<histo.length; i++) {
           sum+=histo[i];                           //Speichern alle daten aus histo-array in eine int-Variable
       }
      return sum;
   }


   /**
    * Algorithm to calculate threshold t for grayscale image binarisation.
    * Runs in constant (O(M)), where M = GRAYSCALEMAX.
    * @param histo, grayscale occurrence count histogram
    * @param N, is the size of the pixelArray = m*n = num rows * num cols
    * @param sumGrayvalues, sum of all gray values
    */

   private static int calcThreshold(int[] histo, int N, int sumGrayvalues){
       double []p = new double [histo.length];                                           //p(i)=histo(i)/N
       for (int i=0; i<p.length;i++) {
           p[i]=((double)histo[i]/(double)N);
           System.out.println(i + " " + p[i]);
       }

       int l = p.length-1;                                                               //Index-Variable fur Forground-Berechnungen

       int threshold = 1;
       double minikv = 9999999999.9;                                                      //Minimale Intra-Klassenvarianz
       for (int thresh=1; thresh<=255; thresh++) {                                        //Scannen alle Threshholds
           //Berechnen alle Funktionen fur Background
           double resultofwbt = wbt(p, thresh);
           double resultofmuebt = muebt(p, resultofwbt, thresh);
           double resultofsquaresigmabt = squaresigmabt(p, resultofmuebt, resultofwbt, thresh);
           //Berechnen alle Funktionen fur Forground
           double resultofwft = wft(p, thresh, l);
           double resultofmueft = mueft(p, resultofwft, thresh, l);
           double resultofsquaresigmaft = squaresigmaft(p, resultofmueft, resultofwft, thresh, l);
           //Berechnen Intra-Klassenvarianz
           double resultofintraklassenvarianz = resultofwbt*resultofsquaresigmabt+resultofwft*resultofsquaresigmaft;

           if (resultofintraklassenvarianz<minikv) {                                                //Gucken, ob Intra-Klassenvarianz minimal ist
               minikv = resultofintraklassenvarianz;                                                //Speichern neue minimale Intra-Klassenvarianz
               threshold = thresh;                                                                  //Merken uns neuen Threshold
           }
       }


	   return threshold;
   }

   //alle Funktionen fur Background
   public static double wbt (double[] p, int thresh) { //Gewicht for Background
       double sum=0;
       for (int i=1;i<=thresh;i++) {
           sum+=p[i];
       }
       return sum;
   }

   public  static double muebt (double [] p, double resultofwbt, int thresh) { //Mittelwert for Backgroung
       double sum = 0;
       for (int i=1; i<=thresh; i++) {
           sum += i*p[i];
       }
       return (sum/resultofwbt);
   }

   public static double squaresigmabt (double[] p, double resultofmuebt, double resultofwbt,int thresh) { //Varianz for Background
       double sum = 0;
       for (int i = 1; i<=thresh; i++) {
           sum+=((i-resultofmuebt)*(i-resultofmuebt))*p[i];
       }
       return (sum/resultofwbt);
   }

    //alle Funktionen fur Forground
    public static double wft (double[] p, int thresh, int l) { //Gewicht for Background
        double sum=0;
        for (int i=thresh; i<=l; i++) {
            sum+=p[i];
        }
        return sum;
    }

    public  static double mueft (double [] p, double resultofwft, int thresh, int l) { //Mittelwert for Backgroung
        double sum = 0;
        for (int i=thresh; i<=l; i++) {
            sum += i*p[i];
        }
        return (sum/resultofwft);
    }

    public static double squaresigmaft (double[] p, double resultofmueft, double resultofwft, int thresh, int l) { //Varianz for Background
        double sum = 0;
        for (int i=thresh; i<=l; i++) {
            sum+=((i-resultofmueft)*(i-resultofmueft))*p[i];
        }
        return (sum/resultofwft);
    }

   
   /**
    * Returns a BufferedImage where the color at [i][j] in pixelArray is black 
    * if the corresponding pixel intensity > threshold and white otherwise.
    * @param pixelArray 
    * @param threshold - threshold calculated
    * @return
    */
   private static BufferedImage applyThreshold(int[][] pixelArray, int threshold, BufferedImage image){
	   BufferedImage originalImage = image;
	   // loop through pixel array O(m*n)
	   for (int i = 0; i < pixelArray.length; i++){
	         for (int j = 0; j < pixelArray[0].length; j++){
	            Color colorPixel = pixelArray [i][j] > threshold ? new Color(255,255,255) : new Color(0,0,0);
	            originalImage.setRGB(i, j, colorPixel.getRGB());
	         }
	   }
      return originalImage;
   }

   /**
    * DO NOT TOUCH !!!
    * Main method for image processing
    * @param args - agrs [0], image to process
    * @throws IOException
    */
   public static void main(String args[]) throws IOException {

	  // read image and store image pixels in grayscale array

      BufferedImage originalImage = ImageIO.read(new File(args[0]));
      int[][] pixelArray = GrayScale.imageToGrayPixelArray(originalImage);

      /**
       * Output the images pixel-array row-wise, for testing your implementation
       */
      for (int i = 0; i < pixelArray.length; i++){
          for (int j = 0; j < pixelArray[0].length; j++){
        	System.out.print(pixelArray[i][j] + " ");
        	if (i==pixelArray.length) {
        	    System.out.println(" ");
            }
          }
      }
       System.out.println(" ");
	  
      // run threshold calculation algorithm - with time measured
      final long start = System.currentTimeMillis();
      
      // Find the optimal global threshold t for the input image applying 
      // aboves algorithm.
      
      // 1st create a histogram from the gray value occurrences in the grayscaled 
      // pixelArray
      int[] histo = histogram(pixelArray);

      // 2nd sum pixel gray values over histogram
      int intensSum = sumGrayvalues(histo);
      for (int i=1; i<histo.length; i++){
          System.out.println(histo[i] + " das ist " + i);
      }
      // 3rd calculate threshold t applying algorithm know in bytetown
      int threshold = calcThreshold(histo, pixelArray.length * pixelArray[0].length, intensSum);

      
      final long end = System.currentTimeMillis();
      final double elapsedTime = (double) (end - start) / 1000;

       // print runtime information of picture processing procedure
       System.out.println("threshold = = " + threshold);
       System.out.println("method runtime = " + elapsedTime + " seconds.");

       // save the processed image under old path but with file-name extension
       // change to not overwrite the old image
       BufferedImage thresholdedImage = applyThreshold(pixelArray, threshold, originalImage);
      
/*    String splits [] = args[0].split(File.separator.toString());

      String outpath = ""+File.separator;
      for(int i = 1; i < splits.length-1; i++){
    	  outpath += splits[i]+File.separator;
      }
      outpath+= splits[splits.length-1].split("\\.")[0]+"Processed.jpg";
  */

       String fileName = args[0];
       String outpath = (fileName.replace(".jpg", "")) + "Processed.jpg";

       // save processed image to same location as input path
       ImageIO.write(thresholdedImage, "jpg", new File(outpath));

   }




}