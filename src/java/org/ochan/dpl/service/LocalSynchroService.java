package org.ochan.dpl.service;

import java.util.prefs.Preferences;

import javax.jws.WebService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ochan.dpl.SleepyEnvironment;
import org.ochan.dpl.SynchroDPL;
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

	private SleepyEnvironment environment;
	private static final Log LOG = LogFactory.getLog(LocalSynchroService.class);
	private static final Preferences PREFERENCES = Preferences.userNodeForPackage(LocalSynchroService.class);
	
	//FIXME (for replicated shards) - even the synchro service should be proxied.... if the synchro
	//is on a replicated node.. it should be calling out to the other dude.. also the proxy should round robin/cache a set of 
	//ids to pass out.. so that we don't die just because we are replicated. 
	
	
	/**
	 * @param environment
	 *            the environment to set
	 */
	public void setEnvironment(SleepyEnvironment environment) {
		this.environment = environment;
	}

	@Override
	public Long getSync() {
		try {
			SynchroDPL dpl = new SynchroDPL();
			environment.synchroByIdentifier.put(dpl);
			return dpl.getIdentifier();
		} catch (Exception e) {
			LOG.error("ugh", e);
		}
		return null;
	}

}
