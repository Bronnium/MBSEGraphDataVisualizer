package com.mbse.layout;

import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.view.mxGraph;

public class DefaultMbseLayout extends mxCompactTreeLayout implements MbseLayout {

	public DefaultMbseLayout(mxGraph arg0) {
		super(arg0);
	}

	@Override
	public void setHorizontalSpacing(int spacing) {
		setLevelDistance(spacing);
		
	}

	@Override
	public void setVerticalSpacing(int spacing) {
		setNodeDistance(spacing);
		
	}


}
