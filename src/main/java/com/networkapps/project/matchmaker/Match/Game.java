/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.networkapps.project.matchmaker.Match;

import com.networkapps.project.matchmaker.Tournament.Tournament;
import com.networkapps.project.matchmaker.Player.Player;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

@Entity
public class Game implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long game_id;
    private short result = 0;
    @Basic(optional = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date startTime;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date endTime;
    @ManyToOne
    private Player player1;
    @ManyToOne
    private Player player2;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "tournament_id"))
    private Tournament tournament;
    
    //Empty constructor required for some reason
    private Game() {}
    
    //Constructor for normal game
    public Game(Long game_id, Date startTime, Player player1, Player player2) {
        this.game_id = game_id;
        this.startTime = startTime;
        this.player1 = player1;
        this.player2 = player2;
    }
    
    //Constructor for tournament game
    public Game(Long game_id, Date startTime, Player player1, Player player2, Tournament tournament) {
        this.game_id = game_id;
        this.startTime = startTime;
        this.player1 = player1;
        this.player2 = player2;
        this.tournament = tournament;
    }

    public long getId() {
        return game_id;
    }

    public void setId(long game_id) {
        this.game_id = game_id;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (game_id != null ? game_id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Game)) {
            return false;
        }
        Game other = (Game) object;
        if ((this.game_id == null && other.game_id != null) || (this.game_id != null && !this.game_id.equals(other.game_id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.networkapps.project.matchmaker.Match[ id=" + game_id + " ]";
    }
    
}
