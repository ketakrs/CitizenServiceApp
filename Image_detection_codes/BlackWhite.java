import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import javax.imageio.*;

public class BlackWhite {
public static void main(String[] args)
{

        System.out.println("Enter the input filenames: ");
        Scanner sc = new Scanner(System.in);
        String inputA = sc.nextLine();

try
{
BufferedImage original = ImageIO.read(new File(inputA));
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
ImageIO.write(binarized, "jpg",new File("bnoutput.jpeg") );
}
catch (IOException e)
{
e.printStackTrace();
}
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
}