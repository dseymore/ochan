package org.Ochan;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.Ochan.dao.CategoryDAO;
import org.Ochan.dao.ThreadDAO;
import org.Ochan.entity.Category;
import org.Ochan.entity.Post;
import org.Ochan.entity.TextPost;
import org.Ochan.entity.Thread;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestingSchema {

	private static final Log LOG = LogFactory.getLog(TestingSchema.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// Springing it up!
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml" });
		BeanFactory factory = (BeanFactory) context;

		LOG.debug("Got beanfactory: " + factory);

		CategoryDAO dao = (CategoryDAO) factory.getBean("categoryDAO");
		Category category = new Category("G", "Technology", null, null, null);
		dao.saveCategory(category);
		LOG.debug("saved category");


		List<Post> posts = new ArrayList<Post>();
		Post p = new TextPost(null, null, "me", "me2", "me3", "me4", "me5", new Date());
		posts.add(p);

		// lets make a thread
		ThreadDAO tDao = (ThreadDAO) factory.getBean("threadDAO");
		org.Ochan.entity.Thread thread = new Thread(null, new Date(), category, posts);
		tDao.create(thread);
		LOG.debug("created thread");

		// happy?

	}

}
