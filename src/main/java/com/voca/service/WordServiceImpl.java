package com.voca.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.voca.dao.TopicDAO;
import com.voca.dao.WordDAO;
import com.voca.enums.TestLevel;
import com.voca.model.Topic;
import com.voca.model.Word;

@Service
public class WordServiceImpl implements WordService {
	
	private WordDAO wordDAO;
	private TopicDAO topicDAO;
	
	
	public TopicDAO getTopicDAO() {
		return topicDAO;
	}

	public void setTopicDAO(TopicDAO topicDAO) {
		this.topicDAO = topicDAO;
	}

	public WordDAO getWordDAO() {
		return wordDAO;
	}

	public void setWordDAO(WordDAO wordDAO) {
		this.wordDAO = wordDAO;
	}

	@Override
	@Transactional
	public void addWord(Word p) {
		this.wordDAO.addWord(p);
	}

	@Override
	@Transactional
	public void updateWord(Word p) {
		this.wordDAO.updateWord(p);
	}

	@Override
	@Transactional
	public List<Word> listWords(int testBlockSize) {
		return this.wordDAO.listWord(testBlockSize);
	}

	@Override
	@Transactional
	public Word getWordById(int id) {
		return this.wordDAO.getWordById(id);
	}

	@Override
	@Transactional
	public void removeWord(int id) {
		this.wordDAO.removeWord(id);
	}

	@Override
	@Transactional
	public List<Word> getRandomWords(int numberOfWords, int skipId) {
		return this.wordDAO.getRandomWords(numberOfWords, skipId);
	}

	@Override
	@Transactional
	public List<Word> getWordsInDayRange(int numberOfWord, TestLevel testLevel) {
		return this.wordDAO.getWordsInDayRange(numberOfWord, testLevel);
	}

	@Override
	@Transactional
	public List<Word> findAllWords() {
		return this.wordDAO.findAll();
	}

	@Override
	@Transactional
	public void addTopic(Topic topic) {
		topicDAO.addTopic(topic);
	}

	@Override
	@Transactional
	public List<Topic> findAllTopic() {
		return topicDAO.findAll();
	}

	@Override
	@Transactional
	public List<Topic> findAllTopicIn(List<Integer> topics) {
		return topicDAO.findAllIn(topics);
	}
	

}
