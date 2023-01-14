package com.example.demo.Repos;

import com.example.demo.Models.Games.Result;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IResultsRepository extends MongoRepository<Result, String> {
}
