package com.mbse.graphx;

import javax.swing.JFrame;

import com.mbse.graphx.ui.MbseGraphVisualizerUI;
import com.mxgraph.model.mxGraphModel;

public class MbseModel extends mxGraphModel {

	private MbseGraphVisualizerUI dataView;
	
	
	public void addView(MbseGraphVisualizerUI data)
	{
		this.dataView = data;
	}
	
	public void notifyView()
	{
		dataView.updateView();
	}
	
}
