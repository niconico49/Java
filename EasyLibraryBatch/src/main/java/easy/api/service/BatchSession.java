/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.api.service;

import java.util.HashMap;

/**
 *
 * @author development
 */
public class BatchSession 
	implements ISession {
	
	private HashMap hashMap;
	
	public BatchSession(HashMap hashMap) {
		this.hashMap = hashMap;
	}

	@Override
	public Object getAttribute(String key) {
		return this.hashMap.get(key);
	}

	@Override
	public void setAttribute(String key, Object value) {
		this.hashMap.put(key, value);
	}

	@Override
	public void removeAllAttribute() {
		if (this.hashMap != null) {
			this.hashMap.clear();
		}
	}
}
