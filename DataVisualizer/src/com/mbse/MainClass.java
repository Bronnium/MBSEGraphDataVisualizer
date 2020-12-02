package com.mbse;

import java.util.LinkedList;

import com.mbse.controller.MbseGraphController;
import com.mbse.model.D2Element;
import com.mbse.model.D2Line;
import com.mbse.model.MbseGraphModel;
import com.mbse.view.MbseGraphView;


public class MainClass {

	private static LinkedList<D2Element> vertexList;
	private static LinkedList<D2Line> edgeList;
	
	/**
	 * Main execution point for standalone application
	 * The MBSE Data Visualizer is base on MVC pattern.
	 * 
	 * Model - stores the data, based on jgraphx
	 * Controller - in charge of the logical part
	 * View - is the user interface 
	 */
	public static void main(String[] args) {
		
		dummyData();
		// create an instance of a MBSE model
		MbseGraphModel mbseGraphModel = new MbseGraphModel(vertexList, edgeList);
		
		// create an instance of a Mbse View
		MbseGraphView mbseGraphView = new MbseGraphView();
		
		// creates an instance of a MBSE Controller
		MbseGraphController mbseGraphController = new MbseGraphController(mbseGraphModel,mbseGraphView);
		
		mbseGraphController.displayView();
		
		// creates an instance of an UI (the UI is linked to the model)
		//MbseGraphVisualizerApplication applicationView = new MbseGraphVisualizerApplication(standardMbseModel);

		/*
		// les diff�rents layouts sont test�s dans le preview
		// on recupere le resultat du graph de donn�e
		synchronized (applicationView.dataController) {
			try {
				applicationView.dataController.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
*/
		// r�cup�ration des position des objects
		//standardMbseModel.getGraphicalPosition();


	}



	/**
	 * The function creates dummy data for graph positioning
	 */
	private static void dummyData() {
	/* JSON format ?
	 * vertex
	 * {
	 * 'GUID':guid_value,
	 * 'NAME':name
	 * }
	 * 
	 * edge
	 * {
	 * GUID:guid_value,
	 * source: GUID,
	 * target: GUID
	 * }
	 */
		vertexList = new LinkedList<D2Element>();
		
		vertexList.add(new D2Element("0", "Function"));
		vertexList.add(new D2Element("1", "Function 1"));
		vertexList.add(new D2Element("1-1", "Function 1-1"));
		vertexList.add(new D2Element("1-2", "Function 1-2"));
		vertexList.add(new D2Element("2", "Function 2"));
		
		edgeList = new LinkedList<D2Line>();
		edgeList.add(new D2Line("a", "", "0", "1"));
		edgeList.add(new D2Line("b", "", "1", "1-1"));
		edgeList.add(new D2Line("c", "", "1", "1-2"));
		edgeList.add(new D2Line("d", "", "0", "2"));
		
	}
}
