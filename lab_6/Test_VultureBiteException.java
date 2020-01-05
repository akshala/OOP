import org.junit.Test;

public class Test_VultureBiteException{
    @Test(expected = VultureBiteException.class)
    public void vultureBiteException_test() throws VultureBiteException {
        Vulture vulture = new Vulture(0);
        vulture.bite();
    }
}
