package com.voca.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.voca.enums.TestLevel;
import com.voca.enums.TestSettingType;
import com.voca.model.TestModel;
import com.voca.model.TestSetting;
import com.voca.model.Topic;
import com.voca.model.Word;
import com.voca.model.TestModel.TestType;
import com.voca.service.TestSettingService;
import com.voca.service.WordService;

@Controller
@SessionAttributes("TestModel")
public class TestController {

	private static final int TEST_BLOCK_SIZE = 20;
	private static final int FIRST_ITEM = 0;
	private WordService wordService;

	@Autowired(required = true)
	@Qualifier(value = "wordService")
	public void setWordService(WordService ps) {
		this.wordService = ps;
	}

	@RequestMapping(value = "/test/word_english_meaning", method = RequestMethod.GET)
	public String homePageList(@ModelAttribute("TestModel") TestModel testModel, Model model) {
		testModel.setTestType(TestType.WORD_ENGLISH_MEANING);
		createTestModel(testModel);

		return "test";
	}


	@RequestMapping(value = "/test/sound_english_meaning", method = RequestMethod.GET)
	public String sound_english_meaning(@ModelAttribute("TestModel") TestModel testModel, Model model) {
		testModel.setTestType(TestType.SOUND_ENGLISH_MEANING);
		
		createTestModel(testModel);

		return "test";
	}
	

	@RequestMapping(value = "/test/sound_vietnamese_meaning", method = RequestMethod.GET)
	public String sound_vietnamese_meaning(@ModelAttribute("TestModel") TestModel testModel, Model model) {
		testModel.setTestType(TestType.SOUND_VIETNAMESE_MEANING);
		createTestModel(testModel);

		return "test";
	}

	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public String submitAnswer(@ModelAttribute("TestModel") TestModel testModel, Model model, RedirectAttributes redirectAttributes) {
		// Correct answer
		if (testModel.getCorrectUUID().equalsIgnoreCase(testModel.getAnswerUUID())) {
			redirectAttributes.addFlashAttribute("msg", "Congrats");
			Word word = testModel.getWord();
			word.setCorrectPoint(word.getCorrectPoint() + 1);
			testModel.setMsg(null);

			wordService.updateWord(word);
			if (testModel.getTestType().equals(TestType.SOUND_ENGLISH_MEANING)) {
				return "redirect:/test/sound_english_meaning";
			} else if (testModel.getTestType().equals(TestType.WORD_ENGLISH_MEANING)) {
				return "redirect:/test/word_english_meaning";
			} else if (testModel.getTestType().equals(TestType.SOUND_VIETNAMESE_MEANING)) {
				return "redirect:/test/sound_vietnamese_meaning";
			}
		}

		// Incorrect answer
		testModel.setMsg("You choosed a wrong answer!");
		Word word = testModel.getWord();
		word.setIncorrectPoint(word.getIncorrectPoint() - 2);
		wordService.updateWord(word);
		
		return "test";
	}

	@ModelAttribute("TestModel")
	public TestModel getTestModel() {
		return new TestModel();
	}
	
	private void createTestModel(TestModel testModel) {
		TestSetting storedSetting = TestSettingService.getStoredTestSetting();
		List<Word> words = new ArrayList<Word>();
	
		if (CollectionUtils.isEmpty(testModel.getWords())) {
			if (storedSetting != null) {
				if (TestSettingType.LAST_BULK.equals(storedSetting.getType())) {
					List<Topic> topics = wordService.findAllTopicIn(storedSetting.getSelectedTopics());
					for (Topic t: topics) {
						words.addAll(t.getWords());
					}
				} else if (TestSettingType.LAST_WORDS.equals(storedSetting.getType())) {
					words = wordService.listWords(storedSetting.getNumberOfWords());
				}
			}
			
			// Select for 
			/*words = wordService.getWordsInDayRange(TEST_BLOCK_SIZE, TestLevel.LEVEL_1);

			if (TEST_BLOCK_SIZE > words.size()) {
				words.addAll(wordService.getWordsInDayRange(TEST_BLOCK_SIZE - words.size(), TestLevel.LEVEL_2));
			}

			if (TEST_BLOCK_SIZE > words.size()) {
				words.addAll(wordService.getWordsInDayRange(TEST_BLOCK_SIZE - words.size(), TestLevel.LEVEL_3));
			}

			if (TEST_BLOCK_SIZE > words.size()) {
				words.addAll(wordService.getWordsInDayRange(TEST_BLOCK_SIZE - words.size(), TestLevel.LEVEL_4));
			}

			if (TEST_BLOCK_SIZE > words.size()) {
				words.addAll(wordService.getWordsInDayRange(TEST_BLOCK_SIZE - words.size(), TestLevel.LEVEL_5));
			}

			if (TEST_BLOCK_SIZE > words.size()) {
				words.addAll(wordService.getWordsInDayRange(TEST_BLOCK_SIZE - words.size(), TestLevel.LEVEL_0));
			}
			
			if (CollectionUtils.isEmpty(words)) {
				words.addAll(wordService.listWords(TEST_BLOCK_SIZE));
			}*/


			testModel.setWords(words);
		} else {
			words = testModel.getWords();
		}

		// Make the item random
		Collections.shuffle(words);
		
		// First word in list will be tested
		Word word = testModel.getWords().get(FIRST_ITEM);

		// Create answer map
		Map<String, String> answerMap = new HashMap<String, String>();

		// Correct UUID for correct answer
		String correctUUID = UUID.randomUUID().toString();
		

		// Get answer options
		List<Word> optionWords = wordService.getRandomWords(3, word.getId());

		if (TestType.SOUND_ENGLISH_MEANING.equals(testModel.getTestType()) || TestType.WORD_ENGLISH_MEANING.equals(testModel.getTestType())) {
			for (int i = 0; i <= 2; i++) {
				answerMap.put(UUID.randomUUID().toString(), optionWords.get(i).getEnMeaning().replaceAll("\r\n", "<br/>"));
			System.out.println(optionWords.get(i).getViMeaning());
			}
			answerMap.put(correctUUID, word.getEnMeaning());
		} else if (TestType.SOUND_VIETNAMESE_MEANING.equals(testModel.getTestType())) {
			for (int i = 0; i <= 2; i++) {
				answerMap.put(UUID.randomUUID().toString(), optionWords.get(i).getViMeaning().replaceAll("\r\n", "<br/>"));
				System.out.println(optionWords.get(i).getViMeaning());
			}
			answerMap.put(correctUUID, word.getViMeaning());
		}

		testModel.setWord(word);
		testModel.setQuestion(word.getWord());
		testModel.setAnswerMap(answerMap);
		testModel.setCorrectUUID(correctUUID);
	}

}
