package com.example.demo.Controllers;

import com.example.demo.Models.Players.Player;
import com.example.demo.Models.Players.PlayerModel;
import com.example.demo.Services.PlayerService;
import com.fasterxml.jackson.annotation.JsonView;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/players")
public class PlayersController {

    private PlayerService playerService;

    private boolean isGridView = false;

    private final String mainControllerUrl = "http://localhost:8080/players";
    @Autowired
    public PlayersController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @RequestMapping("/change-view")
    public RedirectView changeView(){
        this.isGridView = !this.isGridView;
        return redirectView();
    }

    @GetMapping
    public String getPlayers(Model model){
        var players = calculateRankList();
        model.addAttribute("players", players);
        model.addAttribute("isGridView", this.isGridView);
        System.out.println("New request with isGrid:");
        System.out.println(isGridView);
        return "players-list";
    }

    public List<Player> calculateRankList(){
        List<Integer> ranklist = new ArrayList<>();
        var players = this.playerService.GetAll();

        for (Player player:
             players) {
            ranklist.add(player.rankPoints);
        }

        ranklist.sort(Comparator.reverseOrder());

        List<Player> orderedPlayers = new ArrayList<>();
        for (int i = 0; i < ranklist.size(); i++) {

            for (Player p: players) {
                if (p.rankPoints == ranklist.get(i)){
                    orderedPlayers.add(p);
                    orderedPlayers.get(i).ranking = i + 1;
                    this.playerService.updateAll(p.id, p);
                }
            }
        }
        return  orderedPlayers;
    }
    //for (int j = 0; j < players.size(); j++){
    //if (players.get(i).rankPoints == ranklist.get(j)){
    //orderedPlayers.add(players.get(i));
    //}

    //}

    @GetMapping("/{id}")
    public String getPlayerById(@PathVariable UUID id, Model model){
        Player entity = this.playerService.GetById(id.toString());
        model.addAttribute("player", entity);
        return "Player/Home.html";
    }

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("player", new Player());
        return "modal-container";
    }

    @PostMapping("/create")
    public RedirectView create(PlayerModel playerModel){
        this.playerService.create(playerModel);
        return redirectView();
    }

    @PostMapping("/edit/all/{id}")
    public RedirectView editAll(@PathVariable UUID id, Player player){
        this.playerService.updateAll(id.toString(), player);
        return redirectView();
    }

    @PostMapping("/edit/{id}")
    public RedirectView edit(@PathVariable UUID id, Player player){
        this.playerService.edit(id.toString(), player);
        return redirectView();
    }

    @PostMapping("/delete/{id}")
    public RedirectView delete(@PathVariable UUID id){
        this.playerService.Delete(id.toString());
        return redirectView();
    }

    public RedirectView redirectView(){
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(mainControllerUrl);
        return redirectView;
    }


//    @GetMapping
//    public ModelAndView getPlayerByName(){
//        ResponseEntity responseEntity = this.playerService.GetAll();
//        ModelAndView viewModel = new ModelAndView();
//       viewModel.setViewName("Person");
//        viewModel.addObject("Players", responseEntity.getBody());
//        return viewModel;
//    }

    //protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//        String name = req.getParameter("name");
//        int userId = -1;
//        for (String nameParam : people) {
//            if (nameParam != name){
//                resp.sendError(400, "Name is invalid");
//            }
//            else{
//                userId = people.indexOf(nameParam);
//            }
//        }
//        if (userId < 0){
//            resp.sendError(401);
//        }
//        resp.sendRedirect("http://localhost:8080/Person?id="+userId);
//    }

//    @GetMapping()
//    public ResponseEntity<List<Player>> GetAllPlayers(){
//        this.playerService.GetAll();
//
//    }

}
