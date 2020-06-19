

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
		
		//Cr�ation de notre fen�tre avec le contr�leur en param�tre
		dataView = new MbseGraphVisualizerUI(dataController);
		
	    //Ajout de la fen�tre comme observer de notre mod�le
		dataModel.addView(dataView);
		
	}
	
	public static void main(String[] args) {
		new MbseGraphVisualizerApplication();
	}

}
