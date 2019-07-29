package com.internal.gameoflife;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.stream.IntStream;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.internal.gameoflife.enums.GridCellState;
import com.internal.gameoflife.utils.GridUtils;

public class GameOfLifeTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

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
		int[][] grid = GameOfLife.initializeGridLength(args);
		assertEquals(Integer.parseInt(rowLength), GridUtils.getGridRowLenth(grid));
		assertEquals(Integer.parseInt(columnLength), GridUtils.getGridColumnLenth(grid));
	}

	@Test
	public void initializeGridRowLengthWithoutNumericValueTest() {
		this.expectExceptionForRowValue();
		String[] args = {"a", "10", "15", "30"};
		GameOfLife.initializeGridLength(args);
	}

	@Test
	public void initializeGridRowLengthWithInvalidValueTest() {
		this.expectExceptionForRowValue();
		String[] args = {"", "10", "15", "30"};
		GameOfLife.initializeGridLength(args);
	}

	@Test
	public void initializeGridColumnLengthWithoutNumericValueTest() {
		this.expectExceptionForColumnValue();
		String[] args = {"10", "f", "10", "30"};
		GameOfLife.initializeGridLength(args);
	}

	@Test
	public void initializeGridColumnLengthWithInvalidValueTest() {
		this.expectExceptionForColumnValue();
		String[] args = {"10", "", "15", "30"};
		GameOfLife.initializeGridLength(args);
	}

	@Test
	public void initializeGridLengthWithoutSufficientArgumentTest() {
		exception.expect(RuntimeException.class);
		exception.expectMessage("The grid row lenght and the grid column length arguments are mandatory");
		String[] args = {"5"};
		GameOfLife.initializeGridLength(args);
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
