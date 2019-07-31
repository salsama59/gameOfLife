package com.internal.gameoflife.utils;

import java.text.MessageFormat;

import com.internal.gameoflife.GameOfLife;

public class MessageUtils {
	public static String getFormatedMessage(String messageKey, Object... values) {
		String pattern = GameOfLife.resourceBundle.getString(messageKey);
		String message = MessageFormat.format(pattern, values);
		return message;
	}
}
