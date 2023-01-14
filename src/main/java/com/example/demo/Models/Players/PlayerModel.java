package com.example.demo.Models.Players;

import com.example.demo.Models.Tournaments.Tournament;

import java.util.ArrayList;
import java.util.List;

public class PlayerModel {

    public String name;
    public String email;
    public String imageName;

    public int rankPoints;

    public PlayerModel() { }

    public PlayerModel(String name, String email,String imageName) {
        this.name = name;
        this.email = email;
        this.imageName = imageName;
    }

    public PlayerModel(int rankingPoints) {
        this.rankPoints = rankingPoints;
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
