package com.microservice.mongodb.repository;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.microservice.mongodb.model.User;

public interface UserRepository extends MongoRepository<User, UUID>{

}
