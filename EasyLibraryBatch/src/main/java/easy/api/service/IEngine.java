/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package easy.api.service;

/**
 *
 * @author development
 */
public interface IEngine {
    IEngine getInstance();
    public void addObject(String aliasName, Object obj);
    public void execute(String sScriptCode);
    public Object invokeMethod(String functionName, Object... obj);
}
