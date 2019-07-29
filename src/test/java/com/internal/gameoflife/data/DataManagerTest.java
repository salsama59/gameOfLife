package com.internal.gameoflife.data;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Test;

import com.internal.gameoflife.dto.SimulationParameters;

public class DataManagerTest {

	private SimulationParameters simulationParameters;
	private final static int[][] gridToSave = {{1, 0, 1}, {0, 1, 0}, {1, 0, 1}};

	@After
	public void deleteTestFiles() {
		File gridSavedFile = new File("simulation_grid_data.csv");
		File parametersSavedFile = new File("simulation_parameters_data.json");
		gridSavedFile.delete();
		parametersSavedFile.delete();
	}

	@Test
	public void saveDatasTest() {
		saveTestDatas();
	}

	@Test
	public void loadDatasTest() {
		saveTestDatas();
		DataManager dataManager = new DataManager();
		dataManager.loadDatas();
		assertNotNull(dataManager.getLoadedSimulationGrid());
		assertNotNull(dataManager.getLoadedSimulationParameters());
		assertEquals(simulationParameters, dataManager.getLoadedSimulationParameters());
		assertArrayEquals(null, gridToSave, dataManager.getLoadedSimulationGrid());
	}

	@Test
	public void isDataFilesExistingTest() {
		saveTestDatas();
		DataManager dataManager = new DataManager();
		assertTrue(dataManager.isDataFilesExisting());
	}

	@Test
	public void isDataFilesExistingWithoutFilesTest() {
		DataManager dataManager = new DataManager();
		assertFalse(dataManager.isDataFilesExisting());
	}

	private void saveTestDatas() {
		simulationParameters = new SimulationParameters();
		simulationParameters.setColumnLength(3);
		simulationParameters.setRowLength(3);
		simulationParameters.setRefreshRate(2);
		simulationParameters.setInitialActivatedCellPercentage(10f);

		DataManager dataManager = new DataManager();
		dataManager.saveDatas(gridToSave, simulationParameters);

		File gridSavedFile = new File("simulation_grid_data.csv");
		File parametersSavedFile = new File("simulation_parameters_data.json");
		assertTrue(gridSavedFile.exists());
		assertTrue(parametersSavedFile.exists());
	}

}
