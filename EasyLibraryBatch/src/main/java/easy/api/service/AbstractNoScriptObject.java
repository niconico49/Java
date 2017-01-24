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
public class AbstractNoScriptObject {
    private ComponentConnection componentConnection = null;
    private ComponentDevelopment componentDevelopment = null;
    private ComponentFile componentFile = null;
    private ComponentMail componentMail = null;
    private ComponentServer componentServer = null;
    private ComponentSocket componentSocket = null;
    private ComponentThread componentThread = null;
    private ComponentRequestResponse componentRequestResponse = null;
    private ISession iSession = null;
    private IEngine iEngine = null;

    public static AbstractNoScriptObject getInstance() {
        return new AbstractNoScriptObject();
    }

    public static AbstractNoScriptObject getInstance(String value, ISession iSession) {
        AbstractNoScriptObject abstractNoScriptObject = AbstractNoScriptObject.getInstance();
        abstractNoScriptObject.setComponentConnection(new ComponentConnection());
        abstractNoScriptObject.setComponentDevelopment(new ComponentDevelopment());
        abstractNoScriptObject.setComponentFile(new ComponentFile());
        abstractNoScriptObject.setComponentMail(new ComponentMail());
        abstractNoScriptObject.setComponentServer(new ComponentServer());
        abstractNoScriptObject.setComponentSocket(new ComponentSocket());
        abstractNoScriptObject.setComponentThread(new ComponentThread());
        ComponentRequestResponse componentRequestResponse = new ComponentRequestResponse();
        componentRequestResponse.setRequest(value);
        abstractNoScriptObject.setComponentRequestResponse(componentRequestResponse);
        //abstractNoScriptObject.setComponentRequestResponse(new ComponentRequestResponse());
        abstractNoScriptObject.setComponentSession(iSession);
        return abstractNoScriptObject;
    }
    
    public ComponentConnection getComponentConnection() {
        return this.componentConnection;
    }

    public void setComponentConnection(ComponentConnection value) {
        this.componentConnection = value;
    }

    public ComponentDevelopment getComponentDevelopment() {
        return this.componentDevelopment;
    }

    public void setComponentDevelopment(ComponentDevelopment value) {
        this.componentDevelopment = value;
    }

    public ComponentFile getComponentFile() {
        return this.componentFile;
    }

    public void setComponentFile(ComponentFile value) {
        this.componentFile = value;
    }

    public ComponentMail getComponentMail() {
        return this.componentMail;
    }

    public void setComponentMail(ComponentMail value) {
        this.componentMail = value;
    }

    public ComponentServer getComponentServer() {
        return this.componentServer;
    }

    public void setComponentServer(ComponentServer value) {
        this.componentServer = value;
    }

    public ComponentSocket getComponentSocket() {
        return this.componentSocket;
    }

    public void setComponentSocket(ComponentSocket value) {
        this.componentSocket = value;
    }
    
    public ComponentThread getComponentThread() {
        return this.componentThread;
    }

    public void setComponentThread(ComponentThread value) {
        this.componentThread = value;
    }

    public ComponentRequestResponse getComponentRequestResponse() {
        return componentRequestResponse;
    }

    public void setComponentRequestResponse(ComponentRequestResponse value) {
        this.componentRequestResponse = value;
    }

    public ISession getComponentSession() {
        return this.iSession;
    }

    public void setComponentSession(ISession value) {
        this.iSession = value;
    }

    public IEngine getComponentEngine() {
        return this.iEngine;
    }

    public void setComponentEngine(IEngine value) {
        this.iEngine = value;
    }
}
