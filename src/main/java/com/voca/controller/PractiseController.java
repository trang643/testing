package com.voca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.voca.model.Practise;
import com.voca.service.WordService;

@Controller
@SessionAttributes("PractiseModel")
public class PractiseController {

	private WordService wordService;

	@Autowired(required = true)
	@Qualifier(value = "wordService")
	public void setWordService(WordService ps) {
		this.wordService = ps;
	}

	@RequestMapping(value = "/practise/sound_english_meaning", method = RequestMethod.GET)
	public String homePageList(@ModelAttribute("PractiseModel") Practise practise, Model model) {
		if (CollectionUtils.isEmpty(practise.getWords())) {
			practise.setWords(wordService.listWords(1000));
		}
		practise.setCurrentIndex(practise.getCurrentIndex() + 1);
		
		if (practise.getCurrentIndex() == practise.getWords().size()) {
			practise.setCurrentIndex(0);
		}
		
		practise.setWord(practise.getWords().get(practise.getCurrentIndex()));
		

		return "practise";
	}



	@ModelAttribute("PractiseModel")
	public Practise getPractiseModel() {
		return new Practise();
	}

}
