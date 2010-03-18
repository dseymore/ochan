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
package org.ochan.api;

import java.io.Serializable;

import org.ochan.service.ThreadService;

/**
 * This interfact defines the mechanism in which plugins may have jobs to do their work
 * @author dseymore
 */
public interface PluginJob extends Serializable{
	
	/**
	 * This method is called after the thread service is injected; You should DOCUMENT
	 * your configuration, and consumers plug this in through the plugin-job-job jmx configuration 
	 * @param json
	 */
	public void configure(String json);
	
	/**
	 * This method is called first, to inject the thread service into the job
	 * @param threadService
	 */
	public void setThreadService(ThreadService threadService);
	
	/**
	 * This is ran on the schedule of the plugin-job-job of the ochan deployment.
	 */
	public void execute();
}
