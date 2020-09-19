package masko.arundotest.kalah.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import masko.arundotest.kalah.api.model.KalahGame;
import masko.arundotest.kalah.api.model.KalahGameId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.IOException;
import java.util.List;
@Controller
public class GamesApiController implements GamesApi {

    private static final Logger log = LoggerFactory.getLogger(GamesApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public GamesApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<KalahGame> gamesGameIdGet(@Min(1) @PathVariable("gameId") Integer gameId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<KalahGame>(objectMapper.readValue("{\n  \"id\" : \"1234\",\n  \"url\" : \"http://localhost:8080/game/1234\",\n  \"status\" : \"[ 6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0]\"\n}", KalahGame.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<KalahGame>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<KalahGame>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<KalahGame> gamesGameIdPitsPitIdPut(@Min(1) @PathVariable("gameId") Integer gameId,
                                                             @Min(1) @Max(14) @PathVariable("pitId") Integer pitId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<KalahGame>(objectMapper.readValue("{\n  \"id\" : \"1234\",\n  \"url\" : \"http://localhost:8080/game/1234\",\n  \"status\" : \"[ 6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0]\"\n}", KalahGame.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<KalahGame>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<KalahGame>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<KalahGameId>> gamesGet() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<KalahGameId>>(objectMapper.readValue("[ {\n  \"id\" : \"1234\",\n  \"url\" : \"http://localhost:8080/game/1234\"\n}, {\n  \"id\" : \"1234\",\n  \"url\" : \"http://localhost:8080/game/1234\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<KalahGameId>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<KalahGameId>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<KalahGameId> gamesPost() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<KalahGameId>(objectMapper.readValue("{\n  \"id\" : \"1234\",\n  \"url\" : \"http://localhost:8080/game/1234\"\n}", KalahGameId.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<KalahGameId>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<KalahGameId>(HttpStatus.NOT_IMPLEMENTED);
    }

}
