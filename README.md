# NetAppsMatchmaking
Uses Spring Boot

BASE URL:
http://netappsmm.eu-north-1.elasticbeanstalk.com/

GET - /players or /games or /tournaments or /tournament-results
returns all players, games, tournaments or tournament-results

GET - /players/leaderboard
returns sorted list of all players by Elo in descending order

GET -/players/{id} or /games/{id} or /tournaments/{id} or /tournament-results/{id}
returns player, game, tournament or tournament-result with id={id}

GET -/players/me (with Authorization header and the auth_token(without Bearer))
returns the player who owns the auth_token.

POST -/games/{match_id} <- this is to determine the winner of a match
Requires Auth token to determine your own player_id
returns a completed game object with an end time and result (0 = no result, 1 = player 1 win, 2 = player 2 win)
Also performs Elo and win/loss changes on both players based on which player made the api call.

GET -/tournaments/{tournament_id}/players
returns a shuffled list of all players in a tournament

PUT -/tournaments/{tournament_id}/join (with authorisation header - no bearer)
Body should be a Player JSON model with id field. 
Adds you to the list of players in a tournament

POST - /players or /games or /tournaments or /tournament-results

          Player JSON Model:
                {
                    "id" : String,
                    "email" : String,
                    "password" : String
                }

          Games JSON Model
                {
                   "player1" : {
                        <PLAYER OBJECT WITH ID>
                   },
                   "player2" : {
                        <PLAYER OBJECT WITH ID>
                   }
                }

          Tournament JSON Model:
                {
                  "name" : String,
                  "players" : [<Player Objects WITH IDs>]
                }         
                
          Tournament-results JSON Model:
                {
                  "player" : {<Player object with id>},
                  "tournament" : {<Tournament object with id>}, 
                  "position" : Int
                }

PUT -/players/me //with auth or /games/{id} or /tournaments/{id} or /tournament-results/{id}

          Player Model:
                {
                    "elo" : int,
                    "email" : String,
                    "losses" : int,
                    "matches" : int,
                    "password" : String,
                    "tournamentsplayed" : int,
                    "tournamentswon" : int,
                    "wins" : int
                }

          Games Model:
                {
                    "result": short,
                    "startTime": Date,
                    "endTime": Date,
                    "player1": {
                        <PLAYER OBJECT WITH ID>
                    },
                    "player2": {
                        <PLAYER OBJECT WITH ID>
                    },
                    "tournament": {
                        <TOURNAMENT OBJECT WITH ID>
                    }
                }

          Tournament Model:
                {
                    "name" : String,
                    "players" : [<PLAYER OBJECTS WITH IDs>]
                }
                
          Tournament-results Model  (can only modify position):
                {
                     "player" : {<Player object with id>},
                     "tournament" : {<Tournament object with id>}, 
                     "position" : Int
                }          

DELETE /players/me //with auth or /games/{id} or /tournaments/{id} or /tournament-results/{id}
deletes player, game, tournament or tournament-result with id = {id}
