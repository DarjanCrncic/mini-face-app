package com.miniface.utils;

import java.util.ArrayList;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

public class InputValidator {
	
	private Validator validator;

	public InputValidator() {
		this.validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	public <T> String[] validateInput(T object) {
		
		Set<ConstraintViolation<T>> violations = validator.validate(object);
		ArrayList<String> list = new ArrayList<>();
		
		for (ConstraintViolation<T> violation : violations) {
			list.add(violation.getMessage());
		}
		
		return list.toArray(new String[violations.size()]);	
	}
}
