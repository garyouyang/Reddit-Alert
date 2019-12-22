
import java.util.*;
public class Main {
	public static void main(String args[]) {		
		RedditAlert ra = new RedditAlert();
		Timer timer = new Timer();
		// check for new post every 3 minutes
		timer.schedule(ra, 0, 180000);
	}
}
