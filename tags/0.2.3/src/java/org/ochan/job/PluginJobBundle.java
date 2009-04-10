package org.ochan.job;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.ochan.api.PluginJob;

public enum PluginJobBundle {
	INSTANCE;

	private transient Map<String, PluginJob> bundle;

	private PluginJobBundle() {
		bundle = new HashMap<String, PluginJob>();
	}

	public void clear() {
		bundle.clear();
	}

	public boolean containsKey(String key) {
		return bundle.containsKey(key);
	}

	public boolean containsValue(PluginJob value) {
		return bundle.containsValue(value);
	}

	public PluginJob get(String key) {
		return bundle.get(key);
	}

	public boolean isEmpty() {
		return bundle.isEmpty();
	}

	public Set<String> keySet() {
		return bundle.keySet();
	}

	public Object put(String key, PluginJob value) {
		return bundle.put(key, value);
	}

	public Object remove(String key) {
		return bundle.remove(key);
	}

}