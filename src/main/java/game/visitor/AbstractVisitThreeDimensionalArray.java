package game.visitor;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game.geometry.Position3d;
import game.object.Cell;

public abstract class AbstractVisitThreeDimensionalArray {

    protected int[] degreeOfLiberty = { -1, 0, 1 };

    private static final Logger LOGGER = LogManager.getLogger(AbstractVisitThreeDimensionalArray.class);

    public List<Cell> getConnectedCellsFromPosition(Position3d pos, Cell[][][] grid) {

	List<Cell> connectedCells = new ArrayList<>();

	// degre de liberté sur l'axe des X
	for (int zpath = 0; zpath < degreeOfLiberty.length; zpath++) {
	    // degre de liberté sur l'axe des Y
	    for (int ypath = 0; ypath < degreeOfLiberty.length; ypath++) {
		// degre de liberté sur l'axe des X
		for (int xpath = 0; xpath < degreeOfLiberty.length; xpath++) {

		    if (LOGGER.isDebugEnabled())
			LOGGER.debug("Y:X:Z " + pos.y + ":" + pos.y + ":" + pos.z + " Visited : "
				+ (pos.y + degreeOfLiberty[ypath]) + ":" + (pos.x + degreeOfLiberty[xpath]) + ":"
				+ (pos.z + degreeOfLiberty[zpath]));

		    Cell connectedCell = getVisitedCell(pos, grid, zpath, ypath, xpath);

		    if (connectedCell != null)
			connectedCells.add(connectedCell);
		}
	    }
	}
	return connectedCells;
    }

    // a partir de la position d'une cellule visitée détermine si une cellule est sa
    // voisine ( connectée )
    protected abstract Cell getVisitedCell(Position3d pos, Cell[][][] grid, int zpath, int ypath, int xpath);

}
