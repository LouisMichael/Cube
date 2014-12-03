

/**
 * // -------------------------------------------------------------------------
 * /** Center just sets up a cubi that only has one color as to represent the
 * center piece of each face of the cube
 *
<<<<<<< HEAD
 * @author Victoria Hairston (vhairs16)
 * @author Louis (Mischa) Michael (louism)
 * @author Sam Muriello (PID)
 * @version 2014.12.01
=======
 * @author Louis Michael (louism)
 * @version Dec 1, 2014
>>>>>>> origin/master
 */
public class Center
    extends Cubie
{
    private CubeColor color;


    /**
     * sets up a center that has one color
     *
     * @param newColor
     *            the color that will be set for the one color
     */
    public Center(CubeColor newColor)
    {
        color = newColor;
    }


    /**
     * set color changes the one color of the face
     *
     * @param newColor
     *            the color that the center will now be set to
     */
    public void setColor(CubeColor newColor)
    {
        color = newColor;
    }


    /**
     * returns the color of the one side
     *
     * @return the one color that center holds
     */
    public CubeColor getColor()
    {
        return color;
    }
}
