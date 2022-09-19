package game.visitor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game.geometry.Position3d;

public abstract class AbstractVisitThreeDimensionalArray<T>
{

    protected int[] degreeOfLiberty = {-1, 0, 1};

    private static final Logger LOGGER = LogManager.getLogger(AbstractVisitThreeDimensionalArray.class);

    protected T[][][] grid;

    public AbstractVisitThreeDimensionalArray(T[][][] grid)
    {
        super();
        this.grid = grid;
    }

    public void visitCellsFromPosition(Position3d pos)
    {
        // degre de liberté sur l'axe des X
        for (int zpath = 0; zpath < degreeOfLiberty.length; zpath++)
        {
            // degre de liberté sur l'axe des Y
            for (int ypath = 0; ypath < degreeOfLiberty.length; ypath++)
            {
                // degre de liberté sur l'axe des X
                for (int xpath = 0; xpath < degreeOfLiberty.length; xpath++)
                {

                    if (LOGGER.isDebugEnabled())
                        LOGGER
                            .debug("Y:X:Z " + pos.y + ":" + pos.y + ":" + pos.z + " Visited : " + (pos.y + degreeOfLiberty[ypath]) + ":"
                                + (pos.x + degreeOfLiberty[xpath]) + ":" + (pos.z + degreeOfLiberty[zpath]));

                    T visitedCell = grid[pos.z][pos.y][pos.x];

                    T connectedCell = getVisitedCell(pos, zpath, ypath, xpath);

                    if (connectedCell != null)
                        visitOneCell(visitedCell, connectedCell);
                }
            }
        }
    }

    // a partir de la position d'une cellule visitée détermine si une cellule est sa voisine ( connectée )
    protected abstract T getVisitedCell(Position3d pos, int zpath, int ypath, int xpath);

    // a partir d'une cellule visiteée fait un traitement sur une cellule voisine ( connectée )
    protected abstract void visitOneCell(T cellVisitee, T cellVoisine);

}
