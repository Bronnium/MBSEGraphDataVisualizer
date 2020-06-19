package com.mbse.graphx.rhapsody;


import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.RenderingHints.Key;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.List;
import java.util.Map;

import com.mbse.graphx.MbseModel;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.model.mxICell;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.view.mxGraph;
import com.telelogic.rhapsody.core.IRPDependency;
import com.telelogic.rhapsody.core.IRPDiagram;
import com.telelogic.rhapsody.core.IRPGraphEdge;
import com.telelogic.rhapsody.core.IRPGraphElement;
import com.telelogic.rhapsody.core.IRPGraphNode;
import com.telelogic.rhapsody.core.IRPModelElement;
import com.telelogic.rhapsody.core.IRPOperation;

public class RhapsodyMbseModel extends MbseModel {

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
		Object parent = this.getDefaultParent();

		this.getModel().beginUpdate();
		try {
			List<IRPGraphElement> elements = diagram.getGraphicalElements().toList();

			// nodes first then edges
			for (IRPGraphElement graphElement : elements) 
			{
				String graphicalGUID = graphElement.getGraphicalProperty("GUID").getValue();

				if (graphElement instanceof IRPGraphNode) {
					IRPGraphNode node = (IRPGraphNode) graphElement;

					if (node.getModelObject() instanceof IRPOperation)
					{
						String displayName = node.getModelObject().getDisplayName();


						int widthInPixels = (int) (displayName.length()*5.5);
						int heightInPixels = 30;

						System.out.println(displayName+" added vertex:" +graphicalGUID);
						this.insertVertex(parent, graphicalGUID, displayName, 0, 0, widthInPixels, heightInPixels);
					}


				}
				else if (graphElement instanceof IRPGraphEdge)
				{
					IRPGraphEdge edge = (IRPGraphEdge) graphElement;
					if (edge.getModelObject() instanceof IRPDependency) {

						String sourceGUID = edge.getSource().getGraphicalProperty("GUID").getValue();
						String targetGUID = edge.getTarget().getGraphicalProperty("GUID").getValue();

						Object source = ((mxGraphModel)(this.getModel())).getCell(sourceGUID);
						Object target = ((mxGraphModel)(this.getModel())).getCell(targetGUID);
						System.out.println(source +" ---> "+ target);
						if (source != null && target != null)
						{
							this.insertEdge(parent, graphicalGUID, null, source, target);
						}
						else
						{
							System.out.println("error adding edge");
						}

					}
				}
			}


			RhapsodyMbseModel currentModel = this;
			this.addListener(mxEvent.FOLD_CELLS,  new mxIEventListener() {


				@Override
				public void invoke(Object sender, mxEventObject evt) {
					System.out.println("folding repositioning");

					appliedLayout.execute(currentModel.getDefaultParent());

				}
			});
		} finally {
			this.getModel().endUpdate();
		}
	}

	/**
	 * Adds a vertex
	 * @param rhapsodyElement
	 * @return
	 */
	public Object addVertex(IRPModelElement rhapsodyElement) {
		return this.insertVertex(null, rhapsodyElement.getGUID(), rhapsodyElement.getDisplayName(), 0, 0, 60, 40);
	}

	protected Object findObject(String GUID) {
		return ((mxGraphModel)(this.getModel())).getCell(GUID);
	}

	/**
	 * Adds an edge which is directed from Source to Target
	 * @param edgeElement
	 * @return
	 */
	public Object addEdge(IRPDependency edgeElement) {
		Object source = findObject(edgeElement.getDependent().getGUID());
		Object target = findObject(edgeElement.getDependsOn().getGUID());
		return this.insertEdge(null, null, edgeElement.getGUID(), source, target);
	}


}
