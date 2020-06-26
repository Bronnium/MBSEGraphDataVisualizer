package com.mbse.graphx.layout.behavior;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.mbse.graphx.layout.MbseLayout;
import com.mbse.graphx.layout.behavior.stage.mxCoordinateAssignment;
import com.mbse.graphx.layout.behavior.stage.mxHierarchicalLayoutStage;
import com.mbse.graphx.layout.behavior.stage.mxMedianHybridCrossingReduction;
import com.mbse.graphx.layout.behavior.stage.mxMinimumCycleRemover;
import com.mxgraph.canvas.mxICanvas;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.layout.hierarchical.model.mxGraphHierarchyModel;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.view.mxCellState;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxGraphView;

public class FunctionalBehaviorLayout extends mxHierarchicalLayout implements MbseLayout {

	private String frameCell;
	
	public FunctionalBehaviorLayout(mxGraph graph, String frame) {
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
	 * Returns the model for this layout algorithm.
	 */
	public mxGraphHierarchyModel getModel()
	{
		return model;
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

	/**
	 * Executes the layout for the children of the specified parent.
	 * 
	 * @param parent Parent cell that contains the children to be laid out.
	 * @param roots the starting roots of the layout
	 */
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
	 * Returns all visible children in the given parent which do not have
	 * incoming edges. If the result is empty then the children with the
	 * maximum difference between incoming and outgoing edges are returned.
	 * This takes into account edges that are being promoted to the given
	 * root due to invisible children or collapsed cells.
	 * 
	 * @param parent Cell whose children should be checked.
	 * @return List of tree roots in parent.
	 */
	public List<Object> findRoots(Object parent, Set<Object> vertices)
	{
		List<Object> roots = new ArrayList<Object>();

		Object best = null;
		int maxDiff = -100000;
		mxIGraphModel model = graph.getModel();

		for (Object vertex : vertices)
		{
			if (model.isVertex(vertex) && graph.isCellVisible(vertex))
			{
				Object[] conns = this.getEdges(vertex);
				int fanOut = 0;
				int fanIn = 0;

				for (int k = 0; k < conns.length; k++)
				{
					Object src = graph.getView().getVisibleTerminal(conns[k],
							true);

					if (src == vertex)
					{
						fanOut++;
					}
					else
					{
						fanIn++;
					}
				}

				if (fanIn == 0 && fanOut > 0)
				{
					roots.add(vertex);
				}

				int diff = fanOut - fanIn;

				if (diff > maxDiff)
				{
					maxDiff = diff;
					best = vertex;
				}
			}
		}

		if (roots.isEmpty() && best != null)
		{
			roots.add(best);
		}

		return roots;
	}

	/**
	 * 
	 * @param cell
	 * @return
	 */
	public Object[] getEdges(Object cell)
	{
		mxIGraphModel model = graph.getModel();
		boolean isCollapsed = graph.isCellCollapsed(cell);
		List<Object> edges = new ArrayList<Object>();
		int childCount = model.getChildCount(cell);

		for (int i = 0; i < childCount; i++)
		{
			Object child = model.getChildAt(cell, i);

			if (isCollapsed || !graph.isCellVisible(child))
			{
				edges.addAll(Arrays.asList(mxGraphModel.getEdges(model, child,
						true, true, false)));
			}
		}

		edges.addAll(Arrays.asList(mxGraphModel.getEdges(model, cell, true,
				true, false)));
		List<Object> result = new ArrayList<Object>(edges.size());
		Iterator<Object> it = edges.iterator();

		while (it.hasNext())
		{
			Object edge = it.next();
			mxCellState state = graph.getView().getState(edge);
			Object source = (state != null) ? state.getVisibleTerminal(true)
					: graph.getView().getVisibleTerminal(edge, true);
			Object target = (state != null) ? state.getVisibleTerminal(false)
					: graph.getView().getVisibleTerminal(edge, false);

			if (((source != target) && ((target == cell && (parent == null || graph
					.isValidAncestor(source, parent, traverseAncestors))) || (source == cell && (parent == null || graph
					.isValidAncestor(target, parent, traverseAncestors))))))
			{
				result.add(edge);
			}
		}

		return result.toArray();
	}

	/**
	 * The API method used to exercise the layout upon the graph description
	 * and produce a separate description of the vertex position and edge
	 * routing changes made.
	 */
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
			result.add(cell);
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

	/**
	 * Traverses the (directed) graph invoking the given function for each
	 * visited vertex and edge. The function is invoked with the current vertex
	 * and the incoming edge as a parameter. This implementation makes sure
	 * each vertex is only visited once. The function may return false if the
	 * traversal should stop at the given vertex.
	 * 
	 * @param vertex <mxCell> that represents the vertex where the traversal starts.
	 * @param directed Optional boolean indicating if edges should only be traversed
	 * from source to target. Default is true.
	 * @param edge Optional <mxCell> that represents the incoming edge. This is
	 * null for the first step of the traversal.
	 * @param allVertices Array of cell paths for the visited cells.
	 */
	protected void traverse(Object vertex, boolean directed, Object edge,
			Set<Object> allVertices, Set<Object> currentComp,
			List<Set<Object>> hierarchyVertices, Set<Object> filledVertexSet)
	{
		mxGraphView view = graph.getView();
		mxIGraphModel model = graph.getModel();

		if (vertex != null && allVertices != null)
		{
			// Has this vertex been seen before in any traversal
			// And if the filled vertex set is populated, only 
			// process vertices in that it contains
			if (!allVertices.contains(vertex)
					&& (filledVertexSet == null ? true : filledVertexSet
							.contains(vertex)))
			{
				currentComp.add(vertex);
				allVertices.add(vertex);

				if (filledVertexSet != null)
				{
					filledVertexSet.remove(vertex);
				}

				int edgeCount = model.getEdgeCount(vertex);

				if (edgeCount > 0)
				{
					for (int i = 0; i < edgeCount; i++)
					{
						Object e = model.getEdgeAt(vertex, i);
						boolean isSource = view.getVisibleTerminal(e, true) == vertex;

						if (!directed || isSource)
						{
							Object next = view.getVisibleTerminal(e, !isSource);
							traverse(next, directed, e, allVertices,
									currentComp, hierarchyVertices,
									filledVertexSet);
						}
					}
				}
			}
			else
			{
				if (!currentComp.contains(vertex))
				{
					// We've seen this vertex before, but not in the current component
					// This component and the one it's in need to be merged
					Set<Object> matchComp = null;

					for (Set<Object> comp : hierarchyVertices)
					{
						if (comp.contains(vertex))
						{
							currentComp.addAll(comp);
							matchComp = comp;
							break;
						}
					}

					if (matchComp != null)
					{
						hierarchyVertices.remove(matchComp);
					}
				}
			}
		}
	}

	/**
	 * Executes the cycle stage. This implementation uses the
	 * mxMinimumCycleRemover.
	 */
	public void cycleStage(Object parent)
	{
		mxMinimumCycleRemover cycleStage = new mxMinimumCycleRemover(this);
		cycleStage.execute(parent);
	}

	/**
	 * Implements first stage of a Sugiyama layout.
	 */
	public void layeringStage()
	{
		model.initialRank();
		model.fixRanks();
	}

	/**
	 * Executes the crossing stage using mxMedianHybridCrossingReduction.
	 */
	public void crossingStage(Object parent)
	{
		mxMedianHybridCrossingReduction crossingStage = new mxMedianHybridCrossingReduction(
				this);
		crossingStage.execute(parent);
	}

	/**
	 * Executes the placement stage using mxCoordinateAssignment.
	 */
	public double placementStage(double initialX, Object parent)
	{
		mxCoordinateAssignment placementStage = new mxCoordinateAssignment(
				this, intraCellSpacing, interRankCellSpacing, orientation,
				initialX, parallelEdgeSpacing);
		placementStage.setFineTuning(fineTuning);
		placementStage.execute(parent);

		return placementStage.getLimitX() + interHierarchySpacing;
	}

	/**
	 * Returns the resizeParent flag.
	 */
	public boolean isResizeParent()
	{
		return resizeParent;
	}

	/**
	 * Sets the resizeParent flag.
	 */
	public void setResizeParent(boolean value)
	{
		resizeParent = value;
	}

	/**
	 * Returns the moveParent flag.
	 */
	public boolean isMoveParent()
	{
		return moveParent;
	}

	/**
	 * Sets the moveParent flag.
	 */
	public void setMoveParent(boolean value)
	{
		moveParent = value;
	}

	/**
	 * Returns parentBorder.
	 */
	public int getParentBorder()
	{
		return parentBorder;
	}

	/**
	 * Sets parentBorder.
	 */
	public void setParentBorder(int value)
	{
		parentBorder = value;
	}

	/**
	 * @return Returns the intraCellSpacing.
	 */
	public double getIntraCellSpacing()
	{
		return intraCellSpacing;
	}

	/**
	 * @param intraCellSpacing
	 *            The intraCellSpacing to set.
	 */
	public void setIntraCellSpacing(double intraCellSpacing)
	{
		this.intraCellSpacing = intraCellSpacing;
	}

	/**
	 * @return Returns the interRankCellSpacing.
	 */
	public double getInterRankCellSpacing()
	{
		return interRankCellSpacing;
	}

	/**
	 * @param interRankCellSpacing
	 *            The interRankCellSpacing to set.
	 */
	public void setInterRankCellSpacing(double interRankCellSpacing)
	{
		this.interRankCellSpacing = interRankCellSpacing;
	}

	/**
	 * @return Returns the orientation.
	 */
	public int getOrientation()
	{
		return orientation;
	}

	/**
	 * @param orientation
	 *            The orientation to set.
	 */
	public void setOrientation(int orientation)
	{
		this.orientation = orientation;
	}

	/**
	 * @return Returns the interHierarchySpacing.
	 */
	public double getInterHierarchySpacing()
	{
		return interHierarchySpacing;
	}

	/**
	 * @param interHierarchySpacing
	 *            The interHierarchySpacing to set.
	 */
	public void setInterHierarchySpacing(double interHierarchySpacing)
	{
		this.interHierarchySpacing = interHierarchySpacing;
	}

	public double getParallelEdgeSpacing()
	{
		return parallelEdgeSpacing;
	}

	public void setParallelEdgeSpacing(double parallelEdgeSpacing)
	{
		this.parallelEdgeSpacing = parallelEdgeSpacing;
	}

	/**
	 * @return Returns the fineTuning.
	 */
	public boolean isFineTuning()
	{
		return fineTuning;
	}

	/**
	 * @param fineTuning
	 *            The fineTuning to set.
	 */
	public void setFineTuning(boolean fineTuning)
	{
		this.fineTuning = fineTuning;
	}

	/**
	 *
	 */
	public boolean isDisableEdgeStyle()
	{
		return disableEdgeStyle;
	}

	/**
	 * 
	 * @param disableEdgeStyle
	 */
	public void setDisableEdgeStyle(boolean disableEdgeStyle)
	{
		this.disableEdgeStyle = disableEdgeStyle;
	}

	/**
	 * Returns <code>Hierarchical</code>, the name of this algorithm.
	 */
	public String toString()
	{
		return "Hierarchical";
	}

}
