package graphixx;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game.GameOflife;
import game.StateLife;

public class GridFramePaint extends MoteurGraphique2D
{

    private GameOflife jeu;

    private JPanel panelCells = new JPanel();

    private JPanel panelInfos = new JPanel();

    private Rectangle bounds;

    private JLabel nbTurn;

    private JLabel vitesse;

    private Dimension dimCellule;

    private final Font grosseFonte = new Font("", Font.ROMAN_BASELINE, 25);

    private static final Logger LOGGER = LogManager.getLogger(GridFramePaint.class);

    // quelle profondeur afficher ?
    private int selectedZ = 0;

    protected boolean paused = true;

    public GridFramePaint(GameOflife jeu)
    {
        this.jeu = jeu;
        bounds = ((Graphics2D) getGraphics()).getDeviceConfiguration().getBounds();

        addKeyListener(new KeyBordListener(this, jeu));
        add(panelCells, BorderLayout.CENTER);
        addAndinitInfos();
        initPanelCells();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(bounds.width, bounds.height);

        setVisible(true);

    }

    private void initPanelCells()
    {
        dimCellule = new Dimension(5, 5);
        panelCells.setSize(bounds.width, (bounds.height - 100));
        panelCells.addMouseListener(new MouseListenerGrid(this));
    }

    private void addAndinitInfos()
    {
        add(panelInfos, BorderLayout.SOUTH);
        panelInfos.setBorder(new LineBorder(Color.BLACK));
        panelInfos.setLayout(new FlowLayout(FlowLayout.LEFT));

        nbTurn = new JLabel();
        nbTurn.setFont(new Font("Verdana", 1, 20));
        panelInfos.add(nbTurn);

        vitesse = new JLabel();
        vitesse.setFont(new Font("Verdana", 1, 20));
        panelInfos.add(vitesse);

    }

    private void upDatetGfxGrid()
    {
        Graphics g = panelCells.getGraphics();

        for (int y = 0; y < jeu.getGrid().getGrid()[selectedZ].length; y++)
        {
            for (int x = 0; x < jeu.getGrid().getGrid()[selectedZ][y].length; x++)
            {

                if (jeu.getGrid().getGrid()[selectedZ][y][x].getState() == StateLife.DEATH_VALUE)
                {
                    g.setColor(Color.BLACK);
                    g.fillRect(x * dimCellule.width, y * dimCellule.height, dimCellule.width, dimCellule.height);
                }
                else
                {
                    g.setColor(Color.WHITE);
                    g.fillRect(x * dimCellule.width, y * dimCellule.height, dimCellule.width, dimCellule.height);
                }
                // on ne dessine pas ce qui ne se voit pas
                if (x * dimCellule.width > panelCells.getWidth())
                    break;

            }
            // on ne dessine pas ce qui ne se voit pas
            if (y * dimCellule.height > panelCells.getHeight())
                break;
        }

    }

    private void upDateInfo()
    {
        nbTurn.setText("Nb turn :" + jeu.getTurn() + "." + " Z :" + selectedZ);
        vitesse.setText("Refresh :" + waitInMS + " ms" + (paused ? " Game in pause--> Press 'space bar' to resume" : ""));
    }

    @Override
    protected void graphicalRender()
    {
        if (!isPaused() && isDisplayable())
        {
            jeu.playOneTurn();
        }

        long time = System.currentTimeMillis();
        upDatetGfxGrid();
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Grid affichée en : " + (System.currentTimeMillis() - time) + " ms");

        upDateInfo();

    }

    public boolean isPaused()
    {
        return paused;
    }

    public GameOflife getJeu()
    {
        return jeu;
    }

    public int getSelectedZ()
    {
        return selectedZ;
    }

    public void setSelectedZ(int selectedZ)
    {
        this.selectedZ = selectedZ;
    }

    public Dimension getDimCellule()
    {
        return dimCellule;
    }

    public void setDimCellule(Dimension dimCellule)
    {
        this.dimCellule = dimCellule;
    }

}
