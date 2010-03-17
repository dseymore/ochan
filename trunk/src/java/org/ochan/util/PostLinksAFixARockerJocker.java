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
package org.ochan.util;

import org.apache.commons.lang.StringUtils;
import org.ochan.entity.TextPost;

public class PostLinksAFixARockerJocker {

	/**
	 * Method to create the html to do inter-post post linking like a rock star!
	 * @param dude
	 * @return
	 */
	public static String fixMahLinks(TextPost dude, boolean samepage){
		String[] pieces = dude.getComment().split("&gt;&gt;");
		if (pieces != null && pieces.length > 1){
			StringBuilder value = new StringBuilder();
			boolean first = true;
			for (String x : pieces){
				if(first){
					//first one, nothing to do here.
					value.append(x);
					first = false;
				}else{
					//now try and find how long the # is..
					boolean found = false;
					for (int i = x.length(); i > -1; i--){
						String num = x.substring(0,i);
						if (StringUtils.isNumeric(num)){
							value.append("<a href=\"");
							if (!samepage){
								//lets add the url for the thred viewer
								value.append("/chan/thread/").append(dude.getParent().getIdentifier());
							}
							value.append("#").append(num).append("\" onclick=\"replyhl(").append(num).append(")");
							if (!samepage){
								value.append(" target=\"_blank\" ");
							}
							value.append("\">&gt;&gt;").append(num).append("</a> ").append(x.substring(i));
							found = true;
							break;
						}
					}
					if(!found){
						value.append(x);
					}
				}
			}
			return value.toString();
		}
		return dude.getComment();
	}
	
}
