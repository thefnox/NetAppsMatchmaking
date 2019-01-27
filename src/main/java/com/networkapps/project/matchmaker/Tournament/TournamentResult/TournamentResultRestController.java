package com.networkapps.project.matchmaker.Tournament.TournamentResult;

import com.auth0.jwt.interfaces.Claim;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.networkapps.project.matchmaker.Auth.AuthUtil;
import com.networkapps.project.matchmaker.Player.Player;
import com.networkapps.project.matchmaker.Tournament.Tournament;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping("/tournament-result")
public class TournamentResultRestController {

    private final TournamentResultRepository tournamentResultRepository;

    public TournamentResultRestController(TournamentResultRepository tournamentResultRepository) {
        this.tournamentResultRepository = tournamentResultRepository;
    }

    @GetMapping()
    public String list() {
        List<TournamentResult> list = this.tournamentResultRepository.findAll();
        if (!list.isEmpty()) {
            Gson gson = createGson();
            return gson.toJson(list);

        }
        return "";
    }

    @GetMapping("/{id}")
    public Object get(@PathVariable Long id) {
        TournamentResult tournamentResult = this.tournamentResultRepository.findTournamentResultById(id);
        if (tournamentResult != null) {
            Gson gson = createGson();
            return gson.toJson(tournamentResult);
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "/{id}", method = PUT, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<TournamentResult> put(@PathVariable Long id, @RequestBody TournamentResultDto input,
                                                @RequestHeader("Authorization") String auth) {
        Map<String, Claim> claims = AuthUtil.getInstance().verifyAndGetClaims(auth);
        if (claims != null) {
            TournamentResult current = this.tournamentResultRepository.findTournamentResultById(id);
            if (current == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            current.setPosition(input.getPosition());
            return ResponseEntity.ok(this.tournamentResultRepository.save(current));
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> post(@RequestBody TournamentResultDto input,
                                  @RequestHeader("Authorization") String auth) {
        Map<String, Claim> claims = AuthUtil.getInstance().verifyAndGetClaims(auth);
        if (claims != null) {
            TournamentResult check;
            check = this.tournamentResultRepository.findTournamentResultById(input.getId());
            if (check != null) {
                return new ResponseEntity<>("Tournament result already exists!", HttpStatus.CONFLICT);
            }

            Player player = input.getPlayer();
            Tournament tournament = input.getTournament();

            TournamentResult tournamentResult = new TournamentResult(player, input.getPosition(), tournament);
            return ResponseEntity.ok(this.tournamentResultRepository.save(tournamentResult));
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id,
                                    @RequestHeader("Authorization") String auth) {
        Map<String, Claim> claims = AuthUtil.getInstance().verifyAndGetClaims(auth);
        if (claims != null) {
            TournamentResult tournamentResult = this.tournamentResultRepository.findTournamentResultById(id);
            if (tournamentResult != null) {
                this.tournamentResultRepository.deleteById(id);
                if (this.tournamentResultRepository.findTournamentResultById(id) == null) {
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    private Gson createGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        return builder.create();
    }

}
