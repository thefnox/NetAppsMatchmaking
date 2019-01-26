package com.networkapps.project.matchmaker.Session;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, String> {

    @Query("SELECT s FROM Session s WHERE s.refreshToken = :refreshToken")
    public List<Session> findByRefreshToken(@Param("refreshToken") String refreshToken);

    @Query("DELETE FROM Session s WHERE s.id = ?1")
    void deleteById(Long id);
}
