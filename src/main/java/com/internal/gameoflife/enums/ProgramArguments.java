package com.internal.gameoflife.enums;

/**
 * This enum represent the programm argumments, the ordinal attribute is used to ensure the arguments order
 * during launch, So the argument order is as follow :
 * 
 * <ul>
 * 	<li>GRID_ROW_SIZE => first argument</li>
 * 	<li>GRID_COLUMN_SIZE => second argument</li>
 *  <li>INITIAL_LIVING_CELL_PERCENTAGE => Third argument</li>
 *  <li>REFRESH_RATE => fourth argument</li>
 *  <li>TCP_SERVER_MODE => Fifth argument</li>
 * </ul>
 * @author syeponde
 */
public enum ProgramArguments {GRID_ROW_SIZE,
	GRID_COLUMN_SIZE,
	INITIAL_LIVING_CELL_PERCENTAGE,
	REFRESH_RATE,
	TCP_SERVER_MODE}
