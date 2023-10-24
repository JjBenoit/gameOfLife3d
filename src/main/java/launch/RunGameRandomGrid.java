package launch;

import game.GameOflife;
import game.StateLife;
import game.object.Grid;
import game.visitor.VisitThreeDimensionalArrayInitCellTor;
import graphixx.GameInfos;
import graphixx.GridFramePaint;
import util.GridUtil;

public class RunGameRandomGrid {

    public static void main(String[] args) {

	GameInfos gameInfos = new GameInfos();
	gameInfos.setPaused(false);

	gameInfos.setGrid(new Grid(1, 1000, 1000));

	// option pré remplir la gille avec des cellules vivantes
	GridUtil.initGrid(gameInfos.getGrid(), StateLife.DEATH_VALUE,
		new VisitThreeDimensionalArrayInitCellTor(gameInfos.getGrid().getGrid()));

	// option pré remplir la gille avec des cellules mortes random
	GridUtil.addRandomDeathCell((int) ((gameInfos.getGrid().getSizeY() * gameInfos.getGrid().getSizeX()) * 0.5),
		StateLife.LIFE_VALUE, gameInfos.getGrid());

	GridFramePaint frame = new GridFramePaint(gameInfos);
	frame.startRender();

	GameOflife jeuVie = new GameOflife(gameInfos);
	jeuVie.play();

    }

}
