package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import api.DWGraph_Algo;
import api.DWGraph_DS;
import api.NodeData;
import api.directed_weighted_graph;
import api.dw_graph_algorithms;
import api.node_data;

class WGraph_AlgoMyTest {

	@Test
	void get_Graph() {
		directed_weighted_graph g = test_graph1();
		dw_graph_algorithms ag = new DWGraph_Algo();
		ag.init(g);
		assertEquals(g, ag.getGraph());
	}

	@Test
	void isConnected() {
		directed_weighted_graph g1 = test_graph1();
		dw_graph_algorithms ag1 = new DWGraph_Algo();
		ag1.init(g1);
		assertTrue(ag1.isConnected());

		directed_weighted_graph g2 = test_graph2();
		dw_graph_algorithms ag2 = new DWGraph_Algo();
		ag2.init(g2);
		assertFalse(ag2.isConnected());

		g1.removeEdge(4, 0);
		ag1.init(g1);
		assertTrue(ag1.isConnected());

		g1.removeEdge(4, 3);
		ag1.init(g1);
		assertFalse(ag1.isConnected());



	}

	@Test
	void shortestPathDist() {
		directed_weighted_graph g0 = test_graph1();
		dw_graph_algorithms ag0 = new DWGraph_Algo();
		ag0.init(g0);
		assertTrue(ag0.isConnected());

		double d = ag0.shortestPathDist(0,2);
		assertEquals(d, 3);

		d = ag0.shortestPathDist(0,5);
		assertEquals(d, -1);

		d = ag0.shortestPathDist(0,4);
		assertEquals(d, 1);

		g0.removeEdge(0, 4);
		ag0.init(g0);
		d = ag0.shortestPathDist(0,4);
		assertEquals(d, 6);

		g0.removeEdge(0	, 1);
		d = ag0.shortestPathDist(0, 4);
		assertEquals(d, -1);
	}

	@Test
	void shortestPath() {
		directed_weighted_graph g0 = test_graph1();
		dw_graph_algorithms ag0 = new DWGraph_Algo();
		ag0.init(g0);
		List<node_data> sp = ag0.shortestPath(0,2);
		int[] checkKey = {0, 4, 3, 2};
		int i = 0;
		System.out.println(sp.size());
		for(node_data n: sp) {
			assertEquals(n.getKey(), checkKey[i]);
			i++;
		}
	}

	@Test
	void save_load() {
		directed_weighted_graph g = test_graph1();
		dw_graph_algorithms ag1 = new DWGraph_Algo();
		ag1.init(g);
		ag1.save("test_graph.obj");

		dw_graph_algorithms ag2 = new DWGraph_Algo();
		ag2.load("test_graph.obj");

		ag1.equals(ag2);


		//		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		//		String json1 = gson.toJson(ag1.getGraph());
		//		String json2 = gson.toJson(ag2.getGraph());
		//		assertEquals(json1, json2);
	}

	private directed_weighted_graph test_graph1() {
		directed_weighted_graph g0 = new DWGraph_DS();
		g0.addNode(new NodeData(0));
		g0.addNode(new NodeData(1));
		g0.addNode(new NodeData(2));
		g0.addNode(new NodeData(3));
		g0.addNode(new NodeData(4));

		g0.connect(0,1,1);
		g0.connect(1, 0, 1);
		g0.connect(1,2,3);
		g0.connect(2, 1, 3);
		g0.connect(2,3,1);
		g0.connect(3, 2, 1);
		g0.connect(3,4,1);
		g0.connect(4, 3, 1);
		g0.connect(4,0,1);
		g0.connect(0, 4, 1);

		return g0;
	}

	private directed_weighted_graph test_graph2() {
		directed_weighted_graph g0 = new DWGraph_DS();
		g0.addNode(new NodeData(0));
		g0.addNode(new NodeData(1));
		g0.addNode(new NodeData(2));
		g0.addNode(new NodeData(3));
		g0.addNode(new NodeData(4));

		return g0;
	}
}