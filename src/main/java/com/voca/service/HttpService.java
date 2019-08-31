package com.voca.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.voca.model.Word;

public class HttpService {
	public static Set<String> getMp3Links(String keyword) throws Exception {
		String URL = "http://dictionary.cambridge.org/dictionary/english/" + keyword.toLowerCase();
		URL cambridgeURL = new URL(URL);

		URLConnection yc = cambridgeURL.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

		StringBuffer sb = new StringBuffer();
		String inputLine;

		while ((inputLine = in.readLine()) != null)
			sb.append(inputLine);
		in.close();

		String regex = "http://dictionary.cambridge.org/media/english/uk_pron/[^\"]+.mp3";
		Pattern MY_PATTERN = Pattern.compile(regex);
		Matcher m = MY_PATTERN.matcher(sb.toString());

		Set<String> mp3Links = new HashSet<String>();
		while (m.find()) {
			mp3Links.add(m.group(0));
		}

		return mp3Links;
	}

	public static String getEnDefinitionsAndExample(Word word) {
		// can only grab first 100 results
		String userAgent = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36";
		String URL = "http://dictionary.cambridge.org/dictionary/english/" + word.getWord();

		try {
			Document doc = Jsoup.connect(URL).userAgent(userAgent).get();

			Elements definitionElements = doc.select("b.def");

			StringBuilder sb = new StringBuilder();
			for (Element element : definitionElements) {
				sb.append(element.text().replace(":", "") + "\r\n");
			}

			word.setEnMeaning(sb.toString());
			
			sb = new StringBuilder();
			Elements exampleElements = doc.select("span.eg");
			for (Element element : exampleElements) {
				sb.append(element.text().replace(":", "") + "\r\n");
			}
			
			word.setExample(sb.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static String getEnDefinition(String word) {
		// can only grab first 100 results
		String userAgent = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36";
		String URL = "http://dictionary.cambridge.org/dictionary/english/" + word;

		try {
			Document doc = Jsoup.connect(URL).userAgent(userAgent).get();

			Elements definitionElements = doc.select("b.def");

			StringBuilder sb = new StringBuilder();
			for (Element element : definitionElements) {
				sb.append(element.text().replace(":", "") + "\r\n");
			}

			return sb.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static String getURLText(String url) {
		// can only grab first 100 results
		String userAgent = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36";

		try {
			Document doc = Jsoup.connect(url).userAgent(userAgent).get();
			
			return doc.text();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	

	public static String getViDefinitions(String keyword) {
		// can only grab first 100 results
		String userAgent = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36";
		String URL = "http://tratu.coviet.vn/hoc-tieng-anh/tu-dien/lac-viet/A-V/" + keyword + ".html";

		try {
			Document doc = Jsoup.connect(URL).userAgent(userAgent).get();
			Elements elements = doc.select("div.m");
			StringBuilder sb = new StringBuilder();
			for (Element element : elements) {
				System.out.println(element.text());
				sb.append(element.text().replace(":", "") + "\r\n");
			}

			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}