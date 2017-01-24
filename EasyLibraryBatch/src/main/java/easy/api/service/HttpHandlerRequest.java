/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package easy.api.service;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;

/**
 *
 * @author Development
 */
class HttpHandlerRequest implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String uri = httpExchange.getRequestURI().getPath();
        String slashProjectName = "/" + WebService.getProjectName();

        int switchCmd = -1;

        if (uri.equals(slashProjectName + "/")) {
            switchCmd = 0;
        }
        else {
            if (uri.equals(slashProjectName + "/webresources/api/execute")) {
                switchCmd = 1;

            }
            else {
               if (uri.indexOf(slashProjectName) > -1) {
                    switchCmd = 2;
                }
            }
        }
        switch (switchCmd) {
            case 0:
                //Starter.html
                String filename = WebService.getPath() + "/Starter.html";

                makeOutputStreamFromFileName(httpExchange, filename);
                break;
            case 1:
                String requestData = null;
                if ("get".equalsIgnoreCase(httpExchange.getRequestMethod())) {
                    requestData = httpExchange.getRequestURI().getRawQuery();
                }

                if ("post".equalsIgnoreCase(httpExchange.getRequestMethod())) {
                    InputStreamReader inputStreamReader = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    requestData = bufferedReader.readLine();
                }

                String[] requestFields = requestData.split("&");

                String jsonData = requestFields[0];

                String result1 = WebService.execute1(jsonData, new BatchSession(new HashMap()), httpExchange);
                //String result = WebService.execute(jsonData, new BatchSession(new HashMap()));
                //String result = WebService.execute(jsonData, new BatchSession(new HashMap()));

                //byte[] buffer = result.getBytes();
                //makeOutputStream(httpExchange, buffer);
                break;
            case 2:
                int projectNameSize = slashProjectName.length();
                int uriSize = uri.length();

                String fileName = WebService.getPath() + uri.substring(uri.indexOf(slashProjectName) + projectNameSize, uriSize);

                makeOutputStreamFromFileName(httpExchange, fileName);
                break;
        }
    }
    
    public static void makeOutputStream(HttpExchange httpExchange, byte[] buffer) {
        try {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, buffer.length);
            
            try (OutputStream outputStream = httpExchange.getResponseBody()) {
                outputStream.write(buffer);
            }
        }
        catch (IOException ex) {
        }
    }

    public static void makeOutputStreamFromFileName(HttpExchange httpExchange, String fileName) {
        byte[] buffer = new ComponentFile().getBufferFromFile(fileName);

        String fileExtension = fileNameExtension(fileName);
        String contentType = contentType(fileExtension);

        Headers headers = httpExchange.getResponseHeaders();
        //headers.set("Content-Type", contentType);
        headers.add("Content-Type", contentType);
        
        makeOutputStream(httpExchange, buffer);
    }

    public static String fileNameExtension(String fileName) {
        String result = fileName.substring(fileName.lastIndexOf(".") + 1);
        return result;
    }

    public static String contentType(String value) {
        String result = "text/plain";

        switch (value) {
            case "htm":
            case "html":
                result = "text/html";
                break;
            case "css":
                result = "text/css";
                break;
            case "less":
                result = "text/less";
                break;
            case "bmp":
                result = "image/bmp";
                break;
            case "cod":
                result = "image/cis-cod";
                break;
            case "png":
                result = "image/png";
                break;
            case "gif":
                result = "image/gif";
                break;
            case "ief":
                result = "image/ief";
                break;
            case "jpe":
            case "jpeg":
            case "jpg":
                result = "image/jpeg";
                break;
            case "jfif":
                result = "image/pipeg";
                break;
            case "svg":
                result = "image/svg+xml";
                break;
            case "tif":
            case "tiff":
                result = "image/tiff";
                break;
            case "js":
                result = "application/javascript";
                break;
        }
        return result;
    }
}
