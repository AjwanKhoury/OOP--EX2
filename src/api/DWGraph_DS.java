package api;

import java.util.Collection;
import java.util.HashMap;

public class DWGraph_DS implements directed_weighted_graph {

	int mc , vNum;
	HashMap<Integer, node_data> mygraph;

	/*
	 * main constructor
	 */
	public DWGraph_DS() {
		this.mc = 0;
		this.vNum = 0;
		this.mygraph = new HashMap<>();
	}

	/**
	 * copy constructor
	 * @param other , other directed_weighted_graph
	 */
	public DWGraph_DS(directed_weighted_graph other) {
		this.mc = 0;
		this.vNum = 0;
		this.mygraph = new HashMap<>();

		if(other == null)
			return ;

		for(node_data node : other.getV()) 
			this.addNode(new NodeData(node));

		for(node_data node : other.getV()) 
			for(edge_data edge : ((NodeData)node).EdgeNI()) {
				this.connect(edge.getSrc(), edge.getDest(), edge.getWeight());
			}

		this.mc = other.getMC();
		this.vNum = other.edgeSize();
	}

	@Override
	public node_data getNode(int key) {
		return this.mygraph.get(key);
	}

	@Override
	public edge_data getEdge(int src, int dest) {
		if(this.mygraph.containsKey(src) && this.mygraph.containsKey(dest))
			return ((NodeData)this.mygraph.get(src)).hasEdge(dest);
		else
			return null;
	}

	@Override
	public void addNode(node_data n) {
		this.mygraph.put(n.getKey(), n);
		this.mc++;
	}


	@Override
	public void connect(int src, int dest, double w) {
		if(this.mygraph.containsKey(src) && this.mygraph.containsKey(dest)) {
			if( ((NodeData)this.mygraph.get(src)).isConnect(dest) ){
				if(((NodeData)this.mygraph.get(src)).hasEdge(dest).getWeight() == w)
					((NodeData)this.mygraph.get(src)).connect(this.mygraph.get(dest), w);
				else {
					((NodeData)this.mygraph.get(src)).connect(this.mygraph.get(dest), w);
					mc++;
				}
			}
			else {
				((NodeData)this.mygraph.get(src)).connect(this.mygraph.get(dest), w);
				this.mc++;
				this.vNum++;
			}
		}
	}

	@Override
	public Collection<node_data> getV() {
		return this.mygraph.values();
	}

	@Override
	public Collection<edge_data> getE(int node_id) {
		if(this.mygraph.containsKey(node_id))
			return ((NodeData)this.mygraph.get(node_id)).EdgeNI();

		else
			return null;
	}

	@Override
	public node_data removeNode(int key) {
		node_data save = this.mygraph.get(key);
		if(save == null)
			return null;

		this.mygraph.remove(key);
		this.mc += ((NodeData)save).degree();
		this.vNum -= ((NodeData)save).degree();

		for(node_data n : this.mygraph.values()) 
			if(((NodeData)n).isConnect(key)) {
				((NodeData)n).remove(key);
				this.vNum--;
				this.mc++;
			}
		return save;
	}

	@Override
	public edge_data removeEdge(int src, int dest) {
		NodeData node = (NodeData)this.mygraph.get(src);
		if(node == null)
			return null;

		edge_data edge = node.hasEdge(dest);
		if(edge == null)
			return null;

		node.remove(dest);
		this.mc++ ;
		this.vNum-- ;
		return edge;
	}

	@Override
	public int nodeSize() {
		return this.mygraph.size();
	}

	@Override
	public int edgeSize() {
		return this.vNum;
	}

	@Override
	public int getMC() {
		return this.mc;
	}

	/*
	 * updating the mc 
	 */
	public void setMC(int mc) {
		this.mc = mc;
	}
	
	/*
	 * updating num of edges
	 */
	public void setV(int v) {
		this.vNum = v;
	}
	
}
