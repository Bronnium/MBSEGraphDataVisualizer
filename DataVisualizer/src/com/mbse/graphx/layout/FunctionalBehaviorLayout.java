package com.mbse.graphx.layout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.swing.SwingConstants;

import com.mxgraph.canvas.mxICanvas;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.layout.hierarchical.model.mxGraphHierarchyModel;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.view.mxGraph;

public class FunctionalBehaviorLayout extends mxHierarchicalLayout implements MbseLayout {

	private mxCell frameCell;
	
	public FunctionalBehaviorLayout(mxGraph graph, mxCell frame) {
		super(graph, SwingConstants.WEST);
		//setMoveTree(true); //tree is moved to TOP LEFT when updating view
		setMoveParent(true);
		setResizeParent(true);
		setFineTuning(false);
		traverseAncestors = true;	
		
		frameCell = frame;
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
		execute(parent, null);
	}

	public void execute(Object parent, List<Object> roots)
	{
		//super.execute(parent);
		mxIGraphModel model = graph.getModel();

		// If the roots are set and the parent is set, only
		// use the roots that are some dependent of the that
		// parent.
		// If just the root are set, use them as-is
		// If just the parent is set use it's immediate
		// children as the initial set

		if (roots == null && parent == null)
		{
			// TODO indicate the problem
			return;
		}

		if (roots != null && parent != null)
		{
			for (Object root : roots)
			{
				if (!model.isAncestor(parent, root))
				{
					roots.remove(root);
				}
			}
		}

		this.roots = roots;

		model.beginUpdate();
		try
		{
			run(parent);
			
			//graph.setDefaultParent(frameCell);

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

	/**
	 * Creates a set of descendant cells
	 * @param cell The cell whose descendants are to be calculated
	 * @return the descendants of the cell (not the cell)
	 */
	public Set<Object> filterDescendants(Object cell)
	{
		mxIGraphModel model = graph.getModel();

		Set<Object> result = new LinkedHashSet<Object>();

		if (model.isVertex(cell) && cell != this.parent && graph.isCellVisible(cell))
		{
			if (! graph.isPort(cell))
			{
				result.add(cell);
			}

		}

		if (this.traverseAncestors || cell == this.parent
				&& graph.isCellVisible(cell))
		{
			int childCount = model.getChildCount(cell);

			for (int i = 0; i < childCount; i++)
			{
				Object child = model.getChildAt(cell, i);
				result.addAll(filterDescendants(child));
			}
		}

		return result;
	}


	public void run(Object parent)
	{
		// Separate out unconnected hierarchies
		List<Set<Object>> hierarchyVertices = new ArrayList<Set<Object>>();
		Set<Object> allVertexSet = new LinkedHashSet<Object>();

		if (this.roots == null && parent != null)
		{
			Set<Object> filledVertexSet = filterDescendants(parent);

			this.roots = new ArrayList<Object>();

			while (!filledVertexSet.isEmpty())
			{
				List<Object> candidateRoots = findRoots(parent, filledVertexSet);

				for (Object root : candidateRoots)
				{
					Set<Object> vertexSet = new LinkedHashSet<Object>();
					hierarchyVertices.add(vertexSet);

					traverse(root, true, null, allVertexSet, vertexSet,
							hierarchyVertices, filledVertexSet);
				}

				this.roots.addAll(candidateRoots);
			}
		}
		else
		{
			// Find vertex set as directed traversal from roots

			for (int i = 0; i < roots.size(); i++)
			{
				Set<Object> vertexSet = new LinkedHashSet<Object>();
				hierarchyVertices.add(vertexSet);

				traverse(roots.get(i), true, null, allVertexSet, vertexSet,
						hierarchyVertices, null);
			}
		}

		// Iterate through the result removing parents who have children in this layout


		// Perform a layout for each separate hierarchy
		// Track initial coordinate x-positioning
		double initialX = 0;
		Iterator<Set<Object>> iter = hierarchyVertices.iterator();

		while (iter.hasNext())
		{
			Set<Object> vertexSet = iter.next();

			this.model = new mxGraphHierarchyModel(this, vertexSet.toArray(),
					roots, parent);

			cycleStage(parent);
			layeringStage();
			crossingStage(parent);
			initialX = placementStage(initialX, parent);
		}
	}


}
