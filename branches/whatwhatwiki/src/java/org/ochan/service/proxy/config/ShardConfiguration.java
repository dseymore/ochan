package org.ochan.service.proxy.config;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.ochan.service.SynchroService;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource(description = "Configuration for the hosts to be used by this deployment for scaling the dataservices.. YOU CANNOT CHANGE THIS without losing all the data.", objectName = "Ochan:type=scale,name=ShardConfiguration", logFile = "jmx.log")
public class ShardConfiguration {

	private static final Log LOG = LogFactory.getLog(ShardConfiguration.class);
	private static final Preferences PREFERENCES = Preferences.userNodeForPackage(ShardConfiguration.class);

	private JaxWsProxyFactoryBean synchro;

	/**
	 * @param synchro
	 *            the synchro to set
	 */
	public void setSynchro(JaxWsProxyFactoryBean synchro) {
		this.synchro = synchro;
	}

	public boolean isShardEnabled() {
		return !getShardHosts().isEmpty();
	}

	public List<String> getShardHosts() {
		List<String> list = new ArrayList<String>();
		if (StringUtils.isNotEmpty(getHostList())) {
			String[] hosts = StringUtils.split(getHostList(), ',');
			for (String host : hosts) {
				list.add(host);
			}
		}
		return list;

	}

	@ManagedAttribute
	public void setHostList(String val) {
		if (StringUtils.split(",").length > 0) {
			PREFERENCES.put("HOSTS", val);
		}
	}

	@ManagedAttribute
	public String getHostList() {
		return PREFERENCES.get("HOSTS", "");
	}

	@ManagedAttribute
	public String getSynchroHost() {
		return PREFERENCES.get("SYNCHRO", "");
	}

	@ManagedAttribute
	public void setSynchroHost(String host) {
		PREFERENCES.put("SYNCHRO", host);
	}

	public SynchroService getSynchroService() {
		synchro.setAddress(getSynchroHost() + "/remote/synchro");
		SynchroService client = (SynchroService) synchro.create();
		return client;
	}

	public String whichHost(Long id) {
		int mod = getShardHosts().size();
		int whichHost = id.intValue() % mod;

		return getShardHosts().get(whichHost);

	}

}
