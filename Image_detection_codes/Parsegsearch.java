import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parsegsearch {

	public static void main(String[] args) throws IOException {
		Document doc = Jsoup.connect("https://www.google.com/searchbyimage?&image_url=https://i.imgur.com/fr9xkXk.jpg").userAgent("Mozilla/5.0").get();
		//System.out.println(doc.html());
		Elements resultsH3 = doc.select("h3.r > a");

		for (Element result : resultsH3) {
			String linkText = result.text();
			System.out.println("Text::" + linkText);
		}
	}

}