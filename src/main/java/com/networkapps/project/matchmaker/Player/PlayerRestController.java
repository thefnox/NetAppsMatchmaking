/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.networkapps.project.matchmaker.Player;

import com.auth0.jwt.interfaces.Claim;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.networkapps.project.matchmaker.Auth.AuthUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

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

    @GetMapping("/me")
    public ResponseEntity<Player> getMe(@RequestHeader("Authorization") String auth) {
        Map<String, Claim> claims = AuthUtil.getInstance().verifyAndGetClaims(auth);
        if (claims != null) {
            String player_id = claims.get("player_id").asString();
            Player player  = playerRepository.findUserById(player_id);
            if (player != null) {
                return new ResponseEntity<>(playerRepository.findUserById(player_id), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    
    @GetMapping("/{player_id}")
    public Object get(@PathVariable String player_id) {
        Player player = this.playerRepository.findUserById(player_id);
        if (player != null) {
            Gson gson = createGson();
            return gson.toJson(player);
        }
        return ResponseEntity.notFound().build();
    }
    
    @RequestMapping(value = "/me", method = PUT, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Player> put(@RequestHeader("Authorization") String auth, @RequestBody PlayerDto input) {
        Map<String, Claim> claims = AuthUtil.getInstance().verifyAndGetClaims(auth);
        if (claims != null) {
            String player_id = claims.get("player_id").asString();
            Player current = this.playerRepository.findUserById(player_id);
            if(current != null) {
                current.setEmail(input.getEmail());
                current.setElo(input.getElo());
                current.setLosses(input.getLosses());
                current.setMatches(input.getMatches());
                current.setTournamentsPlayed(input.getTournamentsPlayed());
                current.setTournamentsWon(input.getTournamentsWon());
                current.setWins(input.getWins());
                return ResponseEntity.ok(this.playerRepository.save(current));
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
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
    
    @DeleteMapping("/me")
    public ResponseEntity<?> delete(@RequestHeader("Authorization") String auth) {
        Map<String, Claim> claims = AuthUtil.getInstance().verifyAndGetClaims(auth);
        if (claims != null) {
            String player_id = claims.get("player_id").asString();
            Player player = this.playerRepository.findUserById(player_id);
            if (player != null) {
                this.playerRepository.deleteById(player_id);
                if (this.playerRepository.findUserById(player_id) == null) {
                    return new ResponseEntity<>(HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    
    @GetMapping("/leaderboard")
    public String leaderboardList() {
        List<Player> list = this.playerRepository.getLeaderboard();
        if (!list.isEmpty()) {
            Gson gson = createGson();
            return gson.toJson(list);
        }
        return "";
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
