package game.visitor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game.geometry.Position3d;
import game.object.Cell;

public class VisitThreeDimensionalArrayWithEdgeInitCell extends AbstractVisitThreeDimensionalArray<Cell>
{

    private static final Logger LOGGER = LogManager.getLogger(VisitThreeDimensionalArrayWithEdgeInitCell.class);

    public VisitThreeDimensionalArrayWithEdgeInitCell(Cell[][][] grid)
    {
        super(grid);
    }

    @Override
    protected void visitOneCell(Cell visitee, Cell cellVoisine)
    {
        visitee.addConnectedCells(cellVoisine);
    }

    // la grille est considérée comme un plan en 3D a bords : haut et bas, droite et gauche , avant, arrière
    @Override
    protected Cell getVisitedCell(Position3d pos, int zpath, int ypath, int xpath)
    {
        // la recherche doit être circonscrite aux dimensions du tableau et de sa topologie et ne pas visiter la celulle de
        // départ : celle dont on a fourni les corrdonnées (degreeOfLiberty[ypath] != 0 && degreeOfLiberty[xpath] != 0))

        // determination de la cellule voisine en fonction de la position de la célulle visitée et du degrée de liberté et de la
        // topologie de la grille
        Integer z = getVisitedCellPositionZ(pos, zpath);
        Integer y = getVisitedCellPositionY(pos, ypath);
        Integer x = getVisitedCellPositionX(pos, xpath);

        if (z != null && y != null && x != null
            && !(degreeOfLiberty[ypath] == 0 && degreeOfLiberty[xpath] == 0 && degreeOfLiberty[zpath] == 0))
        {
            return grid[z][y][x];
        }

        return null;
    }

    private Integer getVisitedCellPositionZ(Position3d pos, int zpath)
    {
        if ((pos.z + degreeOfLiberty[zpath]) < grid.length && (pos.z + degreeOfLiberty[zpath]) >= 0)
            return pos.z + degreeOfLiberty[zpath];

        return null;

    }

    private Integer getVisitedCellPositionY(Position3d pos, int ypath)
    {
        if ((pos.y + degreeOfLiberty[ypath]) < grid[pos.z].length && (pos.y + degreeOfLiberty[ypath]) >= 0)
            return pos.y + degreeOfLiberty[ypath];

        return null;

    }

    private Integer getVisitedCellPositionX(Position3d pos, int xpath)
    {
        if ((pos.x + degreeOfLiberty[xpath]) < grid[pos.z][pos.y].length && (pos.x + degreeOfLiberty[xpath]) >= 0)
            return pos.x + degreeOfLiberty[xpath];

        return null;

    }
}
