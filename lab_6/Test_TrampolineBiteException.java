import org.junit.Test;

public class Test_TrampolineBiteException{
    @Test(expected = TrampolineException.class)
    public void trampolineBiteException_test() throws TrampolineException {
        Trampoline trampoline = new Trampoline(0);
        trampoline.bite();
    }
}
