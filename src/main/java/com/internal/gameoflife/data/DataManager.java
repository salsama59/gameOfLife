package com.internal.gameoflife.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.internal.gameoflife.GameOfLife;
import com.internal.gameoflife.constants.PropertyKeyConstants;
import com.internal.gameoflife.dto.SimulationParameters;

/**
 * This class is responsible for the data management in the application.
 * Allows the load and the save of datas, the verification of data files existence.
 * @author syeponde
 */
public class DataManager {
	private String csvFileSeparator;
	private String gridSavedFilePath;
	private String parametersSavedFilePath;
	private int[][] loadedSimulationGrid;
	private SimulationParameters loadedSimulationParameters;

	public DataManager() {
		this.gridSavedFilePath = GameOfLife
				.applicationProperties
				.getProperty(PropertyKeyConstants.DATA_SIMULATION_GRID_FILE_PATH_KEY);
		this.parametersSavedFilePath =  GameOfLife
				.applicationProperties
				.getProperty(PropertyKeyConstants.DATA_SIMULATION_PARAMETER_FILE_PATH_KEY);
		this.csvFileSeparator =  GameOfLife
				.applicationProperties
				.getProperty(PropertyKeyConstants.DATA_SIMULATION_GRID_CSV_FILE_SEPARATOR_KEY);
	}
	
	/**
	 * Save the simulation related datas
	 * @param grid the grid to save
	 * @param simulationParameters the simulation parameters to save
	 */
	public void saveDatas(int[][] grid, SimulationParameters simulationParameters) {
		this.saveSimulationGrid(grid);
		this.saveSimulationParameters(simulationParameters);
	}
	
	/**
	 * Load the simulation related datas.
	 */
	public void loadDatas() {
		this.setLoadedSimulationParameters(this.loadSimulationParameters());
		this.setLoadedSimulationGrid(this.loadSimulationGid());
	}
	
	/**
	 * Check if the data files exists, grid data and the parameters data.
	 * @return true if the grid data file and the parameters data file exits.
	 */
	public boolean isDataFilesExisting() {
		File gridSavedFile = new File(this.getGridSavedFilePath());
		File parametersSavedFile = new File(this.getParametersSavedFilePath());
		return gridSavedFile.exists() && parametersSavedFile.exists();
	}

	/**
	 * Load Specificaly the simulation grid data file if it exists from a csv file
	 * @return the grid loaded
	 */
	private int[][] loadSimulationGid() {
		SimulationParameters loadedSimulationParameters = this.getLoadedSimulationParameters();
		int[][] loadedGrid = new int[loadedSimulationParameters.getRowLength()][loadedSimulationParameters.getColumnLength()];
		int rowCount = 0;
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(this.getGridSavedFilePath()))) {
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				String[] values = line.split(this.getCsvFileSeparator());
				loadedGrid[rowCount] = Arrays.stream(values)
						.mapToInt(value -> Integer.parseInt(value)).toArray();
				rowCount++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return loadedGrid;
	}
	
	/**
	 * Load the simulation parameters file data if it exists from a json file
	 * @return the loaded simulation parameters file data
	 */
	private SimulationParameters loadSimulationParameters() {

		ObjectMapper objectMapper = new ObjectMapper();
		SimulationParameters simulationParameters = null;
		try {
			simulationParameters = objectMapper.readValue(new File(this.getParametersSavedFilePath()), SimulationParameters.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return simulationParameters;
	}
	
	/**
	 * Save the simulation grid data in a csv file
	 * @param grid the grid to save
	 */
	private void saveSimulationGrid(int[][] grid) {
		StringBuilder stringBuilder = new StringBuilder();
		Arrays.stream(grid)
		.forEach(cellArray -> {
			stringBuilder.append(Arrays.stream(cellArray)
					.mapToObj(cell -> String.valueOf(cell))
					.collect(Collectors.joining(this.getCsvFileSeparator()))
					.concat("\n"));
		});

		File csvOutputFile = new File(this.getGridSavedFilePath());
		try (PrintWriter printWriter = new PrintWriter(csvOutputFile)) {
			printWriter.print(stringBuilder.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Save the simulation parameters data in a json file
	 * @param simulationParameters the parameters to save
	 */
	private void saveSimulationParameters(SimulationParameters simulationParameters) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			objectMapper.writeValue(new File(this.getParametersSavedFilePath()), simulationParameters);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int[][] getLoadedSimulationGrid() {
		return loadedSimulationGrid;
	}

	public void setLoadedSimulationGrid(int[][] loadedSimulationGrid) {
		this.loadedSimulationGrid = loadedSimulationGrid;
	}

	public SimulationParameters getLoadedSimulationParameters() {
		return loadedSimulationParameters;
	}

	public void setLoadedSimulationParameters(SimulationParameters loadedSimulationParameters) {
		this.loadedSimulationParameters = loadedSimulationParameters;
	}

	public String getGridSavedFilePath() {
		return gridSavedFilePath;
	}

	public void setGridSavedFilePath(String gridSavedFilePath) {
		this.gridSavedFilePath = gridSavedFilePath;
	}

	public String getParametersSavedFilePath() {
		return parametersSavedFilePath;
	}

	public void setParametersSavedFilePath(String parametersSavedFilePath) {
		this.parametersSavedFilePath = parametersSavedFilePath;
	}

	public String getCsvFileSeparator() {
		return csvFileSeparator;
	}

	public void setCsvFileSeparator(String csvFileSeparator) {
		this.csvFileSeparator = csvFileSeparator;
	}


}
