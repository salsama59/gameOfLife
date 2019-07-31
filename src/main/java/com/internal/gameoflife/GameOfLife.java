package com.internal.gameoflife;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Scanner;

import com.internal.gameoflife.constants.PropertyKeyConstants;
import com.internal.gameoflife.data.DataManager;
import com.internal.gameoflife.dto.SimulationParameters;
import com.internal.gameoflife.enums.GridCellState;
import com.internal.gameoflife.enums.ProgramArguments;
import com.internal.gameoflife.server.GameOfLifeTcpServer;
import com.internal.gameoflife.simulation.GameOfLifeSimulation;
import com.internal.gameoflife.utils.GridUtils;
import com.internal.gameoflife.utils.ProgramArgumentsUtils;

/**
 * The main class of the application
 * @author syeponde
 */
public class GameOfLife {

	public static Properties applicationProperties;
	public static ResourceBundle resourceBundle;

	public static void main(String[] args) throws FileNotFoundException, IOException {
		loadApplicationProperties();
		loadApplicationResourceBundle();
		SimulationParameters loadedSimulationParameters = null;
		int[][] loadedGrid = null;
		DataManager dataManager = new DataManager();

		if(dataManager.isDataFilesExisting()) {
			System.out.println(resourceBundle.getString(PropertyKeyConstants.APPLICATION_MESSAGE_DATA_EXISTING));
			System.out.println(resourceBundle.getString(PropertyKeyConstants.APPLICATION_MESSAGE_DATA_LOAD_YES));
			System.out.println(resourceBundle.getString(PropertyKeyConstants.APPLICATION_MESSAGE_DATA_LOAD_NO));
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
		boolean isTcpServerModeEnabled = false;

		if(loadedGrid == null && ProgramArgumentsUtils.hasNecessaryArguments(args)) {
			String stringInitialActivatedCellPercentage = ProgramArgumentsUtils.retrieveArgumentValue(args, ProgramArguments.INITIAL_LIVING_CELL_PERCENTAGE);
			if(ProgramArgumentsUtils.isArgumentValueValid(stringInitialActivatedCellPercentage) 
					&& ProgramArgumentsUtils.isArgumentValueFloatingNumber(stringInitialActivatedCellPercentage)) {
				initialActivatedCellPercentage = Float.parseFloat(stringInitialActivatedCellPercentage);
			}
			else {
				throw new RuntimeException(resourceBundle.getString(PropertyKeyConstants.APPLICATION_MESSAGE_CELL_PERCENTAGE_ARGUMENTS_MANDATORY));
			}

			String stringRefreshRate = ProgramArgumentsUtils.retrieveArgumentValue(args, ProgramArguments.REFRESH_RATE);
			if(ProgramArgumentsUtils.isArgumentValueValid(stringRefreshRate) 
					&& ProgramArgumentsUtils.isArgumentIntegerValue(stringRefreshRate)) {
				refreshRate = Integer.parseInt(stringRefreshRate);
			}
			else {
				throw new RuntimeException(resourceBundle.getString(PropertyKeyConstants.APPLICATION_MESSAGE_REFRESH_RATE_ARGUMENTS_MANDATORY));
			}

			String stringIsTcpServerModeEnabled = ProgramArgumentsUtils.retrieveArgumentValue(args, ProgramArguments.TCP_SERVER_MODE);
			if(ProgramArgumentsUtils.isArgumentValueValid(stringIsTcpServerModeEnabled) 
					&& ProgramArgumentsUtils.isArgumentBooleanValue(stringIsTcpServerModeEnabled)) {
				isTcpServerModeEnabled = Boolean.parseBoolean(stringRefreshRate);
			}
			else {
				throw new RuntimeException(resourceBundle.getString(PropertyKeyConstants.APPLICATION_MESSAGE_TCP_SERVER_ENABLED_ARGUMENTS_MANDATORY));
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
			isTcpServerModeEnabled = loadedSimulationParameters.isTcpServerModeEnabled();
		}
		else {
			initialActivatedCellPercentage = Float.parseFloat(applicationProperties.getProperty(PropertyKeyConstants.ACTIVATED_CELL_PERCENTAGE_KEY));
			refreshRate = Integer.parseInt(applicationProperties.getProperty(PropertyKeyConstants.REFRESH_RATE_KEY));
			int cellTotalNumber = GridUtils.getGridRowLenth(grid) * GridUtils.getGridColumnLenth(grid);
			int initialActiveCellNumber = (int) Math.floor(initialActivatedCellPercentage * cellTotalNumber/100);
			grid = initializeGridCellsValue(grid, initialActiveCellNumber);
			isTcpServerModeEnabled = Boolean.parseBoolean(applicationProperties.getProperty(PropertyKeyConstants.ACCESS_MODE_KEY));
		}

		GameOfLifeSimulation gameOfLifeSimulation = new GameOfLifeSimulation(grid, refreshRate, initialActivatedCellPercentage, simulationIteration, isTcpServerModeEnabled);
		gameOfLifeSimulation.start();
		if(isTcpServerModeEnabled) {
			new GameOfLifeTcpServer(gameOfLifeSimulation);
		}
	}

	/**
	 * Initialize the grid size either from arguments, application property value or loaded data file value.
	 * The priority goes as follow : 1st argument value is used, if there is no arguments the application property value will be used
	 * , however if the user as choosed to load the old simulation datas then it is those who will be used to populate the row length and the column length value.
	 * @param programmArguments the programm argument array containing the grid row and column size.
	 * @param loadedSimulationParameters the simulation parameters loaded or not
	 * @return the grid initialized with the rigth size.
	 */
	public static int[][] initializeGridLength(String[] programmArguments, SimulationParameters loadedSimulationParameters) {
		int rowLength = 0;
		int columnLength = 0;
		if(loadedSimulationParameters == null && ProgramArgumentsUtils.hasNecessaryArguments(programmArguments)) {

			String stringRowLength = ProgramArgumentsUtils.retrieveArgumentValue(programmArguments, ProgramArguments.GRID_ROW_SIZE);
			if(ProgramArgumentsUtils.isArgumentValueValid(stringRowLength) && ProgramArgumentsUtils.isArgumentIntegerValue(stringRowLength)) {
				rowLength = Integer.valueOf(stringRowLength);
			}
			else {
				throw new RuntimeException(resourceBundle.getString(PropertyKeyConstants.APPLICATION_MESSAGE_GRID_ROW_ARGUMENTS_MANDATORY));
			}


			String stringColumnLength = ProgramArgumentsUtils.retrieveArgumentValue(programmArguments, ProgramArguments.GRID_COLUMN_SIZE);
			if(ProgramArgumentsUtils.isArgumentValueValid(stringColumnLength) && ProgramArgumentsUtils.isArgumentIntegerValue(stringColumnLength)) {
				columnLength = Integer.valueOf(stringColumnLength);
			}
			else {
				throw new RuntimeException(resourceBundle.getString(PropertyKeyConstants.APPLICATION_MESSAGE_GRID_COLUMN_ARGUMENTS_MANDATORY));
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

	/**
	 * Initialize the grid cell values by randomly place activated cell define by the initialActivatedCellNumber parameter
	 * @param grid the grid to update with the initial values.
	 * @param initialActivatedCellNumber the number of desired alive cells in the grid
	 * @return the grid with initialize cells values, randomly chosen.
	 */
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

	/**
	 * Load the resource bundle necessary for the application message definition
	 */
	private static void loadApplicationResourceBundle() {
		resourceBundle = ResourceBundle.getBundle("message", Locale.getDefault());
	}

	/**
	 * Load the application properties necessary for the various application parameters
	 * @throws IOException if an issue involve the file parsing
	 * @throws FileNotFoundException if the file is not found
	 */
	private static void loadApplicationProperties() throws IOException, FileNotFoundException {
		//String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		String appConfigPath = "src/main/resources/application.properties";
		applicationProperties = new Properties();
		applicationProperties.load(new FileInputStream(appConfigPath));
	}
}
