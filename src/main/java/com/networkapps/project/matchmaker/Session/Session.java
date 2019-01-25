package com.networkapps.project.matchmaker.Session;

import com.networkapps.project.matchmaker.Tournament.Tournament;
import com.networkapps.project.matchmaker.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Session implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;
    private String refreshToken;
    private String expiry; //TODO: Turn this into an actual date.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refresh_token) {
        this.refreshToken = refresh_token;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
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
        if (!(object instanceof com.networkapps.project.matchmaker.Session.Session)) {
            return false;
        }
        com.networkapps.project.matchmaker.Session.Session other = (com.networkapps.project.matchmaker.Session.Session) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.networkapps.project.matchmaker.Match[ id=" + id + " ]";
    }
}
