package com.voca.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class TestModel {
	private int numberOfWords;
	private TestType testType;
	private List<Word> words;
	private String msg;
	private Word word;
	private Map<String, String> answerMap;
	private String question;
	private String answerUUID;
	private String correctUUID;
	public enum TestType {
		SOUND_ENGLISH_MEANING, SOUND_VIETNAMESE_MEANING, SOUND_SYNONYM, SOUND_WORD, WORD_SOUND, WORD_ENGLISH_MEANING, WORD_SYNONYM;
	}

	
	public TestModel() {
		numberOfWords = 20;
		testType = TestType.WORD_ENGLISH_MEANING;
	}
	
	public int getNumberOfWords() {
		return numberOfWords;
	}
	public void setNumberOfWords(int numberOfWords) {
		this.numberOfWords = numberOfWords;
	}
	public TestType getTestType() {
		return testType;
	}
	public void setTestType(TestType testType) {
		this.testType = testType;
	}

	public Word getWord() {
		return word;
	}

	public void setWord(Word word) {
		this.word = word;
	}

	public Map<String, String> getAnswerMap() {
		return answerMap;
	}

	public void setAnswerMap(Map<String, String> answerMap) {
		this.answerMap = answerMap;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswerUUID() {
		return answerUUID;
	}

	public void setAnswerUUID(String answerUUID) {
		this.answerUUID = answerUUID;
	}

	public String getCorrectUUID() {
		return correctUUID;
	}

	public void setCorrectUUID(String correctUUID) {
		this.correctUUID = correctUUID;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<Word> getWords() {
		return words;
	}

	public void setWords(List<Word> words) {
		this.words = words;
	}
	
	@Override
	public String toString() {
		Map<Word, Integer> map = new HashMap<Word, Integer>();
		for (Word item: words) {
			if (map.containsKey(item)) {
				map.put(item, map.get(item) + 1);
			} else {
				map.put(item, 1);
			}
		}
		String st = "Words size = " + words.size() + "; ";
		for(Entry<Word, Integer> item: map.entrySet()) {
			st += item.getKey().getWord() + ": " + item.getValue() + "\r\n";
		}
		
		return st;
	}
	
}
