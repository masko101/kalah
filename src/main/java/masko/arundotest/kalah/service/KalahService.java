package masko.arundotest.kalah.service;

import masko.arundotest.kalah.model.InvalidMoveException;
import masko.arundotest.kalah.model.KalahGameEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface KalahService {

    List<KalahGameEntity> getGames();
    Optional<KalahGameEntity> getGame(long id);
    KalahGameEntity createGame();
    KalahGameEntity playPit(long gameId, int pit) throws GameNotFoundException, InvalidMoveException;
    KalahGameEntity saveGame(KalahGameEntity kalahGameEntity);
    void clearGames();
}
