import org.junit.Test;

public class Test_SnakeBiteException{
    @Test(expected = SnakeBiteException.class)
    public void snakeBiteException_test() throws SnakeBiteException {
        Snake snake = new Snake(0);
        snake.bite();
    }
}
