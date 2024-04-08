package org.alphasights.techassessment.services;

import org.alphasights.techassessment.bo.BORestaurant;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
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
    @Path("search")
    @Produces(ApplicationConfig.APPLICATION_JSON_CHARSET_UTF8)
    @Consumes(ApplicationConfig.APPLICATION_JSON_CHARSET_UTF8)
    public String search(@Context HttpHeaders hh) {
        MultivaluedMap<String, String> headerParams = hh.getRequestHeaders();

        JSONObject filters = new JSONObject();
        if (headerParams.containsKey("name")) {
            filters.put("name", headerParams.getFirst("name"));
        }
        if (headerParams.containsKey("customer_rating")) {
            filters.put("customer_rating", headerParams.getFirst("customer_rating"));
        }
        if (headerParams.containsKey("distance")) {
            filters.put("distance", headerParams.getFirst("distance"));
        }
        if (headerParams.containsKey("price")) {
            filters.put("price", headerParams.getFirst("price"));
        }
        if (headerParams.containsKey("cuisine")) {
            filters.put("cuisine", headerParams.getFirst("cuisine"));
        }

        return Objects.requireNonNullElse(BORestaurant.search(filters), new JSONObject()).toString();
    }
}
