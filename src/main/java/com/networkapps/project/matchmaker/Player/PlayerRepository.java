/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.networkapps.project.matchmaker.Player;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
/**
 *
 * @author kamai
 */
public interface PlayerRepository extends JpaRepository<Player, String> {   
    @Query(value = "SELECT * FROM player p WHERE p.player_id = :id", nativeQuery=true)
    public Player findUserById(@Param("id") String id);
    
    @Query("SELECT p FROM Player p WHERE p.email = :email")
    public List<Player> findByEmail(@Param("email") String email);
    
    @Query(value = "SELECT * FROM player p ORDER BY elo DESC", nativeQuery = true)
    public List<Player> getLeaderboard();
}
