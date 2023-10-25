package game.visitor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game.geometry.Position3d;
import game.object.Cell;

public class VisitThreeDimensionalArrayCellTor extends AbstractVisitThreeDimensionalArray {

    private static final Logger LOGGER = LogManager.getLogger(VisitThreeDimensionalArrayCellTor.class);

    // la grille est considérée comme un plan en 3D a bords : haut et bas
    @Override
    protected Cell getVisitedCell(Position3d pos, Cell[][][] grid, int zpath, int ypath, int xpath) {
	// la recherche doit être circonscrite aux dimensions des tableaux ( not out of
	// bound ) et de sa topologie et ne pas
	// visiter la celulle de
	// départ : celle dont on a fourni les corrdonnées (degreeOfLiberty[ypath] != 0
	// && degreeOfLiberty[xpath] != 0))

	// determination de la cellule voisine en fonction de la position de la culle
	// visitée et du degrée de liberté et de la
	// topologie de la grille
	Integer z = getVisitedCellPositionZ(pos, grid, zpath);
	Integer y = getVisitedCellPositionY(pos, grid, ypath);
	Integer x = getVisitedCellPositionX(pos, grid, xpath);

	if (z != null && y != null && x != null
		&& !(degreeOfLiberty[ypath] == 0 && degreeOfLiberty[xpath] == 0 && degreeOfLiberty[zpath] == 0)) {
	    return grid[z][y][x];
	}

	return null;
    }

    private Integer getVisitedCellPositionZ(Position3d pos, Cell[][][] grid, int zpath) {
	if ((pos.z + degreeOfLiberty[zpath]) < grid.length && (pos.z + degreeOfLiberty[zpath]) >= 0)
	    return pos.z + degreeOfLiberty[zpath];

	return null;

    }

    private Integer getVisitedCellPositionY(Position3d pos, Cell[][][] grid, int ypath) {

	// si bord droit ( out of bound upper size array ) => 0
	if ((pos.y + degreeOfLiberty[ypath]) >= grid[pos.z].length)
	    return 0;

	// si ( out of bound lower size array ) => max size
	if ((pos.y + degreeOfLiberty[ypath]) < 0)
	    return grid[pos.z].length - 1;

	// else la position demandée
	return pos.y + degreeOfLiberty[ypath];

    }

    private Integer getVisitedCellPositionX(Position3d pos, Cell[][][] grid, int xpath) {
	// si bord droit ( out of bound upper size array ) => 0
	if ((pos.x + degreeOfLiberty[xpath]) >= grid[pos.z][pos.y].length)
	    return 0;

	// si ( out of bound lower size array ) => max size
	if ((pos.x + degreeOfLiberty[xpath]) < 0)
	    return grid[pos.z][pos.y].length - 1;

	// else la position demandée
	return pos.x + degreeOfLiberty[xpath];

    }

}
