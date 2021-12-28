import static org.junit.Assert.*;

import org.junit.Test;

import com.calculator.test.CalculateController;

public class CalcualationAddTest {

	@Test
	public void Addtest() {
		CalculateController cc = new CalculateController();
		double actual_result = 15.5 + 25.5;
		double test_result = cc.add(15.5, 25.5);
		assertEquals(actual_result, test_result, 0.001);
	}
}
