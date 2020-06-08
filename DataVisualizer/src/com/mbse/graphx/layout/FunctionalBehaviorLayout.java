package com.mbse.graphx.layout;

import javax.swing.SwingConstants;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.view.mxGraph;

public class FunctionalBehaviorLayout extends mxHierarchicalLayout {

	public FunctionalBehaviorLayout(mxGraph graph) {
		super(graph, SwingConstants.WEST);
		
		//setMoveTree(true); //tree is moved to TOP LEFT when updating view
		setMoveParent(true);
		setParentBorder(0);
		setResizeParent(true);
	}

}
