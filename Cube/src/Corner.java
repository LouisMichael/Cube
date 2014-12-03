

import java.util.ArrayList;

// -------------------------------------------------------------------------
/**
 * Write a one-sentence summary of your class here. Follow it with additional
 * details about its purpose, what abstraction it represents, and how to use it.
 *
 * @author Victoria Hairston (vhairs16)
 * @author Louis (Mischa) Michael (louism)
 * @author Sam Muriello (PID)
 * @version 2014.12.01
 */
public class Corner
    extends Cubie
{
    private CubeColor       color1;
    private CubeColor       color2;
    private CubeColor       color3;
    private CircularList    list;
    private Node            blue   = new Node(CubeColor.BLUE);
    private Node            red    = new Node(CubeColor.RED);
    private Node            yellow = new Node(CubeColor.YELLOW);
    private Node            white  = new Node(CubeColor.WHITE);
    private Node            green  = new Node(CubeColor.GREEN);
    private Node            orange = new Node(CubeColor.ORANGE);
    private Node            none   = new Node(CubeColor.NONE);
    private ArrayList<Node> listA;
    private CubieLocation   loc1;
    private CubieLocation   loc2;
    private CubieLocation   loc3;


    public Corner()
    {
        color1 = CubeColor.NONE;
        color2 = CubeColor.NONE;
        color3 = CubeColor.NONE;
    }


    public void checkFirstSide()
    {
        listA = new ArrayList<Node>();
        switch (color1)
        {
            case BLUE:
                listA.add(white);
                listA.add(orange);
                listA.add(yellow);
                listA.add(red);
                break;
            case YELLOW:
                listA.add(red);
                listA.add(blue);
                listA.add(orange);
                listA.add(green);
                break;
            case NONE:
                listA.add(blue);
                listA.add(red);
                listA.add(yellow);
                listA.add(white);
                listA.add(green);
                listA.add(orange);
                break;
            case WHITE:
                listA.add(green);
                listA.add(orange);
                listA.add(blue);
                listA.add(red);
                break;
            case GREEN:
                listA.add(white);
                listA.add(orange);
                listA.add(yellow);
                listA.add(red);
                break;
            case ORANGE:
                listA.add(white);
                listA.add(blue);
                listA.add(yellow);
                listA.add(green);
                break;
            case RED:
                listA.add(white);
                listA.add(blue);
                listA.add(yellow);
                listA.add(green);
                break;
        }
    }


    public void checkSide2()
    {
        list = new CircularList();
        switch (color2)
        {
            case BLUE:
                listA.remove(blue);
                listA.remove(green);
                break;
            case YELLOW:
                listA.remove(yellow);
                listA.remove(white);
                break;
            case NONE:
                break;
            case WHITE:
                listA.remove(yellow);
                listA.remove(white);
            case GREEN:
                listA.remove(blue);
                listA.remove(green);
                break;
            case ORANGE:
                listA.remove(orange);
                listA.remove(red);
                break;
            case RED:
                listA.remove(orange);
                listA.remove(red);
                break;
        }
        for (Node node : listA)
        {
            list.add(node);
        }
    }


    public boolean contains(CubeColor color)
    {
        return (color1 == color || color2 == color || color3 == color);
    }


    public CubeColor nextColor()
    {
        return list.nextColor();
    }


    public void setColor1(CubeColor set)
    {
        color1 = set;
    }


    public void setColor2(CubeColor set)
    {
        color2 = set;
    }


    public void setColor3(CubeColor set)
    {
        color3 = set;
    }


    public CubeColor getColor1()
    {
        return color1;
    }


    public CubeColor getColor2()
    {
        return color2;
    }


    public CubeColor getColor3()
    {
        return color3;
    }


    public CubieLocation getLoc1()
    {
        return loc1;
    }


    public CubieLocation getLoc2()
    {
        return loc2;
    }


    public CubieLocation getLoc3()
    {
        return loc3;
    }


    public void setLoc1(CubieLocation loc)
    {
        loc1 = loc;
    }


    public void setLoc2(CubieLocation loc)
    {
        loc2 = loc;
    }


    public void setLoc3(CubieLocation loc)
    {
        loc3 = loc;
    }


    public boolean equals(Object corner)
    {
        if (corner instanceof Corner
            && ((Corner)corner).getLoc1().equals(this.getLoc1())
            && ((Corner)corner).getLoc2().equals(this.getLoc2())
            && ((Corner)corner).getLoc3().equals(this.getLoc3()))
        {
            return true;
        }
        return false;
    }
}
