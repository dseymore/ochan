package org.ochan.entity.comparator;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.ochan.entity.Post;
import org.ochan.entity.Thread;

public class AscendingThreadComparator implements Comparator<Thread>, Serializable {

	
	/**
	 * Genereated Serial Ver ID
	 */
	private static final long serialVersionUID = -2790487850308252589L;

	@Override
	public int compare(Thread o1, Thread o2) {
		if (lastPostDate(o1) <= lastPostDate(o2))
			return 1;
		else
			return -1;
	}

	/**
	 * returns the post date of the last post in a thread
	 * 
	 * @param t
	 * @return
	 */
	public static long lastPostDate(Thread t) {
		if (t.isEnabled()){
			List<Post> p = t.getPosts();
			Collections.sort(p);
			// take the last one, and return its time
			Post post = p.get(p.size() -1);
			return post.getTime().getTime();
		}else{
			//bye bye
			return -1;
		}
	}
}
