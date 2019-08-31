package com.voca.model;

import java.io.Serializable;
import java.util.List;

import com.voca.enums.TestSettingType;

public class TestSetting implements Serializable {
	private static final long serialVersionUID = 1L;
	private TestSettingType type;
	private int numberOfWords;
	private List<Integer> selectedTopics;

	public int getNumberOfWords() {
		return numberOfWords;
	}

	public void setNumberOfWords(int numberOfWords) {
		this.numberOfWords = numberOfWords;
	}

	public TestSettingType getType() {
		return type;
	}

	public void setType(TestSettingType type) {
		this.type = type;
	}

	public List<Integer> getSelectedTopics() {
		return selectedTopics;
	}

	public void setSelectedTopics(List<Integer> selectedTopics) {
		this.selectedTopics = selectedTopics;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + numberOfWords;
		result = prime * result + ((selectedTopics == null) ? 0 : selectedTopics.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TestSetting other = (TestSetting) obj;
		if (numberOfWords != other.numberOfWords)
			return false;
		if (selectedTopics == null) {
			if (other.selectedTopics != null)
				return false;
		} else if (!selectedTopics.equals(other.selectedTopics))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

}
