package com.internal.gameoflife.simulation;

import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;

import org.junit.Test;

import com.internal.gameoflife.simulation.GameOfLifeSimulation;

public class GameOfLifeSimulationTest {

	@Test
	public void updateCellsStateInGridTest() {
		int[][] initialGrid = {{1, 0, 1}, {0, 1, 0}, {1, 0, 1}};
		int[][] expectedGrid = {{1, 1, 1}, {1, 0, 1}, {1, 1, 1}};

		GameOfLifeSimulation gameOfLifeSimulation = new GameOfLifeSimulation(initialGrid, 1, 30f);
		gameOfLifeSimulation.updateCellsStateInGrid();
		int[][] resultingGrid = gameOfLifeSimulation.getGrid();

		assertArrayEquals(Arrays.stream(expectedGrid)
				.flatMapToInt(cellArray -> Arrays.stream(cellArray))
				.toArray(), Arrays.stream(resultingGrid)
				.flatMapToInt(cellArray -> Arrays.stream(cellArray))
				.toArray());
	}

}
