package com.internal.gameoflife;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.internal.gameoflife.data.DataManager;
import com.internal.gameoflife.dto.SimulationParameters;
import com.internal.gameoflife.enums.GridCellState;
import com.internal.gameoflife.enums.ProgramArguments;
import com.internal.gameoflife.simulation.GameOfLifeSimulation;
import com.internal.gameoflife.utils.GridUtils;
import com.internal.gameoflife.utils.ProgramArgumentsUtils;

public class GameOfLife {

	public static void main(String[] args) {

		SimulationParameters loadedSimulationParameters = null;
		int[][] loadeGrid = null;
		DataManager dataManager = new DataManager();

		if(dataManager.isDataFilesExisting()) {
			System.out.println("It seems that olds simulation datas files exists, do you want to load them ?");
			System.out.println("Y -> yes");
			System.out.println("N -> no");
			Scanner scanner = new Scanner(System.in);
			String line = scanner.nextLine();

			if(line.equalsIgnoreCase("y")) {
				dataManager = new DataManager();
				dataManager.loadDatas();
				loadedSimulationParameters = dataManager.getLoadedSimulationParameters();
				loadeGrid = dataManager.getLoadedSimulationGrid();
			}
			scanner.close();
		}

		int[][] grid = initializeGridLength(args, loadedSimulationParameters);

		if(!GridUtils.isGridValid(grid)) {
			return;
		}

		float initialActivatedCellPercentage = 0f;
		int refreshRate = 0;
		long simulationIteration = 0;

		if(loadeGrid == null ) {
			String stringInitialActivatedCellPercentage = ProgramArgumentsUtils.retrieveArgumentValue(args, ProgramArguments.INITIAL_LIVING_CELL_PERCENTAGE);
			if(ProgramArgumentsUtils.isArgumentValueValid(stringInitialActivatedCellPercentage) 
					&& ProgramArgumentsUtils.isArgumentValueFloatingNumber(stringInitialActivatedCellPercentage)) {
				initialActivatedCellPercentage = Float.parseFloat(stringInitialActivatedCellPercentage);
			}
			else {
				throw new RuntimeException("The intial living cell percentage argument is mandatory and must be a number");
			}

			String stringRefreshRate = ProgramArgumentsUtils.retrieveArgumentValue(args, ProgramArguments.REFRESH_RATE);
			if(ProgramArgumentsUtils.isArgumentValueValid(stringRefreshRate) 
					&& ProgramArgumentsUtils.isArgumentIntegerValue(stringRefreshRate)) {
				refreshRate = Integer.parseInt(stringRefreshRate);
			}
			else {
				throw new RuntimeException("The refresh rate argument is mandatory and must be a number");
			}

			int cellTotalNumber = GridUtils.getGridRowLenth(grid) * GridUtils.getGridColumnLenth(grid);
			int initialActiveCellNumber = (int) Math.floor(initialActivatedCellPercentage * cellTotalNumber/100);
			grid = initializeGridCellsValue(grid, initialActiveCellNumber);
		}
		else {
			grid = loadeGrid;
			initialActivatedCellPercentage = loadedSimulationParameters.getInitialActivatedCellPercentage();
			refreshRate = loadedSimulationParameters.getRefreshRate();
			simulationIteration = loadedSimulationParameters.getSimulationIteration();
		}

		GameOfLifeSimulation gameOfLifeSimulation = new GameOfLifeSimulation(grid, refreshRate, initialActivatedCellPercentage, simulationIteration);
		gameOfLifeSimulation.start();
	}

	public static int[][] initializeGridLength(String[] programmArguments, SimulationParameters loadedSimulationParameters) {
		int rowLength = 0;
		int columnLength = 0;
		if(loadedSimulationParameters == null) {
			if(programmArguments.length < ProgramArgumentsUtils.MAX_ALOWED_ARGUMENTS) {
				throw new RuntimeException("The grid row lenght and the grid column length arguments are mandatory");
			}

			String stringRowLength = ProgramArgumentsUtils.retrieveArgumentValue(programmArguments, ProgramArguments.GRID_ROW_SIZE);
			if(ProgramArgumentsUtils.isArgumentValueValid(stringRowLength) && ProgramArgumentsUtils.isArgumentIntegerValue(stringRowLength)) {
				rowLength = Integer.valueOf(stringRowLength);
			}
			else {
				throw new RuntimeException("The grid row lenght argument is mandatory and must be an integer value");
			}


			String stringColumnLength = ProgramArgumentsUtils.retrieveArgumentValue(programmArguments, ProgramArguments.GRID_COLUMN_SIZE);
			if(ProgramArgumentsUtils.isArgumentValueValid(stringColumnLength) && ProgramArgumentsUtils.isArgumentIntegerValue(stringColumnLength)) {
				columnLength = Integer.valueOf(stringColumnLength);
			}
			else {
				throw new RuntimeException("The grid column lenght argument is mandatory and must be an integer value");
			}
		}
		else {
			columnLength = loadedSimulationParameters.getColumnLength();
			rowLength = loadedSimulationParameters.getRowLength();
		}

		int[][] grid = new int[rowLength][columnLength];
		return grid;
	} 

	public static int[][] initializeGridCellsValue(int[][] grid, int initialActivatedCellNumber) {

		int cellUpdatedCount = 0;
		Map<Integer, ArrayList<Integer>> cellCoordinateFilter = new HashMap<>();
		List<Integer> rowIndexes = GridUtils.generateGridRowIndexList(grid);
		List<Integer> columnIndexes = GridUtils.generateGridColumnIndexList(grid);
		Collections.shuffle(rowIndexes);
		Collections.shuffle(columnIndexes);

		while(cellUpdatedCount < initialActivatedCellNumber) {

			for(Integer rowIndex : rowIndexes) {
				for(Integer columnIndex : columnIndexes) {
					List<Integer> columnIndexFilter = cellCoordinateFilter.get(rowIndex);
					boolean isFilterExists = columnIndexFilter != null;

					if(isFilterExists && !columnIndexFilter.contains(columnIndex) || !isFilterExists) {
						grid[rowIndex][columnIndex] = GridCellState.ALIVE.ordinal();
						if(isFilterExists) {
							columnIndexFilter.add(columnIndex);
							cellCoordinateFilter.put(rowIndex, (ArrayList<Integer>) columnIndexFilter);
						}
						else {
							cellCoordinateFilter.put(rowIndex, new ArrayList<Integer>(Arrays.asList(columnIndex)));
						}

						cellUpdatedCount++;
					}

					if(cellUpdatedCount == initialActivatedCellNumber) {
						return grid;
					}
				}
			}

		}

		return grid;
	}
}
