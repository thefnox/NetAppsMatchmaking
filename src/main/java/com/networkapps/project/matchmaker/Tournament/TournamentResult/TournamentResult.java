package com.networkapps.project.matchmaker.Tournament.TournamentResult;

import com.networkapps.project.matchmaker.Player.Player;
import com.networkapps.project.matchmaker.Tournament.Tournament;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class TournamentResult implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Player player;
    @ManyToOne
    private Tournament tournament;
    private int position;

    public TournamentResult() {}

    public TournamentResult(Player player, int position, Tournament tournament) {
        this.player = player;
        this.position = position;
        this.tournament = tournament;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TournamentResult)) {
            return false;
        }
        TournamentResult other = (TournamentResult) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.networkapps.project.matchmaker.TournamentResult[ id=" + id + " ]";
    }
    
}
