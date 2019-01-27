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
    private int maxPlayers;

    private TournamentDto() {};

    private TournamentDto(Long id, String name, int maxPlayers) {
        this.tournament_id = id;
        this.name = name;
        this.maxPlayers = maxPlayers;
    }
    
    public TournamentDto(Long id, String name, Set players, int maxPlayers) {
        this.tournament_id = id;
        this.name = name;
        this.players = players;
        this.maxPlayers = maxPlayers;
    }
    
    public Long getId() {
        return tournament_id;
    }

    public void setId(Long id) {
        this.tournament_id = id;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
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
