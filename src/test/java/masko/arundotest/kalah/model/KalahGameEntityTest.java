package masko.arundotest.kalah.model;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class KalahGameEntityTest {


    @Test
    void newGame() {
        KalahGameEntity kalahGame = new KalahGameEntity(1);
        Assert.assertEquals("KalahGame Id", 1, kalahGame.getId());
        Assert.assertNull("KalahGame Current Player", kalahGame.getCurrentPlayer());
        Assert.assertEquals("KalahGame Status", Arrays.asList(6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0),
                kalahGame.getStatus());
        Assert.assertFalse("KalahGame Not Game Over", kalahGame.gameOver());
    }

    @Nested
    @DisplayName("Play Game")
    class Play {


        @Test
        public void playInvalidMovePlayInvalidPit() {
            KalahGameEntity kalahGame = new KalahGameEntity(15, KalahGamePlayer.NORTH,
                    Arrays.asList(6, 6, 0, 7, 7, 7, 1, 7, 7, 6, 6, 6, 6, 0));
            Exception exception = Assert.assertThrows(InvalidMoveException.class, () -> kalahGame.playPit(17));
            Assert.assertEquals("Kalah Game Error", "Invalid pit specified.", exception.getMessage());
        }

        @Test
        public void playInvalidMovePlayKalahPit() {
            KalahGameEntity kalahGame = new KalahGameEntity(1, KalahGamePlayer.NORTH,
                    Arrays.asList(6, 6, 0, 7, 7, 7, 1, 7, 7, 6, 6, 6, 6, 0));
            Exception exception = Assert.assertThrows(InvalidMoveException.class, () -> kalahGame.playPit(14));
            Assert.assertEquals("Kalah Game Error", "Cannot play the Kalah pit.", exception.getMessage());
        }

        @Test
        public void playInvalidMovePlayEmptyPit() {
            KalahGameEntity kalahGame = new KalahGameEntity(1, KalahGamePlayer.SOUTH,
                    Arrays.asList(6, 6, 0, 7, 7, 7, 1, 7, 7, 6, 6, 6, 6, 0));
            Exception exception = Assert.assertThrows(InvalidMoveException.class, () -> kalahGame.playPit(3));
            Assert.assertEquals("Kalah Game Error", "Cannot play an empty pit.", exception.getMessage());
        }

        @Test
        public void playInvalidMovePlayOpponentPit() {
            KalahGameEntity kalahGame = new KalahGameEntity(1, KalahGamePlayer.SOUTH,
                    Arrays.asList(6, 6, 0, 7, 7, 7, 1, 7, 7, 6, 6, 6, 6, 0));
            Exception exception = Assert.assertThrows(InvalidMoveException.class, () -> kalahGame.playPit(9));
            Assert.assertEquals("KalahGame Error", "Player cannot play opponents pit.", exception.getMessage());
        }

        @Test
        public void playValidMoveEndTurn() throws InvalidMoveException {
            KalahGameEntity kalahGame = new KalahGameEntity(1, KalahGamePlayer.SOUTH, Arrays.asList(0, 7, 7, 7, 7, 7, 1, 6, 6, 6, 6, 6, 6, 0));
            kalahGame.playPit(3);
            Assert.assertEquals("KalahGame Id", 1, kalahGame.getId());
            Assert.assertEquals("KalahGame Current Player", KalahGamePlayer.NORTH, kalahGame.getCurrentPlayer());
            Assert.assertEquals("KalahGame Status", Arrays.asList(0, 7, 0, 8, 8, 8, 2, 7, 7, 7, 6, 6, 6, 0),
                    kalahGame.getStatus());
            Assert.assertFalse("KalahGame Not Game Over", kalahGame.gameOver());
        }

        @Test
        public void playValidMoveExtraTurn() throws InvalidMoveException {
            KalahGameEntity kalahGame = new KalahGameEntity(1);
            kalahGame.playPit(1);
            Assert.assertEquals("KalahGame Id", 1, kalahGame.getId());
            Assert.assertEquals("KalahGame Current Player", KalahGamePlayer.SOUTH, kalahGame.getCurrentPlayer());
            Assert.assertEquals("KalahGame Status", Arrays.asList(0, 7, 7, 7, 7, 7, 1, 6, 6, 6, 6, 6, 6, 0),
                    kalahGame.getStatus());
            Assert.assertFalse("KalahGame Not Game Over", kalahGame.gameOver());
        }

        @Test
        public void playValidMoveCapture() throws InvalidMoveException {
            KalahGameEntity kalahGame = new KalahGameEntity(1, KalahGamePlayer.SOUTH,
                    Arrays.asList(3, 1, 11, 0, 10, 10, 3, 10, 0, 2, 9, 9, 1, 3));
            kalahGame.playPit(1);
            Assert.assertEquals("KalahGame Id", 1, kalahGame.getId());
            Assert.assertEquals("KalahGame Current Player", KalahGamePlayer.NORTH, kalahGame.getCurrentPlayer());
            Assert.assertEquals("KalahGame Status", Arrays.asList(0, 2, 12, 0, 10, 10, 6, 10, 0, 0, 9, 9, 1, 3),
                    kalahGame.getStatus());
            Assert.assertFalse("KalahGame Not Game Over", kalahGame.gameOver());
        }

        @Test
        public void playValidMoveEmptyNoCapture() throws InvalidMoveException {
            KalahGameEntity kalahGame = new KalahGameEntity(1, KalahGamePlayer.NORTH,
                    Arrays.asList(3, 0, 8, 8, 0, 0, 9, 1, 0, 10, 10, 10, 10, 2));
            kalahGame.playPit(8);
            Assert.assertEquals("KalahGame Id", 1, kalahGame.getId());
            Assert.assertEquals("KalahGame Current Player", KalahGamePlayer.SOUTH, kalahGame.getCurrentPlayer());
            Assert.assertEquals("KalahGame Status", Arrays.asList(3, 0, 8, 8, 0, 0, 9, 0, 1, 10, 10, 10, 10, 2),
                    kalahGame.getStatus());
            Assert.assertFalse("KalahGame Not Game Over", kalahGame.gameOver());
        }

        @Test
        public void playLastMove() throws InvalidMoveException {
            KalahGameEntity kalahGame = new KalahGameEntity(1, KalahGamePlayer.SOUTH,
                    Arrays.asList(0, 0, 0, 0, 0, 1, 41, 0, 0, 0, 3, 2, 0, 25));
            kalahGame.playPit(6);
            Assert.assertEquals("KalahGame Id", 1, kalahGame.getId());
            Assert.assertNull("KalahGame Current Player", kalahGame.getCurrentPlayer());
            Assert.assertEquals("KalahGame Status", Arrays.asList(0, 0, 0, 0, 0, 0, 42, 0, 0, 0, 0, 0, 0, 30),
                    kalahGame.getStatus());
            Assert.assertTrue("KalahGame Game Over", kalahGame.gameOver());
        }

        @Test
        public void playInvalidMoveGameFinished() {
            KalahGameEntity kalahGame = new KalahGameEntity(1, null,
                    Arrays.asList(0, 0, 0, 0, 0, 0, 42, 0, 0, 0, 0, 0, 0, 30));
            Exception exception = Assert.assertThrows(InvalidMoveException.class, () -> kalahGame.playPit(1));
            Assert.assertEquals("Kalah Game Error", "Game Over.", exception.getMessage());
        }

    }

    @Test
    void gameOver() {
        KalahGameEntity kalahGame = new KalahGameEntity(1, null,
                Arrays.asList(0, 0, 0, 0, 0, 0, 42, 0, 0, 0, 0, 0, 0, 30));
        Assert.assertTrue("KalahGame Over", kalahGame.gameOver());
    }

    @Test
    void gameNotOver() {
        KalahGameEntity kalahGame = new KalahGameEntity(1, null,
                Arrays.asList(3, 0, 7, 0, 0, 0, 32, 0, 2, 1, 2, 0, 0, 25));
        Assert.assertFalse("KalahGame Not Over", kalahGame.gameOver());
    }
}