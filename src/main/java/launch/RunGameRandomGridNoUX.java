package launch;

import game.GameOflife;
import game.StateLife;
import game.object.Grid;
import game.visitor.VisitThreeDimensionalArrayInitCellTor;
import util.GridUtil;

public class RunGameRandomGridNoUX
{

    public static void main(String[] args)
    {

        Grid grid = null;

        // Taille de la grille
        int sizeX = 3;
        int sizeY = 3;
        int sizeZ = 1;

        GameOflife jeuVie = new GameOflife(sizeY, sizeX, sizeZ);

        // option pré remplir la gille avec des cellules vivantes
        grid = jeuVie.getGrid();
        GridUtil.initGrid(grid, StateLife.DEATH_VALUE, new VisitThreeDimensionalArrayInitCellTor(grid.getGrid()));

        // option pré remplir la gille avec des cellules mortes random
        GridUtil.addRandomDeathCell((int) ((sizeY * sizeX) * 0.5), StateLife.LIFE_VALUE, grid);

        jeuVie.playNumberTurns(10);

    }

}
