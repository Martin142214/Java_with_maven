package com.example.demo.Controllers;

import com.example.demo.Models.Players.Player;
import com.example.demo.Models.Players.PlayerModel;
import com.example.demo.Models.Tournaments.Tournament;
import com.example.demo.Models.Tournaments.TournamentModel;
import com.example.demo.Services.PlayerService;
import com.example.demo.Services.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.UUID;

@Controller
@RequestMapping("/tournaments")
public class TournamentsController {

    private TournamentService tournamentService;

    //private boolean isGridView = false;

    private final String mainControllerUrl = "http://localhost:8080/tournaments";
    @Autowired
    public TournamentsController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    //@RequestMapping("/change-view")
    //public RedirectView changeView(){
        //this.isGridView = !this.isGridView;
        //return redirectView();
    //}

    @GetMapping
    public String getTournaments(Model model){
        model.addAttribute("tournaments", this.tournamentService.GetAll());
        //model.addAttribute("isGridView", this.isGridView);
        //System.out.println("New request with isGrid:");
        //System.out.println(isGridView);
        return "tournaments-list";
    }

    @GetMapping("/{id}")
    public String getTournamentById(@PathVariable UUID id, Model model){
        Tournament entity = this.tournamentService.GetById(id.toString());
        model.addAttribute("tournament", entity);
        return "Tournament/Home.html";
    }

    /*@GetMapping("/create")
    public String create(Model model){
        model.addAttribute("tournament", new Tournament());
        return "modal-container";
    }*/

    @PostMapping("/create")
    public RedirectView create(TournamentModel tournamentModel){
        this.tournamentService.create(tournamentModel);
        return redirectView();
    }

    @PostMapping("/edit/{id}")
    public RedirectView edit(@PathVariable UUID id, TournamentModel tournamentModel){
        this.tournamentService.update(id.toString(), tournamentModel);
        return redirectView();
    }

    @PostMapping("/delete/{id}")
    public RedirectView delete(@PathVariable UUID id){
        this.tournamentService.Delete(id.toString());
        return redirectView();
    }

    public RedirectView redirectView(){
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(mainControllerUrl);
        return redirectView;
    }
}
