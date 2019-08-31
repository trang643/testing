package com.voca.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.voca.enums.TestLevel;
import com.voca.model.Word;

@Repository
public class WordDAOImpl implements WordDAO {

	private static final Logger logger = LoggerFactory.getLogger(WordDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@Override
	public void addWord(Word p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(p);
		logger.info("Person saved successfully, Person Details=" + p);
	}

	@Override
	public void updateWord(Word p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(p);
		logger.info("Person updated successfully, Person Details=" + p);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Word> listWord(int testBlockSize) {
		
		Session session = this.sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Word.class);
		criteria.addOrder(Order.desc("totalPoint"));
		
		/*List<Word> personsList = session.createQuery("from Word").list();
		for (Word p : personsList) {
			logger.info("Person List::" + p);
		}*/
		criteria.setMaxResults(testBlockSize);
		
		return criteria.list();
	}

	@Override
	public Word getWordById(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Word p = (Word) session.load(Word.class, new Integer(id));
		logger.info("Person loaded successfully, Person details=" + p);
		return p;
	}

	@Override
	public void removeWord(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Word p = (Word) session.load(Word.class, new Integer(id));
		if (null != p) {
			session.delete(p);
		}
		logger.info("Person deleted successfully, person details=" + p);
	}

	@Override
	public List<Word> getRandomWords(int maxResult, int skipId) {
		Session session = this.sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Word.class);
		criteria.setMaxResults(maxResult);
		criteria.add(Restrictions.ne("id", skipId));

		// Make it random
		criteria.add(Restrictions.sqlRestriction("1=1 order by rand()"));

		List<Word> list = criteria.list();
		
		return list;
	}

	@Override
	public List<Word> getWordsInDayRange(int maxResult, TestLevel testLevel) {
		Session session = this.sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Word.class);
		
		// Not first test
		if (!TestLevel.LEVEL_0.equals(testLevel)) {
			criteria.add(Restrictions.ge("totalPoint", testLevel.getTotalPoint()));
			criteria.add(Restrictions.lt("totalPoint", testLevel.getTotalPoint() + 3));

			// Shift days
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.add(Calendar.DATE, testLevel.getTotalDay()); // number of days to add

			criteria.add(Restrictions.lt("lastTestDate", c.getTime()));
		} else {
			// First test 
			criteria.add(Restrictions.eqOrIsNull("totalPoint", testLevel.getTotalPoint()));
		}

		// Make it random
		criteria.add(Restrictions.sqlRestriction("1=1 order by rand()"));
		
		criteria.setMaxResults(maxResult);

		return criteria.list();
	}

	@Override
	public List<Word> findAll() {
		Session session = this.sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Word.class);
		
		return criteria.list();
	}

}
