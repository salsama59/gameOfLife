package com.internal.gameoflife.dto;

public class SimulationParameters {

	private int refreshRate;
	private long simulationIteration;
	private float initialActivatedCellPercentage;
	private int rowLength;
	private int columnLength;
	private boolean isTcpServerModeEnabled;

	public SimulationParameters() {}

	public SimulationParameters(int refreshRate, long simulationIteration, float initialActivatedCellPercentage,
			int rowLength, int columnLength, boolean isTcpServerModeEnabled) {
		this.refreshRate = refreshRate;
		this.simulationIteration = simulationIteration;
		this.initialActivatedCellPercentage = initialActivatedCellPercentage;
		this.rowLength = rowLength;
		this.columnLength = columnLength;
		this.isTcpServerModeEnabled = isTcpServerModeEnabled;
	}

	public int getRefreshRate() {
		return refreshRate;
	}
	public void setRefreshRate(int refreshRate) {
		this.refreshRate = refreshRate;
	}
	public long getSimulationIteration() {
		return simulationIteration;
	}
	public void setSimulationIteration(long simulationIteration) {
		this.simulationIteration = simulationIteration;
	}
	public float getInitialActivatedCellPercentage() {
		return initialActivatedCellPercentage;
	}
	public void setInitialActivatedCellPercentage(float initialActivatedCellPercentage) {
		this.initialActivatedCellPercentage = initialActivatedCellPercentage;
	}
	public int getRowLength() {
		return rowLength;
	}
	public void setRowLength(int rowLength) {
		this.rowLength = rowLength;
	}
	public int getColumnLength() {
		return columnLength;
	}
	public void setColumnLength(int columnLength) {
		this.columnLength = columnLength;
	}

	public boolean isTcpServerModeEnabled() {
		return isTcpServerModeEnabled;
	}

	public void setTcpServerModeEnabled(boolean isTcpServerModeEnabled) {
		this.isTcpServerModeEnabled = isTcpServerModeEnabled;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + columnLength;
		result = prime * result + Float.floatToIntBits(initialActivatedCellPercentage);
		result = prime * result + (isTcpServerModeEnabled ? 1231 : 1237);
		result = prime * result + refreshRate;
		result = prime * result + rowLength;
		result = prime * result + (int) (simulationIteration ^ (simulationIteration >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		SimulationParameters other = (SimulationParameters) obj;
		if (columnLength != other.columnLength) {
			return false;
		}
		if (Float.floatToIntBits(initialActivatedCellPercentage) != Float
				.floatToIntBits(other.initialActivatedCellPercentage)) {
			return false;
		}
		if (isTcpServerModeEnabled != other.isTcpServerModeEnabled) {
			return false;
		}
		if (refreshRate != other.refreshRate) {
			return false;
		}
		if (rowLength != other.rowLength) {
			return false;
		}
		if (simulationIteration != other.simulationIteration) {
			return false;
		}
		return true;
	}
}
