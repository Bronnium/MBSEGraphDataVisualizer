package com.mbse.graphx.rhapsody;

import java.util.LinkedHashMap;
import java.util.List;

import com.mbse.graphx.FoldableTree;
import com.mbse.graphx.MbseModel;
import com.mbse.graphx.layout.FunctionalBreakdownStructureLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.model.mxICell;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.view.mxGraph;
import com.telelogic.rhapsody.core.IRPDependency;
import com.telelogic.rhapsody.core.IRPDiagram;
import com.telelogic.rhapsody.core.IRPGraphEdge;
import com.telelogic.rhapsody.core.IRPGraphElement;
import com.telelogic.rhapsody.core.IRPGraphNode;
import com.telelogic.rhapsody.core.IRPModelElement;
import com.telelogic.rhapsody.core.IRPOperation;

public class RhapsodyMbseModel extends MbseModel {

	private FoldableTree graph;
	private Object parent;

public RhapsodyMbseModel() {
	Object parent = this.getDefaultParent();

	this.getModel().beginUpdate();
	
	try
	{

		mxICell containerNode=(mxICell)this.insertVertex(parent, null, "a", 10, 10, 100, 100, "");
		mxICell sampleNode=(mxICell)this.insertVertex(parent, null, "s", 200, 10, 100, 100, "");
		this.insertEdge(parent, null, null, containerNode, sampleNode);
		

		mxCell titleCell = new mxCell("Title",new mxGeometry(20,60,60,30),"");
		titleCell.setVertex(true);
		
		mxCell title2Cell = new mxCell("Title2",new mxGeometry(20,60,60,30),"");
		title2Cell.setVertex(true);

		
		this.addCell(titleCell, containerNode);
		this.addCell(title2Cell, sampleNode);
       
	}
	finally
	{
		this.getModel().endUpdate();
	}
}

	public RhapsodyMbseModel(IRPDiagram diagram) {

		graph = new FoldableTree();
		parent = graph.getDefaultParent();

		graph.getModel().beginUpdate();
		try {
			List<IRPGraphElement> elements = diagram.getGraphicalElements().toList();

			//HashMap hm = new HashMap();
			//map = new LinkedHashMap<String, IRPGraphElement>();


			// the list needs to be sorted !
			// nodes first then edges
			for (IRPGraphElement graphElement : elements) 
			{

				String graphicalGUID = graphElement.getGraphicalProperty("GUID").getValue();
				//map.put(guid, (IRPGraphElement) obj);

				if (graphElement instanceof IRPGraphNode) {
					IRPGraphNode node = (IRPGraphNode) graphElement;

					if (node.getModelObject() instanceof IRPOperation) {
						String displayName = node.getModelObject().getDisplayName();

						int widthInPixels = Integer.valueOf(node.getGraphicalProperty("Width").getValue());
						int heightInPixels = Integer.valueOf(node.getGraphicalProperty("Height").getValue());
						//rhapsodyModel.addVertex(function);

						System.out.println(displayName+" added vertex:" +graphicalGUID);
						graph.insertVertex(parent, graphicalGUID, displayName, 0, 0, widthInPixels, heightInPixels);
					}
				}
				else if (graphElement instanceof IRPGraphEdge)
				{

				}
			}


			/*
			final FunctionalBreakdownStructureLayout layout = new FunctionalBreakdownStructureLayout(graph);
			graphicalInterface.currentAppliedLayout = layout;
			layout.execute(parent);


			graph.addListener(mxEvent.FOLD_CELLS,  new mxIEventListener() {

				@Override
				public void invoke(Object sender, mxEventObject evt) {
					System.out.println("folding repositioning");
					layout.execute(graph.getDefaultParent());
				}
			});*/
		} finally {
			graph.getModel().endUpdate();
		}
	}

	/**
	 * Adds a vertex
	 * @param rhapsodyElement
	 * @return
	 */
	public Object addVertex(IRPModelElement rhapsodyElement) {
		return graph.insertVertex(parent, rhapsodyElement.getGUID(), rhapsodyElement.getDisplayName(), 0, 0, 60, 40);
	}

	protected Object findObject(String GUID) {
		return ((mxGraphModel)(graph.getModel())).getCell(GUID);
	}

	/**
	 * Adds an edge which is directed from Source to Target
	 * @param edgeElement
	 * @return
	 */
	public Object addEdge(IRPDependency edgeElement) {
		Object source = findObject(edgeElement.getDependent().getGUID());
		Object target = findObject(edgeElement.getDependsOn().getGUID());
		return graph.insertEdge(parent, null, edgeElement.getGUID(), source, target);
	}


}
