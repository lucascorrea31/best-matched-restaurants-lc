package org.alphasights.techassessment.services;

import org.alphasights.techassessment.bo.BOCuisine;
import org.alphasights.techassessment.models.Cuisine;
import org.json.JSONObject;

import javax.ws.rs.*;
import java.util.Objects;

@Path("cuisines")
public class ServiceCuisine extends ServiceBase {

    @GET
    @Path("{id}")
    @Produces(ApplicationConfig.APPLICATION_JSON_CHARSET_UTF8)
    @Consumes(ApplicationConfig.APPLICATION_JSON_CHARSET_UTF8)
    public String get(@PathParam("id") int id) {
        return Objects.requireNonNullElse(BOCuisine.get(id), new JSONObject()).toString();
    }

    @GET
    @Produces(ApplicationConfig.APPLICATION_JSON_CHARSET_UTF8)
    @Consumes(ApplicationConfig.APPLICATION_JSON_CHARSET_UTF8)
    public String list() {
        return Objects.requireNonNull(BOCuisine.getAll()).toString();
    }

}
