

// -------------------------------------------------------------------------
/**
 * Node is a basic node that holds data and has a pointer to the next node
 *
 * @author Victoria Hairston (vhairs16)
 * @author Louis (Mischa) Michael (louism)
 * @author Sam Muriello (smur17)
 * @version 2014.12.01
 */
public class Node
{
    // Fields
    private CubeColor data;
    private Node      next;


    /**
     *
     */
    public Node()
    {
        data = null;
        next = null;
    }


    public Node(CubeColor setData)
    {
        data = setData;
        next = null;
    }


    public Node setNext(Node setNext)
    {
        next = setNext;
        return setNext;
    }


    public void setData(CubeColor setData)
    {
        data = setData;
    }


    public CubeColor getData()
    {
        return data;
    }


    public Node getNext()
    {
        return next;
    }

}
