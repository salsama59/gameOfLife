package com.internal.gameoflife.simulation;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;

import com.internal.gameoflife.constants.GridConstants;
import com.internal.gameoflife.data.DataManager;
import com.internal.gameoflife.dto.SimulationParameters;
import com.internal.gameoflife.enums.GridCellState;
import com.internal.gameoflife.utils.GridUtils;

public class GameOfLifeSimulation extends Thread implements Runnable{

	private int[][] grid;
	private int refreshRate;
	private long simulationIteration;
	private DataManager dataManager;
	private SimulationParameters simulationParameters;


	public GameOfLifeSimulation(int[][] grid, int refreshRate, float initialActivatedCellPercentage) {
		this.grid = grid;
		this.refreshRate = refreshRate;
		this.dataManager = new DataManager();
		this.simulationParameters = new SimulationParameters(
				this.refreshRate,
				this.simulationIteration,
				initialActivatedCellPercentage,
				GridUtils.getGridRowLenth(grid),
				GridUtils.getGridColumnLenth(grid));
	}

	@Override
	public void run() {
		while (true) {
			this.displayGrid();
			this.updateCellsStateInGrid();
			this.getDataManager().saveDatas(grid, simulationParameters);
			try {
				sleep(this.getRefreshRate() * 1000);
			} catch (InterruptedException ex) { 
				System.out.println("The thread has been interrupted");
			}

		}
	}

	public void updateCellsStateInGrid() {
		for(int rowIndex = 0; rowIndex < GridUtils.getGridRowLenth(this.getGrid()); rowIndex++) {
			for(int columnIndex = 0; columnIndex < GridUtils.getGridColumnLenth(this.getGrid()); columnIndex++) {
				int cellCurrentValue = this.getGrid()[rowIndex][columnIndex];
				Point currentCellCoordinate = new Point(columnIndex, rowIndex);
				List<Point> suroundingCoordinates = GridUtils.calculatePointSuroundingCellCoordinates(currentCellCoordinate, this.getGrid());

				if(cellCurrentValue == GridCellState.ALIVE.ordinal()) {
					deactivateCell(rowIndex, columnIndex, suroundingCoordinates);
				}
				else if(cellCurrentValue == GridCellState.DEAD.ordinal()) {
					activateCell(rowIndex, columnIndex, suroundingCoordinates);
				}
			}
		}
	}

	private void deactivateCell(int rowIndex, int columnIndex, List<Point> suroundingCoordinates) {
		int proximityCounter = checkCellSurroundings(suroundingCoordinates);

		if(proximityCounter < GridConstants.MIN_ACTIVATED_CELL_SURROUNDING || proximityCounter > GridConstants.MAX_ACTIVATED_CELL_SURROUNDING) {
			this.getGrid()[rowIndex][columnIndex] = GridCellState.DEAD.ordinal();
		}
	}

	private void activateCell(int rowIndex, int columnIndex, List<Point> suroundingCoordinates) {
		int proximityCounter = checkCellSurroundings(suroundingCoordinates);

		if(proximityCounter >= GridConstants.MIN_ACTIVATED_CELL_SURROUNDING && proximityCounter <= GridConstants.MAX_ACTIVATED_CELL_SURROUNDING) {
			this.getGrid()[rowIndex][columnIndex] = GridCellState.ALIVE.ordinal();
		}
	}

	private int checkCellSurroundings(List<Point> suroundingCoordinates) {
		int proximityCounter = 0;
		for(Point coordinate : suroundingCoordinates) {
			int surroundingCellValue = this.getGrid()[(int)coordinate.getY()][(int)coordinate.getX()];

			if(surroundingCellValue == GridCellState.ALIVE.ordinal()) {
				proximityCounter++;
			}
		}
		return proximityCounter;
	}

	private void displayGrid() {
		System.out.println("********** Begining of Simulation iterration " + this.getSimulationIteration() + " **********");
		Arrays.stream(this.getGrid())
		.forEach(cellArray -> {
			Arrays.stream(cellArray).forEach(cell -> {
				System.out.print(cell);
				System.out.print("|");
			});
			System.out.println();
		});
		System.out.println("********** End of Simulation iterration " + this.getSimulationIteration() + " **********");
		this.setSimulationIteration(this.getSimulationIteration() + 1);
		this.getSimulationParameters().setSimulationIteration(simulationIteration);
	}

	public int[][] getGrid() {
		return grid;
	}

	public void setGrid(int[][] grid) {
		this.grid = grid;
	}

	public int getRefreshRate() {
		return refreshRate;
	}

	public void setRefreshRate(int frameRate) {
		this.refreshRate = frameRate;
	}

	public long getSimulationIteration() {
		return simulationIteration;
	}

	public void setSimulationIteration(long simulationIteration) {
		this.simulationIteration = simulationIteration;
	}

	public DataManager getDataManager() {
		return dataManager;
	}

	public void setDataManager(DataManager dataManager) {
		this.dataManager = dataManager;
	}

	public SimulationParameters getSimulationParameters() {
		return simulationParameters;
	}

	public void setSimulationParameters(SimulationParameters simulationParameters) {
		this.simulationParameters = simulationParameters;
	}

}
