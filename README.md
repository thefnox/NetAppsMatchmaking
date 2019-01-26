# NetAppsMatchmaking
Uses Spring Boot

GET - /players or /games or /tournaments
returns all players, games or tournaments

GET - /players/leaderboard
returns sorted list of all players by Elo in descending order

GET -/players/{id} or /games/{id} or /tournaments/{id}
returns player, game or tournament with id={id}

get -/games/{match_id}/{player_id}
returns a completed game object with an end time and result (0 = no result, 1 = player 1 win, 2 = player 2 win)
Also performs Elo and win/loss changes on both players based on which player made the api call.

POST - /players or /games or /tournaments

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

PUT -/players/{id} or /games/{id} or /tournaments/{id}

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

DELETE /players/{id} or /games/{id} or /tournaments/{id}
deletes player, game or tournament with id = {id}