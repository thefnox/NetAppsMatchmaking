package com.networkapps.project.matchmaker.Match;

import com.networkapps.project.matchmaker.Tournament.Tournament;
import com.networkapps.project.matchmaker.Player.Player;
import java.io.Serializable;
import java.util.Date;

public class GameDto implements Serializable{
    
    private Long game_id;
    private short result = 0;
    private Date startTime;
    private Date endTime;
    private Player player1;
    private Player player2;
    private Tournament tournament;
    
    private GameDto() {}

    public GameDto(Long game_id, Date startTime, Player player1, Player player2) {
        this.game_id = game_id;
        this.startTime = startTime;
        this.player1 = player1;
        this.player2 = player2;
    }
    
    public GameDto(Long game_id, Date startTime, Player player1, Player player2, Tournament tournament) {
        this.game_id = game_id;
        this.startTime = startTime;
        this.player1 = player1;
        this.player2 = player2;
        this.tournament = tournament;
    }
    
    public Long getId() {
        return game_id;
    }

    public void setId(Long id) {
        this.game_id = id;
    }
    
    public short getResult() {
        return result;
    }

    public void setResult(short result) {
        this.result = result;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }
}