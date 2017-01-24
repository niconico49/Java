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
public class EasyLibraryBatch {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
        //throws Exception {
		String jsonData = "";
        String operationType = "StarterAndExecute";
        ComponentRequestResponse componentRequestResponse = new ComponentRequestResponse();
        componentRequestResponse.setRequest(jsonData);
        Engine.interact(jsonData, new BatchSession(new HashMap()), System.getProperty("user.dir"), operationType);
	}
}
