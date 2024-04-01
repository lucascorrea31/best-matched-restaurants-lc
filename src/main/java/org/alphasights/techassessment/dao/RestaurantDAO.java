package org.alphasights.techassessment.dao;

import org.alphasights.techassessment.db.DBConnection;
import org.alphasights.techassessment.dto.RestaurantDTO;
import org.alphasights.techassessment.models.Restaurant;
import org.alphasights.techassessment.util.Constants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RestaurantDAO implements Dao<Restaurant> {
    private static final Logger LOGGER = Logger.getLogger(RestaurantDAO.class.getName());
    private final Connection connection;

    public RestaurantDAO() {
        connection = DBConnection.getConnection();
    }

    public Optional<List<Restaurant>> search(String column, String[] needle) {
        return search(column, String.join("%", needle));
    }

    public Optional<List<Restaurant>> search(String column, String needle) {
        try {
            String query = "SELECT r.*, c.name as cuisine_name FROM " + Constants.RESTAURANT_TABLE + " r LEFT JOIN " + Constants.CUISINE_TABLE + " c ON r.cuisine_id = c.id WHERE ? LIKE ?;";

            PreparedStatement stmt = connection.prepareStatement(query, new String[]{column, "%" + needle + "%"});
            stmt.setString(1, "r." + column);
            ResultSet results = stmt.executeQuery();

            return Optional.of(RestaurantDTO.extractListFromResultSet(results));
        } catch (Exception e) {
            LOGGER.log(Level.FINE, "Error searching restaurant with /" + needle + "/", e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Restaurant> get(String id) {
        try {
            String query = "SELECT r.*, c.name as cuisine_name FROM " + Constants.RESTAURANT_TABLE + " r LEFT JOIN " + Constants.CUISINE_TABLE + " c ON r.cuisine_id = c.id WHERE r.id = ?;";

            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(id));

            ResultSet result = stmt.executeQuery();

            return Optional.ofNullable(RestaurantDTO.extractFromResultSet(result));
        } catch (Exception e) {
            LOGGER.log(Level.FINE, "Error getting restaurant with id [" + id + "]", e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<List<Restaurant>> getAll() {
        try {
            String query = "SELECT r.*, c.name as cuisine_name FROM " + Constants.RESTAURANT_TABLE + " r LEFT JOIN " + Constants.CUISINE_TABLE + " c ON r.cuisine_id = c.id;";

            ResultSet results = connection
                    .prepareStatement(query)
                    .executeQuery();

            return Optional.of(RestaurantDTO.extractListFromResultSet(results));
        } catch (Exception e) {
            LOGGER.log(Level.FINE, "Error getting all restaurants", e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Restaurant> save(Restaurant restaurant) {
        try {
            String query = "INSERT INTO " + Constants.RESTAURANT_TABLE + " (id, name, customer_rating, distance, price, cuisine_id) VALUES (?, ?, ?, ?, ?, ?);";

            PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, restaurant.getId());
            stmt.setString(2, restaurant.getName());
            stmt.setDouble(3, restaurant.getCustomerRating());
            stmt.setDouble(4, restaurant.getDistance());
            stmt.setDouble(5, restaurant.getPrice());
            stmt.setInt(6, restaurant.getCuisine().getId());

            int numberOfInsertedRows = stmt.executeUpdate();

            Optional<Integer> generatedId = Optional.empty();

            if (numberOfInsertedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();

                if (generatedKeys.next()) {
                    generatedId = Optional.of(generatedKeys.getInt(1));
                    restaurant.setId(generatedId.get());
                }

                LOGGER.log(
                        Level.INFO,
                        "Restaurant created successfully with id {0}",
                        new Object[]{generatedId.orElse(0)}
                );

                return Optional.of(restaurant);
            }
        } catch (Exception e) {
            LOGGER.log(Level.FINE, "Error saving restaurant", e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Restaurant> update(Restaurant restaurant, String[] params) {
        try {
            String query = "UPDATE " + Constants.RESTAURANT_TABLE + " SET name = ?, customer_rating = ?, distance = ?, price = ?, cuisine_id = ? WHERE id = ?;";

            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, params[0]);
            stmt.setDouble(2, Double.parseDouble(params[1]));
            stmt.setDouble(3, Double.parseDouble(params[2]));
            stmt.setDouble(4, Double.parseDouble(params[3]));
            stmt.setInt(5, Integer.parseInt(params[4]));
            stmt.setInt(6, restaurant.getId());

            int numberOfUpdatedRows = stmt.executeUpdate();

            if (numberOfUpdatedRows > 0) {
                LOGGER.log(
                        Level.INFO,
                        "Restaurant {0} was updated successfully",
                        new Object[]{restaurant.getId()}
                );

                return Optional.of(restaurant);
            }
        } catch (Exception e) {
            LOGGER.log(Level.FINE, "Error updating restaurant with id [" + restaurant.getId() + "]", e);
        }

        return Optional.empty();
    }

    @Override
    public boolean delete(Restaurant restaurant) {
        try {
            String query = "DELETE FROM " + Constants.RESTAURANT_TABLE + " WHERE id = ?;";

            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, restaurant.getId());

            int numberOfDeletedRows = stmt.executeUpdate();

            if (numberOfDeletedRows > 0) {
                LOGGER.log(
                        Level.INFO,
                        "Restaurant deleted successfully with id {0}",
                        new Object[]{restaurant.getId()}
                );

                return true;
            }
        } catch (Exception e) {
            LOGGER.log(Level.FINE, "Error deleting restaurant with id [" + restaurant.getId() + "]", e);
        }

        return false;
    }
}
