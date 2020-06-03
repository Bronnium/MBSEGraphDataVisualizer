package com.mbse.graphx.connectors;

import com.mbse.graphx.FoldableTree;

import com.mxgraph.view.mxGraph;

public class RhapsodyConnector {
	private FoldableTree graph;

	public RhapsodyConnector() {
		graph = new FoldableTree();
	}

	public mxGraph createDummyData() {
		Object parent = graph.getDefaultParent();

		Object root = graph.insertVertex(parent, "treeRoot", "System", 0, 0, 60, 40);

		Object v1 = graph.insertVertex(parent, "v1", "System A", 0, 0, 60, 40);
		graph.insertEdge(parent, null, "", root, v1);

		Object v2 = graph.insertVertex(parent, "v2", "System B", 0, 0, 60, 40);
		graph.insertEdge(parent, null, "", root, v2);

		Object v3 = graph.insertVertex(parent, "v3", "System C", 0, 0, 60, 40);
		graph.insertEdge(parent, null, "", root, v3);

		Object v11 = graph.insertVertex(parent, "v11", "Child 1.1", 0, 0, 60, 40);
		graph.insertEdge(parent, null, "", v1, v11);

		Object v12 = graph.insertVertex(parent, "v12", "Child 1.2", 0, 0, 60, 40);
		graph.insertEdge(parent, null, "", v1, v12);

		Object v13 = graph.insertVertex(parent, "v13", "Child 1.3", 0, 0, 60, 40);
		graph.insertEdge(parent, null, "", v1, v13);

		Object v14 = graph.insertVertex(parent, "v14", "Child 1.4", 0, 0, 60, 40);
		graph.insertEdge(parent, null, "", v1, v14);

		Object v15 = graph.insertVertex(parent, "v15", "Child 1.5", 0, 0, 60, 40);
		graph.insertEdge(parent, null, "", v1, v15);

		Object v21 = graph.insertVertex(parent, "v21", "Child 2.1", 0, 0, 60, 40);
		graph.insertEdge(parent, null, "", v2, v21);

		Object v22 = graph.insertVertex(parent, "v22", "Child 2.2", 0, 0, 60, 40);
		graph.insertEdge(parent, null, "", v2, v22);

		Object v221 = graph.insertVertex(parent, "v221", "Child 2.2.1", 0, 0, 60, 40);
		graph.insertEdge(parent, null, "", v22, v221);

		Object v222 = graph.insertVertex(parent, "v222", "Child 2.2.2", 0, 0, 60, 40);
		graph.insertEdge(parent, null, "", v22, v222);

		return graph;
	}
}
