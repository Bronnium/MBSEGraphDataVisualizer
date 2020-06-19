package com.mbse.graphx.connectors;

import java.util.LinkedHashMap;
import java.util.List;

import com.mbse.graphx.FoldableTree;
import com.mbse.graphx.layout.FunctionalBreakdownStructureLayout;
import com.mxgraph.model.mxGraphModel;
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

public class RhapsodyGraphModel extends mxGraph {

	private FoldableTree graph;
	private Object parent;

	public RhapsodyGraphModel() {
		// TODO Auto-generated constructor stub
		
		graph = new FoldableTree();
		parent = graph.getDefaultParent();

	}
	
	public RhapsodyGraphModel(IRPDiagram diagram) {
		
		FoldableTree graph = new FoldableTree();
		Object parent = graph.getDefaultParent();
	
		graph.getModel().beginUpdate();
		try {

			if (element instanceof IRPDiagram) {
				diagram = (IRPDiagram) element;

				List elements = diagram.getGraphicalElements().toList();
				sortIRPCollection(elements);

				//HashMap hm = new HashMap();
				map = new LinkedHashMap<String, IRPGraphElement>();


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
					else if (obj instanceof IRPGraphEdge)
					{
/*

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
*/
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
