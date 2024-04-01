package org.alphasights.techassessment.dto;

import org.alphasights.techassessment.bo.BOCuisine;
import org.alphasights.techassessment.models.Cuisine;
import org.alphasights.techassessment.models.Restaurant;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class CuisineDTO {
    private int id;
    private String name;

    public CuisineDTO() {
    }

    public CuisineDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CuisineDTO that = (CuisineDTO) o;

        if (id != that.id) return false;
        return Objects.equals(name, that.name);
    }

    public Cuisine toModel() {
        Cuisine cuisine = new Cuisine();
        cuisine.setId(this.id);
        cuisine.setName(this.name);
        return cuisine;
    }

    public static CuisineDTO fromModel(Cuisine cuisine) {
        CuisineDTO cuisineDTO = new CuisineDTO();
        cuisineDTO.setId(cuisine.getId());
        cuisineDTO.setName(cuisine.getName());
        return cuisineDTO;
    }

    public static List<Cuisine> extractListFromResultSet(ResultSet results) throws SQLException {
        List<Cuisine> cuisines = new ArrayList<>();

        while (results.next()) {
            CuisineDTO cuisineDTO = new CuisineDTO(results.getInt("id"), results.getString("name"));

            cuisines.add(cuisineDTO.toModel());
        }

        return cuisines;
    }

    public static Cuisine extractFromResultSet(ResultSet result) throws SQLException {
        if (result.next()) {
            CuisineDTO cuisineDTO = new CuisineDTO(result.getInt("id"), result.getString("name"));

            return cuisineDTO.toModel();
        }

        return null;
    }
}
