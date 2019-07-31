package com.internal.gameoflife.utils;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * A utility class to calculate various informations like :
 * <ul>
 * 	<li>cell coordinates validity</li>
 * 	<li>generate a list of surrounding cells</li>
 * 	<li>Verify grid validity</li>
 * 	<li>get row lenght and column lenght</li>
 * </ul>
 * @author syeponde
 */
public class GridUtils {

	/**
	 * Verify if a coordinate is not out of the grid bound
	 * @param rowIndex the row index to be tested
	 * @param columnIndex the column to be tested
	 * @param grid the coordinates grid. 
	 * @return true if the coordinate is valid
	 */
	public static boolean isCellCoordinateValid(int rowIndex, int columnIndex, int[][] grid) {
		if(rowIndex >= 0 && rowIndex <= getGridRowLenth(grid) - 1 &&
				columnIndex >= 0 && columnIndex <= getGridColumnLenth(grid) - 1) {
			return true;
		}else {
			return false;
		}
	}

	/**
	 * Calculate the coordinate list of cells surrounding the current cell coordinates
	 * @param currentCellCoordinates the current coordinate tested
	 * @param grid the grid coordinate
	 * @return a list of coordinate surrounding a cell.
	 */
	public static List<Point> calculatePointSuroundingCellCoordinates(Point currentCellCoordinates, int[][] grid){

		List<Point> points = new ArrayList<Point>();
		int maxColunmSurounding = (int) currentCellCoordinates.getX() + 1;
		int minColunmSurounding = (int) currentCellCoordinates.getX() - 1;

		int maxRowSurounding = (int) currentCellCoordinates.getY() + 1;
		int minRowSurounding = (int) currentCellCoordinates.getY() - 1;

		for(int rowIndex = minRowSurounding; rowIndex < maxRowSurounding + 1; rowIndex++) {
			for(int columnIndex = minColunmSurounding; columnIndex < maxColunmSurounding + 1; columnIndex++) {
				if(isCellCoordinateValid(rowIndex, columnIndex, grid) 
						&& ((int) currentCellCoordinates.getX() != columnIndex || (int) currentCellCoordinates.getY() != rowIndex)) {
					points.add(new Point(columnIndex, rowIndex));
				}
			}
		}

		return points;
	}

	/**
	 * Generate a row index list from a grid
	 * @param grid the grid from whom the list must be calculated.
	 * @return a list of row index.
	 */
	public static List<Integer> generateGridRowIndexList(int[][] grid){
		int rowLenght = getGridRowLenth(grid);
		List<Integer> rowIndexes = new ArrayList<>();

		for(int i = 0; i < rowLenght; i++) {
			rowIndexes.add(i);
		}

		return rowIndexes;

	}

	/**
	 * Generate a column index list from a grid
	 * @param grid the grid from whom the list must be calculated.
	 * @return a list of column index.
	 */
	public static List<Integer> generateGridColumnIndexList(int[][] grid){
		int columnLenght = getGridColumnLenth(grid);
		List<Integer> columnIndexes = new ArrayList<>();

		for(int i = 0; i < columnLenght; i++) {
			columnIndexes.add(i);
		}

		return columnIndexes;

	}

	/**
	 * Verify if the grid pass as parameter is valid by verifying it row length and it column length.
	 * @param grid the grid to validate
	 * @return true if the row length nor the column length is equals 0, return false otherwise.
	 */
	public static boolean isGridValid(int[][] grid) {
		if(getGridRowLenth(grid) == 0) {
			return false;
		}

		if(getGridColumnLenth(grid) == 0) {
			return false;
		}

		return true;
	}

	/**
	 * Get the grid pass as parameter row length
	 * @param grid the grid from whom the row length must be calculated
	 * @return the row length of the grid.
	 */
	public static int getGridRowLenth(int[][] grid) {
		return grid.length;
	}
	
	/**
	 * Get the grid pass as parameter column length
	 * @param grid the grid from whom the column length must be calculated
	 * @return the column length of the grid.
	 */
	public static int getGridColumnLenth(int[][] grid) {
		return grid[0].length;
	}

}
