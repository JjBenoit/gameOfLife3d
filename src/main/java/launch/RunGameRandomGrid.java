package launch;

import game.GameOflife;
import game.StateLife;
import game.object.Grid;
import game.visitor.VisitThreeDimensionalArrayCellTor;
import graphixx.GameInfos;
import graphixx.GridFramePaint;
import util.GridUtil;

public class RunGameRandomGrid {

    public static void main(String[] args) {

	GameInfos gameInfos = new GameInfos();

	gameInfos.setGrid(new Grid(1, 1000, 1000, StateLife.DEATH_VALUE, new VisitThreeDimensionalArrayCellTor()));

	// option pré remplir la gille avec des cellules mortes random
	GridUtil.addRandomDeathCell((int) ((gameInfos.getGrid().getSizeY() * gameInfos.getGrid().getSizeX()) * 0.5),
		StateLife.LIFE_VALUE, gameInfos.getGrid());

	GridFramePaint frame = new GridFramePaint(gameInfos);
	frame.startRender();

	GameOflife jeuVie = new GameOflife(gameInfos);
	jeuVie.play();

    }

}
