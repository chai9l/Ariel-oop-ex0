package ex0;

import java.util.*;

public class NodeData implements node_data {

    private static int incKey = 1;
    private int key;
    private HashSet<node_data> neighbors;
    private String info;
    private int tag;

    /** Default constructor.*/
    public NodeData() {
        key = incKey++;
        neighbors = new HashSet<node_data>();
        info = "";
        tag = 0;
    }

    /** Parameter constructor. */
    public NodeData(int key, String info) {
        this.key = key;
        neighbors = new HashSet<node_data>();
        this.info = info;
        tag = 0;
    }

    /** Copy constructor. */
    public NodeData(node_data toCopy) {
        key = toCopy.getKey();
        tag = 0;
        neighbors = new HashSet<node_data>();
    }

    @Override
    public int getKey() {
        return key;
    }

    @Override
    public Collection<node_data> getNi() {
        return neighbors;
    }

    @Override
    public boolean hasNi(int key) {
        for(node_data node : neighbors) {
            if(node.getKey() == key) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addNi(node_data t) {
        if (!neighbors.contains(t)) {
            neighbors.add(t);
        }
    }

    @Override
    public void removeNode(node_data node) {
        neighbors.remove(node);
    }

    @Override
    public String getInfo() {
        return info;
    }

    @Override
    public void setInfo(String s) {
        info = s;
    }

    @Override
    public int getTag() {
        return tag;
    }

    @Override
    public void setTag(int t) {
        tag = t;
    }

}
