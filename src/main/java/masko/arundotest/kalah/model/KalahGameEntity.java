package masko.arundotest.kalah.model;

import org.apache.commons.collections4.ListUtils;

import java.util.Arrays;
import java.util.List;

public class KalahGameEntity {

    private static final int PITS = 6;
    private static final int SEEDS_PER_PIT = 6;
    private static final int SOUTH_KALAH = PITS;
    private static final int NORTH_KALAH = (2 * PITS) + 1;
    private final long id;
    private KalahGamePlayer currentPlayer;
    private final List<Integer> status;

    /**
     * Creates a new game
     * @param id New game id
     */
    public KalahGameEntity(long id) {
        this.id = id;
        currentPlayer = null;
        status = Arrays.asList(SEEDS_PER_PIT, SEEDS_PER_PIT, SEEDS_PER_PIT, SEEDS_PER_PIT, SEEDS_PER_PIT, SEEDS_PER_PIT,
                0, SEEDS_PER_PIT, SEEDS_PER_PIT, SEEDS_PER_PIT, SEEDS_PER_PIT, SEEDS_PER_PIT, SEEDS_PER_PIT, 0);
    }

    public KalahGameEntity(long id, KalahGamePlayer currentPlayer, List<Integer> status) {
        this.id = id;
        this.currentPlayer = currentPlayer;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public KalahGamePlayer getCurrentPlayer() {
        return currentPlayer;
    }

    public List<Integer> getStatus() {
        return ListUtils.unmodifiableList(status);
    }

    /**
     * Check if the specified pit is a Kalah
     * @param pitIndex the pit to check
     * @return true if the specified pit is a Kalah
     */
    private boolean isKalahPit(int pitIndex) {
        return pitIndex == SOUTH_KALAH || pitIndex == NORTH_KALAH;
    }

    /**
     * Play a move
     * @param pit the pit to play
     * @throws InvalidMoveException An invalid move was made
     */
    public void playPit(int pit) throws InvalidMoveException {
        if (gameOver())
            throw new InvalidMoveException("Game Over.");

        int pitIndex = pit - 1;
        if (isKalahPit(pitIndex))
            throw new InvalidMoveException("Cannot play the Kalah pit.");

        if (pitIndex < 0 || pitIndex > NORTH_KALAH)
            throw new InvalidMoveException("Invalid pit specified.");

        Integer pitValue = status.get(pitIndex);
        if (pitValue == 0)
            throw new InvalidMoveException("Cannot play an empty pit.");

        if (currentPlayer != null &&
                ((currentPlayer == KalahGamePlayer.SOUTH && pitIndex > SOUTH_KALAH) ||
                 (currentPlayer == KalahGamePlayer.NORTH && pitIndex <= SOUTH_KALAH)))
            throw new InvalidMoveException("Player cannot play opponents pit.");
        if (currentPlayer == null)
            currentPlayer = (pitIndex < SOUTH_KALAH) ? KalahGamePlayer.SOUTH : KalahGamePlayer.NORTH;

        status.set(pitIndex, 0);

        int i = 1;
        int updatePit = incrementPit(pitIndex);
        while (i <= pitValue) {
            if ((currentPlayer == KalahGamePlayer.SOUTH && updatePit != NORTH_KALAH) ||
                (currentPlayer == KalahGamePlayer.NORTH && updatePit != SOUTH_KALAH)) {
                status.set(updatePit, status.get(updatePit) + 1);
                if (i == pitValue) {
                    if (!isKalahPit(updatePit)) {
                        checkAndProcessCapture(updatePit);
                        currentPlayer = (currentPlayer == KalahGamePlayer.SOUTH) ? KalahGamePlayer.NORTH :
                                KalahGamePlayer.SOUTH;
                    }
                    checkAndUpdateGameOver();
                }
                i++;
            }
            updatePit = incrementPit(updatePit);
        }
    }

    /**
     * Check for a capture
     * @param updatePit The last pit to be updated this round
     */
    private void checkAndProcessCapture(int updatePit) {
        int oppPit = NORTH_KALAH - updatePit - 1;
        if (status.get(updatePit) == 1 && status.get(oppPit) != 0) {
            if (currentPlayer == KalahGamePlayer.SOUTH)
                status.set(SOUTH_KALAH, status.get(SOUTH_KALAH) + status.get(updatePit) + status.get(oppPit));
            else
                status.set(NORTH_KALAH, status.get(NORTH_KALAH) + status.get(updatePit) + status.get(oppPit));
            status.set(updatePit, 0);
            status.set(oppPit, 0);
        }
    }

    /**
     * If all seeds are empty on one players side then finish the game
     */
    private void checkAndUpdateGameOver() {
        if (status.subList(0, SOUTH_KALAH).stream().reduce(0, Integer::sum) == 0) {
            moveRemainingSeeds(SOUTH_KALAH, NORTH_KALAH);
        } else if (status.subList(0, NORTH_KALAH).stream().reduce(0, Integer::sum) == 0) {
            moveRemainingSeeds(0, SOUTH_KALAH);
        }
        if (gameOver())
            currentPlayer = null;
    }

    /**
     * Move all seeds from pits to Kalah for one player
     * @param fromPit start pit
     * @param toPit end pit
     */
    private void moveRemainingSeeds(int fromPit, int toPit) {
        for (int j = fromPit + 1; j < toPit; j++) {
            status.set(toPit, status.get(toPit) + status.get(j));
            status.set(j, 0);
        }
    }

    private int incrementPit(int updatePit) {
        return (updatePit + 1) % (NORTH_KALAH);
    }

    public boolean gameOver() {
        return status.get(SOUTH_KALAH) + status.get(NORTH_KALAH) == SEEDS_PER_PIT * PITS * 2;
    }
}
