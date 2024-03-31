package org.alphasights.techassessment.dao;

import org.alphasights.techassessment.bo.BORestaurant;
import org.alphasights.techassessment.db.DBConnection;
import org.alphasights.techassessment.dto.CuisineDTO;
import org.alphasights.techassessment.dto.RestaurantDTO;
import org.alphasights.techassessment.util.Constants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RestaurantDAO implements Dao<BORestaurant> {
    private static final Logger LOGGER =
            Logger.getLogger(RestaurantDAO.class.getName());
    private final Connection connection;

    public RestaurantDAO() {
        connection = DBConnection.getConnection();
    }

    @Override
    public Optional<BORestaurant> get(String id) {
        try {
            String query = "SELECT * FROM " + Constants.RESTAURANT_TABLE + " r WHERE id = ? LEFT JOIN " + Constants.CUISINE_TABLE + " c ON r.cuisine_id = c.id;";

            ResultSet result = connection
                    .prepareStatement(query, new String[]{id})
                    .executeQuery();

            if (result.first()) {
                CuisineDTO cuisineDTO = new CuisineDTO(result.getInt("c.id"), result.getString("c.name"));
                RestaurantDTO restaurantDTO = new RestaurantDTO(
                        UUID.fromString(result.getString("r.id")),
                        result.getString("r.name"),
                        result.getDouble("r.customer_rating"),
                        result.getDouble("r.distance"),
                        result.getDouble("r.price"),
                        cuisineDTO.toBO()
                );

                return Optional.of(restaurantDTO.toBO());
            }
        } catch (Exception e) {
            LOGGER.log(Level.FINE, "Error getting restaurant with id [" + id + "]", e);
        }

        return Optional.empty();
    }

    @Override
    public List<BORestaurant> getAll() {
        try {
            String query = "SELECT * FROM " + Constants.RESTAURANT_TABLE + " r LEFT JOIN " + Constants.CUISINE_TABLE + " c ON r.cuisine_id = c.id;";

            ResultSet results = connection
                    .prepareStatement(query)
                    .executeQuery();

            List<BORestaurant> restaurants = new ArrayList<>();

            while (results.next()) {
                CuisineDTO cuisineDTO = new CuisineDTO(results.getInt("c.id"), results.getString("c.name"));
                RestaurantDTO restaurantDTO = new RestaurantDTO(
                        UUID.fromString(results.getString("r.id")),
                        results.getString("r.name"),
                        results.getDouble("r.customer_rating"),
                        results.getDouble("r.distance"),
                        results.getDouble("r.price"),
                        cuisineDTO.toBO()
                );

                restaurants.add(restaurantDTO.toBO());
            }

            return restaurants;
        } catch (Exception e) {
            LOGGER.log(Level.FINE, "Error getting all restaurants", e);
        }

        return new ArrayList<>();
    }

    @Override
    public Optional<BORestaurant> save(BORestaurant restaurant) {
        try {
            String query = "INSERT INTO " + Constants.RESTAURANT_TABLE + " (id, name, customer_rating, distance, price, cuisine_id) VALUES (?, ?, ?, ?, ?, ?);";

            PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, restaurant.getId().toString());
            stmt.setString(2, restaurant.getName());
            stmt.setDouble(3, restaurant.getCustomerRating());
            stmt.setDouble(4, restaurant.getDistance());
            stmt.setDouble(5, restaurant.getPrice());
            stmt.setInt(6, restaurant.getCuisine().getId());

            int numberOfInsertedRows = stmt.executeUpdate();

            Optional<String> generatedId = Optional.empty();

            if (numberOfInsertedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();

                if (generatedKeys.next()) {
                    generatedId = Optional.of(generatedKeys.getString(1));
                    restaurant.setId(UUID.fromString(generatedId.get()));
                }

                LOGGER.log(
                        Level.INFO,
                        "Restaurant created successfully with id {0}",
                        new Object[]{generatedId.orElse("null")}
                );

                return Optional.of(restaurant);
            }
        } catch (Exception e) {
            LOGGER.log(Level.FINE, "Error saving restaurant", e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<BORestaurant> update(BORestaurant restaurant, String[] params) {
        try {
            String query = "UPDATE " + Constants.RESTAURANT_TABLE + " SET name = ?, customer_rating = ?, distance = ?, price = ?, cuisine_id = ? WHERE id = ?;";

            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, params[0]);
            stmt.setDouble(2, Double.parseDouble(params[1]));
            stmt.setDouble(3, Double.parseDouble(params[2]));
            stmt.setDouble(4, Double.parseDouble(params[3]));
            stmt.setInt(5, Integer.parseInt(params[4]));
            stmt.setString(6, restaurant.getId().toString());

            int numberOfUpdatedRows = stmt.executeUpdate();

            if (numberOfUpdatedRows > 0) {
                LOGGER.log(
                        Level.INFO,
                        "Restaurant {0} was updated successfully",
                        new Object[]{restaurant.getId().toString()}
                );

                return Optional.of(restaurant);
            }
        } catch (Exception e) {
            LOGGER.log(Level.FINE, "Error updating restaurant with id [" + restaurant.getId().toString() + "]", e);
        }

        return Optional.empty();
    }

    @Override
    public boolean delete(BORestaurant restaurant) {
        try {
            String query = "DELETE FROM " + Constants.RESTAURANT_TABLE + " WHERE id = ?;";

            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, restaurant.getId().toString());

            int numberOfDeletedRows = stmt.executeUpdate();

            if (numberOfDeletedRows > 0) {
                LOGGER.log(
                        Level.INFO,
                        "Restaurant deleted successfully with id {0}",
                        new Object[]{restaurant.getId().toString()}
                );

                return true;
            }
        } catch (Exception e) {
            LOGGER.log(Level.FINE, "Error deleting restaurant with id [" + restaurant.getId().toString() + "]", e);
        }

        return false;
    }
}
