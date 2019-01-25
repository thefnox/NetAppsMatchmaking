package com.networkapps.project.matchmaker.Session;

import com.networkapps.project.matchmaker.User;
import com.networkapps.project.matchmaker.UserDto;
import com.networkapps.project.matchmaker.UserRepository;
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

//    @RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE)
//    public ResponseEntity<User> post(@RequestBody UserDto input) {
//        return ResponseEntity
//                .ok(this.userRepository.save(new User(input.getId(), input.getEmail(), input.getPassword())));
//    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error message")
    public void handleError() {
    }

}
