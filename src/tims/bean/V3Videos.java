package tims.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class V3Videos {
	public String getData(String videoId) throws IOException {

		URL url = new URL("https://www.googleapis.com/youtube/v3/videos?id=" + videoId+ "&key=AIzaSyAWEhVnY9S_7qeeA4hEa4KGGtQFDDJ0HsM&part=contentDetails");

		URLConnection con = url.openConnection();

		InputStream is = con.getInputStream();

		InputStreamReader isr = new InputStreamReader(is, "UTF-8");

		BufferedReader reader = new BufferedReader(isr);

		String result = "";

		while (true) {

			String data = reader.readLine();

			if (data == null) {

				break;
			}

			result += data;
		}

		return result;
	}
}