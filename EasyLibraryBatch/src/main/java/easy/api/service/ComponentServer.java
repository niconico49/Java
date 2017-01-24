/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.api.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 *
 * @author development
 */
public class ComponentServer {
   	private static final HashMap<Object, Object> hashMap;
    static {
        hashMap = new HashMap<>();
        hashMap.put("languageProgramming", "Java");
    }

    public String execCmd(String cmd, String args) {
        String result = "";
        try {
            Process process = Runtime.getRuntime().exec(cmd + " " + args);
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                    result += (line + '\n');
                }
/*                
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += (line + '\n');
                }
*/    
            }
        }
        catch (IOException ex) {
        }
        return result;
    }

    public HashMap<Object, Object> getDictionary() {
		return hashMap;
	}

    public void addParameter(Object key, Object value) {
		hashMap.put(key, value);
	}

    public static void addParameterShared(Object key, Object value) {
		hashMap.put(key, value);
	}

    public Object getParameter(Object key) {
		return hashMap.get(key);
	}

    public static Object getParameterShared(Object key) {
		return hashMap.get(key);
	}

    public void removeParameter(Object key) {
        hashMap.remove(key);
	}

    public void clearParameter() {
		hashMap.clear();
	}

}
