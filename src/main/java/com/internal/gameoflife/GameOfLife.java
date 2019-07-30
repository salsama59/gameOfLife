package com.internal.gameoflife;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import com.internal.gameoflife.constants.PropertyKeyConstants;
import com.internal.gameoflife.data.DataManager;
import com.internal.gameoflife.dto.SimulationParameters;
import com.internal.gameoflife.enums.GridCellState;
import com.internal.gameoflife.enums.ProgramArguments;
import com.internal.gameoflife.simulation.GameOfLifeSimulation;
import com.internal.gameoflife.utils.GridUtils;
import com.internal.gameoflife.utils.ProgramArgumentsUtils;

public class GameOfLife {
	
	static Properties applicationProperties;

	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		String appConfigPath = rootPath + "application.properties";
		applicationProperties = new Properties();
		applicationProperties.load(new FileInputStream(appConfigPath));	

		SimulationParameters loadedSimulationParameters = null;
		int[][] loadedGrid = null;
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
				loadedGrid = dataManager.getLoadedSimulationGrid();
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

		if(loadedGrid == null && ProgramArgumentsUtils.hasNecessaryArguments(args)) {
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
		else if(loadedGrid != null) {
			grid = loadedGrid;
			initialActivatedCellPercentage = loadedSimulationParameters.getInitialActivatedCellPercentage();
			refreshRate = loadedSimulationParameters.getRefreshRate();
			simulationIteration = loadedSimulationParameters.getSimulationIteration();
		}
		else {
			initialActivatedCellPercentage = Float.parseFloat(applicationProperties.getProperty(PropertyKeyConstants.ACTIVATED_CELL_PERCENTAGE_KEY));
			refreshRate = Integer.parseInt(applicationProperties.getProperty(PropertyKeyConstants.REFRESH_RATE_KEY));
			int cellTotalNumber = GridUtils.getGridRowLenth(grid) * GridUtils.getGridColumnLenth(grid);
			int initialActiveCellNumber = (int) Math.floor(initialActivatedCellPercentage * cellTotalNumber/100);
			grid = initializeGridCellsValue(grid, initialActiveCellNumber);
		}

		GameOfLifeSimulation gameOfLifeSimulation = new GameOfLifeSimulation(grid, refreshRate, initialActivatedCellPercentage, simulationIteration);
		gameOfLifeSimulation.start();
	}

	public static int[][] initializeGridLength(String[] programmArguments, SimulationParameters loadedSimulationParameters) {
		int rowLength = 0;
		int columnLength = 0;
		if(loadedSimulationParameters == null && ProgramArgumentsUtils.hasNecessaryArguments(programmArguments)) {
			
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
		else if(loadedSimulationParameters != null){
			columnLength = loadedSimulationParameters.getColumnLength();
			rowLength = loadedSimulationParameters.getRowLength();
		}
		else {
			columnLength = Integer.parseInt(applicationProperties.getProperty(PropertyKeyConstants.GRID_COLUMN_LENGTH_KEY));
			rowLength = Integer.parseInt(applicationProperties.getProperty(PropertyKeyConstants.GRID_ROW_LENGTH_KEY));
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
