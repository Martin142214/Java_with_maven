package com.example.demo.Models.Players;

import com.example.demo.Models.Tournaments.Game;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Document("Players")
public class Player implements Serializable {
    @Id
    public String id;
    public String name;
    public String email;
    public int ranking;
    public int rankPoints;
    public String imageName;

    public List<String> tournaments;

    public List<Game> games;

    public Player() {
        tournaments = new ArrayList<>();
        games = new ArrayList<>();
    }

    public Player(String name, String email, int ranking, int rankPoints, String imageName, List<String> tournaments, List<Game> games) {
        this.name = name;
        this.email = email;
        this.ranking = ranking;
        this.rankPoints = rankPoints;
        this.imageName = imageName;
        this.tournaments = tournaments;
        this.games = games;
    }

    public Player(String name, String email, int ranking, int rankPoints, String imageName) {
        this.name = name;
        this.email = email;
        this.ranking = ranking;
        this.rankPoints = rankPoints;
        this.imageName = imageName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
