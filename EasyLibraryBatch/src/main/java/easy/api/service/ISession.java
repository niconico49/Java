/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.api.service;

/**
 *
 * @author development
 */
public interface ISession {
	public Object getAttribute(String key);
	public void setAttribute(String key, Object value);
	public void removeAllAttribute();
}
