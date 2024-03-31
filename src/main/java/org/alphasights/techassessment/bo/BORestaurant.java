package org.alphasights.techassessment.bo;

import java.util.UUID;

public class BORestaurant {
    private UUID id;
    private String name;
    private double customerRating;
    private double distance;
    private double price;
    private BOCuisine cuisine;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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

    public BOCuisine getCuisine() {
        return cuisine;
    }

    public void setCuisine(BOCuisine cuisine) {
        this.cuisine = cuisine;
    }
}
