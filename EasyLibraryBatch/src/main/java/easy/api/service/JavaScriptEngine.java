/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package easy.api.service;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 *
 * @author development
 */
public class JavaScriptEngine implements IEngine {

    private static volatile CompiledScript compiledScript = null;
    private ScriptEngine scriptEngine =  null;
    private Bindings bindings = null;
    
    @Override
    public IEngine getInstance() {
        return new JavaScriptEngine();
    }

    public JavaScriptEngine() {
        this.scriptEngine = new ScriptEngineManager().getEngineByName("JavaScript");
    }
    
    @Override
    public void addObject(String aliasName, Object obj) {
        this.bindings = scriptEngine.getBindings(ScriptContext.ENGINE_SCOPE);
        this.bindings.put(aliasName, obj);
    }

    @Override
    public void execute(String sScriptCode) {
        try {
            Compilable compilable = (Compilable)scriptEngine;
            compiledScript = compilable.compile(sScriptCode);
            compiledScript.eval(this.bindings);
        }
        catch (ScriptException e) {
        }
    }

    @Override
    public Object invokeMethod(String functionName, Object... args) {
        Object result = "";
        try {
            Invocable invocable = (Invocable)compiledScript.getEngine();
            result = invocable.invokeFunction(functionName, args);
        }
        catch (ScriptException | NoSuchMethodException e) {
            String text = e.getMessage();
        }

        return result;
    }
}
