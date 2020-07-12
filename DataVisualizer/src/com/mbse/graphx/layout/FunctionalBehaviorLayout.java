package com.mbse.graphx.layout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.SwingConstants;

import com.mxgraph.canvas.mxICanvas;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.layout.hierarchical.model.mxGraphHierarchyModel;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.model.mxICell;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.view.mxGraph;

public class FunctionalBehaviorLayout extends mxHierarchicalLayout implements MbseLayout {

	private mxCell frameCell;

	public FunctionalBehaviorLayout(mxGraph graph, mxCell rootElement) {
		super(graph, SwingConstants.WEST);
		//setMoveTree(true); //tree is moved to TOP LEFT when updating view
		setMoveParent(true);
		setResizeParent(true);
		setFineTuning(false);
		traverseAncestors = true;	

		frameCell = rootElement;

	}

	@Override
	public void setHorizontalSpacing(int spacing) {
		setInterRankCellSpacing(spacing);

	}

	@Override
	public void setVerticalSpacing(int spacing) {
		setIntraCellSpacing(spacing);		
	}

	/**
	 * Executes the layout for the children of the specified parent.
	 * 
	 * @param parent Parent cell that contains the children to be laid out.
	 */
	public void execute(Object parent)
	{

		System.out.println("TETETES"+frameCell);

		mxIGraphModel model = graph.getModel();

		model.beginUpdate();
		try
		{
			run(parent);

			if (isResizeParent() && !graph.isCellCollapsed(parent))
			{
				graph.updateGroupBounds(new Object[] { parent },
						getParentBorder(), isMoveParent());
			}

		}
		finally
		{
			model.endUpdate();
		}

	}

	@Override
	public void run(Object parent) {
		System.out.println("parent: frame"+parent);
		
		List<Set<Object>> hierarchyVertices = new ArrayList<Set<Object>>();
		Set<Object> allVertexSet = new LinkedHashSet<Object>();
		
		Set<Object> filledVertexSet = filterDescendants(parent);

		List<Object> candidateRoots = findInputs(parent);

		System.out.println(candidateRoots);
		
		for (Object root : candidateRoots)
		{
			Set<Object> vertexSet = new LinkedHashSet<Object>();
			hierarchyVertices.add(vertexSet);

			traverse(root, true, null, allVertexSet, vertexSet,
					hierarchyVertices, filledVertexSet);
		}
		
		
		// Perform a layout for each separate hierarchy
		// Track initial coordinate x-positioning
		double initialX = 0;
		Iterator<Set<Object>> iter = hierarchyVertices.iterator();

		while (iter.hasNext())
		{
			Set<Object> vertexSet = iter.next();

			this.model = new mxGraphHierarchyModel(this, vertexSet.toArray(),
					roots, parent);

			//cycleStage(parent);
			layeringStage();
			crossingStage(parent);
			//initialX = placementStage(initialX, parent);
		}

	}

	private List<Object> findInputs(Object parent) {
		List<Object> inputs = new ArrayList<Object>();

		mxIGraphModel model = graph.getModel();

		Set<Entry<String, Object>> entrySet = ((mxGraphModel) model).getCells().entrySet();
		// for-each loop
		for(Entry<String, Object> entry1 : entrySet) {
			
			mxCell childCell = (mxCell) entry1.getValue();
			
			if (graph.isPort(childCell) && childCell.isVertex())
			{
				if (graph.getCellGeometry(childCell).getX() == 0)
				{
					inputs.add(childCell);
				}
			}
			
		}

		return inputs;
	}




}
