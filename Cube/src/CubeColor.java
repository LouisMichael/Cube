

/**
 * // -------------------------------------------------------------------------
 * /** Sets up a set of constants that are used to represent the colors that
 * appears on the Rubik's cube
 *
<<<<<<< HEAD
 * @author Victoria Hairston (vhairs16)
 * @author Louis (Mischa) Michael (louism)
 * @author Sam Muriello (PID)
 * @version 2014.12.01
=======
 * @author Louis Michael
 * @version Dec 1, 2014
>>>>>>> origin/master
 */
public enum CubeColor
{
    /**
     * Represents the Red color that appears on the cube
     */
    RED(2),
    /**
     * Represents the Blue color that appears on the cube
     */
    BLUE(3),
    /**
     * Represents the Green color that appears on the cube
     */
    GREEN(1),
    /**
     * Represents the White color that appears on the cube
     */
    WHITE(4),
    /**
     * Represents the Orange color that appears on the cube
     */
    ORANGE(0),
    /**
     * Represents the Yellow color that appears on the cube
     */
    YELLOW(5),
    /**
     * Represents a space that has yet to be assigned a color
     */
    NONE(-1);

    private int i;


    CubeColor(int x)
    {
        i = x;
    }


    /**
     * the colors are additionally assigned number in order to use modulus
     * devision in order to make decisions while solving
     *
     * @return the number that represents one of the 7 color options
     */
    public int getInt()
    {
        return i;
    }
}
