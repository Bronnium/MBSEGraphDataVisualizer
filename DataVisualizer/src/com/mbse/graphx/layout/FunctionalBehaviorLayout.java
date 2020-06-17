package com.mbse.graphx.layout;

import javax.swing.SwingConstants;

import com.mxgraph.canvas.mxICanvas;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;

public class FunctionalBehaviorLayout extends mxHierarchicalLayout {

	public FunctionalBehaviorLayout(mxGraph graph) {
		super(graph, SwingConstants.WEST);
		//super(graph);
		//setMoveTree(true); //tree is moved to TOP LEFT when updating view
		setMoveParent(true);
		setParentBorder(0);
		setResizeParent(true);
		setFineTuning(true);
		traverseAncestors = true;
		//this.graph
		//parentBorder=1;
		
		
	}
	
	@Override
	public void execute(Object parent) {
		// TODO Auto-generated method stub
		super.execute(parent);
		
		System.out.println(graph.getGraphBounds().getWidth()+","+graph.getGraphBounds().getHeight());
		
		//mxCell v11 = (mxCell) graph.insertVertex(null, "F1", "Fonction 1", 0, 0, graph.getGraphBounds().getWidth(), graph.getGraphBounds().getHeight());

		graph.getView();
		//g.drawRect(20, 20, 100, 100);
	}

}
