
// -------------------------------------------------------------------------
/**
 *  Write a one-sentence summary of your class here.
 *  Follow it with additional details about its purpose, what abstraction
 *  it represents, and how to use it.
 *
 * @author Victoria Hairston (vhairs16)
 * @author Louis (Mischa) Michael (louism)
 * @author Sam Muriello (PID)
 * @version 2014.12.01
 */
public class Edge
    extends Cubie
{
    private CubeColor color1;
    private CubeColor color2;
    private CircularList list;
    private CubieLocation loc1;
    private CubieLocation loc2;

    public Edge()
    {
        color1 = CubeColor.NONE;
        color2 = CubeColor.NONE;
        list = new CircularList();
    }
    public void checkFirstSide()
    {
        Node blue = new Node(CubeColor.BLUE);
        Node red = new Node(CubeColor.RED);
        Node yellow = new Node(CubeColor.YELLOW);
        Node white = new Node(CubeColor.WHITE);
        Node green = new Node(CubeColor.GREEN);
        Node orange = new Node(CubeColor.ORANGE);
        Node none = new Node(CubeColor.NONE);
        list = new CircularList();
        switch(color1)
        {
            case BLUE:
                list.add(white);
                list.add(orange);
                list.add(yellow);
                list.add(red);
                list.add(none);
                break;
            case YELLOW:
                list.add(red);
                list.add(blue);
                list.add(orange);
                list.add(green);
                list.add(none);
                break;
            case NONE:
                list.add(blue);
                list.add(red);
                list.add(yellow);
                list.add(white);
                list.add(green);
                list.add(orange);
                list.add(none);
                break;
            case WHITE:
                list.add(green);
                list.add(orange);
                list.add(blue);
                list.add(red);
                list.add(none);
                break;
            case GREEN:
                list.add(white);
                list.add(orange);
                list.add(yellow);
                list.add(red);
                list.add(none);
                break;
            case ORANGE:
                list.add(white);
                list.add(blue);
                list.add(yellow);
                list.add(green);
                list.add(none);
                break;
            case RED:
                list.add(white);
                list.add(blue);
                list.add(yellow);
                list.add(green);
                list.add(none);
                break;
        }
    }

    public boolean contains(CubeColor color)
    {
        return (color1 == color || color2 == color);
    }
    /**
     *
     */
    public CubeColor nextColor()
    {
        return list.nextColor();
    }
    /**
     * basic setter for color1
     * @param set the color that you want to change color1 to
     */
    public void setColor1(CubeColor set)
    {
        color1 = set;
    }
    /**
     * basic setter for color2
     * @param set the color that you want to change color2 to
     */
    public void setColor2(CubeColor set)
    {
        color2 = set;
    }
    /**
     * basic getter for color1
     * @return color1
     */
    public CubeColor getColor1()
    {
        return color1;
    }
    /**
     * basic getter for color2
     * @return color2
     */
    public CubeColor getColor2()
    {
        return color2;
    }

    public CubieLocation getLoc1()
    {
        return loc1;
    }

    public CubieLocation getLoc2()
    {
        return loc2;
    }

    public void setLoc1(CubieLocation loc)
    {
        loc1 = loc;
    }

    public void setLoc2(CubieLocation loc)
    {
        loc2 = loc;
    }

    public boolean equals(Object edge)
    {
        if (edge instanceof Edge && ((Edge)edge).getLoc1().equals(this.getLoc1()) && ((Edge)edge).getLoc2().equals(this.getLoc2()))
        {
            return true;
        }
        return false;
    }
}
