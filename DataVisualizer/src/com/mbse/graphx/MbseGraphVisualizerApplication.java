package com.mbse.graphx;


import javax.swing.JFrame;

import com.mbse.graphx.rhapsody.RhapsodyMbseModel;
import com.mbse.graphx.ui.MbseGraphVisualizerUI;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.view.mxGraph;

/**
 * Use MVC pattern
 * 
 *
 */
public class MbseGraphVisualizerApplication {
	
	// controller
	private MbseGraphController dataController;
	// view
	private MbseGraphVisualizerUI dataView;
	// model
	private MbseModel dataModel;
	
	
	public MbseGraphVisualizerApplication() {
		// Instantiate model
		dataModel = new MbseModel();
		
		// create controller
		dataController = new MbseGraphController(dataModel);
		
		// create Frame with controller as parameter
		dataView = new MbseGraphVisualizerUI(dataModel, dataController);
		
	    //add view as OBserver to model
		dataModel.addView(dataView);
		
	}
	
	public MbseGraphVisualizerApplication(MbseModel someDataModel) {
		// Instantiate model
		dataModel = someDataModel;
		
		// create controller
		dataController = new MbseGraphController(dataModel);
		
		// create Frame with controller as parameter
		dataView = new MbseGraphVisualizerUI(dataModel, dataController);
		
	    //add view as OBserver to model
		dataModel.addView(dataView);
		
	}
	

	public static void main(String[] args) {
				
		new MbseGraphVisualizerApplication(new CustomMbseModel());
	}

	public void update() {
		System.out.println("test");
		dataView.repaint();
		dataView.setVisible(true);
		
		
	}

}
