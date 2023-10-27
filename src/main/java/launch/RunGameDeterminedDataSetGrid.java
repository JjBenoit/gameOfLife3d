package launch;

import java.io.File;

import game.GameOflife;
import game.object.Grid;
import graphixx.GameInfos;
import graphixx.GridFramePaint;
import util.GridUtil;

public class RunGameDeterminedDataSetGrid
{

    // Lancement avec un JDD
    public static void main(String[] args) throws Exception
    {

        GameInfos gameInfos = new GameInfos();

        Grid grid = GridUtil
            .initGridFromFile(new File("/home/socle_gaia2/soda1/workspace/gameOfLife3d/patterns/planer.xml"));

        gameInfos.setGrid(grid);

        GridFramePaint frame = new GridFramePaint(gameInfos, new GameOflife(gameInfos));

        frame.startRender();

    }
}
