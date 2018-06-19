package com.example.tavar.arrendaapp;

public class House {
    private int id;
    private String description;
    private String loc;
    private int idSeller;
    private int people;
    private int bedroom;
    private int Bathroom;
    private int weekPrice;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public int getIdSeller() {
        return idSeller;
    }

    public void setIdSeller(int idSeller) {
        this.idSeller = idSeller;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public int getBedroom() {
        return bedroom;
    }

    public void setBedroom(int bedroom) {
        this.bedroom = bedroom;
    }

    public int getBathroom() {
        return Bathroom;
    }

    public void setBathroom(int bathroom) {
        Bathroom = bathroom;
    }

    public int getWeekPrice() {return weekPrice;}

    public void setWeekPrice(int weekPrice) {this.weekPrice = weekPrice;}
}
