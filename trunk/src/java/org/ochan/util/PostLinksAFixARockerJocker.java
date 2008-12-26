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
