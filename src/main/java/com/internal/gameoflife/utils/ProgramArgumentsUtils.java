package com.internal.gameoflife.utils;

import com.internal.gameoflife.enums.ProgramArguments;

/**
 * Class use to manage the programm arguments
 * @author syeponde
 */
public class ProgramArgumentsUtils {

	/**
	 * The maximum argument allowed.
	 */
	public static final int MAX_ALOWED_ARGUMENTS = 5;

	/**
	 * Retrieve an argument value thanks to the argument arrays passed as a parameter.
	 * @param arguments the argument array wich contain the desired arguments.
	 * @param programArguments the enum used to determinate the arguments order.
	 * @return the argument value as a string.
	 */
	public static String retrieveArgumentValue(String[] arguments, ProgramArguments programArguments){
		return arguments[programArguments.ordinal()];
	}

	/**
	 * Verify the argument value validity passed as a parameter
	 * @param argumentValue the argument value to verify
	 * @return true if the argument is nor null or empty return false otherwise.
	 */
	public static boolean isArgumentValueValid(String argumentValue) {
		return argumentValue != null && !argumentValue.isEmpty();
	}

	/**
	 * Verify if an argument is an integer value.
	 * @param argumentValue the argument value to verify
	 * @return true if the argument is a numeric integer value return false otherwise.
	 */
	public static boolean isArgumentIntegerValue(String argumentValue) {
		return argumentValue.matches("\\d+");
	}

	/**
	 * Verify if an argument is a boolean value.
	 * @param argumentValue the argument value to verify
	 * @return true if the argument is a boolean value return false otherwise.
	 */
	public static boolean isArgumentBooleanValue(String argumentValue) {
		return argumentValue.equalsIgnoreCase("true") || argumentValue.equalsIgnoreCase("false");
	}

	/**
	 * Verify if an argument is a numeric floating number value.
	 * @param argumentValue the argument value to verify
	 * @return true if the argument is a floating number value return false otherwise.
	 */
	public static boolean isArgumentValueFloatingNumber(String argumentValue) {
		return argumentValue.matches("^[0-9]+([.][0-9]+)?$");
	}

	/**
	 * Verify if the argument array contains enough argument
	 * @param programmArguments the argument array to verify
	 * @return true if the argument array length is equal the maximum allowed value, and false otherwise.
	 */
	public static boolean hasNecessaryArguments(String [] programmArguments) {
		return programmArguments.length == ProgramArgumentsUtils.MAX_ALOWED_ARGUMENTS;
	}
}
