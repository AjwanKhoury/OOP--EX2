//package gameClient;
//
//import Server.Game_Server_Ex2;
//import api.DWGraph_Algo;
//import api.DWGraph_DS;
//import api.GeoLocation;
//import api.NodeData;
//import api.directed_weighted_graph;
//import api.dw_graph_algorithms;
//import api.edge_data;
//import api.game_service;
//import api.node_data;
//import api.DWGraph_Algo.graphJsonDeserializer;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.JsonArray;
//import com.google.gson.JsonDeserializationContext;
//import com.google.gson.JsonDeserializer;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParseException;
//
//import java.awt.Color;
//import java.awt.Container;
//import java.awt.Image;
//import java.awt.Toolkit;
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Iterator;
//import java.util.List;
//
//import javax.imageio.ImageIO;
//import javax.swing.ImageIcon;
//import javax.swing.JLabel;
//import javax.swing.WindowConstants;
//
//public class Ex2_Client implements Runnable{
//	private static MyFrame _win;
//	private static Arena _ar;
//	private game_service game;
//	public Boolean play ;
//
//	
//
//	@Override
//	public void run() {
//		this.play = true;
//		int scenario_num = 1;
//		game = Game_Server_Ex2.getServer(scenario_num); // you have [0,23] games
//		game.addAgent(1);
//		int id = 208049791;
//		game.login(id);
//		String g = game.getGraph();
//		String pks = game.getPokemons();
//
//		directed_weighted_graph gg = JsonToGraph(game.getGraph());
//		init(game);
//
//		this.nothing();
//
//		_win.setTitle("OOP Ex2 - Ajwan Khoury");
//		int ind=0;
//		long dt=100;
////		game.startGame();
//
//		while(game.timeToEnd() != 0) {
//			while(game.isRunning()) {
//				play = false;
//				moveAgants(game, gg, this._ar);
//				try {
//					_win.update(_ar);
//					if(ind%1==0) {_win.repaint();}
//					Thread.sleep(dt);
//					ind++;
//				}
//				catch(Exception e) {
//					e.printStackTrace();
//				}
//			}
//
//			try {
//				_win.update(_ar);
//				gg = JsonToGraph(game.getGraph());
//				_win.repaint();
//				Thread.sleep(dt);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			if(!play) {
//				String res = game.toString();
//
//				System.out.println(res);
//				play= true;
//			}
//		}
//
//		//		String res = game.toString();
//		//
//		//		System.out.println(res);
//		System.exit(0);
//	}
//
//
//	/** 
//	 * Moves each of the agents along the edge,
//	 * in case the agent is on a node the next destination (next edge) is chosen (randomly).
//	 * @param game
//	 * @param gg
//	 * @param
//	 */
//	private static void moveAgants(game_service game, directed_weighted_graph gg, Arena ar) {
//		String lg = game.move();
//		List<CL_Agent> log = Arena.getAgents(lg, gg);
//		_ar.setAgents(log);
//		//ArrayList<OOP_Point3D> rs = new ArrayList<OOP_Point3D>();
//		String fs =  game.getPokemons();
//		//		System.out.println("nadem " +fs);
//		List<CL_Pokemon> ffs = Arena.json2Pokemons(fs);
//
//		//		Iterator<CL_Pokemon> itr = ffs.iterator();
//		//		while(itr.hasNext()) 
//		//			System.out.println(itr.next().toString());
//
//		_ar.setPokemons(ffs);
//		for(int i=0;i<log.size();i++) {
//			CL_Agent ag = log.get(i);
//			int id = ag.getID();
//			int dest = ag.getNextNode();
//			int src = ag.getSrcNode();
//			double v = ag.getValue();
//			if(dest==-1) {
//				dest = nextNode(gg, src , ar);
//				game.chooseNextEdge(ag.getID(), dest);
//				System.out.println("Agent: "+id+", val: "+v+"   turned to node: "+dest);
//			}
//		}
//	}
//
//	/**
//	 * a very simple random walk implementation!
//	 * @param g
//	 * @param src
//	 * @return
//	 */
//	private static int nextNode(directed_weighted_graph g, int src, Arena ar) {
//		int ans = -1;
//		Collection<edge_data> ee = g.getE(src);
//		DWGraph_Algo aga = new DWGraph_Algo(g);	
//
//		ans = whereToGo(aga, src, ar.getPokemons());
//
//		return ans;
//	}
//
//	private static int whereToGo(DWGraph_Algo aga, int src, Collection<CL_Pokemon> ee) {
//		CL_Pokemon dest = distCal(aga, src, ee);
//
//		List<node_data> sp = aga.shortestPath(src,dest.get_edge().getSrc());
//		if(sp.size() == 1)
//			return dest.get_edge().getDest();
//		else
//			return sp.get(1).getKey();
//	}
//
//	private static CL_Pokemon distCal(DWGraph_Algo aga, int src, Collection<CL_Pokemon> ee) {
//		CL_Pokemon ans = null;
//		double max = Integer.MAX_VALUE;
//		Iterator<CL_Pokemon> itr = ee.iterator();
//
//		while(itr.hasNext()) {
//			CL_Pokemon savePoc = itr.next();
//			if(savePoc.get_edge() == null)
//				continue;
//			int dest = savePoc.get_edge().getSrc();
//			double save = aga.shortestPathDist(src, dest);
//			if(save < max) {
//				max = save;
//				ans = savePoc;
//			}
//		}
//
//		return ans;
//	}
//
//	public void init(game_service game) {
//		String g = game.getGraph();
//		String fs = game.getPokemons();
//		game.getPokemons();
//		directed_weighted_graph gg = JsonToGraph(game.getGraph());
//		//gg.init(g);
//		_ar = new Arena();
//		_ar.setGraph(gg);
//		_ar.setPokemons(Arena.json2Pokemons(fs));
//		_win = new MyFrame(1);
//		_win.gameUpdate(this);
//		_win.update(_ar);
//		_win.setSize(1000, 500);
//		Image img = Toolkit.getDefaultToolkit().getImage("background.jpg");
//		_win.setBackground(Color.black);
////		try {
////            _win.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("background.jpg")))));
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//		_win.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//
//		_win.show();
//		
//		String info = game.toString();
//		JSONObject line;
//		try {
//			line = new JSONObject(info);
//			JSONObject ttt = line.getJSONObject("GameServer");
//			int rs = ttt.getInt("agents");
//			System.out.println(info);
//			System.out.println(game.getPokemons());
//			int src_node = 0;  // arbitrary node, you should start at one of the pokemon
//			ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());
//			for(int a = 0;a<cl_fs.size();a++) { 
//				//				Arena.updateEdge(cl_fs.get(a),gg);
//				_ar.updateEdge(cl_fs.get(a),gg);
//
//			}
//			//			Iterator<CL_Pokemon> itr = arena.get.iterator();
//			//
//			//			while(itr.hasNext()) {
//			//				System.out.println(itr.next().toString());
//
//			//			for(int a = 0;a<rs;a++) {
//			//				int ind = a%cl_fs.size();
//			//				CL_Pokemon c = cl_fs.get(ind);
//			//				int nn = c.get_edge().getDest();
//			//				if(c.getType()<0 ) {
//			//					nn = c.get_edge().getSrc();
//			//				}
//			//
//			//				game.addAgent(nn);
//			//			}
//		}
//		catch (JSONException e) {e.printStackTrace();}
//	}
//
//	public game_service getGame() {
//		return this.game;
//	}
//
//	public void setGame(int num) {
//		this.game =  Game_Server_Ex2.getServer(num);		
//		this.init(game);
//		this.nothing();
//	}
//
//	public void nothing() {
//		directed_weighted_graph gg = JsonToGraph(game.getGraph());
//		String lg = game.getAgents();
//		List<CL_Agent> log = Arena.getAgents(lg, gg);
//		_ar.setAgents(log);
//	}
//
//	public void savingGame(String fileName) {
//		directed_weighted_graph gg = JsonToGraph(game.getGraph());
//		dw_graph_algorithms algo = new DWGraph_Algo(gg);
//		//algo.init(gg);
//		algo.save(fileName);
//	}
//
//	public directed_weighted_graph JsonToGraph(String jsonString) {
//		GsonBuilder builder = new GsonBuilder();
//		builder.registerTypeAdapter(directed_weighted_graph.class, new graphJsonDeserializer());
//		Gson gson =  builder.setPrettyPrinting().create();
//
//		//		FileReader reader = new FileReader(file);
//		directed_weighted_graph helper = gson.fromJson(jsonString, directed_weighted_graph.class);
//		return helper;
//
//		//		JsonObject myObject = Json.j.getAsJsonObject();
//		//		JsonArray nodes = myObject.get("Nodes").getAsJsonArray();
//		//		JsonArray edges = myObject.get("Edges").getAsJsonArray();
//	}
//
//	public class graphJsonDeserializer implements JsonDeserializer<directed_weighted_graph>{
//
//		@Override
//		public directed_weighted_graph deserialize(JsonElement json, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
//			JsonObject myObject = json.getAsJsonObject();
//			JsonArray nodes = myObject.get("Nodes").getAsJsonArray();
//			JsonArray edges = myObject.get("Edges").getAsJsonArray();
//
//			DWGraph_DS ans = new DWGraph_DS();
//
//			for(JsonElement set : nodes ) {
//				JsonObject val = set.getAsJsonObject();
//				int id = val.get("id").getAsInt();
//				String loc = val.get("pos").getAsString();
//				node_data n = new NodeData(id);
//				n.setLocation(new GeoLocation(loc));
//				ans.addNode(n);
//			}
//
//			for(JsonElement set : edges) {
//				JsonObject val = set.getAsJsonObject();
//				int src = val.get("src").getAsInt();
//				double weight = val.get("w").getAsDouble();
//				int dest = val.get("dest").getAsInt();
//				ans.connect(src, dest, weight);
//			}
//
//			return ans;
//		}
//
//	}
//}



package gameClient;

import Server.Game_Server_Ex2;
import api.DWGraph_Algo;
import api.DWGraph_DS;
import api.GeoLocation;
import api.NodeData;
import api.directed_weighted_graph;
import api.dw_graph_algorithms;
import api.edge_data;
import api.game_service;
import api.node_data;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.WindowConstants;

public class Ex2_Client implements Runnable{
	private static MyFrame _win;
	private static Arena _ar;
	private game_service game;
	public Boolean play ;

	

	@Override
	public void run() {
		this.play = true;
		int scenario_num = 1;
		game = Game_Server_Ex2.getServer(scenario_num); // you have [0,23] games
		game.addAgent(1);
		int id = 208049791;
		game.login(id);
		String g = game.getGraph();
		String pks = game.getPokemons();

		directed_weighted_graph gg = JsonToGraph(game.getGraph());
		init(game);

		this.nothing();

		_win.setTitle("OOP Ex2 - Ajwan & Ahmad");
		int ind=0;
		long dt=100;
		while(game.timeToEnd() != 0) {
			while(game.isRunning()) {
				play = false;
				moveAgants(game, gg, this._ar);
				try {
					_win.update(_ar);
					if(ind%1==0) {_win.repaint();}
					Thread.sleep(dt);
					ind++;
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}

			try {
				_win.update(_ar);
				gg = JsonToGraph(game.getGraph());
				_win.repaint();
				Thread.sleep(dt);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(!play) {
				String res = game.toString();
				System.out.println(res);
				play= true;
			}
		}
		System.exit(0);
	}


	/** 
	 * Moves each of the agents along the edge,
	 * in case the agent is on a node the next destination (next edge) is chosen (randomly).
	 * @param game
	 * @param gg
	 * @param
	 */
	private static void moveAgants(game_service game, directed_weighted_graph gg, Arena ar) {
		String lg = game.move();
		List<CL_Agent> log = Arena.getAgents(lg, gg);
		_ar.setAgents(log);
		String fs =  game.getPokemons();
		List<CL_Pokemon> ffs = Arena.json2Pokemons(fs);

		_ar.setPokemons(ffs);
		for(int i=0;i<log.size();i++) {
			CL_Agent ag = log.get(i);
			int id = ag.getID();
			int dest = ag.getNextNode();
			int src = ag.getSrcNode();
			double v = ag.getValue();
			if(dest==-1) {
				dest = nextNode(gg, src , ar);
				game.chooseNextEdge(ag.getID(), dest);
				System.out.println("Agent: "+id+", val: "+v+"   turned to node: "+dest);
			}
		}
	}

	/**
	 * a very simple random walk implementation!
	 * @param g
	 * @param src
	 * @return
	 */
	private static int nextNode(directed_weighted_graph g, int src, Arena ar) {
		int ans = -1;
		Collection<edge_data> ee = g.getE(src);
		DWGraph_Algo aga = new DWGraph_Algo(g);	

		ans = whereToGo(aga, src, ar.getPokemons());

		return ans;
	}

	private static int whereToGo(DWGraph_Algo aga, int src, Collection<CL_Pokemon> ee) {
		CL_Pokemon dest = distCal(aga, src, ee);

		List<node_data> sp = aga.shortestPath(src,dest.get_edge().getSrc());
		if(sp.size() == 1)
			return dest.get_edge().getDest();
		else
			return sp.get(1).getKey();
	}

	private static CL_Pokemon distCal(DWGraph_Algo aga, int src, Collection<CL_Pokemon> ee) {
		CL_Pokemon ans = null;
		double max = Integer.MAX_VALUE;
		Iterator<CL_Pokemon> itr = ee.iterator();

		while(itr.hasNext()) {
			CL_Pokemon savePoc = itr.next();
			if(savePoc.get_edge() == null)
				continue;
			int dest = savePoc.get_edge().getSrc();
			double save = aga.shortestPathDist(src, dest);
			if(save < max) {
				max = save;
				ans = savePoc;
			}
		}

		return ans;
	}

	public void init(game_service game) {
		String g = game.getGraph();
		String fs = game.getPokemons();
		game.getPokemons();
		directed_weighted_graph gg = JsonToGraph(game.getGraph());
		_ar = new Arena();
		_ar.setGraph(gg);
		_ar.setPokemons(Arena.json2Pokemons(fs));
		_win = new MyFrame(1);
		_win.gameUpdate(this);
		_win.update(_ar);
		_win.setSize(1000, 500);
		_win.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		_win.show();
		
		String info = game.toString();
		JSONObject line;
		try {
			line = new JSONObject(info);
			JSONObject ttt = line.getJSONObject("GameServer");
			int rs = ttt.getInt("agents");
			System.out.println(info);
			System.out.println(game.getPokemons());
			int src_node = 0;  // arbitrary node, you should start at one of the pokemon
			ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());
			for(int a = 0;a<cl_fs.size();a++) { 
				_ar.updateEdge(cl_fs.get(a),gg);
			}
		}
		catch (JSONException e) {e.printStackTrace();}
	}

	public game_service getGame() {
		return this.game;
	}

	public void setGame(int num) {
		this.game =  Game_Server_Ex2.getServer(num);		
		this.init(game);
		this.nothing();
	}

	public void nothing() {
		directed_weighted_graph gg = JsonToGraph(game.getGraph());
		String lg = game.getAgents();
		List<CL_Agent> log = Arena.getAgents(lg, gg);
		_ar.setAgents(log);
	}

	public void savingGame(String fileName) {
		directed_weighted_graph gg = JsonToGraph(game.getGraph());
		dw_graph_algorithms algo = new DWGraph_Algo(gg);
		algo.save(fileName);
	}

	public directed_weighted_graph JsonToGraph(String jsonString) {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(directed_weighted_graph.class, new graphJsonDeserializer());
		Gson gson =  builder.setPrettyPrinting().create();
		directed_weighted_graph helper = gson.fromJson(jsonString, directed_weighted_graph.class);
		return helper;
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
		}

	}
}