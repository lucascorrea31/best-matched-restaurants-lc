package org.alphasights.techassessment.models;

import org.alphasights.techassessment.util.Constants;
import org.json.JSONObject;

import javax.persistence.*;

@Entity
@Table(name = Constants.CUISINE_TABLE)
public class Cuisine {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    @Column(name = "name")
    private String name;

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

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("id", getId());
        json.put("name", getName());
        return json.toString();
    }
}
