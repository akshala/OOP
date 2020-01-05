import org.junit.Test;

public class Test_CricketBiteException{
    @Test(expected = CricketBiteException.class)
    public void criketBiteException_test() throws CricketBiteException {
        Cricket cricket = new Cricket(0);
        cricket.bite();
    }
}
