package org.alphasights.techassessment.bo;

import org.alphasights.techassessment.dao.CuisineDAO;
import org.alphasights.techassessment.dao.RestaurantDAO;
import org.alphasights.techassessment.models.Cuisine;
import org.alphasights.techassessment.models.Restaurant;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BORestaurant {
    private static final Logger LOGGER = Logger.getLogger(BORestaurant.class.getName());

    public static Restaurant get(int id) {
        try {
            RestaurantDAO restaurantDAO = new RestaurantDAO();

            return restaurantDAO.get(String.valueOf(id)).orElse(null);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error getting restaurant with id [" + id + "] from the DB", e);
        }

        return null;
    }

    public static List<Restaurant> getAll() {
        try {
            RestaurantDAO restaurantDAO = new RestaurantDAO();

            return restaurantDAO.getAll().orElse(new ArrayList<>());
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error getting all restaurants from the DB", e);
        }

        return new ArrayList<>();
    }

    public static List<Restaurant> search(JSONObject json) {

        Map<String, String> filters = new HashMap<>(
                Map.of(
                        "name", "",
                        "customer_rating", String.valueOf(Integer.MIN_VALUE),
                        "distance", String.valueOf(Integer.MAX_VALUE),
                        "price", String.valueOf(Integer.MAX_VALUE),
                        "cuisine", ""
                )
        );

        if (json.has("name") && !json.isNull("name")) {
            filters.put("name", json.getString("name"));
        }
        if (json.has("customer_rating")) {
            filters.put("customer_rating", json.getString("customer_rating"));
        }
        if (json.has("distance")) {
            filters.put("distance", json.getString("distance"));
        }
        if (json.has("price")) {
            filters.put("price", json.getString("price"));
        }
        if (json.has("cuisine") && !json.isNull("cuisine")) {
            filters.put("cuisine", json.getString("cuisine"));
        }

        try {
            RestaurantDAO restaurantDAO = new RestaurantDAO();

            return restaurantDAO.search(filters).orElse(new ArrayList<>());
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error searching for restaurant by name in the DB", e);
        }

        return new ArrayList<>();
    }
}
