package ex0;

import java.util.HashMap;
import java.util.List;
import java.util.*;

public class Graph_Algo implements graph_algorithms {

    private Graph_DS graph;


    @Override
    public void init(graph g) {
        graph = (Graph_DS)g;
    }

    /** Copy method : creates a new graph using a default constructor, afterwards we iterate over all the nodes in the graph we want to copy and add
     * each node into the new graph we created via nodeData parameter constructor.
     * in the second for loop we're searching for neighbors to copy to the graph we want to copy to. */
    @Override
    public graph copy() {
        graph copied = new Graph_DS();
        for(node_data i : graph.getV()) {
            copied.addNode(new NodeData(i));
            for(node_data n : i.getNi()) {
                if(copied.getNode(n.getKey()) == null) {
                    copied.addNode(new NodeData(n));
                }
                copied.getNode(i.getKey()).addNi(copied.getNode(n.getKey()));
             }
        }
        for(node_data i : copied.getV()) {
            for(node_data j : i.getNi()) {
                copied.connect(i.getKey(),j.getKey());
            }
        }
        return copied;
    }

    /** This function resets the graph's tags to 0 by going through each node via for each loop */
    public void reset(graph g) {
        for(node_data i : g.getV()) {
            i.setTag(0);
        }
    }

    /** isConnected function uses the implementation "isConnected" that's over the Graph_DS class. */
    @Override
    public boolean isConnected() throws NullPointerException{
        if(graph == null) { return false;}
        boolean ret = graph.isConnected();
        reset(graph);
        return ret;
    }

    /** This function relies on the "shortestPath" function to get a list, then it returns it's size. */
    @Override
    public int shortestPathDist(int src, int dest) {
        int x = shortestPath(src,dest).size() - 1;
        if(x == 0) { return -1;}
        else return x;
    }

    /** The shortestPath function uses the BFS algo to go through the entire graph.
     * meaning we create a Queue ,Linked-list and an Hash-map.
     * first we set the starting node to start from the src we're getting via parameter(src)
     * then we add it to out queue and set it's tag to be 1 meaning we've already visited it.
     * (unvisited nodes will have tag=0)
     * now we enter the while loop and start polling 1 node at a time from it, then we're looking at the node's neighbors and start
     * iterating them via for each loop checking if they're visited or not.
     * if we arrive at our dest given to us via parameter, we start adding to our linked list.
     * (everytime we iterate we'll save our previous step that got us to where we are meaning we can trace back our steps)
     * we're tracing back our steps until we reach back to the start, when we do we're done and we're returning the linked list we've added the nodes to.
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<node_data> shortestPath(int src, int dest) {
        node_data start = graph.getNode(src);
        Queue<node_data> queue = new LinkedList<>();
        LinkedList<node_data> ret = new LinkedList<>();
        HashMap<node_data,node_data> prev = new HashMap<>();
        try {
            queue.add(start);
            start.setTag(1);
            while (!queue.isEmpty()) {
                node_data temp = queue.poll();
                for (node_data i : temp.getNi()) {
                    if (i.getTag() == 0) {
                        prev.put(i, temp);
                        if (i.getKey() == dest) {
                            while (i != start) {
                                ret.addFirst(i);
                                i = prev.get(i);
                            }
                            ret.addFirst(start);
                            reset(graph);
                            return ret;
                        }
                        i.setTag(1);
                        queue.add(i);
                    }
                }
            }
        }catch(NullPointerException  e) { return ret; }
        return ret;
    }
}
