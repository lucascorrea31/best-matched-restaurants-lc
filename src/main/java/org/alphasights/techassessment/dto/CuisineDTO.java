package org.alphasights.techassessment.dto;

import org.alphasights.techassessment.bo.BOCuisine;

import java.util.Objects;

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

    public BOCuisine toBO() {
        BOCuisine cuisine = new BOCuisine();
        cuisine.setId(this.id);
        cuisine.setName(this.name);
        return cuisine;
    }

    public static CuisineDTO fromBO(BOCuisine cuisine) {
        CuisineDTO cuisineDTO = new CuisineDTO();
        cuisineDTO.setId(cuisine.getId());
        cuisineDTO.setName(cuisine.getName());
        return cuisineDTO;
    }

    @Override
    public String toString() {
        return "CuisineDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
