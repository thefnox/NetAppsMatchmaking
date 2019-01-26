/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.networkapps.project.matchmaker;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long player_id;
    private String username;
    private String email;
    private String password;
    private int wins = 0;
    private int losses = 0;
    private int matches = 0;
    
    @Column(name="tournaments_played", nullable = true)
    private int tournamentsPlayed = 0;
    
    @Column(name="tournaments_won", nullable = true)
    private int tournamentsWon = 0;
    
    private int elo = 1000;
    
    public Long getId() {
        return player_id;
    }

    public void setId(Long id) {
        this.player_id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getMatches() {
        return matches;
    }

    public void setMatches(int matches) {
        this.matches = matches;
    }

    public int getTournamentsPlayed() {
        return tournamentsPlayed;
    }

    public void setTournamentsPlayed(int tournamentsPlayed) {
        this.tournamentsPlayed = tournamentsPlayed;
    }

    public int getTournamentsWon() {
        return tournamentsWon;
    }

    public void setTournamentsWon(int tournamentsWon) {
        this.tournamentsWon = tournamentsWon;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public Player() {}
    
    public Player(Long id, String username, String email, String password) {
        this.player_id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (player_id != null ? player_id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Player)) {
            return false;
        }
        Player other = (Player) object;
        if ((this.player_id == null && other.player_id != null) || (this.player_id != null && !this.player_id.equals(other.player_id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User{[ id=" + player_id + "], [email=" + email + "], [elo=" + elo + "]}";
    }
    
}
