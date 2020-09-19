package masko.arundotest.kalah.service;

import masko.arundotest.kalah.model.InvalidMoveException;
import masko.arundotest.kalah.model.KalahGameEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("KalahService")
public class KalahServiceImpl implements KalahService {

    private final TreeMap<Long, KalahGameEntity> games = new TreeMap<>();

    @Override
    public Optional<KalahGameEntity> getGame(long id) {
        return Optional.ofNullable(games.get(id));
    }

    @Override
    public List<KalahGameEntity> getGames() {
        return new ArrayList<>(games.values());
    }

    @Override
    public KalahGameEntity createGame() {
        Long nextId = Optional.of(getLastId()).orElse(1L);
        KalahGameEntity kalahGame = new KalahGameEntity(nextId);
        games.put(nextId, kalahGame);
        return kalahGame;
    }

    private Long getLastId() {
        if (games.isEmpty())
            return 1L;
        else
            return games.lastKey() + 1;
    }

    @Override
    public KalahGameEntity playPit(long gameId, int pit) throws GameNotFoundException, InvalidMoveException {
        KalahGameEntity kalahGame = games.get(gameId);
        if (kalahGame == null)
            throw new GameNotFoundException();
        kalahGame.playPit(pit);
        return kalahGame;
    }

    @Override
    public KalahGameEntity saveGame(KalahGameEntity kalahGameEntity) {
        return games.put(kalahGameEntity.getId(), kalahGameEntity);
    }

    @Override
    public void clearGames() {
        games.clear();
    }
}
