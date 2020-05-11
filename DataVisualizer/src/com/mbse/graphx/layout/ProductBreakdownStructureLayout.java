package com.mbse.graphx.layout;

import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.view.mxGraph;

/**
 * A Product Breakdown Structure is the decomposition of certain System.
 * This layout is suitable for a System decomposition. It's based on a Tree Layout
 * from Top to Bottom.
 * 
 * Note that, a system contains other systems (and can be recursive).
 */
public class ProductBreakdownStructureLayout extends mxCompactTreeLayout {

	/**
	 * 
	 * @param graph - the graphdata that will be displayed
	 */
	public ProductBreakdownStructureLayout(mxGraph graph) {
		super(graph, false);
		
		this.setResetEdges(true);
		this.setLevelDistance(10); // gestion de l'espacement vertical
		//layout.setNodeDistance(20); // gestion de l'espacement horizontal
		
	}

}
