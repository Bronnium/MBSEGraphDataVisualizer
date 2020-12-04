package com.mbse.layout;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;

public class FunctionalBehaviorLayout extends mxHierarchicalLayout implements MbseLayout {


	public FunctionalBehaviorLayout(mxGraph graph, mxCell rootElement) {
		//super(graph, SwingConstants.WEST);
		super(graph);
		setMoveParent(true);
		setResizeParent(true);
		setFineTuning(false);
		traverseAncestors = true;	
	}

	@Override
	public void setHorizontalSpacing(int spacing) {
		setInterRankCellSpacing(spacing);

	}

	@Override
	public void setVerticalSpacing(int spacing) {
		setIntraCellSpacing(spacing);		
	}

	@Override
	public void execute(Object parent) {
		super.execute(parent);
		
	}


}
