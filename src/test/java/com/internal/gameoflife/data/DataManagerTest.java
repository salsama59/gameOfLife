package com.internal.gameoflife.data;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import com.internal.gameoflife.GameOfLife;
import com.internal.gameoflife.constants.PropertyKeyConstants;
import com.internal.gameoflife.dto.SimulationParameters;
import com.internal.gameoflife.utils.TestsUtils;

public class DataManagerTest {

	private SimulationParameters simulationParameters;
	private final static int[][] gridToSave = {{1, 0, 1}, {0, 1, 0}, {1, 0, 1}};
	private static String gridDataFilePath;
	private static String parametersDataFilePath;

	@BeforeClass
	public static void loadProperties() throws FileNotFoundException, IOException {
		TestsUtils.loadTestsConfigurationProperties();
		setGridDataFilePath(GameOfLife.applicationProperties.getProperty(PropertyKeyConstants.DATA_SIMULATION_GRID_FILE_PATH_KEY));
		setParametersDataFilePath(GameOfLife.applicationProperties.getProperty(PropertyKeyConstants.DATA_SIMULATION_PARAMETER_FILE_PATH_KEY));
	}

	@After
	public void deleteTestFiles() {
		File gridSavedFile = new File(getGridDataFilePath());
		File parametersSavedFile = new File(getParametersDataFilePath());
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

		File gridSavedFile = new File(getGridDataFilePath());
		File parametersSavedFile = new File(getParametersDataFilePath());
		assertTrue(gridSavedFile.exists());
		assertTrue(parametersSavedFile.exists());
	}

	public String getGridDataFilePath() {
		return gridDataFilePath;
	}

	public static void setGridDataFilePath(String gridDataFilePath) {
		DataManagerTest.gridDataFilePath = gridDataFilePath;
	}

	public String getParametersDataFilePath() {
		return parametersDataFilePath;
	}

	public static void setParametersDataFilePath(String parametersDataFilePath) {
		DataManagerTest.parametersDataFilePath = parametersDataFilePath;
	}

}
