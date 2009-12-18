package org.ochan.dpl.replication;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sleepycat.je.rep.StateChangeEvent;

/**
 * This bean is an observer to the berkeleydb replication mechanism. It exposes
 * the current configuration, and RECONFIGURES the node to point to the proper
 * master when the state of the game changes.
 * 
 * @author David Seymore
 */
public class StateChangeListener implements com.sleepycat.je.rep.StateChangeListener {

	private static final Log LOG = LogFactory.getLog(StateChangeListener.class);
	private static final String WRITE_LOCAL = "Magic key to tell us to write locally";
	
	private String currentMaster = WRITE_LOCAL;

	@Override
	public void stateChange(StateChangeEvent stateChangeEvent) throws RuntimeException {
		//synchronizing on state changes so that reads of state are blocked ONLY when in contention. 
		synchronized (WRITE_LOCAL) {
			if (LOG.isWarnEnabled()) {
				LOG.warn("A vote just occurred, responding: "
						+ new ToStringBuilder(stateChangeEvent)
								.append("Time", stateChangeEvent.getEventTime())
								.append("State", stateChangeEvent.getState())
								.append("Master",stateChangeEvent.getMasterNodeName()));
			}
			switch (stateChangeEvent.getState()) {
	
			case MASTER:
				currentMaster = WRITE_LOCAL;
			case REPLICA:
				currentMaster = stateChangeEvent.getMasterNodeName();
				break;
			default:
				LOG.error("Unkown state!, master is now null!!");
				currentMaster = null;
				break;
			}
		}
	}
	
	public boolean isMaster(){
		if (WRITE_LOCAL.equals(currentMaster)){
			return true;
		}
		return false;
	}

	public String getMasterNodeName(){
		return currentMaster;
	}
}
