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
	private static final String WRITE_LOCAL = "Magic";
	
	private String currentMaster = WRITE_LOCAL;

	@Override
	public void stateChange(StateChangeEvent stateChangeEvent) throws RuntimeException {
		//synchronizing on state changes so that reads of state are blocked ONLY when in contention. 
		synchronized (WRITE_LOCAL) {
			if (LOG.isWarnEnabled()) {
				try{
					LOG.fatal("A vote just occurred, responding: "
							+ new ToStringBuilder(stateChangeEvent)
									.append("Time", stateChangeEvent.getEventTime())
									.append("State", stateChangeEvent.getState())
									.append("Master",stateChangeEvent.getMasterNodeName()));
				}catch(Exception e){
					LOG.warn("Oops.... logging caused an exception:", e);
				}
			}
			switch (stateChangeEvent.getState()) {
	
			case MASTER:
				currentMaster = WRITE_LOCAL;
				break;
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
		boolean value = true;
		if (!WRITE_LOCAL.equals(currentMaster)){
			value = false;
		}
		if (LOG.isDebugEnabled()){
			LOG.debug("Informing service that this node is " +  (value ? "a master" : "a slave"));
		}
		return value;
	}

	public String getMasterNodeName(){
		return currentMaster;
	}
}
