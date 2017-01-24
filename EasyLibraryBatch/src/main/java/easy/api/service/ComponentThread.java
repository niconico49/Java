/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.api.service;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Development
 */
public class ComponentThread {
    private static Timer timer = new Timer();
   	private static final HashMap<String, TimerTask> hashMap = new HashMap<>();
    
    public String setThreadTimer(TimerTask timerTask, long delay, boolean onlyOnce, String id) {
        if (onlyOnce) {
            //setTimeout
            timer.schedule(timerTask, delay);
        }
        else {
            //setInterval
            timer.schedule(timerTask, delay, delay);
        }
        
        hashMap.put(id, timerTask);

        return id;
    }

    public void cancelThreadTimer(String id) {
        if (hashMap.containsKey(id)) {
            TimerTask timerTask = hashMap.get(id);
            timerTask.cancel();
            hashMap.remove(id);
            timer.purge();
       }
    }
}
