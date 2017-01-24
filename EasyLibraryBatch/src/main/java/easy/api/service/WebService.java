/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package easy.api.service;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;

/**
 *
 * @author Development
 */
public class WebService {
    private static boolean isStarted = false;
    private static String path;
    private static String projectName;
    private static String host;
    private static int port;

    public static void setHost(String value) {
        WebService.host = value;
    }

    public static String getHost() {
        return WebService.host;
    }

    public static void setPort(int value) {
        WebService.port = value;
    }

    public static int getPort() {
        return WebService.port;
    }

    public static void setPath(String value) {
        WebService.path = value;
    }

    public static String getPath() {
        return WebService.path;
    }

    public static void setProjectName(String value) {
        WebService.projectName = value;
    }

    public static String getProjectName() {
        return WebService.projectName;
    }

    private static HttpServer httpServer;

    public static String start(String path, String projectName) {
        //String path = System.AppDomain.CurrentDomain.BaseDirectory;
        //WebService.setPath(System.AppDomain.CurrentDomain.BaseDirectory);
        WebService.setPath(path);
        WebService.setProjectName(projectName);

        WebService.setHost("127.0.0.1");
        WebService.setPort(7777);
        //String url = "http://*:7777/" & projectName & "/";
        //String url = "http://127.0.0.1:7777/";
        try {
            //httpServer = HttpServer.create(new InetSocketAddress("127.0.0.1", 7777), 0);
            httpServer = HttpServer.create(new InetSocketAddress(WebService.getHost(), WebService.getPort()), 0);
        }
        catch (IOException ex) {
        }

        //HttpContext httpContext = httpServer.createContext("/" + WebService.projectName, new HttpHandlerRequest());
        httpServer.createContext("/" + WebService.projectName, new HttpHandlerRequest());
        //Creates a default executor
        httpServer.setExecutor(null);
        //httpServer.setExecutor(Executors.newFixedThreadPool(20));
        httpServer.start();
        String result = WebService.execute("{}", new BatchSession(new HashMap()));
        return result;
    }

    public static void httpListenerStop() {
        httpServer.stop(0);
        //httpListener.Stop();
    }

    public static String execute1(String jsonData, ISession iSession, HttpExchange httpExchange) {
        ComponentServer.addParameterShared("WebService.type", "RESTful");
        ComponentServer.addParameterShared("WebService.path", "api/execute");
        Engine.execute1(jsonData, iSession, WebService.getPath(), httpExchange);
        return "";
    }

    public static String execute(String jsonData, ISession iSession) {
        ComponentServer.addParameterShared("WebService.type", "RESTful");
        ComponentServer.addParameterShared("WebService.path", "api/execute");

        String operationType = "Execute";

        if (!WebService.isStarted) {
            WebService.isStarted = true;
            operationType = "Starter";
        }

        return Engine.interact(jsonData, iSession, WebService.getPath(), operationType);
    }
}
