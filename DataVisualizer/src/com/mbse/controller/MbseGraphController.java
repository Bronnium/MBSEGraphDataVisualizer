package com.mbse.controller;

import com.mbse.model.MbseGraphModel;
import com.mbse.view.MbseGraphView;

public class MbseGraphController {

	private MbseGraphView view;
	private MbseGraphModel model;

	public MbseGraphController(MbseGraphModel mbseGraphModel, MbseGraphView mbseGraphView) {
		model = mbseGraphModel;
		view = mbseGraphView;
		
		
		view.addMbseGraphComponent(model);
		
		model.getAppliedLayout().execute(model.getDefaultParent());
		
	}

	public void displayView() {
		view.setVisible(true);
	}

}
