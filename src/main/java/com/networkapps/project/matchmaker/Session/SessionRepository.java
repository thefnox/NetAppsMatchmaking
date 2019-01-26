package com.networkapps.project.matchmaker.Session;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, String> {

    @Query(
            value = "SELECT * FROM session",
            nativeQuery=true
    )
    public List<Session> getSessions();

    @Query("SELECT s FROM Session s WHERE s.refreshToken = :refreshToken")
    public List<Session> findByRefreshToken(@Param("refreshToken") String refreshToken);
}
