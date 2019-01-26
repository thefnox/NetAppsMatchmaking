/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.networkapps.project.matchmaker.Player;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.web.bind.annotation.ExceptionHandler;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author kamai
 */
@RestController
@RequestMapping(path = "/players", produces = APPLICATION_JSON_VALUE)
public class PlayerRestController {
    
    private final PlayerRepository playerRepository;
    
    public PlayerRestController(PlayerRepository userRepository) {
        this.playerRepository = userRepository;
    }
    
    @GetMapping()
    public String list() {
        List<Player> list = this.playerRepository.findAll();
        if (!list.isEmpty()) {
            Gson gson = createGson();
            return gson.toJson(list);

        }
        return "";
    }
    
    @GetMapping("/{id}")
    public Object get(@PathVariable String id) {
        Player player = this.playerRepository.findUserById(id);
        if (player != null) {
            Gson gson = createGson();
            return gson.toJson(player);
        }
        return ResponseEntity.notFound().build();
    }
    
    @RequestMapping(value = "/{id}", method = PUT, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Player> put(@PathVariable String id, @RequestBody PlayerDto input) {
        Player current = this.playerRepository.findUserById(id);
        if(current == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        current.setEmail(input.getEmail());
        current.setElo(input.getElo());
        current.setLosses(input.getLosses());
        current.setMatches(input.getMatches());
        current.setTournamentsPlayed(input.getTournamentsPlayed());
        current.setTournamentsWon(input.getTournamentsWon());
        current.setWins(input.getWins());
        
        return ResponseEntity.ok(this.playerRepository.save(current));
    }
    
    @RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Player> post(@RequestBody PlayerDto input) {
        Player check = this.playerRepository.findUserById(input.getId());
        if(check != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Player player = new Player(input.getId(), input.getEmail(), input.getPassword());
        player.setElo(1000);
        player.setWins(0);
        player.setLosses(0);
        player.setTournamentsPlayed(0);
        player.setTournamentsWon(0);
        return ResponseEntity.ok(this.playerRepository.save(player));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        Player player = this.playerRepository.findUserById(id);
        if (player != null) {
            this.playerRepository.deleteById(id);
            if (this.playerRepository.findUserById(id) == null) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return ResponseEntity.notFound().build();
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error message")
    public void handleError() {
    }
    
    private Gson createGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        return builder.create();
    } 
}
