package com.networkapps.project.matchmaker.Match;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.networkapps.project.matchmaker.MatchmakerApplication;
import com.networkapps.project.matchmaker.Player.Player;
import com.networkapps.project.matchmaker.Player.PlayerRepository;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.http.HttpStatus;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import javax.xml.ws.Response;

@RestController
@RequestMapping(path = "/games", produces = APPLICATION_JSON_VALUE)
public class GameRestController {
    static class DeferredMatch extends DeferredResult<Object> {
        private final String player;
        private final Date creationDate;

        public DeferredMatch(String player) {
            this.player = player;
            this.creationDate = new Date();
        }
    }

    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    private final ConcurrentHashMap<String, String> matchRequests = new ConcurrentHashMap<>();
    private final Queue<DeferredMatch> responseQueue  = new ConcurrentLinkedQueue<>();
    
    public GameRestController(GameRepository matchRepository, PlayerRepository playerRepository) {
        this.gameRepository = matchRepository;
        this.playerRepository = playerRepository;
    }
    
    @GetMapping()
    public String list() {
        List<Game> list = this.gameRepository.findAll();
        if (!list.isEmpty()) {
            Gson gson = createGson();
            return gson.toJson(list);
        }
        return "";
    }
    
    @GetMapping("/{id}")
    public Object get(@PathVariable Long id) {
        Game game = this.gameRepository.findGameById(id);
        if (game != null) {
            Gson gson = createGson();
            return gson.toJson(game);
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/request/{challenge_id}/{my_id}")
    public Object challenge(@PathVariable String challenge_id, @PathVariable String my_id) {
        Player player = this.playerRepository.findUserById(challenge_id);
        Player me = this.playerRepository.findUserById(my_id);
        if (matchRequests.get(challenge_id) == null) {
            if (player != null && me != null) {
                matchRequests.put(challenge_id, my_id);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/listen/{my_id}")
    public DeferredMatch listen(@PathVariable String my_id) {
        Player me = this.playerRepository.findUserById(my_id);
        DeferredMatch response = new DeferredMatch(my_id);
        if (me != null) {
            responseQueue.add(response);
        }
        return response;
    }

    @Scheduled(fixedRate = 1000)
    public void processMatchRequests() {
        Date curTime = new Date();
        for (DeferredMatch response : responseQueue) {
            if (matchRequests.get(response.player) != null) {
                Player challenger = this.playerRepository.findUserById(matchRequests.get(response.player));
                Gson gson = createGson();
                response.setResult(gson.toJson(challenger));
                responseQueue.remove(response);
            } else if (((curTime.getTime() - response.creationDate.getTime()) / 1000) > 30) {
                //cull old responses
                responseQueue.remove(response);
            }
        }
    }

    @RequestMapping(value = "/{id}", method = PUT, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Game> put(@PathVariable Long id, @RequestBody GameDto input) {
        Game current = this.gameRepository.findGameById(id);
        if(current == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Player player1 = input.getPlayer1();
        Player player2 = input.getPlayer2();
        
        current.setStartTime(input.getStartTime());
        current.setPlayer1(player1);
        current.setPlayer2(player2);
        current.setResult(input.getResult());
        
        if(input.getEndTime() != null) {
            current.setEndTime(input.getEndTime());
        }
        
        if(input.getTournament() != null) {
            current.setTournament(input.getTournament());
        }
        
        return ResponseEntity.ok(this.gameRepository.save(current));
    }
    
    @RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Game> post(@RequestBody GameDto input) {
        Game check;
        check = this.gameRepository.findGameById(input.getId());
        if(check != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        
        Player player1 = input.getPlayer1();
        Player player2 = input.getPlayer2();
        
        Game game = new Game(input.getId(), new Date(), player1, player2);
        return ResponseEntity.ok(this.gameRepository.save(game));
    }
    
    @DeleteMapping("/{game_id}")
    public ResponseEntity<?> delete(@PathVariable Long game_id) {
        Game game = this.gameRepository.findGameById(game_id);
        if (game != null) {
            this.gameRepository.deleteById(game_id);
            if (this.gameRepository.findGameById(game_id) == null) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/{game_id}/{player_id}")
    public ResponseEntity<?> getResult(@PathVariable Long game_id, @PathVariable String player_id) {
        Game current = this.gameRepository.findGameById(game_id);
        Player player = this.playerRepository.findUserById(player_id);
        
        int playerWins = player.getWins();
        int playerLosses = player.getLosses();
        
        if(current.getEndTime() == null) {
            current.setEndTime(new Date());
            this.gameRepository.save(current);
            
            Player opponent;
            short result;
            if(player.getId().equals(current.getPlayer1().getId())) {
                opponent = current.getPlayer2();
                result = 2;
            } else if (player.getId().equals(current.getPlayer2().getId())) {
                opponent = current.getPlayer1();
                result = 1;
            } else {
                return new ResponseEntity<>("You are not a player in this match.", HttpStatus.FORBIDDEN);
            }
            
            int opponentLosses = opponent.getLosses();
            int opponentWins = opponent.getWins();
            int opponentElo = opponent.getElo();
            
            int playerEloHolder = player.getElo();
            
            opponent.setElo(player.getElo() + getEloChanges(opponentElo, playerWins, playerLosses, true));
            player.setElo(opponentElo + getEloChanges(playerEloHolder, opponentWins, opponentLosses, false));
            
            current.setResult(result);
            
            opponent.setMatches(player.getMatches()+1);
            opponent.setWins(player.getWins()+1);
            
            player.setLosses(opponentLosses+1);
            player.setMatches(opponentWins+opponentLosses+1);
            
            this.playerRepository.save(player);
            this.playerRepository.save(opponent);
            this.gameRepository.save(current);

            return new ResponseEntity<>(current, HttpStatus.OK);
        }
        return new ResponseEntity<>("Game is already complete.", HttpStatus.CONFLICT);
    }
    
    private int getEloChanges(int opponentElo, int wins, int losses, boolean didWin) {
        if(didWin) {
            return (opponentElo + 200*(wins-losses))/(wins+losses);
        } else {
            return (opponentElo - 200*(wins-losses))/(wins+losses);
        }
    }

    private Gson createGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        return builder.create();
    } 
}
