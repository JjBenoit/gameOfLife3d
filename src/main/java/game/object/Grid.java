package game.object;

import java.util.ArrayList;
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

    private List<Cell> bagOfCells = new ArrayList<Cell>();

    public Grid(int sizeZ, int sizeY, int sizeX, int defaultCellValue,
	    AbstractVisitThreeDimensionalArray abstractVisitThreeDimensionalArray) {

	grid = (new Cell[sizeZ][sizeY][sizeX]);
	this.abstractVisitThreeDimensionalArray = abstractVisitThreeDimensionalArray;
	this.bagOfCells = new ArrayList<Cell>();
	initGrid(defaultCellValue);
    }

    private void initGrid(int defaultCellValue) {

	for (int z = 0; z < grid.length; z++) {
	    for (int y = 0; y < grid[z].length; y++) {
		for (int x = 0; x < grid[z][y].length; x++) {
		    Cell cell = new Cell(this, defaultCellValue, new Position3d(x, y, z));
		    grid[z][y][x] = cell;
		    bagOfCells.add(cell);
		}
	    }
	}
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Initialisation Grid\n" + GridUtil.printGrid(this));
	}

    }

    public List<Cell> getConnectedCells(Cell cell) {

	return abstractVisitThreeDimensionalArray.getConnectedCellsFromPosition(cell.getPositionInGrid(), grid);

    }

    public void addCell(Cell cell) {
	bagOfCells.add(cell);
	grid[cell.getPositionInGrid().z][cell.getPositionInGrid().y][cell.getPositionInGrid().x] = cell;
    }

    public List<Cell> getBagOfCells() {
	return bagOfCells;
    }

    public List<Cell> getBagOfCellsOfZ(int z) {

	List<Cell> bagOfCellsZ = new ArrayList<>();

	for (int y = 0; y < grid[z].length; y++) {
	    for (int x = 0; x < grid[z][y].length; x++) {
		bagOfCellsZ.add(grid[z][y][x]);
	    }
	}
	return bagOfCellsZ;
    }

    public Cell getCell(int z, int y, int x) {
	return grid[z][y][x];
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

}
