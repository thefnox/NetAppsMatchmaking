/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.networkapps.project.matchmaker.Tournament;

import com.networkapps.project.matchmaker.Match.Match;
import com.networkapps.project.matchmaker.Match.MatchRepository;
import java.util.List;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 *
 * @author Martin
 */
@RestController
@RequestMapping("/tournament")
public class TournamentRestController {
    private final TournamentRepository tournamentRepository;
    
    public TournamentRestController(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }
    
    @GetMapping()
    public List<Tournament> list() {
        return (List<Tournament>) this.tournamentRepository.findAll();
    }
    
    @GetMapping("/{tournament_id}")
    public List<Tournament> matchList(@PathVariable String tournament_id) { 
        return (List<Tournament>) this.tournamentRepository.findById(tournament_id).get();
    }
    
    @PutMapping("/{match_id}")
    public ResponseEntity<?> put(@PathVariable String match_id, @RequestBody Object input) {
        return null;
    }
    
//    @RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE)
//    public ResponseEntity<Match> post(@RequestBody TournamentDto input) {
//        return ResponseEntity.ok(this.tournamentRepository.save(new Tournament(input.getId(), 
//                                                                     input.getName(), 
//                                                                     input.getPlayers()
//        )));
//    }
}
