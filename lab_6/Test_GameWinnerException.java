import org.junit.Test;

public class Test_GameWinnerException{
    @Test(expected = GameWinnerException.class)
    public void gameWinnerException_test() throws GameWinnerException {
        Game game = new Game(10);
        game.win();
    }
}
