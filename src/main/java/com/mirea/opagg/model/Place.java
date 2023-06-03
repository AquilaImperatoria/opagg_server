package com.mirea.opagg.model;

import lombok.Builder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "places")
public class Place implements Serializable {
    @Id
    @GeneratedValue(generator = "place_generator")
    @SequenceGenerator(
            name = "place_generator",
            sequenceName = "place_sequence",
            initialValue = 1
    )
    private Long id;

    @Column(columnDefinition = "text")
    private String name;

    @Column(columnDefinition = "text")
    private String priceandtype;

    @Column(columnDefinition = "text")
    private String rating;

    @Column(columnDefinition = "text")
    private String adress;

    @Column(columnDefinition = "text")
    private String point;

    @Column(columnDefinition = "text")
    private String contacts;

    @Column(columnDefinition = "text")
    private String tags;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Place(){};

    public Place(String name, String priceandtype, String rating, String adress, String point, String contacts, String tags) {
        this.name = name;
        this.priceandtype = priceandtype;
        this.rating = rating;
        this.adress = adress;
        this.point = point;
        this.contacts = contacts;
        this.tags = tags;
    }

    public String getPriceandtype() {
        return priceandtype;
    }

    public void setPriceandtype(String priceandtype) {
        this.priceandtype = priceandtype;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
