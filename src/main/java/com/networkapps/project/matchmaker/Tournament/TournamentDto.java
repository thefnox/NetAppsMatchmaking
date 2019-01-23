/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.networkapps.project.matchmaker.Tournament;

import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author Martin
 */
public class TournamentDto implements Serializable{
    
    private Long id;
    private String name;
    private Set players;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set getPlayers() {
        return players;
    }

    public void setPlayers(Set players) {
        this.players = players;
    }
    
}
