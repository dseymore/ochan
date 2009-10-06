package org.ochan.service;

import java.util.HashMap;
import java.util.List;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlRootElement;

import org.ochan.entity.Thread;

/**
 * 
 * @author David Seymore Oct 27, 2007
 */
@WebService
public interface ThreadService {

	/**
	 * 
	 * @author David Seymore Oct 27, 2007
	 */
	@XmlRootElement
	public class ThreadCriteria{
		private Object category;
		private Object deleteQueue;
		private Object notDeleted;
		private Object newerThan;
		private Object max;
		/**
		 * @return the category
		 */
		public Object getCategory() {
			return category;
		}
		/**
		 * @param category the category to set
		 */
		public void setCategory(Object category) {
			this.category = category;
		}
		/**
		 * @return the deleteQueue
		 */
		public Object getDeleteQueue() {
			return deleteQueue;
		}
		/**
		 * @param deleteQueue the deleteQueue to set
		 */
		public void setDeleteQueue(Object deleteQueue) {
			this.deleteQueue = deleteQueue;
		}
		/**
		 * @return the notDeleted
		 */
		public Object getNotDeleted() {
			return notDeleted;
		}
		/**
		 * @param notDeleted the notDeleted to set
		 */
		public void setNotDeleted(Object notDeleted) {
			this.notDeleted = notDeleted;
		}
		/**
		 * @return the newerThan
		 */
		public Object getNewerThan() {
			return newerThan;
		}
		/**
		 * @param newerThan the newerThan to set
		 */
		public void setNewerThan(Object newerThan) {
			this.newerThan = newerThan;
		}
		/**
		 * @return the max
		 */
		public Object getMax() {
			return max;
		}
		/**
		 * @param max the max to set
		 */
		public void setMax(Object max) {
			this.max = max;
		}
		
		
		
	};

	/**
	 * 
	 * @param thisIdentifier
	 *            (DONT USE ME)
	 * @param category
	 * @param author
	 * @param subject
	 * @param url
	 * @param email
	 * @param content
	 * @param file
	 *            (null if not an image post)
	 * @param filename
	 *            (null if not an image post)
	 */
	public void createThread(Long thisIdentifier, Long category, String author, String subject, String url, String email, String content, Byte[] file, String filename);

	/**
	 * 
	 * @param criteria
	 * @return
	 */
	public List<Thread> retrieveThreads(ThreadCriteria criteria);

	/**
	 * 
	 * @param identifier
	 * @return
	 */
	public Thread getThread(Long identifier);

	/**
	 * 
	 * @param identifier
	 */
	public void deleteThread(Long identifier);

	/**
	 * 
	 * @param thread
	 */
	public void updateThread(Thread thread);
}
