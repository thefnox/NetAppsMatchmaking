# NetAppsMatchmaking
Uses Spring Boot

GET - /players or /games or /tournaments
returns all players, games or tournaments

GET -/players/{id} or /games/{id} or /tournaments/{id}
returns player, game or tournament with id={id}

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
                        <PLAYER OBJECT>
                   },
                   "player2" : {
                        <PLAYER OBJECT>
                   }
                }

          Tournament JSON Model:
                {
                  "name" : String,
                  "players" : [<Player Objects>]
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
                        <PLAYER OBJECT>
                    },
                    "player2": {
                        <PLAYER OBJECT>
                    },
                    "tournament": {
                        <TOURNAMENT OBJECT>
                    }
                }

          Tournament Model:
                {
                    "name" : String,
                    "players" : [<PLAYER OBJECTS>]
                }

DELETE /players/{id} or /games/{id} or /tournaments/{id}
deletes player, game or tournament with id = {id}
