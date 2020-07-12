package com.mbse.graphx;


import com.mbse.graphx.ui.MbseGraphVisualizerUI;

/**
 * Use MVC pattern
 * 
 *
 */
public class MbseGraphVisualizerApplication {
	
	// controller
	public MbseGraphController dataController;
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

	public void close() {
		dataView.dispose();
		dataController = null;
		dataModel = null;
		dataView = null;
		
	}

}
