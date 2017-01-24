/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.api.service;

import com.ibm.icu.text.MessageFormat;
import java.util.concurrent.ExecutorService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;

/**
 * REST Web Service
 *
 * @author development
 */
//@Path("generic")
@Path("api")
public class GenericResource {

	private HttpSession session;
    private ExecutorService executorService = java.util.concurrent.Executors.newCachedThreadPool();

	@POST
	@Path("execute")
	//@Produces({"application/xml", "application/json"})
	//@Produces({"application/octet-stream"})
	//@Produces({"text/plain"})
	//@Produces({"application/json"})
	//@Consumes({"application/json"})
	//@Produces({"application/json"})
	@Consumes({"text/plain"})
	@Produces({"text/plain"})
	public String execute(String jsonData, @Context HttpServletRequest request) {

		if (session == null) {
			session = request.getSession(true);
		}
/*
        String protocol = request.getScheme();
        String hostName = request.getServerName();
        int port = request.getServerPort();
        String pathName = request.getContextPath();
        pathName = pathName.substring(1);
*/        
/*
        ComponentServer.addParameter("Request.protocol", protocol);
        ComponentServer.addParameter("Request.hostName", hostName);
        ComponentServer.addParameter("Request.port", port);
        ComponentServer.addParameter("Request.pathName", pathName);
*/
        ComponentServer.addParameterShared("WebService.type", "Classic");
        ComponentServer.addParameterShared("WebService.path", "webresources/api/execute");
/*        
        Object[] params = {
            session.getServletContext().getRealPath("")
        };
        
        String path = MessageFormat.format("file:/{0}", params);
*/
        String path = session.getServletContext().getRealPath("");
        String operationType = "Execute";
        return Engine.interact(jsonData, new WebSession(session), path, operationType);
        //return Engine.interact(jsonData, new WebSession(session), session.getServletContext().getRealPath(""));
/*        
		try {
            return Engine.interact(jsonData, new WebSession(session), session.getServletContext().getRealPath(""));
        }
        catch(Exception e) {
            return e.getMessage();
        }
*/
    }

/*
	@POST
	@Path("execute")
	//@Produces({"application/xml", "application/json"})
	@Consumes({"application/json"})
	@Produces({"application/json"})
    public void execute(@Suspended
    final AsyncResponse asyncResponse, final String jsonData, @Context
    final HttpServletRequest request) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                asyncResponse.resume(doExecute(jsonData, request));
            }
        });
	}

    private String doExecute(String jsonData, @Context HttpServletRequest request) {
        if (session == null) {
            session = request.getSession(true);
        }
        
        return Engine.interact(jsonData, new WebSession(session), session.getServletContext().getRealPath(""));
    }
*/
/*
	@GET
	@Consumes({"application/json"})
	@Produces({"application/json"})
	public String getJson(String jsonData) {
		return "get Test Test";
		//TODO return proper representation object
		//throw new UnsupportedOperationException();
	}

	@PUT
    @Consumes("application/json")
	public void putJson(String content) {
	}
*/
}
