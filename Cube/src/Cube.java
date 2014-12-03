

import java.util.LinkedList;

// -------------------------------------------------------------------------
/**
 * Represents a Rubiks cube, with methods for solving. Contains two different
 * representations of a rubik's cube: one breaking the cube down into faces, and
 * another breaking into cubies. Contains methods to manipulate the cube, from
 * rotating one face to solving a cube.
 *
 * @author Victoria Hairston (vhairs16)
 * @author Louis (Mischa) Michael (louism)
 * @author Sam Muriello (PID)
 * @version 2014.12.01
 */
public class Cube
{
    private static Cube instance = null;


    // ----------------------------------------------------------
    /**
     * getInstance method
     *
     * @return instance of cube
     */
    public static Cube getInstance()
    {
        if (instance == null)
        {
            instance = new Cube();
        }
        return instance;
    }

    // Fields

    // Unsolved cube, never manipulated
    private CubeColor[][]    redFace;
    private CubeColor[][]    greenFace;
    private CubeColor[][]    yellowFace;
    private CubeColor[][]    whiteFace;
    private CubeColor[][]    blueFace;
    private CubeColor[][]    orangeFace;

    // Changable variables used for solving purposes only.
    private CubeColor[][]    upFace;
    private CubeColor[][]    downFace;
    private CubeColor[][]    leftFace;
    private CubeColor[][]    rightFace;
    private CubeColor[][]    frontFace;
    private CubeColor[][]    backFace;
    private Edge[]           edges;
    private Edge[]           solveEdges;
    private Corner[]         corners;
    private Corner[]         solveCorners;
    private Cubie[][][]      cube;

    private CubeColor        perspective;

    private LinkedList<Move> solution;


    // ----------------------------------------------------------
    /**
     * Create a new Cube object.
     */
    protected Cube()
    {
        redFace = new CubeColor[3][3];
        greenFace = new CubeColor[3][3];
        yellowFace = new CubeColor[3][3];
        blueFace = new CubeColor[3][3];
        orangeFace = new CubeColor[3][3];
        whiteFace = new CubeColor[3][3];
        upFace = new CubeColor[3][3];
        downFace = new CubeColor[3][3];
        leftFace = new CubeColor[3][3];
        rightFace = new CubeColor[3][3];
        frontFace = new CubeColor[3][3];
        backFace = new CubeColor[3][3];
        corners = new Corner[8];
        edges = new Edge[12];
        solveEdges = new Edge[12];
        solveCorners = new Corner[8];
        cube = new Cubie[3][3][3];
        perspective = CubeColor.ORANGE;
        for (int i = 0; i < 8; i++)
        {
            corners[i] = new Corner();
            solveCorners[i] = new Corner();
        }
        for (int i = 0; i < 12; i++)
        {
            edges[i] = new Edge();
            solveEdges[i] = new Edge();
        }
        fillLocations();
// whiteFace[1][1] = new Center(CubeColor.WHITE);
// whiteFace[0][1] = edge1;
// whiteFace[1][0] = edge2;
// whiteFace[2][1] = edge3;
// whiteFace[1][2] = edge4;

    }

    // ----------------------------------------------------------
    /**
     * Makes the cube solved, for the solved stuff.
     */
    public void resetCube()
    {
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                upFace[i][j] = CubeColor.YELLOW;
                downFace[i][j] = CubeColor.WHITE;
                leftFace[i][j] = CubeColor.GREEN;
                rightFace[i][j] = CubeColor.BLUE;
                frontFace[i][j] = CubeColor.ORANGE;
                backFace[i][j] = CubeColor.RED;
            }
        }
        updateCube();
    }


    // ----------------------------------------------------------
    /**
     * Fills the cubies with the CubieLocations for the find methods. Also fills
     * the solve cubies.
     */
    public void fillLocations()
    {
        edges[0].setLoc1(new CubieLocation(CubeColor.WHITE, 0, 1));
        edges[0].setLoc2(new CubieLocation(CubeColor.ORANGE, 1, 0));
        edges[1].setLoc1(new CubieLocation(CubeColor.WHITE, 1, 0));
        edges[1].setLoc2(new CubieLocation(CubeColor.BLUE, 1, 0));
        edges[2].setLoc1(new CubieLocation(CubeColor.WHITE, 2, 1));
        edges[2].setLoc2(new CubieLocation(CubeColor.RED, 1, 0));
        edges[3].setLoc1(new CubieLocation(CubeColor.WHITE, 1, 2));
        edges[3].setLoc2(new CubieLocation(CubeColor.GREEN, 1, 0));
        edges[4].setLoc1(new CubieLocation(CubeColor.ORANGE, 0, 1));
        edges[4].setLoc2(new CubieLocation(CubeColor.BLUE, 2, 1));
        edges[5].setLoc1(new CubieLocation(CubeColor.ORANGE, 2, 1));
        edges[5].setLoc2(new CubieLocation(CubeColor.GREEN, 0, 1));
        edges[6].setLoc1(new CubieLocation(CubeColor.ORANGE, 1, 2));
        edges[6].setLoc2(new CubieLocation(CubeColor.YELLOW, 0, 1));
        edges[7].setLoc1(new CubieLocation(CubeColor.BLUE, 0, 1));
        edges[7].setLoc2(new CubieLocation(CubeColor.RED, 2, 1));
        edges[8].setLoc1(new CubieLocation(CubeColor.BLUE, 1, 2));
        edges[8].setLoc2(new CubieLocation(CubeColor.YELLOW, 1, 2));
        edges[9].setLoc1(new CubieLocation(CubeColor.RED, 0, 1));
        edges[9].setLoc2(new CubieLocation(CubeColor.GREEN, 2, 1));
        edges[10].setLoc1(new CubieLocation(CubeColor.RED, 1, 2));
        edges[10].setLoc2(new CubieLocation(CubeColor.YELLOW, 2, 1));
        edges[11].setLoc1(new CubieLocation(CubeColor.GREEN, 1, 2));
        edges[11].setLoc2(new CubieLocation(CubeColor.YELLOW, 1, 0));

        corners[0].setLoc1(new CubieLocation(CubeColor.WHITE, 0, 0));
        corners[0].setLoc2(new CubieLocation(CubeColor.ORANGE, 0, 0));
        corners[0].setLoc3(new CubieLocation(CubeColor.BLUE, 2, 0));
        corners[1].setLoc1(new CubieLocation(CubeColor.WHITE, 2, 0));
        corners[1].setLoc2(new CubieLocation(CubeColor.BLUE, 0, 0));
        corners[1].setLoc3(new CubieLocation(CubeColor.RED, 2, 0));
        corners[2].setLoc1(new CubieLocation(CubeColor.WHITE, 2, 2));
        corners[2].setLoc2(new CubieLocation(CubeColor.RED, 0, 0));
        corners[2].setLoc3(new CubieLocation(CubeColor.GREEN, 2, 0));
        corners[3].setLoc1(new CubieLocation(CubeColor.WHITE, 0, 2));
        corners[3].setLoc2(new CubieLocation(CubeColor.ORANGE, 2, 0));
        corners[3].setLoc3(new CubieLocation(CubeColor.GREEN, 0, 0));
        corners[4].setLoc1(new CubieLocation(CubeColor.ORANGE, 2, 2));
        corners[4].setLoc2(new CubieLocation(CubeColor.GREEN, 0, 2));
        corners[4].setLoc3(new CubieLocation(CubeColor.YELLOW, 0, 0));
        corners[5].setLoc1(new CubieLocation(CubeColor.ORANGE, 0, 2));
        corners[5].setLoc2(new CubieLocation(CubeColor.BLUE, 2, 2));
        corners[5].setLoc3(new CubieLocation(CubeColor.YELLOW, 0, 2));
        corners[6].setLoc1(new CubieLocation(CubeColor.BLUE, 0, 2));
        corners[6].setLoc2(new CubieLocation(CubeColor.RED, 2, 2));
        corners[6].setLoc3(new CubieLocation(CubeColor.YELLOW, 2, 2));
        corners[7].setLoc1(new CubieLocation(CubeColor.RED, 0, 2));
        corners[7].setLoc2(new CubieLocation(CubeColor.GREEN, 2, 2));
        corners[7].setLoc3(new CubieLocation(CubeColor.YELLOW, 2, 0));

        solveEdges[0].setLoc1(new CubieLocation(CubeColor.WHITE, 0, 1));
        solveEdges[0].setLoc2(new CubieLocation(CubeColor.ORANGE, 1, 0));
        solveEdges[1].setLoc1(new CubieLocation(CubeColor.WHITE, 1, 0));
        solveEdges[1].setLoc2(new CubieLocation(CubeColor.BLUE, 1, 0));
        solveEdges[2].setLoc1(new CubieLocation(CubeColor.WHITE, 2, 1));
        solveEdges[2].setLoc2(new CubieLocation(CubeColor.RED, 1, 0));
        solveEdges[3].setLoc1(new CubieLocation(CubeColor.WHITE, 1, 2));
        solveEdges[3].setLoc2(new CubieLocation(CubeColor.GREEN, 1, 0));
        solveEdges[4].setLoc1(new CubieLocation(CubeColor.ORANGE, 0, 1));
        solveEdges[4].setLoc2(new CubieLocation(CubeColor.BLUE, 2, 1));
        solveEdges[5].setLoc1(new CubieLocation(CubeColor.ORANGE, 2, 1));
        solveEdges[5].setLoc2(new CubieLocation(CubeColor.GREEN, 0, 1));
        solveEdges[6].setLoc1(new CubieLocation(CubeColor.ORANGE, 1, 2));
        solveEdges[6].setLoc2(new CubieLocation(CubeColor.YELLOW, 0, 1));
        solveEdges[7].setLoc1(new CubieLocation(CubeColor.BLUE, 0, 1));
        solveEdges[7].setLoc2(new CubieLocation(CubeColor.RED, 2, 1));
        solveEdges[8].setLoc1(new CubieLocation(CubeColor.BLUE, 1, 2));
        solveEdges[8].setLoc2(new CubieLocation(CubeColor.YELLOW, 1, 2));
        solveEdges[9].setLoc1(new CubieLocation(CubeColor.RED, 0, 1));
        solveEdges[9].setLoc2(new CubieLocation(CubeColor.GREEN, 2, 1));
        solveEdges[10].setLoc1(new CubieLocation(CubeColor.RED, 1, 2));
        solveEdges[10].setLoc2(new CubieLocation(CubeColor.YELLOW, 2, 1));
        solveEdges[11].setLoc1(new CubieLocation(CubeColor.GREEN, 1, 2));
        solveEdges[11].setLoc2(new CubieLocation(CubeColor.YELLOW, 1, 0));

        solveCorners[0].setLoc1(new CubieLocation(CubeColor.WHITE, 0, 0));
        solveCorners[0].setLoc2(new CubieLocation(CubeColor.ORANGE, 0, 0));
        solveCorners[0].setLoc3(new CubieLocation(CubeColor.BLUE, 2, 0));
        solveCorners[1].setLoc1(new CubieLocation(CubeColor.WHITE, 2, 0));
        solveCorners[1].setLoc2(new CubieLocation(CubeColor.BLUE, 0, 0));
        solveCorners[1].setLoc3(new CubieLocation(CubeColor.RED, 2, 0));
        solveCorners[2].setLoc1(new CubieLocation(CubeColor.WHITE, 2, 2));
        solveCorners[2].setLoc2(new CubieLocation(CubeColor.RED, 0, 0));
        solveCorners[2].setLoc3(new CubieLocation(CubeColor.GREEN, 2, 0));
        solveCorners[3].setLoc1(new CubieLocation(CubeColor.WHITE, 0, 2));
        solveCorners[3].setLoc2(new CubieLocation(CubeColor.ORANGE, 2, 0));
        solveCorners[3].setLoc3(new CubieLocation(CubeColor.GREEN, 0, 0));
        solveCorners[4].setLoc1(new CubieLocation(CubeColor.ORANGE, 2, 2));
        solveCorners[4].setLoc2(new CubieLocation(CubeColor.GREEN, 0, 2));
        solveCorners[4].setLoc3(new CubieLocation(CubeColor.YELLOW, 0, 0));
        solveCorners[5].setLoc1(new CubieLocation(CubeColor.ORANGE, 0, 2));
        solveCorners[5].setLoc2(new CubieLocation(CubeColor.BLUE, 2, 2));
        solveCorners[5].setLoc3(new CubieLocation(CubeColor.YELLOW, 0, 2));
        solveCorners[6].setLoc1(new CubieLocation(CubeColor.BLUE, 0, 2));
        solveCorners[6].setLoc2(new CubieLocation(CubeColor.RED, 2, 2));
        solveCorners[6].setLoc3(new CubieLocation(CubeColor.YELLOW, 2, 2));
        solveCorners[7].setLoc1(new CubieLocation(CubeColor.RED, 0, 2));
        solveCorners[7].setLoc2(new CubieLocation(CubeColor.GREEN, 2, 2));
        solveCorners[7].setLoc3(new CubieLocation(CubeColor.YELLOW, 2, 0));
    }


// public void setWhiteFace(int x, int y, CubeColor color)
// {
// if(whiteFace[x][y] instanceof Edge)
// {
// if(((Edge)whiteFace[x][y]).getColor1() == CubeColor.NONE)
// {
// ((Edge)whiteFace[x][y]).setColor1(color);
// }
// }
// }
// public Cubie getWhite(int x, int y)
// {
// return whiteFace[x][y];
// }
    // ----------------------------------------------------------
    /**
     * Returns a given edge
     *
     * @param x
     *            the edge we are looking for
     * @return edges[x - 1]
     */
    public Edge getEdge(int x)
    {
        return edges[x - 1];
    }


    // ----------------------------------------------------------
    /**
     * Returns the correct corner cubie
     *
     * @param i
     *            which corner you want
     * @return corner[i-1]
     */
    public Corner getCorner(int i)
    {
        return corners[i - 1];
    }

    // ----------------------------------------------------------
    /**
     * Returns the solveCorner[i]
     * @param i
     * @return corner
     */
    public Corner getSolveCorner(int i)
    {
        return solveCorners[i];
    }

    // ----------------------------------------------------------
    /**
     * Returns the solveEdge[i]
     * @param i
     * @return edge
     */
    public Edge getSolveEdge(int i)
    {
        return solveEdges[i];
    }


    /**
     * this method goes through all the different colors of the different pieces
     * and knowing their colors and orientation fills all of the 2D arrays that
     * represents the layout of the Cube
     */
    public void writeCube()
    {
        fillCube();
        writeWhiteFace();
        writeOrangeFace();
        writeBlueFace();
        writeRedFace();
        writeGreenFace();
        writeYellowFace();
    }


    // ----------------------------------------------------------
    /**
     * Fills the cube with the cubies.
     */
    public void fillCube()
    {
        cube[0][0][0] = corners[0];
        cube[1][0][0] = edges[1];
        cube[2][0][0] = corners[1];
        cube[2][1][0] = edges[2];
        cube[2][2][0] = corners[2];
        cube[1][2][0] = edges[3];
        cube[0][2][0] = corners[3];
        cube[0][1][0] = edges[0];
        cube[0][0][1] = edges[4];
        cube[1][0][1] = new Center(CubeColor.BLUE);
        cube[2][0][1] = edges[7];
        cube[2][1][1] = new Center(CubeColor.RED);
        cube[2][2][1] = edges[9];
        cube[1][2][1] = new Center(CubeColor.GREEN);
        cube[0][2][1] = edges[5];
        cube[0][1][1] = new Center(CubeColor.ORANGE);
        cube[0][0][2] = corners[5];
        cube[1][0][2] = edges[8];
        cube[2][0][2] = corners[6];
        cube[2][1][2] = edges[10];
        cube[2][2][2] = corners[7];
        cube[1][2][2] = edges[11];
        cube[0][2][2] = corners[4];
        cube[0][1][2] = edges[6];
    }


    // -------------------------------------------------
    /**
     * Fills the white face from the cubies.
     */
    public void writeWhiteFace()
    {
        whiteFace = new CubeColor[3][3];
        whiteFace[1][1] = CubeColor.WHITE;
        whiteFace[0][0] = corners[0].getColor1();
        whiteFace[1][0] = edges[1].getColor1();
        whiteFace[2][0] = corners[1].getColor1();
        whiteFace[2][1] = edges[2].getColor1();
        whiteFace[2][2] = corners[2].getColor1();
        whiteFace[1][2] = edges[3].getColor1();
        whiteFace[0][2] = corners[3].getColor1();
        whiteFace[0][1] = edges[0].getColor1();
    }
    /**
     * used primarily for testing, this method fills the cube based on a
     * set of 3 strings, the strings must each be 3 characters long
     * each character represents the color that a given face will be
     * @param s1 the first row on the face ex: a all white row would be www
     * @param s2 the second row
     * @param s3 the third row
     */
    public void writeUpFace(String s1, String s2, String s3)
    {
        upFace[0][0] = stringToColor(s1.charAt(0));
        upFace[1][0] = stringToColor(s1.charAt(1));
        upFace[2][0] = stringToColor(s1.charAt(2));
        upFace[0][1] = stringToColor(s2.charAt(0));
        upFace[1][1] = stringToColor(s2.charAt(1));
        upFace[2][1] = stringToColor(s2.charAt(2));
        upFace[0][2] = stringToColor(s3.charAt(0));
        upFace[1][2] = stringToColor(s3.charAt(1));
        upFace[2][2] = stringToColor(s3.charAt(2));
    }
    public void writeFrontFace(String s1, String s2, String s3)
    {
        frontFace[0][0] = stringToColor(s1.charAt(0));
        frontFace[1][0] = stringToColor(s1.charAt(1));
        frontFace[2][0] = stringToColor(s1.charAt(2));
        frontFace[0][1] = stringToColor(s2.charAt(0));
        frontFace[1][1] = stringToColor(s2.charAt(1));
        frontFace[2][1] = stringToColor(s2.charAt(2));
        frontFace[0][2] = stringToColor(s3.charAt(0));
        frontFace[1][2] = stringToColor(s3.charAt(1));
        frontFace[2][2] = stringToColor(s3.charAt(2));
    }
    public void writeRightFace(String s1, String s2, String s3)
    {
        rightFace[0][0] = stringToColor(s1.charAt(0));
        rightFace[1][0] = stringToColor(s1.charAt(1));
        rightFace[2][0] = stringToColor(s1.charAt(2));
        rightFace[0][1] = stringToColor(s2.charAt(0));
        rightFace[1][1] = stringToColor(s2.charAt(1));
        rightFace[2][1] = stringToColor(s2.charAt(2));
        rightFace[0][2] = stringToColor(s3.charAt(0));
        rightFace[1][2] = stringToColor(s3.charAt(1));
        rightFace[2][2] = stringToColor(s3.charAt(2));
    }
    public void writeDownFace(String s1, String s2, String s3)
    {
        downFace[0][0] = stringToColor(s1.charAt(0));
        downFace[1][0] = stringToColor(s1.charAt(1));
        downFace[2][0] = stringToColor(s1.charAt(2));
        downFace[0][1] = stringToColor(s2.charAt(0));
        downFace[1][1] = stringToColor(s2.charAt(1));
        downFace[2][1] = stringToColor(s2.charAt(2));
        downFace[0][2] = stringToColor(s3.charAt(0));
        downFace[1][2] = stringToColor(s3.charAt(1));
        downFace[2][2] = stringToColor(s3.charAt(2));
    }
    public void writeBackFace(String s1, String s2, String s3)
    {
        backFace[0][0] = stringToColor(s1.charAt(0));
        backFace[1][0] = stringToColor(s1.charAt(1));
        backFace[2][0] = stringToColor(s1.charAt(2));
        backFace[0][1] = stringToColor(s2.charAt(0));
        backFace[1][1] = stringToColor(s2.charAt(1));
        backFace[2][1] = stringToColor(s2.charAt(2));
        backFace[0][2] = stringToColor(s3.charAt(0));
        backFace[1][2] = stringToColor(s3.charAt(1));
        backFace[2][2] = stringToColor(s3.charAt(2));
    }
    public void writeLeftFace(String s1, String s2, String s3)
    {
        leftFace[0][0] = stringToColor(s1.charAt(0));
        leftFace[1][0] = stringToColor(s1.charAt(1));
        leftFace[2][0] = stringToColor(s1.charAt(2));
        leftFace[0][1] = stringToColor(s2.charAt(0));
        leftFace[1][1] = stringToColor(s2.charAt(1));
        leftFace[2][1] = stringToColor(s2.charAt(2));
        leftFace[0][2] = stringToColor(s3.charAt(0));
        leftFace[1][2] = stringToColor(s3.charAt(1));
        leftFace[2][2] = stringToColor(s3.charAt(2));
    }

    public CubeColor stringToColor(char s)
    {
       if(s == 'w')
                return CubeColor.WHITE;
       else if(s == 'r')
                return CubeColor.RED;
       else if(s == 'b')
           return CubeColor.BLUE;
       else if(s == 'y')
           return CubeColor.YELLOW;
       else if(s == 'g')
           return CubeColor.GREEN;
       else if(s == 'o')
           return CubeColor.ORANGE;
       else
           return CubeColor.NONE;
    }
    /**
     * Fills the orange face from the cubies.
     */
    public void writeOrangeFace()
    {
        orangeFace = new CubeColor[3][3];
        orangeFace[1][1] = CubeColor.ORANGE;
        orangeFace[0][0] = corners[0].getColor2();
        orangeFace[1][0] = edges[0].getColor2();
        orangeFace[2][0] = corners[3].getColor2();
        orangeFace[2][1] = edges[5].getColor1();
        orangeFace[2][2] = corners[4].getColor1();
        orangeFace[1][2] = edges[6].getColor1();
        orangeFace[0][2] = corners[5].getColor1();
        orangeFace[0][1] = edges[4].getColor1();

    }


    /**
     * Fills the blue face from the cubies.
     */
    public void writeBlueFace()
    {
        blueFace = new CubeColor[3][3];
        blueFace[1][1] = CubeColor.BLUE;
        blueFace[0][0] = corners[1].getColor2();
        blueFace[1][0] = edges[1].getColor2();
        blueFace[2][0] = corners[0].getColor3();
        blueFace[2][1] = edges[4].getColor2();
        blueFace[2][2] = corners[5].getColor2();
        blueFace[1][2] = edges[8].getColor1();
        blueFace[0][2] = corners[6].getColor1();
        blueFace[0][1] = edges[7].getColor1();
    }


    /**
     * Fills the red face from the cubies.
     */
    public void writeRedFace()
    {
        redFace = new CubeColor[3][3];
        redFace[1][1] = CubeColor.RED;
        redFace[0][0] = corners[2].getColor2();
        redFace[1][0] = edges[2].getColor2();
        redFace[2][0] = corners[1].getColor3();
        redFace[2][1] = edges[7].getColor2();
        redFace[2][2] = corners[6].getColor2();
        redFace[1][2] = edges[10].getColor1();
        redFace[0][2] = corners[7].getColor1();
        redFace[0][1] = edges[9].getColor1();

    }


    /**
     * Fills the greem face from the cubies.
     */
    public void writeGreenFace()
    {
        greenFace = new CubeColor[3][3];
        greenFace[1][1] = CubeColor.GREEN;
        greenFace[0][0] = corners[3].getColor3();
        greenFace[1][0] = edges[3].getColor2();
        greenFace[2][0] = corners[2].getColor3();
        greenFace[2][1] = edges[9].getColor2();
        greenFace[2][2] = corners[7].getColor2();
        greenFace[1][2] = edges[11].getColor1();
        greenFace[0][2] = corners[4].getColor2();
        greenFace[0][1] = edges[5].getColor2();

    }


    /**
     * Fills the yellow face from the cubies.
     */
    public void writeYellowFace()
    {
        yellowFace = new CubeColor[3][3];
        yellowFace[1][1] = CubeColor.YELLOW;
        yellowFace[0][0] = corners[4].getColor3();
        yellowFace[1][0] = edges[11].getColor2();
        yellowFace[2][0] = corners[7].getColor3();
        yellowFace[2][1] = edges[10].getColor2();
        yellowFace[2][2] = corners[6].getColor3();
        yellowFace[1][2] = edges[8].getColor2();
        yellowFace[0][2] = corners[5].getColor3();
        yellowFace[0][1] = edges[6].getColor2();
    }


    // ----------------------------------------------------------
    /**
     * Used while solving. Fills the temporary cubies with the correct colors.
     */
    public void updateCube()
    {
        solveCorners[0].setColor1(downFace[0][0]);
        solveEdges[1].setColor1(downFace[1][0]);
        solveCorners[1].setColor1(downFace[2][0]);
        solveEdges[2].setColor1(downFace[2][1]);
        solveCorners[2].setColor1(downFace[2][2]);
        solveEdges[3].setColor1(downFace[1][2]);
        solveCorners[3].setColor1(downFace[0][2]);
        solveEdges[0].setColor1(downFace[0][1]);

        solveCorners[0].setColor2(frontFace[0][0]);
        solveEdges[0].setColor2(frontFace[1][0]);
        solveCorners[3].setColor2(frontFace[2][0]);
        solveEdges[5].setColor1(frontFace[2][1]);
        solveCorners[4].setColor1(frontFace[2][2]);
        solveEdges[6].setColor1(frontFace[1][2]);
        solveCorners[5].setColor1(frontFace[0][2]);
        solveEdges[4].setColor1(frontFace[0][1]);

        solveCorners[1].setColor2(rightFace[0][0]);
        solveEdges[1].setColor2(rightFace[1][0]);
        solveCorners[0].setColor3(rightFace[2][0]);
        solveEdges[4].setColor2(rightFace[2][1]);
        solveCorners[5].setColor2(rightFace[2][2]);
        solveEdges[8].setColor1(rightFace[1][2]);
        solveCorners[6].setColor1(rightFace[0][2]);
        solveEdges[7].setColor1(rightFace[0][1]);

        solveCorners[2].setColor2(backFace[0][0]);
        solveEdges[2].setColor2(backFace[1][0]);
        solveCorners[1].setColor3(backFace[2][0]);
        solveEdges[7].setColor2(backFace[2][1]);
        solveCorners[6].setColor2(backFace[2][2]);
        solveEdges[10].setColor1(backFace[1][2]);
        solveCorners[7].setColor1(backFace[0][2]);
        solveEdges[9].setColor1(backFace[0][1]);

        solveCorners[3].setColor3(leftFace[0][0]);
        solveEdges[3].setColor2(leftFace[1][0]);
        solveCorners[2].setColor3(leftFace[2][0]);
        solveEdges[9].setColor2(leftFace[2][1]);
        solveCorners[7].setColor2(leftFace[2][2]);
        solveEdges[11].setColor1(leftFace[1][2]);
        solveCorners[4].setColor2(leftFace[0][2]);
        solveEdges[5].setColor2(leftFace[0][1]);

        solveCorners[4].setColor3(upFace[0][0]);
        solveEdges[11].setColor2(upFace[1][0]);
        solveCorners[7].setColor3(upFace[2][0]);
        solveEdges[10].setColor2(upFace[2][1]);
        solveCorners[6].setColor3(upFace[2][2]);
        solveEdges[8].setColor2(upFace[1][2]);
        solveCorners[5].setColor3(upFace[0][2]);
        solveEdges[6].setColor2(upFace[0][1]);

        fillLocations();
    }


    // ----------------------------------------------------------
    /**
     * toString method for enum CubeColor
     *
     * @param color
     *            the color
     * @return one character string representing the color.
     */
    public String colorToString(CubeColor color)
    {
        switch (color)
        {
            case WHITE:
                return "w";
            case BLUE:
                return "b";
            case GREEN:
                return "g";
            case ORANGE:
                return "o";
            case RED:
                return "r";
            case YELLOW:
                return "y";
            case NONE:
                return "n";
        }
        return "";
    }


    // {
    // "       |y y y|"
    // "       |y<--y|"
    // "       |y y y|"
    // "       "
    // "|g | g||o | o||b | b||r | r|"
    // "|g V g||o V o||b V b||r V r|"
    // "|g g g||o o o||b b b||r r r|"
    // "       "
    // "       |w w w|"
    // "       |w-->w|"
    // "       |w w w|"
    //
    //
    // }

    /**
     * Sets solution to be a queue with the moves, in order, to solve the cube.
     * We will assume that white is the bottom face, orange is front, and blue
     * is right.
     */
    public void solve()
    {
        prepare();
        // Solves the cube, from bottom to top
        solveBottomLayer();
        solveMiddleLayer();
        solveTopLayer();
    }

    // ----------------------------------------------------------
    /**
     * Preps the cube for surgery.
     */
    public void prepare()
    {
        // First, copies the cube to manipulate for solving.
        downFace = whiteFace;
        upFace = yellowFace;
        frontFace = orangeFace;
        backFace = redFace;
        leftFace = greenFace;
        rightFace = blueFace;
        solveEdges = edges;
        solveCorners = corners;
        // Resets the solution queue.
        solution = new LinkedList<Move>();
    }


    // ----------------------------------------------------------
    /**
     * Solves the bottom (white) layer of the cube.
     */
    public void solveBottomLayer()
    {
        whiteCross();
        whiteCorners();
    }

    // ----------------------------------------------------------
    /**
     * Puts a cross on the white layer.
     */
    public void whiteCross()
    {
        for (int i = 0; i < 4; i++)
        {
            setPerspective(intToColor(i));
            CubieLocation whiteLoc =
                findEdgeLoc(CubeColor.WHITE, intToColor(i));
            CubieLocation otherLoc =
                findEdgeLoc(intToColor(i), CubeColor.WHITE);
            // Case where the cubie is on the white layer
            // possibly in the wrong location.
            if (whiteLoc.getFace() == CubeColor.WHITE)
            {
                if (otherLoc.getFace() != intToColor(i))
                {
                    setPerspective(otherLoc.getFace());
                    front();
                    switch (i - (otherLoc.getFace().getInt()) % 4)
                    {
                        case 1:
                            down();
                            frontPrime();
                            downPrime();
                            break;
                        case 2:
                            down();
                            down();
                            frontPrime();
                            down();
                            down();
                            break;
                        case 3:
                            downPrime();
                            frontPrime();
                            down();
                            break;
                        default:
                            frontPrime();
                            break;
                    }
                }
            }
            // Case where the cubie is on the top face.
            else if (whiteLoc.getFace() == CubeColor.YELLOW)
            {
                setPerspective(otherLoc.getFace());
                switch ((i - otherLoc.getFace().getInt()) % 4)
                {
                    case 1:
                        down();
                        front();
                        front();
                        downPrime();
                        break;
                    case 2:
                        down();
                        down();
                        front();
                        front();
                        down();
                        down();
                        break;
                    case 3:
                        downPrime();
                        front();
                        front();
                        down();
                        break;
                    default:
                        front();
                        front();
                        break;
                }
            }
            // Case where cubie is on bottom but inverted.
            else if (otherLoc.getFace() == CubeColor.WHITE)
            {
                setPerspective(whiteLoc.getFace());
                switch ((i - whiteLoc.getFace().getInt()) % 4)
                {
                    case 0:
                        front();
                        downPrime();
                        left();
                        down();
                        break;
                    case 1:
                        front();
                        left();
                        break;
                    case 2:
                        front();
                        down();
                        left();
                        downPrime();
                        break;
                    default:
                        frontPrime();
                        rightPrime();
                        break;
                }
            }
            // Case where cubie is on top but inverted.
            else if (otherLoc.getFace() == CubeColor.YELLOW)
            {
                setPerspective(whiteLoc.getFace());
                switch ((i - whiteLoc.getFace().getInt()) % 4)
                {
                    case 0:
                        frontPrime();
                        downPrime();
                        left();
                        down();
                        break;
                    case 1:
                        frontPrime();
                        left();
                        front();
                        break;
                    case 2:
                        down();
                        frontPrime();
                        left();
                        front();
                        downPrime();
                        break;
                    default:
                        front();
                        rightPrime();
                        frontPrime();
                        break;
                }
            }
            // Case where cubie is in the middle layer.
            else
            {
                setPerspective(otherLoc.getFace());
                boolean prime;
                if (otherLoc.getFace().getInt()%4 == (whiteLoc.getFace().getInt()+1)%4)
                {
                    prime = false;
                }
                else
                {
                    prime = true;
                }
                switch ((i - otherLoc.getFace().getInt()) % 4)
                {
                    case 0:
                        if (prime)
                        {
                            front();
                        }
                        else
                        {
                            frontPrime();
                        }
                        break;
                    case 1:
                        down();
                        if (prime)
                        {
                            front();
                        }
                        else
                        {
                            frontPrime();
                        }
                        downPrime();
                        break;
                    case 2:
                        down();
                        down();
                        if (prime)
                        {
                            front();
                        }
                        else
                        {
                            frontPrime();
                        }
                        down();
                        down();
                        break;
                    default:
                        downPrime();
                        if (prime)
                        {
                            front();
                        }
                        else
                        {
                            frontPrime();
                        }
                        down();
                        break;
                }
            }

        }
    }

    // ----------------------------------------------------------
    /**
     * Solves the white corners
     */
    public void whiteCorners()
    {
        for (int i = 0; i < 4; i++)
        {
            // Finds the corners.
            CubieLocation whiteLoc =
                findCornerLoc(
                    CubeColor.WHITE,
                    intToColor(i),
                    intToColor((i - 1) % 4));
            CubieLocation targetLoc =
                findCornerLoc(
                    intToColor(i),
                    CubeColor.WHITE,
                    intToColor((i - 1) % 4));
            CubieLocation thirdLoc =
                findCornerLoc(
                    intToColor((i - 1) % 4),
                    CubeColor.WHITE,
                    intToColor(i));
            // If the white face is already on the bottom but wrong.
            if (whiteLoc.getFace() == CubeColor.WHITE)
            {
                setPerspective(targetLoc.getFace());
                switch ((i - targetLoc.getFace().getInt()) % 4)
                {
                    case 0:
                        break;
                    case 1:
                        leftPrime();
                        upPrime();
                        left();
                        upPrime();
                        frontPrime();
                        up();
                        front();
                        break;
                    case 2:
                        leftPrime();
                        upPrime();
                        left();
                        up();
                        up();
                        rightPrime();
                        up();
                        right();
                        break;
                    default:
                        leftPrime();
                        upPrime();
                        left();
                        up();
                        backPrime();
                        up();
                        back();
                }
            }
            else
            {
                // If the cubie is on the bottom, get it out.
                if (targetLoc.getFace() == CubeColor.WHITE)
                {
                    setPerspective(thirdLoc.getFace());
                    leftPrime();
                    upPrime();
                    left();
                }
                if (thirdLoc.getFace() == CubeColor.WHITE)
                {
                    setPerspective(whiteLoc.getFace());
                    leftPrime();
                    upPrime();
                    left();
                }
                // Reset where the cubie is.
                whiteLoc =
                    findCornerLoc(
                        CubeColor.WHITE,
                        intToColor(i),
                        intToColor((i - 1) % 4));
                targetLoc =
                    findCornerLoc(
                        intToColor(i),
                        CubeColor.WHITE,
                        intToColor((i - 1) % 4));
                thirdLoc =
                    findCornerLoc(
                        intToColor((i - 1) % 4),
                        CubeColor.WHITE,
                        intToColor(i));
                // If the white face is on the top
                if (whiteLoc.getFace() == CubeColor.YELLOW)
                {
                    switch ((i - thirdLoc.getFace().getInt()) % 4)
                    {
                        case 0:
                            break;
                        case 1:
                            upPrime();
                            break;
                        case 2:
                            up();
                            up();
                            break;
                        default:
                            up();
                            break;
                    }
                    setPerspective(intToColor(i));
                    leftPrime();
                    up();
                    up();
                    left();
                    up();
                    leftPrime();
                    upPrime();
                    left();
                }
                // If the target color is on top.
                else if (targetLoc.getFace() == CubeColor.YELLOW)
                {
                    switch ((i - whiteLoc.getFace().getInt()) % 4)
                    {
                        case 0:
                            break;
                        case 1:
                            upPrime();
                            break;
                        case 2:
                            up();
                            up();
                            break;
                        default:
                            up();
                            break;
                    }
                    setPerspective(whiteLoc.getFace());
                    front();
                    up();
                    frontPrime();
                }
                // If the third color is on top.
                else if (thirdLoc.getFace() == CubeColor.YELLOW)
                {
                    switch ((i - targetLoc.getFace().getInt()) % 4)
                    {
                        case 0:
                            break;
                        case 1:
                            upPrime();
                            break;
                        case 2:
                            up();
                            up();
                            break;
                        default:
                            up();
                            break;
                    }
                    setPerspective(whiteLoc.getFace());
                    frontPrime();
                    upPrime();
                    front();
                }
            }
        }
    }


    // ----------------------------------------------------------
    /**
     * Solves the middle layer of the cube, given a solved bottom layer.
     */
    public void solveMiddleLayer()
    {
        while (!middleDone())
        {
            Edge edge;
            // First four options: edge is on the top layer
            if (solveEdges[6].contains(CubeColor.YELLOW) == false)
            {
                edge = solveEdges[6];
            }
            else if (solveEdges[8].contains(CubeColor.YELLOW) == false)
            {
                edge = solveEdges[8];
            }
            else if (solveEdges[10].contains(CubeColor.YELLOW) == false)
            {
                edge = solveEdges[10];
            }
            else if (solveEdges[11].contains(CubeColor.YELLOW) == false)
            {
                edge = solveEdges[11];
            }

            else if (frontFace[2][1] != CubeColor.ORANGE
                || rightFace[0][1] != CubeColor.BLUE)
            {
                edge = solveEdges[4];
            }
            else if (rightFace[2][1] != CubeColor.BLUE
                || backFace[0][1] != CubeColor.RED)
            {
                edge = solveEdges[5];
            }
            else if (backFace[2][1] != CubeColor.RED
                || leftFace[0][1] != CubeColor.GREEN)
            {
                edge = solveEdges[7];
            }
            else
            // The last one has to be wrong, or we would be finished.
            {
                edge = solveEdges[9];
            }
            if (edge.getLoc2().getFace() == CubeColor.YELLOW)
            {
                if (edge.getColor2() == CubeColor.YELLOW)
                {
                    setPerspective(edge.getColor1());
                }
                else
                {
                    setPerspective(edge.getColor2());
                }
                switch ((perspective.getInt() - edge.getLoc1().getFace()
                    .getInt()) % 4)
                {
                    case 1:
                        up();
                        break;
                    case 2:
                        up();
                        up();
                        break;
                    case 3:
                        upPrime();
                        break;
                    default:
                        break;
                }
                if ((perspective.getInt() - edge.getLoc2().getFace().getInt()) % 4 == 1)
                {
                    insertToTheRight();
                }
                else
                {
                    insertToTheLeft();
                }
            }
            else
            {
                boolean right;
                if (edge.getLoc1().equals(
                    new CubieLocation(CubeColor.WHITE, 0, 1)))
                {
                    right = true;
                    setPerspective(CubeColor.WHITE);
                }
                else if (edge.getLoc1().equals(
                    new CubieLocation(CubeColor.WHITE, 2, 1)))
                {
                    right = false;
                    setPerspective(CubeColor.WHITE);
                }
                else if (edge.getLoc1().equals(
                    new CubieLocation(CubeColor.RED, 0, 1)))
                {
                    right = true;
                    setPerspective(CubeColor.RED);
                }
                else
                {
                    right = false;
                    setPerspective(CubeColor.RED);
                }
                switch (perspective)
                {
                    case RED:
                        edge = solveEdges[10];
                    case BLUE:
                        edge = solveEdges[11];
                    case GREEN:
                        edge = solveEdges[8];
                    default:
                        edge = solveEdges[6];
                }
                while (edge.contains(CubeColor.YELLOW) == false)
                {
                    up();
                }
                if (right)
                {
                    insertToTheRight();
                }
                else
                {
                    insertToTheLeft();
                }
            }
        }
    }


    // ----------------------------------------------------------
    /**
     * Returns the front face (of the solving faces)
     *
     * @return The front face, given current perspective
     */
    public CubeColor[][] getFront()
    {
        switch (perspective)
        {
            case RED:
                return backFace;
            case GREEN:
                return leftFace;
            case BLUE:
                return rightFace;
            default:
                return frontFace;
        }
    }


    // ----------------------------------------------------------
    /**
     * Returns true if the middle layer is solved correctly.
     *
     * @return True if each edge in the middle layer is correct AND correctly
     *         oriented.
     */
    public boolean middleDone()
    {
        return (findEdgeLoc(CubeColor.ORANGE, CubeColor.BLUE).equals(
            new CubieLocation(CubeColor.ORANGE, 0, 1))
            && findEdgeLoc(CubeColor.BLUE, CubeColor.RED).equals(
                new CubieLocation(CubeColor.BLUE, 1, 0))
            && findEdgeLoc(CubeColor.RED, CubeColor.GREEN).equals(
                new CubieLocation(CubeColor.RED, 1, 0)) && findEdgeLoc(
                CubeColor.GREEN,
                CubeColor.ORANGE).equals(
            new CubieLocation(CubeColor.GREEN, 1, 0)));
    }


    // ----------------------------------------------------------
    /**
     * Inserts a piece from the top of a (front) face to the left of it.
     */
    public void insertToTheLeft()
    {
        upPrime();
        leftPrime();
        up();
        left();
        up();
        front();
        upPrime();
        frontPrime();
    }


    /**
     * Inserts a piece from the top of a (front) face to the right of it.
     */
    public void insertToTheRight()
    {
        up();
        right();
        upPrime();
        rightPrime();
        upPrime();
        frontPrime();
        up();
        front();
    }


    // ----------------------------------------------------------
    /**
     * Given a solved bottom and middle layer, solves the top layer
     */
    public void solveTopLayer()
    {
        solveYellowCross();
        fixYellowCross();
        insertYellowCorners();
        orientYellowCorners();
    }


    // ----------------------------------------------------------
    /**
     * Makes a cross on the top layer. Does not account for side layers.
     */
    public void solveYellowCross()
    {
        if (upFace[0][1] == CubeColor.YELLOW
            && upFace[1][0] == CubeColor.YELLOW
            && upFace[2][1] == CubeColor.YELLOW
            && upFace[1][2] == CubeColor.YELLOW)
        {
            // Cross is done
        }
        else if (upFace[0][1] == CubeColor.YELLOW
            && upFace[2][1] == CubeColor.YELLOW)
        {
            setPerspective(CubeColor.BLUE);
            yellowLineToCross();
        }
        else if (upFace[1][0] == CubeColor.YELLOW
            && upFace[1][2] == CubeColor.YELLOW)
        {
            setPerspective(CubeColor.ORANGE);
            yellowLineToCross();
        }
        else if (upFace[1][0] == CubeColor.YELLOW
            && upFace[0][1] == CubeColor.YELLOW)
        {
            setPerspective(CubeColor.BLUE);
            yellowLToCross();
        }
        else if (upFace[1][0] == CubeColor.YELLOW
            && upFace[2][1] == CubeColor.YELLOW)
        {
            setPerspective(CubeColor.ORANGE);
            yellowLToCross();
        }
        else if (upFace[2][1] == CubeColor.YELLOW
            && upFace[1][2] == CubeColor.YELLOW)
        {
            setPerspective(CubeColor.GREEN);
            yellowLToCross();
        }
        else if (upFace[1][2] == CubeColor.YELLOW
            && upFace[0][1] == CubeColor.YELLOW)
        {
            setPerspective(CubeColor.RED);
            yellowLToCross();
        }
    }


    // ----------------------------------------------------------
    /**
     * Case if the top layer has a line; turns it into a cross. PRECONDITION:
     * The perspective is such that the line is horizontal
     */
    public void yellowLineToCross()
    {
        front();
        right();
        up();
        rightPrime();
        upPrime();
        frontPrime();
    }


    // ----------------------------------------------------------
    /**
     * Case if the top layer has an L; turns it into a cross. PRECONDITION: The
     * perspective is such that the L is upright but backwards
     */
    public void yellowLToCross()
    {
        front();
        up();
        right();
        upPrime();
        rightPrime();
        frontPrime();
    }


    // ----------------------------------------------------------
    /**
     * Given the yellow cross, swaps edges so that each is in the right spot.
     */
    public void fixYellowCross()
    {
        for (int i = 0; i < 3; i++)
        {
            CubieLocation loc = findEdgeLoc(intToColor(i), CubeColor.YELLOW);
            int n = (loc.getFace().getInt() - i) % 4;
            setPerspective(loc.getFace());
            for (int j = 0; j < n; j++)
            {
                setPerspective(Move.LEFT);
                switchAdjacentEdges();
            }
        }
    }


    // ----------------------------------------------------------
    /**
     * Swaps the top edges of the front and left face.
     */
    public void switchAdjacentEdges()
    {
        right();
        up();
        rightPrime();
        up();
        right();
        up();
        up();
        rightPrime();
        up();
    }


    // ----------------------------------------------------------
    /**
     * Puts all of the yellow Corners in the correct place.
     */
    public void insertYellowCorners()
    {
        if (cornersGood())
        {
            return;
        }
        if (solveCorners[4] == findCorner(
            CubeColor.YELLOW,
            CubeColor.GREEN,
            CubeColor.ORANGE))
        {
            setPerspective(CubeColor.GREEN);
            rotateCorners();
        }
        else if (solveCorners[7] == findCorner(
            CubeColor.YELLOW,
            CubeColor.GREEN,
            CubeColor.RED))
        {
            setPerspective(CubeColor.RED);
            rotateCorners();
        }
        else if (solveCorners[6] == findCorner(
            CubeColor.YELLOW,
            CubeColor.BLUE,
            CubeColor.RED))
        {
            setPerspective(CubeColor.BLUE);
            rotateCorners();
        }
        else if (solveCorners[5] == findCorner(
            CubeColor.YELLOW,
            CubeColor.BLUE,
            CubeColor.ORANGE))
        {
            setPerspective(CubeColor.ORANGE);
            rotateCorners();
        }
        else
        {
            rotateCorners();
        }
        insertYellowCorners();
    }


    // ----------------------------------------------------------
    /**
     * Determines whether the corners are in the correct places.
     *
     * @return true if all four yellow corners are in the right place.
     */
    public boolean cornersGood()
    {
        return (solveCorners[4] == findCorner(
            CubeColor.YELLOW,
            CubeColor.GREEN,
            CubeColor.ORANGE)
            && solveCorners[7] == findCorner(
                CubeColor.YELLOW,
                CubeColor.GREEN,
                CubeColor.RED)
            && solveCorners[6] == findCorner(
                CubeColor.YELLOW,
                CubeColor.BLUE,
                CubeColor.RED) && solveCorners[5] == findCorner(
                CubeColor.YELLOW,
                CubeColor.BLUE,
                CubeColor.ORANGE));
    }


    // ----------------------------------------------------------
    /**
     * Changes position of the top three corners, except for the front right one
     */
    public void rotateCorners()
    {
        up();
        right();
        upPrime();
        leftPrime();
        up();
        rightPrime();
        upPrime();
        left();
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void orientYellowCorners()
    {
        for (int i = 0; i < 4; i++)
        {
            setPerspective(intToColor(i));
            while (getFront()[0][2] != perspective)
            {
                spinYellowCorners();
            }
            upPrime();
        }
    }


    // ----------------------------------------------------------
    /**
     * Part of the corner rotation method.
     */
    public void spinYellowCorners()
    {
        rightPrime();
        downPrime();
        right();
        down();
    }


    // ----------------------------------------------------------
    /**
     * Given two colors, finds the location of the edge cubie. The location
     * returned will be the face containing color1.
     *
     * @param color1
     *            The color which is on the face
     * @param color2
     *            The color which is adjacent to the face
     * @return the CubieLocation of the Edge we are looking for. PRECONDITION:
     *         The edgeCubie exists in the cube exactly once
     */
    public CubieLocation findEdgeLoc(CubeColor color1, CubeColor color2)
    {
        for (Edge e : solveEdges)
        {
            if (color1 == e.getColor1() && color2 == e.getColor2())
            {
                return e.getLoc1();
            }
            if (color2 == e.getColor1() && color1 == e.getColor2())
            {
                return e.getLoc2();
            }
        }
        return new CubieLocation(CubeColor.NONE, -1, -1);
    }


    // ----------------------------------------------------------
    /**
     * Returns an edge with two colors
     *
     * @param color1
     *            first color
     * @param color2
     *            second color
     * @return the edge
     */
    public Edge findEdge(CubeColor color1, CubeColor color2)
    {
        for (Edge e : solveEdges)
        {
            if (color1 == e.getColor1() && color2 == e.getColor2())
            {
                return e;
            }
            if (color2 == e.getColor1() && color1 == e.getColor2())
            {
                return e;
            }
        }
        return null;
    }


    /**
     * Given two colors, finds the location of the edge cubie. The location
     * returned will be the face containing color1.
     *
     * @param color1
     *            The color which is on the face
     * @param color2
     *            The color which is adjacent to the face
     * @param color3
     *            The color which is also adjacent to the face
     * @return the CubieLocation of the Edge we are looking for. PRECONDITION:
     *         The edgeCubie exists in the cube exactly once
     */
    public CubieLocation findCornerLoc(
        CubeColor color1,
        CubeColor color2,
        CubeColor color3)
    {
        for (Corner c : solveCorners)
        {
            if (color1 == c.getColor1()
                && (color2 == c.getColor2() || color2 == c.getColor3())
                && (color3 == c.getColor2() || color3 == c.getColor3()))
            {
                return c.getLoc1();
            }
            if (color1 == c.getColor2()
                && (color2 == c.getColor1() || color2 == c.getColor3())
                && (color3 == c.getColor1() || color3 == c.getColor3()))
            {
                return c.getLoc2();
            }
            if (color1 == c.getColor3()
                && (color2 == c.getColor2() || color2 == c.getColor1())
                && (color3 == c.getColor2() || color3 == c.getColor1()))
            {
                return c.getLoc3();
            }
        }
        return new CubieLocation(CubeColor.NONE, -1, -1);
    }


    // ----------------------------------------------------------
    /**
     * Returns the corner containing the three colors
     *
     * @param color1
     *            first color
     * @param color2
     *            second color
     * @param color3
     *            third color
     * @return corner
     */
    public Corner findCorner(
        CubeColor color1,
        CubeColor color2,
        CubeColor color3)
    {
        for (Corner c : solveCorners)
        {
            if (color1 == c.getColor1()
                && (color2 == c.getColor2() || color2 == c.getColor3())
                && (color3 == c.getColor2() || color3 == c.getColor3()))
            {
                return c;
            }
            if (color1 == c.getColor2()
                && (color2 == c.getColor1() || color2 == c.getColor3())
                && (color3 == c.getColor1() || color3 == c.getColor3()))
            {
                return c;
            }
            if (color1 == c.getColor3()
                && (color2 == c.getColor2() || color2 == c.getColor1())
                && (color3 == c.getColor2() || color3 == c.getColor1()))
            {
                return c;
            }
        }
        return null;
    }


    // ----------------------------------------------------------
    /**
     * Rotates the given face one clockwise turn. Does not update the cubies on
     * adjacent face.
     *
     * @param face
     *            the face to rotate.
     */
    public void rotateClockwise(CubeColor[][] face)
    {
        CubeColor temp = face[0][1];
        face[0][1] = face[1][2];
        face[1][2] = face[2][1];
        face[2][1] = face[1][0];
        face[1][0] = temp;
        temp = face[0][0];
        face[0][0] = face[0][2];
        face[0][2] = face[2][2];
        face[2][2] = face[2][0];
        face[2][0] = temp;
    }


    // ----------------------------------------------------------
    /**
     * Performs a clockwise turn on the down face. Only updates the faces, not
     * the cubies.
     */
    public void d()
    {
        rotateClockwise(downFace);
        for (int i = 0; i < 2; i++)
        {
            CubeColor temp = frontFace[i][0];
            frontFace[i][0] = leftFace[i][0];
            leftFace[i][0] = backFace[i][0];
            backFace[i][0] = rightFace[i][0];
            rightFace[i][0] = temp;
        }
    }


    // ----------------------------------------------------------
    /**
     * Performs a clockwise turn on the up face. Only updates the faces, not the
     * cubies.
     */
    public void u()
    {
        rotateClockwise(upFace);
        for (int i = 0; i < 3; i++)
        {
            CubeColor temp = rightFace[i][2];
            rightFace[i][2] = backFace[i][2];
            backFace[i][2] = leftFace[i][2];
            leftFace[i][2] = frontFace[i][2];
            frontFace[i][2] = temp;
        }
    }


    /**
     * Performs a clockwise turn on the front face. Only updates the faces, not
     * the cubies.
     */
    public void f()
    {
        rotateClockwise(frontFace);
        for (int i = 0; i < 3; i++)
        {
            CubeColor temp = downFace[0][i];
            downFace[0][i] = rightFace[2][2 - i];
            rightFace[2][2 - i] = upFace[0][i];
            upFace[0][i] = leftFace[0][i];
            leftFace[0][i] = temp;
        }
    }


    /**
     * Performs a clockwise turn on the back face. Only updates the faces, not
     * the cubies.
     */
    public void b()
    {
        rotateClockwise(backFace);
        for (int i = 0; i < 3; i++)
        {
            CubeColor temp = downFace[2][i];
            downFace[2][i] = leftFace[2][i];
            leftFace[2][i] = upFace[2][i];
            upFace[2][i] = rightFace[0][2 - i];
            rightFace[0][2 - i] = temp;
        }
    }


    /**
     * Performs a clockwise turn on the left face. Only updates the faces, not
     * the cubies.
     */
    public void l()
    {
        rotateClockwise(leftFace);
        for (int i = 0; i < 3; i++)
        {
            CubeColor temp = downFace[i][2];
            downFace[i][2] = frontFace[2][2 - i];
            frontFace[2][2 - i] = upFace[2 - i][0];
            upFace[2 - i][0] = backFace[0][i];
            backFace[0][i] = temp;
        }
    }


    /**
     * Performs a clockwise turn on the right face. Only updates the faces, not
     * the cubies.
     */
    public void r()
    {
        rotateClockwise(rightFace);
        for (int i = 0; i < 3; i++)
        {
            CubeColor temp = downFace[2 - i][0];
            downFace[2 - i][0] = backFace[2][2 - i];
            backFace[2][2 - i] = upFace[i][2];
            upFace[i][2] = frontFace[0][i];
            frontFace[0][i] = temp;
        }
    }


    // ----------------------------------------------------------
    /**
     * Rotates the down face clockwise from an absolute perspective. Updated
     * cubies and adds move to the solution queue.
     */
    public void down()
    {
        d();
        updateCube();
        solution.add(Move.DOWN);
    }


    /**
     * Rotates the down face counterclockwise from an absolute perspective.
     * Updated cubies and adds move to the solution queue.
     */
    public void downPrime()
    {
        d();
        d();
        d();
        updateCube();
        solution.add(Move.DOWN_P);
    }


    /**
     * Rotates the up face clockwise from an absolute perspective. Updated
     * cubies and adds move to the solution queue.
     */
    public void up()
    {
        u();
        updateCube();
        solution.add(Move.UP);
    }


    /**
     * Rotates the up face counterclockwise from an absolute perspective.
     * Updated cubies and adds move to the solution queue.
     */
    public void upPrime()
    {
        u();
        u();
        u();
        updateCube();
        solution.add(Move.UP_P);
    }


    /**
     * Rotates the front face clockwise from an absolute perspective. Updated
     * cubies and adds move to the solution queue.
     */
    public void fTurn()
    {
        f();
        updateCube();
        solution.add(Move.FRONT);
    }


    /**
     * Rotates the front face counterclockwise from an absolute perspective.
     * Updated cubies and adds move to the solution queue.
     */
    public void fPrime()
    {
        f();
        f();
        f();
        updateCube();
        solution.add(Move.FRONT_P);
    }


    /**
     * Rotates the back face clockwise from an absolute perspective. Updated
     * cubies and adds move to the solution queue.
     */
    public void bTurn()
    {
        b();
        updateCube();
        solution.add(Move.BACK);
    }


    /**
     * Rotates the back face counterclockwise from an absolute perspective.
     * Updated cubies and adds move to the solution queue.
     */
    public void bPrime()
    {
        b();
        b();
        b();
        updateCube();
        solution.add(Move.BACK_P);
    }


    /**
     * Rotates the left face clockwise from an absolute perspective. Updated
     * cubies and adds move to the solution queue.
     */
    public void lTurn()
    {
        l();
        updateCube();
        solution.add(Move.LEFT);
    }


    /**
     * Rotates the left face counterclockwise from an absolute perspective.
     * Updated cubies and adds move to the solution queue.
     */
    public void lPrime()
    {
        l();
        l();
        l();
        updateCube();
        solution.add(Move.LEFT_P);
    }


    /**
     * Rotates the right face clockwise from an absolute perspective. Updated
     * cubies and adds move to the solution queue.
     */
    public void rTurn()
    {
        r();
        updateCube();
        solution.add(Move.RIGHT);
    }


    /**
     * Rotates the right face counterclockwise from an absolute perspective.
     * Updated cubies and adds move to the solution queue.
     */
    public void rPrime()
    {
        r();
        r();
        r();
        updateCube();
        solution.add(Move.RIGHT_P);
    }


    // ----------------------------------------------------------
    /**
     * Rotates the front face clockwise from a local perspective. White is
     * always down, but the front face is determined by perspective.
     */
    public void front()
    {
        switch (perspective)
        {
            case GREEN:
                rTurn();
                break;
            case RED:
                bTurn();
                break;
            case BLUE:
                lTurn();
                break;
            default:
                fTurn();
                break;
        }
    }


    /**
     * Rotates the back face clockwise from a local perspective. White is always
     * down, but the front face is determined by perspective.
     */
    public void back()
    {
        switch (perspective)
        {
            case GREEN:
                lTurn();
                break;
            case RED:
                fTurn();
                break;
            case BLUE:
                rTurn();
                break;
            default:
                bTurn();
                break;
        }
    }


    /**
     * Rotates the left face clockwise from a local perspective. White is always
     * down, but the front face is determined by perspective.
     */
    public void left()
    {
        switch (perspective)
        {
            case GREEN:
                fTurn();
                break;
            case RED:
                rTurn();
                break;
            case BLUE:
                bTurn();
                break;
            default:
                lTurn();
                break;
        }
    }


    /**
     * Rotates the right face clockwise from a local perspective. White is
     * always down, but the front face is determined by perspective.
     */
    public void right()
    {
        switch (perspective)
        {
            case GREEN:
                bTurn();
                break;
            case RED:
                lTurn();
                break;
            case BLUE:
                fTurn();
                break;
            default:
                rTurn();
                break;
        }
    }


    // ----------------------------------------------------------
    /**
     * Rotates the front face counterclockwise from a local perspective. White
     * is always down, but the front face is determined by perspective.
     */
    public void frontPrime()
    {
        switch (perspective)
        {
            case GREEN:
                rPrime();
                break;
            case RED:
                bPrime();
                break;
            case BLUE:
                lPrime();
                break;
            default:
                fPrime();
                break;
        }
    }


    /**
     * Rotates the back face counterclockwise from a local perspective. White is
     * always down, but the front face is determined by perspective.
     */
    public void backPrime()
    {
        switch (perspective)
        {
            case GREEN:
                lPrime();
                break;
            case RED:
                fPrime();
                break;
            case BLUE:
                rPrime();
                break;
            default:
                bPrime();
                break;
        }
    }


    /**
     * Rotates the left face counterclockwise from a local perspective. White is
     * always down, but the front face is determined by perspective.
     */
    public void leftPrime()
    {
        switch (perspective)
        {
            case GREEN:
                fPrime();
                break;
            case RED:
                rPrime();
                break;
            case BLUE:
                bPrime();
                break;
            default:
                lPrime();
                break;
        }
    }


    /**
     * Rotates the right face counterclockwise from a local perspective. White
     * is always down, but the front face is determined by perspective.
     */
    public void rightPrime()
    {
        switch (perspective)
        {
            case GREEN:
                bPrime();
                break;
            case RED:
                lPrime();
                break;
            case BLUE:
                fPrime();
                break;
            default:
                rPrime();
                break;
        }
    }


    // ----------------------------------------------------------
    /**
     * Sets the perspective relative to the current perspective
     *
     * @param move
     *            the direction to set the perspective.
     */
    public void setPerspective(Move move)
    {
        CubeColor setColor;
        switch (move)
        {
            case BACK:
                switch (perspective)
                {
                    case RED:
                        setColor = CubeColor.ORANGE;
                    case BLUE:
                        setColor = CubeColor.GREEN;
                    case GREEN:
                        setColor = CubeColor.BLUE;
                    default:
                        setColor = CubeColor.RED;
                }
                break;
            case LEFT:
                switch (perspective)
                {
                    case RED:
                        setColor = CubeColor.GREEN;
                    case BLUE:
                        setColor = CubeColor.RED;
                    case GREEN:
                        setColor = CubeColor.ORANGE;
                    default:
                        setColor = CubeColor.BLUE;
                }
                break;
            case RIGHT:
                switch (perspective)
                {
                    case RED:
                        setColor = CubeColor.BLUE;
                    case BLUE:
                        setColor = CubeColor.ORANGE;
                    case GREEN:
                        setColor = CubeColor.RED;
                    default:
                        setColor = CubeColor.GREEN;
                }
                break;
            default:
                setColor = perspective;
                break;
        }
        setPerspective(setColor);
    }


    // ----------------------------------------------------------
    /**
     * Sets the perspective to color
     *
     * @param color
     *            the color to be front.
     */
    public void setPerspective(CubeColor color)
    {
        perspective = color;
    }


    // ----------------------------------------------------------
    /**
     * Given an int, returns the corresponding CubeColor.
     *
     * @param j
     * @return the color matching j.
     */
    public CubeColor intToColor(int j)
    {
        switch (j)
        {
            case -1:
                return CubeColor.NONE;
            case 0:
                return CubeColor.ORANGE;
            case 1:
                return CubeColor.GREEN;
            case 2:
                return CubeColor.RED;
            case 3:
                return CubeColor.BLUE;
            case 4:
                return CubeColor.WHITE;
            case 5:
                return CubeColor.YELLOW;
        }
        return CubeColor.NONE;
    }


    // ----------------------------------------------------------
    /**
     * Returns the solution
     *
     * @return solution
     */
    public LinkedList<Move> getSolution()
    {
        return solution;
    }


    // ----------------------------------------------------------
    /**
     * Return downFace
     *
     * @return downFace
     */
    public CubeColor[][] getDownFace()
    {
        return downFace;
    }


    // ----------------------------------------------------------
    /**
     * Return upFace
     *
     * @return upFace
     */
    public CubeColor[][] getUpFace()
    {
        return upFace;
    }


    // ----------------------------------------------------------
    /**
     * Return leftFace
     *
     * @return leftFace
     */
    public CubeColor[][] getLeftFace()
    {
        return leftFace;
    }


    // ----------------------------------------------------------
    /**
     * Return rightFace
     *
     * @return rightFace
     */
    public CubeColor[][] getRightFace()
    {
        return rightFace;
    }


    // ----------------------------------------------------------
    /**
     * Return frontFace
     *
     * @return frontFace
     */
    public CubeColor[][] getFrontFace()
    {
        return frontFace;
    }


    // ----------------------------------------------------------
    /**
     * Return backFace
     *
     * @return backFace
     */
    public CubeColor[][] getBackFace()
    {
        return backFace;
    }


    // ----------------------------------------------------------
    /**
     * Fills the cube with a desired state for testing
     *
     * @param white
     * @param orange
     * @param blue
     * @param green
     * @param red
     * @param yellow
     */
    public void cubeTestFill(
        CubeColor[][] white,
        CubeColor[][] orange,
        CubeColor[][] blue,
        CubeColor[][] green,
        CubeColor[][] red,
        CubeColor[][] yellow)
    {
        whiteFace = white;
        orangeFace = orange;
        blueFace = blue;
        greenFace = green;
        redFace = red;
        yellowFace = yellow;
        prepare();
        updateCube();
    }
}
