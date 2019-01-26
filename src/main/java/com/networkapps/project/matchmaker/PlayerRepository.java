/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.networkapps.project.matchmaker;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
/**
 *
 * @author kamai
 */
public interface PlayerRepository extends JpaRepository<Player, String> {
    @Query("SELECT p FROM Player p WHERE p.id = :name")
    public List<Player> findByName(@Param("name") String name);
    
    @Query(value = "SELECT * FROM player p WHERE p.id = :id", nativeQuery=true)
    public Player findUserById(@Param("id") String id);
}
