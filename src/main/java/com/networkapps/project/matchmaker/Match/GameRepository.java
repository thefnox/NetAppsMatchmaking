package com.networkapps.project.matchmaker.Match;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Transactional
public interface GameRepository extends JpaRepository<Game, Long> {
    @Query(value="SELECT * FROM game g WHERE g.game_id = :id", nativeQuery = true)
    public Game findGameById(@Param("id") Long id);    
}
