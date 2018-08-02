import java.util.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.BufferedImage;
import java.awt.Color;

public class mainfunc{

    public static BufferedImage gsimg(String str)throws IOException{
        BufferedImage img = null;
        File f = null;

        
        try{
            f = new File(str);
            img = ImageIO.read(f);
        }catch(IOException e){
            System.out.println(e);
        }
        
        //get image width and height
        int width = img.getWidth();
        int height = img.getHeight();
        
        //convert to grayscale
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                int p = img.getRGB(x,y);
                
                int a = (p>>24)&0xff;
                int r = (p>>16)&0xff;
                int g = (p>>8)&0xff;
                int b = p&0xff;
                
                //calculate average
                int avg = (r+g+b)/3;
                
                //replace RGB value with avg
                p = (a<<24) | (avg<<16) | (avg<<8) | avg;
                
                img.setRGB(x, y, p);
            }
        }
        
        return img;//return image
    }//method ends here


    // Convert to Binary Image
	public static BufferedImage bnw(BufferedImage original)
	{

		BufferedImage binarized = new BufferedImage(original.getWidth(), original.getHeight(),BufferedImage.TYPE_BYTE_BINARY);
		int red;
		int newPixel;
		int threshold =182;
		for(int i=0; i<original.getWidth(); i++)
		{
		for(int j=0; j<original.getHeight(); j++)
		{
		// Get pixels
		red = new Color(original.getRGB(i, j)).getRed();
		int alpha = new Color(original.getRGB(i, j)).getAlpha();
		if(red > threshold)
		{
		newPixel = 255;
		}
		else
		{
		newPixel = 0;
		}
		newPixel = colorToRGB(alpha, newPixel, newPixel, newPixel);
		binarized.setRGB(i, j, newPixel);
		}
		}

		return binarized;
	}


	private static int colorToRGB(int alpha, int red, int green, int blue) {

		int newPixel = 0;
		newPixel += alpha;
		newPixel = newPixel << 8;
		newPixel += red; newPixel = newPixel << 8;
		newPixel += green; newPixel = newPixel << 8;
		newPixel += blue;
		
		return newPixel;
	}

	public static BufferedImage mdfil(BufferedImage mdimg)throws IOException{

	Color[] pixel=new Color[9];
	int[] R=new int[9];
	int[] B=new int[9];
	int[] G=new int[9];
	
	
	for(int i=1;i<mdimg.getWidth()-1;i++)
	for(int j=1;j<mdimg.getHeight()-1;j++)
	{
		pixel[0]=new Color(mdimg.getRGB(i-1,j-1));
		pixel[1]=new Color(mdimg.getRGB(i-1,j));
		pixel[2]=new Color(mdimg.getRGB(i-1,j+1));
		pixel[3]=new Color(mdimg.getRGB(i,j+1));
		pixel[4]=new Color(mdimg.getRGB(i+1,j+1));
		pixel[5]=new Color(mdimg.getRGB(i+1,j));
		pixel[6]=new Color(mdimg.getRGB(i+1,j-1));
		pixel[7]=new Color(mdimg.getRGB(i,j-1));
		pixel[8]=new Color(mdimg.getRGB(i,j));
		for(int k=0;k<9;k++){
		R[k]=pixel[k].getRed();
		B[k]=pixel[k].getBlue();
		G[k]=pixel[k].getGreen();
		}
		Arrays.sort(R);
		Arrays.sort(G);
		Arrays.sort(B);
		mdimg.setRGB(i,j,new Color(R[4],B[4],G[4]).getRGB());
	}

	return mdimg;

	}

	public static BufferedImage dler(BufferedImage dlerimg,boolean dilateBackgroundPixel){

		/**
     * This method will perform dilation operation on the binary image.
     * A binary image has two types of pixels - Black and White.
     * WHITE pixel has the ARGB value (255,255,255,255)
     * BLACK pixel has the ARGB value (255,0,0,0)
     * 
     * For dilation we generally consider the background pixel. So, dilateBlackPixel = true.
     * 
     * @param dlerimg The image on which dilation operation is performed
     * @param dilateBackgroundPixel If set to TRUE will perform dilation on BLACK pixels else on WHITE pixels.
     */

     int pixels[];
    
        
        //get image width and height
        int width = dlerimg.getWidth();
        int height = dlerimg.getHeight();

        int totalPixels=width*height;
    
        pixels= new int[totalPixels];

        /**
         * This will hold the dilation result which will be copied to image dlerimg.
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
                Color c = new Color(dlerimg.getRGB(x, y));
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
                                c = new Color(dlerimg.getRGB(x, y));
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
         * Save the dilation value in image dlerimg.
         */
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                int v = output[x+y*width];

                pixels[x+(y*width)] = (255<<24) | (v<<16) | (v<<8) | v;

                dlerimg.setRGB(x, y, pixels[x+(y*width)]);

        

            }
        }

        return dlerimg;
    
	}

	public static void main(String[] args) throws IOException{

		BufferedImage img = null;
		File f = null;

        //read image
		System.out.println("Enter the input filename: ");
        Scanner sc = new Scanner(System.in);
        String inputA = sc.nextLine();
        
		
		img=gsimg(inputA);
		
		img=bnw(img);

		img=mdfil(img);

		img=dler(img,true);

		img=mdfil(img);

		img=dler(img,false);

		img=mdfil(img);

		try{
            f = new File("newoutput.jpeg");
            ImageIO.write(img, "jpg", f);
        }catch(IOException e){
            System.out.println(e);
        }
	}
}