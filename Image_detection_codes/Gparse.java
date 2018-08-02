import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.apache.commons.codec.binary.Base32;

import org.apache.http.Header;  
import org.apache.http.HttpResponse;  
import org.apache.http.client.HttpClient;  
import org.apache.http.client.methods.HttpPost;  
import org.apache.http.entity.mime.MultipartEntity;  
import org.apache.http.entity.mime.content.InputStreamBody;  
import org.apache.http.impl.client.DefaultHttpClient;  


public class Gparse {

	public static final String GOOGLE_SEARCH_URL = "https://www.google.com/search?&image_url=https://en.wikipedia.org/wiki/Pothole#/media/File:Newport_Whitepit_Lane_pot_hole.JPG";

	public static void webdetect(String str) {

        File file = new File(str);
 
        try {            
            // Reading a Image file from file system

   HttpClient client = new DefaultHttpClient();  
        MultipartEntity entity = new MultipartEntity();  
   entity.addPart("encoded_image", new InputStreamBody(new FileInputStream(file), file.getName()));  
   HttpPost post = new HttpPost("https://images.google.com/searchbyimage/upload");  
   post.setEntity(entity);  
   HttpResponse response = client.execute(post);

String site = response.getFirstHeader("location").getValue();
 
		//without proper User-Agent, we will get 403 error
		Document doc = Jsoup.connect(response.getFirstHeader("location").getValue()).userAgent("Chrome").get();
		
		//below will print HTML data, save it to a file and open in browser to compare
		//System.out.println(doc.html());
		
		//If google search results HTML change the <h3 class="r" to <h3 class="r1"
		//we need to change below accordingly
		Elements results = doc.select("h3.r > a");

		for (Element result : results) {
			String linkText = result.text();
			System.out.println("Text::" + linkText);
		}
  

        } catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the Image " + ioe);
        }
		
	}


	public static void main(String[] args) {
		
		//Taking search term input from console

		System.out.println("Enter the input filename: ");
        Scanner sc = new Scanner(System.in);
        String inputA = sc.nextLine();

        webdetect(inputA);

	}

}