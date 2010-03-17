/*
Ochan - image board/anonymous forum
Copyright (C) 2010  David Seymore

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/
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
