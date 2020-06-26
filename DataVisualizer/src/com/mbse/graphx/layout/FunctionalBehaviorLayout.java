package com.mbse.graphx.layout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.swing.SwingConstants;

import com.mxgraph.canvas.mxICanvas;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.layout.hierarchical.model.mxGraphHierarchyModel;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.view.mxGraph;

public class FunctionalBehaviorLayout extends mxHierarchicalLayout implements MbseLayout {

	private mxCell frameCell;

	public FunctionalBehaviorLayout(mxGraph graph) {
		super(graph, SwingConstants.WEST);
		//setMoveTree(true); //tree is moved to TOP LEFT when updating view
		setMoveParent(true);
		setResizeParent(true);
		setFineTuning(false);
		traverseAncestors = true;	

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

		List<Object> candidateRoots = findInputs(parent);

		System.out.println(candidateRoots);

	}

	private List<Object> findInputs(Object parent) {
		List<Object> inputs = new ArrayList<Object>();

		mxIGraphModel model = graph.getModel();

		int childCount = model.getChildCount(parent);

		for (int i = 0; i < childCount; i++)
		{
			Object child = model.getChildAt(parent, i);

			if (graph.isPort(child))
			{
				System.out.println("X:"+graph.getCellGeometry(child).getX());

				if (graph.getCellGeometry(child).getX() == 0)
				{
					inputs.add(child);
				}
			}
		}


		return inputs;
	}




}
