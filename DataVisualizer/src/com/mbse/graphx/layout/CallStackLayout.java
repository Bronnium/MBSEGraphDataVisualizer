package com.mbse.graphx.layout;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import com.mxgraph.layout.mxGraphLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.swing.util.mxMorphing;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxRectangle;
import com.mxgraph.util.mxResources;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.view.mxCellState;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxGraphView;

/**
 * A Product Breakdown Structure is the decomposition of certain System.
 * This layout is suitable for a System decomposition. It's based on a Tree Layout
 * from Top to Bottom.
 * 
 * Note that, a system contains other systems (and can be recursive).
 */
public class CallStackLayout extends mxGraphLayout {

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

	/**
	 * 
	 * @param graph - the graphdata that will be displayed
	 */
	public CallStackLayout(mxGraph graph) {
		super(graph);

		//this.setResetEdges(true);
		//this.setLevelDistance(10); // gestion de l'espacement vertical
		//layout.setNodeDistance(20); // gestion de l'espacement horizontal

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

		callstackexecute(parent,null);
		
	}
	
	private void callstackexecute(Object parent, Object object) {
		if (parent == null && object == null)
			return;

		mxIGraphModel model = graph.getModel();
		mxGeometry pgeo = model.getGeometry(parent);

		if (parent instanceof mxCell) {
			mxCell cell = (mxCell) parent;
			int i = 0;
			double valueX=0;
			double valueY=0;
			int childCount = cell.getChildCount();

			for (i = 0; i < childCount; i++)
			{
				Object child = model.getChildAt(parent, i);
				if (child instanceof mxCell) {
					mxCell cellChild = (mxCell) child;
					mxGeometry bounds = model.getGeometry(cellChild);
					System.out.println("test");
					
					bounds.setX(valueX);
					valueX+=40;
					bounds.setY(valueY);
					valueY+=40;
					model.setGeometry(cellChild, bounds);
				}
				
			}			
			
		}

	}

	/**
	 * Hook for subclassers to return the container size.
	 */
	public mxRectangle getContainerSize()
	{
		return new mxRectangle();
	}

	public void execute(Object parent, Object root)
	{
		if (parent != null)
		{
			mxIGraphModel model = graph.getModel();
			mxGeometry pgeo = model.getGeometry(parent);

			// Handles special case where the parent is either a layer with no
			// geometry or the current root of the view in which case the size
			// of the graph's container will be used.
			if (pgeo == null && model.getParent(parent) == model.getRoot()
					|| parent == graph.getView().getCurrentRoot())
			{
				mxRectangle tmp = getContainerSize();
				pgeo = new mxGeometry(0, 0, tmp.getWidth(), tmp.getHeight());
			}

			double fillValue = 0;

			if (pgeo != null)
			{
				fillValue = (horizontal) ? pgeo.getHeight() : pgeo.getWidth();
			}

			fillValue -= 2 * spacing + 2 * border;

			// Handles swimlane start size
			mxRectangle size = graph.getStartSize(parent);
			fillValue -= (horizontal) ? size.getHeight() : size.getWidth();
			double x0 = this.x0 + size.getWidth() + border;
			double y0 = this.y0 + size.getHeight() + border;

			model.beginUpdate();
			try
			{
				double tmp = 0;
				mxGeometry last = null;
				int childCount = model.getChildCount(parent);

				for (int i = 0; i < childCount; i++)
				{
					Object child = model.getChildAt(parent, i);

					if (!isVertexIgnored(child) && isVertexMovable(child))
					{
						mxGeometry geo = model.getGeometry(child);

						if (geo != null)
						{
							geo = (mxGeometry) geo.clone();

							if (wrap != 0 && last != null)
							{

								if ((horizontal && last.getX()
										+ last.getWidth() + geo.getWidth() + 2
										* spacing > wrap)
										|| (!horizontal && last.getY()
												+ last.getHeight()
												+ geo.getHeight() + 2 * spacing > wrap))
								{
									last = null;

									if (horizontal)
									{
										y0 += tmp + spacing;
									}
									else
									{
										x0 += tmp + spacing;
									}

									tmp = 0;
								}
							}

							tmp = Math.max(tmp, (horizontal) ? geo
									.getHeight() : geo.getWidth());

							if (last != null)
							{
								if (horizontal)
								{
									geo.setX(last.getX() + last.getWidth()
									+ spacing);
								}
								else
								{
									geo.setY(last.getY() + last.getHeight()
									+ spacing);
								}
							}
							else
							{
								if (horizontal)
								{
									geo.setX(x0);
								}
								else
								{
									geo.setY(y0);
								}
							}

							if (horizontal)
							{
								geo.setY(y0);
							}
							else
							{
								geo.setX(x0);
							}

							if (fill  && fillValue > 0)
							{
								if (horizontal)
								{
									geo.setHeight(fillValue);
								}
								else
								{
									geo.setWidth(fillValue);
								}
							}

							model.setGeometry(child, geo);
							last = geo;
						}
					}
				}

				if (resizeParent && pgeo != null && last != null
						&& !graph.isCellCollapsed(parent))
				{
					pgeo = (mxGeometry) pgeo.clone();

					if (horizontal)
					{
						pgeo.setWidth(last.getX() + last.getWidth() + spacing);
					}
					else
					{
						pgeo
						.setHeight(last.getY() + last.getHeight()
						+ spacing);
					}

					model.setGeometry(parent, pgeo);
				}
			}
			finally
			{
				model.endUpdate();
			}
		}

	}
}

