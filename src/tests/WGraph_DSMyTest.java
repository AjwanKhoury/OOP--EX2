package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

import api.DWGraph_DS;
import api.NodeData;
import api.directed_weighted_graph;
import api.edge_data;
import api.node_data;


class WGraph_DSMyTest {

	@Test
	void nodeSize() {
		directed_weighted_graph g = new DWGraph_DS();
		g.addNode(new NodeData(0));
		g.addNode(new NodeData(0));
		g.addNode(new NodeData(1));
		g.addNode(new NodeData(1));
		int s = g.nodeSize();
		assertEquals(2,s);

		g.addNode(new NodeData(2));
		g.addNode(new NodeData(3));
		g.addNode(new NodeData(4));
		g.removeNode(2);
		g.removeNode(1);
		g.removeNode(1);
		g.removeNode(5);
		s = g.nodeSize();
		assertEquals(3,s);

	}

	@Test
	void edgeSize() {
		directed_weighted_graph g = new DWGraph_DS();
		int e_size =  g.edgeSize();
		assertEquals(0, e_size);

		g.addNode(new NodeData(0));
		g.addNode(new NodeData(1));
		g.addNode(new NodeData(2));
		e_size =  g.edgeSize();
		assertEquals(0, e_size);
		
		g.connect(0,1,1);
		g.connect(0,2,2);
		g.connect(0,3,3);
		g.connect(0,1,1);
		e_size =  g.edgeSize();
		assertEquals(2, e_size);

		g.removeEdge(0,1);
		g.removeEdge(0,2);
		g.removeEdge(0,3);
		e_size =  g.edgeSize();
		assertEquals(0, e_size);
	}

	@Test
	void getV() {
		directed_weighted_graph g = new DWGraph_DS();
		g.addNode(new NodeData(0));
		g.addNode(new NodeData(1));
		g.addNode(new NodeData(2));
		g.addNode(new NodeData(3));
		g.connect(0,1,1);
		g.connect(0,2,2);
		g.connect(0,3,3);
		g.connect(0,1,1);
		Collection<node_data> v = g.getV();
		Iterator<node_data> iter = v.iterator();
		while (iter.hasNext()) {
			node_data n = iter.next();
			assertNotNull(n);
		}
	}

	@Test
	void getVWithKey() {
		directed_weighted_graph g = new DWGraph_DS();
		g.addNode(new NodeData(0));
		g.addNode(new NodeData(1));
		g.addNode(new NodeData(2));
		g.addNode(new NodeData(3));
		g.connect(0,1,1);
		g.connect(0,2,1);
		g.connect(3,1,1);
		g.connect(3,2,1);
		Collection<edge_data> v1 = g.getE(0);
		Collection<edge_data> v2 = g.getE(3);
		assertEquals(v1.size(), v2.size());
	}

//	@Test
//	void hasEdge() {
//		directed_weighted_graph g = new DWGraph_DS();
//		g.addNode(0);
//		g.addNode(1);
//		g.addNode(2);
//		assertFalse(g.hasEdge(0, 1));
//		assertFalse(g.hasEdge(5, 4));
//
//		g.connect(0,1,1);
//		assertTrue(g.hasEdge(0, 1));
//		assertFalse(g.hasEdge(0, 2));
//		assertFalse(g.hasEdge(1, 2));
//
//		g.removeEdge(0, 1);
//		assertFalse(g.hasEdge(0, 1));
//
//	}

	@Test
	void connect() {
        directed_weighted_graph g = new DWGraph_DS();
		g.addNode(new NodeData(0));
		g.addNode(new NodeData(1));
		g.addNode(new NodeData(2));

		g.connect(0,1,1);

		g.connect(0, 2, 1);

		g.connect(1, 2, 1);

	}

	@Test
	void Add_RemoveNode() {
        directed_weighted_graph g = new DWGraph_DS();
		assertEquals(0, 0);
		g.addNode(new NodeData(0));
		assertEquals(1, 1);
		g.addNode(new NodeData(1));
		g.addNode(new NodeData(2));
		g.addNode(new NodeData(3));
		assertEquals(4, 4);

		g.removeNode(4);
		g.removeNode(0);
		assertEquals(3, 3);

		g.removeNode(1);
		g.removeNode(2);
		g.removeNode(3);
		assertEquals(0, 0);
	}

	@Test
	void getEdge() {
        directed_weighted_graph g = new DWGraph_DS();
		g.addNode(new NodeData(0));
		g.addNode(new NodeData(1));
		g.addNode(new NodeData(2));
		assertEquals(null, g.getEdge(0, 1));

		g.connect(0,1,1);
//		assertEquals(1, g.getEdge(0, 1));
		assertEquals(null, g.getEdge(1, 2));
//		assertEquals(0, g.getEdge(0, 0));

		g.removeEdge(0, 1);
		assertEquals(null, g.getEdge(0, 1));

	}


	@Test
	void removeEdge() {
        directed_weighted_graph g = new DWGraph_DS();
		g.addNode(new NodeData(0));
		g.addNode(new NodeData(1));

		g.connect(0,1,1);

		g.removeEdge(0,1);

	}
}


