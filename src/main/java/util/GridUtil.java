package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import game.geometry.Position3d;
import game.object.Cell;
import game.object.Grid;
import game.visitor.AbstractVisitThreeDimensionalArray;

public class GridUtil
{
    private static Random random = new Random();

    private static final Logger LOGGER = LogManager.getLogger(GridUtil.class);

    public static void addRandomDeathCell(int nbCell, int defaultValue, Grid grid)
    {

        int i = 0;
        while (i < nbCell)
        {

            int randomPosZ = grid.getGrid().length > 1 ? random.nextInt(grid.getGrid().length - 1) : 0;
            int randomPosY = grid.getGrid()[randomPosZ].length > 1 ? random.nextInt(grid.getGrid()[randomPosZ].length - 1) : 0;
            int randomPosX =
                grid.getGrid()[randomPosZ][randomPosY].length > 1 ? random.nextInt(grid.getGrid()[randomPosZ][randomPosY].length - 1) : 0;

            grid.getGrid()[randomPosZ][randomPosY][randomPosX].changeState(defaultValue);
            i++;
        }
    }

    public static void initGrid(Grid grid, int defaultValue, AbstractVisitThreeDimensionalArray visitor)
    {

        for (int z = 0; z < grid.getGrid().length; z++)
        {
            for (int y = 0; y < grid.getGrid()[z].length; y++)
            {
                for (int x = 0; x < grid.getGrid()[z][y].length; x++)
                {
                    grid.getGrid()[z][y][x] = new Cell(defaultValue, new Position3d(x, y, z));
                }
            }
        }
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Initialisation Grid\n" + GridUtil.printGrid(grid));
        }
        // ajoute les cellules voisines
        connectCells(grid, visitor);

    }

    public static void connectCells(Grid grid, AbstractVisitThreeDimensionalArray visitor)
    {

        for (int z = 0; z < grid.getGrid().length; z++)
        {
            for (int y = 0; y < grid.getGrid()[z].length; y++)
            {
                for (int x = 0; x < grid.getGrid()[z][y].length; x++)
                {
                    visitor.visitCellsFromPosition(grid.getGrid()[z][y][x].getPosition());
                }
            }
        }

    }

    public static void saveGrid(File path, Grid grid, int turn) throws IOException, ParserConfigurationException, TransformerException
    {
        path.mkdirs();
        File file = new File(path, "Grid_" + System.currentTimeMillis() + "_" + turn + ".xml");

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root elements
        Document doc = docBuilder.newDocument();
        Element gridXml = doc.createElement("grid");
        doc.appendChild(gridXml);
        gridXml.setAttribute("zSize", grid.getGrid().length + "");
        gridXml.setAttribute("turn", turn + "");

        for (int z = 0; z < grid.getGrid().length; z++)
        {
            Element zDim = doc.createElement("zDim");
            zDim.setAttribute("zIndex", z + "");
            gridXml.appendChild(zDim);
            Text text = doc.createTextNode("data");
            text.setData(GridUtil.print2dDimendionGrid(grid.getGrid()[z]));
            zDim.appendChild(text);
        }

        try (FileOutputStream output = new FileOutputStream(file))
        {
            writeXml(doc, output);
        }
        catch (IOException e)
        {
            throw e;
        }
    }

    public static Grid initGridFromFile(File file) throws Exception
    {

        // Instantiate the Factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Grid grid = null;

        try
        {

            // optional, but recommended
            // process XML securely, avoid attacks like XML External Entities (XXE)
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(file);

            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("zDim");

            grid = new Grid(list.getLength());

            for (int z = 0; z < list.getLength(); z++)
            {

                Node node = list.item(z);
                String content = node.getFirstChild().getTextContent();
                String adjusted = content.replaceAll("(?m)^[ \t]*\r?\n", "");
                String[] lignes = adjusted.split("\\n");
                grid.getGrid()[z] = new Cell[lignes.length][];
                for (int y = 0; y < lignes.length; y++)
                {

                    char[] gridJddChar = lignes[y].toCharArray();

                    grid.getGrid()[z][y] = new Cell[gridJddChar.length];

                    for (int x = 0; x < gridJddChar.length; x++)
                    {
                        grid.getGrid()[z][y][x] = new Cell(Character.getNumericValue(gridJddChar[x]), new Position3d(x, y, z));
                    }
                }

                if (LOGGER.isDebugEnabled())
                {
                    LOGGER.debug("Initialisation Grid\n" + GridUtil.printGrid(grid));
                }

            }

        }
        catch (ParserConfigurationException | SAXException | IOException e)
        {
            throw e;
        }
        return grid;

    }

    public static String printGrid(Grid grid)
    {
        StringBuilder buffer = new StringBuilder();

        for (int z = 0; z < grid.getGrid().length; z++)
        {
            buffer.append("--Z" + z + "--\n");

            print2dDimendionGrid(grid.getGrid()[z]);
        }

        return buffer.toString();

    }

    public static String print2dDimendionGrid(Cell[][] grid)
    {
        StringBuilder buffer = new StringBuilder();

        for (int y = 0; y < grid.length; y++)
        {
            for (int x = 0; x < grid[y].length; x++)
            {
                buffer.append(grid[y][x].getState());
            }
            buffer.append("\n");
        }
        return buffer.toString();

    }

    private static void writeXml(Document doc, OutputStream output) throws TransformerException
    {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);

    }
}
