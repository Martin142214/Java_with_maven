package com.example.demo.Controllers;

import com.example.demo.Models.Players.Player;
import com.example.demo.Services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/players")
public class PlayersApiController {

    private PlayerService playerService;
    @Autowired
    public PlayersApiController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public ResponseEntity<List<Player>> getPlayers(){
        return this.playerService.GetApiAll();
    }

//    @GetMapping("/{name}")
//    @ResponseBody
//    public List<Player> getPlayerByName(@PathVariable String name){
//        return this.playerService.GetByName(name);
//    }

    @GetMapping("/{id}")
    public ResponseEntity getPlayerById(@PathVariable UUID id){
        return this.playerService.GetApiById(id.toString());
    }

    @PostMapping
    public ResponseEntity<Player> createPlayer(@RequestBody Player player){
        return this.playerService.createApi(player);
    }

    @PostMapping("/delete/{id}")
    public void delete(@PathVariable UUID id){
        this.playerService.DeleteApi(id.toString());
    }
}
