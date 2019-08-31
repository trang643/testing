package com.voca.dao;

import java.util.List;

import com.voca.enums.TestLevel;
import com.voca.model.Word;

public interface WordDAO {

	public void addWord(Word p);
	public void updateWord(Word p);
	public List<Word> listWord(int testBlockSize);
	public List<Word> findAll();
	public Word getWordById(int id);
	public void removeWord(int id);
	public List<Word> getRandomWords(int maxResult, int skipId);
	public List<Word> getWordsInDayRange(int maxResult, TestLevel testLevel);
}
