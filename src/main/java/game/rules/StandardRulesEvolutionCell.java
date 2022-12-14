package game.rules;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game.StateLife;
import game.object.Cell;

public class StandardRulesEvolutionCell implements EvolutionCellRule
{
    private static final Logger LOGGER = LogManager.getLogger(StandardRulesEvolutionCell.class);

    @Override
    public void evolutionCell(Cell cell)
    {

        int newState = cell.getState();

        // Une cellule morte possédant exactement trois voisines vivantes devient vivante.
        if (cell.getState() == StateLife.DEATH_VALUE && cell.getContext() == 3)
            newState = StateLife.LIFE_VALUE;

        // Une cellule vivante possédant deux ou trois voisines vivantes reste vivante sinon elle meurt.
        if (cell.getState() == StateLife.LIFE_VALUE && !(cell.getContext() == 2 || cell.getContext() == 3))
        {
            newState = StateLife.DEATH_VALUE;
        }

        cell.changeState(newState);

    }
}
