package com.internal.gameoflife.utils;

import com.internal.gameoflife.enums.ProgramArguments;

public class ProgramArgumentsUtils {

	public static final int MAX_ALOWED_ARGUMENTS = 4;

	public static String retrieveArgumentValue(String[] arguments, ProgramArguments programArguments){
		return arguments[programArguments.ordinal()];
	}

	public static boolean isArgumentValueValid(String argumentValue) {
		return argumentValue != null && !argumentValue.isEmpty();
	}

	public static boolean isArgumentIntegerValue(String argumentValue) {
		return argumentValue.matches("\\d+");
	}
	
	public static boolean isArgumentValueFloatingNumber(String argumentValue) {
		return argumentValue.matches("^[0-9]+([.][0-9]+)?$");
	}
}
