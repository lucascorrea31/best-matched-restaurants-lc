package org.alphasights.techassessment.bo;

import org.alphasights.techassessment.dao.CuisineDAO;
import org.alphasights.techassessment.dao.RestaurantDAO;
import org.alphasights.techassessment.models.Cuisine;
import org.alphasights.techassessment.models.Restaurant;

import java.util.ArrayList;
import java.util.List;
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

    public static List<Restaurant> searchByName(String name) {
        try {
            RestaurantDAO restaurantDAO = new RestaurantDAO();

            return restaurantDAO.search("name", name.split(" ")).orElse(new ArrayList<>());
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error searching for restaurant by name in the DB", e);
        }

        return new ArrayList<>();
    }
}
