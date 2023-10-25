package game.object;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game.geometry.Position3d;
import game.visitor.AbstractVisitThreeDimensionalArray;
import util.GridUtil;

public class Grid {

    private static final Logger LOGGER = LogManager.getLogger(Grid.class);

    private AbstractVisitThreeDimensionalArray abstractVisitThreeDimensionalArray;

    // Z,Y,X, grid en cours ( profondeur Z, puis liste lignes de colonnes , 2 dimen
    // les lignes (Y) , 3 dimension les données
    // de chaque
    // pour chaque niveau de Z , une grille Y,X
    private Cell[][][] grid;

    public Grid(int sizeZ, int sizeY, int sizeX, int defaultCellValue,
	    AbstractVisitThreeDimensionalArray abstractVisitThreeDimensionalArray) {
	grid = (new Cell[sizeZ][sizeY][sizeX]);
	this.abstractVisitThreeDimensionalArray = abstractVisitThreeDimensionalArray;

	initGrid(defaultCellValue);
    }

    private void initGrid(int defaultCellValue) {

	for (int z = 0; z < grid.length; z++) {
	    for (int y = 0; y < grid[z].length; y++) {
		for (int x = 0; x < grid[z][y].length; x++) {
		    grid[z][y][x] = new Cell(this, defaultCellValue, new Position3d(x, y, z));
		}
	    }
	}
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Initialisation Grid\n" + GridUtil.printGrid(grid));
	}

    }

    public List<Cell> getConnectedCells(Cell cell) {

	return abstractVisitThreeDimensionalArray.getConnectedCellsFromPosition(cell.getPosition(), grid);

    }

    public int getSizeZ() {
	return grid.length;
    }

    public int getSizeY() {
	return grid[0] != null ? grid[0].length : 0;
    }

    public int getSizeX() {
	return grid[0] != null && grid[0][0] != null ? grid[0][0].length : 0;
    }

    public Cell[][][] getGrid() {
	return grid;
    }

}
