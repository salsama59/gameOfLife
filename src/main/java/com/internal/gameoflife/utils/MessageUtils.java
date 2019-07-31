package com.internal.gameoflife.utils;

import java.text.MessageFormat;

import com.internal.gameoflife.GameOfLife;

/**
 * Utility class for get message formated
 * @author syeponde
 */
public class MessageUtils {

	/**
	 * Retrieve a mesage from a property file by replacing it placeholders by the values passed as parameter
	 * @param messageKey the property key of the message
	 * @param values the value to replace the placeholders
	 * @return the message formated depending of the message key.
	 */
	public static String getFormatedMessage(String messageKey, Object... values) {
		String pattern = GameOfLife.resourceBundle.getString(messageKey);
		String message = MessageFormat.format(pattern, values);
		return message;
	}
}
