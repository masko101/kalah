package masko.arundotest.kalah;

import masko.arundotest.kalah.api.model.GeneralError;
import masko.arundotest.kalah.api.model.KalahGame;
import masko.arundotest.kalah.api.model.KalahGameId;
import masko.arundotest.kalah.model.KalahGameEntity;
import masko.arundotest.kalah.model.KalahGamePlayer;
import masko.arundotest.kalah.service.KalahService;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

@SpringBootTest(classes = KalahApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class KalahApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private KalahService kalahService;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @BeforeEach
    public void initialize() {
        kalahService.clearGames();
    }

    @Test
    public void testGetAllGames() {

        kalahService.saveGame(new KalahGameEntity(1L));
        kalahService.saveGame(new KalahGameEntity(2L));
        kalahService.saveGame(new KalahGameEntity(3L));
        kalahService.saveGame(new KalahGameEntity(4L));

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<KalahGameId[]> response = restTemplate.exchange(getRootUrl() + "/games", HttpMethod.GET, entity, KalahGameId[].class);
        KalahGameId[] allKalahGames = response.getBody();
        Assert.assertNotNull(allKalahGames);
        Assert.assertEquals("Status OK", 200, response.getStatusCode().value());
//        Assert.assertEquals("KalahGames Found", testKalahGameEntities.size(), allKalahGames.length);
        Assert.assertEquals("KalahGame 1 Id", "1", allKalahGames[0].getId());
        Assert.assertEquals("KalahGame 2 Id", "2", allKalahGames[1].getId());
        Assert.assertEquals("KalahGame 3 Id", "3", allKalahGames[2].getId());
        Assert.assertEquals("KalahGame 4 Id", "4", allKalahGames[3].getId());
    }

    @Test
    public void testGetKalahGameById() {
        kalahService.saveGame(new KalahGameEntity(1L));
        kalahService.saveGame(new KalahGameEntity(2L));
        kalahService.saveGame(new KalahGameEntity(3L));
        kalahService.saveGame(new KalahGameEntity(4L));

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<KalahGame> response = restTemplate.exchange(getRootUrl() + "/games/1", HttpMethod.GET, entity, KalahGame.class);
        KalahGame KalahGame = response.getBody();
        Assert.assertEquals("Status OK", 200, response.getStatusCode().value());
        Assert.assertNotNull(KalahGame);
        Assert.assertEquals("KalahGame 1 ID", "1", KalahGame.getId());
    }

    @Test
    public void testCreateNewGame() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<KalahGameId> response = restTemplate.exchange(getRootUrl() + "/games", HttpMethod.POST, entity, KalahGameId.class);
        KalahGameId KalahGame = response.getBody();
        Assert.assertEquals("Status OK", 200, response.getStatusCode().value());
        Assert.assertNotNull(KalahGame);
        Assert.assertEquals("KalahGame 1 ID", "1", KalahGame.getId());
    }

    @Test
    public void playInvalidMovePlayKalahPit() {
        kalahService.saveGame(new KalahGameEntity(1L));

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        //Init Arrays.asList(6, 6, 0, 7, 7, 7, 1, 7, 7, 6, 6, 6, 6, 0)
        ResponseEntity<GeneralError> errorResponse = restTemplate.exchange(getRootUrl() + "/games/1/pits/7", HttpMethod.PUT, entity, GeneralError.class);
        GeneralError error = errorResponse.getBody();
        Assert.assertEquals("Bad Reqest", 400, errorResponse.getStatusCode().value());
        Assert.assertNotNull(error);
        Assert.assertEquals("Kalah Game Error", "Cannot play the Kalah pit.", error.getMessage());
    }

    @Test
    public void playInvalidMovePlayEmptyPit() {
        kalahService.saveGame(new KalahGameEntity(1L, KalahGamePlayer.SOUTH,
                Arrays.asList(6, 6, 0, 7, 7, 7, 1, 7, 7, 6, 6, 6, 6, 0)));
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        //Init Arrays.asList(6, 6, 0, 7, 7, 7, 1, 7, 7, 6, 6, 6, 6, 0)
        ResponseEntity<GeneralError> errorResponse = restTemplate.exchange(getRootUrl() + "/games/1/pits/3", HttpMethod.PUT, entity, GeneralError.class);
        GeneralError error = errorResponse.getBody();
        Assert.assertEquals("Bad Reqest", 400, errorResponse.getStatusCode().value());
        Assert.assertNotNull(error);
        Assert.assertEquals("Kalah Game Error", "Cannot play an empty pit.", error.getMessage());
    }

    @Test
    public void playInvalidMovePlayOpponentPit() {
        kalahService.saveGame(new KalahGameEntity(1L, KalahGamePlayer.SOUTH,
                Arrays.asList(6, 6, 0, 7, 7, 7, 1, 7, 7, 6, 6, 6, 6, 0)));
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        //Init Arrays.asList(6, 6, 0, 7, 7, 7, 1, 7, 7, 6, 6, 6, 6, 0) //south turn
        ResponseEntity<GeneralError> errorResponse = restTemplate.exchange(getRootUrl() + "/games/1/pits/10", HttpMethod.PUT, entity, GeneralError.class);
        GeneralError error = errorResponse.getBody();
        Assert.assertEquals("Bad Reqest", 400, errorResponse.getStatusCode().value());
        Assert.assertNotNull(error);
        Assert.assertEquals("Kalah Game Error", "Player cannot play opponents pit.", error.getMessage());
    }

    @Test
    public void playValidMoveEndTurn() {
        kalahService.saveGame(new KalahGameEntity(1L));

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/games/1/pits/2", HttpMethod.PUT, entity, String.class);
        String kalahGame = response.getBody();
        Assert.assertEquals("Status OK", 200, response.getStatusCode().value());
        Assert.assertNotNull(kalahGame);
        Assert.assertEquals("kalahGame String", "{\"id\":\"1\",\"url\":\"" + buildURL(1) + "\",\"status\":[6,0,7,7,7,7,1,7,6,6,6,6,6,0]}", kalahGame);

        ResponseEntity<GeneralError> errorResponse = restTemplate.exchange(getRootUrl() + "/games/1/pits/3", HttpMethod.PUT, entity, GeneralError.class);
        GeneralError error = errorResponse.getBody();
        Assert.assertEquals("Bad Reqest", 400, errorResponse.getStatusCode().value());
        Assert.assertNotNull(error);
        Assert.assertEquals("Kalah Game Error", "Player cannot play opponents pit.", error.getMessage());
    }

    @Test
    public void playValidMoveExtraTurn() {
        kalahService.saveGame(new KalahGameEntity(1L));

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/games/1/pits/1", HttpMethod.PUT, entity, String.class);
        String kalahGame = response.getBody();
        Assert.assertEquals("Status OK", 200, response.getStatusCode().value());
        Assert.assertNotNull(kalahGame);
        Assert.assertEquals("kalahGame String", "{\"id\":\"1\",\"url\":\"" + buildURL(1) + "\",\"status\":[0,7,7,7,7,7,1,6,6,6,6,6,6,0]}", kalahGame);

        response = restTemplate.exchange(getRootUrl() + "/games/1/pits/2", HttpMethod.PUT, entity, String.class);
        kalahGame = response.getBody();
        Assert.assertEquals("Status OK", 200, response.getStatusCode().value());
        Assert.assertNotNull(kalahGame);
    }

    @Test
    public void playValidMoveEmptyCapture() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        kalahService.saveGame(new KalahGameEntity(1L, KalahGamePlayer.SOUTH,
                Arrays.asList(3, 1, 11, 0, 10, 10, 3, 10, 0, 2, 9, 9, 1, 3)));
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/games/1/pits/1", HttpMethod.PUT, entity, String.class);
        String kalahGame = response.getBody();
        Assert.assertEquals("Status OK", 200, response.getStatusCode().value());
        Assert.assertNotNull(kalahGame);
        Assert.assertEquals("kalahGame String", "{\"id\":\"1\",\"url\":\"" + buildURL(1) + "\",\"status\":[0,2,12,0,10,10,6,10,0,0,9,9,1,3]}", kalahGame);

        ResponseEntity<GeneralError> errorResponse = restTemplate.exchange(getRootUrl() + "/games/1/pits/3", HttpMethod.PUT, entity, GeneralError.class);
        GeneralError error = errorResponse.getBody();
        Assert.assertEquals("Bad Reqest", 400, errorResponse.getStatusCode().value());
        Assert.assertNotNull(error);
        Assert.assertEquals("Kalah Game Error", "Player cannot play opponents pit.", error.getMessage());
    }

    @Test
    public void playValidMoveEmptyNoCapture() {
        kalahService.saveGame(new KalahGameEntity(1L, KalahGamePlayer.NORTH,
                Arrays.asList(3, 0, 8, 8, 0, 0, 9, 1, 0, 10, 10, 10, 10, 2)));

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/games/1/pits/8", HttpMethod.PUT, entity, String.class);
        String kalahGame = response.getBody();
        Assert.assertEquals("Status OK", 200, response.getStatusCode().value());
        Assert.assertNotNull(kalahGame);
        Assert.assertEquals("kalahGame String", "{\"id\":\"1\",\"url\":\"" + buildURL(1) + "\",\"status\":[3,0,8,8,0,0,9,0,1,10,10,10,10,2]}", kalahGame);

        ResponseEntity<GeneralError> errorResponse = restTemplate.exchange(getRootUrl() + "/games/1/pits/13", HttpMethod.PUT, entity, GeneralError.class);
        GeneralError error = errorResponse.getBody();
        Assert.assertEquals("Bad Reqest", 400, errorResponse.getStatusCode().value());
        Assert.assertNotNull(error);
        Assert.assertEquals("Kalah Game Error", "Player cannot play opponents pit.", error.getMessage());
    }

    @NotNull
    private String buildURL(int id) {
        return getRootUrl() + "/games/" + id;
    }

    @Test
    public void playInvalidMoveGameFinished() {
        kalahService.saveGame(new KalahGameEntity(1L, null,
                Arrays.asList(0, 0, 0, 0, 0, 0, 42, 0, 0, 0, 0, 0, 0, 30)));

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        //Init Arrays.asList(0, 0, 0, 0, 0, 0, 42, 0, 0, 0, 0, 0, 0, 30) //over
        ResponseEntity<GeneralError> errorResponse = restTemplate.exchange(getRootUrl() + "/games/1/pits/3", HttpMethod.PUT, entity, GeneralError.class);
        GeneralError error = errorResponse.getBody();
        Assert.assertEquals("Bad Reqest", 400, errorResponse.getStatusCode().value());
        Assert.assertNotNull(error);
        Assert.assertEquals("Kalah Game Error", "Game Over.", error.getMessage());
    }
}
