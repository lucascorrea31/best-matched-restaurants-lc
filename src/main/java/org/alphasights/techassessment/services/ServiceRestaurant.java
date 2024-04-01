package org.alphasights.techassessment.services;

import org.alphasights.techassessment.bo.BORestaurant;
import org.alphasights.techassessment.models.Restaurant;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.*;
import java.util.Objects;

@Path("restaurants")
public class ServiceRestaurant extends ServiceBase {

    @GET
    @Path("{id}")
    @Produces(ApplicationConfig.APPLICATION_JSON_CHARSET_UTF8)
    @Consumes(ApplicationConfig.APPLICATION_JSON_CHARSET_UTF8)
    public String get(@PathParam("id") int id) {
        return Objects.requireNonNullElse(BORestaurant.get(id), new JSONObject()).toString();
    }

    @GET
    @Produces(ApplicationConfig.APPLICATION_JSON_CHARSET_UTF8)
    @Consumes(ApplicationConfig.APPLICATION_JSON_CHARSET_UTF8)
    public String list() {
        return Objects.requireNonNullElse(BORestaurant.getAll(), new JSONArray()).toString();
    }

    @GET
    @Path("search/{name}")
    @Produces(ApplicationConfig.APPLICATION_JSON_CHARSET_UTF8)
    @Consumes(ApplicationConfig.APPLICATION_JSON_CHARSET_UTF8)
    public String getByName(@PathParam("name") String name) {
        return Objects.requireNonNullElse(BORestaurant.searchByName(name), new JSONObject()).toString();
    }
}
