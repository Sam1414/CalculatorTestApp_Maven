import static org.junit.Assert.*;

import org.junit.Test;

import com.calculator.test.CalculateController;

public class CalculationMultiplyTest {

	@Test
	public void Multest() {
		CalculateController cc = new CalculateController();
		double actual_result = 15.3 * 25.2;
		double test_result = cc.multiply(15.3, 25.2);
		assertEquals(actual_result, test_result, 0.001);
	}

}
