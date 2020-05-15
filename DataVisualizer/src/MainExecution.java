import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map.Entry;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.mbse.graphx.layout.CallStackLayout;
import com.mbse.graphx.ui.MbseGraphVisualizer;
import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.layout.mxStackLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

public class MainExecution {

	private static MbseGraphVisualizer graphicalInterface;

	/*
	 * Utilisation souhaitée dans le plugin Rhapsody
	 * 
	 */
	public static void main(String[] args) {
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
		
		// déclaration du Visualiseur
		graphicalInterface = new MbseGraphVisualizer();

		// création du graph depuis le modèle avec SnecmaML
		graphicalInterface.setGraphData(createDummyData());

		// les différents layouts sont testés dans le preview
		// on recupere le resultat du graph de donnée
		synchronized (graphicalInterface) {
			try {
				graphicalInterface.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		System.out.println("test");

		// récupération des position des objects
		getObjectPositions();


		//((mxCell) graphModel.getCell("test")).getGeometry().getX());;
	}


	/**
	 * To be defined.
	 * @return Probably Map<GUID, (X.int,Y.int)>
	 */
	public static void getObjectPositions() {
		mxGraph graph = graphicalInterface.getGraphModel();

		mxGraphModel graphModel = (mxGraphModel) graph.getModel();		

		for (Entry<String, Object> pair : graphModel.getCells().entrySet()) {
			System.out.println(pair.getKey() +" / "+ pair.getValue());
			mxCell cell = (mxCell) pair.getValue();
			if (cell.isVertex()) {
				System.out.println(cell.getGeometry().getX()+","+cell.getGeometry().getY());
			}

		}
		return;
	}
	private static mxGraph createDummyData() {
		mxGraph graph = new mxGraph();
		Object parent = graph.getDefaultParent();

		graph.setCellsDisconnectable(false);
		graph.setAllowDanglingEdges(false);
		graph.setCellsEditable(false);
		final CallStackLayout layout = new CallStackLayout(graph); 
		//final mxStackLayout layout = new mxStackLayout(graph, true, 20, 10, 10, 10); 
		//layout.setUseBoundingBox(false);
		//layout.setEdgeRouting(false);
		//layout.setLevelDistance(10); // gestion de l'espacement vertical
		//layout.setNodeDistance(20); // gestion de l'espacement horizontal


		graph.getModel().beginUpdate();
		try {
			Object root = graph.insertVertex(parent, "treeRoot", "System", 0, 0, 60, 40);

			Object v1 = graph.insertVertex(parent, "v1", "System A", 0, 0, 60, 40);
			graph.insertEdge(parent, null, "", root, v1);

			Object v2 = graph.insertVertex(parent, "v2", "System B", 0, 0, 60, 40);
			graph.insertEdge(parent, null, "", root, v2);

			Object v3 = graph.insertVertex(parent, "v3", "System C", 0, 0, 60, 40);
			graph.insertEdge(parent, null, "", root, v3);

			Object v11 = graph.insertVertex(parent, "v11", "Child 1.1", 0, 0, 60, 40);
			graph.insertEdge(parent, null, "", v1, v11);

			Object v12 = graph.insertVertex(parent, "v12", "Child 1.2", 0, 0, 60, 40);
			graph.insertEdge(parent, null, "", v1, v12);

			Object v13 = graph.insertVertex(parent, "v13", "Child 1.3", 0, 0, 60, 40);
			graph.insertEdge(parent, null, "", v1, v13);
			
			Object v14 = graph.insertVertex(parent, "v14", "Child 1.4", 0, 0, 60, 40);
			graph.insertEdge(parent, null, "", v1, v14);

			Object v15 = graph.insertVertex(parent, "v15", "Child 1.5", 0, 0, 60, 40);
			graph.insertEdge(parent, null, "", v1, v15);

			Object v21 = graph.insertVertex(parent, "v21", "Child 2.1", 0, 0, 60, 40);
			graph.insertEdge(parent, null, "", v2, v21);
			
			Object v22 = graph.insertVertex(parent, "v22", "Child 2.2", 0, 0, 60, 40);
			graph.insertEdge(parent, null, "", v2, v22);

			Object v221 = graph.insertVertex(parent, "v221", "Child 2.2.1", 0, 0, 60, 40);
			graph.insertEdge(parent, null, "", v22, v221);

			Object v222 = graph.insertVertex(parent, "v222", "Child 2.2.2", 0, 0, 60, 40);
			graph.insertEdge(parent, null, "", v22, v222);

			Object v31 = graph.insertVertex(parent, "v31", "Child 3.1", 0, 0, 60, 40);
			graph.insertEdge(parent, null, "", v3, v31);

			layout.execute(parent);
		} finally {
			
			graph.getModel().endUpdate();
		}

		
		return graph;
	}

}
