package com.mbse.graphx.connectors;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

import com.mbse.graphx.FoldableTree;
import com.mbse.graphx.layout.FunctionalBreakdownStructureLayout;
import com.mbse.graphx.layout.ProductBreakdownStructureLayout;
import com.mbse.graphx.ui.MbseGraphVisualizerUI;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.util.mxPoint;
import com.mxgraph.util.mxRectangle;
import com.mxgraph.view.mxEdgeStyle;
import com.mxgraph.view.mxGraph;
import com.telelogic.rhapsody.core.IRPApplication;
import com.telelogic.rhapsody.core.IRPClass;
import com.telelogic.rhapsody.core.IRPCollection;
import com.telelogic.rhapsody.core.IRPDependency;
import com.telelogic.rhapsody.core.IRPDiagram;
import com.telelogic.rhapsody.core.IRPGraphEdge;
import com.telelogic.rhapsody.core.IRPGraphElement;
import com.telelogic.rhapsody.core.IRPGraphNode;
import com.telelogic.rhapsody.core.IRPGraphicalProperty;
import com.telelogic.rhapsody.core.IRPModelElement;
import com.telelogic.rhapsody.core.IRPNode;
import com.telelogic.rhapsody.core.IRPOperation;
import com.telelogic.rhapsody.core.IRPProject;
import com.telelogic.rhapsody.core.RPOperation;
import com.telelogic.rhapsody.core.RPUserPlugin;

public class RhapsodyConnector extends RPUserPlugin {

	protected IRPApplication m_rhpApplication = null;
	protected MbseGraphVisualizerUI graphicalInterface;
	private IRPDiagram diagram;
	private LinkedHashMap<String, IRPGraphElement> map;
	private int rhapsodyYoffset = 10;
	private int rhapsodyXoffset = 10;

	// called when the plug-in is loaded
	public void RhpPluginInit(final IRPApplication rpyApplication) {
		// keep the application interface for later use
		m_rhpApplication = rpyApplication;

		//graphicalInterface  = new MbseGraphVisualizer();
		String msg = "MBSE Graph Visualizer plug-in: Loaded.\n";
		m_rhpApplication.writeToOutputWindow("Log" , msg);
	}

	// called when the plug-in menu item under the "Tools" menu is selected	
	public void RhpPluginInvokeItem() {
		// show the active project overridden properties
		//		String projectProps = new String();
		//		IRPProject prj = m_rhpApplication.activeProject();
		//		if (prj != null) {
		//			IRPCollection propsCollection = prj.getOverriddenProperties(0);
		//			if (propsCollection != null && propsCollection.getCount() > 0) {
		//				Iterator proprsIter = propsCollection.toList().iterator();
		//				while (proprsIter.hasNext()) {
		//					projectProps += proprsIter.next() + "\n";
		//				}
		//			} else {
		//				projectProps = "No overriden properties";
		//
		//			}
		//		} else {
		//			projectProps = "No active project";
		//		}
		//		//JOptionPane.showMessageDialog(null,"Hello world from SimplePlugin.RhpPluginInvokeItem.\n Your active project overridden properties are: \n"+ projectProps);
		//		String msg = "Hello world from SimplePlugin.RhpPluginInvokeItem.\n Your active project overridden properties are: \n"+ projectProps + "\n";
		//		m_rhpApplication.writeToOutputWindow("Simple Plugin" , msg);
	}

	// called when the plug-in pop-up menu (if applicable) is selected
	public void OnMenuItemSelect(String menuItem) {
		//show the selected element name
		String selElemName = new String();
		IRPModelElement element = m_rhpApplication.getSelectedElement();
		if(element != null){
			selElemName = element.getName();			
		} else {
			selElemName = "No selected element";
		}
		String msg = "Hello world from SimplePlugin.OnMenuItemSelect " + menuItem + "\n Selected element name is: " + selElemName + "\n"; 
		//JOptionPane.showMessageDialog(null,	msg		 );
		m_rhpApplication.writeToOutputWindow("Simple Plugin" , msg);

		switch (menuItem) {
		case "Rearrange diagram":
			prepareGraphData(element);
			break;

		default:
			break;
		}
	}

	private void prepareGraphData(IRPModelElement element) {
		graphicalInterface = new MbseGraphVisualizerUI("FBS Preview");

		if (element instanceof IRPDiagram) {
			diagram = (IRPDiagram) element;
			
			RhapsodyGraphModel rhapsodyGraphModel = new RhapsodyGraphModel();
		}
		
		
		
		
		


		// création du graph depuis le modèle avec SnecmaML
		graphicalInterface.setGraphData(graph);

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


	@SuppressWarnings("unchecked")
	private void sortIRPCollection(List list) {

		Collections.sort(list, new Comparator<IRPGraphElement>() {

			@Override
			public int compare(IRPGraphElement o1, IRPGraphElement o2) {
				if (o1 instanceof IRPGraphEdge && o2 instanceof IRPGraphNode) {
					return 1;
				}
				else if (o1 instanceof IRPGraphNode && o2 instanceof IRPGraphEdge) {
					return -1;
				}
				else
					return 0;

			}
		});	
	}

	/**
	 * To be defined.
	 * @return Probably Map<GUID, (X.int,Y.int)>
	 */
	public void getObjectPositions() {
		
		mxGraph graph = graphicalInterface.getGraphModel();

		mxGraphModel graphModel = (mxGraphModel) graph.getModel();	
		
		for (Entry<String, IRPGraphElement> entry : map.entrySet())
		{
			System.out.println("GUID = " + entry.getKey() + ", IRPGraph = " + entry.getValue());
			String GUID = entry.getKey();
			
			mxCell cell = (mxCell) graphModel.getCell(GUID);
			
			if (cell == null)
			{
				System.out.println("not found for: "+GUID);
				continue;
			}
			
			if (cell.isVisible()) 
			{
				if(cell.getGeometry()==null)
					continue;
				
				if (cell.isVertex())
				{
					IRPGraphNode node = (IRPGraphNode) entry.getValue();
					System.out.println("vertex: "+cell.getGeometry());
					node.setGraphicalProperty("Position", (int) cell.getGeometry().getX()+","+ (int) cell.getGeometry().getY());
					System.out.println("vertex position: "+(int) cell.getGeometry().getX()+","+ (int) cell.getGeometry().getY());
				}
				else
				{

					
					System.out.println("Visible: "+cell.isVisible() +" vertex: "+cell.isVertex());
					List<mxPoint> points = cell.getGeometry().getPoints();
					System.out.println(points.toString());
					
					IRPGraphEdge edge = (IRPGraphEdge) entry.getValue();
					System.out.println(" ---> "+ edge.getTarget().getGraphicalProperty("Position").getValue());
					
					IRPGraphElement Source = edge.getSource();
					
					List<mxPoint> newPoints = new ArrayList<mxPoint>(4);

					/*
					x = (int) (parentBounds.getX() + parentBounds.getWidth());
					y = (int) parentBounds.getCenterY();
					
					newPoints.add(new mxPoint(x, y));

					int centerXOffset = (int) ((childBounds.getX()-x)/2);
					x = (int) (x + centerXOffset);
					newPoints.add(new mxPoint(x, y));

					y = (int) childBounds.getCenterY();
					newPoints.add(new mxPoint(x, y));

					x = (int) childBounds.getX();
					newPoints.add(new mxPoint(x, y));
					
					edgesProperty = convertToPointsToString(newPoints);
					System.out.println("---> Polygon property: "+edgesProperty);*/

				}
			}
		}

		return;
	}

	private String convertToPointsToString(List<mxPoint> points) {
		String stringPoints = "";
		//number of points
		stringPoints+=points.size();
		
		//loop over points
		for (mxPoint point : points) 
		{
			stringPoints+=","+(int) point.getX()+","+(int) point.getY();
		}
		
		return stringPoints;
	}

	// called when the plug-in popup trigger (if applicable) fired
	public void OnTrigger(String trigger) {
		//show the trigger string
		//JOptionPane.showMessageDialog(null, "Hello world from SimplePlugin.OnTrigger " + trigger);
		String msg = "Hello world from SimplePlugin.OnTrigger " + trigger + "\n"; 
		m_rhpApplication.writeToOutputWindow("Simple Plugin" , msg);
	}

	// called when the project is closed - if true is returned the plugin will
	// be unloaded
	public boolean RhpPluginCleanup() {
		//JOptionPane.showMessageDialog(null,		"Hello world from SimplePlugin.RhpPluginCleanup");
		//cleanup
		m_rhpApplication = null;
		//return true so the plug-in will be unloaded now (on project close)
		return true;
	}

	// called when Rhapsody exits
	public void RhpPluginFinalCleanup() {
		//JOptionPane.showMessageDialog(null,	"Hello world from SimplePlugin.RhpPluginFinalCleanup");
	}
}
