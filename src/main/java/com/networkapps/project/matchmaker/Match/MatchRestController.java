/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.networkapps.project.matchmaker.Match;

import java.util.List;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Martin
 */

@RestController
@RequestMapping(path = "/match", produces = APPLICATION_JSON_VALUE)
public class MatchRestController {
    
    private final MatchRepository matchRepository;
    
    public MatchRestController(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }
    
    @GetMapping()
    public List<Match> list() {
        return (List<Match>) this.matchRepository.findAll();
    }
    
    @GetMapping("/{user_id}")
    public List<Match> matchList(@PathVariable String user_id) { 
        return (List<Match>) this.matchRepository.findAll();
    }
    
//    @GetMapping("/{match_id)")
//    public Match match(@PathVariable String match_id) {
//        return this.matchRepository.findMatch(match_id);
//    }
    
    @PutMapping("/{match_id}")
    public ResponseEntity<?> put(@PathVariable String match_id, @RequestBody Object input) {
        return null;
    }
    
    @RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Match> post(@RequestBody MatchDto input) {
        return ResponseEntity.ok(this.matchRepository.save(new Match(input.getId(), 
                                                                     input.getPlayer1(), 
                                                                     input.getPlayer2())));
    }
   

}
