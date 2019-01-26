/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.networkapps.project.matchmaker.Tournament;

import com.networkapps.project.matchmaker.Player;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Tournament implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tournament_id;
    private String name;
    
    @ManyToMany(targetEntity=Player.class)
    private Set players;

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

    public Set getPlayers() {
        return players;
    }

    public void setPlayers(Set players) {
        this.players = players;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tournament_id != null ? tournament_id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tournament)) {
            return false;
        }
        Tournament other = (Tournament) object;
        if ((this.tournament_id == null && other.tournament_id != null) || (this.tournament_id != null && !this.tournament_id.equals(other.tournament_id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.networkapps.project.matchmaker.Tournament[ id=" + tournament_id + ", name=" + name + " ]";
    }
    
}
