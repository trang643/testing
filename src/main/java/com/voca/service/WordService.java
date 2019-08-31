package com.voca.service;

import java.util.List;

import com.voca.enums.TestLevel;
import com.voca.model.Topic;
import com.voca.model.Word;

public interface WordService {
	public void addTopic(Topic topic);
	
	public void addWord(Word p);

	public void updateWord(Word p);

	public List<Word> listWords(int testBlockSize);

	public List<Word> findAllWords();

	public Word getWordById(int id);

	public void removeWord(int id);

	public List<Word> getRandomWords(int numberOfWords, int skipId);

	public List<Word> getWordsInDayRange(int numberOfWord, TestLevel testLevel);
	
	public List<Topic> findAllTopic();
	public List<Topic> findAllTopicIn(List<Integer> topics);
}
