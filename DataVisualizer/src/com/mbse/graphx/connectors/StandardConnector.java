package com.mbse.graphx.connectors;

import com.mbse.graphx.MbseGraphVisualizerApplication;

public class StandardConnector {
	
	public static void main(String[] args) {

		System.out.println();

		// input data
		
		RhapsodyMbseModel rhapsodyMbseModel = new RhapsodyMbseModel();

		MbseGraphVisualizerApplication application = new MbseGraphVisualizerApplication(rhapsodyMbseModel);

		// les diff�rents layouts sont test�s dans le preview
		// on recupere le resultat du graph de donn�e
		synchronized (application.dataController) {
			try {
				application.dataController.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// r�cup�ration des position des objects
		//getGraphicalPosition(rhapsodyMbseModel);

		// fermeture de l'application
		application.close();
	}

}
