package org.alphasights.techassessment.dao;

import org.alphasights.techassessment.db.DBConnection;
import org.alphasights.techassessment.dto.CuisineDTO;
import org.alphasights.techassessment.models.Cuisine;
import org.alphasights.techassessment.util.Constants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CuisineDAO implements Dao<Cuisine> {
    private static final Logger LOGGER = Logger.getLogger(CuisineDAO.class.getName());
    private final Connection connection;

    public CuisineDAO() {
        connection = DBConnection.getConnection();
    }

    @Override
    public Optional<Cuisine> get(String id) {
        try {
            String query = "SELECT * FROM " + Constants.CUISINE_TABLE + " WHERE id = ?";

            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(id));

            ResultSet result = stmt.executeQuery();

            return Optional.ofNullable(CuisineDTO.extractFromResultSet(result));
        } catch (Exception e) {
            LOGGER.log(Level.FINE, "Error getting cuisine with id [" + id + "]", e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<List<Cuisine>> getAll() {
        try {
            String query = "SELECT * FROM " + Constants.CUISINE_TABLE;

            ResultSet results = connection
                    .prepareStatement(query)
                    .executeQuery();

            return Optional.of(CuisineDTO.extractListFromResultSet(results));
        } catch (Exception e) {
            LOGGER.log(Level.FINE, "Error getting all cuisines", e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Cuisine> save(Cuisine cuisine) {
        try {
            String query = "INSERT INTO " + Constants.CUISINE_TABLE + " (id, name) VALUES (?, ?);";

            PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, cuisine.getId());
            stmt.setString(2, cuisine.getName());

            int numberOfInsertedRows = stmt.executeUpdate();

            Optional<Integer> generatedId = Optional.empty();

            if (numberOfInsertedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();

                if (generatedKeys.next()) {
                    generatedId = Optional.of(generatedKeys.getInt(1));
                    cuisine.setId(generatedId.get());
                }

                LOGGER.log(
                        Level.INFO,
                        "Cuisine created successfully with id {0}",
                        new Object[]{generatedId.orElse(-1)}
                );

                return Optional.of(cuisine);
            }
        } catch (Exception e) {
            LOGGER.log(Level.FINE, "Error saving cuisine", e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Cuisine> update(Cuisine cuisine, String[] params) {
        try {
            String query = "UPDATE " + Constants.CUISINE_TABLE + " SET name = ? WHERE id = ?;";

            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, params[0]);
            stmt.setInt(2, cuisine.getId());

            int numberOfUpdatedRows = stmt.executeUpdate();

            if (numberOfUpdatedRows > 0) {
                LOGGER.log(
                        Level.INFO,
                        "Cuisine {0} was updated successfully",
                        new Object[]{cuisine.getId()}
                );

                return Optional.of(cuisine);
            }
        } catch (Exception e) {
            LOGGER.log(Level.FINE, "Error updating cuisine with id [" + cuisine.getId() + "]", e);
        }

        return Optional.empty();
    }

    @Override
    public boolean delete(Cuisine cuisine) {
        try {
            String query = "DELETE FROM " + Constants.CUISINE_TABLE + " WHERE id = ?;";

            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, cuisine.getId());

            int numberOfDeletedRows = stmt.executeUpdate();

            if (numberOfDeletedRows > 0) {
                LOGGER.log(
                        Level.INFO,
                        "Cuisine deleted successfully with id {0}",
                        new Object[]{cuisine.getId()}
                );

                return true;
            }
        } catch (Exception e) {
            LOGGER.log(Level.FINE, "Error deleting cuisine with id [" + cuisine.getId() + "]", e);
        }

        return false;
    }
}
