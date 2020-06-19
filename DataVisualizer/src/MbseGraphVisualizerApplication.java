

import javax.swing.JFrame;

import com.mbse.graphx.MbseGraphController;
import com.mbse.graphx.MbseModel;
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
		// instanciate model
		dataModel = new MbseModel();
		
		// create controller
		dataController = new MbseGraphController(dataModel);
		
		//Création de notre fenêtre avec le contrôleur en paramètre
		dataView = new MbseGraphVisualizerUI(dataController);
		
	    //Ajout de la fenêtre comme observer de notre modèle
		dataModel.addView(dataView);
		
	}
	
	public static void main(String[] args) {
		new MbseGraphVisualizerApplication();
	}

}
