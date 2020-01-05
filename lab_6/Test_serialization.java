import org.junit.Test;
import java.io.IOException;
import static org.junit.Assert.assertEquals;

public class Test_serialization {
    @Test
    public void serialization_test() throws IOException, ClassNotFoundException {
        Game game = new Game(10);
        game.setPlayer_name(" ");
        game.save();
        Game loadedGame = lab6.load(" ");
        assertEquals("fail", game.getPlayer_name(), loadedGame.getPlayer_name());
        assertEquals("fail", game.getSnake_throw(), loadedGame.getSnake_throw());
        assertEquals("fail", game.getVulture_throw(), loadedGame.getVulture_throw());
        assertEquals("fail", game.getCricket_throw(), loadedGame.getCricket_throw());
        assertEquals("fail", game.getTrampoline_throw(), loadedGame.getTrampoline_throw());
        assertEquals("fail", game.getNum_snakes(), loadedGame.getNum_snakes());
        assertEquals("fail", game.getNum_vultures(), loadedGame.getNum_vultures());
        assertEquals("fail", game.getNum_crickets(), loadedGame.getNum_crickets());
        assertEquals("fail", game.getNum_trampolines(), loadedGame.getNum_trampolines());
    }
}
