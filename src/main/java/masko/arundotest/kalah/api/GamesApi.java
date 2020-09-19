/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.21).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package masko.arundotest.kalah.api;

import masko.arundotest.kalah.api.model.KalahGame;
import masko.arundotest.kalah.api.model.KalahGameId;
import masko.arundotest.kalah.model.InvalidMoveException;
import masko.arundotest.kalah.service.GameNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-09-16T19:59:07.899Z[GMT]")
public interface GamesApi {

    @RequestMapping(value = "/games/{gameId}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<KalahGame> gamesGameIdGet(@Min(1) @PathVariable("gameId") Integer gameId);


    @RequestMapping(value = "/games/{gameId}/pits/{pitId}",
        produces = { "application/json" }, 
        method = RequestMethod.PUT)
    ResponseEntity<KalahGame> gamesGameIdPitsPitIdPut(@Min(1) @PathVariable("gameId") Integer gameId,
                                                      @Min(1) @Max(14)  @PathVariable("pitId") Integer pitId) throws InvalidMoveException, GameNotFoundException;

    @RequestMapping(value = "/games",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<KalahGameId>> gamesGet();

    @RequestMapping(value = "/games",
        produces = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<KalahGameId> gamesPost();

}

