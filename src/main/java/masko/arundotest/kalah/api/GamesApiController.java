package masko.arundotest.kalah.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import masko.arundotest.kalah.api.model.GeneralError;
import masko.arundotest.kalah.api.model.KalahGame;
import masko.arundotest.kalah.api.model.KalahGameId;
import masko.arundotest.kalah.model.InvalidMoveException;
import masko.arundotest.kalah.model.KalahGameEntity;
import masko.arundotest.kalah.service.GameNotFoundException;
import masko.arundotest.kalah.service.KalahService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Validated
public class GamesApiController implements GamesApi {

    private static final Logger log = LoggerFactory.getLogger(GamesApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private final KalahService kalahService;

    @Autowired
    public GamesApiController(ObjectMapper objectMapper, HttpServletRequest request, KalahService kalahService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.kalahService = kalahService;
    }

    @ExceptionHandler(GameNotFoundException.class)
    public ResponseEntity<GeneralError> handleGameNotFoundException(Exception ex) {
        return new ResponseEntity<>(new GeneralError().message("Game not found."), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidMoveException.class)
    public ResponseEntity<GeneralError> handleInvalidMoveException(Exception ex) {
        return new ResponseEntity<>(new GeneralError().message(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * Get game by id
     * @param gameId The game id to retrieve
     * @return A Kalah Game
     */
    public ResponseEntity<KalahGame> gamesGameIdGet(@Min(1) @PathVariable("gameId") Integer gameId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            Optional<KalahGameEntity> gameEntityOptional = kalahService.getGame(gameId);
            if (gameEntityOptional.isPresent()) {
                KalahGameEntity gameEntity = gameEntityOptional.get();
                KalahGame kalahGame = gameFromGameEntity(gameEntity);
                return new ResponseEntity<>(kalahGame, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    private KalahGame gameFromGameEntity(KalahGameEntity gameEntity) {
        return new KalahGame(String.valueOf(gameEntity.getId()), getGameUrl(gameEntity),
                gameEntity.getStatus());
    }

    /**
     * Play a pit
     * @param gameId the game to execute the move on
     * @param pitId the pit to play
     * @return Game state after the specified pit has been played
     */
    public ResponseEntity<KalahGame> gamesGameIdPitsPitIdPut(@Min(1) @PathVariable("gameId") Integer gameId,
                                                             @Min(1) @Max(14) @PathVariable("pitId") Integer pitId)
            throws InvalidMoveException, GameNotFoundException {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            KalahGameEntity gameEntity = kalahService.playPit(gameId, pitId);
            KalahGame kalahGame = gameFromGameEntity(gameEntity);
            return new ResponseEntity<>(kalahGame, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    /**
     * Get all games
     * @return All games
     */
    public ResponseEntity<List<KalahGameId>> gamesGet() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            List<KalahGameEntity> gameEntity = kalahService.getGames();
            List<KalahGameId> games =
                    gameEntity.stream().map(this::gameIdFromEntity).collect(Collectors.toList());
            return new ResponseEntity<>(games, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    /**
     * Create a new game
     * @return A new Kalah game
     */
    public ResponseEntity<KalahGameId> gamesPost() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            KalahGameEntity gameEntity = kalahService.createGame();
            KalahGameId kalahGameId = gameIdFromEntity(gameEntity);
            return new ResponseEntity<>(kalahGameId, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    private KalahGameId gameIdFromEntity(KalahGameEntity gameEntity) {
        return new KalahGameId(String.valueOf(gameEntity.getId()),
                getGameUrl(gameEntity));
    }

    /**
     * Build the entity reference
     * @param gameEntity the enity to buildthe url for
     * @return
     */
    private String getGameUrl(KalahGameEntity gameEntity) {
        return ((request.isSecure()) ? "https" : "http") + "://" + request.getServerName() + ":" +
                request.getServerPort() + "/games/" + gameEntity.getId();
    }

}
