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
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

import com.mbse.graphx.FoldableTree;
import com.mbse.graphx.layout.FunctionalBreakdownStructureLayout;
import com.mbse.graphx.layout.ProductBreakdownStructureLayout;
import com.mbse.graphx.ui.MbseGraphVisualizer;
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
	protected MbseGraphVisualizer graphicalInterface;
	private IRPDiagram diagram;
	private HashMap<String, IRPGraphElement> map;

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
			//
			generateGraphData(element);
			break;

		default:
			break;
		}
	}

	private void generateGraphData(IRPModelElement element) {
		graphicalInterface = new MbseGraphVisualizer("FBS Preview");

		FoldableTree graph = new FoldableTree();
		Object parent = graph.getDefaultParent();

		Map<String, Object> style = graph.getStylesheet().getDefaultEdgeStyle();
		//style.put(mxConstants.STYLE_EDGE, mxEdgeStyle.SideToSide);
		
		graph.getModel().beginUpdate();
		try {

			if (element instanceof IRPDiagram) {
				diagram = (IRPDiagram) element;

				List elements = diagram.getGraphicalElements().toList();
				sortIRPCollection(elements);

				//HashMap hm = new HashMap();
				map = new HashMap<String, IRPGraphElement>();


				// the list needs to be sorted !
				// nodes first then edges
				for (Object obj : elements) 
				{

					String guid = ((IRPGraphElement) obj).getGraphicalProperty("GUID").getValue();
					map.put(guid, (IRPGraphElement) obj);

					if (obj instanceof IRPGraphNode) {
						IRPGraphNode node = (IRPGraphNode) obj;

						if (node.getModelObject() instanceof IRPOperation) {
							String displayName = node.getModelObject().getDisplayName();
							String size = node.getGraphicalProperty("TextFontSize").getValue();

							int widthInPixels = Integer.valueOf(node.getGraphicalProperty("Width").getValue());
							int heightInPixels = Integer.valueOf(node.getGraphicalProperty("Height").getValue());
							//rhapsodyModel.addVertex(function);

							System.out.println(displayName+" added vertex:" +guid);
							graph.insertVertex(parent, guid, displayName, 0, 0, widthInPixels, heightInPixels);
						}


					}
					else if (obj instanceof IRPGraphEdge) {


						IRPGraphEdge edge = (IRPGraphEdge) obj;
						if (edge.getModelObject() instanceof IRPDependency) {
							//String guid = edge.getGraphicalProperty("GUID").getValue();

							String sourceGUID = edge.getSource().getGraphicalProperty("GUID").getValue();
							String targetGUID = edge.getTarget().getGraphicalProperty("GUID").getValue();
							System.out.println(sourceGUID +" ---> "+ targetGUID);

							//rhapsodyModel.addEdge(dependency);

							Object source = ((mxGraphModel)(graph.getModel())).getCell(sourceGUID);
							Object target = ((mxGraphModel)(graph.getModel())).getCell(targetGUID);
							System.out.println(source +" ---> "+ target);
							if (source != null && target != null)
							{
								graph.insertEdge(parent, guid, null, source, target);
							}
							else
							{
								System.out.println("error adding edge");
							}

						}

					}
				}

			}

			final FunctionalBreakdownStructureLayout layout = new FunctionalBreakdownStructureLayout(graph);
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

		for (Entry<String, Object> pair : graphModel.getCells().entrySet()) {
			//
			mxCell cell = (mxCell) pair.getValue();
			String searchGUID = pair.getKey();
			
			if (cell.isVisible()) 
			{
				System.out.println(pair.getKey() +" / "+ pair.getValue());
				if(cell.getGeometry()==null)
					continue;
				if (cell.isVertex())
				{
					IRPGraphNode node = (IRPGraphNode) map.get(searchGUID);
					System.out.println("vertex: "+cell.getGeometry());
					node.setGraphicalProperty("Position", (int) cell.getGeometry().getX()+","+ (int) cell.getGeometry().getY());
					System.out.println("vertex position: "+(int) cell.getGeometry().getX()+","+ (int) cell.getGeometry().getY());
				}
				else
				{

					System.out.println("Visible: "+cell.isVisible() +" vertex: "+cell.isVertex());
					List<mxPoint> points = cell.getGeometry().getPoints();
					System.out.println(points.toString());
					
					IRPGraphEdge edge = (IRPGraphEdge) map.get(searchGUID);
					
					String edgesProperty = convertToPointsToString(points);
	                //edge.setGraphicalProperty("Polygon", edgesProperty);
					System.out.println("---> Polygon property: "+edgesProperty);
					

					mxRectangle parentBounds = cell.getTerminal(true).getGeometry();
			
					mxRectangle childBounds = cell.getTerminal(false).getGeometry();

					double x = 0, y = 0;
									
					List<mxPoint> newPoints = new ArrayList<mxPoint>(4);

					x = parentBounds.getX() + parentBounds.getWidth();
					y = parentBounds.getCenterY();
					newPoints.add(new mxPoint(x, y));

					double centerXOffset = (childBounds.getX()-x)/2;
					x = x + centerXOffset;
					newPoints.add(new mxPoint(x, y));

					y = childBounds.getCenterY();
					newPoints.add(new mxPoint(x, y));

					x = childBounds.getX();
					newPoints.add(new mxPoint(x, y));
					
					edgesProperty = convertToPointsToString(newPoints);
					System.out.println("---> Polygon property: "+edgesProperty);

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
