package com.example.demo.Repos;

import com.example.demo.Models.Tournaments.Tournament;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITournamentRepository extends MongoRepository<Tournament, String> {
}
