import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.util.*;

public class Dilation {


    /**
     * This method will perform dilation operation on the binary image img.
     * A binary image has two types of pixels - Black and White.
     * WHITE pixel has the ARGB value (255,255,255,255)
     * BLACK pixel has the ARGB value (255,0,0,0)
     * 
     * For dilation we generally consider the background pixel. So, dilateBlackPixel = true.
     * 
     * @param img The image on which dilation operation is performed
     * @param dilateBackgroundPixel If set to TRUE will perform dilation on BLACK pixels else on WHITE pixels.
     */

    public static int pixels[];
    
    public static void main(String[] args){



        BufferedImage img = null;
        File f = null;
        
        System.out.println("Enter the input filenames: ");
        Scanner sc = new Scanner(System.in);
        String inputA = sc.nextLine();

        //read image
        try{
            f = new File(inputA);
            img = ImageIO.read(f);
        }catch(IOException e){
            System.out.println(e);
        }
        
        //get image width and height
        int width = img.getWidth();
        int height = img.getHeight();

        int totalPixels=width*height;
    
        pixels= new int[totalPixels];

        boolean dilateBackgroundPixel=true;

        /**
         * This will hold the dilation result which will be copied to image img.
         */
        int output[] = new int[width * height];
        
        /**
         * If dilation is to be performed on BLACK pixels then
         * targetValue = 0
         * else
         * targetValue = 255;  //for WHITE pixels
         */
        int targetValue = (dilateBackgroundPixel == true)?0:255;
        
        /**
         * If the target pixel value is WHITE (255) then the reverse pixel value will
         * be BLACK (0) and vice-versa.
         */
        int reverseValue = (targetValue == 255)?0:255;
        int r=0;
        //perform dilation
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                //For BLACK pixel RGB all are set to 0 and for WHITE pixel all are set to 255.
                Color c = new Color(img.getRGB(x, y));
                r = c.getRed();
                if(r == targetValue){
                    /**
                     * We are using a 3x3 kernel
                     * [1, 1, 1
                     *  1, 1, 1
                     *  1, 1, 1]
                     */
                    boolean flag = false;   //this will be set if a pixel of reverse value is found in the mask
                    for(int ty = y - 1; ty <= y + 1 && flag == false; ty++){
                        for(int tx = x - 1; tx <= x + 1 && flag == false; tx++){
                            if(ty >= 0 && ty < height && tx >= 0 && tx < width){
                                //origin of the mask is on the image pixels
                                c = new Color(img.getRGB(x, y));
                                r = c.getRed();
                                if(r != targetValue){
                                    flag = true;
                                    output[x+y*width] = reverseValue;
                                }
                            }
                        }
                    }
                    if(flag == false){
                        //all pixels inside the mask [i.e., kernel] were of targetValue
                        output[x+y*width] = targetValue;
                    }
                }else{
                    output[x+y*width] = reverseValue;
                }
            }
        }
        
        /**
         * Save the dilation value in image img.
         */
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                int v = output[x+y*width];

                pixels[x+(y*width)] = (255<<24) | (v<<16) | (v<<8) | v;

                img.setRGB(x, y, pixels[x+(y*width)]);

        

            }
        }

        //write image
        try{
            f = new File("dloutput.jpeg");
            ImageIO.write(img, "jpg", f);
        }catch(IOException e){
            System.out.println(e);
        }

    }
}