package com.networkapps.project.matchmaker.Session;

import com.networkapps.project.matchmaker.Auth.AuthUtil;
import com.networkapps.project.matchmaker.Player.Player;
import com.networkapps.project.matchmaker.Player.PlayerRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(path = "/session", produces = APPLICATION_JSON_VALUE)
public class SessionRestController {

    private final SessionRepository sessionRepository;
    private final PlayerRepository playerRepository;

    public SessionRestController(SessionRepository sessionRepository, PlayerRepository playerRepository) {
        this.sessionRepository = sessionRepository;
        this.playerRepository = playerRepository;
    }

    @GetMapping()
    public List<Session> list() {
        return this.sessionRepository.findAll();
    }

    @RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<SessionResponseDto> post(@RequestBody SessionRequestDto request) {

        if (request.getEmail() == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Player player = playerRepository.findByEmail(request.getEmail()).get(0);

        if (player == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        /*  ## Create new Session ## */
        Session newSession = new Session();
        // Create a new refresh token.
        String newRefreshToken = UUID.randomUUID().toString();
        // Create new expiration date that is 30 days from now.
        Calendar calendar=new GregorianCalendar();
        calendar.add(Calendar.DATE, 30);
        Date expirationDate=calendar.getTime();

        newSession.setRefreshToken(newRefreshToken);
        newSession.setExpiry(expirationDate);
        newSession.setEmail(player.getEmail());

        /* ## Create new response */
        SessionResponseDto response = new SessionResponseDto();
        response.setRefresh_token(newRefreshToken);

        if (request.getRefresh_token() != null) {
            // Check if a session with refresh_token does exist
            Session session = sessionRepository.findByRefreshToken(request.getRefresh_token()).get(0);
            if (session == null) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            } else {
                // Check if session was expired
                if (session.getExpiry().before(new Date())) {
                    return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
                }

                // Delete the old session, in any case.
                sessionRepository.delete(session);

                // Insert new session to the database.
                sessionRepository.save(newSession);

                response.setAuth_token(AuthUtil.getInstance().generateJWT(player));

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } else if (request.getPassword() != null) {

            // Check if the password is correct.
            if (!player.getPassword().equals(request.getPassword())) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            // Insert new session to the database.
            sessionRepository.save(newSession);

            response.setAuth_token(AuthUtil.getInstance().generateJWT(player));

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error message")
    public void handleError() {
    }
}