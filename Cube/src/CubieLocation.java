

// -------------------------------------------------------------------------
/**
 *  Represents the location of a cubie on the cube.
 *
 * @author Victoria Hairston (vhairs16)
 * @author Louis (Mischa) Michael (louism)
 * @author Sam Muriello (PID)
 * @version 2014.12.01
 */
public class CubieLocation
{
    private CubeColor face;
    private int x;
    private int y;

    // ----------------------------------------------------------
    /**
     * Create a new CubieLocation object.
     * @param c
     * @param i
     * @param j
     */
    public CubieLocation(CubeColor c, int i, int j)
    {
        face = c;
        x = i;
        y = j;
    }

    // ----------------------------------------------------------
    /**
     * Returns x
     * @return x
     */
    public int getX()
    {
        return x;
    }

    // ----------------------------------------------------------
    /**
     * Returns y
     * @return y
     */
    public int getY()
    {
        return y;
    }

    // ----------------------------------------------------------
    /**
     * Returns face
     * @return face
     */
    public CubeColor getFace()
    {
        return face;
    }

    public void setFace(CubeColor color)
    {
        face = color;
    }

    public void setX(int i)
    {
        x = i;
    }

    public void setY(int j)
    {
        y = j;
    }

    public void setLoc(CubeColor f, int i, int j)
    {
        setFace(f);
        setX(i);
        setY(j);
    }

    public boolean equals(Object loc)
    {
        if (loc instanceof CubieLocation && x == ((CubieLocation)loc).getX() && y == ((CubieLocation)loc).getY() && face == ((CubieLocation)loc).getFace())
        {
            return true;
        }
        return false;
    }
}
