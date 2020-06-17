import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.text.Utilities;

import com.mbse.graphx.FoldableTree;
import com.mbse.graphx.Port;
import com.mbse.graphx.layout.CallStackLayout;
import com.mbse.graphx.layout.FunctionalBehaviorLayout;
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
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxPoint;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.view.mxEdgeStyle;
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


	
	final static int PORT_DIAMETER = 20;

	final static int PORT_RADIUS = PORT_DIAMETER / 2;
/*	private static mxGraph createBehaviorDummyData() {
		mxGraph graph = new mxGraph() {
			
			// Ports are not used as terminals for edges, they are
			// only used to compute the graphical connection point
			public boolean isPort(Object cell)
			{
				mxGeometry geo = getCellGeometry(cell);
				
				return (geo != null) ? geo.isRelative() : false;
			}
			
			// Implements a tooltip that shows the actual
			// source and target of an edge
			public String getToolTipForCell(Object cell)
			{
				if (model.isEdge(cell))
				{
					return convertValueToString(model.getTerminal(cell, true)) + " -> " +
						convertValueToString(model.getTerminal(cell, false));
				}
				
				return super.getToolTipForCell(cell);
			}
			
			// Removes the folding icon and disables any folding
			public boolean isCellFoldable(Object cell, boolean collapse)
			{
				return false;
			}
		};
		
		
		// Sets the default edge style
		Map<String, Object> style = graph.getStylesheet().getDefaultEdgeStyle();
		style.put(mxConstants.STYLE_EDGE, mxEdgeStyle.ElbowConnector);
		//EDGESTYLE_SIDETOSIDE
		//ElbowConnector
	
		//style.put(mxConstants.STYLE_EDGE, mxEdgeStyle.OrthConnector);
		style.put(mxConstants.STYLE_STROKECOLOR, "red");
		//style.put(mxConstants.STYLE_STROKEWIDTH, 2);
		
		graph.setAllowDanglingEdges(false);
		
		Object parent = graph.getDefaultParent();
		
		

		try
		{
			//String style1 =mxConstants.STYLE_LABEL_POSITION+"="+mxConstants.ALIGN_LEFT+";";
			//String style1 = mxConstants.STYLE_VERTICAL_LABEL_POSITION+"="+mxConstants.ALIGN_TOP+";";
			
			String style1 = mxConstants.STYLE_FILLCOLOR + "=#FFFFFF";
			
			// manual setting
			mxCell root = (mxCell) graph.insertVertex(parent, "F1", "Fonction 1", 40, 20, 450, 300,style1);

			//mxCell root = (mxCell) graph.insertVertex(parent, "F1", "Fonction 1", 0, 0, 450, 300,style1);
			//root.setConnectable(false);
			
			//graph.addCell(new Port("Name In", SwingConstants.LEFT));
			ArrayList<Port> listOfInputs = new ArrayList<Port>();
			//Port rootPortIn1 = new Port("Name In", SwingConstants.LEFT);
			//Port rootPortIn2 = new Port("Name In", SwingConstants.LEFT);
			
			//graph.addCell(rootPortIn1, root);
			//graph.addCell(rootPortIn2, root);
	
			listOfInputs.add(rootPortIn1);
			listOfInputs.add(rootPortIn1);
			Utilies.positionPort(listOfInputs);
			
			ArrayList<Port> listOfOutputs = new ArrayList<Port>();
			Port rootPortOut1 = new Port("Name out",SwingConstants.RIGHT);
			graph.addCell(rootPortOut1, root);
			
			listOfOutputs.add(rootPortOut1);
			Utilies.positionPort(listOfOutputs);
		
			// manual setting
			mxCell v11 = (mxCell) graph.insertVertex(root, "F1.1", "Fonction 1.1", 100, 70, 60, 40);
			mxCell v12 = (mxCell) graph.insertVertex(root, "F1.2", "Fonction 1.2", 100, 220, 80, 30);
			mxCell v13 = (mxCell) graph.insertVertex(root, "F1.3", "Fonction 1.3", 340, 150, 80, 30);
	
			mxCell v11 = (mxCell) graph.insertVertex(root, "F1.1", "Fonction 1.1", 0, 0, 60, 40);
			mxCell v12 = (mxCell) graph.insertVertex(root, "F1.2", "Fonction 1.2", 0, 0, 80, 30);
			mxCell v13 = (mxCell) graph.insertVertex(root, "F1.3", "Fonction 1.3", 0, 0, 80, 30);

			
			graph.insertEdge(parent, null, "Flux 1", root, v11);
			graph.insertEdge(parent, null, "FLux 2", root, v12);
			graph.insertEdge(parent, null, "Flux 3", v13, root);
			

			graph.insertEdge(parent, null, "Flux 4", v11, v13);
			graph.insertEdge(parent, null, "Flux 5", v12, v13);
			
			// Installs auto layout for all levels
			FunctionalBehaviorLayout layout = new FunctionalBehaviorLayout(graph);
			
			
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
	}*/	
	private static mxGraph createBehaviorDummyData() {
		mxGraph graph = new mxGraph() {
			
			// Ports are not used as terminals for edges, they are
			// only used to compute the graphical connection point
			public boolean isPort(Object cell)
			{
				mxGeometry geo = getCellGeometry(cell);
				
				return (geo != null) ? geo.isRelative() : false;
			}
			
			// Implements a tooltip that shows the actual
			// source and target of an edge
			public String getToolTipForCell(Object cell)
			{
				if (model.isEdge(cell))
				{
					return convertValueToString(model.getTerminal(cell, true)) + " -> " +
						convertValueToString(model.getTerminal(cell, false));
				}
				
				return super.getToolTipForCell(cell);
			}
			
			// Removes the folding icon and disables any folding
			public boolean isCellFoldable(Object cell, boolean collapse)
			{
				return false;
			}
		};
		
		
		// Sets the default edge style
		Map<String, Object> style = graph.getStylesheet().getDefaultEdgeStyle();
		style.put(mxConstants.STYLE_EDGE, mxConstants.STYLE_ORTHOGONAL);
		//mxConstants.ALIGN_MIDDLE
		System.out.println(mxConstants.STYLE_VERTICAL_LABEL_POSITION);

	
		//style.put(mxConstants.STYLE_EDGE, mxEdgeStyle.OrthConnector);
		style.put(mxConstants.STYLE_STROKECOLOR, "red");
		//style.put(mxConstants.STYLE_STROKEWIDTH, 2);
		
		graph.setAllowDanglingEdges(false);
		
		Object parent = graph.getDefaultParent();

		try
		{
			mxCell root = (mxCell) graph.insertVertex(parent, "F1", "Fonction 1", 10, 10, 400, 300);
			// manual setting
			Port port1 = new Port("Test", Port.IN);
			
			Port port2 = new Port("test2", Port.IN);
			
			Port port3 = new Port("test3", Port.OUT);
			
			Port port4 = new Port("test4", Port.OUT);
			
			ArrayList<Port> listOfInputPorts = new ArrayList<Port>();
			
			listOfInputPorts.add(port1);
			listOfInputPorts.add(port2);
			Utilies.positionPort(listOfInputPorts);
			graph.addCells(listOfInputPorts.toArray(), root);
			
			ArrayList<Port> listOfOutputPorts = new ArrayList<Port>();
			listOfOutputPorts.add(port3);
			listOfOutputPorts.add(port4);
			Utilies.positionPort(listOfOutputPorts);
			graph.addCells(listOfOutputPorts.toArray(), root);
			
			
			// manual setting
			mxCell v11 = (mxCell) graph.insertVertex(parent, "F1.1", "Fonction 1.1", 0, 0, 80, 30);
			mxCell v12 = (mxCell) graph.insertVertex(parent, "F1.2", "Fonction 1.2", 0, 0, 80, 30);
			mxCell v13 = (mxCell) graph.insertVertex(parent, "F1.3", "Fonction 1.3", 0, 0, 80, 30);
			mxCell v14 = (mxCell) graph.insertVertex(parent, "F1.4", "Fonction 1.4", 0, 0, 80, 30);

			String edgeStyle = mxConstants.STYLE_EDGE+"="+mxConstants.STYLE_ORTHOGONAL;
			graph.insertEdge(parent, null, "Flux 1", port1, v11, edgeStyle);
			graph.insertEdge(parent, null, "FLux 2", port2, v12, edgeStyle);
			graph.insertEdge(parent, null, "Flux 3", v13, port3, edgeStyle);
			
			graph.insertEdge(parent, null, "Flux 7", port1, v14, edgeStyle);
			graph.insertEdge(parent, null, "Flux 8", v14, port4, edgeStyle);
			

			graph.insertEdge(parent, null, "Flux 4", v11, v13, edgeStyle);
			graph.insertEdge(parent, null, "Flux 5", v12, v13, edgeStyle);
			
			// Installs auto layout for all levels
			//FunctionalBehaviorLayout layout = new FunctionalBehaviorLayout(graph);
			//graphicalInterface.currentAppliedLayout = layout;
					
			//layout.execute(graph.getDefaultParent());
			
	        
	        graph.addListener(mxEvent.RESIZE_CELLS,  new mxIEventListener() {

	            @Override
	            public void invoke(Object sender, mxEventObject evt) {
	                System.out.println("resize cells");
	            	//layout.execute(graph.getDefaultParent());
	            }
	        });
	        
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
