package com.networkapps.project.matchmaker.Tournament.TournamentResult;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TournamentResultRepository extends JpaRepository<TournamentResult, Long> {
    @Query(value = "SELECT * FROM tournament_result tr WHERE tr.tournament_id = :id", nativeQuery=true)
    public TournamentResult findTournamentResultById(@Param("id") Long id);
}
