package com.example.demo.Models.Tournaments;

public class TournamentModel {

    public String name;
    public String country;

    public int points;

    public String typeOfGround;

    public String imageName;

    public TournamentModel(String name, String country, int points, String typeOfGround, String imageName) {
        this.name = name;
        this.country = country;
        this.points = points;
        this.typeOfGround = typeOfGround;
        this.imageName = imageName;
    }
}
