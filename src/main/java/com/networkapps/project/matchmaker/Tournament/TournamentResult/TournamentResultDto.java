package com.networkapps.project.matchmaker.Tournament.TournamentResult;

import com.networkapps.project.matchmaker.Player.Player;
import com.networkapps.project.matchmaker.Tournament.Tournament;

public class TournamentResultDto {

    private Long id;
    private Player player;
    private int position;
    private Tournament tournament;

    public TournamentResultDto() {}

    public TournamentResultDto(Long id, Player player, int position) {
        this.id = id;
        this.player = player;
        this.position = position;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }
}
