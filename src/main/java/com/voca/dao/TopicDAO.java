package com.voca.dao;

import java.util.List;

import com.voca.model.Topic;

public interface TopicDAO {
	void addTopic(Topic topic);
	List<Topic> findAll();
	List<Topic> findAllIn(List<Integer> topics);
}
