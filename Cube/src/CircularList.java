

/**
 * // -------------------------------------------------------------------------
 * /** this sets up a circular single linked list the purpose of this is to have
 * colors added to the list and then be the list to be moved over in order to
 * select an appropriate color for a given position in the cube
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
public class CircularList
{
    // Fields
    private Node current;
    private Node end;
    private Node first;


    /**
     * default constructor that just sets up an empty list
     */
    public CircularList()
    {
        current = null;
    }


    /**
     * when adding a new node to the list it checks to see if the list was empty
     * if it was it sets up a one node list where the first node loops back to
     * itself otherwise the new node is added after the current node and loops
     * back to the lead node
     *
     * @param newNode
     *            the node that is going to be added to the list
     */
    public void add(Node newNode)
    {
        if (current == null)
        {
            newNode.setNext(newNode);
            current = newNode;
            first = newNode;
            end = newNode;
        }
        else
        {
            end.setNext(newNode);
            end = newNode;
            end.setNext(first);

        }
    }


    /**
     * next simply moves current to the next node
     */
    public void next()
    {
        current = current.getNext();
    }


    /**
     * moves the current forward and returns the color that is at this new
     * position, this means that as this method is called current moves through
     * the list returning appropriate colors
     *
     * @return the next color in the list
     */
    public CubeColor nextColor()
    {
        next();
        return current.getData();
    }

}
