package launch;

import java.io.File;

import game.GameOflife;
import game.object.Grid;
import game.visitor.VisitThreeDimensionalArrayInitCellTor;
import graphixx.GridFramePaint;
import util.GridUtil;

public class RunGameDeterminedDataSetGrid
{

    // Lancement avec un JDD
    public static void main(String[] args) throws Exception
    {

        Grid grid =
            GridUtil.initGridFromFile(new File("/home/socle_gaia2/soda1/workspace/gameOfLife3d/patterns/planer.xml"));

        // ajoute les cellules voisines
        GridUtil.connectCells(grid, new VisitThreeDimensionalArrayInitCellTor(grid.getGrid()));

        GameOflife jeuVie = new GameOflife(grid);

        GridFramePaint frame = new GridFramePaint(jeuVie);

        frame.go();

    }
}
