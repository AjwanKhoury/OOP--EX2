package api;

import java.util.Collection;
import java.util.HashMap;

public class NodeData implements node_data{

	int id;
	geo_location location;
	double weight;
	String info;
	int tag;
	HashMap<Integer, edge_data> neigbers;

/**
 * main Constructor 
 * @param key is the id of the Node
 */
	public NodeData(int key) {
		this.id = key;
		this.location = new GeoLocation();
		this.weight = 0;
		this.info = "";
		this.tag = 0;
		this.neigbers = new HashMap<>();
	}

	/**
	 * Constructor
	 * @param key is the id of the Node
	 * @param weight is the weight of the Node
	 */
	public NodeData(int key , double weight) {
		this.id = key;
		this.location = new GeoLocation();
		this.weight = weight;
		this.info = "";
		this.tag = 0;
		this.neigbers = new HashMap<>();
	}
	
	/**
	 * Constructor that accept all the arguments for the node
	 * @param key
	 * @param weight
	 * @param location
	 * @param info
	 * @param tag
	 * @param neigbers
	 */
	public NodeData(int key , double weight , geo_location location , String info , int tag , HashMap<Integer, edge_data> neigbers) {
		this.id = key;
		this.location = new GeoLocation();
		this.weight = weight;
		this.info = info;
		this.tag = tag;
		this.neigbers = neigbers;
	}

	/**
	 * copy Constructor  
	 * @param other the other node that we want to be equal with
	 */
	public NodeData(node_data other) {
		this.id = other.getKey();
		this.location = new GeoLocation(other.getLocation());
		this.weight = other.getWeight();
		this.info = other.getInfo();
		this.tag = other.getTag();
		this.neigbers = new HashMap<>();
	}

	@Override
	public int getKey() {
		return this.id;
	}

	@Override
	public geo_location getLocation() {
		return location;
	}

	@Override
	public void setLocation(geo_location p) {
		this.location = new GeoLocation(p);
	}

	@Override
	public double getWeight() {
		return this.weight;
	}

	@Override
	public void setWeight(double w) {
		this.weight = w;
	}

	@Override
	public String getInfo() {
		return this.info;
	}

	@Override
	public void setInfo(String s) {
		this.info = s;
	}

	@Override
	public int getTag() {
		return this.tag;
	}

	@Override
	public void setTag(int t) {
		this.tag = t ;
	}

	/*
	 * this function connect the node to other node 
	 * by building edge data with the other node. 
	 * it accept the other node and the weight of this connect.
	 */
	public void connect(node_data n , double weight) {
		edge_data newEdge = new EdgeData(this.getKey(), n.getKey(), weight);
		this.neigbers.put(n.getKey(), newEdge);
	}
	
	/*
	 * this function connect the node to other node 
	 * by building edge data with the other node. 
	 * it accept the other node key and the weight of this connect.
	 */
	public void connect(int otherKey , double weight) {
		edge_data newEdge = new EdgeData(this.getKey(), otherKey, weight);
		this.neigbers.put(otherKey , newEdge);
	}
	
	/*
	 * this function remove the connection with other node.
	 */
	public void remove(int key) {
		this.neigbers.remove(key);
	}

	/*
	 * return list of neighbors node id
	 */
	public Collection<Integer> KeyNI() {
		return this.neigbers.keySet();
	}
	
	/*
	 * return list of the edges that this node connect with
	 */
	public Collection<edge_data> EdgeNI() {
		return this.neigbers.values();
	}
	
	/*
	 * this function accept id of node and it check if there is connection with it.
	 */
	public boolean isConnect(int key) {
		return this.neigbers.containsKey(key);
	}
	
	/*
	 * this function return the edge that connect with other node(by its id).
	 * if there is no connection it will return null.
	 */
	public edge_data hasEdge(int key) {
		return this.neigbers.get(key);
	}
	
	/*
	 * this function return the number of edges with this node.
	 */
	public int degree() {
		return this.neigbers.size();
	}
}
