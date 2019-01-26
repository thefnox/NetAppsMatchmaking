package com.networkapps.project.matchmaker.Session;

import com.networkapps.project.matchmaker.User;
import com.networkapps.project.matchmaker.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(path = "/session", produces = APPLICATION_JSON_VALUE)
public class SessionRestController {

    private final SessionRepository sessionRepository;

    public SessionRestController(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @GetMapping()
    public List<Session> list() {
        return this.sessionRepository.findAll();
    }

    @RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Session> post(@RequestBody SessionRequest request) {



        //Down here works..
        Session session = new Session();
        session.setId(new Long(234));
        session.setEmail(request.getEmail());
        session.setExpiry("whattee");
        session.setRefreshToken(request.getRefresh_token());

        System.out.println(request.getPassword());

        return ResponseEntity
                .ok(this.sessionRepository.save(session));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error message")
    public void handleError() {
    }
}

class SessionRequest {
    String email;
    String password;
    String refresh_token;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }
}
