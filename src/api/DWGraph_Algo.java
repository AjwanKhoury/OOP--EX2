package api;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonWriter;

public class DWGraph_Algo implements dw_graph_algorithms {

	directed_weighted_graph graph ;

	/**
	 * main constructor 
	 */
	public DWGraph_Algo() {
		graph = new DWGraph_DS();
	}

	/**
	 * Copy constructor
	 * @param other ,other directed_weighted_graph
	 */
	public DWGraph_Algo(directed_weighted_graph other) {
		graph = new DWGraph_DS(other);
	}

	@Override
	public void init(directed_weighted_graph g) {
		this.graph = g;
	}

	@Override
	public directed_weighted_graph getGraph() {
		return this.graph;
	}

	@Override
	public directed_weighted_graph copy() {
		directed_weighted_graph ans = new DWGraph_DS(this.graph);
		return ans;
	}

	@Override
	public boolean isConnected() {
		if(this.graph == null || this.graph.nodeSize() < 2)
			return true;

		for(node_data node : this.graph.getV())
			if(!isConnectedHelper(node)) {
				this.resetTag();
				return false;
			}

		this.resetTag();
		return true;
	}

	private boolean isConnectedHelper(node_data node) {
		resetTag();

		Stack<Integer> stack = new Stack<Integer>();
		stack.add(node.getKey());
		node.setTag(1);

		while(!stack.isEmpty()) {
			node_data help = this.graph.getNode(stack.pop());
			help.setTag(2);
			for(int nodeChild : ((NodeData)help).KeyNI()) 
				if(this.graph.getNode(nodeChild).getTag() == 0) {
					stack.push(nodeChild);
					this.graph.getNode(nodeChild).setTag(1);
				}
		}

		for(node_data nodes : this.graph.getV())
			if(nodes.getTag() != 2)
				return false;

		return true;
	}

	/*
	 * reseting the Tag to Integer max value , for helping with calculating.
	 */
	private void resetTag() {
		for(node_data node : this.graph.getV())
			node.setTag(0);
	}

	@Override
	public double shortestPathDist(int src, int dest) {
		if(this.graph.getNode(src) == null || this.graph.getNode(dest) ==  null)
			return -1;

		if(src == dest)
			return 0;

		resetWeight();

		Stack<Integer> stack = new Stack<Integer>();
		stack.add(src);
		this.graph.getNode(src).setWeight(0);

		while(!stack.isEmpty()) {
			NodeData help = (NodeData)this.graph.getNode(stack.pop());

			((NodeData)help).EdgeNI();
			for(edge_data edge : ((NodeData)help).EdgeNI()) {
				if(help.getWeight() + edge.getWeight() < this.graph.getNode(edge.getDest()).getWeight()) {
					this.graph.getNode(edge.getDest()).setWeight(help.getWeight() + edge.getWeight());
					stack.push(edge.getDest());
				}
			}
		}

		double ans = this.graph.getNode(dest).getWeight();
		this.resetWeight();
		if(ans == Integer.MAX_VALUE)
			return -1;
		return ans;
	}

	/*
	 * reseting the weight to Integer max value , for helping with calculating.
	 */
	public void resetWeight() {
		for(node_data node : this.graph.getV())
			node.setWeight(Integer.MAX_VALUE);
	}

	@Override
	public List<node_data> shortestPath(int src, int dest) {
		if(this.graph.getNode(src) == null || this.graph.getNode(dest) ==  null)
			return null;

		if(src == dest) {
			List<node_data> ans = new ArrayList<node_data>();
			node_data helper = this.graph.getNode(dest);
			ans.add(helper);
			return ans;
		}

		else {
			this.resetWeight();
			this.resetTag();
			Stack<Integer> stack = new Stack<Integer>();
			stack.add(src);
			this.graph.getNode(src).setWeight(0);
			this.graph.getNode(src).setTag(-1);

			while(!stack.isEmpty()) {
				node_data help = this.graph.getNode(stack.pop());
				for(edge_data edge : ((NodeData)help).EdgeNI()) {
					if(help.getWeight() + edge.getWeight() < this.graph.getNode(edge.getDest()).getWeight()) {
						this.graph.getNode(edge.getDest()).setWeight(help.getWeight() + edge.getWeight());
						this.graph.getNode(edge.getDest()).setTag(edge.getSrc());
						if(!stack.contains(edge.getDest()))
							stack.push(edge.getDest());
					}
				}
			}

			List<node_data> ans = new ArrayList<node_data>();
			node_data helper = this.graph.getNode(dest);

			while(helper.getTag() != -1) {
				ans.add(0, helper);
				helper = this.graph.getNode(helper.getTag());
			}
			ans.add(0, helper);
			this.resetTag();
			this.resetWeight();
			return ans;
		}
	}

	@Override
	public boolean save(String file) {

		try {
			FileWriter fw = new FileWriter(file);
			JsonWriter jw = new JsonWriter(fw);
			jw.beginObject();
			jw.name("Edges");
			jw.beginArray();
			for (node_data node : this.graph.getV()) 
				for (edge_data edge: this.graph.getE(node.getKey())) {
					jw.beginObject();
					jw.name("src").value(edge.getSrc());
					jw.name("w").value(edge.getWeight());
					jw.name("dest").value(edge.getDest());
					jw.endObject();
				}

			jw.endArray();
			jw.name("Nodes");
			jw.beginArray();
			for (node_data node : this.graph.getV()) {
				jw.beginObject();
				jw.name("pos").value(node.getLocation().toString());
				jw.name("id").value(node.getKey());
				jw.endObject();
			}
			jw.endArray();
			jw.endObject();
			jw.close();
		}

		catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	public boolean load(String file) {

		try {
			GsonBuilder builder = new GsonBuilder();
			builder.registerTypeAdapter(directed_weighted_graph.class, new graphJsonDeserializer());
			Gson gson =  builder.setPrettyPrinting().create();

			FileReader reader = new FileReader(file);
			directed_weighted_graph helper = gson.fromJson(reader, directed_weighted_graph.class);
			this.init(helper);
		}

		catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return "DWGraph_Algo{" + "myGraph=" + this.graph + '}';
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) 
			return true;

		if (other == null || getClass() != other.getClass()) 
			return false;

		DWGraph_Algo check = (DWGraph_Algo) other;

		if( this.toString().equals(other.toString()))
			return true;

		return false;
	}



	public class graphJsonDeserializer implements JsonDeserializer<directed_weighted_graph>{

		@Override
		public directed_weighted_graph deserialize(JsonElement json, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
			JsonObject myObject = json.getAsJsonObject();
			JsonArray nodes = myObject.get("Nodes").getAsJsonArray();
			JsonArray edges = myObject.get("Edges").getAsJsonArray();

			DWGraph_DS ans = new DWGraph_DS();

			for(JsonElement set : nodes ) {
				JsonObject val = set.getAsJsonObject();
				int id = val.get("id").getAsInt();
				String loc = val.get("pos").getAsString();
				node_data n = new NodeData(id);
				n.setLocation(new GeoLocation(loc));
				ans.addNode(n);
			}
			
			for(JsonElement set : edges) {
				JsonObject val = set.getAsJsonObject();
				int src = val.get("src").getAsInt();
				double weight = val.get("w").getAsDouble();
				int dest = val.get("dest").getAsInt();
				ans.connect(src, dest, weight);
			}
			
			return ans;



			//			JsonObject myObject = json.getAsJsonObject();
			//
			//			int mc = myObject.get("mc").getAsInt();
			//			int edgeNum = myObject.get("vNum").getAsInt();
			//			JsonObject t= myObject.get("mygraph").getAsJsonObject();
			//
			//			DWGraph_DS ans = new DWGraph_DS();
			//
			//			for(Entry<String, JsonElement> set : t.entrySet()) {
			//
			//				String hashKey = set.getKey();
			//				JsonElement hashValue = set.getValue();
			//				int id = hashValue.getAsJsonObject().get("id").getAsInt();
			//
			//
			//				JsonElement geoLocation = hashValue.getAsJsonObject().get("location").getAsJsonObject();
			//				JsonElement pointLocation = geoLocation.getAsJsonObject().get("myPoint").getAsJsonObject();
			//				double x = pointLocation.getAsJsonObject().get("_x").getAsDouble();
			//				double y = pointLocation.getAsJsonObject().get("_y").getAsDouble();
			//				double z = pointLocation.getAsJsonObject().get("_z").getAsDouble();
			//				geo_location g = new GeoLocation(x, y, z);
			//
			//				double weight = hashValue.getAsJsonObject().get("weight").getAsDouble();
			//				String info =  hashValue.getAsJsonObject().get("info").getAsString();
			//				int tag = hashValue.getAsJsonObject().get("tag").getAsInt();
			//
			//				JsonObject neigber = hashValue.getAsJsonObject().get("neigbers").getAsJsonObject();
			//				HashMap<Integer, edge_data> edges = new HashMap<>();
			//
			//				for(Entry<String, JsonElement> ni : neigber.entrySet()) {
			//					String hashKey2 = ni.getKey();
			//					JsonElement hashValue2 = ni.getValue();
			//					int src = hashValue2.getAsJsonObject().get("src").getAsInt();
			//					int dest = hashValue2.getAsJsonObject().get("dest").getAsInt();
			//					int tag2 = hashValue2.getAsJsonObject().get("tag").getAsInt();
			//					double weight2 = hashValue2.getAsJsonObject().get("weight").getAsDouble();
			//					String info2 = hashValue2.getAsJsonObject().get("info").getAsString();
			//
			//					edge_data edge = new EdgeData(src, dest, weight2, tag2, info2);
			//					edges.put(dest, edge);
			//				}
			//
			//				NodeData n = new NodeData(id , weight , g , info , tag , edges);
			//				ans.addNode(n);
			//			}
			//
			//			ans.setMC(mc);
			//			ans.setV(edgeNum);
			//			return  ans;
		}

	}

}

















