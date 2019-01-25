/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.networkapps.project.matchmaker;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.web.bind.annotation.ExceptionHandler;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author kamai
 */
@RestController
@RequestMapping(path = "/users", produces = APPLICATION_JSON_VALUE)
public class UserRestController {
    
    private final UserRepository userRepository;
    
    public UserRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @GetMapping()
    public List<User> list() {
        return this.userRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public Object get(@PathVariable String id) {
        User user;
        user = this.userRepository.findByName(id).get(0);
        if (user != null) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable String id, @RequestBody Object input) {
        return null;
    }
    
    @RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<User> post(@RequestBody UserDto input) {
        return ResponseEntity
                .ok(this.userRepository.save(new User(input.getId(), input.getEmail(), input.getPassword())));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        User user;
        user = this.userRepository.findByName(id).get(0);
        if (user != null) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error message")
    public void handleError() {
    }
    
}
