package org.alphasights.techassessment.bo;

import org.alphasights.techassessment.dao.CuisineDAO;
import org.alphasights.techassessment.models.Cuisine;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BOCuisine {
    private static final Logger LOGGER = Logger.getLogger(BOCuisine.class.getName());

    public static Cuisine get(int id) {
        try {
            CuisineDAO cuisineDAO = new CuisineDAO();

            return cuisineDAO.get(String.valueOf(id)).orElse(null);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error getting cuisine with id [" + id + "] from the DB", e);
        }

        return null;
    }

    public static List<Cuisine> getAll() {
        try {
            CuisineDAO cuisineDAO = new CuisineDAO();

            return cuisineDAO.getAll().orElse(new ArrayList<>());
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error getting all cuisines from the DB", e);
        }

        return new ArrayList<>();
    }
}
