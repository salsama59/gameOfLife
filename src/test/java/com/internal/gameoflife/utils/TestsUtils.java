package com.internal.gameoflife.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.internal.gameoflife.GameOfLife;

public class TestsUtils {
	
	public static void loadTestsConfigurationProperties() throws IOException, FileNotFoundException {
		String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		String appConfigPath = rootPath + "application-test.properties";
		GameOfLife.applicationProperties = new Properties();
		GameOfLife.applicationProperties.load(new FileInputStream(appConfigPath));
	}

}
