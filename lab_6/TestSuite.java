import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        Test_GameWinnerException.class,
        Test_SnakeBiteException.class,
        Test_VultureBiteException.class,
        Test_CricketBiteException.class,
        Test_TrampolineBiteException.class,
        Test_serialization.class
})
public class TestSuite{ }
