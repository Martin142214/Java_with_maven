package com.example.demo.Models.Games;

import java.util.UUID;

public class Rivals {

    public String firstPlayerName;

    public String secondPlayerName;

    public String tournamentName;

    public Rivals(String firstPlayerName, String secondPlayerName, String tournamentName){
        this.firstPlayerName = firstPlayerName;
        this.secondPlayerName = secondPlayerName;
        this.tournamentName = tournamentName;
    }
}
