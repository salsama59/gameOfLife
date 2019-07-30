package com.internal.gameoflife.simulation;

import static org.junit.Assert.assertArrayEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import org.junit.BeforeClass;
import org.junit.Test;

import com.internal.gameoflife.GameOfLife;

public class GameOfLifeSimulationTest {
	
	@BeforeClass
	public static void loadProperties() throws FileNotFoundException, IOException {
		String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		String appConfigPath = rootPath + "application-test.properties";
		GameOfLife.applicationProperties = new Properties();
		GameOfLife.applicationProperties.load(new FileInputStream(appConfigPath));	
	}

	@Test
	public void updateCellsStateInGridTest() {
		int[][] initialGrid = {{1, 0, 1}, {0, 1, 0}, {1, 0, 1}};
		int[][] expectedGrid = {{1, 1, 1}, {1, 0, 1}, {1, 1, 1}};

		GameOfLifeSimulation gameOfLifeSimulation = new GameOfLifeSimulation(initialGrid, 1, 30f, 0l);
		gameOfLifeSimulation.updateCellsStateInGrid();
		int[][] resultingGrid = gameOfLifeSimulation.getGrid();

		assertArrayEquals(Arrays.stream(expectedGrid)
				.flatMapToInt(cellArray -> Arrays.stream(cellArray))
				.toArray(), Arrays.stream(resultingGrid)
				.flatMapToInt(cellArray -> Arrays.stream(cellArray))
				.toArray());
	}

}
