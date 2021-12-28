package com.calculator.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CalculateController {
	
	@RequestMapping(value = "calculate", method = RequestMethod.GET)
	public ModelAndView calculate(HttpServletRequest request, HttpServletResponse response) {
		String inp1 = request.getParameter("t1");
		String inp2 = request.getParameter("t2");
		String operation = request.getParameter("operation");
		
		double a = Double.parseDouble(inp1);
		double b = Double.parseDouble(inp2);
		double result = performOperation(a, b, operation);
		
		ModelAndView model=new ModelAndView();
		
		// result is the name of the page in which the result is displayed
		model.setViewName("result");
		model.addObject("a", inp1);
		model.addObject("b", inp2);
		model.addObject("operation", operation);
		model.addObject("answer", result);
		return model;
		
	}
	
	public double add(double a, double b) {
		return a + b;
	}
	
	public double subtract(double a, double b) {
		return a - b;
	}

	public double multiply(double a, double b) {
		return a * b;
	}
	
	public double divide(double a, double b) {
		return a / b;
	}
	
	public boolean checkOperation(String operation) {
		if (operation.equals("+") || operation.equals("-") || operation.equals("*") || operation.equals("/")) {
			return true;
		}
		else
			return false;
	}

	public double performOperation(double a, double b, String operation) {
		double result = 0.0;
		
		if (checkOperation(operation)) {
			if (operation.equals("+"))
				result = add(a, b);
			else if (operation.equals("-"))
				result = subtract(a, b);
			else if (operation.equals("*"))
				result = multiply(a, b);
			else if (operation.equals("/"))
				result = divide(a, b);
		}
		
		return result;
	}
}
