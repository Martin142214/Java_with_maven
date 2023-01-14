package com.example.demo.Models.Games;

import java.time.LocalDate;
import java.util.Date;

public class Result {

    public String id;

    public Rivals opponents;

    public String firstSetResult;

    public String secondSetResult;

    public String winner;

    public LocalDate dateOfClash;

    public Result(){ }

    public Result(String id, Rivals opponents, String firstSetResult, String secondSetResult, String winner, LocalDate dateOfClash) {
        this.id = id;
        this.opponents = opponents;
        this.firstSetResult = firstSetResult;
        this.secondSetResult = secondSetResult;
        this.winner = winner;
        this.dateOfClash = dateOfClash;
    }
}
