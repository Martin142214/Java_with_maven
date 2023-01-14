package com.example.demo.Models.Tournaments;

import com.example.demo.Models.Players.Player;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Document("Tournaments")
public class Tournament {

    @Id
    public String id;
    public String name;
    public String country;

    public int points;
    public String imageName;

    public String typeOfGround;

    public Tournament(){ }

    public Tournament(String id, String name, String country, int points, String typeOfGround, String imageName) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.points = points;
        this.typeOfGround = typeOfGround;
        this.imageName = imageName;
    }
}
