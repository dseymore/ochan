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
package org.ochan.job;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.ochan.api.PluginJob;

/*8
 * 
 */
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