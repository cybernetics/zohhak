package com.googlecode.zohhak.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;


import org.junit.AfterClass;
import org.junit.runner.RunWith;

import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;

@RunWith(ZohhakRunner.class)
public class NumberOfInvocationsTest {

	static List<Integer> passedParams = new ArrayList<Integer>(2);
	
	@TestWith({
		"13",
		"17"
	})
	public void simpleParams(int param) {
		passedParams.add(param);
	}
	
	@AfterClass public static void finalCheck() {
		assertThat(passedParams).containsOnly(17, 13).hasSize(2);
	}
}
