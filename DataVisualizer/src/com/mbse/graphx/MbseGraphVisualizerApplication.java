package com.mbse.graphx;


import com.mbse.graphx.ui.MbseGraphVisualizerUI;
import com.mbse.model.MbseGraphModel;
import com.mbse.model.MbseModel;
import com.mbse.model.StandardMbseGraphModel;

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
	private MbseGraphModel dataModel;
	
	
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
	
	public MbseGraphVisualizerApplication(StandardMbseGraphModel standardMbseModel) {
		// Instantiate model
		dataModel = standardMbseModel;
		
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
