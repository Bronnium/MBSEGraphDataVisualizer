import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import com.mbse.graphx.FoldableTree;
import com.mbse.graphx.layout.CallStackLayout;
import com.mbse.graphx.layout.FunctionalBreakdownStructureLayout;
import com.mbse.graphx.layout.ProductBreakdownStructureLayout;
import com.mbse.graphx.ui.MbseGraphVisualizer;
import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.layout.mxStackLayout;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.model.mxICell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource.mxIEventListener;
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
		//graphicalInterface = new MbseGraphVisualizer("Product Breakdown Structure Preview");
		graphicalInterface = new MbseGraphVisualizer("Functional Behavior Preview");

		// création du graph depuis le modèle avec SnecmaML
		graphicalInterface.setGraphData(createBehaviorDummyData());

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
	}


	private static mxGraph createBehaviorDummyData() {
		mxGraph graph = new mxGraph();
		
		Object parent = graph.getDefaultParent();
		
		

		try
		{
		
			Object root = graph.insertVertex(parent, "F1", "Fonction 1", 0, 0, 60, 40, "column");
			
			Object v11 = graph.insertVertex(root, "F1.1", "Fonction 1.1", 0, 0, 60, 40);
			Object v12 = graph.insertVertex(root, "F1.2", "Fonction 1.2", 0, 0, 80, 30);
			Object v13 = graph.insertVertex(root, "F1.3", "Fonction 1.3", 0, 0, 80, 30);
			
			graph.insertEdge(parent, null, "", root, v11);
			graph.insertEdge(parent, null, "", root, v12);
			
			graph.insertEdge(parent, null, "", v13, root);
			

			graph.insertEdge(parent, null, "", v11, v13);
			graph.insertEdge(parent, null, "", v12, v13);
			
			// Installs auto layout for all levels
			mxHierarchicalLayout layout = new mxHierarchicalLayout(graph, SwingConstants.WEST);
			
			
			//layout.edgeStyle=4;
			//layout.intraCellSpacing=20;
			//layout.interRankCellSpacing=70;
			
			layout.execute(graph.getDefaultParent());
		}
		finally
		{
			// Updates the display
			graph.getModel().endUpdate();
		}
		return graph;
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
	

	private static mxGraph createStructureDummyData() {
		mxGraph graph = new FoldableTree();
		Object parent = graph.getDefaultParent();

		graph.getModel().beginUpdate();
		try {
			Object root = graph.insertVertex(parent, "treeRoot", "System", 0, 0, 60, 40);
			
			// ces lignes permettent de créer un conteneur parent
			//graph.setDefaultParent(root);
			//Object parent = root;
			
			
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

			//layout.execute(parent);
			
			final ProductBreakdownStructureLayout layout = new ProductBreakdownStructureLayout(graph);
			graphicalInterface.currentAppliedLayout = layout;
			layout.execute(parent);
			
	        graph.addListener(mxEvent.FOLD_CELLS,  new mxIEventListener() {

	            @Override
	            public void invoke(Object sender, mxEventObject evt) {
	                System.out.println("folding repositioning");
	            	layout.execute(graph.getDefaultParent());
	            }
	        });
			
		} finally {
			
			graph.getModel().endUpdate();
		}

		
		return graph;
	}

}
