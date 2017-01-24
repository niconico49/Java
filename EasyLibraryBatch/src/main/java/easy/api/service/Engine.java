/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//https://github.com/tmpvar/jsdom
//https://github.com/jfahrenkrug/SproutCoreNative/blob/master/SproutCoreNative/domcore.js
package easy.api.service;

import com.sun.net.httpserver.HttpExchange;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;

/**
 *
 * @author development
 */
public class Engine {

    private static IEngine iEngine = null;

    private static volatile boolean firstTime = true;
    private static volatile String scriptingCode = "";

    private static String replaceBadCharacters(String value) {
        value = value.replaceAll("\uFFFD", "\"");
        value = value.replaceAll("\u00EF", "\"");
        value = value.replaceAll("\u00BF", "\"");
        value = value.replaceAll("\u00BD", "\"");
        return value;
    }

    private static void starter(AbstractNoScriptObject abstractNoScriptObject) {
        String projectName = "EasyWakeOnLan";
        //webType = "desktop"
        //webType = "execute"
        //webType = ""
        String[] args = new String[] {
            String.format("http://127.0.0.1:7777/%s/", projectName),
            "desktop"
        };

        WebForm.setAbstractNoScriptObject(abstractNoScriptObject);
        WebForm.runner(args);

        //String result = abstractNoScriptObject.getComponentRequestResponse().getResponse();
        //String path = ComponentServer.getServerPathInfo();
        //String languageProgramming = ComponentServer.getLanguageProgramming();
        String path = (String)ComponentServer.getParameterShared("serverPathInfo");
        String languageProgramming = (String)ComponentServer.getParameterShared("languageProgramming");

        //String scriptCode = ComponentFile.getTxtFromFile(MessageFormat.format("{0}/Scripts/Deployer1.js", path));
        String scriptCode = new ComponentFile().getTxtFromFile(MessageFormat.format("{0}/Scripts/Deployer.js", path));
        scriptCode = Engine.replaceBadCharacters(scriptCode);

        iEngine = new JavaScriptEngine();

        abstractNoScriptObject.setComponentEngine(iEngine);

        iEngine.addObject("AbstractNoScriptObject", abstractNoScriptObject);

        iEngine.execute(scriptCode);

        scriptCode = (String)iEngine.invokeMethod("starter", languageProgramming);
        scriptingCode = scriptCode;
    }

    public static void execute1(String jsonData, ISession iSession, String path, HttpExchange httpExchange) {
        ComponentServer.addParameterShared("serverPathInfo", path);

        AbstractNoScriptObject abstractNoScriptObject = AbstractNoScriptObject.getInstance(jsonData, iSession);

        String projectName = "EasyWakeOnLan";
        WebForm.execute1(String.format("http://127.0.0.1:7777/%s/index.html", projectName), abstractNoScriptObject, httpExchange);
        //String result = abstractNoScriptObject.getComponentRequestResponse().getResponse();
        //String nick = "";
    }

    private static Object execute(String functionName, AbstractNoScriptObject abstractNoScriptObject, Object... parameters) {
        String projectName = "EasyWakeOnLan";
        //webType = "desktop"
        //webType = "execute"
        //webType = ""
        String[] args = new String[] {
            String.format("http://127.0.0.1:7777/%s/", projectName),
            "execute"
        };

        //WebForm.test(String.format("http://127.0.0.1:7777/%s/index.html", projectName), abstractNoScriptObject);
        WebForm.execute(String.format("http://127.0.0.1:7777/%s/index.html", projectName), abstractNoScriptObject);
        //WebForm.test(String.format("http://127.0.0.1:7777/%s/", projectName), UUID.randomUUID().toString());
        //while (abstractNoScriptObject.getComponentRequestResponse().getResponse() == null) {
        //}
        String result = abstractNoScriptObject.getComponentRequestResponse().getResponse();
        return result;
        //return defineWebEngine(args, abstractNoScriptObject);
/*
        IEngine _iEngine = iEngine.getInstance();
        _iEngine.addObject("AbstractNoScriptObject", abstractNoScriptObject);
        _iEngine.execute(scriptingCode);
        return _iEngine.invokeMethod(functionName, parameters);
*/        
/*
        Object result = null;

        if (firstTime) {
            firstTime = false;
            abstractNoScriptObject.setComponentEngine(iEngine);

            iEngine.addObject("AbstractNoScriptObject", abstractNoScriptObject);
            iEngine.execute(scriptingCode);
            result = iEngine.invokeMethod(functionName, parameters);
        }
        else {
            //IEngine _iEngine = new JavaScriptEngine();
            IEngine _iEngine = iEngine.getInstance();
            _iEngine.addObject("AbstractNoScriptObject", abstractNoScriptObject);
            _iEngine.execute(scriptingCode);
            result = _iEngine.invokeMethod(functionName, parameters);
            //iEngine.addObject("AbstractNoScriptObject", abstractNoScriptObject);
            //result = iEngine.invokeMethod(functionName, parameters);
        }
        return result;
*/
    }
    
    public static Object scriptEngine(String operationType, String functionName, AbstractNoScriptObject abstractNoScriptObject, Object... parameters) {
        //throws Exception {
        Object result = "";
        switch(operationType) {
            case "StarterAndExecute" :
                starter(abstractNoScriptObject);
                result = execute(functionName, abstractNoScriptObject, parameters);
                break;
            case "Starter" :
                starter(abstractNoScriptObject);
                break;
            case "Execute" :
                result = execute(functionName, abstractNoScriptObject, parameters);
                break;
        }
        return result;
    }

    public static Object dynamicInvoke(String operationType, String functionName, AbstractNoScriptObject abstractNoScriptObject, Object[] args) {
        //throws Exception {
        Object result = null;

        try {
            Class clazz = Class.forName(Engine.class.getCanonicalName());
            Method method = clazz.getMethod("scriptEngine", String.class, String.class, AbstractNoScriptObject.class, Object[].class);
            //Method method = clazz.getMethod("scriptEngine", Object[].class);
            result = method.invoke(null, operationType, functionName, abstractNoScriptObject, args);
        }
        catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            result = e.getMessage();
            //throw e;
            e.printStackTrace();
        }

        return result;
    }

    public static String interact(String jsonData, ISession iSession, String path, String operationType) {
        //throws Exception {
        //ComponentServer.setServerPathInfo(path);
        ComponentServer.addParameterShared("serverPathInfo", path);

        String result = "";
        try {
            AbstractNoScriptObject abstractNoScriptObject = AbstractNoScriptObject.getInstance(jsonData, iSession);
            Object internal = Engine.dynamicInvoke(operationType, "execute", abstractNoScriptObject, new Object[] {
                    jsonData
            });
            result = internal.toString();
        }
        catch (Exception e) {
            result = e.getMessage();
            //throw e;
        }

        return result;
    }
}
