/*
  * To change this license header, choose License Headers in Project Properties.
  * To change this template file, choose Tools | Templates
  * and open the template in the editor.
  */
package easy.api.service;

import com.sun.net.httpserver.HttpExchange;
import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.FutureTask;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

//https://github.com/MachinePublishers/jBrowserDriver/
//http://stackoverflow.com/questions/814757/headless-internet-browser/814929#814929
//http://www.boost.org/doc/libs/1_60_0/libs/python/doc/html/tutorial/tutorial/exposing.html
//https://seleniumhq.github.io/selenium/docs/api/dotnet/html/T_OpenQA_Selenium_PhantomJS_PhantomJSDriverService.htm

//https://github.com/ariya/phantomjs/pull/11990/files

/*w  w  w.j  ava  2s  .c om*/
public class WebForm extends Application {

    private static AbstractNoScriptObject abstractNoScriptObject = new AbstractNoScriptObject();

    public static void setAbstractNoScriptObject(AbstractNoScriptObject value) {
        abstractNoScriptObject = value;
    }
    
    public static void execute1(String url, AbstractNoScriptObject value, HttpExchange httpExchange) {
        final FutureTask futureTask = new FutureTask(() -> {
            String result = "";
            WebEngine webEngine = new WebEngine();
           
            webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
                if (newState == Worker.State.SUCCEEDED) {
                    String response = value.getComponentRequestResponse().getResponse();
                    byte[] buffer = response.getBytes();
                    HttpHandlerRequest.makeOutputStream(httpExchange, buffer);
                }
            });

            JSObject jsObject = (JSObject)webEngine.executeScript("window");
            jsObject.setMember("AbstractNoScriptObject", value);
    
            webEngine.setJavaScriptEnabled(true);
            webEngine.load(url);
            return result;
        });
        PlatformUtil.runAndWait(futureTask);
    }
    
    public static void execute(String url, AbstractNoScriptObject value) {
        final FutureTask futureTask = new FutureTask(() -> {
            //test(url, value);
            String test = "TEST";
            WebEngine webEngine = new WebEngine();
           
            webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
                if (newState == Worker.State.SUCCEEDED) {
                    //String test1 = value.getComponentRequestResponse().getResponse();
                }
            });

            JSObject jsObject = (JSObject)webEngine.executeScript("window");
            jsObject.setMember("AbstractNoScriptObject", value);
    
            webEngine.setJavaScriptEnabled(true);
            webEngine.load(url);

            return test;
        });
        PlatformUtil.runAndWait(futureTask);
    }
    
    @Override
    public void start(final Stage stage) throws MalformedURLException {
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        
        stage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });

        webEngine.setOnAlert(event -> showAlert(event.getData()));
        webEngine.setConfirmHandler(message -> showConfirm(message));

        Parameters parameters = getParameters();
        
        //Map<String, String> namedParameters = parameters.getNamed();
        List<String> rawArguments = parameters.getRaw();
        //List<String> unnamedParameters = parameters.getUnnamed();
        Object[] args = rawArguments.toArray();
        String url = args[0].toString();
        String webType = args[1].toString();
/*        
        //System.out.println("start()");
        //System.out.println("\nnamedParameters -");
        namedParameters.entrySet().stream().forEach((entry) -> {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        });
        System.out.println("\nrawArguments -");
        rawArguments.stream().forEach((raw) -> {
            System.out.println(raw);
        });
        System.out.println("\nunnamedParameters -");
        unnamedParameters.stream().forEach((unnamed) -> {
            System.out.println(unnamed);
        });
*/
        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                // new page has loaded, process:
                //String test = abstractNoScriptObject.getComponentRequestResponse().getResponse();
                //String nick = "";
                //return test;
            }
        });

        JSObject jsObject = (JSObject)webEngine.executeScript("window");
        jsObject.setMember("AbstractNoScriptObject", abstractNoScriptObject);
        //String content = (String)webEngine.executeScript("window.external.notify(document.body.innerHTML);");

        //webEngine.setConfirmHandler((String p) -> isConfirmOK); 
        webEngine.setJavaScriptEnabled(true);

        //String path = System.getProperty("user.dir") + "/index.html";
        //webEngine.load("file:///" + path);
        webEngine.load(url);
        //webEngine.load(params[0] + "/Starter.html?test=test1&nick=nick1");
        //webEngine.load(String.format("http://127.0.0.1:7777/%s/", "EasyWakeOnLan"));
        //webEngine.load("http://java2s.com");

        stage.setScene(new Scene(new StackPane(webView)));
        if (webType.equals("desktop")) {
            stage.show();
        }
    }

    private static void showAlert(String message) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.getDialogPane().setContentText(message);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.show();
        //dialog.showingProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {});
        //dialog.showAndWait();
    }

    private static boolean showConfirm(String message) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.getDialogPane().setContentText(message);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
        return dialog.showAndWait().filter(ButtonType.YES::equals).isPresent();
    }

    public static void runner(String[] args) {
        launch(args);
    }
}
