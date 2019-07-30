package com.internal.gameoflife;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.internal.gameoflife.constants.PropertyKeyConstants;
import com.internal.gameoflife.dto.SimulationParameters;
import com.internal.gameoflife.enums.GridCellState;
import com.internal.gameoflife.utils.GridUtils;
import com.internal.gameoflife.utils.TestsUtils;

public class GameOfLifeTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@BeforeClass
	public static void loadProperties() throws FileNotFoundException, IOException {
		TestsUtils.loadTestsConfigurationProperties();
	}

	@Test
	public void initializeGridCellsValueTest() {

		int rowLength = 5;
		int columnLength = 5;
		int initialActivatedCellNumber = 20;

		int[][] grid = new int[rowLength][columnLength];

		grid = GameOfLife.initializeGridCellsValue(grid, initialActivatedCellNumber);

		IntStream gridStream = Arrays.stream(grid)
				.flatMapToInt(cellArray -> Arrays.stream(cellArray))
				.filter(cellValue -> cellValue == GridCellState.ALIVE.ordinal());

		assertEquals(initialActivatedCellNumber, gridStream.toArray().length);
	}

	@Test
	public void initializeGridLengthTest() {
		String rowLength = "10";
		String columnLength = "10";
		String[] args = {rowLength, columnLength, "10", "30"};
		int[][] grid = GameOfLife.initializeGridLength(args, null);
		assertEquals(Integer.parseInt(rowLength), GridUtils.getGridRowLenth(grid));
		assertEquals(Integer.parseInt(columnLength), GridUtils.getGridColumnLenth(grid));
	}

	@Test
	public void initializeGridLengthWithLoadedValueTest() {
		String programmArgumentsRowLength = "10";
		String programmArgumentsColumnLength = "10";
		int loadedRowLength = 20;
		int loadedColumnLength = 20;
		String[] args = {programmArgumentsRowLength, programmArgumentsColumnLength, "10", "30"};
		SimulationParameters loadedSimulationParameters = new SimulationParameters();
		loadedSimulationParameters.setColumnLength(loadedColumnLength);
		loadedSimulationParameters.setRowLength(loadedRowLength);
		int[][] grid = GameOfLife.initializeGridLength(args, loadedSimulationParameters);
		assertNotEquals(Integer.parseInt(programmArgumentsRowLength), GridUtils.getGridRowLenth(grid));
		assertNotEquals(Integer.parseInt(programmArgumentsColumnLength), GridUtils.getGridColumnLenth(grid));
		assertEquals(loadedSimulationParameters.getRowLength(), GridUtils.getGridRowLenth(grid));
		assertEquals(loadedSimulationParameters.getColumnLength(), GridUtils.getGridColumnLenth(grid));
	}

	@Test
	public void initializeGridRowLengthWithoutNumericValueTest() {
		this.expectExceptionForRowValue();
		String[] args = {"a", "10", "15", "30"};
		GameOfLife.initializeGridLength(args, null);
	}

	@Test
	public void initializeGridRowLengthWithInvalidValueTest() {
		this.expectExceptionForRowValue();
		String[] args = {"", "10", "15", "30"};
		GameOfLife.initializeGridLength(args, null);
	}

	@Test
	public void initializeGridColumnLengthWithoutNumericValueTest() {
		this.expectExceptionForColumnValue();
		String[] args = {"10", "f", "10", "30"};
		GameOfLife.initializeGridLength(args, null);
	}

	@Test
	public void initializeGridColumnLengthWithInvalidValueTest() {
		this.expectExceptionForColumnValue();
		String[] args = {"10", "", "15", "30"};
		GameOfLife.initializeGridLength(args, null);
	}

	@Test
	public void initializeGridLengthWithoutSufficientArgumentTest() {
		String[] args = {"5"};
		int[][] initializedGrid = GameOfLife.initializeGridLength(args, null);
		assertEquals(Integer.parseInt(GameOfLife.applicationProperties.getProperty(PropertyKeyConstants.GRID_COLUMN_LENGTH_KEY))
				, GridUtils.getGridColumnLenth(initializedGrid));
		assertEquals(Integer.parseInt(GameOfLife.applicationProperties.getProperty(PropertyKeyConstants.GRID_ROW_LENGTH_KEY))
				, GridUtils.getGridRowLenth(initializedGrid));
	}

	private void expectExceptionForRowValue() {
		exception.expect(RuntimeException.class);
		exception.expectMessage("The grid row lenght argument is mandatory and must be an integer value");
	}

	private void expectExceptionForColumnValue() {
		exception.expect(RuntimeException.class);
		exception.expectMessage("The grid column lenght argument is mandatory and must be an integer value");
	}
}
