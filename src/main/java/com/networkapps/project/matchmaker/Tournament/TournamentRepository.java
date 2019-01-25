/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.networkapps.project.matchmaker.Tournament;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Martin
 */
public interface TournamentRepository extends JpaRepository<Tournament, String> {
    
}
