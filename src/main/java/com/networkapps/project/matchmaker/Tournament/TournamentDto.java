/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.networkapps.project.matchmaker.Tournament;

import com.networkapps.project.matchmaker.Player.Player;
import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author Martin
 */
public class TournamentDto implements Serializable{
    
    private Long tournament_id;
    private String name;
    private Set<Player> players;
    
    private TournamentDto() {};
    
    public TournamentDto(Long id, String name, Set players) {
        this.tournament_id = id;
        this.name = name;
        this.players = players;
    }
    
    public Long getId() {
        return tournament_id;
    }

    public void setId(Long id) {
        this.tournament_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }
    
}
