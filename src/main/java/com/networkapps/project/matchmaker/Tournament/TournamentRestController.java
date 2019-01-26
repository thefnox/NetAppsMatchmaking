/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.networkapps.project.matchmaker.Tournament;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.networkapps.project.matchmaker.Player.Player;
import java.util.List;
import java.util.Set;
import org.springframework.http.HttpStatus;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/**
 *
 * @author Martin
 */
@RestController
@RequestMapping("/tournaments")
public class TournamentRestController {
    private final TournamentRepository tournamentRepository;
    
    public TournamentRestController(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }
    
     @GetMapping()
    public String list() {
        List<Tournament> list = this.tournamentRepository.findAll();
        if (!list.isEmpty()) {
            Gson gson = createGson();
            return gson.toJson(list);

        }
        return "";
    }
    
    @GetMapping("/{id}")
    public Object get(@PathVariable Long id) {
        Tournament tournament = this.tournamentRepository.findTournamentById(id);
        if (tournament != null) {
            Gson gson = createGson();
            return gson.toJson(tournament);
        }
        return ResponseEntity.notFound().build();
    }
    
    @RequestMapping(value = "/{id}", method = PUT, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Tournament> put(@PathVariable Long id, @RequestBody TournamentDto input) {
        Tournament current = this.tournamentRepository.findTournamentById(id);
        if(current == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Set<Player> players = input.getPlayers();
        
        current.setName(input.getName());
        current.setPlayers(players);
        
        return ResponseEntity.ok(this.tournamentRepository.save(current));
    }
    
    @RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Tournament> post(@RequestBody TournamentDto input) {
        Tournament check;
        check = this.tournamentRepository.findTournamentById(input.getId());
        if(check != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        
        Set<Player> players = input.getPlayers();
        
        Tournament tournament=  new Tournament(input.getId(), input.getName(), players);
        return ResponseEntity.ok(this.tournamentRepository.save(tournament));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Tournament tournament = this.tournamentRepository.findTournamentById(id);
        if (tournament != null) {
            this.tournamentRepository.deleteById(id);
            if (this.tournamentRepository.findTournamentById(id) == null) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    
    private Gson createGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        return builder.create();
    } 
}
