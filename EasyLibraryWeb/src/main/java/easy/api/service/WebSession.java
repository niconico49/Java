/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.api.service;

import java.util.Collections;
import javax.servlet.http.HttpSession;

/**
 *
 * @author development
 */
public class WebSession 
	implements ISession {
	
	private HttpSession session;

	public WebSession(HttpSession session) {
		this.session = session;
	}

	@Override
	public Object getAttribute(String key) {
		return this.session.getAttribute(key);
	}

	@Override
	public void setAttribute(String key, Object value) {
		this.session.setAttribute(key, value);
	}

	@Override
	public void removeAllAttribute() {
		if (this.session != null) {
			for (Object name : Collections.list(this.session.getAttributeNames())) {
				this.session.removeAttribute((String)name);
			}
			this.session = null;
		}
	}
}
