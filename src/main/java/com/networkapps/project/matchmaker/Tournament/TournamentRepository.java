/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.networkapps.project.matchmaker.Tournament;

import com.networkapps.project.matchmaker.Player.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Martin
 */
public interface TournamentRepository extends JpaRepository<Tournament, Long> {
    @Query(value = "SELECT * FROM tournament t WHERE t.tournament_id = :id", nativeQuery=true)
    public Tournament findTournamentById(@Param("id") Long id);
}
