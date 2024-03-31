package org.alphasights.techassessment.dto;

import org.alphasights.techassessment.bo.BOCuisine;
import org.alphasights.techassessment.bo.BORestaurant;

import java.util.Objects;
import java.util.UUID;

public class RestaurantDTO {
    private UUID id;
    private String name;
    private double customerRating;
    private double distance;
    private double price;
    private int cuisineId;
    private String cuisineName;

    public RestaurantDTO() {
    }

    public RestaurantDTO(UUID id, String name, double customerRating, double distance, double price) {
        setId(id);
        setName(name);
        setCustomerRating(customerRating);
        setDistance(distance);
        setPrice(price);
    }

    public RestaurantDTO(UUID id, String name, double customerRating, double distance, double price, int cuisineId, String cuisineName) {
        setId(id);
        setName(name);
        setCustomerRating(customerRating);
        setDistance(distance);
        setPrice(price);
        setCuisineId(cuisineId);
        setCuisineName(cuisineName);
    }

    public RestaurantDTO(UUID id, String name, double customerRating, double distance, double price, BOCuisine cuisine) {
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = Objects.requireNonNullElseGet(id, UUID::randomUUID);
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

        if (Double.compare(that.customerRating, customerRating) != 0) return false;
        if (Double.compare(that.distance, distance) != 0) return false;
        if (Double.compare(that.price, price) != 0) return false;
        if (cuisineId != that.cuisineId) return false;
        if (!id.equals(that.id)) return false;
        return name.equals(that.name);
    }

    public BORestaurant toBO() {
        BORestaurant restaurant = new BORestaurant();
        restaurant.setId(getId());
        restaurant.setName(getName());
        restaurant.setCustomerRating(getCustomerRating());
        restaurant.setDistance(getDistance());
        restaurant.setPrice(getPrice());

        if (this.cuisineName != null) {
            BOCuisine cuisine = new BOCuisine();
            cuisine.setId(getCuisineId());
            cuisine.setName(getCuisineName());
            restaurant.setCuisine(cuisine);
        }

        return restaurant;
    }

    public static RestaurantDTO fromBO(BORestaurant restaurant) {
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

    @Override
    public String toString() {
        return "RestaurantDTO{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", customerRating=" + getCustomerRating() +
                ", distance=" + getDistance() +
                ", price=" + getPrice() +
                ", cuisineId=" + getCuisineId() +
                ", cuisineName=" + getCuisineName() +
                '}';
    }
}
