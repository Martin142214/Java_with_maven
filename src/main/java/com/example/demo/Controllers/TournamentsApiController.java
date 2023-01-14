package com.example.demo.Controllers;

import com.example.demo.Models.Players.Player;
import com.example.demo.Models.Tournaments.Tournament;
import com.example.demo.Services.PlayerService;
import com.example.demo.Services.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tournaments")
public class TournamentsApiController {

        private TournamentService tournamentService;
        @Autowired
        public TournamentsApiController(TournamentService tournamentService) {
            this.tournamentService = tournamentService;
        }

        @GetMapping
        public ResponseEntity<List<Tournament>> getTournaments(){
            return this.tournamentService.GetApiAll();
        }

//    @GetMapping("/{name}")
//    @ResponseBody
//    public List<Player> getPlayerByName(@PathVariable String name){
//        return this.playerService.GetByName(name);
//    }

        @GetMapping("/{id}")
        public ResponseEntity getTournamentById(@PathVariable UUID id){
            return this.tournamentService.GetApiById(id.toString());
        }

        @PostMapping
        public ResponseEntity<Tournament> createTournament(@RequestBody Tournament tournament){
            return this.tournamentService.createApi(tournament);
        }
}
