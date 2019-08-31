package com.voca.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.voca.model.Topic;
import com.voca.model.Word;
import com.voca.model.WordBulk;
import com.voca.service.HttpService;
import com.voca.service.WordService;
import com.voca.utils.FileDownloader;
import com.voca.utils.GoogleImageService;

@Controller
public class WordController {

	private static final String SPACE = " ";
	private WordService wordService;

	@Autowired(required = true)
	@Qualifier(value = "wordService")
	public void setWordService(WordService ps) {
		this.wordService = ps;
	}

	@RequestMapping(value = "/word", method = RequestMethod.GET)
	public String listWords(HttpServletRequest request, Model model) {
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if (flashMap != null) {
			Word word = (Word) flashMap.get("word");
			String imageHtml = (String) flashMap.get("imageHtml");
			String audioHtml = (String) flashMap.get("audioHtml");

			model.addAttribute("word", word);
			model.addAttribute("imageHtml", imageHtml);
			model.addAttribute("audioHtml", audioHtml);
		} else {
			model.addAttribute("word", new Word());
		} // I Deleted below line
		model.addAttribute("listWords", this.wordService.listWords(1000));

		// I added this line
		return "word";  // I changed this line
	}


	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpServletRequest request, Model model) {
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if (flashMap != null) {
			Word word = (Word) flashMap.get("word");
			String imageHtml = (String) flashMap.get("imageHtml");
			String audioHtml = (String) flashMap.get("audioHtml");

			model.addAttribute("word", word);
			model.addAttribute("imageHtml", imageHtml);
			model.addAttribute("audioHtml", audioHtml);
		} else {
			model.addAttribute("word", new Word());
		}

		model.addAttribute("listWords", this.wordService.listWords(1000));

		return "word";
	}

	@RequestMapping(value = "/add_bulk", method = RequestMethod.GET)
	public String addBulk(HttpServletRequest request, Model model) {
		model.addAttribute("listWords", this.wordService.listWords(100));
		WordBulk wordBulk = new WordBulk();
		wordBulk.setSelectedBulkType("text");

		model.addAttribute("wordBulk", wordBulk);

		return "word_bulk";
	}

	@RequestMapping(value = "/add_bulk", method = RequestMethod.POST)
	public String addBulkPost(@ModelAttribute("wordBulk") WordBulk wordBulk, Model model) {

		String[] splittedWords = null;

		if (!StringUtils.isEmpty(wordBulk.getUrl())) {
			String text = HttpService.getURLText(wordBulk.getUrl());

			splittedWords = text.split(SPACE);
		} else {
			splittedWords = wordBulk.getText().replaceAll("[^\\w\\s]", "").replaceAll("\\d", "").replaceAll("\n", "").replaceAll("\r", "").trim()
					.replaceAll(" +", " ").split(SPACE);
		}

		List<String> sentences = Arrays.asList(wordBulk.getText().split("\\."));

		Set<String> words = new HashSet<String>();
		words.addAll(Arrays.asList(splittedWords));

		// Find all words in DB, put to map
		List<Word> dbWords = wordService.findAllWords();
		Map<String, String> wordMap = new HashMap<String, String>();
		for (Word word : dbWords) {
			wordMap.put(word.getWord(), null);
		}

		Topic topic = null;
		if (wordBulk.getTopic() != null) {
			topic = new Topic();
			topic.setTopicName(wordBulk.getTopic());
			wordService.addTopic(topic);
		}

		for (String w : words) {
			w = w.toLowerCase();
			try {
				if (!wordMap.containsKey(w)) {
					Word word = new Word();
					word.setWord(w);
					word.setEnMeaning(HttpService.getEnDefinition(w));
					word.setViMeaning(HttpService.getViDefinitions(w));

					String sentence = findSentenceContainsWord(w, sentences);
					if (sentence != null) {
						word.setExample(sentence);
					} else {
						// should not happen
					}

					if (word.getEnMeaning() != null) {
						Set<String> mp3Links = null;

						mp3Links = HttpService.getMp3Links(w);
						if (mp3Links.size() > 0) {
							FileDownloader.downloadMp3(mp3Links.iterator().next(), "/home/meo/voca/mp3/", w + ".mp3");
						}

						List<String> images = GoogleImageService.searchImages(w);
						if (images.size() > 0) {
							String imageFileName = FileDownloader.downloadImage(images, "/home/meo/voca/images/", w);
							word.setImage(imageFileName);
						}

						word.setMp3FileName(w + ".mp3");
						word.setCreatedDay(new Date());
						word.setTopic(topic);
					}
					wordService.addWord(word);
				}
			} catch (Exception e) {
				System.err.println("Error with the word" + w);
				e.printStackTrace();
			}
		}

		return "word_bulk";
	}

	private String findSentenceContainsWord(String w, List<String> sentences) {
		for (String sentence : sentences) {
			if (sentence.toLowerCase().contains(w.toLowerCase())) {
				return sentence;
			}
		}

		return null;
	}

	// For add and update person both
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String addWord(@ModelAttribute("word") Word p, Model model, RedirectAttributes redirectAttributes) {
		if (p.isNeedPrefill()) {
			// English definition
			HttpService.getEnDefinitionsAndExample(p);

			// Vietnamese definition
			p.setViMeaning(HttpService.getViDefinitions(p.getWord()));

			List<String> images = GoogleImageService.searchImages(p.getWord());
			String imgFormat = "<img src=\"%s\" width=\"%s\" height=\"%s\" style=\"margin-right: 50px; margin-bottom: 5px\"/>";
			String imageHtml = "";
			boolean isFirstItem = true;
			int counter = 0;
			for (String url : images) {
				if (counter++ > 50) {
					break;
				}

				if (isFirstItem) {
					imageHtml += "<input type=\"radio\" name=\"image\" value=" + url + " checked=\"checked\">"
							+ String.format(imgFormat, url, "150px", "150px");
				} else {
					imageHtml += "<input type=\"radio\" name=\"image\" value=" + url + ">" + String.format(imgFormat, url, "150px", "150px");
				}
			}

			String audioHtml = createAutioHTML(p.getWord());

			redirectAttributes.addFlashAttribute("audioHtml", audioHtml);
			redirectAttributes.addFlashAttribute("word", p);
			redirectAttributes.addFlashAttribute("imageHtml", imageHtml);
		} else {
			try {
				if (p.getMp3FileName() != null) {
					FileDownloader.downloadMp3(p.getMp3FileName(), "/home/meo/voca/mp3/", p.getWord() + ".mp3");
				}

				if (p.getImage() != null) {
					FileDownloader.downloadImage(Arrays.asList(p.getImage()), "/home/meo/voca/images/", p.getWord() + ".jpg");
				}
			} catch (Exception e) {
			}

			p.setMp3FileName(p.getWord() + ".mp3");
			if (p.getId() == 0) {
				// new person, add it
				this.wordService.addWord(p);
			} else {
				// existing person, call update
				this.wordService.updateWord(p);
			}
		}

		return "redirect:/";
	}

	private String createAutioHTML(String word) {
		boolean isFirstItem;
		Set<String> links = null;
		try {
			links = HttpService.getMp3Links(word);
			// CambridgeService.getDefinitions(word);
		} catch (Exception e) {
			e.printStackTrace();
		}
		isFirstItem = true;
		String audioHtml = "";
		for (String item : links) {
			if (isFirstItem) {
				audioHtml += "<input type=\"radio\" name=\"mp3FileName\" checked=\"checked\" value=" + item + "><audio controls><source src=" + item
						+ " type=\"audio/mpeg\"></audio><br><br>";
				isFirstItem = false;
			} else {
				audioHtml += "<input type=\"radio\" name=\"mp3FileName\" value=" + item + "><audio controls><source src=" + item
						+ " type=\"audio/mpeg\"></audio><br><br>";
			}
		}
		return audioHtml;
	}

	@RequestMapping("/remove/{id}")
	public String removeWord(@PathVariable("id") int id) {

		this.wordService.removeWord(id);
		return "redirect:/words";
	}

	@RequestMapping("/edit/{id}")
	public String editWord(@PathVariable("id") int id, Model model) {
		Word word = this.wordService.getWordById(id);
		model.addAttribute("word", word);
		model.addAttribute("listWords", this.wordService.listWords(100));

		String audioHtml = createAutioHTML(word.getWord());

		model.addAttribute("audioHtml", audioHtml);

		return "word";
	}

}
