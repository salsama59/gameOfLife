package com.internal.gameoflife.utils;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class GridUtils {

	public static boolean isCellCoordinateValid(int rowIndex, int columnIndex, int[][] grid) {
		if(rowIndex >= 0 && rowIndex <= getGridRowLenth(grid) - 1 &&
				columnIndex >= 0 && columnIndex <= getGridColumnLenth(grid) - 1) {
			return true;
		}else {
			return false;
		}
	}

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

	public static List<Integer> generateGridRowIndexList(int[][] grid){
		int rowLenght = getGridRowLenth(grid);
		List<Integer> rowIndexes = new ArrayList<>();

		for(int i = 0; i < rowLenght; i++) {
			rowIndexes.add(i);
		}

		return rowIndexes;

	}

	public static List<Integer> generateGridColumnIndexList(int[][] grid){
		int columnLenght = getGridColumnLenth(grid);
		List<Integer> columnIndexes = new ArrayList<>();

		for(int i = 0; i < columnLenght; i++) {
			columnIndexes.add(i);
		}

		return columnIndexes;

	}

	public static boolean isGridValid(int[][] grid) {
		if(getGridRowLenth(grid) == 0) {
			return false;
		}

		if(getGridColumnLenth(grid) == 0) {
			return false;
		}

		return true;
	}

	public static int getGridRowLenth(int[][] grid) {
		return grid.length;
	}

	public static int getGridColumnLenth(int[][] grid) {
		return grid[0].length;
	}

}
