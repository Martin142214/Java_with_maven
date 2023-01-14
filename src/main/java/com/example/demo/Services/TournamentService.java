package com.example.demo.Services;

import com.example.demo.Models.Players.Player;
import com.example.demo.Models.Players.PlayerModel;
import com.example.demo.Models.Tournaments.Tournament;
import com.example.demo.Models.Tournaments.TournamentModel;
import com.example.demo.Repos.IPlayerRepository;
import com.example.demo.Repos.ITournamentRepository;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TournamentService {

    public ITournamentRepository tournamentRepository;

    @RegExp
    private final String RegexPattern = "[a-f0-9]{8}(?:-[a-f0-9]{4}){4}[a-f0-9]{8}";
    @Autowired
    public TournamentService(ITournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public List<Tournament> GetAll(){
        List<Tournament> tournaments = this.tournamentRepository.findAll();
        return tournaments;
    }

    public ResponseEntity<List<Tournament>> GetApiAll(){
        List<Tournament> tournaments = this.tournamentRepository.findAll();
        ResponseEntity<List<Tournament>> response = ResponseEntity.ok(tournaments);
        return response;
    }

    public List<Tournament> GetByName(@NotNull String paramName){
        Stream<Tournament> streamOfTournaments = this.tournamentRepository.findAll().stream();
        List<Tournament> result = streamOfTournaments
                .filter(tournament -> tournament.name.equalsIgnoreCase(paramName))
                .collect(Collectors.toList());
        streamOfTournaments.close();
        return result;
    }

    public ResponseEntity GetApiById(@NotNull String id){
        Pattern pattern = Pattern.compile(RegexPattern);
        Matcher matcher = pattern.matcher(id);
        if (matcher.find()) {
            Optional<Tournament> tournament = this.tournamentRepository.findById(id);
            if (tournament.isPresent()){
                return ResponseEntity.ok(tournament.get());
            }
            else{
                return ResponseEntity.ok("Tournament with id" + id.toString() + "is not found");
            }
        }
        else{
            return ResponseEntity.ok("The provided id in the url is not with a correct Regex pattern");
        }
    }

    public Tournament GetById(@NotNull String id){
        Pattern pattern = Pattern.compile(RegexPattern);
        Matcher matcher = pattern.matcher(id);
        if (matcher.find()) {
            Optional<Tournament> tournament = this.tournamentRepository.findById(id);
            if (tournament.isPresent()){
                return tournament.get();
            }
            else{
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tournament with id: " + id + " is not found");
            }
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The id of the tournament does not match the UUID pattern");
        }
    }

    public ResponseEntity<Tournament> createApi(@NotNull Tournament tournament){
        tournament.id = UUID.randomUUID().toString();
        return ResponseEntity.status(201).body(this.tournamentRepository.save(tournament));
    }

    public void create(@NotNull TournamentModel tournamentModel){
        Tournament tournament = new Tournament();
        tournament.id = UUID.randomUUID().toString();
        tournament.name = tournamentModel.name;
        tournament.country = tournamentModel.country;
        tournament.points = tournamentModel.points;
        tournament.typeOfGround = tournamentModel.typeOfGround;
        tournament.imageName = tournamentModel.imageName;
        this.tournamentRepository.insert(tournament);
    }

    public ResponseEntity<Tournament> UpdateApi(Tournament tournament){
        return ResponseEntity.status(204).body(this.tournamentRepository.save(tournament));
    }

    public void update(@NotNull String id, TournamentModel tournamentModel){
        Tournament tournament = this.GetById(id);
        this.mapToEntity(tournament, tournamentModel);
        this.tournamentRepository.save(tournament);
    }

    public Tournament mapToModel(TournamentModel tournamentModel, Tournament tournament){
        return tournament;
    }

    public Tournament mapToEntity(Tournament tournament, TournamentModel tournamentModel){
        if (tournament == null){
            tournament = new Tournament();
        }
        tournament.name = tournamentModel.name;
        tournament.country = tournamentModel.country;
        tournament.points = tournamentModel.points;
        tournament.typeOfGround = tournamentModel.typeOfGround;
        tournament.imageName = tournamentModel.imageName;
        return tournament;
    }

    public void Delete(@NotNull String id){
        this.tournamentRepository.deleteById(id);
    }
}
