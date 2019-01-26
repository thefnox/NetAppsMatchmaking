package com.networkapps.project.matchmaker.Match;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.networkapps.project.matchmaker.Player.Player;
import com.networkapps.project.matchmaker.Player.PlayerRepository;
import java.util.Date;
import java.util.List;
import org.springframework.http.HttpStatus;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/games", produces = APPLICATION_JSON_VALUE)
public class GameRestController {
    
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    
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
    public Game getResult(@PathVariable Long game_id, @PathVariable String player_id) {
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
                result = 1;
            } else if (player.getId().equals(current.getPlayer2().getId())) {
                opponent = current.getPlayer1();
                result = 2;
            } else {
                return null;
            }
            
            int opponentLosses = opponent.getLosses();
            int opponentWins = opponent.getWins();
            int opponentElo = opponent.getElo();
            
            int playerEloHolder = player.getElo();
            
            player.setElo(player.getElo() + getEloChanges(opponentElo, playerWins, playerLosses, true));
            opponent.setElo(opponentElo + getEloChanges(playerEloHolder, opponentWins, opponentLosses, false));
            
            current.setResult(result);
            
            player.setMatches(player.getMatches()+1);
            player.setWins(player.getWins()+1);
            
            opponent.setLosses(opponentLosses+1);
            opponent.setMatches(opponentWins+opponentLosses+1);
            
            this.playerRepository.save(player);
            this.playerRepository.save(opponent);
            this.gameRepository.save(current);
            
            return current;
        }
        return null;
    }
    
    private int getEloChanges(int opponentElo, int wins, int losses, boolean didWin) {
        if(didWin) {
            return (opponentElo + 400*(wins-losses))/(wins+losses);
        } else {
            return (opponentElo - 400*(wins-losses))/(wins+losses);
        }
    }

    private Gson createGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        return builder.create();
    } 
}
