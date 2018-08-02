// Java Program to compare two images
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.*;
 
class ImageComparision2
{
    public static void main(String[] args)
    {
        BufferedImage imgA = null;
        BufferedImage imgB = null;

        System.out.println("Enter the input filenames: ");
        Scanner sc = new Scanner(System.in);
        String inputA = sc.nextLine();
        String inputB = sc.nextLine();
 
        try
        {
            File fileA = new File("pothole2edit.jpeg");
            File fileB = new File("output.jpeg");
 
            imgA = ImageIO.read(fileA);
            imgB = ImageIO.read(fileB);
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
        int width1 = imgA.getWidth();
        int width2 = imgB.getWidth();
        int height1 = imgA.getHeight();
        int height2 = imgB.getHeight();

        double total_pixels = width1 * height1 * 3;
        double avg_different_pixels=0.0, percentage=0.0;

        if ((width1 != width2) || (height1 != height2))
            System.out.println("Error: Images dimensions"+
                                             " mismatch");
        else
        {
            long difference = 0;
            
            // grid row:column

            // grid 1:1
            for (int y = 0; y < (height1/4); y+=2)
            {
                for (int x = 0; x < (width1/4); x+=2)
                {
                    int rgbA = imgA.getRGB(x, y);
                    int rgbB = imgB.getRGB(x, y);
                    int redA = (rgbA >> 16) & 0xff;
                    int greenA = (rgbA >> 8) & 0xff;
                    int blueA = (rgbA) & 0xff;
                    int redB = (rgbB >> 16) & 0xff;
                    int greenB = (rgbB >> 8) & 0xff;
                    int blueB = (rgbB) & 0xff;
                    difference += Math.abs(redA - redB);
                    difference += Math.abs(greenA - greenB);
                    difference += Math.abs(blueA - blueB);
                }
            }
            avg_different_pixels += difference / total_pixels;
            

            // grid 1:2
            for (int y = 0; y < (height1/4); y+=2)
            {
                for (int x = (width1/4); x < (width1/2); x+=2)
                {
                    int rgbA = imgA.getRGB(x, y);
                    int rgbB = imgB.getRGB(x, y);
                    int redA = (rgbA >> 16) & 0xff;
                    int greenA = (rgbA >> 8) & 0xff;
                    int blueA = (rgbA) & 0xff;
                    int redB = (rgbB >> 16) & 0xff;
                    int greenB = (rgbB >> 8) & 0xff;
                    int blueB = (rgbB) & 0xff;
                    difference += Math.abs(redA - redB);
                    difference += Math.abs(greenA - greenB);
                    difference += Math.abs(blueA - blueB);
                }
            }
            avg_different_pixels += difference / total_pixels;
            

            // grid 1:3
            for (int y = 0; y < (height1/4); y+=2)
            {
                for (int x = (width1/2); x < (width1*3/4); x+=2)
                {
                    int rgbA = imgA.getRGB(x, y);
                    int rgbB = imgB.getRGB(x, y);
                    int redA = (rgbA >> 16) & 0xff;
                    int greenA = (rgbA >> 8) & 0xff;
                    int blueA = (rgbA) & 0xff;
                    int redB = (rgbB >> 16) & 0xff;
                    int greenB = (rgbB >> 8) & 0xff;
                    int blueB = (rgbB) & 0xff;
                    difference += Math.abs(redA - redB);
                    difference += Math.abs(greenA - greenB);
                    difference += Math.abs(blueA - blueB);
                }
            }
            avg_different_pixels += difference / total_pixels;
            

            // grid 1:4
            for (int y = 0; y < (height1/4); y+=2)
            {
                for (int x = (width1*3/4); x < (width1); x+=2)
                {
                    int rgbA = imgA.getRGB(x, y);
                    int rgbB = imgB.getRGB(x, y);
                    int redA = (rgbA >> 16) & 0xff;
                    int greenA = (rgbA >> 8) & 0xff;
                    int blueA = (rgbA) & 0xff;
                    int redB = (rgbB >> 16) & 0xff;
                    int greenB = (rgbB >> 8) & 0xff;
                    int blueB = (rgbB) & 0xff;
                    difference += Math.abs(redA - redB);
                    difference += Math.abs(greenA - greenB);
                    difference += Math.abs(blueA - blueB);
                }
            }
            avg_different_pixels += difference / total_pixels;
            

            // grid 2:1
            for (int y = (height1/4); y < (height1/2); y+=2)
            {
                for (int x = 0; x < (width1/4); x+=2)
                {
                    int rgbA = imgA.getRGB(x, y);
                    int rgbB = imgB.getRGB(x, y);
                    int redA = (rgbA >> 16) & 0xff;
                    int greenA = (rgbA >> 8) & 0xff;
                    int blueA = (rgbA) & 0xff;
                    int redB = (rgbB >> 16) & 0xff;
                    int greenB = (rgbB >> 8) & 0xff;
                    int blueB = (rgbB) & 0xff;
                    difference += Math.abs(redA - redB);
                    difference += Math.abs(greenA - greenB);
                    difference += Math.abs(blueA - blueB);
                }
            }
            avg_different_pixels += difference / total_pixels;
            

            // grid 2:2
            for (int y = (height1/4); y < (height1/2); y+=2)
            {
                for (int x = (width1/4); x < (width1/2); x+=2)
                {
                    int rgbA = imgA.getRGB(x, y);
                    int rgbB = imgB.getRGB(x, y);
                    int redA = (rgbA >> 16) & 0xff;
                    int greenA = (rgbA >> 8) & 0xff;
                    int blueA = (rgbA) & 0xff;
                    int redB = (rgbB >> 16) & 0xff;
                    int greenB = (rgbB >> 8) & 0xff;
                    int blueB = (rgbB) & 0xff;
                    difference += Math.abs(redA - redB);
                    difference += Math.abs(greenA - greenB);
                    difference += Math.abs(blueA - blueB);
                }
            }
            avg_different_pixels += difference / total_pixels;
            

            // grid 2:3
            for (int y = (height1/4); y < (height1/2); y+=2)
            {
                for (int x = (width1/2); x < (width1*3/4); x+=2)
                {
                    int rgbA = imgA.getRGB(x, y);
                    int rgbB = imgB.getRGB(x, y);
                    int redA = (rgbA >> 16) & 0xff;
                    int greenA = (rgbA >> 8) & 0xff;
                    int blueA = (rgbA) & 0xff;
                    int redB = (rgbB >> 16) & 0xff;
                    int greenB = (rgbB >> 8) & 0xff;
                    int blueB = (rgbB) & 0xff;
                    difference += Math.abs(redA - redB);
                    difference += Math.abs(greenA - greenB);
                    difference += Math.abs(blueA - blueB);
                }
            }
            avg_different_pixels += difference / total_pixels;
            

            // grid 2:4
            for (int y = (height1/4); y < (height1/2); y+=2)
            {
                for (int x = (width1*3/4); x < width1; x+=2)
                {
                    int rgbA = imgA.getRGB(x, y);
                    int rgbB = imgB.getRGB(x, y);
                    int redA = (rgbA >> 16) & 0xff;
                    int greenA = (rgbA >> 8) & 0xff;
                    int blueA = (rgbA) & 0xff;
                    int redB = (rgbB >> 16) & 0xff;
                    int greenB = (rgbB >> 8) & 0xff;
                    int blueB = (rgbB) & 0xff;
                    difference += Math.abs(redA - redB);
                    difference += Math.abs(greenA - greenB);
                    difference += Math.abs(blueA - blueB);
                }
            }
            avg_different_pixels += difference / total_pixels;
            

            

            //grid 3:1
            for (int y = (height1/2); y < (height1*3/4); y+=2)
            {
                for (int x = 0; x < (width1/4); x+=2)
                {
                    int rgbA = imgA.getRGB(x, y);
                    int rgbB = imgB.getRGB(x, y);
                    int redA = (rgbA >> 16) & 0xff;
                    int greenA = (rgbA >> 8) & 0xff;
                    int blueA = (rgbA) & 0xff;
                    int redB = (rgbB >> 16) & 0xff;
                    int greenB = (rgbB >> 8) & 0xff;
                    int blueB = (rgbB) & 0xff;
                    difference += Math.abs(redA - redB);
                    difference += Math.abs(greenA - greenB);
                    difference += Math.abs(blueA - blueB);
                }
            }
            avg_different_pixels += difference / total_pixels;
            

            //grid 3:2
            for (int y = (height1/2); y < (height1*3/4); y+=2)
            {
                for (int x = (width1/4); x < (width1/2); x+=2)
                {
                    int rgbA = imgA.getRGB(x, y);
                    int rgbB = imgB.getRGB(x, y);
                    int redA = (rgbA >> 16) & 0xff;
                    int greenA = (rgbA >> 8) & 0xff;
                    int blueA = (rgbA) & 0xff;
                    int redB = (rgbB >> 16) & 0xff;
                    int greenB = (rgbB >> 8) & 0xff;
                    int blueB = (rgbB) & 0xff;
                    difference += Math.abs(redA - redB);
                    difference += Math.abs(greenA - greenB);
                    difference += Math.abs(blueA - blueB);
                }
            }
            avg_different_pixels += difference / total_pixels;
            


            //grid 3:3
            for (int y = (height1/2); y < (height1*3/4); y+=2)
            {
                for (int x = (width1/2); x < (width1*3/4); x+=2)
                {
                    int rgbA = imgA.getRGB(x, y);
                    int rgbB = imgB.getRGB(x, y);
                    int redA = (rgbA >> 16) & 0xff;
                    int greenA = (rgbA >> 8) & 0xff;
                    int blueA = (rgbA) & 0xff;
                    int redB = (rgbB >> 16) & 0xff;
                    int greenB = (rgbB >> 8) & 0xff;
                    int blueB = (rgbB) & 0xff;
                    difference += Math.abs(redA - redB);
                    difference += Math.abs(greenA - greenB);
                    difference += Math.abs(blueA - blueB);
                }
            }
            avg_different_pixels += difference / total_pixels;
            

            //grid 3:4
            for (int y = (height1/2); y < (height1*3/4); y+=2)
            {
                for (int x = (width1*3/4); x < width1; x+=2)
                {
                    int rgbA = imgA.getRGB(x, y);
                    int rgbB = imgB.getRGB(x, y);
                    int redA = (rgbA >> 16) & 0xff;
                    int greenA = (rgbA >> 8) & 0xff;
                    int blueA = (rgbA) & 0xff;
                    int redB = (rgbB >> 16) & 0xff;
                    int greenB = (rgbB >> 8) & 0xff;
                    int blueB = (rgbB) & 0xff;
                    difference += Math.abs(redA - redB);
                    difference += Math.abs(greenA - greenB);
                    difference += Math.abs(blueA - blueB);
                }
            }
            avg_different_pixels += difference / total_pixels;
            


            //grid 4:1
            for (int y = (height1*3/4); y < height1; y+=2)
            {
                for (int x = 0; x < width1/4; x+=2)
                {
                    int rgbA = imgA.getRGB(x, y);
                    int rgbB = imgB.getRGB(x, y);
                    int redA = (rgbA >> 16) & 0xff;
                    int greenA = (rgbA >> 8) & 0xff;
                    int blueA = (rgbA) & 0xff;
                    int redB = (rgbB >> 16) & 0xff;
                    int greenB = (rgbB >> 8) & 0xff;
                    int blueB = (rgbB) & 0xff;
                    difference += Math.abs(redA - redB);
                    difference += Math.abs(greenA - greenB);
                    difference += Math.abs(blueA - blueB);
                }
            }
            avg_different_pixels += difference / total_pixels;
            


            //grid 4:2
            for (int y = (height1*3/4); y < height1; y+=2)
            {
                for (int x = (width1/4); x < (width1/2); x+=2)
                {
                    int rgbA = imgA.getRGB(x, y);
                    int rgbB = imgB.getRGB(x, y);
                    int redA = (rgbA >> 16) & 0xff;
                    int greenA = (rgbA >> 8) & 0xff;
                    int blueA = (rgbA) & 0xff;
                    int redB = (rgbB >> 16) & 0xff;
                    int greenB = (rgbB >> 8) & 0xff;
                    int blueB = (rgbB) & 0xff;
                    difference += Math.abs(redA - redB);
                    difference += Math.abs(greenA - greenB);
                    difference += Math.abs(blueA - blueB);
                }
            }
            avg_different_pixels += difference / total_pixels;
            

            //grid 4:3
            for (int y = (height1*3/4); y < height1; y+=2)
            {
                for (int x = (width1/2); x < (width1*3/4); x+=2)
                {
                    int rgbA = imgA.getRGB(x, y);
                    int rgbB = imgB.getRGB(x, y);
                    int redA = (rgbA >> 16) & 0xff;
                    int greenA = (rgbA >> 8) & 0xff;
                    int blueA = (rgbA) & 0xff;
                    int redB = (rgbB >> 16) & 0xff;
                    int greenB = (rgbB >> 8) & 0xff;
                    int blueB = (rgbB) & 0xff;
                    difference += Math.abs(redA - redB);
                    difference += Math.abs(greenA - greenB);
                    difference += Math.abs(blueA - blueB);
                }
            }
            avg_different_pixels += difference / total_pixels;
            

            //grid 4:4
            for (int y = (height1*3/4); y < height1; y+=2)
            {
                for (int x = (width1*3/4); x < width1; x+=2)
                {
                    int rgbA = imgA.getRGB(x, y);
                    int rgbB = imgB.getRGB(x, y);
                    int redA = (rgbA >> 16) & 0xff;
                    int greenA = (rgbA >> 8) & 0xff;
                    int blueA = (rgbA) & 0xff;
                    int redB = (rgbB >> 16) & 0xff;
                    int greenB = (rgbB >> 8) & 0xff;
                    int blueB = (rgbB) & 0xff;
                    difference += Math.abs(redA - redB);
                    difference += Math.abs(greenA - greenB);
                    difference += Math.abs(blueA - blueB);
                }
            }
            avg_different_pixels += difference / total_pixels;
            
            percentage += (avg_different_pixels / 255) * 100;
 
            System.out.println("Difference Percentage-->" +
                                                percentage);
        }
    }
}