package com.mbse.graphx;

import com.mbse.graphx.ui.MbseGraphVisualizerUI;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.view.mxGraph;

public class MbseModel extends mxGraph {

	protected MbseGraphVisualizerUI dataView;
	
	
	public void addView(MbseGraphVisualizerUI data)
	{
		this.dataView = data;
	}
	
	public void notifyView()
	{
		dataView.updateView(this);
	}
	
}
