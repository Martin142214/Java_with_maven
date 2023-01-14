package com.example.demo.Repos;

import com.example.demo.Models.Players.Player;
import org.hibernate.id.GUIDGenerator;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IPlayerRepository extends MongoRepository<Player, String> {

}
