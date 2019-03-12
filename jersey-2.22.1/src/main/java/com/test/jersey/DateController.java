package com.test.jersey;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Date;

@Path("/date")
public class DateController {

    @GET
    @Path("/test")
    @Consumes(value={"application/json"})
    @Produces(value={"application/json"})
    public Response getDate(@QueryParam(value="todayDate") Date date){
        return Response.ok("OK: " + date.toString()).build();
    }
}
