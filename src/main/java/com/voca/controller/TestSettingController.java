package com.voca.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.voca.enums.TestSettingType;
import com.voca.model.TestModel;
import com.voca.model.TestSetting;
import com.voca.model.Topic;
import com.voca.service.TestSettingService;
import com.voca.service.WordService;

@Controller
@SessionAttributes("TestSetting")
public class TestSettingController {
	private WordService wordService;

	@Autowired(required = true)
	@Qualifier(value = "wordService")
	public void setWordService(WordService ps) {
		this.wordService = ps;
	}

	@RequestMapping(value = "/test_setting", method = RequestMethod.GET)
	public String testSetting(@ModelAttribute("TestSetting") TestSetting testSetting, Model model) {
		TestSetting storedSetting = TestSettingService.getStoredTestSetting();
		if (storedSetting != null)
			if (TestSettingType.LAST_WORDS.equals(storedSetting.getType())) {
				testSetting.setNumberOfWords(storedSetting.getNumberOfWords());
			} else if (TestSettingType.LAST_BULK.equals(storedSetting.getType())) {
				testSetting.setSelectedTopics(storedSetting.getSelectedTopics());
			}
		
		List<TestSettingType> types = new ArrayList<TestSettingType>();

		for (TestSettingType t : TestSettingType.values()) {
			types.add(t);
		}

		model.addAttribute("types", types);

		List<Topic> topics = wordService.findAllTopic();
		for (Topic t: topics) {
			t.setTopicName(t.getTopicName() + ": " + t.getWords().size() + " words");
		}
		model.addAttribute("topics", topics);

		return "test_setting";
	}

	@RequestMapping(value = "/test_setting", method = RequestMethod.POST)
	public String testSettingPost(@ModelAttribute("TestSetting") TestSetting testSetting, Model model) {
		try {
			FileOutputStream f = new FileOutputStream(new File("testSetting.txt"));
			ObjectOutputStream o = new ObjectOutputStream(f);

			// Write objects to file
			o.writeObject(testSetting);
			o.close();
			f.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "redirect:/test_setting";
	}

	@RequestMapping(value = "/test-setting/last-words/{number}", method = RequestMethod.GET)
	public String setTestSetting(@PathVariable("number") String number, @ModelAttribute("TestModel") TestModel testModel) {
		try {
			Integer wordNumber = Integer.valueOf(number);
			testModel.setNumberOfWords(wordNumber);
		} catch (NumberFormatException e) {
			throw e;
		}

		return "redirect:/test_setting";
	}

	@ModelAttribute("TestSetting")
	public TestSetting getTestSetting() {
		return new TestSetting();
	}

}
