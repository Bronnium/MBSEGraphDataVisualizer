package com.mbse.graphx.layout;

import java.util.ArrayList;
import java.util.List;

import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.model.mxICell;
import com.mxgraph.util.mxPoint;
import com.mxgraph.util.mxRectangle;
import com.mxgraph.view.mxGraph;

/**
 * A Product Breakdown Structure is the decomposition of certain System.
 * This layout is suitable for a System decomposition. It's based on a Tree Layout
 * from Top to Bottom.
 * 
 * Note that, a system contains other systems (and can be recursive).
 */
public class ProductBreakdownStructureLayout extends mxCompactTreeLayout {

	/**
	 * Display Leafs over default is false.
	 */
	private boolean leafOver;
	/**
	 * 
	 * @param graph - the graphdata that will be displayed
	 */
	public ProductBreakdownStructureLayout(mxGraph graph) {
		super(graph, false);

		setMoveTree(true); //tree is moved to TOP LEFT when updating view


		prefHozEdgeSep = 0;
		prefVertEdgeOff = 0;

		minEdgeJetty=0;
	}

	public void setLeafOver(boolean selected) {
		leafOver = selected;
	}

	@Override
	public void execute(Object parent)
	{
		// Execute the CompactTreeLayout
		super.execute(parent);

		// Modify the edges to ensure they exit the source cell at the midpoint
		if(!horizontal)
		{
			// get all the vertexes
			Object[] vertexes = ((mxGraphModel)graph.getModel()).getChildVertices(graph.getModel(), graph.getDefaultParent());
			for(int i=0; i < vertexes.length; i++)
			{
				mxICell parentCell = ((mxICell)(vertexes[i]));

				// For each edge of the vertex
				for(int j=0; j < parentCell.getEdgeCount(); j++)
				{
					mxICell edge = parentCell.getEdgeAt(j);
					// Only consider edges that are from the cell
					if(edge.getTerminal(true) != parentCell)
					{
						continue;
					}
					mxRectangle parentBounds = getVertexBounds(parentCell);
			
					mxRectangle childBounds = getVertexBounds(edge.getTerminal(false));

					double x = 0, y = 0;
									
					List<mxPoint> newPoints = new ArrayList<mxPoint>(4);

					
					x = parentBounds.getCenterX();
					y = parentBounds.getY() + parentBounds.getHeight();
					newPoints.add(new mxPoint(x, y));

					double centerYOffset = (childBounds.getY()-y)/2;
					y = y + centerYOffset;
					newPoints.add(new mxPoint(x, y));

					x = childBounds.getCenterX();
					newPoints.add(new mxPoint(x, y));

					y = childBounds.getY();
					newPoints.add(new mxPoint(x, y));
					
					setEdgePoints(edge, newPoints);
				}

			}
		}  

	}

}
