package org.ochan.entity.comparator;

import java.io.Serializable;
import java.util.Comparator;

import org.ochan.entity.Post;

public class AscendingPostComparator implements Comparator<Post>, Serializable {

	/**
	 * Generated Serial Ver. ID
	 */
	private static final long serialVersionUID = -116675767405495720L;

	/**
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Post o1, Post o2) {
		if (o1 == null && o2 == null){
			return 0;
		}else if (o1 == null && o2 != null){
			return -1;
		}else if (o2 == null && o1 != null){
			return 1;
		}
		return o1.getTime().compareTo(o2.getTime());
	}

	
}