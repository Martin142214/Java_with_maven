package com.example.demo.Controllers;

import com.example.demo.Models.Games.Result;
import com.example.demo.Models.Players.Player;
import com.example.demo.Models.Games.Rivals;
import com.example.demo.Models.Tournaments.Game;
import com.example.demo.Models.Tournaments.Tournament;
import com.example.demo.Repos.IResultsRepository;
import com.example.demo.Services.PlayerService;
import com.example.demo.Services.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/play")
public class GameController {

    private PlayerService playerService;

    private TournamentService tournamentService;

    private IResultsRepository resultsRepository;

    @Autowired
    public GameController(PlayerService playerService, TournamentService tournamentService, IResultsRepository resultsRepository) {
        this.playerService = playerService;
        this.tournamentService = tournamentService;
        this.resultsRepository = resultsRepository;
    }

    @GetMapping
    public String game(Model model){
        model.addAttribute("players", this.playerService.GetAll());
        model.addAttribute("tournaments", this.tournamentService.GetAll());
        return "game";
    }


    @PostMapping
    public String play(Rivals data, Model model){
        Player p1 = this.playerService.GetByName(data.firstPlayerName).get(0);
        Player p2 = this.playerService.GetByName(data.secondPlayerName).get(0);
        Tournament tour = this.tournamentService.GetByName(data.tournamentName).get(0);
        Result result = new Result();
        result.id = UUID.randomUUID().toString();
        result.opponents = data;

        List<Integer> resultFromFirstSet = this.playerService.generateTwoRandomNumbers();
        List<Integer> resultFromSecondSet = this.playerService.generateTwoRandomNumbers();

        if (resultFromSecondSet.get(0) > resultFromSecondSet.get(1)){
            result.winner = p1.name;
            p1.rankPoints += tour.points;
            p1.ranking = this.playerService.calculateRanking(p1.rankPoints, p1);

            this.playerService.checkPlayersForTakingPartInSuchTournament(p1, p2, data.tournamentName);
        }
        else{
            result.winner = p2.name;
            p2.rankPoints += tour.points;
            p2.ranking = this.playerService.calculateRanking(p2.rankPoints, p2); //check calculation -> throws '0'

            this.playerService.checkPlayersForTakingPartInSuchTournament(p1, p2, data.tournamentName);
        }

        result.dateOfClash = LocalDate.now();
        result.firstSetResult = String.format("%2d :%2d", resultFromFirstSet.get(0), resultFromFirstSet.get(1));
        result.secondSetResult = String.format("%2d :%2d", resultFromSecondSet.get(0), resultFromSecondSet.get(1));

        var player = new Player();

        model.addAttribute("playerOne", p1);
        model.addAttribute("playerTwo", p2);
        model.addAttribute("tournamentName", data.tournamentName);
        model.addAttribute("tournamentPoints", tour.points);
        model.addAttribute("result", result);

        this.resultsRepository.insert(result);
        this.playerService.updateAll(p1.id, p1);
        this.playerService.updateAll(p2.id, p2);

        return "/game/results.html";
    }

    public RedirectView redirectView(String url){
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(url);
        return redirectView;
    }
}
