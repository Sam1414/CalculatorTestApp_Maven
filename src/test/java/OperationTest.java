import static org.junit.Assert.*;

import org.junit.Test;

import com.calculator.test.CalculateController;

public class OperationTest {

	@Test
	public void test() {
		CalculateController cc = new CalculateController();
		double actual_result = (double) 32.4 / 2.3;
		double test_result = cc.performOperation(32.4, 2.3, "/");
		assertEquals(actual_result, test_result, 0.0001);
	}

}
