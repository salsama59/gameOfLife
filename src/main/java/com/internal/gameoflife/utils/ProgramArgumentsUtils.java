package com.internal.gameoflife.utils;

import com.internal.gameoflife.enums.ProgramArguments;

public class ProgramArgumentsUtils {

	public static final int MAX_ALOWED_ARGUMENTS = 5;

	public static String retrieveArgumentValue(String[] arguments, ProgramArguments programArguments){
		return arguments[programArguments.ordinal()];
	}

	public static boolean isArgumentValueValid(String argumentValue) {
		return argumentValue != null && !argumentValue.isEmpty();
	}

	public static boolean isArgumentIntegerValue(String argumentValue) {
		return argumentValue.matches("\\d+");
	}
	
	public static boolean isArgumentBooleanValue(String argumentValue) {
		return argumentValue.equalsIgnoreCase("true") || argumentValue.equalsIgnoreCase("false");
	}
	
	public static boolean isArgumentValueFloatingNumber(String argumentValue) {
		return argumentValue.matches("^[0-9]+([.][0-9]+)?$");
	}
	
	public static boolean hasNecessaryArguments(String [] programmArguments) {
		return programmArguments.length == ProgramArgumentsUtils.MAX_ALOWED_ARGUMENTS;
	}
}
