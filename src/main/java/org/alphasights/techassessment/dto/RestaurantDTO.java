package org.alphasights.techassessment.dto;

import org.alphasights.techassessment.models.Cuisine;
import org.alphasights.techassessment.models.Restaurant;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RestaurantDTO {
    private int id;
    private String name;
    private double customerRating;
    private double distance;
    private double price;
    private int cuisineId;
    private String cuisineName;

    public RestaurantDTO() {
    }

    public RestaurantDTO(int id, String name, double customerRating, double distance, double price) {
        setId(id);
        setName(name);
        setCustomerRating(customerRating);
        setDistance(distance);
        setPrice(price);
    }

    public RestaurantDTO(int id, String name, double customerRating, double distance, double price, int cuisineId, String cuisineName) {
        setId(id);
        setName(name);
        setCustomerRating(customerRating);
        setDistance(distance);
        setPrice(price);
        setCuisineId(cuisineId);
        setCuisineName(cuisineName);
    }

    public RestaurantDTO(int id, String name, double customerRating, double distance, double price, Cuisine cuisine) {
        setId(id);
        setName(name);
        setCustomerRating(customerRating);
        setDistance(distance);
        setPrice(price);
        if (cuisine != null) {
            setCuisineId(cuisine.getId());
            setCuisineName(cuisine.getName());
        }
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

    public double getCustomerRating() {
        return customerRating;
    }

    public void setCustomerRating(double customerRating) {
        this.customerRating = customerRating;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCuisineId() {
        return cuisineId;
    }

    public void setCuisineId(int cuisineId) {
        this.cuisineId = cuisineId;
    }

    public String getCuisineName() {
        return cuisineName;
    }

    public void setCuisineName(String cuisineName) {
        this.cuisineName = cuisineName;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RestaurantDTO that = (RestaurantDTO) o;

        if (id != that.id && id != 0) return false;
        if (Double.compare(that.customerRating, customerRating) != 0) return false;
        if (Double.compare(that.distance, distance) != 0) return false;
        if (Double.compare(that.price, price) != 0) return false;
        if (cuisineId != that.cuisineId) return false;
        return name.equals(that.name);
    }

    public Restaurant toModel() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(getId());
        restaurant.setName(getName());
        restaurant.setCustomerRating(getCustomerRating());
        restaurant.setDistance(getDistance());
        restaurant.setPrice(getPrice());

        if (this.cuisineName != null) {
            Cuisine cuisine = new Cuisine();
            cuisine.setId(getCuisineId());
            cuisine.setName(getCuisineName());
            restaurant.setCuisine(cuisine);
        }

        return restaurant;
    }

    public static RestaurantDTO fromModel(Restaurant restaurant) {
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        restaurantDTO.setId(restaurant.getId());
        restaurantDTO.setName(restaurant.getName());
        restaurantDTO.setCustomerRating(restaurant.getCustomerRating());
        restaurantDTO.setDistance(restaurant.getDistance());
        restaurantDTO.setPrice(restaurant.getPrice());

        if (restaurant.getCuisine() != null) {
            restaurantDTO.setCuisineId(restaurant.getCuisine().getId());
            restaurantDTO.setCuisineName(restaurant.getCuisine().getName());
        }

        return restaurantDTO;
    }

    public static List<Restaurant> extractListFromResultSet(ResultSet results) throws SQLException {
        List<Restaurant> restaurants = new ArrayList<>();

        while (results.next()) {
            CuisineDTO cuisineDTO = new CuisineDTO(results.getInt("cuisine_id"), results.getString("cuisine_name"));
            RestaurantDTO restaurantDTO = new RestaurantDTO(
                    results.getInt("id"),
                    results.getString("name"),
                    results.getDouble("customer_rating"),
                    results.getDouble("distance"),
                    results.getDouble("price"),
                    cuisineDTO.toModel()
            );

            restaurants.add(restaurantDTO.toModel());
        }

        return restaurants;
    }

    public static Restaurant extractFromResultSet(ResultSet result) throws SQLException {
        if (result.next()) {
            CuisineDTO cuisineDTO = new CuisineDTO(result.getInt("cuisine_id"), result.getString("cuisine_name"));
            RestaurantDTO restaurantDTO = new RestaurantDTO(
                    result.getInt("id"),
                    result.getString("name"),
                    result.getDouble("customer_rating"),
                    result.getDouble("distance"),
                    result.getDouble("price"),
                    cuisineDTO.toModel()
            );

            return restaurantDTO.toModel();
        }

        return null;
    }
}
