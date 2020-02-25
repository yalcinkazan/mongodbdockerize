package com.microservice.mongodb.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.mongodb.exception.ResourceNotFoundException;
import com.microservice.mongodb.model.User;
import com.microservice.mongodb.repository.UserRepository;

@RestController
public class UserController {
	
	@Autowired
	private UserRepository userRepository;

	@RequestMapping(path = "/", method = RequestMethod.GET)
	public String index() {
		return "Hey MongoDB!";
	}
	 
	@RequestMapping(path = "/user", method = RequestMethod.GET)
    public ResponseEntity<Iterable<User>> getAllUser() {
    	Iterable<User> users = this.userRepository.findAll();
        return new ResponseEntity<Iterable<User>>(users, HttpStatus.OK);
    }
    
    @RequestMapping(path = "/user/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable(value = "id") UUID userId) { 
        return this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }
    
    @RequestMapping(path = "/user", method = RequestMethod.POST)
	public ResponseEntity<User> saveUser(@RequestBody User user) {
		user.setId(UUID.randomUUID());
		User persistedObject = this.userRepository.save(user);
		return new ResponseEntity<User>(persistedObject, HttpStatus.OK);
	}
    
    @RequestMapping(path = "/user/{id}", method = RequestMethod.PUT)
    public User updateUser(@PathVariable(value = "id") UUID userId, @Valid @RequestBody User user) {
        User existUser = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        existUser.setFirstName(user.getFirstName());
        existUser.setLastName(user.getLastName());
        User updatedUser = this.userRepository.save(existUser);
        return updatedUser;
    }

    @DeleteMapping(path = "/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") UUID userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        this.userRepository.delete(user);
        return ResponseEntity.ok().build();
    }
	
}
