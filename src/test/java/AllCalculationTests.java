import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CalcualationAddTest.class, CalculationDivideTest.class, CalculationMultiplyTest.class,
		CalculationSubtractTest.class })
public class AllCalculationTests {

}
