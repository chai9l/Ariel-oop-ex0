package ex0;

import java.security.KeyPair;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.*;

public class Graph_DS implements graph {

    private HashMap<Integer, node_data> graph;
    private HashMap<String, Boolean> edges;
    private int mc;

    public Graph_DS(){
        graph = new HashMap<Integer, node_data>();
        edges = new HashMap<String, Boolean>();
        mc = 0;
    }

    // num + "/" + num2 -- alternate way of pairing.
    private String generateKey(int node1, int node2) {
        String s = node1 <= node2 ? node1 + "/" + node2 : node2 + "/" + node1;
        return s;
    }

    /** the isConnected function is a worker function using that's implementing the BFS algo.
     * first we check if the graph size is not 0.
     * afterwards we create a Queue to and add our first node (we find that node via stream().findFirst().get() ).
     * then we set it's tag to be 1 meaning we've already visited it.
     * (unvisited nodes will have tag=0)
     * now we enter the while loop and start polling 1 node at a time from it, then we're looking at the node's neighbors and start
     * iterating them via for each loop checking if they're visited or not.
     * We'll notice that each time we change the tag we're adding back to the queue to keep on iterating and update our counter.
     * if we finish the run with a counter that does not equal the size of the entire graph it means we missed some nodes because they're not connected
     * then returning false and vise-versa.
     * @return
     */
    //Implementation of the "isConnected" method using the BFS algo.
    public boolean isConnected() {
        if(graph.size()==0) {
            return true;
        }
        int counter = 1;
            node_data first = graph.values().stream().findFirst().get();
            Queue<node_data> queue = new LinkedList<>();
            queue.add(first);
            first.setTag(1);
            while (!queue.isEmpty()) {
                node_data temp = queue.poll();
                for (node_data i : temp.getNi()) {
                    if (i.getTag() == 0) {
                        i.setTag(1);
                        queue.add(i);
                        counter++;
                    }
                }
            }
        if(counter == graph.size()) { return true; }
        else { return false; }
    }



    @Override
    public node_data getNode(int key) {
        return graph.get(key);
    }

    @Override
    public boolean hasEdge(int node1, int node2) {
        String s = generateKey(node1,node2);
        Boolean ret = edges.get(s);
        return ret != null && ret;
    }

    // check if hashmap add runs in O(1) ! if not check hashtable.
    @Override
    public void addNode(node_data n) {
        mc++;
        graph.put(n.getKey(), n);
    }

    /** connects 2 nodes using the "generateString" helper function */
    @Override
    public void connect(int node1, int node2) {
        if(node1 == node2 ) {
            return;
        }
        if(graph.containsKey(node1) && graph.containsKey(node2)) {
            String key = generateKey(node1, node2);
            if (!hasEdge(node1, node2)) {
                edges.put(key, true);
                node_data nodeX = graph.get(node1);
                node_data nodeY = graph.get(node2);
                nodeX.addNi(nodeY);
                nodeY.addNi(nodeX);
                mc++;
            }
        }
    }

    @Override
    public Collection<node_data> getV() {
        return graph.values();
    }

    @Override
    public Collection<node_data> getV(int node_id) {
        if(graph.containsKey(node_id)) {
            return graph.get(node_id).getNi();
        }
        else return null;
    }

    /** A function to remove the node by getting a key.
     * we create a node called ret to find the right via get(key)
     * @param key
     * @return
     */
    @Override
    public node_data removeNode(int key) {
        node_data ret = graph.get(key);
        LinkedList<node_data> l = new LinkedList<>();
        if (ret != null) {
            for (node_data i : ret.getNi()) {
                l.add(i);
            }
            for(node_data i : l) {
                removeEdge(i.getKey(),key);
            }
            mc++;
            return graph.remove(ret.getKey());
        }
        return null;
    }

    /** Removes the edge by using the "generateKey" function, we're creating a string from the given node1 and node2
     * then we check if that key values we created exists in the edge's hash map if it does we delete it.
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if(graph.containsKey(node1) && graph.containsKey(node2)) {
            String s = generateKey(node1, node2);
            if (hasEdge(node1, node2)) {
                edges.remove(s);
                node_data nodeX = graph.get(node1);
                node_data nodeY = graph.get(node2);
                nodeX.removeNode(nodeY);
                nodeY.removeNode(nodeX);
            }
        }
    }

    @Override
    public int nodeSize() {
        return graph.size();
    }

    @Override
    public int edgeSize() {
        return edges.size();
    }

    // check if connect and removeNode adds to mc once or the amount of edges attached to it.
    @Override
    public int getMC() {
        return mc;
    }
}
