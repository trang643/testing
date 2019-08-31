package com.voca.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;

import com.voca.enums.WordType;

/**
 * Entity bean with JPA annotations Hibernate provides JPA implementation
 * 
 * @author pankaj
 *
 */
@Entity
@Table(name = "WORD")
public class Word {

	@Id
	@Column(name = "WORD_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String word;

	private String synonym;

	@Column(length = 4000)
	private String enMeaning;

	@Column(length = 4000)
	private String viMeaning;

	@Column(length = 4000)
	private String example;

	private String pronoun;

	private WordType wordType;

	private String mp3FileName;

	private String image;

	private int correctPoint;
	
	private int incorrectPoint;
	
	@Formula(value = "(correctPoint - incorrectPoint)")
	private int totalPoint;

	private Date lastTestDate;
	
	private Date createdDay;
	
	@ManyToOne (fetch = FetchType.EAGER)
	@JoinColumn(name = "TOPIC_ID")
	private Topic topic;

	@Transient
	private boolean needPrefill;

	public Date getCreatedDay() {
		return createdDay;
	}

	public void setCreatedDay(Date createdDay) {
		this.createdDay = createdDay;
	}


	public boolean isNeedPrefill() {
		return needPrefill;
	}

	public void setNeedPrefill(boolean needPrefill) {
		this.needPrefill = needPrefill;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMp3FileName() {
		return mp3FileName;
	}

	public void setMp3FileName(String mp3FileName) {
		this.mp3FileName = mp3FileName;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getSynonym() {
		return synonym;
	}

	public void setSynonym(String synonym) {
		this.synonym = synonym;
	}

	public String getPronoun() {
		return pronoun;
	}

	public void setPronoun(String pronoun) {
		this.pronoun = pronoun;
	}

	public WordType getWordType() {
		return wordType;
	}

	public void setWordType(WordType wordType) {
		this.wordType = wordType;
	}

	public String getExample() {
		return example;
	}

	public void setExample(String example) {
		this.example = example;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getCorrectPoint() {
		return correctPoint;
	}

	public void setCorrectPoint(int correctPoint) {
		this.correctPoint = correctPoint;
	}

	public int getIncorrectPoint() {
		return incorrectPoint;
	}

	public void setIncorrectPoint(int incorrectPoint) {
		this.incorrectPoint = incorrectPoint;
	}

	public int getTotalPoint() {
		return totalPoint;
	}

	public void setTotalPoint(int totalPoint) {
		this.totalPoint = totalPoint;
	}

	public Date getLastTestDate() {
		return lastTestDate;
	}

	public void setLastTestDate(Date lastTestDate) {
		this.lastTestDate = lastTestDate;
	}

	public String getEnMeaning() {
		return enMeaning;
	}

	public void setEnMeaning(String enMeaning) {
		this.enMeaning = enMeaning;
	}

	public String getViMeaning() {
		return viMeaning;
	}

	public void setViMeaning(String viMeaning) {
		this.viMeaning = viMeaning;
	}
	
	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((word == null) ? 0 : word.hashCode());
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
		Word other = (Word) obj;
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (!word.equals(other.word))
			return false;
		return true;
	}
	

}
