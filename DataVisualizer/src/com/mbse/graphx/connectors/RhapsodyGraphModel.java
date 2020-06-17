package com.mbse.graphx.connectors;

import com.mbse.graphx.FoldableTree;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.view.mxGraph;
import com.telelogic.rhapsody.core.IRPDependency;
import com.telelogic.rhapsody.core.IRPModelElement;

public class RhapsodyGraphModel extends mxGraph {

	private FoldableTree graph;
	private Object parent;

	public RhapsodyGraphModel() {
		// TODO Auto-generated constructor stub
		
		graph = new FoldableTree();
		parent = graph.getDefaultParent();

	}
	
	/**
	 * Adds a vertex
	 * @param rhapsodyElement
	 * @return
	 */
	public Object addVertex(IRPModelElement rhapsodyElement) {
		return graph.insertVertex(parent, rhapsodyElement.getGUID(), rhapsodyElement.getDisplayName(), 0, 0, 60, 40);
	}
	
	protected Object findObject(String GUID) {
		return ((mxGraphModel)(graph.getModel())).getCell(GUID);
	}
	
	/**
	 * Adds an edge which is directed from Source to Target
	 * @param edgeElement
	 * @return
	 */
	public Object addEdge(IRPDependency edgeElement) {
		Object source = findObject(edgeElement.getDependent().getGUID());
		Object target = findObject(edgeElement.getDependsOn().getGUID());
		return graph.insertEdge(parent, null, edgeElement.getGUID(), source, target);
	}
	
	
}
