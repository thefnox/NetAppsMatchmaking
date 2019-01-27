/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.networkapps.project.matchmaker.Tournament;

import com.auth0.jwt.interfaces.Claim;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.networkapps.project.matchmaker.Auth.AuthUtil;
import com.networkapps.project.matchmaker.Player.Player;

import java.util.*;

import com.networkapps.project.matchmaker.Player.PlayerDto;
import com.networkapps.project.matchmaker.Player.PlayerRepository;
import org.springframework.http.HttpStatus;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private final PlayerRepository playerRepository;

    public TournamentRestController(TournamentRepository tournamentRepository, PlayerRepository playerRepository) {
        this.tournamentRepository = tournamentRepository;
        this.playerRepository = playerRepository;
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

    @GetMapping("/{id}/players")
    public ResponseEntity<?> getAllPlayers(@PathVariable Long id) {
        Tournament tournament = this.tournamentRepository.findTournamentById(id);
        Set<Player> players = tournament.getPlayers();
        if(players.isEmpty()) {
            return new ResponseEntity<>("No players in tournament", HttpStatus.NOT_FOUND);
        } else {
            List<Player> playerList = new ArrayList<Player>(players);
            Collections.shuffle(playerList);
            return new ResponseEntity<>(playerList, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/{id}/start")
    public ResponseEntity<?> startTournament(@PathVariable Long id) {
        Tournament tournament = this.tournamentRepository.findTournamentById(id);
        Set<Player> players = tournament.getPlayers();
        List<Player> playerList = new ArrayList<Player>(players);
        Collections.shuffle(playerList);

        //Not Happening
        return new ResponseEntity<>("Tournament started!", HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/join", method = PUT, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> joinTournament(@PathVariable Long id,
                                            @RequestBody PlayerDto input,
                                            @RequestHeader("Authorization") String auth) {
        Map<String, Claim> claims = AuthUtil.getInstance().verifyAndGetClaims(auth);
        if (claims != null) {
            Tournament tournament = this.tournamentRepository.findTournamentById(id);
            Set<Player> players = tournament.getPlayers();

            if(tournament.getMaxPlayers()==players.size()) {
                return new ResponseEntity<>("Tournament is full.", HttpStatus.CONFLICT);
            }

            for(Player p : players) {
                if (p.getId().equals(input.getId())) {
                    return new ResponseEntity<>("Player already registered in tournament", HttpStatus.CONFLICT);
                }
            }

            Player player = this.playerRepository.findUserById(input.getId());
            players.add(player);
            tournament.setPlayers(players);

            return new ResponseEntity<>(this.tournamentRepository.save(tournament), HttpStatus.OK);

        } else {
            return new ResponseEntity<>("Invalid authentication.", HttpStatus.FORBIDDEN);
        }
    }
    
    @RequestMapping(value = "/{id}", method = PUT, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> put(@PathVariable Long id, @RequestBody TournamentDto input,
                                 @RequestHeader("Authorization") String auth) {
        Map<String, Claim> claims = AuthUtil.getInstance().verifyAndGetClaims(auth);
        if (claims != null) {
            Tournament current = this.tournamentRepository.findTournamentById(id);
            if (current == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Set<Player> players = input.getPlayers();

            if (players.size() > current.getMaxPlayers()) {
                return new ResponseEntity<>("Tournament is full!", HttpStatus.LOCKED);
            }

            current.setName(input.getName());
            current.setPlayers(players);

            return ResponseEntity.ok(this.tournamentRepository.save(current));
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    
    @RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> post(@RequestBody TournamentDto input,
                                  @RequestHeader("Authorization") String auth) {
        Map<String, Claim> claims = AuthUtil.getInstance().verifyAndGetClaims(auth);
        if (claims != null) {

            if(input.getMaxPlayers()%2 != 0) {
                return new ResponseEntity<>("Tournament must have an even max number of players.", HttpStatus.BAD_REQUEST);
            }

            Tournament check;
            check = this.tournamentRepository.findTournamentById(input.getId());
            if (check != null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }

            Set<Player> players = input.getPlayers();
            if (players.size() > input.getMaxPlayers()) {
                return new ResponseEntity<>("Too many players added to tournament!", HttpStatus.LOCKED);
            }

            Tournament tournament = new Tournament(input.getId(), input.getName(), players, input.getMaxPlayers());
            return ResponseEntity.ok(this.tournamentRepository.save(tournament));
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @RequestHeader("Authorization") String auth) {
        Map<String, Claim> claims = AuthUtil.getInstance().verifyAndGetClaims(auth);
        if (claims != null) {
            Tournament tournament = this.tournamentRepository.findTournamentById(id);
            if (tournament != null) {
                this.tournamentRepository.deleteById(id);
                if (this.tournamentRepository.findTournamentById(id) == null) {
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    
    private Gson createGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        return builder.create();
    } 
}
