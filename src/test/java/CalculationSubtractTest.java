import static org.junit.Assert.*;

import org.junit.Test;

import com.calculator.test.CalculateController;

public class CalculationSubtractTest {

	@Test
	public void Subtest() {
		CalculateController cc = new CalculateController();
		double actual_result = 15.4 - 25.9;
		double test_result = cc.subtract(15.4, 25.9);
		assertEquals(actual_result, test_result, 0.001);
	}

}
