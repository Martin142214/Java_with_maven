package com.example.demo.Services;

import com.example.demo.Models.Players.Player;
import com.example.demo.Models.Players.PlayerModel;
import com.example.demo.Models.Tournaments.Game;
import com.example.demo.Models.Tournaments.Tournament;
import com.example.demo.Models.Tournaments.TournamentModel;
import com.example.demo.Repos.IPlayerRepository;
import net.bytebuddy.matcher.StringMatcher;
import org.hibernate.id.GUIDGenerator;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.core.aggregation.StringOperators;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PlayerService {

    private IPlayerRepository playerRepository;

    @RegExp
    private final String RegexPattern = "[a-f0-9]{8}(?:-[a-f0-9]{4}){4}[a-f0-9]{8}";
    @Autowired
    public PlayerService(IPlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> GetAll(){
         List<Player> players = this.playerRepository.findAll();
         return players;
    }

    public ResponseEntity<List<Player>> GetApiAll(){
        List<Player> players = this.playerRepository.findAll();
        ResponseEntity<List<Player>> response = ResponseEntity.ok(players);
        return response;
    }

    public List<Player> GetByName(@NotNull String paramName){
        Stream<Player> streamOfPlayers = this.playerRepository.findAll().stream();
        List<Player> result = streamOfPlayers
                .filter(player -> player.getName().equalsIgnoreCase(paramName))
                .collect(Collectors.toList());
        streamOfPlayers.close();
        return result;
  }

    public ResponseEntity GetApiById(@NotNull String id){
        Pattern pattern = Pattern.compile(RegexPattern);
        Matcher matcher = pattern.matcher(id);
        if (matcher.find()) {
            Optional<Player> player = this.playerRepository.findById(id);
            if (player.isPresent()){
                return ResponseEntity.ok(player.get());
            }
            else{
                return ResponseEntity.ok("Player with id" + id.toString() + "is not found");
            }
        }
        else{
            return ResponseEntity.ok("The provided id in the url is not with a correct Regex pattern");
        }
    }

    public Player GetById(@NotNull String id){
        Pattern pattern = Pattern.compile(RegexPattern);
        Matcher matcher = pattern.matcher(id);
        if (matcher.find()) {
            Optional<Player> player = this.playerRepository.findById(id);
            if (player.isPresent()){
                return player.get();
            }
            else{
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player with id: " + id + " is not found");
            }
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The id of the player does not match the UUID pattern");
        }
    }

    public ResponseEntity<Player> createApi(@NotNull Player player){
        player.id = UUID.randomUUID().toString();
        return ResponseEntity.status(201).body(this.playerRepository.save(player));
    }

    public void DeleteApi(@NotNull String id){
        this.playerRepository.deleteById(id);
    }

    public void create(@NotNull PlayerModel playerModel){
        Player player = new Player();
        player.id = UUID.randomUUID().toString();
        player.setName(playerModel.getName());
        player.setEmail(playerModel.getEmail());
        player.imageName = playerModel.imageName;
        player.rankPoints = 0;
        player.ranking = getBiggestRanking() + 1;
        int i = 0;
        i +=1;
        this.playerRepository.insert(player);
    }

    public int getBiggestRanking(){
        var players = this.GetAll();
        int highest = 0;
        for (int i=0; i<players.size()-1; i++){
            highest = players.get(i).ranking;
            if (highest < players.get(i+1).ranking){
                highest = players.get(i+1).ranking;
            }
        }
        return highest;
    }

    public int calculateRanking(int points, Player player){
        var players = this.GetAll();
        List<Integer> ranklist = new ArrayList<>();
        for (Player p: players) {
            if (p.rankPoints + 1000 != player.rankPoints){
                ranklist.add(p.rankPoints);
            }
        }
        ranklist.sort(Comparator.reverseOrder());
        var position = 0;
        for (int i = 0; i < ranklist.size(); i++){
            if (points > ranklist.get(i)){
                position = i + 1;
                break;
            }
        }
        return position;
    }

    public List<Integer> generateTwoRandomNumbers(){
        Random random = new Random();
        int firstNum = random.nextInt(7);
        int secondNum = random.nextInt(7);

        return checkResult(firstNum, secondNum);
    }

    public List<Integer> checkResult(int firstNum, int secondNum){
        Random random = new Random();

        if (firstNum != 6){
            secondNum = 6;
        }
        else{
            secondNum = random.nextInt(7);
        }

        if (secondNum == firstNum-1 && firstNum == 6){
            firstNum++;
        }
        else if(secondNum == 6 && firstNum == secondNum -1){
            secondNum++;
        }

        List<Integer> result = new ArrayList<>();
        result.add(firstNum);
        result.add(secondNum);
        return result;
    }

    public Boolean isFirstTour(Player player, String tournamentName){
        boolean isFirstTour = false;
        if (player.games.isEmpty()){
            isFirstTour = true;
        }
        else{
            for (Game game: player.games) {
                if (game.tournamentName != tournamentName){
                    isFirstTour = true;
                }
            }
        }
        return isFirstTour;
    }

    public void incrementParticipations(Player p1, String tournamentName){
            for (Game game: p1.games) {
                if (game.tournamentName == tournamentName){
                    game.numberOfParticipations += 1;
                }
            }
    }

    //Check if the player have ever participated in the same tournament before
    public void checkPlayersForTakingPartInSuchTournament(Player p1, Player p2, String tournamentName){
        if (this.isFirstTour(p1, tournamentName)){  //isFirstTour does not return the legit check, PAY ATTENTION!
            p1.tournaments.add(tournamentName);
            p1.games.add(new Game(tournamentName, 1));
        }
        else{
            this.incrementParticipations(p1, tournamentName);
        }

        if (this.isFirstTour(p2, tournamentName)){
            p2.tournaments.add(tournamentName);
            p2.games.add(new Game(tournamentName, 1));
        }
        else{
            this.incrementParticipations(p1, tournamentName);
        }
    }


    public ResponseEntity<Player> Update(Player player){
        return ResponseEntity.status(204).body(this.playerRepository.save(player));
    }

    public void updateAll(@NotNull String id, @NotNull Player player){
        var entity = this.GetById(id.toString());
            entity.setName(player.getName());
            entity.setEmail(player.getEmail());
            entity.rankPoints = player.rankPoints;
            entity.ranking = player.ranking;
            entity.tournaments = player.tournaments;
            entity.games = player.games;
        this.playerRepository.save(entity);
    }

    public void edit(@NotNull String id, @NotNull Player player) {
        var entity = this.GetById(id.toString());
        entity.setName(player.getName());
        entity.setEmail(player.getEmail());
        this.playerRepository.save(entity);
    }

    public PlayerModel mapToModel(Player player, PlayerModel playerModel){
        if (playerModel == null){
            playerModel = new PlayerModel();
        }
        playerModel.name = player.name;
        playerModel.email = player.email;
        playerModel.imageName = player.imageName;
        return playerModel;
    }

    public Player mapToEntity(PlayerModel playerModel, Player player){
        if (player == null){
            player = new Player();
        }
        player.name = playerModel.name;
        player.email = playerModel.email;
        player.imageName = playerModel.imageName;
        return player;
    }

    public void Delete(@NotNull String id){
        this.playerRepository.deleteById(id);
    }

}
