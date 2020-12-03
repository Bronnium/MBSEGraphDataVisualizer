package com.mbse.model;

import java.util.ArrayList;
import java.util.LinkedList;

import com.mbse.graphx.layout.CallStackLayout;
import com.mbse.graphx.layout.DefaultMbseLayout;
import com.mbse.graphx.layout.FunctionalBreakdownStructureLayout;
import com.mbse.graphx.layout.MbseLayout;
import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.layout.mxGraphLayout;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.view.mxGraph;

public class MbseGraphModel extends mxGraph {

	protected MbseLayout appliedLayout;

	/*
	 * Probably the constructor to use
	 */
	public MbseGraphModel(LinkedList vertexList, LinkedList edgeList, MbseLayout layout) {
		this(vertexList, edgeList);
		
		appliedLayout = layout;

	}

	public MbseGraphModel(LinkedList<D2Element> vertexList, LinkedList<D2Line> edgeList) {
		super();
		
		appliedLayout = new DefaultMbseLayout(this);
		//appliedLayout = new CallStackLayout(this);
		/*
		//this.insertVertex(root, null, "TEST", 10, 10, 100, 100, "");
		for (D2Element element : vertexList) {
			// mxGraph.insertVertex(parent, id, value, x, y, width, height, style)
			insertVertex(defaultParent, element.getGuid(), element.getLabel(), 0, 0, 50, 30);
		}
		
		for (D2Line line : edgeList) {
			//mxGraph.insertEdge(parent, id, value, source, target, style)
			Object source = getD2ElementByGUID(line.getSourceGUID());
			Object target = getD2ElementByGUID(line.getTargetGUID());
			insertEdge(defaultParent, line.getGuid(), line.label, source, target);
		}
		*/
		Object parent = getDefaultParent();
		Object root = insertVertex(parent, "treeRoot", "Root", 0, 0, 60, 40);

        Object v1 = insertVertex(parent, "v1", "Child 1", 0, 0, 60, 40);
        insertEdge(parent, null, "", root, v1);

        Object v2 = insertVertex(parent, "v2", "Child 2", 0, 0, 60, 40);
        insertEdge(parent, null, "", root, v2);

        Object v3 = insertVertex(parent, "v3", "Child 3", 0, 0, 60, 40);
        insertEdge(parent, null, "", root, v3);

        Object v11 = insertVertex(parent, "v11", "Child 1.1", 0, 0, 60, 40);
        insertEdge(parent, null, "", v1, v11);

        Object v12 = insertVertex(parent, "v12", "Child 1.2", 0, 0, 60, 40);
        insertEdge(parent, null, "", v1, v12);

        Object v21 = insertVertex(parent, "v21", "Child 2.1", 0, 0, 60, 40);
        insertEdge(parent, null, "", v2, v21);

        Object v22 = insertVertex(parent, "v22", "Child 2.2", 0, 0, 60, 40);
        insertEdge(parent, null, "", v2, v22);

        Object v221 = insertVertex(parent, "v221", "Child 2.2.1", 0, 0, 60, 40);
        insertEdge(parent, null, "", v22, v221);

        Object v222 = insertVertex(parent, "v222", "Child 2.2.2", 0, 0, 60, 40);
        insertEdge(parent, null, "", v22, v222);

        Object v31 = insertVertex(parent, "v31", "Child 3.1", 0, 0, 60, 40);
        insertEdge(parent, null, "", v3, v31); 
	}
	
	protected Object getD2ElementByGUID(String GUID) {
		return ((mxGraphModel)(getModel())).getCell(GUID);
	}

	public MbseLayout getAppliedLayout() {
		return appliedLayout;
	}

	public void setAppliedLayout(MbseLayout appliedLayout) {
		this.appliedLayout = appliedLayout;
	}

}
