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
package org.ochan.dpl.service;

import java.util.prefs.Preferences;

import javax.jws.WebService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ochan.dpl.OchanEnvironment;
import org.ochan.dpl.SynchroDPL;
import org.ochan.dpl.replication.TransactionTemplate;
import org.ochan.service.SynchroService;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * Can not replicate the synchro service until the proxy is written on top of this. 
 * @author David Seymore 
 * Dec 17, 2009
 */
@WebService(endpointInterface = "org.ochan.service.SynchroService")
@ManagedResource(description = "Local Synchro Service", objectName = "Ochan:service=local,name=LocalSynchroService", logFile = "jmx.log")
public class LocalSynchroService implements SynchroService {

	private OchanEnvironment environment;
	private static final Log LOG = LogFactory.getLog(LocalSynchroService.class);
	private static final Preferences PREFERENCES = Preferences.userNodeForPackage(LocalSynchroService.class);
	
	//FIXME (for replicated shards) - even the synchro service should be proxied.... if the synchro
	//is on a replicated node.. it should be calling out to the other dude.. also the proxy should round robin/cache a set of 
	//ids to pass out.. so that we don't die just because we are replicated. 
	
	
	/**
	 * @param environment
	 *            the environment to set
	 */
	public void setEnvironment(OchanEnvironment environment) {
		this.environment = environment;
	}

	@Override
	public Long getSync() {
		try {
			final SynchroDPL dpl = new SynchroDPL();
			new TransactionTemplate(environment){
				public void doInTransaction(){
					environment.synchroByIdentifier().put(dpl);
				}
			}.run();
			return dpl.getIdentifier();
		} catch (Exception e) {
			LOG.error("ugh", e);
		}
		return null;
	}

}
