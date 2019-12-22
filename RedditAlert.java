/**
 * A program that notifies the user of new posts of a subreddit 
 * 
 * @author garyouyang
 * @date 8/31/2019
 */
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.*;

public class RedditAlert extends TimerTask {
	private final String reddit = "https://www.reddit.com/r/";
	private final String time_class = "_3jOxDPIQ0KaOWpzvSQo-1s";
	
	private String new_post_name = "";
	
	private String subreddit;
	private Document doc;
	
	/**
	 * constructor
	 */
	public RedditAlert() {
		// Ask the user for a subreddit
		Scanner sc = new Scanner(System.in);
		System.out.println("Which subreddit do you want to be notified?");
		String input = sc.nextLine();
		
		// Get Document object after parsing the html from given url.
		while(true) {
			subreddit = reddit + input + "/new/";
			try {
				doc = Jsoup.connect(subreddit).get();
				break;
			} catch(IOException e) {
				System.out.println("The subreddit " + input + " does not exist, please reenter");
				input = sc.nextLine();
			} 
		}
		new_post_name = get_new_post_name();
	}
	
	/**
	 * Get the new post time when the object is first time created
	 */
	private String get_new_post_name() {
		return doc.select("h3").first().text();
	}
	
	/**
	 * Get the post time of the post
	 */
	private String get_post_time() {
		return doc.getElementsByClass(time_class).first().text();
	}
	
	/**
	 * Refresh the connection to the url
	 */
	private void refresh() {
		try {
			doc = Jsoup.connect(subreddit).get();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * check for new post every 5 minutes
	 */
	public void run() {
		refresh();
		String new_post_name = get_new_post_name();
		
		// a new post is posted, update newest post time of the subreddit
		// notify the user the name of the new post
		if(!(new_post_name.equals(this.new_post_name))) {
			this.new_post_name = new_post_name;
			System.out.println(new_post_name + " " + get_post_time());
		} 
	}
}
