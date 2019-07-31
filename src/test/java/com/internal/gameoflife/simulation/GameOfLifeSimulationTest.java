package com.internal.gameoflife.simulation;

import static org.junit.Assert.assertArrayEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;

import com.internal.gameoflife.utils.TestsUtils;

public class GameOfLifeSimulationTest {

	@BeforeClass
	public static void loadProperties() throws FileNotFoundException, IOException {
		TestsUtils.loadTestsConfigurationProperties();
	}

	@Test
	public void updateCellsStateInGridTest() {
		int[][] initialGrid = {{1, 0, 1}, {0, 1, 0}, {1, 0, 1}};
		int[][] expectedGrid = {{1, 1, 1}, {1, 0, 1}, {1, 1, 1}};

		GameOfLifeSimulation gameOfLifeSimulation = new GameOfLifeSimulation(initialGrid, 1, 30f, 0l, false);
		gameOfLifeSimulation.updateCellsStateInGrid();
		int[][] resultingGrid = gameOfLifeSimulation.getGrid();

		assertArrayEquals(Arrays.stream(expectedGrid)
				.flatMapToInt(cellArray -> Arrays.stream(cellArray))
				.toArray(), Arrays.stream(resultingGrid)
				.flatMapToInt(cellArray -> Arrays.stream(cellArray))
				.toArray());
	}

}
