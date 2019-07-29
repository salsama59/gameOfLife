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
import com.internal.gameoflife.dto.SimulationParameters;

public class DataManager {

	private int[][] loadedSimulationGrid;
	private SimulationParameters loadedSimulationParameters;

	public void saveDatas(int[][] grid, SimulationParameters simulationParameters) {
		this.saveSimulationGrid(grid);
		this.saveSimulationParameters(simulationParameters);
	}

	public void loadDatas() {
		this.setLoadedSimulationParameters(this.loadSimulationParameters());
		this.setLoadedSimulationGrid(this.loadSimulationGid());
	}

	public boolean isDataFilesExisting() {
		File gridSavedFile = new File("simulation_grid_data.csv");
		File parametersSavedFile = new File("simulation_parameters_data.json");
		return gridSavedFile.exists() && parametersSavedFile.exists();
	}


	private int[][] loadSimulationGid() {
		SimulationParameters loadedSimulationParameters = this.getLoadedSimulationParameters();
		int[][] loadedGrid = new int[loadedSimulationParameters.getRowLength()][loadedSimulationParameters.getColumnLength()];
		int rowCount = 0;
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader("simulation_grid_data.csv"))) {
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				String[] values = line.split(",");
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

	private SimulationParameters loadSimulationParameters() {

		ObjectMapper objectMapper = new ObjectMapper();
		SimulationParameters simulationParameters = null;
		try {
			simulationParameters = objectMapper.readValue(new File("simulation_parameters_data.json"), SimulationParameters.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return simulationParameters;
	}

	private void saveSimulationGrid(int[][] grid) {
		StringBuilder stringBuilder = new StringBuilder();
		Arrays.stream(grid)
		.forEach(cellArray -> {
			stringBuilder.append(Arrays.stream(cellArray)
					.mapToObj(cell -> String.valueOf(cell))
					.collect(Collectors.joining(","))
					.concat("\n"));
		});

		File csvOutputFile = new File("simulation_grid_data.csv");
		try (PrintWriter printWriter = new PrintWriter(csvOutputFile)) {
			printWriter.print(stringBuilder.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void saveSimulationParameters(SimulationParameters simulationParameters) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			objectMapper.writeValue(new File("simulation_parameters_data.json"), simulationParameters);
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


}
