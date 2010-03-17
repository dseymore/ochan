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
package org.ochan.exception.handler;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ochan.util.ManagedCommonsMultipartResolver;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * Spring has a way of handling exceptions INSIDE the default servlet.. instead of relying on shady error-page stuff. 
 * @author David Seymore 
 * Nov 12, 2008
 */
public class FileSizeHandlerExceptionResolver implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) {
		if (arg3 instanceof MaxUploadSizeExceededException){
			Map<String,Object> stuff = new HashMap<String,Object>();
			ManagedCommonsMultipartResolver commonsMultipartResolver = new ManagedCommonsMultipartResolver();
			stuff.put("currentLimit", commonsMultipartResolver.getMaxUploadSize());
			stuff.put("threadId",arg0.getAttribute("parent"));
			stuff.put("categoryId",arg0.getAttribute("categoryIdentifier"));
			return new ModelAndView("errorFileTooBig", stuff);
		}
		//pass this thing.
		return null;
	}
}
