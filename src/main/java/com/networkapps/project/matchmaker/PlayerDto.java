/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.networkapps.project.matchmaker;

/**
 *
 * @author kamai
 */
public class PlayerDto {

    private Long player_id;
    private String username;
    private String email;
    private String password;
    private int wins = 0;
    private int losses = 0;
    private int matches = 0;
    private int tournamentsPlayed = 0;
    private int tournamentsWon = 0;
    private int elo = 1000;

    public PlayerDto(Long id, String email, String password) {
        this.player_id = id;
        this.email = email;
        this.password = password;
    }

    public PlayerDto(Long id, String username, String email, String password) {
        this.player_id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return player_id;
    }

    public void setId(Long id) {
        this.player_id = id;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
