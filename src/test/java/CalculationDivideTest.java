import static org.junit.Assert.*;

import org.junit.Test;

import com.calculator.test.CalculateController;

public class CalculationDivideTest {

	@Test
	public void Divtest() {
		CalculateController cc = new CalculateController();
		double actual_result = (double) 25.0 / 4.0;
		double test_result = cc.divide(25.0, 4.0);
		assertEquals(actual_result, test_result, 0.001);
	}

}
