package com.voca.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.voca.model.Topic;

@Repository
public class TopicDAOImpl implements TopicDAO {

	private static final Logger logger = LoggerFactory.getLogger(TopicDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@Override
	public void addTopic(Topic topic) {
		Session session = sessionFactory.getCurrentSession();
		session.persist(topic);
		logger.debug("Persisted Topic");
	}

	@Override
	public List<Topic> findAll() {
		Session session = this.sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(Topic.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return c.list();
	}

	@Override
	public List<Topic> findAllIn(List<Integer> topics) {
		Session session = this.sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(Topic.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		c.add(Restrictions.in("id", topics.toArray()));
		return c.list();
	}

}
