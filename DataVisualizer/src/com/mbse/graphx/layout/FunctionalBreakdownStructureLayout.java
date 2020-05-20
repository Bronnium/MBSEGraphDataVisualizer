package com.mbse.graphx.layout;

import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.view.mxGraph;

/**
 * A Product Breakdown Structure is the decomposition of certain System.
 * This layout is suitable for a System decomposition. It's based on a Tree Layout
 * from Top to Bottom.
 * 
 * Note that, a system contains other systems (and can be recursive).
 */
public class FunctionalBreakdownStructureLayout extends mxCompactTreeLayout {

	private final FunctionalBreakdownStructureLayout layout;
	/**
	 * 
	 * @param graph - the graphdata that will be displayed
	 */
	public FunctionalBreakdownStructureLayout(mxGraph graph) {
		super(graph, true);
		
		layout = this;
		this.setResetEdges(true);
		this.setLevelDistance(10); // gestion de l'espacement vertical
		//layout.setNodeDistance(20); // gestion de l'espacement horizontal
		
        this.setUseBoundingBox(false);
        this.setEdgeRouting(false);
        this.setLevelDistance(30);
        this.setNodeDistance(10);
        // interdire globalement le déplacement de tous les objets dans le graph
        graph.setCellsMovable(false);
        
        


		
		//graph.setCellStyle("ROUNDED;strokeColor=red;fillColor=green",test);
	}

}
