package org.Ochan.entity.comparator;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.Ochan.entity.Post;
import org.Ochan.entity.Thread;

public class AscendingThreadComparator implements Comparator<Thread> {

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
		List<Post> p = t.getPosts();
		Collections.sort(p);
		// take the last one, and return its time
		Post post = p.get(p.size() -1);
		return post.getTime().getTime();
	}
}