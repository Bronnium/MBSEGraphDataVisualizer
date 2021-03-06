package com.mbse.layout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.mxgraph.layout.mxGraphLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxICell;
import com.mxgraph.util.mxPoint;
import com.mxgraph.view.mxGraph;

/**
 * A Product Breakdown Structure is the decomposition of certain System.
 * This layout is suitable for a System decomposition. It's based on a Tree Layout
 * from Top to Bottom.
 * 
 * Note that, a system contains other systems (and can be recursive).
 */
public class CallStackLayout extends mxGraphLayout {

	private double Xspacing = 80;
	private double Yspacing = 60;
	private boolean edgeRouting;
	private boolean resizeParent;
	private boolean horizontal;
	private boolean moveTree;
	private HashSet<Object> parentsChanged;
	private int spacing = 0;
	private int border = 0;
	private double x0 = 0;
	private double y0 = 0;
	private int wrap = 0;
	private boolean fill = false;
	private double currentY;

	/**
	 * 
	 * @param graph - the graphdata that will be displayed
	 */
	public CallStackLayout(mxGraph graph) {
		super(graph);
		
		CallStackLayout layout = this;

	}

	/*
	 * (non-Javadoc)
	 * @see com.mxgraph.layout.mxIGraphLayout#execute(java.lang.Object)
	 */
	public void execute(Object parent)
	{
		super.execute(parent);
		System.out.println("Time to have fun !");
		//execute(parent, null);

		callstackexecute(parent);

	}

	private void callstackexecute(Object parent) {

		
		System.out.println("root element: "+parent);

		if (parent instanceof mxCell)
		{
			mxCell parentCell = (mxCell) parent;

			if(!parentCell.isVertex()) {
				parentCell = (mxCell) parentCell.getChildAt(0);
			}
			
			mxGeometry parentGeo = parentCell.getGeometry();

			for (int i=0; i<parentCell.getEdgeCount() ;i++)
			{
				
				mxICell childCell = ((mxCell) parentCell.getEdgeAt(i)).getTarget();

				if (!parentCell.equals(childCell))
				{
					mxGeometry childGeo = childCell.getGeometry();

					childGeo.setX(parentGeo.getX()+Xspacing);
					currentY = currentY + Yspacing;
					double max = Math.max(currentY, parentGeo.getY()+i*Yspacing);
					childGeo.setY(max);

					childCell.setGeometry(childGeo);

					Object[] edgesArray = graph.getEdgesBetween(parentCell, childCell, true);
					localEdgeRouting(edgesArray, parentGeo, childGeo);
					System.out.println(edgesArray);

					callstackexecute(childCell);
				}
				
				
			}
		}
		System.out.println("");

	}
	

	private void localEdgeRouting(Object[] edgesArray, mxGeometry parentBounds, mxGeometry childBounds) {
		List<mxPoint> newPoints = new ArrayList<mxPoint>(3);
		double x = 0;
		double y = 0;

		for (int i = 0; i < edgesArray.length; i++)
		{
			// 3 points edge
			x = parentBounds.getX() + parentBounds.getWidth() / 4.0; // 25%
			y = parentBounds.getY() + parentBounds.getHeight();// + currentXOffset;
			newPoints.add(new mxPoint(x, y));
			
			y = childBounds.getY()+ childBounds.getHeight() / 2.0;
			newPoints.add(new mxPoint(x, y));
			
			x = childBounds.getX();
			newPoints.add(new mxPoint(x, y));
			setEdgePoints(edgesArray[i], newPoints);
		}

	}

	public void setSpacing(int spacing2) {
		// TODO Auto-generated method stub
		Yspacing = spacing2;
	}
}

