package org.alphasights.techassessment.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

public class ServiceBase {

    @Context
    protected UriInfo context;
    @Context
    protected HttpServletRequest request;
    @Context
    protected HttpServletResponse response;

    protected Response responseData(int code, String body) {
        return Response
                .status(code)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .entity(body)
                .build();
    }

    protected Response responseData(int code) {
        return responseData(code, "");
    }

    public void send401() throws Exception {
        response.sendError(401);
    }

    public void send400() throws Exception {
        response.sendError(400);
    }

    public void send403() throws Exception {
        response.sendError(403);
    }
}
