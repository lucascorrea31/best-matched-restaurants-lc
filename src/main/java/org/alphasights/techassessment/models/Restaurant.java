package org.alphasights.techassessment.models;

import org.alphasights.techassessment.util.Constants;
import org.json.JSONObject;

import javax.persistence.*;

@Entity
@Table(name = Constants.RESTAURANT_TABLE)
public class Restaurant {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Column(name = "customer_rating")
    private double customerRating;
    @Column(name = "distance")
    private double distance;
    @Column(name = "price")
    private double price;
    @ManyToOne
    @JoinColumn(name = "cuisine_id")
    private Cuisine cuisine;

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

    public Cuisine getCuisine() {
        return cuisine;
    }

    public void setCuisine(Cuisine cuisine) {
        this.cuisine = cuisine;
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("id", getId());
        json.put("name", getName());
        json.put("customerRating", getCustomerRating());
        json.put("distance", getDistance());
        json.put("price", getPrice());
        json.put("cuisine", new JSONObject(getCuisine()));
        return json.toString();
    }
}
