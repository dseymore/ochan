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
import java.util.Comparator;

import org.ochan.entity.Post;

/**
 * 
 * @author dseymore
 * 
 */
public class AscendingPostComparator implements Comparator<Post>, Serializable {

	/**
	 * Generated Serial Ver. ID
	 */
	private static final long serialVersionUID = -116675767405495720L;

	/**
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Post o1, Post o2) {
		if (o1 == null && o2 == null) {
			return 0;
		} else if (o1 == null && o2 != null) {
			return -1;
		} else if (o2 == null && o1 != null) {
			return 1;
		}
		return o1.getTime().compareTo(o2.getTime());
	}

}
