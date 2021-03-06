package com.googlecode.zohhak.internal.coercing;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;


import org.junit.Test;
import org.junit.runner.RunWith;

import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;
import com.googlecode.zohhak.helper.CoercionCandidates;
import com.googlecode.zohhak.internal.coercing.CoercingService;
import com.googlecode.zohhak.internal.coercing.Coercion;
import com.googlecode.zohhak.internal.coercing.CoercionHandler;

@RunWith(ZohhakRunner.class)
public class CoercingServiceTest {

	CoercingService coercingService = new CoercingService();
	List<Coercion> noCoercions = Collections.emptyList();
	CoercionHandler coercionFinder = new CoercionHandler();
	
	@Test(expected=IllegalArgumentException.class)
	public void notSupportedType() {
		coercingService.coerceParameter(Thread.class, "", noCoercions);
	}

	@Test(expected=IllegalArgumentException.class)
	public void notClass() throws Exception {
		Type nonClassType = getNonClassType(); 
		coercingService.coerceParameter(nonClassType, "", noCoercions);
	}
	
	private Type getNonClassType() throws Exception {
		return getClass().getDeclaredMethod("methodWithNonClassTypeParameter", Object.class).getGenericParameterTypes()[0];
	}
	
	<T> void methodWithNonClassTypeParameter(T parameter) {}

	@TestWith({
		"voidReturn",
		"noParams",
		"tooManyParams",
		"wrongParamType"
	})
	public void invalidCoercionMethod(String name) {
		Method method = getMethod(name);
		assertFalse(coercionFinder.isValidCoercionMethod(method));
	}
	
	@Test
	public void validCoercionMehod() {
		Method method = getMethod("validCoercion");
		assertTrue(coercionFinder.isValidCoercionMethod(method));
	}
	
	private Method getMethod(String name) {
		Method[] methods = CoercionCandidates.class.getMethods();
		for (Method method : methods) {
			if (method.getName().equals(name)) {
				return method;
			}
		}
		throw new IllegalArgumentException("no method named "+name);
	}
	
}
