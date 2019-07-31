package com.internal.gameoflife.constants;

public class PropertyKeyConstants {
	
	public static final String CONFIG_ROOT_PROPERTY_KEY = "application.config.";
	public static final String REFRESH_RATE_KEY = CONFIG_ROOT_PROPERTY_KEY + "refresh.rate";
	public static final String ACTIVATED_CELL_PERCENTAGE_KEY = CONFIG_ROOT_PROPERTY_KEY + "initial.activated.cell.percentage";
	public static final String GRID_ROW_LENGTH_KEY = CONFIG_ROOT_PROPERTY_KEY + "grid.row.length";
	public static final String GRID_COLUMN_LENGTH_KEY = CONFIG_ROOT_PROPERTY_KEY + "grid.column.length";
	public static final String DATA_SIMULATION_GRID_FILE_PATH_KEY = CONFIG_ROOT_PROPERTY_KEY + "data.simulation.grid.file.path";
	public static final String DATA_SIMULATION_GRID_CSV_FILE_SEPARATOR_KEY = CONFIG_ROOT_PROPERTY_KEY + "data.simulation.grid.csv.file.separator";
	public static final String DATA_SIMULATION_PARAMETER_FILE_PATH_KEY = CONFIG_ROOT_PROPERTY_KEY + "data.simulation.parameter.file.path";
	public static final String ACCESS_MODE_KEY = CONFIG_ROOT_PROPERTY_KEY + "tcp.server.access.mode";
	
	public static final String MESSAGE_ROOT_PROPERTY_KEY = "application.message.";
	public static final String APPLICATION_MESSAGE_GRID_COLUMN_ARGUMENTS_MANDATORY = MESSAGE_ROOT_PROPERTY_KEY + "grid.column.arguments.mandatory";
	public static final String APPLICATION_MESSAGE_GRID_ROW_ARGUMENTS_MANDATORY = MESSAGE_ROOT_PROPERTY_KEY + "grid.row.arguments.mandatory";
	public static final String APPLICATION_MESSAGE_TCP_SERVER_ENABLED_ARGUMENTS_MANDATORY = MESSAGE_ROOT_PROPERTY_KEY + "tcp.server.enabled.arguments.mandatory";
	public static final String APPLICATION_MESSAGE_REFRESH_RATE_ARGUMENTS_MANDATORY = MESSAGE_ROOT_PROPERTY_KEY + "refresh.rate.arguments.mandatory";
	public static final String APPLICATION_MESSAGE_CELL_PERCENTAGE_ARGUMENTS_MANDATORY = MESSAGE_ROOT_PROPERTY_KEY + "cell.percentage.arguments.mandatory";
	public static final String APPLICATION_MESSAGE_DATA_LOAD_NO = MESSAGE_ROOT_PROPERTY_KEY + "data.load.no";
	public static final String APPLICATION_MESSAGE_DATA_LOAD_YES = MESSAGE_ROOT_PROPERTY_KEY + "data.load.yes";
	public static final String APPLICATION_MESSAGE_DATA_EXISTING = MESSAGE_ROOT_PROPERTY_KEY + "data.existing";
	public static final String APPLICATION_MESSAGE_THREAD_INTERRUPTED_EXCEPTION = MESSAGE_ROOT_PROPERTY_KEY + "thread.interrupted.exception";
	public static final String APPLICATION_MESSAGE_CONSOLE_DISPLAY_BEGIN = MESSAGE_ROOT_PROPERTY_KEY + "console.display.begin";
	public static final String APPLICATION_MESSAGE_CONSOLE_DISPLAY_END = MESSAGE_ROOT_PROPERTY_KEY + "console.display.end";
	
}
